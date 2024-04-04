# Use an official Golang runtime as the base image
FROM golang:1.21.1

# Set the working directory to /app
WORKDIR /app/server

# Copy the current directory contents into the container at /app
COPY go.mod go.sum ./
RUN go mod download

COPY *.go ./backend

# Build the Go app
RUN CGO_ENABLED=0 GOOS=linux go build -o /docker-gs-ping

# Expose port 4000 for the app
EXPOSE 4000

# Run
CMD ["/docker-gs-ping"]