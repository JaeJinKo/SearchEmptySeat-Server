package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStorePK(Long storePK);
    List<Reservation> findByUserId(Long userId);
    long countByStorePK(Long storePK);
    List<Reservation> findByStorePKAndReservationTimeBetween(Long storePK, LocalDateTime start, LocalDateTime end);
    long countByStorePKAndReservationTimeAfter(Long storePK, LocalDateTime time);
    long countByStorePKAndReservationTimeBetween(Long storePK, LocalDateTime start, LocalDateTime end);
    List<Reservation> findByReservationTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
    List<Reservation> findByStorePKAndStatus(Long storePK, String status);
}
