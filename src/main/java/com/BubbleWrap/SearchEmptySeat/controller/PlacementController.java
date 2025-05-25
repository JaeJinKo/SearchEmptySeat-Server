package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementRequest;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementResponse;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.service.PlacementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placement")
public class PlacementController {

    private final PlacementService placementService;

    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlacementResponse>> createPlacement(@RequestBody PlacementRequest request) {
        return placementService.createPlacement(request);
    }

    @GetMapping("/store/{storePK}")
    public ResponseEntity<ApiResponse<PlacementResponse>> getPlacementByStore(@PathVariable Long storePK) {
        return placementService.getPlacementByStore(storePK);
    }

    @PutMapping("/{placementPK}")
    public ResponseEntity<ApiResponse<PlacementResponse>> updatePlacement(
            @PathVariable Long placementPK,
            @RequestBody PlacementUpdateRequest request) {
        return placementService.updatePlacement(placementPK, request);
    }
}
