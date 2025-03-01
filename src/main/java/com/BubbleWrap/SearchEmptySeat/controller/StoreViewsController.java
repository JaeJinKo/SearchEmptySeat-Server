package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.storeViews.StoreViewsRequest;
import com.BubbleWrap.SearchEmptySeat.dto.storeViews.StoreViewsResponse;
import com.BubbleWrap.SearchEmptySeat.service.StoreViewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store-views")
public class StoreViewsController {

    private final StoreViewsService storeViewsService;

    public StoreViewsController(StoreViewsService storeViewsService) {
        this.storeViewsService = storeViewsService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StoreViewsResponse>> create(@RequestBody StoreViewsRequest request) {
        return storeViewsService.createStoreViews(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreViewsResponse>>> getAll() {
        return storeViewsService.getAllStoreViews();
    }
}

