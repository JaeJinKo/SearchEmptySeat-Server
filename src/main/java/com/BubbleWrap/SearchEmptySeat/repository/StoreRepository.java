package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.model.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByOwnerUserId(Long ownerId);

    @Query("SELECT s FROM Store s JOIN s.category c WHERE c = :category")
    List<Store> findByCategory(@Param("category") StoreCategory category);

    @Query("SELECT s FROM Store s WHERE s.storeName LIKE %:storeName% ESCAPE '\\'")
    List<Store> findByStoreNameContaining(@Param("storeName") String storeName);
}
