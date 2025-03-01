package com.BubbleWrap.SearchEmptySeat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_storeViews")
public class StoreViews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewsPK;

    private Long storeId;
    private int viewCount;
    private LocalDateTime lastViewedDate;
    private LocalDateTime updatedDate;
}
