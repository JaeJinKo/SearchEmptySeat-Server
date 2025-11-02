package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.AvailableTimeSlotsResponse;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationRequest;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.ReservationResponse;
import com.BubbleWrap.SearchEmptySeat.dto.reservation.TimeSlotResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Placement;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.PlacementRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final PlacementRepository placementRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository, 
                            StoreRepository storeRepository, PlacementRepository placementRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.storeRepository = storeRepository;
        this.placementRepository = placementRepository;
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

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<AvailableTimeSlotsResponse>> getAvailableTimeSlots(Long storePK, String date) {
        // 가게 정보 조회
        Store store = storeRepository.findById(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 자리 배치 정보 조회
        Placement placement = placementRepository.findByStorePK(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLACEMENT_NOT_FOUND));

        // 날짜 파싱
        LocalDate requestDate = LocalDate.parse(date);
        
        // 요일 확인
        DayOfWeek dayOfWeek = requestDate.getDayOfWeek();
        String dayName = getDayNameInKorean(dayOfWeek);

        // 임시 휴무일 확인
        if (store.getTemporaryHolidays() != null && store.getTemporaryHolidays().contains(date)) {
            throw new BusinessException(ErrorCode.STORE_CLOSED);
        }

        // 정기 휴무일 확인
        Map<String, Integer> regularHolidays = store.getRegularHolidays();
        if (regularHolidays != null && regularHolidays.containsKey(dayName) && regularHolidays.get(dayName) == 1) {
            throw new BusinessException(ErrorCode.STORE_CLOSED);
        }

        // 영업 시간 가져오기
        Map<String, String> businessHours = store.getBusinessHours();
        if (businessHours == null || !businessHours.containsKey(dayName)) {
            throw new BusinessException(ErrorCode.BUSINESS_HOURS_NOT_SET);
        }

        String[] hours = businessHours.get(dayName).split("-");
        String openTime = hours[0].trim();
        String closeTime = hours[1].trim();

        // 총 좌석 수 계산
        int totalSeats = calculateTotalSeats(placement.getLayout());

        // 시간대별 가용 좌석 계산
        List<TimeSlotResponse> timeSlots = new ArrayList<>();
        LocalTime start = LocalTime.parse(openTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(closeTime, DateTimeFormatter.ofPattern("HH:mm"));

        while (start.isBefore(end)) {
            LocalDateTime slotDateTime = LocalDateTime.of(requestDate, start);
            
            // 해당 시간대에 예약된 좌석 수 계산 (예약 시간 ~ 예약 시간 + 1시간 범위)
            LocalDateTime slotEndTime = slotDateTime.plusHours(1);
            List<Reservation> reservations = reservationRepository.findByStorePKAndReservationTimeBetween(
                storePK, slotDateTime, slotEndTime
            );

            // 취소되지 않은 예약만 카운트
            int reservedSeats = reservations.stream()
                    .filter(r -> !"cancelled".equals(r.getStatus()))
                    .mapToInt(Reservation::getPartySize)
                    .sum();

            int availableSeats = Math.max(0, totalSeats - reservedSeats);

            timeSlots.add(new TimeSlotResponse(
                start.format(DateTimeFormatter.ofPattern("HH:mm")),
                availableSeats
            ));

            start = start.plusHours(1);
        }

        AvailableTimeSlotsResponse response = new AvailableTimeSlotsResponse(
            date,
            openTime,
            closeTime,
            timeSlots
        );

        return ResponseEntity.ok(ApiResponse.success(response, "Available time slots fetched"));
    }

    private int calculateTotalSeats(Map<String, Object> layout) {
        if (layout == null || layout.isEmpty()) {
            return 0;
        }

        int totalSeats = 0;
        for (Map.Entry<String, Object> entry : layout.entrySet()) {
            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> tableInfo = (Map<String, Object>) entry.getValue();
                Object tableCapacity = tableInfo.get("table");
                if (tableCapacity instanceof Number) {
                    totalSeats += ((Number) tableCapacity).intValue();
                }
            }
        }
        return totalSeats;
    }

    private String getDayNameInKorean(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "월요일";
            case TUESDAY: return "화요일";
            case WEDNESDAY: return "수요일";
            case THURSDAY: return "목요일";
            case FRIDAY: return "금요일";
            case SATURDAY: return "토요일";
            case SUNDAY: return "일요일";
            default: return "";
        }
    }


}
