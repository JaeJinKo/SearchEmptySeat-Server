package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.paymentHistory.PaymentHistoryRequest;
import com.BubbleWrap.SearchEmptySeat.dto.paymentHistory.PaymentHistoryResponse;
import com.BubbleWrap.SearchEmptySeat.model.PaymentHistory;
import com.BubbleWrap.SearchEmptySeat.repository.PaymentHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<PaymentHistoryResponse>> createPaymentHistory(PaymentHistoryRequest request) {
        PaymentHistory p = new PaymentHistory();
        p.setReservationPK(request.getReservationPK());
        p.setAmount(request.getAmount());
        p.setPaymentDate(LocalDateTime.now());

        paymentHistoryRepository.save(p);
        return ResponseEntity.ok(
                ApiResponse.success(new PaymentHistoryResponse(p), "PaymentHistory created")
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<PaymentHistoryResponse>>> getAllPaymentHistories() {
        List<PaymentHistory> list = paymentHistoryRepository.findAll();
        List<PaymentHistoryResponse> resp = list.stream()
                .map(PaymentHistoryResponse::new)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(resp, "All payment histories"));
    }
}
