package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.Menu;

import com.BubbleWrap.SearchEmptySeat.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore(Store store);
}
