package com.example.removie.movie.saveHelper;



/**
 * 저장 또는 영속화 작업을 정의하는 인터페이스입니다.
 * <p>
 * 이 인터페이스의 구현체는 실제 저장 동작을 정의하며,
 * 일반적으로 내부 로직을 처리하는 JPA 매니저 위임합니다.
 * </p>
 *
 * <p>
 * 이 인터페이스는 특정 데이터 타입이나 저장 대상에 맞는 {@code SaveHelper} 인스턴스를
 * 팩토리 클래스에서 생성해 사용할 때 주로 활용됩니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface SaveHelper {
     void save();
}