package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebService {

    private final ReservationRepository reservationRepository;

    public WebService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, String>>> getMenuSalesStatistics(Long storeId) {
        // 확정된 예약만 조회
        List<Reservation> confirmedReservations = reservationRepository.findByStorePKAndStatus(storeId, "confirmed");
        
        // 전체 메뉴 판매 수량 계산
        Map<String, Integer> menuCounts = new HashMap<>();
        int totalCount = 0;

        for (Reservation reservation : confirmedReservations) {
            Map<String, Object> menu = reservation.getMenu();
            for (Map.Entry<String, Object> entry : menu.entrySet()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> menuItem = (Map<String, Object>) entry.getValue();
                String menuName = (String) menuItem.get("name");
                int quantity = ((Number) menuItem.get("quantity")).intValue();
                
                menuCounts.merge(menuName, quantity, Integer::sum);
                totalCount += quantity;
            }
        }

        // 퍼센트 계산
        Map<String, String> menuPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : menuCounts.entrySet()) {
            if (totalCount > 0) {
                int percentage = (entry.getValue() * 100) / totalCount;
                menuPercentages.put(entry.getKey(), String.valueOf(percentage));
            }
        }

        return ResponseEntity.ok(ApiResponse.success(menuPercentages, "메뉴 판매율 통계를 조회했습니다."));
    }
} 