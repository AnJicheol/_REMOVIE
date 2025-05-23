package com.example.removie.cinema.service;


import com.example.removie.cinema.CinemaEntity;
import com.example.removie.cinema.fetcher.CinemaDataFetcher;
import com.example.removie.log.LogGroup;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  상영관 엔티티 리스트를 생성하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Service
public class CinemaServiceImpl implements CinemaService{
    private final CinemaDataFetcher cinemaDataFetcher;

    @Autowired
    public CinemaServiceImpl(CinemaDataFetcher cinemaDataFetcher) {
        this.cinemaDataFetcher = cinemaDataFetcher;
    }

    /**
     * {@code cinemaDataFetcher.getCinemaData(movieCode)} 는 상영관 리스트를 반환합니다
     *
     * @param movieCodeList 상영 중인 영화에 식별자 코드 리스트입니다.
     * @return Redis에 저장하기 위한 엔티티를 리턴합니다.
     */
    @LogGroup
    public @Nonnull List<CinemaEntity> getCinemaEntityList(@NotBlank List<String> movieCodeList) {
        return movieCodeList.stream()
                .map(movieCode -> new CinemaEntity(
                        movieCode,
                        cinemaDataFetcher.getCinemaData(movieCode)))
                .collect(Collectors.toList());
    }
}


