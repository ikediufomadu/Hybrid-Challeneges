package main

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
)

type ServerResponse struct {
	Success bool   `json:"success"`
	Message string `json:"message"`
}

type LoginRequestBody struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

// CORSPreflight handles CORS preflight request
func CORSPreflight(w http.ResponseWriter, r *http.Request) {
	addCORSHeaders(w)
	w.WriteHeader(http.StatusNoContent)
}

// addCORSHeaders adds CORS headers to the response
func addCORSHeaders(w http.ResponseWriter) {
	w.Header().Set("Access-Control-Allow-Origin", "http://localhost:3000")
	w.Header().Set("Access-Control-Allow-Methods", "POST, PUT, OPTIONS")
	w.Header().Set("Access-Control-Allow-Headers", "Content-Type")
	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Credentials", "true")
}



// sendJSONResponse sends a JSON response with the given status code and data.
func sendJSONResponse(w http.ResponseWriter, statusCode int, data interface{}) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(statusCode)
	json.NewEncoder(w).Encode(data)
}

func handleLogin(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodOptions {
		CORSPreflight(w, r)
		return
	}
	addCORSHeaders(w)

	// Handle incorrect method access
	if r.Method != http.MethodPost {
		fmt.Println("Incorrect method access:", r.Method)
		sendJSONResponse(w, http.StatusBadRequest, ServerResponse{Success: false, Message: "Incorrect method access"})
		return
	}

	// Grabs JSON from the post request
	var requestBody LoginRequestBody
	body, err := io.ReadAll(r.Body)
	if err != nil {
		fmt.Println("Error reading request body:", err)
		sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Error reading request body"})
		return
	}
	defer r.Body.Close()

	// Unmarshal JSON data from requestBody
	err = json.Unmarshal(body, &requestBody)
	if err != nil {
		fmt.Println("Error parsing JSON:", err)
		sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Error parsing JSON"})
		return
	}
	dbName := "users"
	username := requestBody.Username
	password := requestBody.Password

	// Query the database with the retrieved username, and grabs the user's password
	var storedPassword string
	err = db.QueryRow("SELECT password FROM "+dbName+" WHERE username = ?", username).Scan(&storedPassword)
	if err != nil {
		fmt.Println("Error querying database:", err)
		sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Error querying database"})
		return
	}

	// Compare the retrieved password with the submitted one
	if password == storedPassword {
		// Query the database with the retrieved username
		var userID int
		err = db.QueryRow("SELECT id FROM "+dbName+" WHERE username = ?", username).Scan(&userID)

		createCookie(db, userID, username, w)
		fmt.Println("A user just logged in", "username:", username, "userID:", userID)
		sendJSONResponse(w, http.StatusOK, ServerResponse{Success: true, Message: "Login successful"})
		return
	}

	// If passwords don't match, return error response
	fmt.Println("Failed login attempt", "username:", username, "password:", password)
	sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Incorrect username or password"})
}

func handleLogout(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodOptions {
		CORSPreflight(w, r)
		return
	}
	addCORSHeaders(w)

	// Handle incorrect method access
	if r.Method != http.MethodPut {
		fmt.Println("Incorrect method access:", r.Method)
		sendJSONResponse(w, http.StatusBadRequest, ServerResponse{Success: false, Message: "Incorrect method access"})
		return
	}

	// Retrieve session ID from the brower's cookie
	cookie, err := r.Cookie("session_id")
	if err != nil {
		fmt.Println("Error retrieving session ID cookie:", err)
		sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Error retrieving session ID cookie"})
		return
	}

	// Update the ended_at value in the sessions table
	_, err = db.Exec("UPDATE sessions SET ended_at = ? WHERE session_id = ?", time.Now().UTC(), cookie.Value)
	if err != nil {
		fmt.Println("Error updating session:", err)
		sendJSONResponse(w, http.StatusInternalServerError, ServerResponse{Success: false, Message: "Error updating session"})
		return
	}

	deleteSessionCookie(w)

	// Respond with JSON containing the redirect URL
	sendJSONResponse(w, http.StatusOK, ServerResponse{Success: true, Message: "http://localhost:3000"})
}
