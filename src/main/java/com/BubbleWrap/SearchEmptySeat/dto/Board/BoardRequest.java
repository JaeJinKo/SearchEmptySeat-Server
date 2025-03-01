package com.BubbleWrap.SearchEmptySeat.dto.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    private String title;
    private String content;
    private Long userId;
    private boolean notice;
}