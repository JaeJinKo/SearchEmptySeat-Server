package com.BubbleWrap.SearchEmptySeat.dto.placement;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableLayoutData {
    private int x;           // X 좌표
    private int y;           // Y 좌표
    private int table;       // 최대 인원
    private int min;         // 최소 인원
    private int status;      // 0=빈자리, 1=예약, 2=사용중
} 