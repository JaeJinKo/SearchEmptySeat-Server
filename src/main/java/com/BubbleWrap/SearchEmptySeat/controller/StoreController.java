package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerStore(
            @RequestPart("data") String userData,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        StoreRequest request;
        try {
            request = objectMapper.readValue(userData, StoreRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return storeService.registerStore(request, imageFiles);
    }

    @PutMapping("/update/{storeId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateStore(
            @PathVariable Long storeId,
            @RequestPart("data") String userData,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        StoreRequest request;
        try {
            request = objectMapper.readValue(userData, StoreRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return storeService.updateStore(storeId, request, imageFiles);
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
