package com.BubbleWrap.SearchEmptySeat.controller;


import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.OutOfStockRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.MenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Map<String, Object>>> addMenu(
            @RequestPart("data") String userData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        MenuRequest request;
        try {
            request = objectMapper.readValue(userData, MenuRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return menuService.addMenu(request, imageFile);
    }
    @GetMapping("/store/{storePK}")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenu(@PathVariable Long storePK){
        return menuService.getMenu(storePK);
    }

    @PutMapping("update/{menuId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMenu(
            @PathVariable Long menuId,
            @RequestPart("data") String userData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        MenuRequest request;
        try {
            request = objectMapper.readValue(userData, MenuRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return menuService.updateMenu(menuId, request, imageFile);
    }

    @DeleteMapping("del/{menuId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    @PatchMapping("/outofstock")
    public ResponseEntity<ApiResponse<List<Map<String,Object>>>> updateMenusStock(@RequestBody OutOfStockRequest request) {
        return menuService.updateMenusStock(request);
    }

}
