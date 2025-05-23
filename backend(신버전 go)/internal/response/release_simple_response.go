package response

import "removie_backend/internal/vo"

type ReleaseSimpleResponse struct {
	MovieData *vo.ReleaseSimpleData
	Cinema    []string
}
