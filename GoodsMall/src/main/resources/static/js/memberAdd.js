function submitMember(event) {
    event.preventDefault();

    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const role = document.querySelector('input[name="role"]:checked').value;

    if (!name || !email || !password || !role) {
        showAlert('error', '이름, 이메일, 비밀번호, 회원 권한을 모두 입력해주세요.');
        return;
    }

    const memberData = {
        name: name,
        email: email,
        password: password,
        role: role,
    };

    const submitBtn = event.target.querySelector('.btn-submit');
    submitBtn.disabled = true;
    submitBtn.textContent = '등록 중...';

    fetch('/api/member/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(memberData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류: ' + response.status);
            }
            return response.text();
        })
        .then(data => {
            showAlert('success', '회원이 성공적으로 등록되었습니다!');
            document.getElementById('memberForm').reset();

            setTimeout(() => {
                if (confirm('회원 목록을 확인하시겠습니까?')) {
                    goToMemberList();
                }
            }, 1500);
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('error', '회원 등록에 실패했습니다: ' + error.message);
        })
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = '가입하기';
        });
}

function showAlert(type, message) {
    const alertBox = document.getElementById('alertBox');
    const alertClass = type === 'success' ? 'alert-success' : 'alert-error';
    const icon = type === 'success' ? '✅' : '❌';

    alertBox.innerHTML = `
        <div class="alert ${alertClass}">
            <span class="alert-icon">${icon}</span>
            ${message}
        </div>
    `;

    setTimeout(() => {
        alertBox.innerHTML = '';
    }, 5000);

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function goToMemberList() {
    window.location.href = '/member/list';
}