package com.BubbleWrap.SearchEmptySeat.dto.favorite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private Long userId;
    private Long storeID;
}
