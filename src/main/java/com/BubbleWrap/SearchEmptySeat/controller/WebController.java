package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.service.WebService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/web")
public class WebController {

    private final WebService webService;

    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/statistics/menu-sales/{storeId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getMenuSalesStatistics(@PathVariable Long storeId) {
        return webService.getMenuSalesStatistics(storeId);
    }
} 