package com.BubbleWrap.SearchEmptySeat.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotResponse {
    private String time;              // "11:00" 형식
    private Integer availableSeats;   // 예약 가능한 좌석 수
}

