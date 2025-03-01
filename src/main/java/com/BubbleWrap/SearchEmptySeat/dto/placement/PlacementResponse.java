package com.BubbleWrap.SearchEmptySeat.dto.placement;

import com.BubbleWrap.SearchEmptySeat.model.Placement;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class PlacementResponse {
    private Long placementPK;
    private Long storePK;
    private Map<String, Object> layout;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PlacementResponse(Placement p) {
        this.placementPK = p.getPlacementPK();
        this.storePK = p.getStorePK();
        this.layout = p.getLayout();
        this.createdDate = p.getCreatedDate();
        this.updatedDate = p.getUpdatedDate();
    }
}