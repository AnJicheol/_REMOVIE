package config

import (
	"context"
	"log"
	"os"
	"strconv"
	"time"

	"github.com/redis/go-redis/v9"
)

var (
	RedisClient *redis.Client
)

func InitRedis() {
	addr := os.Getenv("REDIS_ADDR")
	password := os.Getenv("REDIS_PASSWORD")
	dbStr := os.Getenv("REDIS_DB")
	dbIndex, _ := strconv.Atoi(dbStr)

	RedisClient = redis.NewClient(&redis.Options{
		Addr:         addr,
		Password:     password,
		DB:           dbIndex,
		DialTimeout:  1 * time.Second,
		ReadTimeout:  500 * time.Millisecond,
		WriteTimeout: 500 * time.Millisecond,
	})

	ctx, cancel := context.WithTimeout(context.Background(), 2*time.Second)
	defer cancel()

	if err := RedisClient.Ping(ctx).Err(); err != nil {
		log.Fatalf("Redis 연결 실패: %v", err)
	}
	log.Println("Redis 연결 성공")
}
