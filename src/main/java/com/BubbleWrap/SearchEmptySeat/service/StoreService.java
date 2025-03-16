package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreRequest;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.BubbleWrap.SearchEmptySeat.model.StoreViews;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreViewsRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreViewsRepository storeViewsRepository;
    private final ObjectMapper objectMapper;
    private final FileStorageService fileStorageService;

    private int getStoreViews(Long storeId) {
        return storeViewsRepository.findByStoreStorePK(storeId)
                .map(StoreViews::getViewCount)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_VIEWS_NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerStore(StoreRequest request, List<MultipartFile> imageFiles) {
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
        storeRepository.save(store);

        String subDirectory = "store/"+ store.getStorePK();
        List<String> storedImagePaths = fileStorageService.saveMultipleFiles(
                store.getStorePK(), subDirectory, "store", imageFiles
        );
        store.setImage(storedImagePaths);

        storeRepository.save(store);

        StoreViews storeViews = new StoreViews(store);
        storeViewsRepository.save(storeViews);

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
        responseData.put("viewCount", 0);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Store registration successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateStore(Long storeId, StoreRequest request, List<MultipartFile> imageFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        if (!store.getOwner().getUserId().equals(owner.getUserId())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (request.getStoreName() != null) store.setStoreName(request.getStoreName());
        if (request.getLocation() != null) store.setLocation(request.getLocation());
        if (request.getDescription() != null) store.setDescription(request.getDescription());
        if (request.getBusinessHours() != null) store.setBusinessHours(request.getBusinessHours());
        if (request.getCategory() != null) store.setCategory(request.getCategory());
        if (request.getBank() != null) store.setBank(request.getBank());
        if (request.getAccountNumber() != null) store.setAccountNumber(request.getAccountNumber());
        if (request.getDepositor() != null) store.setDepositor(request.getDepositor());

        // 이미지 업데이트
        if (imageFiles != null && !imageFiles.isEmpty()) {
            // 기존 이미지 삭제
            fileStorageService.deleteFiles(store.getImage());

            // 새 이미지 저장
            String subDirectory = "store/"+ store.getStorePK();
            List<String> storedImagePaths = fileStorageService.saveMultipleFiles(
                    store.getStorePK(), subDirectory, "store", imageFiles
            );
            store.setImage(storedImagePaths);
        }

        storeRepository.save(store);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("storeName", store.getStoreName());
        responseData.put("location", store.getLocation());
        responseData.put("description", store.getDescription());
        responseData.put("businessHours", store.getBusinessHours());
        responseData.put("category", store.getCategory());
        responseData.put("bank", store.getBank());
        responseData.put("accountNumber", store.getAccountNumber());
        responseData.put("depositor", store.getDepositor());
        responseData.put("image", store.getImage());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Store update successful"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getUserStores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Store> stores = storeRepository.findByOwnerUserId(owner.getUserId());

        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View My Stores"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> response = stores.stream()
                .map(store -> new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "View All Stores"));
    }

    public ResponseEntity<ApiResponse<StoreResponse>> getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STORE_NOT_FOUND.getMessage()));

        StoreViews storeViews = storeViewsRepository.findByStoreStorePK(storeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STORE_VIEWS_NOT_FOUND.getMessage()));

        storeViews.increaseViewCount();
        storeViewsRepository.save(storeViews);

        StoreResponse storeResponse = new StoreResponse(store, objectMapper, storeViews.getViewCount());

        return ResponseEntity.ok(ApiResponse.success(storeResponse, "View Store By Id"));
    }

    public ResponseEntity<ApiResponse<List<StoreResponse>>> getStoresByCategory(String category) {
        try {
            StoreCategory storeCategory = StoreCategory.valueOf(category.trim().toUpperCase());

            List<Store> stores = storeRepository.findByCategory(storeCategory);
            List<StoreResponse> response = stores.stream()
                    .map(store -> new StoreResponse(store, objectMapper, getStoreViews(store.getStorePK())))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(response, "View Stores By Category"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_CATEGORY.getCode(), ErrorCode.INVALID_CATEGORY.getMessage()));
        }
    }
}
