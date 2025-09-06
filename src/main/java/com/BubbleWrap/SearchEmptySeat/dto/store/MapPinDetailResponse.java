package com.BubbleWrap.SearchEmptySeat.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapPinDetailResponse {
    private Long storePK;        // 가게 PK
    private String storeName;   // 가게 이름
    private int availableSeats; // 빈자리 수
}
