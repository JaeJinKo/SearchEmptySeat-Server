package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteRequest;
import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // POST
    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteResponse>> createFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.createFavorite(request);
    }

    // GET
    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }
}
