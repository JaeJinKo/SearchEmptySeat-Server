package com.BubbleWrap.SearchEmptySeat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BubbleWrap.SearchEmptySeat.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStoreStorePKOrderByCreatedDateDesc(Long storePK);
}