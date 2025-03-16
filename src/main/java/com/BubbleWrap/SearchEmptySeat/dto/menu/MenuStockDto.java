package com.BubbleWrap.SearchEmptySeat.dto.menu;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuStockDto {
    private Long menuPK;
    private boolean isAvailable;
}
