package handler

import (
	"context"
	"encoding/json"
	"log"
	"net/http"
	"removie_backend/internal/service"
	"time"
)

type MovieHandler struct {
	movieService *service.MovieService
}

func NewMovieHandler(movieService *service.MovieService) *MovieHandler {
	return &MovieHandler{movieService: movieService}
}

func (m *MovieHandler) GetMovieDataByTitle(w http.ResponseWriter, r *http.Request) {
	movieTitle := r.URL.Query().Get("movieTitle")

	if movieTitle == "" {
		http.Error(w, "empty 'movieTitle' parameter", http.StatusBadRequest)
		return
	}

	ctx, cancel := context.WithTimeout(r.Context(), 2*time.Second)
	defer cancel()

	movieData, err := m.movieService.GetMovieData(ctx, movieTitle)

	if err != nil {
		ErrorHandle(w, err, "MovieHandler")
		return
	}

	w.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(w).Encode(movieData); err != nil {
		log.Printf("[ERROR][MovieH] encode failed: %v", err)
	}
	log.Printf("[INFO][OUT] %s %s?movieTitle=%s", r.Method, r.URL.Path, movieTitle)
}
