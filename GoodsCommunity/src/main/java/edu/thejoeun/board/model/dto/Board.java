package edu.thejoeun.board.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity = JPA 데이터베이스 자체를 자바에서부터 생성해서 DB 컬럼 관리
// Builder
@Data               // Getter Setter ToString 과 같은 기능 어노테이션을 모아놓은 어노테이션
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    // JPA 로 상태 관리할 때 기본키라는 설정
    // @Id
    // GeneratedValue
    private int id;
    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String createdAt; // DB 명칭 created_at
    private String updatedAt; // DB 명칭 updated_at

    // 인기글 전용 필드(일반 게시글 조회 시에는 null)
    // private int ranking;            // 인기글 순위
    private Integer ranking;           // 인기글 순위
    private String popularUpdateAt;    // 인기글 업데이트 시간
}
