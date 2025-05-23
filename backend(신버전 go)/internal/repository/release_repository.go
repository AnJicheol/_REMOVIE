package repository

import (
	"context"
	"database/sql"
	"log"
	"removie_backend/internal/vo"
)

type ReleaseRepository struct {
	db *sql.DB
}

func NewReleaseRepository(db *sql.DB) *ReleaseRepository {
	return &ReleaseRepository{
		db: db,
	}
}

// GetReleaseInfo 클라이언트 리팩토링 후 query 파일 분리 예정.
func (r *ReleaseRepository) GetReleaseInfo(ctx context.Context, limit int, offset int) ([]vo.ReleaseData, error) {
	const tmpQuery = `WITH latest_version AS (SELECT MAX(version) AS ver FROM release_version),
     prev_version AS (SELECT MAX(version) AS ver 
                      FROM release_version 
                      WHERE version < (SELECT ver FROM latest_version)),
    
     today AS (SELECT r.movie_code, r.ranking, m.movie_title
               FROM release_info r 
               JOIN latest_version v ON r.version = v.ver
               JOIN movie_data m ON m.movie_code = r.movie_code),
    
    yesterday AS (SELECT r.movie_code, r.ranking 
                  FROM release_info r
                  JOIN prev_version p ON p.ver = r.version)

	SELECT t.movie_title, t.movie_code,
	CASE
		WHEN y.ranking IS NULL THEN 'NEW'
		WHEN y.ranking > t.ranking THEN CONCAT('+', y.ranking - t.ranking)
		WHEN y.ranking < t.ranking THEN CONCAT('-', t.ranking - y.ranking)
		ELSE '0'
	END AS ranking_ch
	FROM today t
	LEFT JOIN yesterday y ON y.movie_code = t.movie_code
	ORDER BY t.ranking
	LIMIT ? OFFSET ?;
`

	rows, err := r.db.QueryContext(ctx, tmpQuery, limit, offset)

	if err != nil {
		log.Printf("[ERROR][ReleaseRepo] query failed: %v", err)
		return nil, err
	}

	defer func() {
		cErr := rows.Close()
		if cErr != nil {
			log.Printf("[ERROR][ReleaseRepo] rows Close failed: %v", cErr)
		}
	}()

	var data []vo.ReleaseData

	for rows.Next() {
		info := vo.ReleaseData{}

		if err := rows.Scan(&info.Title, &info.MovieCode, &info.Ranking); err != nil {
			log.Printf("[ERROR][ReleaseRepo] Scan failed: %v", err)
			return nil, err
		}

		data = append(data, info)
	}

	if err := rows.Err(); err != nil {
		log.Printf("[ERROR][ReleaseRepo] rowsErr: %v", err)
		return nil, err
	}

	return data, err
}

// GetReMovie 클라이언트 리팩토링 후 query 파일 분리 예정.
func (r *ReleaseRepository) GetReMovie(ctx context.Context) ([]vo.ReleaseSimpleData, error) {
	const tmpQuery = `SELECT m.movie_title, r.movie_code
						FROM release_info r
						JOIN movie_data m ON m.movie_code = r.movie_code
						JOIN release_date d ON d.movie_code = r.movie_code
						WHERE r.version = (SELECT MAX(version) FROM release_version)
						  AND d.release_date < CURDATE() - INTERVAL 3 MONTH
						ORDER BY r.ranking;`

	rows, err := r.db.QueryContext(ctx, tmpQuery)

	if err != nil {
		log.Printf("[ERROR][ReleaseRepo] query failed: %v", err)
		return nil, err
	}

	defer func() {
		cErr := rows.Close()
		if cErr != nil {
			log.Printf("[ERROR][ReleaseRepo] rows Close failed: %v", cErr)
		}
	}()

	var data []vo.ReleaseSimpleData

	for rows.Next() {
		info := vo.ReleaseSimpleData{}

		if err := rows.Scan(&info.Title, &info.MovieCode); err != nil {
			log.Printf("[ERROR][ReleaseRepo] Scan failed: %v", err)
			return nil, err
		}

		data = append(data, info)
	}

	if err := rows.Err(); err != nil {
		log.Printf("[ERROR][ReleaseRepo] rowsErr: %v", err)
		return nil, err
	}

	return data, err
}
