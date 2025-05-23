package service

import (
	"context"
	"removie_backend/internal/repository"
	"removie_backend/internal/vo"
)

type MovieService struct {
	repo *repository.MovieRepository
}

func NewMovieService(repo *repository.MovieRepository) *MovieService {
	return &MovieService{repo: repo}
}

func (m *MovieService) GetMovieData(ctx context.Context, title string) (*vo.MovieData, error) {
	movieData, err := m.repo.GetMovieData(ctx, title)

	if err != nil {
		return nil, err
	}

	return movieData, nil
}
