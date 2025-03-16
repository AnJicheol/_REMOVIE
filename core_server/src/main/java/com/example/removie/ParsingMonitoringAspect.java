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
