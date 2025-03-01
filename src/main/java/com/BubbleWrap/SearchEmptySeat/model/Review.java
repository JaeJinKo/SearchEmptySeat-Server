package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPK;

    private Long userPK;   // FK -> members
    private Long storePK;  // FK -> store

    @Convert(converter = JsonConverterList.class)
    private List<String> image; // 예: 여러 이미지 URL

    private int rating; // 1~5
    private String content;
    private LocalDateTime createdDate;
}
