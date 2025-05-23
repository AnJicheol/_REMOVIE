package com.example.removie.movie.repository;

import com.example.removie.movie.entity.ReleaseEntity;
import com.example.removie.movie.vo.BasicMovieVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository <ReleaseEntity, Long> {

    @Query("SELECT BasicMovieVO(r.movieCode, r.ranking) FROM ReleaseEntity r WHERE r.version = :version")
    List<BasicMovieVO> findByVersion(@Param("version") Integer version);
}
