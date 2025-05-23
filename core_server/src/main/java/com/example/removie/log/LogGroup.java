package com.example.removie.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 메서드 실행 시 로그 추적을 위한 고유 식별자를 MDC에 설정하는 용도의 애너테이션입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogGroup {
}
