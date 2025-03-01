package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewRequest;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewResponse;
import com.BubbleWrap.SearchEmptySeat.model.Review;
import com.BubbleWrap.SearchEmptySeat.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(ReviewRequest request) {
        Review r = new Review();
        r.setUserPK(request.getUserPK());
        r.setStorePK(request.getStorePK());
        r.setImage(request.getImage());
        r.setRating(request.getRating());
        r.setContent(request.getContent());
        r.setCreatedDate(LocalDateTime.now());

        reviewRepository.save(r);

        return ResponseEntity.ok(ApiResponse.success(new ReviewResponse(r), "Review created"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
        List<Review> list = reviewRepository.findAll();
        List<ReviewResponse> resp = list.stream()
                .map(ReviewResponse::new)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(resp, "All reviews"));
    }
}
