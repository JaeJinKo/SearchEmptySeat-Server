package com.BubbleWrap.SearchEmptySeat.dto.storeViews;

import com.BubbleWrap.SearchEmptySeat.model.StoreViews;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreViewsResponse {
    private Long viewsPK;
    private Long storeId;
    private int viewCount;
    private LocalDateTime lastViewedDate;
    private LocalDateTime updatedDate;

    public StoreViewsResponse(StoreViews sv) {
        this.viewsPK = sv.getViewsPK();
        this.storeId = sv.getStoreId();
        this.viewCount = sv.getViewCount();
        this.lastViewedDate = sv.getLastViewedDate();
        this.updatedDate = sv.getUpdatedDate();
    }
}