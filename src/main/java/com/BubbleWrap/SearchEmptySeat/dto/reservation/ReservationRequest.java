package com.BubbleWrap.SearchEmptySeat.dto.reservation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ReservationRequest {
    private Long userId;
    private Long storePK;
    private int reservationNum;
    private LocalDateTime reservationTime;
    private Map<String, Object> menu;
    private String seats;
    private int partySize;
    private String paymentMethod;
    private String status;
}