package com.example.removie.cinema.service;


import com.example.removie.cinema.CinemaEntity;

import java.util.List;

/**
 *  상영관 엔티티 리스트를 생성하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface CinemaService {

    /**
     * @param movieCodeList 상영 중인 영화에 식별자 코드 리스트입니다.
     * @return Redis에 저장하기 위한 엔티티를 리턴합니다.
     */
    List<CinemaEntity> getCinemaEntityList(List<String> movieCodeList);
}
