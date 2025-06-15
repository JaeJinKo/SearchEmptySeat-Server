package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.MenuSection;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuSectionRepository extends JpaRepository<MenuSection, Long> {
    List<MenuSection> findByStoreOrderByPriorityAsc(Store store);
    List<MenuSection> findByStore(Store store);
    Optional<MenuSection> findByStoreAndName(Store store, String name);
} 