package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementRequest;
import com.BubbleWrap.SearchEmptySeat.dto.placement.PlacementResponse;
import com.BubbleWrap.SearchEmptySeat.model.Placement;
import com.BubbleWrap.SearchEmptySeat.repository.PlacementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlacementService {

    private final PlacementRepository placementRepository;

    public PlacementService(PlacementRepository placementRepository) {
        this.placementRepository = placementRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<PlacementResponse>> createPlacement(PlacementRequest request) {
        Placement p = new Placement();
        p.setStorePK(request.getStorePK());
        p.setLayout(request.getLayout());
        p.setCreatedDate(LocalDateTime.now());
        p.setUpdatedDate(LocalDateTime.now());

        placementRepository.save(p);
        return ResponseEntity.ok(
                ApiResponse.success(new PlacementResponse(p), "Placement created")
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<PlacementResponse>>> getAllPlacements() {
        List<Placement> list = placementRepository.findAll();
        List<PlacementResponse> response = list.stream()
                .map(PlacementResponse::new)
                .toList();
        return ResponseEntity.ok(
                ApiResponse.success(response, "All placements")
        );
    }
}
