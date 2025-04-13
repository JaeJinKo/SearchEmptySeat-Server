package com.BubbleWrap.SearchEmptySeat.dto.review;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReviewRequest {
    private Long userPK;
    private Long storePK;
    private List<String> image;
    private int rating;
    private String content;
}