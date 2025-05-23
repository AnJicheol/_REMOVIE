package com.example.removie.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파싱 과정을 모니터링하기 위한 어노테이션입니다.
 * AOP를 통해 {@link DocConnect} 사용 위치를 추적합니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KOBISCall {

}