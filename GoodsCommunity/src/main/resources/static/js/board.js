
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
                <td class="title-cell" onclick="openModal(${b.id})">${b.title}</td>
                <td>${b.writer}</td>
                `;
                tbody.appendChild(row);
            }
        );
    }

    table.style.display = "table";

}

// 페이지 로드 시 자동으로 특정 기능 실행
window.addEventListener("DOMContentLoaded", fetchBoardData);

