package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementRequest;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementResponse;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Placement;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.repository.PlacementRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PlacementService {

    private final PlacementRepository placementRepository;
    private final ReservationRepository reservationRepository;

    public PlacementService(PlacementRepository placementRepository, ReservationRepository reservationRepository) {
        this.placementRepository = placementRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<PlacementResponse>> createPlacement(PlacementRequest request) {
        Placement p = new Placement();
        p.setStorePK(request.getStorePK());
        p.setLayout(request.getLayout());
        p.setLayoutSize(request.getLayoutSize());
        p.setCreatedDate(LocalDateTime.now());
        p.setUpdatedDate(LocalDateTime.now());

        placementRepository.save(p);
        return ResponseEntity.ok(
                ApiResponse.success(new PlacementResponse(p), "자리 배치가 생성되었습니다.")
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<PlacementResponse>> getPlacementByStore(Long storePK) {
        Placement placement = placementRepository.findByStorePK(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLACEMENT_NOT_FOUND));
        return ResponseEntity.ok(
                ApiResponse.success(new PlacementResponse(placement), "가게의 자리 배치 정보를 조회했습니다.")
        );
    }

    @Transactional
    public ResponseEntity<ApiResponse<PlacementResponse>> updatePlacement(Long placementPK, PlacementUpdateRequest request) {
        Placement placement = placementRepository.findById(placementPK)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLACEMENT_NOT_FOUND));

        // 레이아웃 업데이트
        Map<String, Object> layout = placement.getLayout();
        Map<String, Object> updateLayout = request.getLayout();
        
        // 각 테이블의 상태만 업데이트
        for (Map.Entry<String, Object> entry : updateLayout.entrySet()) {
            String tableNumber = entry.getKey();
            if (layout.containsKey(tableNumber)) {
                Map<String, Object> tableInfo = (Map<String, Object>) layout.get(tableNumber);
                Map<String, Object> updateInfo = (Map<String, Object>) entry.getValue();
                tableInfo.put("status", updateInfo.get("status"));
                layout.put(tableNumber, tableInfo);
            }
        }

        placement.setLayout(layout);
        placement.setUpdatedDate(LocalDateTime.now());
        placementRepository.save(placement);

        return ResponseEntity.ok(
                ApiResponse.success(new PlacementResponse(placement), "자리 배치가 업데이트되었습니다.")
        );
    }

    @Scheduled(fixedRate = 1800000) // 30분마다 실행
    @Transactional
    public void updatePlacementStatusForReservations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyMinutesLater = now.plusMinutes(30);

        // 30분 이내의 예약 조회
        List<Reservation> upcomingReservations = reservationRepository.findByReservationTimeBetweenAndStatus(
                now, thirtyMinutesLater, "confirmed");

        for (Reservation reservation : upcomingReservations) {
            Placement placement = placementRepository.findByStorePK(reservation.getStorePK())
                    .orElse(null);

            if (placement != null) {
                Map<String, Object> layout = placement.getLayout();
                String tableNumber = String.valueOf(reservation.getTableNumber());

                if (layout.containsKey(tableNumber)) {
                    Map<String, Object> tableInfo = (Map<String, Object>) layout.get(tableNumber);
                    int currentStatus = (int) tableInfo.get("status");

                    // 현재 상태가 빈자리(0)인 경우에만 예약 상태(1)로 변경
                    if (currentStatus == 0) {
                        tableInfo.put("status", 1);
                        layout.put(tableNumber, tableInfo);
                        placement.setLayout(layout);
                        placement.setUpdatedDate(LocalDateTime.now());
                        placementRepository.save(placement);
                    }
                }
            }
        }
    }
}
