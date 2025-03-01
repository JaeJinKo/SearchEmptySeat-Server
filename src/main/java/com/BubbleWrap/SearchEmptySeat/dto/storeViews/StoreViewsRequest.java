package com.BubbleWrap.SearchEmptySeat.dto.storeViews;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreViewsRequest {
    private Long storeId;
    private int viewCount;
}
