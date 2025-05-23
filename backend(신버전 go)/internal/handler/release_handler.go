package handler

import (
	"context"
	"encoding/json"
	"log"
	"net/http"
	"removie_backend/internal/service"
	"strconv"
	"time"
)

type ReleaseHandler struct {
	releaseService *service.ReleaseService
}

func NewReleaseHandler(service *service.ReleaseService) *ReleaseHandler {
	return &ReleaseHandler{releaseService: service}
}

func (rh *ReleaseHandler) GetReleaseInfoByPage(w http.ResponseWriter, r *http.Request) {
	limit := r.URL.Query().Get("limit")
	offset := r.URL.Query().Get("offset")

	lmt, errL := strconv.Atoi(limit)
	ofs, errO := strconv.Atoi(offset)

	if errL != nil || lmt < 1 {
		http.Error(w, "invalid 'limit' parameter", http.StatusBadRequest)
		return
	}

	if errO != nil || ofs < 0 {
		http.Error(w, "invalid 'offset' parameter", http.StatusBadRequest)
		return
	}

	ctx, cancel := context.WithTimeout(r.Context(), 2*time.Second)
	defer cancel()

	releaseData, err := rh.releaseService.GetReleaseInfo(ctx, lmt, ofs)

	if err != nil {
		ErrorHandle(w, err, "ReleaseHandler")
		return
	}

	w.Header().Set("Content-Type", "application/json")

	if err := json.NewEncoder(w).Encode(releaseData); err != nil {
		log.Printf("[ERROR][ReleaseHandler] %v", err)
		http.Error(w, "internal server error", http.StatusInternalServerError)
		return
	}

	log.Printf("[INFO][OUT] %s %s?limit=%s&offset=%s", r.Method, r.URL.Path, limit, offset)
}

func (rh *ReleaseHandler) GetReMovie(w http.ResponseWriter, r *http.Request) {

	ctx, cancel := context.WithTimeout(r.Context(), 2*time.Second)
	defer cancel()

	releaseData, err := rh.releaseService.GetReMovieList(ctx)

	if err != nil {
		ErrorHandle(w, err, "ReleaseHandler")
		return
	}

	w.Header().Set("Content-Type", "application/json")

	if err := json.NewEncoder(w).Encode(releaseData); err != nil {
		log.Printf("[ERROR][ReleaseHandler] %v", err)
		http.Error(w, "internal server error", http.StatusInternalServerError)
		return
	}
	log.Printf("[INFO][OUT] %s %s", r.Method, r.URL.Path)
}
