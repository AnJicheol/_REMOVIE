package com.example.removie.cinema.service;

import com.example.removie.cinema.CinemaEntity;
import com.example.removie.cinema.redisManager.CinemaRedisManager;
import com.example.removie.kobis.NewReleasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CinemaUpdateServiceImpl implements CinemaUpdateService{
    private final CinemaService cinemaService;
    private final CinemaRedisManager cinemaRedisManager;
    private final NewReleasesService newReleasesService;

    @Autowired
    public CinemaUpdateServiceImpl(CinemaService cinemaService, CinemaRedisManager cinemaRedisManager, NewReleasesService newReleasesService) {
        this.cinemaService = cinemaService;
        this.cinemaRedisManager = cinemaRedisManager;
        this.newReleasesService = newReleasesService;
    }

    public void cinemaDataUpdate(){
        List<CinemaEntity> cinemaEntityList = cinemaService.getCinemaEntityList(getMovieCodeList());
        update(cinemaEntityList);
    }

    private void update(List<CinemaEntity> cinemaEntityList){
        cinemaRedisManager.saveCurrentCinema(cinemaEntityList);
    }

    private List<String> getMovieCodeList(){
        return newReleasesService.getReleaseMap().getMovieCodeList();
    }
}
