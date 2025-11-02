package com.BubbleWrap.SearchEmptySeat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BubbleWrap.SearchEmptySeat.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStoreStorePKOrderByCreatedDateDesc(Long storePK);
    
    // 가게별 총 리뷰 수
    Long countByStoreStorePK(Long storePK);
    
    // 가게별 평점별 리뷰 수
    Long countByStoreStorePKAndRating(Long storePK, Integer rating);
    
    // 가게별 평균 평점 계산
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.store.storePK = :storePK")
    Double findAverageRatingByStorePK(@Param("storePK") Long storePK);
}