package com.example.removie.movie.fetcher;

import com.example.removie.kobis.parser.KOBISMovieDateParser;
import com.example.removie.movie.vo.ReleaseDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 영화의 개봉일 정보를 조회하는 컴포넌트입니다.
 *
 * <p>
 * 이 클래스는 {@link NewMovieFetcher}를 통해 새로운 영화 코드 목록을 가져오고,
 * 각 영화에 대해 {@link KOBISMovieDateParser}를 사용하여 개봉일 정보를 파싱합니다.
 * </p>
 *
 * <p>
 * 최종적으로 각 영화 코드에 해당하는 {@link ReleaseDate} 객체 목록을 반환합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class ReleaseDateFetcher {
    private final KOBISMovieDateParser kobisMovieDateParser;
    private final NewMovieFetcher newMovieFetcher;

    @Autowired
    public ReleaseDateFetcher(KOBISMovieDateParser kobisMovieDateParser, NewMovieFetcher newMovieFetcher) {
        this.kobisMovieDateParser = kobisMovieDateParser;
        this.newMovieFetcher = newMovieFetcher;
    }

    public List<ReleaseDate> fetchReleaseDate(){
        return newMovieFetcher.getNewMovieList().stream()
                .map(kobisMovieDateParser::getParsingResult)
                .toList();
    }
}
