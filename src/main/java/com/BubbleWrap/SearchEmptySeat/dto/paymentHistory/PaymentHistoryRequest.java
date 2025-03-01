package com.BubbleWrap.SearchEmptySeat.dto.paymentHistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentHistoryRequest {
    private Long reservationPK;
    private int amount;
}