package edu.thejoeun.member.model.service;

import edu.thejoeun.member.model.dto.Member;

public interface MemberService {

    /**
     * 프로젝트에서 기능 명칭을 지정하는 공간
     */
    Member login(String memberEmail, String memberPassword);

}
