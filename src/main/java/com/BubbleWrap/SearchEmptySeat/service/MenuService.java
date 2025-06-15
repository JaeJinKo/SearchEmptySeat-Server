package com.BubbleWrap.SearchEmptySeat.service;


import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuRequest;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuResponse;
import com.BubbleWrap.SearchEmptySeat.dto.menu.MenuStockDto;
import com.BubbleWrap.SearchEmptySeat.dto.menu.OutOfStockRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Menu;
import com.BubbleWrap.SearchEmptySeat.model.MenuSection;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.MenuRepository;
import com.BubbleWrap.SearchEmptySeat.repository.MenuSectionRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuSectionRepository menuSectionRepository;
    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> addMenu(MenuRequest request, MultipartFile imageFile){
        Store store = storeRepository.findById(request.getStorePK())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        MenuSection section = null;
        if (request.getSection() != null && !request.getSection().trim().isEmpty()) {
            // 기존 섹션이 있는지 확인
            section = menuSectionRepository.findByStoreAndName(store, request.getSection())
                    .orElse(null);
            
            // 섹션이 없으면 새로 생성
            if (section == null) {
                section = new MenuSection();
                section.setStore(store);
                section.setName(request.getSection());
                section.setPriority(request.getPriority());
                menuSectionRepository.save(section);
            }
        }

        Menu menu = new Menu();
        menu.setStore(store);
        menu.setName(request.getName());
        menu.setSection(section);
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());
        menu.setAvailable(request.isAvailable());

        menuRepository.save(menu);

        List<String> profileImagePath = new ArrayList<>();
        String subDirectory = "store/" +menu.getStore().getStorePK() + "/menu";
        if (imageFile != null && !imageFile.isEmpty()) {
            profileImagePath.add(fileStorageService.saveSingleFile(menu.getMenuPK(), subDirectory, "meun", imageFile));
            menu.setImage(profileImagePath);
        }

        menuRepository.save(menu);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("name", menu.getName());
        responseData.put("section", section != null ? section.getName() : null);
        responseData.put("sectionPK", section != null ? section.getSectionPK() : null);
        responseData.put("priority", section != null ? section.getPriority() : null);
        responseData.put("price", menu.getPrice());
        responseData.put("image", menu.getImage());
        responseData.put("Description", menu.getDescription());
        responseData.put("available", menu.isAvailable());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu add successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenu(Long storePK){
        try {
            Store store = storeRepository.findById(storePK)
                    .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
            List<Menu> menus = menuRepository.findByStore(store);
            List<MenuResponse> response = menus.stream()
                    .map(menu -> new MenuResponse(menu, objectMapper))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(response, "View menus"));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.STORE_NOT_FOUND.getCode(), ErrorCode.STORE_NOT_FOUND.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMenu(Long menuId, MenuRequest request, MultipartFile imageFile) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        MenuSection section = null;
        if (request.getSection() != null && !request.getSection().trim().isEmpty()) {
            // 기존 섹션이 있는지 확인
            section = menuSectionRepository.findByStoreAndName(menu.getStore(), request.getSection())
                    .orElse(null);
            
            // 섹션이 없으면 새로 생성
            if (section == null) {
                section = new MenuSection();
                section.setStore(menu.getStore());
                section.setName(request.getSection());
                section.setPriority(request.getPriority());
                menuSectionRepository.save(section);
            }
        }

        Map<String, Object> responseData = new HashMap<>();

        menu.setName(request.getName());
        menu.setSection(section);
        menu.setPrice(request.getPrice());

        String subDirectory = "store/" +menu.getStore().getStorePK() + "/menu";
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedImagePath = fileStorageService.saveSingleFile(menu.getMenuPK(), subDirectory, "meun", imageFile);

            List<String> currentImages = menu.getImage();
            if (currentImages == null) {
                currentImages = new ArrayList<>();
            }
            currentImages.clear();
            currentImages.add(savedImagePath);

            menu.setImage(currentImages);
            responseData.put("image", currentImages);
        }

        menu.setDescription(request.getDescription());
        menu.setAvailable(request.isAvailable());

        responseData.put("menuId", menuId);
        responseData.put("name", menu.getName());
        responseData.put("section", section != null ? section.getName() : null);
        responseData.put("sectionPK", section != null ? section.getSectionPK() : null);
        responseData.put("priority", section != null ? section.getPriority() : null);
        responseData.put("price", menu.getPrice());
        responseData.put("image", menu.getImage());
        responseData.put("description", menu.getDescription());
        responseData.put("available", menu.isAvailable());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu update successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("menuId", menuId);
        responseData.put("name", menu.getName());
        responseData.put("section", menu.getSection() != null ? menu.getSection().getName() : null);
        responseData.put("sectionPK", menu.getSection() != null ? menu.getSection().getSectionPK() : null);
        responseData.put("priority", menu.getSection() != null ? menu.getSection().getPriority() : null);
        responseData.put("price", menu.getPrice());
        responseData.put("image", menu.getImage());
        responseData.put("description", menu.getDescription());
        responseData.put("available", menu.isAvailable());

        menuRepository.delete(menu);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Menu delete successful"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<Map<String,Object>>>> updateMenusStock(OutOfStockRequest request) {
        List<Map<String,Object>> updatedList = new ArrayList<>();
        for (MenuStockDto dto : request.getMenus()) {
            Menu menu = menuRepository.findById(dto.getMenuPK())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

            menu.setAvailable(dto.isAvailable());

            Map<String, Object> item = new HashMap<>();
            item.put("menuId", dto.getMenuPK());
            item.put("available", dto.isAvailable());
            updatedList.add(item);
        }

        return ResponseEntity.ok(
                ApiResponse.success(updatedList, "Bulk stock update success")
        );
    }
}
