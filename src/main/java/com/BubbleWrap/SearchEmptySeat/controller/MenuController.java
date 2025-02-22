package com.BubbleWrap.SearchEmptySeat.controller;


import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.OutOfStockRequest;
import com.BubbleWrap.SearchEmptySeat.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<Map<String, Object>>> addMenu(@RequestBody MenuRequest request){
        return menuService.addMenu(request);
    }
    @GetMapping("/store/{storePK}")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenu(@PathVariable Long storePK){
        return menuService.getMenu(storePK);
    }

    @PutMapping("update/{menuId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMenu(@PathVariable Long menuId, @RequestBody MenuRequest request) {
        return menuService.updateMenu(menuId, request);
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
