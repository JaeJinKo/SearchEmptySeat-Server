package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationRequest;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationResponse;
import com.BubbleWrap.SearchEmptySeat.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> create(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAll() {
        return reservationService.getAllReservations();
    }
}
