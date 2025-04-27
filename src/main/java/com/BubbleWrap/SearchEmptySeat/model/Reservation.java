package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterMap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "tbl_reservation")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationPK;

    private Long userId;     // FK -> members
    private Long storePK;    // FK -> store
    private int reservationNum;
    private LocalDateTime reservationTime;

    @Convert(converter = JsonConverterMap.class)
    private Map<String, Object> menu; // JSON (선택 메뉴)

    @Convert(converter = JsonConverterMap.class)
    private Map<String, Object> placement; // JSON (좌석 정보)

    private String seats;
    private int partySize;
    private String paymentMethod;  // "point", "offline"
    private String status;         // "pending", "confirmed", "cancelled"
    private LocalDateTime createdDate;
    private LocalDateTime endDate;

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
