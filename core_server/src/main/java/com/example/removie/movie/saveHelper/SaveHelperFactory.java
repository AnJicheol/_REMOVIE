package com.example.removie.movie.saveHelper;


/**
 * {@link SaveHelper} 인스턴스를 생성하는 팩토리 인터페이스입니다.
 * <p>
 * 이 인터페이스의 구현체는 {@link SaveHelper}를 생성하는 데 필요한 의존성이나
 * 설정값을 주입하여, 완전히 구성된 {@code SaveHelper} 인스턴스를 반환하는 책임을 가집니다.
 * </p>
 *
 * 해당 인터페이스는 영화 데이터에 대한 확장성을 제공합니다.
 *
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface SaveHelperFactory {
    SaveHelper createSaveHelper();
}
