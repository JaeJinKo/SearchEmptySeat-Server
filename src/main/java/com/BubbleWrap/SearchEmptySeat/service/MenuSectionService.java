package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionBulkUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.MenuSection;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.MenuSectionRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuSectionService {

    private final MenuSectionRepository menuSectionRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<MenuSectionResponse>>> getSectionsByStore(Long storePK) {
        Store store = storeRepository.findById(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        List<MenuSection> sections = menuSectionRepository.findByStoreOrderByPriorityAsc(store);
        List<MenuSectionResponse> response = sections.stream()
                .map(MenuSectionResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response, "Menu sections retrieved successfully"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateSection(Long sectionPK, MenuSectionRequest request) {
        MenuSection section = menuSectionRepository.findById(sectionPK)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_SECTION_NOT_FOUND));

        section.setName(request.getName());
        section.setPriority(request.getPriority());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("sectionPK", section.getSectionPK());
        responseData.put("name", section.getName());
        responseData.put("priority", section.getPriority());
        responseData.put("updatedDate", section.getUpdatedDate());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu section updated successfully"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteSection(Long sectionPK) {
        MenuSection section = menuSectionRepository.findById(sectionPK)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_SECTION_NOT_FOUND));

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("sectionPK", section.getSectionPK());
        responseData.put("name", section.getName());
        responseData.put("priority", section.getPriority());

        menuSectionRepository.delete(section);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu section deleted successfully"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> addSection(Long storePK, MenuSectionRequest request) {
        Store store = storeRepository.findById(storePK)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 동일한 이름의 섹션이 이미 존재하는지 확인
        if (menuSectionRepository.findByStoreAndName(store, request.getName()).isPresent()) {
            throw new BusinessException(ErrorCode.MENU_SECTION_ALREADY_EXISTS);
        }

        MenuSection section = new MenuSection();
        section.setStore(store);
        section.setName(request.getName());
        section.setPriority(request.getPriority());

        menuSectionRepository.save(section);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("sectionPK", section.getSectionPK());
        responseData.put("name", section.getName());
        responseData.put("priority", section.getPriority());
        responseData.put("createdDate", section.getCreatedDate());
        responseData.put("updatedDate", section.getUpdatedDate());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu section added successfully"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> bulkUpdateSections(Long storePK, java.util.List<MenuSectionBulkUpdateRequest> requests) {
        java.util.List<MenuSectionResponse> updated = new java.util.ArrayList<>();
        for (MenuSectionBulkUpdateRequest req : requests) {
            MenuSection section = menuSectionRepository.findById(req.getSectionPK())
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_SECTION_NOT_FOUND));
            if (!section.getStore().getStorePK().equals(storePK)) {
                throw new BusinessException(ErrorCode.MENU_SECTION_NOT_FOUND);
            }
            section.setName(req.getName());
            section.setPriority(req.getPriority());
            updated.add(new MenuSectionResponse(section));
        }
        return ResponseEntity.ok(ApiResponse.success(updated, "Menu sections updated successfully"));
    }
} 