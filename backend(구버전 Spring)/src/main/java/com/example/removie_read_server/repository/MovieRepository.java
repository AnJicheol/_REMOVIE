package com.example.removie_read_server.repository;

import com.example.removie_read_server.Entity.MovieDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface MovieRepository extends JpaRepository<MovieDataEntity, Long> {

    List<MovieDataEntity> findAllByMovieCodeIn (List<String> movieCodeList);
    Optional<MovieDataEntity> findByTitle(String title);
}
