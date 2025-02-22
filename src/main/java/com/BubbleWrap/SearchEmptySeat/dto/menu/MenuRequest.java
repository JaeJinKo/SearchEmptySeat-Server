package com.BubbleWrap.SearchEmptySeat.dto.menu;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MenuRequest {

    private Long storePK;

    private String name;

    private String section;

    private int price;

    private List<String> image;

    private String description;

    private boolean isAvailable;
}
