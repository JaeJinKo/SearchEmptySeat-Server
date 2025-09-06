package com.BubbleWrap.SearchEmptySeat.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    private final StoreService storeService;
    private final ObjectMapper objectMapper;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
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
    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores(@RequestParam(value = "sortBy", required = false, defaultValue = "default") String sortBy) {
        return storeService.getAllStores(sortBy);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(@PathVariable Long storeId) {
        return storeService.getStoreById(storeId);
    }

    @GetMapping("/all/category/{category}")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(@PathVariable String category, @RequestParam(value = "sortBy", required = false, defaultValue = "default") String sortBy) {
        return storeService.getStoresByCategory(category, sortBy);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> searchStoresByName(@RequestParam String storeName) {
        logger.info("store search Request - target: {}", storeName);
        ResponseEntity<ApiResponse<List<StoreResponse>>> response = storeService.searchStoresByName(storeName);
        ApiResponse<List<StoreResponse>> apiResponse = response.getBody();
        if (apiResponse != null) {
            logger.info("store search Response - status: {}", apiResponse.getStatus());
            logger.info("store search Response - message: {}", apiResponse.getMessage());
            List<StoreResponse> stores = apiResponse.getData();
            if (stores != null) {
                logger.info("store search Result - number of stores: {}", stores.size());
                try {
                    String storesJson = objectMapper.writeValueAsString(stores);
                    logger.info("store search Result - data: {}", storesJson);
                } catch (JsonProcessingException e) {
                    logger.error("Error converting stores to JSON: {}", e.getMessage());
                }
            }
        }
        return response;
    }

    @GetMapping("/{storeId}/reservations/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getReservationStats(@PathVariable Long storeId) {
        Map<String, Object> data = storeService.getReservationStats(storeId);
        return ResponseEntity.ok(ApiResponse.success(data, "Reservation statistics retrieved successfully"));
    }

    @GetMapping("/{storeId}/today-stats")
    public ResponseEntity<ApiResponse<com.BubbleWrap.SearchEmptySeat.dto.store.TodayStatsResponse>> getTodayStats(@PathVariable Long storeId) {
        return storeService.getTodayStats(storeId);
    }
}
