package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewRequest;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewResponse;
import com.BubbleWrap.SearchEmptySeat.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> create(@RequestBody ReviewRequest request) {
        return reviewService.createReview(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAll() {
        return reviewService.getAllReviews();
    }
}
