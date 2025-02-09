package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.Converter.JsonConverterList;
import com.BubbleWrap.SearchEmptySeat.Converter.JsonConverterMap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storePK;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Member owner;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String location;

    private String description;

    @Column(nullable = false, unique = true)
    private String businessRegistrationNumber;

    private String bank;
    private String accountNumber;
    private String depositor;

    @Convert(converter = JsonConverterMap.class)
    private Map<String, String> businessHours;

    @Convert(converter = JsonConverterList.class)
    private List<String> image;

    @ElementCollection
    @CollectionTable(name = "store_categories", joinColumns = @JoinColumn(name = "store_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private List<StoreCategory> category;

    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate = LocalDateTime.now();
}
