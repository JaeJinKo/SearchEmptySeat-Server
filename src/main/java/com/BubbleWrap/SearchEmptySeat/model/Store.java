package com.BubbleWrap.SearchEmptySeat.model;

import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterList;
import com.BubbleWrap.SearchEmptySeat.utils.JsonConverterMap;
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

    @Convert(converter = JsonConverterMap.class)
    private Map<String, Integer> regularHolidays; // 요일별 휴무일 (0=영업, 1=휴무)

    @Convert(converter = JsonConverterList.class)
    private List<String> temporaryHolidays; // 임시 휴무일 (날짜 리스트)

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

    private Double latitude;
    private Double longitude;

    public Long getStorePK() {
        return storePK;
    }

    public Member getOwner() {
        return owner;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public String getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDepositor() {
        return depositor;
    }

    public Map<String, String> getBusinessHours() {
        return businessHours;
    }

    public Map<String, Integer> getRegularHolidays() {
        return regularHolidays;
    }

    public List<String> getTemporaryHolidays() {
        return temporaryHolidays;
    }

    public List<String> getImage() {
        return image;
    }

    public List<StoreCategory> getCategory() {
        return category;
    }
}
