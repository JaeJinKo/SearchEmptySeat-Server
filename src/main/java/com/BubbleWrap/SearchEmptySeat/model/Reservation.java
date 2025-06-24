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
    @Column(name = "reservationpk")
    private Long reservationPK;

    @Column(name = "user_id")
    private Long userId;     // FK -> members

    @Column(name = "storepk")
    private Long storePK;    // FK -> store

    @Column(name = "reservation_num")
    private int reservationNum;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @Column(name = "table_num")
    private int tableNumber; // 예약된 테이블 번호

    @Convert(converter = JsonConverterMap.class)
    @Column(name = "menu")
    private Map<String, Object> menu; // JSON (선택 메뉴)

    @Column(name = "seats")
    private String seats;

    @Column(name = "party_size")
    private int partySize;

    @Column(name = "payment_method")
    private String paymentMethod;  // "point", "offline"

    @Column(name = "status")
    private String status;         // "pending", "confirmed", "cancelled"

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
