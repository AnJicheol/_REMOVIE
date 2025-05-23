package main

import (
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/awslabs/aws-lambda-go-api-proxy/httpadapter"
	"log"
	"net/http"
	"removie_backend/internal/config"
	"removie_backend/internal/handler"
	"removie_backend/internal/repository"
	"removie_backend/internal/service"
)

func main() {
	dbC := config.InitDB()
	config.InitRedis()

	defer func() {
		if err := dbC.Close(); err != nil {
			log.Printf("DB Close 중 에러 발생: %v", err)
		}
	}()

	repoM := repository.NewMovieRepository(dbC)
	svsM := service.NewMovieService(repoM)
	hM := handler.NewMovieHandler(svsM)

	svsC := service.NewCinemaService(config.RedisClient)

	repoR := repository.NewReleaseRepository(dbC)
	svsR := service.NewReleaseService(repoR, svsC)
	hR := handler.NewReleaseHandler(svsR)

	mux := http.NewServeMux()
	mux.HandleFunc("/release/info", hR.GetReleaseInfoByPage)
	mux.HandleFunc("/release/info/re", hR.GetReMovie)
	mux.HandleFunc("/movie", hM.GetMovieDataByTitle)

	adapter := httpadapter.New(mux)
	lambda.Start(adapter.Proxy)

	//// /release/info/limit, offset
	//http.HandleFunc("/release/info", hR.GetReleaseInfoByPage)
	//http.HandleFunc("/release/info/re", hR.GetReMovie)
	//http.HandleFunc("/movie", hM.GetMovieDataByTitle)
	//err := http.ListenAndServe(":8080", nil)
	//
	//if err != nil {
	//	log.Printf("[ERROR][main] ListenAndServe failed: %v", err)
	//	return
	//}
}
