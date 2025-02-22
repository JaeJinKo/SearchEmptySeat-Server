package com.BubbleWrap.SearchEmptySeat.dto.menu;

import com.BubbleWrap.SearchEmptySeat.model.Menu;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponse {
    private Store store;

    private String name;

    private String section;

    private List<String> image;

    private int price;

    private String description;

    private boolean isAvailable;

    public MenuResponse(Menu menu, ObjectMapper objectMapper) {
        this.store = menu.getStore();
        this.name = menu.getName();
        this.section = menu.getSection();
        this.image = menu.getImage();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.isAvailable = menu.isAvailable();
    }
}
