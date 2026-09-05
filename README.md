# Task Manager API

A RESTful Task Manager API built with Spring Boot and PostgreSQL.

This project was built as part of my Spring Boot learning journey,
focusing on building a clean backend application with REST APIs,
database migrations, validation, filtering, pagination, testing, API
documentation, environment configuration, and Docker.

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
-   OpenAPI / Swagger UI
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
-   Filter by status
-   Filter by priority
-   Search by title
-   Filter by due date range
-   Request validation
-   Centralized exception handling
-   Database migrations with Flyway
-   Dynamic filtering with JPA Specifications
-   Application logging
-   Environment-based configuration
-   Interactive API documentation with Swagger UI
-   Dockerized Spring Boot application
-   PostgreSQL with persistent Docker volumes

## Project Structure

``` text
src/main/java/com/arshad/taskmanager/
├── config/
│   ├── OpenApiConfig.java
│   └── WebConfig.java
├── controller/
│   └── TaskController.java
├── dto/
│   ├── TaskRequest.java
│   ├── TaskPatchRequest.java
│   ├── TaskStatusRequest.java
│   └── TaskResponse.java
├── entity/
│   ├── Task.java
│   ├── TaskStatus.java
│   └── TaskPriority.java
├── exception/
│   ├── ApiErrorResponse.java
│   ├── ApiFieldError.java
│   ├── GlobalExceptionHandler.java
│   └── TaskNotFoundException.java
├── repository/
│   └── TaskRepository.java
├── service/
│   └── TaskService.java
└── specification/
    └── TaskSpecification.java
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

### Task Status

`PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

### Task Priority

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
  "description": "Practice Docker Compose with Spring Boot",
  "priority": "HIGH",
  "dueDate": "2026-09-20T10:00:00"
}
```

## API Documentation

The API is documented using OpenAPI and Swagger UI.

After starting the application, open:

``` text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON specification:

``` text
http://localhost:8080/v3/api-docs
```

OpenAPI YAML specification:

``` text
http://localhost:8080/v3/api-docs.yaml
```

Swagger UI allows you to explore and test the API endpoints directly
from the browser.

## Environment Variables

Create a `.env` file in the project root.

Use `.env.example` as a template:

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

On macOS/Linux:

``` bash
./gradlew bootRun
```

The API is available at `http://localhost:8080`.

Flyway automatically applies the required database migrations during
application startup.

## Running with Docker Compose

Build the Spring Boot JAR:

``` powershell
.\gradlew clean bootJar
```

Start Spring Boot and PostgreSQL:

``` bash
docker compose up -d --build
```

Check running containers:

``` bash
docker compose ps
```

View application logs:

``` bash
docker compose logs -f app
```

View PostgreSQL logs:

``` bash
docker compose logs -f postgres
```

Stop the application:

``` bash
docker compose down
```

PostgreSQL data is stored in a Docker volume and remains available after
`docker compose down`.

> **Warning:** `docker compose down -v` also removes the PostgreSQL
> volume and its stored data.

## Database Migrations

Database schema changes are managed using Flyway.

Migration files are stored in:

``` text
src/main/resources/db/migration/
```

Hibernate is configured with `ddl-auto: validate`, so Flyway manages the
schema while Hibernate validates the entity mappings.

## Validation

Request DTOs use Jakarta Bean Validation for required fields, field
sizes, priorities, and future due dates.

Invalid requests return structured `400 Bad Request` responses.

## Exception Handling

The application uses centralized exception handling with
`@RestControllerAdvice`.

Handled cases include:

-   Validation errors
-   Task not found
-   Invalid enum values in JSON request bodies
-   Invalid enum values in query parameters
-   Malformed request bodies

## Testing

Service-layer unit tests are implemented using JUnit and Mockito.

Run tests on Windows:

``` powershell
.\gradlew test
```

On macOS/Linux:

``` bash
./gradlew test
```

Controller tests, integration tests, and Testcontainers are planned
improvements.

## Useful Docker Commands

Start:

``` bash
docker compose up -d
```

Start and rebuild:

``` bash
docker compose up -d --build
```

Check containers:

``` bash
docker compose ps
```

View logs:

``` bash
docker compose logs -f app
```

Stop:

``` bash
docker compose down
```

Remove unused Docker images:

``` bash
docker image prune -a
```

## What I Learned

-   Spring Boot project setup
-   REST API development
-   Controller-Service-Repository architecture
-   DTO and entity separation
-   Spring Data JPA
-   PostgreSQL integration
-   Flyway database migrations
-   Jakarta Bean Validation
-   Global exception handling
-   HTTP status codes
-   CRUD operations
-   Partial updates with PATCH
-   Pagination and sorting
-   Dynamic filtering with JPA Specifications
-   Transaction management
-   Application logging
-   Unit testing with JUnit and Mockito
-   Environment variables and externalized configuration
-   OpenAPI and Swagger UI
-   Docker fundamentals
-   Dockerizing Spring Boot
-   Docker Compose
-   Container networking
-   Docker volumes and PostgreSQL persistence

## Future Improvements

-   Controller / Web MVC tests
-   Integration tests
-   Testcontainers
-   Authentication and authorization
-   User-owned task data
-   Cloud deployment

## License

This project is for learning and portfolio purposes.
