package com.example.removie;

import com.example.removie.document.DocConnection;
import jakarta.annotation.PreDestroy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



/**
 * KOBIS 문서 파싱 과정의 호출 경로 및 URL 요청 횟수를 추적하는 AOP 컴포넌트입니다.
 * <p>
 * {@link com.example.removie.document.KOBISCall} 애너테이션이 붙은 메서드를 기준으로
 * 호출자 클래스명을 추적하고, 이후 {@code KOBISDocConnect.responseDoc(..)} 메서드에서 사용된
 * URL 요청 정보를 수집하여 호출 횟수를 기록합니다.
 * </p>
 *
 * <p>
 * 추적 정보는 {@code UpdateServiceImpl.runUpdate*()} 메서드 실행 종료 시점에 로그로 출력되며,
 * URL별 호출 횟수 및 호출한 클래스명을 함께 제공합니다.
 * </p>
 *
 * <p>
 * @@@ 해당 프로세스는 단일 스레드에서 작동합니다 @@@
 *
 * 단 내부적으로는 {@code ThreadLocal<Stack<String>>}을 통해 호출 스택을 관리하고,
 * {@code ConcurrentMap}을 통해 멀티스레드 환경에서도 안전하게 URL 호출 기록을 집계합니다.
 * </p>
 *
 * <p>
 * 애플리케이션 종료 시점에는 {@link PreDestroy}를 통해 스레드 로컬 및 맵 자원을 정리합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Aspect
@Component
public class ParsingMonitoringAspect {
    private final static Logger logger = LoggerFactory.getLogger(ParsingMonitoringAspect.class);

    private final ThreadLocal<Stack<String>> methodCallStack = ThreadLocal.withInitial(Stack::new);
    private final ConcurrentMap<String, ConcurrentHashMap<String, Integer>> trackingMap = new ConcurrentHashMap<>();

    @Around("@annotation(com.example.removie.document.KOBISCall)")
    public Object logParsingCalls(ProceedingJoinPoint joinPoint) throws Throwable {

        String callerClassName = joinPoint.getTarget().getClass().getSimpleName();
        methodCallStack.get().push(callerClassName);

        return joinPoint.proceed();
    }

    @Around("execution(* com.example.removie.document.kobis.KOBISDocConnect.responseDoc(..))")
    public Object logDocCalls(ProceedingJoinPoint joinPoint) throws Throwable {

        String callerClassName = methodCallStack.get().isEmpty() ? "Unknown" : methodCallStack.get().pop();

        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof DocConnection docConnection) {
            String url = docConnection.getUrl();

            trackingMap.computeIfAbsent(callerClassName, k -> new ConcurrentHashMap<>())
                    .merge(url, 1, Integer::sum);
        }

        return joinPoint.proceed();
    }


    @AfterReturning("execution(* com.example.removie.update.UpdateServiceImpl.runUpdate*(..))")
    public void logParsingCallsAfterFacadeExecution() {

        for (String className : trackingMap.keySet()) {
            ConcurrentHashMap<String, Integer> calls = trackingMap.remove(className);

            if (calls != null && !calls.isEmpty()) {
                calls.forEach((url, count) -> logger.info(" {} - 요청 URL: {} ({}회 호출됨)", className, url, count));
            }
        }

        Stack<String> stack = methodCallStack.get();
        if (!stack.isEmpty()) {
            stack.clear();
        }
    }

    @PreDestroy
    public void cleanup() {
        // 시스템 종료 전 스택 정리
        methodCallStack.remove();
        trackingMap.clear();
    }
}
