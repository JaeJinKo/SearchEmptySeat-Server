package com.BubbleWrap.SearchEmptySeat.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTimeSlotsResponse {
    private String date;                    // "2024-10-24" 형식
    private String openTime;                // "11:00"
    private String closeTime;               // "22:00"
    private List<TimeSlotResponse> timeSlots;  // 시간대별 가용 좌석
}

