# 🎫 Helpdesk System

A RESTful Helpdesk System built using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

This project allows users to create support tickets, agents to manage assigned tickets, and administrators to assign tickets to agents.

---

## 🚀 Features

### 👤 User Features
- Register new users
- Login with email and password
- Create support tickets
- View own tickets

### 👨‍💼 Agent Features
- View assigned tickets
- Update ticket status

### 👑 Admin Features
- View all tickets
- Assign tickets to agents

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok (Optional)
- Postman (API Testing)

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

## 🗄️ Database

MySQL Database

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticket_system
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 📌 API Endpoints

### Authentication

#### Register User

```http
POST /auth/register
```

Request Body

```json
{
  "name": "Saravanan",
  "email": "saravanan@gmail.com",
  "password": "123456",
  "role": "USER"
}
```

---

#### Login User

```http
POST /auth/login
```

Request Body

```json
{
  "email": "saravanan@gmail.com",
  "password": "123456"
}
```

---

### Helpdesk

#### Create Ticket

```http
POST /tickets/create/{userId}
```

Request Body

```json
{
  "title": "Login Issue",
  "description": "Unable to login to the system"
}
```

---

#### View User Tickets

```http
GET /tickets/user/{userId}
```

---

#### View Assigned Tickets

```http
GET /tickets/agent/{agentId}
```

---

#### Assign Ticket To Agent

```http
PUT /tickets/{ticketId}/assign/{agentId}/{adminId}
```

---

#### Update Ticket Status

```http
PUT /tickets/{ticketId}/status/{agentId}
```

Request Body

```json
{
  "status": "IN_PROGRESS"
}
```

Possible Status Values

```text
OPEN
IN_PROGRESS
RESOLVED
CLOSED
```

---

#### View All Tickets

```http
GET /tickets/admin/{adminId}
```

---

## ✅ Validation

Implemented using Bean Validation.

Examples:

```java
@NotBlank
@Email
@Size(min = 6, max = 20)
```

Validation errors are automatically returned to the client.

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

Provides consistent error responses across all APIs.

---

## 🔄 Transaction Management

Implemented using:

```java
@Transactional
```

Used in:

- assignTicketToAgent()
- updateTicketStatus()

Ensures automatic rollback when failures occur.

---

## 🎯 Learning Outcomes

This project demonstrates:

- REST API Development
- Layered Architecture
- Spring Boot Fundamentals
- Dependency Injection
- Spring Data JPA
- Hibernate ORM
- MySQL Integration
- Validation
- Exception Handling
- Transaction Management
- DTO Usage
- Role-Based Access Logic

---

## 🚀 Future Enhancements

- JWT Authentication
- Spring Security
- Password Encryption (BCrypt)
- Swagger/OpenAPI Documentation
- Pagination & Sorting
- Search & Filtering
- Audit Logging
- Docker Deployment
- AWS Deployment

---

## 👨‍💻 Author

**Saravanan I**

- Java Backend Developer
- Spring Boot Enthusiast