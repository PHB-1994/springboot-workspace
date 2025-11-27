package edu.thejoeun.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private int memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String memberAddress;
    private String memberPhone;
    private String memberProfileImage; // 프로필 이미지 경로 추가
    private String memberRole;
    private String memberCreatedAt;
    private String memberUpdatedAt;
}
