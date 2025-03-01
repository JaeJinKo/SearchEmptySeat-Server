package com.BubbleWrap.SearchEmptySeat.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_board")
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPK;

    private String title;
    private String content;
    private Long userId; // FK -> tbl_members
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isNotice;
}
