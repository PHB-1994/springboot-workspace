package edu.thejoeun.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// Pointcut : 실제 advice 가 적용될 지점
// Pointcut 모아두는 클래스
public class PointcutBundle {

    // 매번 작성하기 어려운 Pointcut 을 미리 작성해서
    // 필요한 곳에서 클래스명.메서드명() 으로 호출해서 사용 가능
    // 소괄호 내부에는 "" 전체 감싸기 큰따옴표가 한 개만 크게 존재
    // 프로젝트에서 Controller 와 ServiceImpl 을 중점으로 로그 확인
    // 프로젝트에서는   패키지명칭만 변경 !!!
    @Pointcut("execution(* edu.thejoeun..*Controller*.*(..))")
    public void controllerPointCut(){}

    @Pointcut("execution(* edu.thejoeun..*ServiceImpl*.*(..))")
    public void serviceImplPointCut(){}
}
