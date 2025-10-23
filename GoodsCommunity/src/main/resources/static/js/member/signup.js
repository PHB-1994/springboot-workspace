/* 다음 주소 API 활용
* <!-- 주소 검색 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

* 다음에서 제공하는 기능 활용
* new daum.Postcode(매개변수) 모든 기능은  저 위 주소에서 제공하는 기능
* 다른 회사에서 만든 api 주소로 기능을 활용할 때는
* 다른 회사에서 제공하는 Docs(기능사전)을 잘 이해해야함
*  */
function daumPostCode(){
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if( data.userSelectedType === 'R') { //사용자가 도로명 주소를 사용할 경우 Road
                addr = data.roadAddress;
            } else { // === 'J' Jibun 을 선택했을 경우 지번주소를 가져온다.
                addr = data.jibunAddress;
            }

            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('address').value = addr;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
document.querySelector("#searchAddress").addEventListener("click",daumPostCode);


//   <button id="sendAuthKeyBtn" type="button">인증번호 받기</button>

const memberEmail = document.querySelector("#memberEmail");

const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
//인증번호 입력 input 창
const authKey = document.querySelector("#authKey");
// 인증번호 입력 후 확인하는 버튼
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");
// 인증번호 관련 메세지 출력 span
const authKeyMessage = document.querySelector("#authKeyMessage");
let authTimer; // 타이머 역할을 할  setInterval 을 저장할 변수
const initMin = 4; // 타이머 초기값(분) -> 시간이 지날 수록 분의 값이 변할 것
const initSec = 59;// 타이머 초기값(초) -> 시간이 지날 수록 초의 값이 변할 것
const initTime = "05:00"; //처음 시작하는 타이머는 그대로 문자열 유지
let min = initMin; // 실제 줄어드는 시간 분값을 저장할 변수
let sec = initSec; // 실제 줄어드는 시간 초값을 저장할 변수
const checkObj ={ // 내부에 있는게 모두  true 가 되어야지 나중에 회원가입 완료가 될 것
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberPhoneTel" : false,
    "authKey" : false,
}
// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {
    checkObj.authKey = false; //초반에는 인증번호 받기를 클릭을 할 때마다 인증이 완료될 때 까지 가입 못하게 방지
    document.querySelector("#authKeyMessage").innerText = '';
    // 클릭시 타이머 숫자 초기화
    min = initMin;
    // 시간줄어드는 동작 초기화 작업
    clearInterval(authTimer);
    checkObj.authKey = false;
    // fetch -> async await 로 변경하기
    fetch("/email/signup", {
        method :"POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value // const memberEmail = document.querySelector("#memberEmail");생략상태는 회색처리되어있음
    })
        .then(response => response.text())
        .then(result => {
            if(result == 1) {
                console.log("인증 번호 발송 성공")
            } else {
                console.log("인증 번호 발송 실패")
            }
        })
    authKeyMessage.innerText = initTime; // 5:00 세팅
    authKeyMessage.classList.remove("confirm","error"); //클래스 추가 삭제

    alert("인증번호가 발송되었습니다.");

    authTimer = setInterval(() => {
        authKeyMessage.innerText = `${zeroPlus(min)}:${zeroPlus(sec)}`;

        if(min == 0 && sec == 0 ){
            checkObj.authKey = false; //0분 0초이기 때문에 인증 못함
            clearInterval(authTimer); //시간초멈추기 -로 숫자가 흐를 것
            authKeyMessage.classList.add("error");
            authKeyMessage.classList.remove("confirm");
            return; // 타이머 종료 되고 나서는 빨간 글씨로 초가 끝남을 인지
        }

        // 분은 0분 아니지만 초는 0초인 경우
        // 3:00 초일 때 다시 60초부터 -1 씩 분을 먼저 줄이기
        if(sec == 0) {
            sec = 60;
            min--;
        }
        sec--; // 1초씩 감소
    }, 1000); //1초씩 감소한다
});

// 전달 받은 숫자가 10 미만인 경우(한자리) 앞에 0 붙여서 반환
function  zeroPlus(number){
    if(number < 10) return "0" + number;
    else            return number;
}


// 인증하기 버튼 클릭 시
//입력된 인증번호를 비동기로 서버에 전달
// 인증번호와 발급된 인증번호가 같은지 비교
// 아니면 0 같으면 1 반환
// 단, 타이머가 00:00 초가 아닐 경우만 수행
checkAuthKeyBtn.addEventListener("click", () => {
    if(min ===0 && sec === 0) { // 타이머가 00:00 인 경우
        alert("인증번호 입력 제한시간을 초과하였습니다.");
        return;
    }

    if(authKey.value.length < 6) { // 인증번호가 6자리 미만인 경우
        alert("인증번호를 정확히 입력해주세요.");
        return;
    }
    const obj = {
        "email" : memberEmail.value,
        "authKey": authKey.value
    }
    fetch("/email/checkAuthKey", {
        method:"POST",
        headers:{"Content-Type" : "application/json"},
        body:JSON.stringify(obj) //obj를 json 변경
    }) .then(resp => resp.text())
        .then(result => {
            if (result == 0 ){
                alert("인증번호가 일치하지 않습니다.");
                checkObj.authKey = false;
                return;
            }
            clearInterval(authTimer); // 정답!!! 타이머 중지!!!
            authKeyMessage.innerText = "인증되었습니다.";
            authKeyMessage.classList.remove("error");
            authKeyMessage.classList.add("confirm");
            checkObj.authKey = true; //인증번호 검사했을 때 통과!!!
        })
})