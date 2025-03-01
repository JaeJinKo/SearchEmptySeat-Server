package com.BubbleWrap.SearchEmptySeat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_paymentHistory")
@Getter
@Setter
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentHistory; // PK

    private Long reservationPK;  // FK -> tbl_reservation
    private int amount;
    private LocalDateTime paymentDate;
}
