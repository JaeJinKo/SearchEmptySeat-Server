package com.BubbleWrap.SearchEmptySeat.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapPinResponse {
    private Long storePK;        // 가게 PK
    private String location;     // 주소
}
