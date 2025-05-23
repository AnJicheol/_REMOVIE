package response

import "removie_backend/internal/vo"

type ReleaseResponse struct {
	MovieData *vo.ReleaseData
	Cinema    []string
}
