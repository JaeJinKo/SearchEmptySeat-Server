package com.BubbleWrap.SearchEmptySeat.dto.favorite;

import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FavoriteResponse {
    private Long favoritePK;
    private StoreResponse store; // 가게 정보 포함

    public FavoriteResponse(Favorite favorite) {
        this.favoritePK = favorite.getFavoritePK();
        this.store = new StoreResponse(favorite.getStore()); // 가게 정보 포함
    }

    public FavoriteResponse(Favorite favorite, StoreResponse storeResponse) {
        this.favoritePK = favorite.getFavoritePK();
        this.store = storeResponse;
    }
}

