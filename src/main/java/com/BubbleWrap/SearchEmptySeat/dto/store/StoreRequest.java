package com.BubbleWrap.SearchEmptySeat.dto.store;

import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StoreRequest {
    private String storeName;
    private String location;
    private String description;
    private String businessRegistrationNumber;
    private String bank;
    private String accountNumber;
    private String depositor;
    private Map<String, String> businessHours;
    private Map<String, Integer> regularHolidays; // 요일별 휴무일 (0=영업, 1=휴무)
    private List<String> temporaryHolidays; // 임시 휴무일 (날짜 리스트)
    private List<StoreCategory> category;
}
