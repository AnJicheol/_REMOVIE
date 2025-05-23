package com.example.removie.movie.jpaManager;


import com.example.removie.movie.entity.MovieDataEntity;
import com.example.removie.movie.repository.MovieRepository;
import com.example.removie.retry.IORetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MovieJpaManager implements UpdateJpaManager<List<MovieDataEntity>>{
    private final MovieRepository movieRepository;

    @Autowired
    public MovieJpaManager(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @IORetry
    @Transactional
    public void saveAllMovieData(List<MovieDataEntity> movieDataList){
        movieRepository.saveAll(movieDataList);
    }

    @IORetry
    @Transactional(readOnly = true)
    public boolean notExistsByMovieCode(String movieCode){
        return !movieRepository.existsByMovieCode(movieCode);
    }

    @Override
    @Transactional
    public void update(List<MovieDataEntity> movieDataList) {
        saveAllMovieData(movieDataList);
    }


}
