package com.BubbleWrap.SearchEmptySeat.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewRequest {
    private Long userPK;
    private Long storePK;
    private List<String> image;
    private int rating;
    private String content;
}