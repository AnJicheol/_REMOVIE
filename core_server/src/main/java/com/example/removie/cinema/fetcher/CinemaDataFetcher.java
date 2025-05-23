package com.example.removie.cinema.fetcher;


import com.example.removie.cinema.kobis.KOBISCinemaDataParser;
import com.example.removie.cinema.kobis.KOBISCinemaElement;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 상영 리스트 반환하는 객체
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class CinemaDataFetcher {
    private final KOBISCinemaDataParser KOBISCinemaDataParser;
    private final KOBISCinemaElement kobisCinemaElement;

    @Autowired
    public CinemaDataFetcher(KOBISCinemaDataParser KOBISCinemaDataParser, KOBISCinemaElement kobisCinemaElement) {
        this.KOBISCinemaDataParser = KOBISCinemaDataParser;
        this.kobisCinemaElement = kobisCinemaElement;
    }

    /**
     * 영화 코드를 기반으로 HTML 요소를 파싱하여 유효한 상영관 정보를 추출합니다.
     *
     * @param movieCode 상영관 정보를 조회할 영화의 고유 코드
     * @return 해당 영화 코드에 매칭되는 {@code CinemaData} 객체 리스트;
     *         유효한 데이터가 없을 경우 빈 리스트 반환
     */
    public @Nonnull List<String> getCinemaData(String movieCode) {
        return kobisCinemaElement.getCinemaCodeElements(movieCode).stream()
                .map(KOBISCinemaDataParser::extractCinemaData)
                .collect(Collectors.toList());
    }
}
