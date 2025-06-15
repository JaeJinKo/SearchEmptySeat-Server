package com.BubbleWrap.SearchEmptySeat.dto.menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuSectionBulkUpdateRequest {
    private Long sectionPK;
    private String name;
    private int priority;
} 