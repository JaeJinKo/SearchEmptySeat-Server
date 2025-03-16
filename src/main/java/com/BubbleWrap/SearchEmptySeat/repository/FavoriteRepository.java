package com.BubbleWrap.SearchEmptySeat.repository;

import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndStore(Member user, Store store);
    List<Favorite> findByUser(Member user);
}
