/* 다음 주소 API 활용
*
*
*
* 다음에서 제공하는 기능 활용
* new daum.Postcode(매개변수) 모든 기능은 저 위 주소에서 제공하는 기능
* 다른 회사에서 만든 api 주소로 기능을 활용할 때는
* 다른 회사에서 제공하는 Docs(기능사전)을 잘 이해해야함
 */

function daumPostCode(){
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if(data.userSelectedType === 'R') { // 사용자가 도로명 주소를 사용할 경우 Road
                addr = data.roadAddress;
            }else { // === 'J' Jibun 을 선택했을 경우 지번주소를 가져온다.
                addr = data.jibunAddress;
            }

            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('detailAddress').focus();
        }
    }).open();
}
document.querySelector("#searchAddress").addEventListener("click", daumPostCode);