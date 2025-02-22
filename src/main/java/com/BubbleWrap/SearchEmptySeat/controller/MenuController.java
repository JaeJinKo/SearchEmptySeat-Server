package com.BubbleWrap.SearchEmptySeat.controller;


import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuResponse;
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

}
