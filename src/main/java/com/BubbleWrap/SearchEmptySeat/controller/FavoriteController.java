package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteResponse;
import com.BubbleWrap.SearchEmptySeat.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{storeId}")
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(@PathVariable Long storeId) {
        return favoriteService.addFavorite(storeId);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> removeFavorite(@PathVariable Long storeId) {
        return favoriteService.removeFavorite(storeId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getFavoriteList(@PathVariable Long userId) {
        return favoriteService.getFavoriteList(userId);
    }
}
