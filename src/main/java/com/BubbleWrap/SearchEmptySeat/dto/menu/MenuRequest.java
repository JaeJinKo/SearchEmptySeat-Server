package com.BubbleWrap.SearchEmptySeat.dto.menu;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuRequest {

    private Long storePK;

    private String name;

    private String section;

    private int price;

    private String description;

    private boolean isAvailable;
}
