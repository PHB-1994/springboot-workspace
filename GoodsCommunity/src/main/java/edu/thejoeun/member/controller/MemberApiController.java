package edu.thejoeun.member.controller;

import edu.thejoeun.common.exception.ForbiddenExceptions;
import edu.thejoeun.common.util.FileUploadService;
import edu.thejoeun.common.util.SessionUtil;
import edu.thejoeun.member.model.dto.Member;
import edu.thejoeun.member.model.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberServiceImpl memberService;

    private final FileUploadService fileUploadService;

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody Map<String, String> loginData, HttpSession session){

        String memberEmail = loginData.get("memberEmail");
        String memberPassword = loginData.get("memberPassword");

        Map<String, Object> res = memberService.loginProcess(memberEmail, memberPassword, session);

        return res;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session){
        return memberService.logoutProcess(session);
    }

    /**
     * 로그인 상태 확인 API
     * React 앱이 시작될 때 호출
     * @param session
     * @return 로그인 상태 반한
     */
    @GetMapping("/check")
    public Map<String, Object> checkLoginStatus(HttpSession session){
        return memberService.checkLoginStatus(session);
    }

    // const res = axios.post("/api/auth/signup",signupData);
    // PostMapping 만들기
    // mapper.xml -> mapper.java -> service.java -> serviceImpl.java apiController.java

    /**
     * @RequestPart  : multipart/form-data 파일 + JSON 파트로 데이터 받을 때
     * @RequestParam : URL 쿼리 파라미터 / HTML Form 파라미터
     * @RequestBody  : 요청 전체를 객체로 받을 때
     *
     * required = false 는 @PathVariable @RequestPart @RequestParam @RequestBody
     * 모두에서 쓸 수 있는 속성으로 각 데이터가 필수로 존재하지 않아도 될 때 사용
     * 기본값은 true
     * @param member
     */
    @PostMapping("/signup")
    public void signup(@RequestPart Member member,
                       @RequestPart(required=false) MultipartFile profileImage) {
        log.info("=== 회원가입 요청===");
        log.info("요청 데이터 - 이름 : {}, 이메일{}",member.getMemberName(),member.getMemberEmail());

        try{
            memberService.saveMember(member, profileImage);
            log.info("회원가입 성공 - 이메일 : {}",member.getMemberEmail());
            /**
             * 브로드 캐스트를 통해서
             * 모든 사람들에게 ㅇㅇㅇ 님이 가입했습니다. 알림 설정
             */
        } catch (Exception e){
            log.error("회원가입 실패 - 이메일 : {}, 에러 : {}", member.getMemberEmail(), e.getMessage());
        }
    }

    /**
     * 상품 이미지 업로드를 프로필 사진 업로드처럼 product-images 폴더에 업데이트 되도록 설정
     * -> 이미지 업로드 한 데이터 가져오고 가져가는 서버 - 웹 페이지 작업]
     * -> updateProduct 로!
     * 과제...! .. Docs 로 공부 추천..!
     * fetchMypageEditWithProfile(axios, formData, profileFile, navigate, setIsSubmitting);
     * 받기 위한 매개변수 수정 일어날 것
     * @param updateData
     * @param session
     * @return
     */
    @PutMapping("/update")
    public Map<String, Object> updateMypage(@RequestBody Map<String, Object> updateData, HttpSession session)  {
        log.info("회원정보 수정 요청");

        try {
            Member m = new Member();
            m.setMemberPhone(updateData.get("memberPhone").toString());
            m.setMemberEmail(updateData.get("memberEmail").toString());
            m. setMemberName(updateData.get("memberName").toString());
            m. setMemberAddress(updateData.get("memberAddress").toString());

            // 새 비밀번호가 있는 경우
            String newPassword = (String) updateData.get("memberPassword");
            if(newPassword != null && !newPassword.isEmpty()) {
               m.setMemberPassword(newPassword);
            }

            // 현재 비밀번호
            String currentPassword = (String) updateData.get("currentPassword");
            Map<String, Object> res = memberService.updateMember(m, currentPassword, session);
            // 서비스에서 성골 실패에 대한 결과를 res 담고 프론트엔드 전달
            log.info("회원정보 수정 결과 : {}", res.get("message"));
            return res;

        } catch (Exception e) {
            log.error("서비스 접근했거나, 서비스 가기 전에 문제가 발생해서 회원정보 수정 실패 - 에러 : {}", e.getMessage());
            Map<String, Object> res = new HashMap<>();
            res.put("success", false);
            res.put("message", "회원정보 수정 중 오류가 발생했습니다.");
            return res;
        }

    }

    // 요청을 받아서 Service 로 넘기고 결과를 응답하는 역할만 함
    @PostMapping("/profile-image")
    public ResponseEntity<Map<String, Object>> uploadProfileImage(
            @RequestParam("file")MultipartFile file,
            @RequestParam("memberEmail")String memberEmail, HttpSession session) {

        Map<String, Object> res = new HashMap<>();

        try {
            Member loginUser = SessionUtil.getLoginUser(session);
            String imageUrl = memberService.updateProfileImage(loginUser, memberEmail, file, session);

            res.put("success", true);
            res.put("message", "프로필 이미지가 업데이트 되었습니다.");
            res.put("imageUrl", imageUrl);
            log.info("프로필 이미지 업로드 성공 - 이메일 : {}, 파일명 : {}", memberEmail, file.getOriginalFilename() );
            return ResponseEntity.ok(res); // 업데이트가 무사히 되면 200만 전달

        // 개발자가 만든 exception 은 최 상위 작성
        // 자바에서 기본으로 제공하는 exception 은
        // 최 상위가 아닌 순부터 작성
        // exception 의 부모인 exception 은 맨 마지막에 작성
        // 부모 exception 은 까지 올 때는
        // 어떤 문제인지 파악을 회사에서 못한 상태
        } catch(IllegalStateException e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.status(401).body(res);
            // ResponseEntity 401

        } catch(ForbiddenExceptions e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.status(403).body(res);

        } catch(IllegalArgumentException e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(res);

        } catch(Exception e) {
            log.error("프로필 이미지 업로드 실패 - 이메일 : {}, 오류 : {}", memberEmail, e.getMessage());
            res.put("success", false);
            res.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(res);
            // 500 error - 백엔드 에러
        }

    }
}

