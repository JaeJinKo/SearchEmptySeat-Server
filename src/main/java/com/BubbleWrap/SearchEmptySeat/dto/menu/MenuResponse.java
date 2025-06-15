package com.BubbleWrap.SearchEmptySeat.dto.menu;

import com.BubbleWrap.SearchEmptySeat.model.Menu;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponse {

    private Long menuPK;

    private String name;

    private MenuSectionResponse section;

    private List<String> image;

    private int price;

    private String description;

    private boolean isAvailable;

    public MenuResponse(Menu menu, ObjectMapper objectMapper) {
        this.menuPK = menu.getMenuPK();
        this.name = menu.getName();
        this.section = menu.getSection() != null ? new MenuSectionResponse(menu.getSection()) : null;
        this.image = menu.getImage();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.isAvailable = menu.isAvailable();
    }
}
