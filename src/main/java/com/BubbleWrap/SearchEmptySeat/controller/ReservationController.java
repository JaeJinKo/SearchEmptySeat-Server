package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationRequest;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationResponse;
import com.BubbleWrap.SearchEmptySeat.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<ApiResponse<String>> cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    @GetMapping("/owner/{storeId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getOwnerReservations(@PathVariable Long storeId) {
        return reservationService.getOwnerReservations(storeId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }

    @GetMapping("/details/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservationDetails(@PathVariable Long reservationId) {
        return reservationService.getReservationDetails(reservationId);
    }
}
