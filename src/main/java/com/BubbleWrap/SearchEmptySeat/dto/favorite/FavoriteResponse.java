package com.BubbleWrap.SearchEmptySeat.dto.favorite;

import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FavoriteResponse {
    private Long favoritePK;
    private Long userId;
    private Long storeID;
    private LocalDateTime favoriteDate;

    public FavoriteResponse(Favorite fav) {
        this.favoritePK = fav.getFavoritePK();
        this.userId = fav.getUserId();
        this.storeID = fav.getStoreID();
        this.favoriteDate = fav.getFavoriteDate();
    }
}
