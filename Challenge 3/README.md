# Login Website

This project is a simple login website application built with Go for the backend and React for the frontend.

## Prerequisites
    Before you begin, ensure you have met the following requirements:
    -   Node.js v18.13.0 or later installed on your machine.
    - npm package manager.
    - Golang v1.21.1 installed on your machine.
    - Operating System: Windows 64-bit.
    - MySQL server

## Getting Started
### Database Setup
    1. Open your terminal and navigate to the Backend folder.
    2. Open the SQL_Script text file.
    3. Copy the commands into your MySQL server to set up the necessary tables and data.
    4. Inorder to change the database user information go into the main.go file, in the InitializeDB function you can change the login credentials to match your own database users.
    5. If necessary, you can change the database connection type and port in the main.go file as well.
        - db, err = sql.Open("mysql", username+":"+password+"@tcp(localhost:3306)/"+dbname)
### Backend Setup
    1. Open your terminal and navigate to the Backend folder.
    2. Ensure you have Go installed and configured.
    3. Download backend dependencies using go mod download.
    4. Start the server by running 'go run .'.
### Frontend Setup
    1. Open your terminal and navigate to the Frontend folder.
    2. Install frontend dependencies using npm install.
    3. Start the website using one of two methods:
        * npm start
        * docker compose up --build
## Using the program
    1. Open your web browser and navigate to 'http://localhost:3000'.
    2. Use one of the authorized users to log in to the secure page.
    3. Upon successful login, you will be redirected to the secure page where you can access additional functionalities.

## Security Considerations
- Password Hashing: I did not hash the user's password, so it is sent as plaintext over the network.

- Page Access: The secure page can be accessed by directly inputing the endpoint into the url.
    - http://localhost:3000/secure will allow an unauthorized user to freely access the webpage.
        
- Access Manipulation: Once a user logs in you can press freely access the authorized webpage by simply using the back and forward history arrow buttons.
