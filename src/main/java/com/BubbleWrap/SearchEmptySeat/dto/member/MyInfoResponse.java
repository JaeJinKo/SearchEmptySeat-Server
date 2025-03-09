package com.BubbleWrap.SearchEmptySeat.dto.member;

import com.BubbleWrap.SearchEmptySeat.model.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyInfoResponse {
    private Long userId;
    private String email;
    private String name;
    private String phone;
    private String location;
    private List<String> image;
    private int points;
    private String userType;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public MyInfoResponse(Member member) {
        this.userId = member.getUserId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.location = member.getLocation();
        this.image = member.getImage();
        this.points = member.getPoints();
        this.userType = member.getUserType().name();
        this.createdDate = member.getCreatedDate();
        this.updatedDate = member.getUpdatedDate();
    }
}
