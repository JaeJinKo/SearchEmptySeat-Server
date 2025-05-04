package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.favorite.FavoriteResponse;
import com.BubbleWrap.SearchEmptySeat.dto.store.StoreResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Favorite;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.model.Store;
import com.BubbleWrap.SearchEmptySeat.repository.FavoriteRepository;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;


    @Transactional
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 회원 타입 검증 (USER만 가능)
        if (!user.getUserType().name().equals("USER")) {
            throw new BusinessException(ErrorCode.ONLY_USER);
        }

        // 가게 존재 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 이미 찜한 가게인지 확인
        if (favoriteRepository.findByUserAndStore(user, store).isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_FAVORITED);
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setStore(store);
        favoriteRepository.save(favorite);

        return ResponseEntity.ok(ApiResponse.success(new FavoriteResponse(favorite), "Added to favorites"));
    }

    /**
     * 가게 찜 삭제
     */
    @Transactional
    public ResponseEntity<ApiResponse<String>> removeFavorite(Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Favorite favorite = favoriteRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new BusinessException(ErrorCode.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favorite);

        return ResponseEntity.ok(ApiResponse.success("Removed from favorites", "Favorite removed"));
    }

    /**
     * 찜 목록 조회
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getFavoriteList(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<Favorite> favorites = favoriteRepository.findByUser(user);

        List<FavoriteResponse> response = favorites.stream()
                .map(favorite -> {
                    Store store = favorite.getStore();
                    StoreResponse storeResponse = new StoreResponse(store);
                    return new FavoriteResponse(favorite, storeResponse);
                })
                .toList();

        return ResponseEntity.ok(ApiResponse.success(response, "Favorite list retrieved successfully"));
    }
}
