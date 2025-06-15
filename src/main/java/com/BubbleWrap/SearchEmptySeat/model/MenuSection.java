package com.BubbleWrap.SearchEmptySeat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_menu_section")
public class MenuSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionPK;

    @ManyToOne
    @JoinColumn(name = "storePK", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int priority;

    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
} 