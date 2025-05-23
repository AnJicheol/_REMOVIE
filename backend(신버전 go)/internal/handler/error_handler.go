package handler

import (
	"context"
	"database/sql"
	"errors"
	"log"
	"net/http"
)

func ErrorHandle(w http.ResponseWriter, err error, handle string) {
	var status int
	var message string

	switch {
	case errors.Is(err, sql.ErrNoRows):
		status = http.StatusNotFound
		message = "resource not found"
	case errors.Is(err, context.DeadlineExceeded):
		status = http.StatusRequestTimeout
		message = "request timeout"

	default:
		status = http.StatusInternalServerError
		message = "internal server error"
	}

	log.Printf("[ERROR][%v] err: %v", handle, err)
	http.Error(w, message, status)
}
