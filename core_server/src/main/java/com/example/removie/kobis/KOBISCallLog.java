package com.example.removie.kobis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 해당 클래스는 KOBIS에 대한 모든 요청을 기록합니다.
 * 이는 지속적인 모니터링을 통해 불필요한 호출을 차단하여 데이터를 자유롭게
 * 이용하게 허락해 준 KOBIS에 혹시 모를 피해를 차단하기 위함입니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Aspect
@Component
public class KOBISCallLog {

}


