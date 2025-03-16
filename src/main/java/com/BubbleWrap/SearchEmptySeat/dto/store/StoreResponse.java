package com.BubbleWrap.SearchEmptySeat.dto.store;

import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class StoreResponse {
    private Long storePK;
    private String storeName;
    private String location;
    private String description;
    private String businessRegistrationNumber;
    private String bank;
    private String accountNumber;
    private String depositor;
    private Map<String, String> businessHours;
    private List<String> image;
    private List<StoreCategory> category;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public StoreResponse(Store store) {
        this.storePK = store.getStorePK();
        this.storeName = store.getStoreName();
        this.location = store.getLocation();
        this.description = store.getDescription();
        this.businessRegistrationNumber = store.getBusinessRegistrationNumber();
        this.bank = store.getBank();
        this.accountNumber = store.getAccountNumber();
        this.depositor = store.getDepositor();
        this.businessHours = store.getBusinessHours();
        this.image = store.getImage();
        this.category = store.getCategory();
        this.createdDate = store.getCreatedDate();
        this.updatedDate = store.getUpdatedDate();
    }

    public StoreResponse(Store store, ObjectMapper objectMapper) {
        this.storePK = store.getStorePK();
        this.storeName = store.getStoreName();
        this.location = store.getLocation();
        this.description = store.getDescription();
        this.businessRegistrationNumber = store.getBusinessRegistrationNumber();
        this.bank = store.getBank();
        this.accountNumber = store.getAccountNumber();
        this.depositor = store.getDepositor();
        this.businessHours = store.getBusinessHours();
        this.image = store.getImage();
        this.category = store.getCategory();
        this.createdDate = store.getCreatedDate();
        this.updatedDate = store.getUpdatedDate();
    }
}
