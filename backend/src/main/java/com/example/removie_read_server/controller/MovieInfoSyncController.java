package com.example.removie_read_server.controller;


import com.example.removie_read_server.dto.MovieSyncDTO;
import com.example.removie_read_server.service.MovieInfoSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie/info/sync")
public class MovieInfoSyncController {
    private final MovieInfoSyncService movieInfoSyncService;

    @Autowired
    public MovieInfoSyncController(MovieInfoSyncService movieInfoSyncService) {
        this.movieInfoSyncService = movieInfoSyncService;
    }

    @GetMapping("/{version}")
    public ResponseEntity<MovieSyncDTO> getMovieSyncData(@PathVariable Integer version){
        return ResponseEntity.ok(movieInfoSyncService.getMovieSyneData(version));
    }
}
