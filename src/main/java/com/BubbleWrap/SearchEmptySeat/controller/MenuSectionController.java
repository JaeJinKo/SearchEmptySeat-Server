package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuSectionBulkUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.service.MenuSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu-section")
@RequiredArgsConstructor
public class MenuSectionController {

    private final MenuSectionService menuSectionService;

    @GetMapping("/store/{storePK}")
    public ResponseEntity<ApiResponse<List<MenuSectionResponse>>> getSectionsByStore(@PathVariable Long storePK) {
        return menuSectionService.getSectionsByStore(storePK);
    }

    @PutMapping("/update/{sectionPK}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateSection(
            @PathVariable Long sectionPK,
            @RequestBody MenuSectionRequest request) {
        return menuSectionService.updateSection(sectionPK, request);
    }

    @DeleteMapping("/delete/{sectionPK}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteSection(@PathVariable Long sectionPK) {
        return menuSectionService.deleteSection(sectionPK);
    }

    @PutMapping("/bulk-update/{storePK}")
    public ResponseEntity<ApiResponse<?>> bulkUpdateSections(
            @PathVariable Long storePK,
            @RequestBody java.util.List<MenuSectionBulkUpdateRequest> requests) {
        return menuSectionService.bulkUpdateSections(storePK, requests);
    }
} 