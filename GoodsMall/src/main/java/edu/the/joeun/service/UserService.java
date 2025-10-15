package edu.the.joeun.service;

import edu.the.joeun.common.util.SessionUtil;
import edu.the.joeun.mapper.UsersMapper;


import edu.the.joeun.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersMapper userMapper;

    // 다른 개발자가 만든 비밀번호 암호화 생성할 수 있는 객체
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }

    public void insertUser(User user){

        String 유저작성패스워드 = user.getPassword();
        String 암호화처리패스워드 = bCryptPasswordEncoder.encode(유저작성패스워드);
        
        user.setPassword(암호화처리패스워드); // 암호화 처리된 패스워드로 교체하여 user DB 저장하도록 설정

        userMapper.insertUser(user);

        /**
         * 비밀번호 암호화 같은 복합 작업 진행하는 공간
         * 프로필 사진을 폴더에 저장하고 폴더 경로도 DB 에 저장 가능
         */
        // user.setRole("USER");
        // html 에서 클라이언트가 작성설정을 하는 것이 아니라
        // 개발자의 회사에서 클라이언트 정보를 강제적으로
        // 기본값 설정해줄 때 추가 로직을 작성할 수 있음
    }

    /**
     *
     * @param email     유저가 입력한 이메일
     * @param password  유저가 입력한 비밀번호
     * @param session   로그인 상태를 유지하기 위한 Session
     *                  로그인 성공 시 세션에 사용자 정보를 저장하여, 이후 요청에서
     *                  세션을 통해 사용자 정보를 가져옴
     * @param model     org.springframework.ui.Model
     *                  Spring MVC 의 Model 객체로, 뷰 데이터를 전달하는데 사용
     *                  로그인 실패 시 오류 메세지를 뷰에 전달하는데 활용
     *
     * @return          로그인 처리 후 반환되는 api 뷰 이름.
     *                  로그인 성공 시 메  인 페이지로 리다이렉트 하거나
     *                  로그인 실패 시 로그인 페이지로 돌아갈 수 있다.
     */
    public String processLogin(String email, String password, HttpSession session, Model model){

        // 1. 이메일로 사용자 조회. 조회된 데이터를 user 내부에 저장
        User user = userMapper.getUserByEmail(email);

        // 2. 사용자가 존재하지 않는 경우
        if(user == null){
            model.addAttribute("error","존재하지 않는 이메일입니다.");
            return "/login";
        }

        // 3. 비밀번호 검증
        boolean passwordMatch =  bCryptPasswordEncoder.matches(password, user.getPassword());

        if(!passwordMatch){
            model.addAttribute("error","비밀번호가 일치하지 않습니다.");
            return "/login";
        }

        // 4. 로그인 성공 : 세션 저장(비밀번호는 null 형태로 설정)
        user.setPassword(null);
        SessionUtil.setLoginUser(session, user);
        return "redirect:/";
    }
}
