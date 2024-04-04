package main

import (
	"database/sql"
	"fmt"
	"net/http"
	"os"

	_ "github.com/go-sql-driver/mysql"
)

var db *sql.DB

// InitializeDB initializes the database connection.
func InitializeDB() error {
	var err error
	// TODO: put in .env file later
	username := "root"
	password := "123"
	dbname := "loginwebsite"
	db, err = sql.Open("mysql", username+":"+password+"@tcp(localhost:3306)/"+dbname)
	if err != nil {
		return fmt.Errorf("error opening database connection: %w", err)
	}
	return nil
}

func main() {
	if err := InitializeDB(); err != nil {
		fmt.Println("Error initializing:", err)
		os.Exit(1)
	}
	defer db.Close()

	http.HandleFunc("/v1/api/login", handleLogin)
	http.HandleFunc("/v1/api/logout", handleLogout)

	fmt.Println("Server listening on :4000")
	http.ListenAndServe(":4000", nil)
}
