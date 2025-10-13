
let goodsData = [];

window.onload = function () {
    loadGoods();
};

function loadGoods() {
    const content = document.getElementById("content");
    content.innerHTML = `
                <div class="loading">
                    <div class="loading-spinner"></div>
                    <p>상품 정보를 불러오는 중...</p>
                </div>
            `;

    fetch("http://localhost:8080/api/goods")
        .then((response) => {
            if (!response.ok) {
                throw new Error("서버 응답 오류: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            goodsData = data;
            displayGoods(goodsData);
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

function displayGoods(goods) {
    const content = document.getElementById("content");

    if (!goods || goods.length === 0) {
        content.innerHTML = `
                    <div class="empty">
                        <p class="empty-text">등록된 상품이 없습니다.</p>
                    </div>
                `;
        return;
    }

    let html = '<div class="goods-grid">';

    goods.forEach((item) => {

        const stockClass =
            item.stock === 0 ? "out" : item.stock < 10 ? "low" : "";
        const stockText = item.stock === 0 ? "품절" : `재고: ${item.stock}개`;

        html += `
                    <div class="goods-card">

                        <span class="goods-id">ID: ${item.id}</span>
                        <span class="goods-category">${item.category}</span>
                        <div class="goods-name">${item.name}</div>
                        <div class="goods-info">
                            <span class="goods-price">${item.price.toLocaleString()}원</span>
                            <span class="goods-stock ${stockClass}">${stockText}</span>
                        </div>
                    </div>
                `;
    });

    html += "</div>";
    content.innerHTML = html;
}