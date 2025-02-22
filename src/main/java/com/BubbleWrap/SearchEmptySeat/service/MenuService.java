package com.BubbleWrap.SearchEmptySeat.service;


import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Menu;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.MenuRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;

    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository, ObjectMapper objectMapper) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> addMenu(MenuRequest request){
//        Store store = storeRepository.findById(request.getStorePK())
//                .orElse(null);
//
//        if(store==null) return ResponseEntity.badRequest()
//                .body(ApiResponse.error(
//                        ErrorCode.STORE_NOT_FOUND.getCode(),
//                        ErrorCode.STORE_NOT_FOUND.getMessage()));
        Store store = storeRepository.findById(request.getStorePK())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = new Menu();
        menu.setStore(store);
        menu.setName(request.getName());
        menu.setSection(request.getSection());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());
        menu.setAvailable(request.isAvailable());

        menuRepository.save(menu);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("storeName", menu.getStore());
        responseData.put("name", menu.getName());
        responseData.put("section", menu.getSection());
        responseData.put("price", menu.getPrice());
        responseData.put("Description", menu.getDescription());
        responseData.put("available", menu.isAvailable());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu add successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenu(Long storePK){
        try {
            List<Menu> menus = menuRepository.findByStore(storePK);
            List<MenuResponse> response = menus.stream()
                    .map(menu -> new MenuResponse(menu, objectMapper))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(response, "View menus"));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.STORE_NOT_FOUND.getCode(), ErrorCode.STORE_NOT_FOUND.getMessage()));
        }
    }

}
