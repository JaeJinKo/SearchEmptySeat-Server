package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerStore(@RequestBody StoreRequest request) {
        return storeService.registerStore(request);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> getUserStores() {
        return storeService.getUserStores();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(@PathVariable Long storeId) {
        return storeService.getStoreById(storeId);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(@PathVariable String category) {
        return storeService.getStoresByCategory(category);
    }
}
