package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByOrderByReservationPKDesc();
    List<Reservation> findByStorePKOrderByReservationPKDesc(Long storePK);
    List<Reservation> findByUserIdOrderByReservationPKDesc(Long userId);
    long countByStorePK(Long storePK);
    List<Reservation> findByStorePKAndReservationTimeBetween(Long storePK, LocalDateTime start, LocalDateTime end);
    long countByStorePKAndReservationTimeAfter(Long storePK, LocalDateTime time);
    long countByStorePKAndReservationTimeBetween(Long storePK, LocalDateTime start, LocalDateTime end);
    List<Reservation> findByReservationTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
    List<Reservation> findByStorePKAndStatus(Long storePK, String status);
    List<Reservation> findByStatusAndReservationTimeBefore(String status, LocalDateTime time);
    
    // 특정 가게의 오늘 예약 중 가장 큰 예약 번호 조회
    @Query("SELECT MAX(r.reservationNum) FROM Reservation r WHERE r.storePK = :storePK AND r.createdDate >= :startOfDay AND r.createdDate < :endOfDay")
    Integer findMaxReservationNumByStorePKAndToday(@Param("storePK") Long storePK, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
