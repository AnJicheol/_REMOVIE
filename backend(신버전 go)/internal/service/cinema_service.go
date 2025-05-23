package service

import (
	"context"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/redis/go-redis/v9"
)

type CinemaService struct {
	rc *redis.Client
}

func NewCinemaService(rc *redis.Client) *CinemaService {
	return &CinemaService{rc: rc}
}

func (c *CinemaService) GetCinemaList(ctx context.Context, key string) ([]string, error) {
	val, err := c.rc.Get(ctx, key).Result()

	if errors.Is(err, redis.Nil) {
		return []string{}, nil
	}
	if err != nil {
		return nil, err
	}

	var cinemas []string
	if err := json.Unmarshal([]byte(val), &cinemas); err != nil {
		return nil, fmt.Errorf("[ERROR][CinemaService] cinema JSON 역직렬화 실패: %w", err)
	}

	return cinemas, nil
}
