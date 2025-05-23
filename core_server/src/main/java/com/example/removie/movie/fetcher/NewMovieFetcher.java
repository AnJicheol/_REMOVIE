package com.example.removie.movie.fetcher;


import com.example.removie.kobis.NewReleasesService;
import com.example.removie.movie.jpaManager.ReleaseJpaManager;
import com.example.removie.movie.vo.BasicMovieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 금일 새로 개봉한 영화 코드 목록을 조회하는 객체입니다.
 *
 * <p>
 * {@link NewReleasesService}를 통해 현재 상영 중인 영화 목록을 조회하고,
 * {@link ReleaseJpaManager}를 통해 이미 저장된 영화 데이터를 가져와 비교합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class NewMovieFetcher {
    private final NewReleasesService newReleasesService;
    private final ReleaseJpaManager releaseJpaManager;

    @Autowired
    public NewMovieFetcher(NewReleasesService newReleasesService, ReleaseJpaManager releaseJpaManager) {
        this.newReleasesService = newReleasesService;
        this.releaseJpaManager = releaseJpaManager;
    }

    @Cacheable(value = "newMovieList")
    public List<String> getNewMovieList(){
        Set<String> lastMovieCodes = releaseJpaManager.getLatestMovieList().stream()
                .map(BasicMovieVO::getMovieCode)
                .collect(Collectors.toSet());

        return  newReleasesService.getReleaseMap().getMovieCodesSet().stream()
                .filter(movieCode -> !lastMovieCodes.contains(movieCode))
                .toList();
    }
}
