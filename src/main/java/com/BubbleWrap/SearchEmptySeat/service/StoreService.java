package com.BubbleWrap.SearchEmptySeat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.TodayStatsResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Review;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.BubbleWrap.SearchEmptySeat.model.StoreViews;
import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import com.BubbleWrap.SearchEmptySeat.repository.FavoriteRepository;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReviewRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreViewsRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReservationRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreViewsRepository storeViewsRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;
    private final FileStorageService fileStorageService;

    private int getStoreViews(Long storeId) {
        return storeViewsRepository.findByStoreStorePK(storeId)
                .map(StoreViews::getViewCount)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_VIEWS_NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerStore(StoreRequest request, List<MultipartFile> imageFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Store store = new Store();
        store.setOwner(owner);
        store.setStoreName(request.getStoreName());
        store.setLocation(request.getLocation());
        store.setDescription(request.getDescription());
        store.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        store.setBank(request.getBank());
        store.setAccountNumber(request.getAccountNumber());
        store.setDepositor(request.getDepositor());
        store.setCategory(request.getCategory());
        store.setBusinessHours(request.getBusinessHours());
        store.setRegularHolidays(request.getRegularHolidays());
        store.setTemporaryHolidays(request.getTemporaryHolidays());
        storeRepository.save(store);

        String subDirectory = "store/"+ store.getStorePK();
        List<String> storedImagePaths = fileStorageService.saveMultipleFiles(
                store.getStorePK(), subDirectory, "store", imageFiles
        );
        store.setImage(storedImagePaths);

        storeRepository.save(store);

        StoreViews storeViews = new StoreViews(store);
        storeViewsRepository.save(storeViews);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("storeName", store.getStoreName());
        responseData.put("location", store.getLocation());
        responseData.put("description", store.getDescription());
        responseData.put("businessRegistrationNumber", store.getBusinessRegistrationNumber());
        responseData.put("bank", store.getBank());
        responseData.put("accountNumber", store.getAccountNumber());
        responseData.put("depositor", store.getDepositor());
        responseData.put("businessHours", store.getBusinessHours());
        responseData.put("regularHolidays", store.getRegularHolidays());
        responseData.put("temporaryHolidays", store.getTemporaryHolidays());
        responseData.put("image", store.getImage());
        responseData.put("category", store.getCategory());
        responseData.put("viewCount", 0);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Store registration successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateStore(Long storeId, StoreRequest request, List<MultipartFile> imageFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        if (!store.getOwner().getUserId().equals(owner.getUserId())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (request.getStoreName() != null) store.setStoreName(request.getStoreName());
        if (request.getLocation() != null) store.setLocation(request.getLocation());
        if (request.getDescription() != null) store.setDescription(request.getDescription());
        if (request.getBusinessHours() != null) store.setBusinessHours(request.getBusinessHours());
        if (request.getRegularHolidays() != null) store.setRegularHolidays(request.getRegularHolidays());
        if (request.getTemporaryHolidays() != null) store.setTemporaryHolidays(request.getTemporaryHolidays());
        if (request.getCategory() != null) store.setCategory(request.getCategory());
        if (request.getBank() != null) store.setBank(request.getBank());
        if (request.getAccountNumber() != null) store.setAccountNumber(request.getAccountNumber());
        if (request.getDepositor() != null) store.setDepositor(request.getDepositor());

        // 이미지 업데이트
        if (imageFiles != null && !imageFiles.isEmpty()) {
            // 기존 이미지 삭제
            fileStorageService.deleteFiles(store.getImage());

            // 새 이미지 저장
            String subDirectory = "store/"+ store.getStorePK();
            List<String> storedImagePaths = fileStorageService.saveMultipleFiles(
                    store.getStorePK(), subDirectory, "store", imageFiles
            );
            store.setImage(storedImagePaths);
        }

        storeRepository.save(store);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("storeName", store.getStoreName());
        responseData.put("location", store.getLocation());
        responseData.put("description", store.getDescription());
        responseData.put("businessHours", store.getBusinessHours());
        responseData.put("regularHolidays", store.getRegularHolidays());
        responseData.put("temporaryHolidays", store.getTemporaryHolidays());
        responseData.put("category", store.getCategory());
        responseData.put("bank", store.getBank());
        responseData.put("accountNumber", store.getAccountNumber());
        responseData.put("depositor", store.getDepositor());
        responseData.put("image", store.getImage());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Store update successful"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getUserStores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Store> stores = storeRepository.findByOwnerUserId(owner.getUserId());

        List<StoreResponse> response = stores.stream()
                .map(store -> {
                    Double averageRating = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(store.getStorePK())
                            .stream()
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(0.0);
                    return new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK()), averageRating);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View My Stores"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores(String sortBy) {
        List<Store> stores = storeRepository.findAll();

        List<StoreResponse> response = stores.stream()
                .map(store -> {
                    Double averageRating = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(store.getStorePK())
                            .stream()
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(0.0);
                    int favoriteCount = favoriteRepository.countByStoreStorePK(store.getStorePK());
                    long reservationCount = reservationRepository.countByStorePK(store.getStorePK());
                    return new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK()), averageRating, favoriteCount, reservationCount);
                })
                .sorted((s1, s2) -> {
                    switch (sortBy) {
                        case "favorite":
                            return Integer.compare(s2.getFavoriteCount(), s1.getFavoriteCount());
                        case "rating":
                            return Double.compare(s2.getAverageRating(), s1.getAverageRating());
                        case "reservation":
                            return Integer.compare(s2.getReservationCount(), s1.getReservationCount());
                        default:
                            return 0; // 기본 순서
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View All Stores"));
    }

    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STORE_NOT_FOUND.getMessage()));

        StoreViews storeViews = storeViewsRepository.findByStoreStorePK(storeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STORE_VIEWS_NOT_FOUND.getMessage()));

        // Calculate average rating
        Double averageRating = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(storeId)
                .stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        StoreResponse storeResponse = new StoreResponse(store, objectMapper, storeViews.getViewCount(), averageRating);

        storeViews.increaseViewCount();
        storeViewsRepository.save(storeViews);

        return ResponseEntity.ok(ApiResponse.success(storeResponse, "View Store By Id"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(String category, String sortBy) {
        try {
            StoreCategory storeCategory = StoreCategory.valueOf(category.trim().toUpperCase());

            List<Store> stores = storeRepository.findByCategory(storeCategory);
            List<StoreResponse> response = stores.stream()
                    .map(store -> {
                        Double averageRating = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(store.getStorePK())
                                .stream()
                                .mapToDouble(Review::getRating)
                                .average()
                                .orElse(0.0);
                        int favoriteCount = favoriteRepository.countByStoreStorePK(store.getStorePK());
                        long reservationCount = reservationRepository.countByStorePK(store.getStorePK());
                        return new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK()), averageRating, favoriteCount, reservationCount);
                    })
                    .sorted((s1, s2) -> {
                        switch (sortBy) {
                            case "favorite":
                                return Integer.compare(s2.getFavoriteCount(), s1.getFavoriteCount());
                            case "rating":
                                return Double.compare(s2.getAverageRating(), s1.getAverageRating());
                            case "reservation":
                                return Integer.compare(s2.getReservationCount(), s1.getReservationCount());
                            default:
                                return 0; // 기본 순서
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(response, "View Stores By Category"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_CATEGORY.getCode(), ErrorCode.INVALID_CATEGORY.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> searchStoresByName(String storeName) {
        List<Store> stores = storeRepository.findByStoreNameContaining(storeName);

        List<StoreResponse> response = stores.stream()
                .map(store -> {
                    Double averageRating = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(store.getStorePK())
                            .stream()
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(0.0);
                    int favoriteCount = favoriteRepository.countByStoreStorePK(store.getStorePK());
                    long reservationCount = reservationRepository.countByStorePK(store.getStorePK());
                    return new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK()), averageRating, favoriteCount, reservationCount);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "Search Stores By Name"));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Map<String, Object> getReservationStats(Long storeId) {
        Map<String, Object> data = new HashMap<>();
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        // 요일별 예약 건수 계산 (한달 평균)
        List<Map<String, Object>> monthlyStats = new ArrayList<>();
        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        Map<String, Integer> dayCounts = new HashMap<>();
        Map<String, Integer> dayWeeks = new HashMap<>(); // 각 요일별 주차 수를 세기 위한 맵
        
        for (String day : days) {
            dayCounts.put(day, 0);
            dayWeeks.put(day, 0);
        }

        // 한달(30일) 전부터 오늘까지의 예약을 가져오기
        LocalDateTime monthStart = todayStart.minusDays(30);
        LocalDateTime monthEnd = todayEnd;

        List<Reservation> reservations = reservationRepository.findByStorePKAndReservationTimeBetween(storeId, monthStart, monthEnd);
        
        // 각 요일별로 예약 수를 세고, 해당 요일이 몇 주차에 나타났는지도 세기
        for (Reservation reservation : reservations) {
            String dayOfWeek = reservation.getReservationTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            dayCounts.put(dayOfWeek, dayCounts.get(dayOfWeek) + 1);
            
            // 해당 요일이 몇 주차에 나타났는지 계산 (30일 동안 최대 5주차까지 가능)
            int weekOfYear = reservation.getReservationTime().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            String weekKey = dayOfWeek + "_" + weekOfYear;
            if (!dayWeeks.containsKey(weekKey)) {
                dayWeeks.put(dayOfWeek, dayWeeks.get(dayOfWeek) + 1);
            }
        }

        // 각 요일별 평균 예약 수 계산
        for (String day : days) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("day", day);
            
            int totalCount = dayCounts.get(day);
            int weekCount = dayWeeks.get(day);
            
            // 해당 요일이 나타난 주차 수로 나누어 평균 계산
            // 최소 1주차는 있다고 가정 (0으로 나누기 방지)
            double averageTeams = weekCount > 0 ? (double) totalCount / weekCount : 0.0;
            stats.put("averageTeams", Math.round(averageTeams * 10.0) / 10.0); // 소수점 첫째자리까지 반올림
            
            monthlyStats.add(stats);
        }

        // 오늘 예약 수로 설정
        long currentReservations = reservationRepository.countByStorePKAndReservationTimeBetween(storeId, todayStart, todayEnd);
        data.put("currentReservations", (int) currentReservations);

        data.put("estimatedWaitTime", 1);
        data.put("monthlyStats", monthlyStats); // weeklyStats에서 monthlyStats로 변경
        return data;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<TodayStatsResponse>> getTodayStats(Long storeId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        // 오늘의 모든 예약 조회
        List<Reservation> todayReservations = reservationRepository.findByStorePKAndReservationTimeBetween(storeId, todayStart, todayEnd);
        
        // 오늘의 취소된 예약 조회 (삭제된 예약은 별도 테이블이 없으므로 createdDate 기준으로 오늘 생성된 것 중 삭제된 것)
        // 실제로는 예약 상태가 'cancelled'인 것들을 조회해야 하지만, 현재 모델에는 삭제만 있으므로
        // 실제 취소 건수는 0으로 설정 (추후 예약 상태 관리 개선 시 수정 필요)
        int cancelledReservations = 0;

        // 매출 계산
        long totalRevenue = 0;
        long highestReservationAmount = 0;
        List<Long> reservationAmounts = new ArrayList<>();

        for (Reservation reservation : todayReservations) {
            long reservationAmount = calculateReservationAmount(reservation);
            totalRevenue += reservationAmount;
            reservationAmounts.add(reservationAmount);
            
            if (reservationAmount > highestReservationAmount) {
                highestReservationAmount = reservationAmount;
            }
        }

        // 평균 예약 금액 계산
        double averageReservationAmount = reservationAmounts.isEmpty() ? 0.0 : 
            (double) totalRevenue / reservationAmounts.size();

        TodayStatsResponse todayStats = new TodayStatsResponse(
            now,
            totalRevenue,
            todayReservations.size(),
            cancelledReservations,
            Math.round(averageReservationAmount * 10.0) / 10.0, // 소수점 첫째자리까지 반올림
            highestReservationAmount
        );

        return ResponseEntity.ok(ApiResponse.success(todayStats, "View Today Stats"));
    }

    /**
     * 예약의 총 금액을 계산하는 헬퍼 메서드
     */
    private long calculateReservationAmount(Reservation reservation) {
        long totalAmount = 0;
        Map<String, Object> menu = reservation.getMenu();
        
        if (menu != null) {
            for (Map.Entry<String, Object> entry : menu.entrySet()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> menuItem = (Map<String, Object>) entry.getValue();
                
                if (menuItem.containsKey("price") && menuItem.containsKey("quantity")) {
                    long price = ((Number) menuItem.get("price")).longValue();
                    int quantity = ((Number) menuItem.get("quantity")).intValue();
                    totalAmount += price * quantity;
                }
            }
        }
        
        return totalAmount;
    }

}
