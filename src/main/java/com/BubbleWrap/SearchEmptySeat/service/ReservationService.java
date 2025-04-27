package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationRequest;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationResponse;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(ReservationRequest request) {
        Reservation r = new Reservation();
        r.setUserId(request.getUserId());
        r.setStorePK(request.getStorePK());
        r.setReservationNum(request.getReservationNum());
        r.setReservationTime(request.getReservationTime());
        r.setMenu(request.getMenu());
        r.setSeats(request.getSeats());
        r.setPartySize(request.getPartySize());
        r.setPaymentMethod(request.getPaymentMethod());
        r.setStatus(request.getStatus());
        r.setCreatedDate(LocalDateTime.now());

        reservationRepository.save(r);

        return ResponseEntity.ok(ApiResponse.success(new ReservationResponse(r), "Reservation created"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations() {
        List<Reservation> list = reservationRepository.findAll();
        List<ReservationResponse> resp = list.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(resp, "All reservations"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<String>> cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getReservationTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.FAILED_CANCEL_RESERVATION.getCode(), ErrorCode.FAILED_CANCEL_RESERVATION.getMessage()));
        }

        reservationRepository.delete(reservation);
        return ResponseEntity.ok(ApiResponse.success("Reservation cancelled successfully"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getOwnerReservations(Long storeId) {
        List<Reservation> reservations = reservationRepository.findByStorePK(storeId);
        List<ReservationResponse> response = reservations.stream()
                .map(reservation -> new ReservationResponse(reservation))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Owner's reservations"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getUserReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        List<ReservationResponse> response = reservations.stream()
                .map(reservation -> new ReservationResponse(reservation))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(response, "User's reservations"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservationDetails(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return ResponseEntity.ok(ApiResponse.success(new ReservationResponse(reservation), "Reservation details"));
    }
}
