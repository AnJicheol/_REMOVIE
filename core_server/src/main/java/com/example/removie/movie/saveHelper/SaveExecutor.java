package com.example.removie.movie.saveHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 여러 {@link SaveHelperFactory}로부터 생성된 {@link SaveHelper} 인스턴스를 일괄 실행하여
 * 저장 작업을 수행하는 컴포넌트입니다.
 * <p>
 * 이 클래스는 애플리케이션 내에 정의된 모든 {@code SaveHelperFactory}를 주입받아,
 * 각각의 팩토리로부터 {@code SaveHelper}를 생성한 뒤 순차적으로 {@link SaveHelper#save()}를 호출합니다.
 * </p>
 *
 * <p>
 * {@link #execute()} 메서드는 트랜잭션으로 감싸져 있어,
 * 모든 저장 작업이 하나의 트랜잭션으로 처리되며, 중간에 실패할 경우 전체 롤백됩니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class SaveExecutor {
    private final List<SaveHelperFactory> saveHelperFactoryList;

    @Autowired
    public SaveExecutor(List<SaveHelperFactory> saveHelperFactoryList) {
        this.saveHelperFactoryList = saveHelperFactoryList;
    }

    @Transactional
    public void execute(){
        for(SaveHelper saveHelper : createSaveHelperList()){
            saveHelper.save();
        }
    }

    private List<SaveHelper> createSaveHelperList(){
        return saveHelperFactoryList.stream()
                .map(SaveHelperFactory::createSaveHelper)
                .toList();
    }
}
