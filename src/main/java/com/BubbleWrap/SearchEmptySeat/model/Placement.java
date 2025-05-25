package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterMap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "tbl_placement")
@Getter
@Setter
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placementPK;

    private Long storePK; // FK -> tbl_store

    @Convert(converter = JsonConverterMap.class)
    private Map<String, Object> layout;
    /*
     * 레이아웃 예시
     * {
     *  "1": {
     *      "x": 10,
     *      "y": 10,
     *      "table": 4, (최대 인원)
     *      "min": 2, (최소 인원)
     *      "status": 0 (0=empty, 1=reserved, 2=occupied)
     *  },
     *  "2": {
     *      "x": 10,
     *      "y": 10,
     *      "table": 4, (최대 인원)
     *      "min": 2, (최소 인원)
     *      "status": 0 (0=empty, 1=reserved, 2=occupied)
     *  }
     * }
     */

    private int layoutSize; // 레이아웃 크기(평수 or 인원수) 1="1~20", 2="21~40", 3="41~60"

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
