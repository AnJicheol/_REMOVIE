package service

import (
	"context"
	"log"
	"removie_backend/internal/repository"
	"removie_backend/internal/response"
)

type ReleaseService struct {
	repo          *repository.ReleaseRepository
	cinemaService *CinemaService
}

func NewReleaseService(releaseRepository *repository.ReleaseRepository, service *CinemaService) *ReleaseService {
	return &ReleaseService{repo: releaseRepository, cinemaService: service}
}

func (r *ReleaseService) GetReleaseInfo(ctx context.Context, limit int, offset int) ([]response.ReleaseResponse, error) {
	data, err := r.repo.GetReleaseInfo(ctx, limit, offset)

	if err != nil {
		return nil, err
	}

	var res []response.ReleaseResponse

	for _, item := range data {
		cinema, errC := r.cinemaService.GetCinemaList(ctx, item.MovieCode)

		if errC != nil {
			log.Printf("[ERROR][ReleaseService]cinema 조회 실패: movieCode=%s, err=%v", item.MovieCode, errC)
			cinema = nil
		}

		res = append(res, response.ReleaseResponse{
			MovieData: &item,
			Cinema:    cinema,
		})
	}

	return res, nil
}

func (r *ReleaseService) GetReMovieList(ctx context.Context) ([]response.ReleaseSimpleResponse, error) {
	data, err := r.repo.GetReMovie(ctx)

	if err != nil {
		return nil, err
	}

	var res []response.ReleaseSimpleResponse

	for _, item := range data {
		cinema, errC := r.cinemaService.GetCinemaList(ctx, item.MovieCode)

		if errC != nil {
			log.Printf("[ERROR][ReleaseService]cinema 조회 실패: movieCode=%s, err=%v", item.MovieCode, errC)
			continue
		}

		res = append(res, response.ReleaseSimpleResponse{
			MovieData: &item,
			Cinema:    cinema,
		})
	}

	return res, nil
}
