package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.Converter.JsonConverterList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuPK;

    @ManyToOne
    @JoinColumn(name = "storePK", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    private String section;  // 메뉴 섹션 (예: 음료, 메인, 사이드)

    @Convert(converter = JsonConverterList.class)
    private List<String> image;

    @Column(nullable = false)
    private int price;

    private String description;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
