package com.BubbleWrap.SearchEmptySeat.dto.placement;

import com.BubbleWrap.SearchEmptySeat.model.Placement;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Getter
public class PlacementResponse {
    private Long placementPK;
    private Long storePK;
    private Map<String, TableLayoutData> layout;
    private int layoutSize;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PlacementResponse(Placement p) {
        this.placementPK = p.getPlacementPK();
        this.storePK = p.getStorePK();
        this.layoutSize = p.getLayoutSize();
        this.createdDate = p.getCreatedDate();
        this.updatedDate = p.getUpdatedDate();
        
        // Map<String, Object>를 Map<String, TableLayoutData>로 변환
        this.layout = convertMapToTableLayoutData(p.getLayout());
    }
    
    private Map<String, TableLayoutData> convertMapToTableLayoutData(Map<String, Object> layoutMap) {
        if (layoutMap == null) {
            return new HashMap<>();
        }
        
        Map<String, TableLayoutData> tables = new HashMap<>();
        for (Map.Entry<String, Object> entry : layoutMap.entrySet()) {
            String tableNumber = entry.getKey();
            Object tableData = entry.getValue();
            
            if (tableData instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> tableMap = (Map<String, Object>) tableData;
                
                TableLayoutData tableLayoutData = new TableLayoutData(
                    (Integer) tableMap.get("x"),
                    (Integer) tableMap.get("y"),
                    (Integer) tableMap.get("table"),
                    (Integer) tableMap.get("min"),
                    (Integer) tableMap.get("status")
                );
                tables.put(tableNumber, tableLayoutData);
            }
        }
        
        return tables;
    }
}