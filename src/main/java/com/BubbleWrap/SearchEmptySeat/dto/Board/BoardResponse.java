package com.BubbleWrap.SearchEmptySeat.dto.Board;

import com.BubbleWrap.SearchEmptySeat.model.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long boardPK;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean notice;

    public BoardResponse(Board b) {
        this.boardPK = b.getBoardPK();
        this.title = b.getTitle();
        this.content = b.getContent();
        this.userId = b.getUserId();
        this.createdDate = b.getCreatedDate();
        this.updatedDate = b.getUpdatedDate();
        this.notice = b.isNotice();
    }
}