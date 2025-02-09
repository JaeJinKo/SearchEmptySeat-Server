package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public ResponseEntity<ApiResponse<String>> registerStore(StoreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Store store = new Store();
        store.setOwner(owner);
        store.setStoreName(request.getStoreName());
        store.setLocation(request.getLocation());
        store.setDescription(request.getDescription());
        store.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        store.setBank(request.getBank());
        store.setAccountNumber(request.getAccountNumber());
        store.setDepositor(request.getDepositor());
        store.setBusinessHours(request.getBusinessHours());
        store.setImage(request.getImage());
        store.setCategory(request.getCategory());

        storeRepository.save(store);
        return ResponseEntity.ok(ApiResponse.success("가게 등록이 완료되었습니다."));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getUserStores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Store> stores = storeRepository.findByOwnerUserId(owner.getUserId());

        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 가게를 찾을 수 없습니다."));

        return ResponseEntity.ok(ApiResponse.success(new StoreResponse(store, objectMapper)));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(String category) {
        try {
            StoreCategory storeCategory = StoreCategory.valueOf(category.trim().toUpperCase());

            List<Store> stores = storeRepository.findByCategory(storeCategory);
            List<StoreResponse> response = stores.stream()
                    .map(store -> new StoreResponse(store, objectMapper))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("유효하지 않은 카테고리입니다. 올바른 값: " + StoreCategory.values()));
        }
    }

}
