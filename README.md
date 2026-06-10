# 🎫 HelpDesk Ticket Management System

A role-based HelpDesk Ticket Management System built using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

The system allows Users to create support tickets, Agents to manage assigned tickets, and Admins to assign and monitor tickets through RESTful APIs.

---

## 🚀 Features

### 👤 User
- Register account
- Login with email and password
- Create support tickets
- View own tickets

### 👨‍💼 Agent
- View assigned tickets
- Update ticket status

### 👑 Admin
- View all tickets
- Assign tickets to agents

---

## 🏗️ Architecture

```text
Client (Postman)
       │
       ▼
 Controller
       │
       ▼
   Service
       │
       ▼
 Repository
       │
       ▼
 MySQL Database
```

This project follows a layered architecture to separate business logic, database operations, and API handling.

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- Maven

### Database
- MySQL

### Tools
- IntelliJ IDEA
- Postman
- Git & GitHub

---

## 📂 Project Structure

```text
src/main/java/org/helpdesk

├── controller
│   ├── AuthController
│   └── TicketController
│
├── dto
│   ├── TicketRequest
│   └── StatusRequest
│
├── entity
│   ├── User
│   ├── Ticket
│   ├── Role
│   └── TicketStatus
│
├── repository
│   ├── UserRepository
│   └── TicketRepository
│
├── service
│   ├── AuthService
│   └── TicketService
│
├── exception
│   ├── GlobalExceptionHandler
│   ├── UserNotFoundException
│   ├── TicketNotFoundException
│   └── UnauthorizedActionException
│
└── HelpdeskApplication
```

---

## 🗄️ Database Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticket_system
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# 📌 API Endpoints

## Authentication

### Register User

```http
POST /auth/register
```

Request:

```json
{
  "name": "Saravanan",
  "email": "saravanan@gmail.com",
  "password": "123456",
  "role": "USER"
}
```

---

### Login User

```http
POST /auth/login
```

Request:

```json
{
  "email": "saravanan@gmail.com",
  "password": "123456"
}
```

---

## Ticket Management

### Create Ticket

```http
POST /tickets/create/{userId}
```

Request:

```json
{
  "title": "Login Issue",
  "description": "Unable to login"
}
```

---

### View User Tickets

```http
GET /tickets/user/{userId}
```

---

### View Assigned Tickets

```http
GET /tickets/agent/{agentId}
```

---

### Assign Ticket To Agent

```http
PUT /tickets/{ticketId}/assign/{agentId}/{adminId}
```

---

### Update Ticket Status

```http
PUT /tickets/{ticketId}/status/{agentId}
```

Request:

```json
{
  "status": "IN_PROGRESS"
}
```

Available Status Values:

```text
OPEN
IN_PROGRESS
RESOLVED
CLOSED
```

---

### View All Tickets

```http
GET /tickets/admin/{adminId}
```

---

## 📦 Sample Response

```json
{
  "id": 1,
  "title": "Login Issue",
  "description": "Unable to login",
  "status": "OPEN"
}
```

---

## ✅ Validation

Implemented using Jakarta Bean Validation.

Examples:

```java
@NotBlank
@Email
@Size(min = 6, max = 20)
```

Benefits:

- Prevents invalid data
- Automatic request validation
- Cleaner controller code

---

## ⚠️ Global Exception Handling

Implemented using:

```java
@RestControllerAdvice
@ExceptionHandler
```

Custom Exceptions:

- UserNotFoundException
- TicketNotFoundException
- UnauthorizedActionException

Provides consistent API error responses.

Example:

```json
{
  "message": "Ticket not found."
}
```

---

## 🔄 Transaction Management

Implemented using:

```java
@Transactional
```

Used in:

- assignTicketToAgent()
- updateTicketStatus()

Ensures automatic rollback if an operation fails.

---

## 🧠 Key Concepts Implemented

- RESTful API Design
- Layered Architecture
- Dependency Injection
- Repository Pattern
- DTO Pattern
- Spring Data JPA
- Hibernate ORM
- Bean Validation
- Global Exception Handling
- Transaction Management
- Role-Based Access Control (RBAC)

---

## 🚀 Run Locally

Clone the repository:

```bash
git clone https://github.com/i-saravanan/helpdesk_system.git
```

Move into the project directory:

```bash
cd helpdesk_system
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

Application starts at:

```text
http://localhost:8080
```

---

## 📈 Current Status

### Version 1.0

Implemented:

- User Registration
- User Login
- Ticket Creation
- Ticket Assignment
- Ticket Status Updates
- Validation
- Exception Handling
- Transaction Management

---

## 🔮 Future Enhancements

- JWT Authentication
- Spring Security
- BCrypt Password Encryption
- Swagger / OpenAPI Documentation
- Pagination & Sorting
- Search & Filtering
- Audit Logging
- Docker Containerization
- AWS Deployment

---

## 👨‍💻 Author

### Saravanan I

- Java Backend Developer
- Spring Boot Learner

GitHub:
https://github.com/i-saravanan

LinkedIn:
www.linkedin.com/in/saravanan-i

---

⭐ If you found this project useful, consider giving it a star.
