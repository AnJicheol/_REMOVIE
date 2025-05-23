package com.example.removie.movie.repository;

import com.example.removie.movie.entity.MovieDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MovieRepository extends JpaRepository<MovieDataEntity, Long> {
    boolean existsByMovieCode(String movieCode);

}
