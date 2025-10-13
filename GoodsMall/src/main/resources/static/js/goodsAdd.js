function submitGoods(event) {
    event.preventDefault();

    const name = document.getElementById('name').value.trim();
    const category = document.querySelector('input[name="category"]:checked').value;
    const price = parseInt(document.getElementById('price').value);
    const stock = parseInt(document.getElementById('stock').value);

    if (!name) {
        showAlert('error', '상품명을 입력해주세요.');
        return;
    }
    if (!category) {
        showAlert('error', '카테고리를 선택해주세요.');
        return;
    }
    if (price < 0) {
        showAlert('error', '가격은 0 이상이어야 합니다.');
        return;
    }

    if (stock < 0) {
        showAlert('error', '재고는 0 이상이어야 합니다.');
        return;
    }

    const goodsData = {
        name: name,
        price: price,
        stock: stock,
        category : category
    };

    const submitBtn = event.target.querySelector('.btn-submit');
    submitBtn.disabled = true;
    submitBtn.textContent = '등록 중...';

    fetch('http://localhost:8080/api/goods', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(goodsData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류: ' + response.status);
            }
            return response.text();
        })
        .then(data => {
            showAlert('success', '상품이 성공적으로 등록되었습니다!');
            document.getElementById('goodsForm').reset();

            setTimeout(() => {
                if (confirm('상품 목록을 확인하시겠습니까?')) {
                    goToList();
                }
            }, 1500);
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('error', '상품 등록에 실패했습니다: ' + error.message);
        })
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = '등록하기';
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

function goToList() {
    window.location.href = '/goodList';
}

document.getElementById('goodsForm').addEventListener('reset', function(e) {
    if (!confirm('입력한 내용을 모두 지우시겠습니까?')) {
        e.preventDefault();
    }
});

document.querySelectorAll('input[type="number"]').forEach(input => {
    input.addEventListener('keypress', function(e) {
        if (e.key === '-' || e.key === '+' || e.key === 'e') {
            e.preventDefault();
        }
    });
});