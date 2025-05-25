package com.BubbleWrap.SearchEmptySeat.dto.placement;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PlacementUpdateRequest {
    private Map<String, Object> layout;
} 