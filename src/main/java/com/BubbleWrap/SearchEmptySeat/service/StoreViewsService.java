package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.storeViews.StoreViewsRequest;
import com.BubbleWrap.SearchEmptySeat.dto.storeViews.StoreViewsResponse;
import com.BubbleWrap.SearchEmptySeat.model.StoreViews;
import com.BubbleWrap.SearchEmptySeat.repository.StoreViewsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoreViewsService {

    private final StoreViewsRepository storeViewsRepository;

    public StoreViewsService(StoreViewsRepository storeViewsRepository) {
        this.storeViewsRepository = storeViewsRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<StoreViewsResponse>> createStoreViews(StoreViewsRequest request) {
        StoreViews sv = new StoreViews();
        sv.setStoreId(request.getStoreId());
        sv.setViewCount(request.getViewCount());
        sv.setLastViewedDate(LocalDateTime.now());
        sv.setUpdatedDate(LocalDateTime.now());

        storeViewsRepository.save(sv);

        return ResponseEntity.ok(
                ApiResponse.success(new StoreViewsResponse(sv), "StoreViews created")
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<StoreViewsResponse>>> getAllStoreViews() {
        List<StoreViews> list = storeViewsRepository.findAll();
        List<StoreViewsResponse> response = list.stream()
                .map(StoreViewsResponse::new)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(response, "All storeViews")
        );
    }
}
