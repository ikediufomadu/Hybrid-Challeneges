package main

import (
	"crypto/rand"
	"database/sql"
	"encoding/base64"
	"fmt"
	"net/http"
	"time"
)

// CreateCookie handles session management
func CreateCookie(db *sql.DB, userID int, username string, w http.ResponseWriter) {
	// Generate a new unique session ID
	sessionIDValue := generateSessionID()
	for {
		// Check if the generated session ID already exists in the sessions table
		var count int
		err := db.QueryRow("SELECT COUNT(*) FROM sessions WHERE session_id = ?", sessionIDValue).Scan(&count)
		if err != nil {
			// Handle duplicate session ID generation error
			fmt.Println("Error duplicate session ID:", err)
			http.Error(w, "Internal server error", http.StatusConflict)
			return
		}
		if count == 0 {
			// Session ID is unique, break out of the loop
			break
		}
		// If session ID already exists, generate a new one
		sessionIDValue = generateSessionID()
	}

	// Store session data in the sessions table
	_, err := db.Exec("INSERT INTO sessions (session_id, user_id, username, started_at, ended_at) VALUES (?, ?, ?, ?, ?)",
		sessionIDValue, userID, username, time.Now().UTC(), time.Now().UTC())
	if err != nil {
		// Handle session creation error
		fmt.Println("Error creating session:", err)
		http.Error(w, "Internal server error", http.StatusInternalServerError)
		return
	}

	// Create a cookie with session ID
	cookie := http.Cookie{
		Name:  "session_id",
		Value: sessionIDValue,
		// Set cookie path to root ("/") to make it accessible from all paths
		Path: "/",
		// Set cookie expiration time for 24 hours
		Expires:  time.Now().UTC().Add(24 * time.Hour),
		HttpOnly: true,
		SameSite: http.SameSiteStrictMode, // Set SameSite to Strict to prevent CSRF attacks
	}
	http.SetCookie(w, &cookie)
}

// generateSessionID generates a random session ID
func generateSessionID() string {
	// Generate a random byte slice
	b := make([]byte, 32)
	_, err := rand.Read(b)
	if err != nil {
		// Handle error possible method recall
		return ""
	}
	// Encode the byte slice to base64 string
	return base64.StdEncoding.EncodeToString(b)
}
