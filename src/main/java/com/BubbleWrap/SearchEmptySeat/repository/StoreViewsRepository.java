package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.StoreViews;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StoreViewsRepository extends JpaRepository<StoreViews, Long> {
    Optional<StoreViews> findByStoreStorePK(Long storePK);
}
