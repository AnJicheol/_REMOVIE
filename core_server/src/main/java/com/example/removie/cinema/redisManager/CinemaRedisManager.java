package com.example.removie.cinema.redisManager;

import com.example.removie.cinema.CinemaEntity;
import com.example.removie.cinema.exception.CinemaJsonFailException;
import com.example.removie.retry.IORetry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class CinemaRedisManager {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CinemaRedisManager(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }


    @IORetry
    public void saveCurrentCinema(List<CinemaEntity> cinemaEntityList) {
        for(CinemaEntity entity : cinemaEntityList){
            try {
                String json = objectMapper.writeValueAsString(entity.getCinemaDataList());
                String key = entity.getMovieCode();

                stringRedisTemplate.opsForValue().set(key, json, Duration.ofHours(25));

            } catch (JsonProcessingException e) {
                throw new CinemaJsonFailException(e.getMessage());
            }
        }
    }
}
