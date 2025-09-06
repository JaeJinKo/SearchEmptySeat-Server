package com.BubbleWrap.SearchEmptySeat.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.store.MapPinResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.MapPinDetailResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.Placement;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.BubbleWrap.SearchEmptySeat.repository.PlacementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapService {

    private final StoreRepository storeRepository;
    private final PlacementRepository placementRepository;

    /**
     * 지도 핀용 가게 목록 조회 (가게 PK, 주소만)
     */
    public ResponseEntity<ApiResponse<List<MapPinResponse>>> getMapPins() {
        List<Store> stores = storeRepository.findAll();
        
        List<MapPinResponse> mapPins = stores.stream()
                .map(store -> new MapPinResponse(store.getStorePK(), store.getLocation()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(mapPins, "Map pins fetched"));
    }

    /**
     * 지도 핀 디테일 조회 (가게 PK, 이름, 빈자리 수)
     */
    public ResponseEntity<ApiResponse<MapPinDetailResponse>> getMapPinDetail(Long storePK) {
        Store store = storeRepository.findById(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        
        // 빈자리 수 계산
        int availableSeats = calculateAvailableSeats(storePK);
        
        MapPinDetailResponse detail = new MapPinDetailResponse(
                store.getStorePK(),
                store.getStoreName(),
                availableSeats
        );
        
        return ResponseEntity.ok(ApiResponse.success(detail, "Map pin detail fetched"));
    }

    /**
     * 가게의 빈자리 수를 계산하는 헬퍼 메서드
     */
    private int calculateAvailableSeats(Long storePK) {
        // Placement에서 레이아웃 정보 가져오기
        Optional<Placement> placementOpt = placementRepository.findByStorePK(storePK);
        if (!placementOpt.isPresent()) {
            return 0; // 레이아웃이 없으면 빈자리 0개
        }
        
        Placement placement = placementOpt.get();
        Map<String, Object> layout = placement.getLayout();
        if (layout == null || layout.isEmpty()) {
            return 0;
        }
        
        int availableSeats = 0;
        
        // 각 테이블의 상태 확인
        for (Map.Entry<String, Object> entry : layout.entrySet()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> tableData = (Map<String, Object>) entry.getValue();
            
            if (tableData.containsKey("status")) {
                int status = ((Number) tableData.get("status")).intValue();
                if (status == 0) { // 0 = 빈자리
                    if (tableData.containsKey("table")) {
                        int maxCapacity = ((Number) tableData.get("table")).intValue();
                        availableSeats += maxCapacity;
                    }
                }
            }
        }
        
        return availableSeats;
    }
}
