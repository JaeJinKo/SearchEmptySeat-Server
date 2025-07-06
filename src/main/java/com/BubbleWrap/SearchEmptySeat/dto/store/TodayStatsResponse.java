package com.BubbleWrap.SearchEmptySeat.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodayStatsResponse {
    private LocalDateTime currentTime;        // 현재 시간
    private long totalRevenue;                // 오늘 총 매출
    private int totalReservations;            // 오늘 예약 건수
    private int cancelledReservations;        // 오늘 예약 취소 건수
    private double averageReservationAmount;  // 평균 예약 금액
    private long highestReservationAmount;    // 최고 예약 금액
} 