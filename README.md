# Task Management API

## Overview
This API provides task management functionality where users can create tasks, add comments, and assign tasks to other users. The application implements JWT-based authentication and role-based authorization.

## Authentication
- All endpoints require authentication via JWT except:
    - `/api/accounts` (User Registration)
    - `/api/auto/token` (JWT Token Retrieval)
- The JWT token should be passed in the `Authorization` header as a Bearer token.

## User Roles
- **USER**: Can create tasks, delete their own tasks, add comments, delete their own comments, and delete their own account.
- **ADMIN**: Can perform all actions, including deleting any user's tasks, comments, or accounts.

## Endpoints

### 1. **User Authentication & Registration**
- **POST** `/api/accounts`
    - Register a new user with email and password.
    - **Request Body:**
      ```json
      {
        "email": "user@example.com",
        "password": "password123"
      }
      ```
    - **Response:** 200 OK

- **POST** `/api/auto/token`
    - Retrieve JWT token using Basic Authentication.
    - **Request Header:** `Authorization: Basic base64(username:password)`
    - **Response:**
      ```json
      {
        "token": "your.jwt.token"
      }
      ```

### 2. **Task Management**
- **POST** `/api/tasks`
    - Create a new task.
    - **Request Body:**
      ```json
      {
        "title": "New Task",
        "description": "Task details",
        "status": "Pending"
      }
      ```
    - **Response:** 200 OK

- **GET** `/api/tasks`
    - Retrieve tasks based on optional query parameters `author` and `assignee`.
    - **Query Parameters:**
        - `author` (optional)
        - `assignee` (optional)
    - **Response:**
      ```json
      [
        {
          "id": 1,
          "title": "Task 1",
          "description": "Details",
          "author": "user@example.com",
          "assignee": "other@example.com",
          "status": "Pending"
        }
      ]
      ```

- **PUT** `/api/tasks/{taskId}/assign`
    - Assign a task to a user by email.
    - **Request Body:**
      ```json
      {
        "email": "assignee@example.com"
      }
      ```
    - **Response:** 200 OK

- **PUT** `/api/tasks/{taskId}/status`
    - Update task status.
    - **Request Body:**
      ```json
      {
        "status": "Completed"
      }
      ```
    - **Response:** 200 OK

- **DELETE** `/api/tasks/{taskId}`
    - Delete a task (Only the creator or an ADMIN can delete a task).
    - **Response:** 200 OK

### 3. **Task Comments**
- **POST** `/api/tasks/{taskId}/comments`
    - Add a comment to a task.
    - **Request Body:**
      ```json
      {
        "text": "This is a comment."
      }
      ```
    - **Response:** 200 OK

- **GET** `/api/tasks/{taskId}/comments`
    - Retrieve all comments for a specific task.
    - **Response:**
      ```json
      [
        {
          "id": 1,
          "text": "This is a comment.",
          "author": "user@example.com",
          "timestamp": "2025-03-23T10:00:00"
        }
      ]
      ```

- **DELETE** `/api/tasks/comments/{commentId}`
    - Delete a comment (Only the author or an ADMIN can delete a comment).
    - **Response:** 200 OK

### 4. **User Management**
- **DELETE** `/api/users/{userId}`
    - Delete a user account (Users can delete their own account, ADMINs can delete any account).
    - **Response:** 200 OK

## Error Handling
- **400 Bad Request:** If the request is malformed or missing required fields.
- **403 Forbidden:** If the user is not authorized to perform the operation.
- **404 Not Found:** If the resource does not exist.
- **500 Internal Server Error:** If an unexpected error occurs.

## Tech Stack
- **Java 21**
- **Spring Boot 3.4.4**
- **H2 Database** (for development and testing)
- **JWT Authentication**

## Notes
- Ensure that the JWT token is included in the `Authorization` header for all protected endpoints.
- Use Basic Authentication to obtain the JWT token before making requests to secured endpoints.
