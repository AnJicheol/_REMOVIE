package repository

import (
	"context"
	"database/sql"
	"log"
	"removie_backend/internal/vo"
	"time"
)

type MovieRepository struct {
	db *sql.DB
}

func NewMovieRepository(db *sql.DB) *MovieRepository {
	return &MovieRepository{
		db: db,
	}
}

// GetMovieData 클라이언트 리팩토링 후 query 파일 분리 예정.
func (m *MovieRepository) GetMovieData(ctx context.Context, title string) (*vo.MovieData, error) {
	const tmpQuery = `SELECT m.movie_title, g.genre, d.release_date FROM MOVIE_DATA m 
                                 left join movie_genre g on m.movie_code = g.movie_code
                                 left join release_date d on m.movie_code = d.movie_code
                                 WHERE movie_title = ?`

	rows, err := m.db.QueryContext(ctx, tmpQuery, title)

	if err != nil {
		log.Printf("[ERROR][MovieRepo] query failed: %v", err)
		return nil, err
	}

	defer func() {
		cErr := rows.Close()
		if cErr != nil {
			log.Printf("[ERROR][MovieRepo] rows Close failed: %v", cErr)
		}
	}()

	var data *vo.MovieData

	for rows.Next() {
		var t string
		var genre string
		var date time.Time

		if err := rows.Scan(&t, &genre, &date); err != nil {
			log.Printf("[ERROR][MovieRepo] Scan failed: %v", err)
			return nil, err
		}

		if data == nil {
			data = vo.NewMovieData()
			data.Title = t
		}
		data.Genres[genre] = struct{}{}
		data.ReleaseDates[date] = struct{}{}
	}

	if err := rows.Err(); err != nil {
		log.Printf("[ERROR][MovieRepo] rowsErr: %v", err)
		return nil, err
	}
	return data, nil
}
