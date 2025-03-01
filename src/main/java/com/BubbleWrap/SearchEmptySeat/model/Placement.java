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

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
