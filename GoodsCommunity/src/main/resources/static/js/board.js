
const API_BASE_URL = "http://localhost:8080/api"
async function fetchBoardData() {
    const loading = document.getElementById("loading");
    const error = document.getElementById("error");
    const table = document.getElementById("boardTable");
    const tbody = document.getElementById("boardBody");

    loading.style.display = "block";
    error.style.display = "none";
    table.style.display = "none";
    tbody.innerText = "";

    const res = await fetch(API_BASE_URL + "/board/all")

    // ok = 200. 200이 아닌게 맞을 때
    if(!res.ok){
        throw new Error("서버 응답 오류 : " + res.status);
    }

    const board = await res.json();

    if(board.length === 0){
        tbody.innerText = `<tr><td colspan="5">게시글이 없습니다.</td></tr>`;
    } else {
        board.forEach(
            b => {
                const row = document.createElement("tr")

                row.innerHTML = `
                <td class="title-cell">${b.id}</td>
                <td>${b.title}</td>
                <td>${b.writer}</td>
                <td>${b.viewCount}</td>
                <td>${b.createdAt}</td>
                <td onclick="openModal(${b.id})">미리보기</td>
                <td onclick="gotoDetail(${b.id})">상세보기</td>
                `;
                tbody.appendChild(row);
            }
        );
    }

    table.style.display = "table";

}

// 게시글 상세보기를 호출하는 기능
async function detailFunction(id) {

    const res = await fetch(API_BASE_URL + `/board/${id}`);

    if(!res.ok){
        throw new Error("게시글을 불러오는데 실패했습니다.")
    }

    return await res.json(); // detailFunction 을 이용하면 백엔드에서 호출한 데이터가 반환
}

// 상세보기 페이지에서 실행할 기능
async function fetchBoardDetail(id){

    const urlParams = new URLSearchParams(window.location.search);
    const boardId = urlParams.get("id");

    // 클라이언트가 DB 에 존재하지 않는 id 를 작성할 때 게시물 메인으로 돌려보내기
    if(!boardId) {
        alert("잘못된 게시글 번호입니다.")
        window.location.href = "/board";
    }

    const b = await detailFunction(boardId);
    console.log("DB 데이터 조회 : ", b);

    const title = document.querySelector(".board-title");
    const writer = document.querySelector(".board-writer");
    const date = document.querySelector(".board-date");
    const views = document.querySelector(".board-views");
    const content = document.querySelector(".board-content");

    title.textContent = b.title;
    writer.textContent = b.writer;
    date.textContent = b.createdAt;
    views.textContent = b.viewCount;
    content.textContent = b.content;
}


// 모달 열기 기능
/**
 * 모달에서 게시글 상세보기
 * @param id id - 게시글 아이디
 */
async function openModal(id) {
    const modal = document.getElementById("viewModal");
    const modalTitle = document.getElementById("modalTitle");
    const modalInfo = document.getElementById("modalInfo");
    const modalContent = document.getElementById("modalContent");

    modal.style.display = "flex";

    // const res = await fetch(API_BASE_URL + `/board/${id}`);

    const board = await detailFunction(id);

    modalTitle.textContent = board.title;
    modalInfo.textContent = `작성자 ${board.writer}`;
    modalContent.textContent = board.content;

}

function closeModal() {
    const modal = document.getElementById("viewModal");
    modal.style.display = "none";
}

/**
 * 페이지를 구분해서 함수 실행
 */

// 페이지 로드 시 자동으로 특정 기능 실행
window.addEventListener("DOMContentLoaded", () => {
    if(document.getElementById("boardTable")){
        fetchBoardData();
    }
    if(document.querySelector(".board-detail-container")){
        fetchBoardDetail();
    }
});

const listBtn = document.querySelector(".btn-list");
listBtn.addEventListener("click", gotoList);

// 상세 페이지 이동 버튼 기능
function gotoDetail(id){
    window.location.href = `/board/detail?id=${id}`;
}

// 목록으로 돌아가기 버튼 기능
function gotoList(){
    window.location.href = `/board`;
}