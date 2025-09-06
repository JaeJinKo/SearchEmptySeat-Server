package com.BubbleWrap.SearchEmptySeat.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.MapPinResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.MapPinDetailResponse;
import com.BubbleWrap.SearchEmptySeat.service.MapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);
    private final MapService mapService;

    @GetMapping("/api/map/pins")
    public ResponseEntity<ApiResponse<List<MapPinResponse>>> getMapPins() {
        logger.info("MapController.getMapPins() called");
        return mapService.getMapPins();
    }

    @GetMapping("/api/map/pin/{storePK}")
    public ResponseEntity<ApiResponse<MapPinDetailResponse>> getMapPinDetail(@PathVariable Long storePK) {
        logger.info("MapController.getMapPinDetail() called with storePK: {}", storePK);
        return mapService.getMapPinDetail(storePK);
    }
}
