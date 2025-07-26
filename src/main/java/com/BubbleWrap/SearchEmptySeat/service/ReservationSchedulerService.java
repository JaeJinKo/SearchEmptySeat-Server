package com.BubbleWrap.SearchEmptySeat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchedulerService.class);
    private final ReservationService reservationService;
    
    public ReservationSchedulerService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    // 매시간 정각에 실행 (예: 00:00, 01:00, 02:00...)
    @Scheduled(cron = "0 0 * * * *")
    public void completeExpiredReservations() {
        try {
            logger.info("만료된 예약 완료 처리 시작");
            reservationService.completeExpiredReservations();
            logger.info("만료된 예약 완료 처리 완료");
        } catch (Exception e) {
            logger.error("만료된 예약 완료 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }
} 