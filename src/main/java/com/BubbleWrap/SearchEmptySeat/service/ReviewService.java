package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewRequest;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewResponse;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewStatsResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Review;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.ReviewRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerReview(
            ReviewRequest request,
            List<MultipartFile> imageFiles
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findById(request.getStorePK())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Review review = new Review();
        review.setUser(user);
        review.setStore(store);
        review.setRating(request.getRating());  // 0~10 저장
        review.setContent(request.getContent());

        if (imageFiles != null && !imageFiles.isEmpty()) {
            if (imageFiles.size() > 5) {
                throw new BusinessException(ErrorCode.IMAGE_UPLOAD_LIMIT_EXCEEDED);
            }
            String path = "store/" + store.getStorePK() + "/review";
            List<String> savedImages = fileStorageService.saveMultipleFiles(user.getUserId(), path, "review", imageFiles);
            review.setImage(savedImages);
        }

        reviewRepository.save(review);

        Map<String, Object> data = new HashMap<>();
        data.put("reviewId", review.getReviewPK());
        data.put("storeId", store.getStorePK());
        data.put("user", user.getName());
        data.put("rating", review.getRating() / 2.0);
        data.put("content", review.getContent());
        data.put("image", review.getImage());

        return ResponseEntity.ok(ApiResponse.success(data, "Review submitted"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByStore(Long storePK) {
        List<Review> reviews = reviewRepository.findByStoreStorePKOrderByCreatedDateDesc(storePK);
        List<ReviewResponse> response = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(response, "Reviews fetched"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<ReviewStatsResponse>> getReviewStats(Long storePK) {
        // 가게 존재 여부 확인
        storeRepository.findById(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 총 리뷰 수
        Long totalReviews = reviewRepository.countByStoreStorePK(storePK);

        // 평균 평점 (DB에 0~10으로 저장되어 있으므로 0.0~5.0으로 변환)
        Double averageRating = reviewRepository.findAverageRatingByStorePK(storePK);
        if (averageRating == null) {
            averageRating = 0.0;
        } else {
            averageRating = averageRating / 2.0; // 0~10을 0~5로 변환
        }

        // 각 평점별 리뷰 수 (DB에는 0~10으로 저장되어 있음)
        // 1점 = 0~2, 2점 = 3~4, 3점 = 5~6, 4점 = 7~8, 5점 = 9~10
        Long rating1Count = reviewRepository.countByStoreStorePKAndRating(storePK, 0) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 1) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 2);
        
        Long rating2Count = reviewRepository.countByStoreStorePKAndRating(storePK, 3) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 4);
        
        Long rating3Count = reviewRepository.countByStoreStorePKAndRating(storePK, 5) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 6);
        
        Long rating4Count = reviewRepository.countByStoreStorePKAndRating(storePK, 7) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 8);
        
        Long rating5Count = reviewRepository.countByStoreStorePKAndRating(storePK, 9) +
                           reviewRepository.countByStoreStorePKAndRating(storePK, 10);

        ReviewStatsResponse stats = new ReviewStatsResponse(
                totalReviews,
                Math.round(averageRating * 10.0) / 10.0, // 소수점 첫째자리까지만
                rating1Count,
                rating2Count,
                rating3Count,
                rating4Count,
                rating5Count
        );

        return ResponseEntity.ok(ApiResponse.success(stats, "Review statistics fetched"));
    }
}