package com.BubbleWrap.SearchEmptySeat.dto.menu;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuSectionRequest {
    private Long storePK;
    private String name;
    private int priority;
} 