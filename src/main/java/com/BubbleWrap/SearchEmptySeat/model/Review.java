package com.BubbleWrap.SearchEmptySeat.model;

import java.time.LocalDateTime;
import java.util.List;

import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterList;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPK;

    @ManyToOne
    @JoinColumn(name = "userPK", nullable = false)
    private Member user;

    @ManyToOne
    @JoinColumn(name = "storePK", nullable = false)
    private Store store;

    @Convert(converter = JsonConverterList.class)
    private List<String> image;

    private int rating;  // 0 ~ 10 저장 (0.5 단위 × 2)

    private String content;

    private LocalDateTime createdDate = LocalDateTime.now();
}
