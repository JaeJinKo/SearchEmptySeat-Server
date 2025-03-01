package com.BubbleWrap.SearchEmptySeat.dto.paymentHistory;

import com.BubbleWrap.SearchEmptySeat.model.PaymentHistory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentHistoryResponse {
    private Long paymentHistory;
    private Long reservationPK;
    private int amount;
    private LocalDateTime paymentDate;

    public PaymentHistoryResponse(PaymentHistory p) {
        this.paymentHistory = p.getPaymentHistory();
        this.reservationPK = p.getReservationPK();
        this.amount = p.getAmount();
        this.paymentDate = p.getPaymentDate();
    }
}