package com.example.removie.cinema.service;

import com.example.removie.cinema.CinemaEntity;

import java.util.List;

public interface CinemaRedisService {
    void saveCurrentCinema(List<CinemaEntity> cinemaEntityList);
}
