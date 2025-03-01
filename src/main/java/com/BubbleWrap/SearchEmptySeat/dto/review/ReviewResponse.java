package com.BubbleWrap.SearchEmptySeat.dto.review;

import com.BubbleWrap.SearchEmptySeat.model.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {
    private Long reviewPK;
    private Long userPK;
    private Long storePK;
    private List<String> image;
    private int rating;
    private String content;
    private LocalDateTime createdDate;

    public ReviewResponse(Review r) {
        this.reviewPK = r.getReviewPK();
        this.userPK = r.getUserPK();
        this.storePK = r.getStorePK();
        this.image = r.getImage();
        this.rating = r.getRating();
        this.content = r.getContent();
        this.createdDate = r.getCreatedDate();
    }
}