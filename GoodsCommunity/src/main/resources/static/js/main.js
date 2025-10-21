/* 쿠키에서 key 가 일치하는 value 얻어오기 함수 = 기능 */

// 쿠키는 K=V, K=V 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후
//                 결과값으로 새로운 배열을 만들어서 반환

const getCookie = key => {
    const cookies = document.cookie;

    const cookieList = cookies.split(";").map(el =>el.split("="));
}