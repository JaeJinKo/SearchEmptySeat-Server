package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationRequest;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationResponse;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(ReservationRequest request) {
        // 토큰에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // 가게별 오늘 예약 번호 자동 생성
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        
        Integer maxReservationNum = reservationRepository.findMaxReservationNumByStorePKAndToday(request.getStorePK(), startOfDay, endOfDay);
        int nextReservationNum = (maxReservationNum != null) ? maxReservationNum + 1 : 1;

        Reservation r = new Reservation();
        r.setUserId(user.getUserId()); // 토큰에서 가져온 userId 사용
        r.setStorePK(request.getStorePK());
        r.setReservationNum(nextReservationNum); // 자동 생성된 예약 번호
        r.setReservationTime(request.getReservationTime());
        r.setTableNumber(request.getTableNumber());
        r.setMenu(request.getMenu());
        r.setPartySize(request.getPartySize());
        r.setPaymentMethod(request.getPaymentMethod());
        r.setStatus("pending"); // 항상 pending으로 시작
        r.setCreatedDate(LocalDateTime.now());
        r.setEndDateFromReservationTime(); // 예약 시간으로부터 1시간 후 설정

        reservationRepository.save(r);

        return ResponseEntity.ok(ApiResponse.success(new ReservationResponse(r), "Reservation created"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations() {
        List<Reservation> list = reservationRepository.findAllByOrderByReservationPKDesc();
        List<ReservationResponse> resp = list.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(resp, "All reservations"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<String>> cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // 이미 취소된 예약인지 확인
        if ("cancelled".equals(reservation.getStatus())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("RESERVATION_ALREADY_CANCELLED", "이미 취소된 예약입니다."));
        }

        // 예약 시간 30분 전까지만 취소 가능
        if (reservation.getReservationTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.FAILED_CANCEL_RESERVATION.getCode(), ErrorCode.FAILED_CANCEL_RESERVATION.getMessage()));
        }

        // 삭제 대신 status를 cancelled로 변경
        reservation.setStatus("cancelled");
        reservationRepository.save(reservation);
        
        return ResponseEntity.ok(ApiResponse.success(null, "Reservation cancelled successfully"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getOwnerReservations(Long storeId) {
        List<Reservation> reservations = reservationRepository.findByStorePKOrderByReservationPKDesc(storeId);
        List<ReservationResponse> response = reservations.stream()
                .map(reservation -> new ReservationResponse(reservation))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Owner's reservations"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getUserReservations() {
        // 토큰에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Reservation> reservations = reservationRepository.findByUserIdOrderByReservationPKDesc(user.getUserId());
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



    @Transactional
    public ResponseEntity<ApiResponse<String>> completeExpiredReservations() {
        // 예약 시간이 1시간 지난 pending 예약들을 completed로 변경
        List<Reservation> expiredReservations = reservationRepository.findByStatusAndReservationTimeBefore("pending", LocalDateTime.now().minusHours(1));
        
        for (Reservation reservation : expiredReservations) {
            reservation.setStatus("completed");
            reservationRepository.save(reservation);
        }

        return ResponseEntity.ok(ApiResponse.success("Expired reservations completed: " + expiredReservations.size()));
    }


}
