package edu.thejoeun.board.model.dto;

/** Pagination : 목록을 일정 페이지로 분할해서
 *               원하는 페이지를 볼 수 있게 하는 것
 *               == 페이징 처리
 * Pagination 객체 : 페이징 처리에 필요한 값을 모아두고, 계산하는 객체
 *
 * 프론트엔드 페이지네이션
 *
 * 백엔드 페이지네이션
 * -> DB 데이터나, 이미지 데이터가 무수히 많아
 * 프론트엔드로 데이터를 전달할 때 시간 소모가 클 경우
 * 데이터를 나누어 프론트엔드로 전달하여, 클라이언트가 기다리는 로딩 시간을 축소
 * 21번 페이지를 클라이언트가 보고 있다.
 * 그 전 페이지 데이터나, 그 후 페이지 데이터를 나누어 프론트엔드한테
 * 40만 장 중에 30장을 부분 전달
 */
public class Pagination {

    private int currentPage;    // 현재 페이지 번호
    private int listCount;      // 전체 게시글 수

    private int limit = 10;     // 한 페이지 목록에 보여지는 게시글 수
    private int pageSize = 10;  // 보여질 페이지 번호 개수

    private int maxPage;        // 마지막 페이지 번호
    private int startPage;      // 보여지는 맨 앞 페이지 번호
    private int endPage;        // 보여지는 맨 뒤 페이지 번호
    private int prevPage;       // 이전 페이지 모음의 마지막 번호
    private int nextPage;       // 다음 페이지 모음의 마지막 번호

}
