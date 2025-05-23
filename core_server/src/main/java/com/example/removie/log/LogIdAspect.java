package com.example.removie.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * {@link LogGroup} 애너테이션이 적용된 메서드에 대해 고유한 Log ID를 설정하고 해제하는 AOP Aspect 클래스입니다.
 * <p>
 * - 메서드 실행 전: UUID 기반의 Log ID를 생성하여 MDC에 저장합니다.<br>
 * - 메서드 실행 후: MDC를 정리하여 메모리 누수 및 로그 오염을 방지합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Aspect
@Component
public class LogIdAspect {

    @Before("@annotation(com.example.removie.log.LogGroup)")
    public void setLogId(){
        String id = UUID.randomUUID().toString();
        MDC.put("LogId", id);
    }

    @After("@annotation(com.example.removie.log.LogGroup)")
    public void clearLogId() {
        MDC.clear();
    }
}
