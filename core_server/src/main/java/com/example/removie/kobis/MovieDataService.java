package com.example.removie.kobis;


import com.example.removie.movie.vo.MovieData;


/**
 * 영화 또는 드라마의 식별 코드를 기반으로 상세 정보를 제공하는 서비스 인터페이스입니다.
 * <p>
 * 현재는 편의상 {@code movieCode}를 사용하고 있지만, 장기적으로는 OTT를
 * 지원하기 위한 확장 가능성을 염두에 두고 설계되었습니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface MovieDataService {
    MovieData getMovieData(String movieCode);
}
