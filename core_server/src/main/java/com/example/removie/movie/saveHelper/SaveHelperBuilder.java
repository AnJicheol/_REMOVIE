package com.example.removie.movie.saveHelper;

import com.example.removie.movie.jpaManager.UpdateJpaManager;



/**
 * {@link SaveHelper} 인터페이스의 구현체로, 주어진 대상 객체를 {@link UpdateJpaManager}를 통해 저장 또는 업데이트하는 역할을 수행합니다.
 * <p>
 * 이 클래스는 제네릭 타입 {@code T}에 대해 저장 로직을 위임하며,
 * {@code SaveHelper} 인터페이스를 간단하게 구현할 수 있는 빌더 형태를 제공합니다.
 * </p>
 *
 * <p>
 * 정적 팩토리 메서드 {@link #of(UpdateJpaManager, Object)}를 통해 인스턴스를 생성하며,
 * 별도의 구현 없이 재사용 가능한 저장 전략을 제공합니다.
 * </p>
 *
 * @param <T> 저장 대상 객체의 타입
 *
 * @author An_Jicheol
 * @version 2.0
 */
public class SaveHelperBuilder<T> implements SaveHelper{
    private final UpdateJpaManager<T> updateJpaManager;
    private final T target;

    public SaveHelperBuilder(UpdateJpaManager<T> updateJpaManager, T target) {
        this.updateJpaManager = updateJpaManager;
        this.target = target;
    }

    public static <T> SaveHelper of(UpdateJpaManager<T> manager, T target) {
        return new SaveHelperBuilder<>(manager, target);
    }

    @Override
    public void save() {
        updateJpaManager.update(target);
    }

}
