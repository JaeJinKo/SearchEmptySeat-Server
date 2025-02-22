package com.BubbleWrap.SearchEmptySeat.dto.menu;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutOfStockRequest {
    private List<MenuStockDto> menus;
}
