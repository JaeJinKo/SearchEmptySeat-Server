package com.BubbleWrap.SearchEmptySeat.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    USER,        // 일반 사용자
    OWNER,       // 점주
    SUPER_ADMIN;  // 관리자

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
