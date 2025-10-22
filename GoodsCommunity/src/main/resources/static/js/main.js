/* 쿠키에서 key 가 일치하는 value 얻어오기 함수 = 기능 */

// 쿠키는 K=V, K=V 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후
//                 결과값으로 새로운 배열을 만들어서 반환

/*
찾고 싶은 쿠키의 이름을 키 매개변수로 받음



*/
const getCookie = key => { // () 내부에 들어가는 매개변수가 하나의 값일 경우에는 () 생략 가능
    const cookies = document.cookie; // 브라우저에 저장된 모든 쿠키를 하나의 긴 문자열 형태로 가져와 cookies 변수에 저장
    console.log("cookies : ", cookies);

    // el 명칭으로 cookies 에서 가져온 모든 데이터를 하나씩 꺼내 ; 을 기준으로 쪼개서 배열을 만듬
    // java javaScript 는 한국이 아니라 미국을 중점으로 만들었기 때문에
    // 코드 문법 또한 영어처럼 명사 + 동사
    const cookieList = cookies.split(";").map(el =>el.trim().split("="));
    console.log("cookieList : ", cookieList);

    // 위에서 하나씩 가져온 배열 형태를 key-value json 형태처럼 변환해서 사용
    // obj 를 {} json 틀처럼 생성
    const obj = {}; // 비어있는 객체로 선언

    // json 틀과 같은 obj 내부에 key-value 형태로 데이터 저장
    for(let i =0; i < cookieList.length; i++){
        const k = cookieList[i][0].trim(); // key 값
        console.log("k : ", k);
        const v = cookieList[i][1]; // value 값
        console.log("v : ", v);
        obj[k] = v; // 객체에 추가
    }
    
    return obj[key]; // 매개 변수로 전달 받은 key 와 obj 객체에 저장된 키가 일치하는 요소의 값 반환
    console.log("obj : ", obj);
}

// id 값이 loginForm 인 태그 내부에 input 에서 name 명칭이 memberEmail 인 태그의 값을
// loginEmail 이라는 변수이름에 담아두기
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

// 로그인이 안된 상태의 경우만 수행
if(loginEmail != null){
    const saveId = getCookie("saveId"); // undefined 또는 이메일이 올 것

    // saveId 값이 존재할 경우
    // 쿠키에서 saveId 라는 키 명칭에는 멤버이메일 데이터가 value 값으로 저장되어 있음
    if(saveId != undefined){
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input 에 value 값으로 세팅

        // 아이디 저장 체크박스에 체크 해두기
        document.querySelector("input[name='saveId']").checked = true;
    }
}

/* 이메일 비밀번호 미 작성 시 로그인 막기 */
const loginForm = document.querySelector("#loginForm")

const loginPw = document.querySelector("#loginForm input[name='memberPassword']")

if(loginForm != null){
    // 로그인 버튼 클릭해서 form 내부에 작성한 내용을 제출한다는 동작이 발생할 경우 button 기본값 submit
    loginForm.addEventListener("submit", e=>{

        // 이메일 미작성
        if(loginEmail.value.trim().length === 0){
            alert("이메일을 작성해주세요.");
            e.preventDefault(); // 기본 제출 막기
            loginEmail.focus(); // 작성 안된 곳으로 초점을 이동해서 작성하도록 유도
            return;
        }

        // 비밀번호 미작성
        if(loginPw.value.trim().length === 0){
            alert("비밀번호를 작성해주세요.");
            e.preventDefault(); // 기본 제출 막기
            loginEmail.focus(); // 작성 안된 곳으로 초점을 이동해서 작성하도록 유도
            return;
        }
    })
}