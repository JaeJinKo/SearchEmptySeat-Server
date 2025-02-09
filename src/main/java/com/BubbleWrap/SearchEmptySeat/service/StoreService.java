package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    public StoreService(StoreRepository storeRepository, MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerStore(StoreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        Store store = new Store();
        store.setOwner(owner);
        store.setStoreName(request.getStoreName());
        store.setLocation(request.getLocation());
        store.setDescription(request.getDescription());
        store.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        store.setBank(request.getBank());
        store.setAccountNumber(request.getAccountNumber());
        store.setDepositor(request.getDepositor());
        store.setCategory(request.getCategory());
        store.setBusinessHours(request.getBusinessHours());
        store.setImage(request.getImage());

        storeRepository.save(store);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("storeName", store.getStoreName());
        responseData.put("location", store.getLocation());
        responseData.put("description", store.getDescription());
        responseData.put("businessRegistrationNumber", store.getBusinessRegistrationNumber());
        responseData.put("bank", store.getBank());
        responseData.put("accountNumber", store.getAccountNumber());
        responseData.put("depositor", store.getDepositor());
        responseData.put("businessHours", store.getBusinessHours());
        responseData.put("image", store.getImage());
        responseData.put("category", store.getCategory());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Store registration successful"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getUserStores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Store> stores = storeRepository.findByOwnerUserId(owner.getUserId());

        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View My Stores"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View All Stores"));
    }

    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STORE_NOT_FOUND.getMessage()));

        return ResponseEntity.ok(ApiResponse.success(new StoreResponse(store, objectMapper), "View Store By Id"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(String category) {
        try {
            StoreCategory storeCategory = StoreCategory.valueOf(category.trim().toUpperCase());

            List<Store> stores = storeRepository.findByCategory(storeCategory);
            List<StoreResponse> response = stores.stream()
                    .map(store -> new StoreResponse(store, objectMapper))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(response, "View Stores By Category"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_CATEGORY.getCode(), ErrorCode.INVALID_CATEGORY.getMessage()));
        }
    }
}
