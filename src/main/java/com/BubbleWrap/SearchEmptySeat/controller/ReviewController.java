package com.BubbleWrap.SearchEmptySeat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewRequest;
import com.BubbleWrap.SearchEmptySeat.dto.review.ReviewResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Map<String, Object>>> addReview(
            @RequestPart("data") String requestJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewRequest request;
        try {
            request = objectMapper.readValue(requestJson, ReviewRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }

        return reviewService.registerReview(request, imageFiles);
    }

    @GetMapping("/store/{storePK}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long storePK) {
        return reviewService.getReviewsByStore(storePK);
    }
}