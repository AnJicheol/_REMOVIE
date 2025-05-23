package com.example.removie.movie.jpaManager;

import com.example.removie.movie.entity.GenreEntity;
import com.example.removie.movie.repository.GenreRepository;
import com.example.removie.retry.IORetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GenreJpaManager implements UpdateJpaManager<List<GenreEntity>>{
    private final GenreRepository genreRepository;

    @Autowired
    public GenreJpaManager(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @IORetry
    @Override
    @Transactional
    public void update(List<GenreEntity> genreEntities) {
        genreRepository.saveAll(genreEntities);
    }
}
