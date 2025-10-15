package edu.the.joeun.mapper;



import edu.the.joeun.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {

    List<User> getAllUser();

    /*
    insert 의 경우 void 와 int 둘 다 가능

        int - return
            여러 개의 데이터를 한 번에 저장할 때 몇 개의 데이터가 저장되었고,
            몇 개의 데이터가 저장되지 않았는지 클라이언트한테 전달하고자 할 때 사용

        void - 저장결과 유무 확인할 수 있다.
            단 일 데이터를 저장하고, 데이터가 몇 개 저장되었는지
            클라이언트한테 전달하지 않을 때 주로 사용 (단순 저장)

        select, update, delete 위 insert 와 비슷하게
        상황에 따라 int 를 사용하기도 하고 void 를 사용한다.

        몇 개의 데이터를 조회(검색) 했는지 확인하고자 할 때 int 를 주로 사용
        몇 개의 데이터를 수정, 삭제했는지 확인하고자 할 때 int 를 주로 사용
        단순 조회, 수정, 삭제의 경우 void 나 User 와 같은 자료혀을 활용하기도 함
        개발자가 원하는 결과 상황에 따라 자료형은 void, int User 와 같은 자료형을 사용
     */
    // 단순 저장 확인용 void(= 반환데이터 없음) 선택
    // AI 나 인터넷에서 아래와 같은 방식을 추천할 경우
    // 쿼리가 동작하긴 하지만
    // 유지보수가 어려워 사용을 지양하는 형태
    // INSERT INTO user(name, email, role) VALUES(${name}, ${email}, ${role})
    void insertUser(User user);

    /**
     * 이메일로 사용자 조회 (로그인용)
     * 특정 유저 1명만 조회하는 것이기 때문에
     * User 자료형 사용
     *
     * @param email html -> js -> controller -> service 에서 가져온 email 값 매개변수로 받아옴
     * @return email 을 활용한 유저 정보를 service 에 전달. email 존재 유무에 따라 전달 여부 설정
     */
    User getUserByEmail(String email);
}
