package com.example.removie.update;


import com.example.removie.cinema.service.CinemaUpdateService;
import com.example.removie.movie.saveHelper.SaveExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 모든 프로세스에 파사드 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Service
public class UpdateServiceImpl implements UpdateService{
    private final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

    private final CinemaUpdateService cinemaUpdateService;
    private final SaveExecutor saveExecutor;

    @Autowired
    public UpdateServiceImpl(CinemaUpdateService cinemaUpdateService, SaveExecutor saveExecutor) {
        this.cinemaUpdateService = cinemaUpdateService;
        this.saveExecutor = saveExecutor;
    }


    @Override
    public void runUpdate(){
        movieUpdate();
        cinemaUpdate();
    }

    private void movieUpdate(){
        try{
            saveExecutor.execute();
        }catch (Exception e){
            logFatalError(e);
        }
    }

    private void cinemaUpdate(){
        try{
            cinemaUpdateService.cinemaDataUpdate();
        }catch (Exception e){
            logFatalError(e);
        }
    }

    private void logFatalError(Exception e){
        logger.info("""
                프로세스 종료
                예외 내용 :
                {}
                """, e.getMessage(), e);
    }

}
