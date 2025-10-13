let usersData = [];

window.onload = function () {
    loadUsers();
};

function loadUsers() {
    const content = document.getElementById("content");
    content.innerHTML = `
            <div class="loading">
                <div class="loading-spinner"></div>
                <p>사용자 정보를 불러오는 중...</p>
            </div>
        `;

    fetch("http://localhost:8080/api/users")
        .then((response) => {
            if (!response.ok) {
                throw new Error("서버 응답 오류: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            usersData = data;
            displayUsers(usersData);
        })
        .catch((error) => {
            console.error("Error:", error);
            content.innerHTML = `
                    <div class="error">
                        <p>${error.message}</p>
                        <p style="margin-top: 10px;">서버가 실행 중인지 확인해주세요.</p>
                    </div>
                `;
        });
}

function displayUsers(users) {
    const content = document.getElementById("content");

    if (!users || users.length === 0) {
        content.innerHTML = `
                <div class="empty">
                    <p class="empty-text">등록된 사용자가 없습니다.</p>
                </div>
            `;
        return;
    }

    let html = `
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>역할</th>
                    </tr>
                </thead>
                <tbody>
        `;

    users.forEach((user) => {
        const roleClass = user.role === 'ADMIN' ? 'admin' : 'user';
        const roleText = user.role === 'ADMIN' ? '관리자' : '사용자';

        html += `
                <tr>
                    <td class="user-id">#${user.id}</td>
                    <td class="user-name">${user.name}</td>
                    <td class="user-email">${user.email}</td>
                    <td><span class="user-role ${roleClass}">${roleText}</span></td>
                </tr>
            `;
    });

    html += `
                </tbody>
            </table>
        `;

    content.innerHTML = html;
}

function goToRegister() {
    window.location.href = '/user/add';
}