package com.BubbleWrap.SearchEmptySeat.dto.reservation;

import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ReservationResponse {
    private Long reservationPK;
    private Long userId;
    private Long storePK;
    private int reservationNum;
    private LocalDateTime reservationTime;
    private Map<String, Object> menu;
    private String seats;
    private int partySize;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime endDate;

    public ReservationResponse(Reservation r) {
        this.reservationPK = r.getReservationPK();
        this.userId = r.getUserId();
        this.storePK = r.getStorePK();
        this.reservationNum = r.getReservationNum();
        this.reservationTime = r.getReservationTime();
        this.menu = r.getMenu();
        this.seats = r.getSeats();
        this.partySize = r.getPartySize();
        this.paymentMethod = r.getPaymentMethod();
        this.status = r.getStatus();
        this.createdDate = r.getCreatedDate();
        this.endDate = r.getEndDate();
    }
}