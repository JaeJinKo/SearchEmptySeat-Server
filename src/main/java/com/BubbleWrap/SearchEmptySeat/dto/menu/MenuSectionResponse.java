package com.BubbleWrap.SearchEmptySeat.dto.menu;

import com.BubbleWrap.SearchEmptySeat.model.MenuSection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuSectionResponse {
    private Long sectionPK;
    private String name;
    private int priority;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public MenuSectionResponse(MenuSection section) {
        this.sectionPK = section.getSectionPK();
        this.name = section.getName();
        this.priority = section.getPriority();
        this.createdDate = section.getCreatedDate();
        this.updatedDate = section.getUpdatedDate();
    }
} 