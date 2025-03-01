package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.paymentHistory.PaymentHistoryRequest;
import com.BubbleWrap.SearchEmptySeat.dto.paymentHistory.PaymentHistoryResponse;
import com.BubbleWrap.SearchEmptySeat.service.PaymentHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    public PaymentHistoryController(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentHistoryResponse>> create(@RequestBody PaymentHistoryRequest request) {
        return paymentHistoryService.createPaymentHistory(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentHistoryResponse>>> getAll() {
        return paymentHistoryService.getAllPaymentHistories();
    }
}
