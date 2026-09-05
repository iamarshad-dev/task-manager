# Task Manager API

A RESTful Task Manager API built with Spring Boot and PostgreSQL.

This project was built as part of my Spring Boot learning journey,
focusing on building a clean backend application with REST APIs,
database migrations, validation, filtering, pagination, testing,
environment configuration, and Docker.

## Tech Stack

-   Java 25
-   Spring Boot 4.1.1
-   Spring Web
-   Spring Data JPA
-   PostgreSQL
-   Flyway
-   Jakarta Validation
-   Lombok
-   JUnit & Mockito
-   Gradle
-   Docker
-   Docker Compose

## Features

-   Create tasks
-   Retrieve all tasks
-   Retrieve a task by ID
-   Update tasks
-   Partially update tasks
-   Update task status
-   Delete tasks
-   Pagination and sorting
-   Filter by status and priority
-   Search by title
-   Filter by due date range
-   Request validation
-   Centralized exception handling
-   Database migrations with Flyway
-   Application logging
-   Environment-based configuration
-   Dockerized application
-   PostgreSQL persistence with Docker volumes

## Project Structure

``` text
src/main/java/com/arshad/taskmanager/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── service/
└── specification/
```

The application follows a layered architecture:

``` text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

## API Endpoints

  Method   Endpoint                      Description
  -------- ----------------------------- -------------------------
  POST     `/api/v1/tasks`               Create a task
  GET      `/api/v1/tasks`               Get all tasks
  GET      `/api/v1/tasks/{id}`          Get task by ID
  PUT      `/api/v1/tasks/{id}`          Update a task
  PATCH    `/api/v1/tasks/{id}`          Partially update a task
  PATCH    `/api/v1/tasks/{id}/status`   Update task status
  DELETE   `/api/v1/tasks/{id}`          Delete a task

## Filtering, Pagination and Sorting

`GET /api/v1/tasks` supports:

-   `status`
-   `priority`
-   `title`
-   `dueDateFrom`
-   `dueDateTo`
-   `page`
-   `size`
-   `sort`

Example:

``` http
GET /api/v1/tasks?status=PENDING&priority=HIGH&page=1&size=10&sort=createdAt,desc
```

## Task Model

A task contains:

-   `id`
-   `title`
-   `description`
-   `status`
-   `priority`
-   `dueDate`
-   `createdAt`
-   `updatedAt`

### Status

`PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

### Priority

`LOW`, `MEDIUM`, `HIGH`, `URGENT`

## Example Request

### Create Task

``` http
POST /api/v1/tasks
Content-Type: application/json
```

``` json
{
  "title": "Learn Docker",
  "description": "Practice Docker with Spring Boot",
  "priority": "HIGH",
  "dueDate": "2026-09-20T10:00:00"
}
```

## Environment Variables

Create a `.env` file in the project root using `.env.example` as a
template:

``` env
DB_URL=jdbc:postgresql://localhost:5432/task_manager
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

The actual `.env` file is ignored by Git and should not be committed.

## Running Locally

### Prerequisites

-   Java 25
-   PostgreSQL

Create the PostgreSQL database:

``` sql
CREATE DATABASE task_manager;
```

Configure `.env`, then run on Windows:

``` powershell
.\gradlew bootRun
```

Or on macOS/Linux:

``` bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

Flyway automatically applies the required database migrations during
startup.

## Running with Docker Compose

Build the Spring Boot JAR:

``` powershell
.\gradlew clean bootJar
```

Start Spring Boot and PostgreSQL:

``` bash
docker compose up -d --build
```

Check the containers:

``` bash
docker compose ps
```

View application logs:

``` bash
docker compose logs -f app
```

Stop the containers:

``` bash
docker compose down
```

PostgreSQL data is stored in a Docker volume and remains available after
`docker compose down`.

> **Warning:** `docker compose down -v` also removes the PostgreSQL
> volume and its data.

## Database Migrations

Database schema changes are managed with Flyway.

Migration files are stored in:

``` text
src/main/resources/db/migration/
```

Hibernate uses `ddl-auto: validate`, so Flyway manages the schema while
Hibernate validates the entity mappings.

## Testing

Service-layer unit tests use JUnit and Mockito.

Run tests:

``` powershell
.\gradlew test
```

Controller tests, integration tests, and Testcontainers are planned
improvements.

## What I Learned

-   Building REST APIs with Spring Boot
-   Layered architecture
-   DTO and entity separation
-   Spring Data JPA
-   PostgreSQL integration
-   Flyway migrations
-   Bean validation
-   Global exception handling
-   HTTP status codes
-   Pagination and sorting
-   Dynamic filtering with JPA Specifications
-   Transaction management
-   Application logging
-   Unit testing with JUnit and Mockito
-   Environment-based configuration
-   Docker fundamentals
-   Dockerizing Spring Boot
-   Docker Compose
-   Container networking
-   Docker volumes and database persistence

## Future Improvements

-   Controller tests
-   Integration tests
-   Testcontainers
-   OpenAPI / Swagger documentation
-   Authentication and authorization

## License

This project is for learning and portfolio purposes.
