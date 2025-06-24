package com.BubbleWrap.SearchEmptySeat.dto.placement;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PlacementRequest {
    private Long storePK;
    private Map<String, TableLayoutData> layout;
    private int layoutSize; // 레이아웃 크기(평수 or 인원수) 1="1~20", 2="21~40", 3="41~60"
}