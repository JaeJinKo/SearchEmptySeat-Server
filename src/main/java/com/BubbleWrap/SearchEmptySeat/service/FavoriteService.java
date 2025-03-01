package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteRequest;
import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import com.BubbleWrap.SearchEmptySeat.repository.FavoriteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<FavoriteResponse>> createFavorite(FavoriteRequest request) {
        Favorite fav = new Favorite();
        fav.setUserId(request.getUserId());
        fav.setStoreID(request.getStoreID());
        fav.setFavoriteDate(LocalDateTime.now());
        favoriteRepository.save(fav);

        return ResponseEntity.ok(
                ApiResponse.success(new FavoriteResponse(fav), "Favorite created")
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getAllFavorites() {
        List<Favorite> list = favoriteRepository.findAll();
        List<FavoriteResponse> responseList = list.stream()
                .map(FavoriteResponse::new)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(responseList, "All favorites")
        );
    }
}
