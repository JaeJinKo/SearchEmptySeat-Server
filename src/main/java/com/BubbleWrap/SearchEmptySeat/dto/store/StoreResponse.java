package com.BubbleWrap.SearchEmptySeat.dto.store;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

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
    private Map<String, Integer> regularHolidays;
    private List<String> temporaryHolidays;
    private List<String> image;
    private List<StoreCategory> category;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int viewCount;
    private double averageRating;
    private int favoriteCount;
    private int reservationCount;

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
        this.regularHolidays = store.getRegularHolidays();
        this.temporaryHolidays = store.getTemporaryHolidays();
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
        this.regularHolidays = store.getRegularHolidays();
        this.temporaryHolidays = store.getTemporaryHolidays();
        this.image = store.getImage();
        this.category = store.getCategory();
        this.createdDate = store.getCreatedDate();
        this.updatedDate = store.getUpdatedDate();
    }

    public StoreResponse(Store store, ObjectMapper objectMapper, int viewCount) {
        this.storePK = store.getStorePK();
        this.storeName = store.getStoreName();
        this.location = store.getLocation();
        this.description = store.getDescription();
        this.businessRegistrationNumber = store.getBusinessRegistrationNumber();
        this.bank = store.getBank();
        this.accountNumber = store.getAccountNumber();
        this.depositor = store.getDepositor();
        this.businessHours = store.getBusinessHours();
        this.regularHolidays = store.getRegularHolidays();
        this.temporaryHolidays = store.getTemporaryHolidays();
        this.image = store.getImage();
        this.category = store.getCategory();
        this.createdDate = store.getCreatedDate();
        this.updatedDate = store.getUpdatedDate();
        this.viewCount = viewCount;
    }

    public StoreResponse(Store store, ObjectMapper objectMapper, int viewCount, double averageRating) {
        this(store, objectMapper, viewCount);
        this.averageRating = averageRating / 2.0;
    }

    public StoreResponse(Store store, ObjectMapper objectMapper, int viewCount, double averageRating, int favoriteCount) {
        this(store, objectMapper, viewCount, averageRating);
        this.favoriteCount = favoriteCount;
    }

    public StoreResponse(Store store, ObjectMapper objectMapper, int viewCount, double averageRating, int favoriteCount, long reservationCount) {
        this(store, objectMapper, viewCount, averageRating, favoriteCount);
        this.reservationCount = (int) reservationCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getReservationCount() {
        return reservationCount;
    }
}
