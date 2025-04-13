package com.BubbleWrap.SearchEmptySeat.dto.review;

import com.BubbleWrap.SearchEmptySeat.model.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {
    private Long reviewPK;
    private String userName;
    private double rating;
    private String content;
    private List<String> image;
    private LocalDateTime createdDate;

    public ReviewResponse(Review review) {
        this.reviewPK = review.getReviewPK();
        this.userName = review.getUser().getName();
        this.rating = review.getRating() / 2.0;
        this.content = review.getContent();
        this.image = review.getImage();
        this.createdDate = review.getCreatedDate();
    }
}