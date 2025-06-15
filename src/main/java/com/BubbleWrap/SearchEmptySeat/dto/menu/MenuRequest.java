package com.BubbleWrap.SearchEmptySeat.dto.menu;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuRequest {

    private Long storePK;

    private String name;

    private String section;

    private int priority;

    private int price;

    private String description;

    @JsonProperty("available")
    private boolean available;
}
