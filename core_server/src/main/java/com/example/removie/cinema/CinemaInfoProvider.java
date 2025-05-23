package com.example.removie.cinema;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 영화관 코드에 따른 상영관 정보를 제공하는 유틸리티 클래스입니다.
 * <p>
 * 개봉 정보 및 상영관 정보는 웹 스크래핑을 통해 수집되므로,
 * 데이터의 정확성을 높이기 위해 미리 정의된 상수 기반의 신뢰 가능한 데이터를 제공합니다.
 * <p>
 * 상영관 코드는 외부 시스템(KOBIS 등)에서 제공하는 코드 체계를 기준으로 하며,
 * 코드가 일치하지 않거나 등록되지 않은 경우에는 null를 반환합니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class CinemaInfoProvider {

    private final Map<String, String> cinemaMap;

    public CinemaInfoProvider() {
        this.cinemaMap = Map.ofEntries(
                Map.entry("A07", "현대예술관시네마"),
                Map.entry("A08", "문화인"),
                Map.entry("A09", "영화의전당"),
                Map.entry("A10", "디트릭스"),
                Map.entry("A11", "더브이엑스"),
                Map.entry("A12", "유유코리아"),
                Map.entry("A14", "영화공장"),
                Map.entry("A15", "에스아이티랩"),
                Map.entry("A16", "MONOPLEX"),
                Map.entry("A17", "MOVIEE"),
                Map.entry("B01", "CGV"),
                Map.entry("B02", "롯데시네마"),
                Map.entry("B07", "메가박스"),
                Map.entry("B08", "CGV"),
                Map.entry("B09", "씨네큐")
        );
    }

    /**
     * 상영관 코드에 대응하는 문자열 반환.
     * 등록되지 않은 코드인 경우 null 객체 반환.
     *
     * @param cinemaCode 상영관 코드
     * @return 상영관 정보
     */
    public @Nonnull String getMovieInfo(@NotNull String cinemaCode) {
        return cinemaMap.getOrDefault(cinemaCode, null);
    }
}