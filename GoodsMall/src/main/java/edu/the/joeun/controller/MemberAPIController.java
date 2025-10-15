package edu.the.joeun.controller;

import edu.the.joeun.model.Member;
import edu.the.joeun.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberAPIController {

    @Autowired
    private MemberService memberService;
    // postMapping 데이터 저장 시작


    /**
     *     fetch('/api/member/add', {                 <br/>
     *         method: 'POST',                        <br/>
     *         headers: {                             <br/>
     *             'Content-Type': 'application/json',<br/>
     *         },<br/><br/>
     *         body: JSON.stringify(memberData),      <br/>
     *               json 형태의 데이터를 문자열로 변환해서 자바로 전송! <br/>
     *     })                                         <br/>
     * 
     * 클라이언트가 /api/member/add 로 데이터 저장요청을 하러 <br/>
     * 위 주소로 들어오면 <br/>
     *
     * js 로 들고 온 문자열 형태의 키-값 데이터를 <br/>
     * 자바 모델에 걸맞는 클래스 변수명칭에 <br/>
     * 각 키-데이터를 객체 형태로 대입 <br/>
     * 
     * @param member js 에서 가져온 문자열 데이터를
     * @RequestBody 이용하여 Member 에 걸맞는 객체 형태로 변환해서 값 대입해놓기
     * 
     * 저장된 데이터를 기반으로 service 기능을 시작 후
     * service 기능에 대한 결과 유무를 클라이언트한테 전달
     */
    @PostMapping("/api/member/add")
    public void saveMember(@RequestBody Member member){
        memberService.saveMember(member);
    }

}
