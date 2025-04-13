package com.BubbleWrap.SearchEmptySeat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndStore(Member user, Store store);
    List<Favorite> findByUser(Member user);
    
    int countByStoreStorePK(Long storePK);
}
