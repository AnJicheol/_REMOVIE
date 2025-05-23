package vo

import (
	"encoding/json"
	"time"
)

type MovieData struct {
	Title        string
	Genres       map[string]struct{}
	ReleaseDates map[time.Time]struct{}
}

func NewMovieData() *MovieData {
	return &MovieData{
		Genres:       make(map[string]struct{}),
		ReleaseDates: make(map[time.Time]struct{}),
	}
}
func (m MovieData) MarshalJSON() ([]byte, error) {

	genres := make([]string, 0, len(m.Genres))
	for g := range m.Genres {
		genres = append(genres, g)
	}
	dates := make([]string, 0, len(m.ReleaseDates))
	for d := range m.ReleaseDates {
		dates = append(dates, d.Format("2006-01-02"))
	}

	type Alias MovieData
	return json.Marshal(&struct {
		Title        string   `json:"title"`
		Genres       []string `json:"genres"`
		ReleaseDates []string `json:"releaseDates"`
	}{
		Title:        m.Title,
		Genres:       genres,
		ReleaseDates: dates,
	})
}
