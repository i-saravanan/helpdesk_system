# 🎟️ Role-Based Ticket Management System

## 📌 Overview

A backend system built using **Java, Hibernate, and MySQL** that simulates a real-world helpdesk platform with role-based access control.

Users can create tickets, admins assign them, and agents update their status — all enforced through proper authorization logic.

---

## 🏗️ Architecture

```
Main (Console UI)
   ↓
Service Layer (Business Logic + Role Validation)
   ↓
DAO Layer (Database Operations)
   ↓
Hibernate ORM
   ↓
MySQL Database
```

---

## ⚙️ Tech Stack

* Java
* Hibernate (ORM)
* MySQL
* Maven

---

## 👥 Roles & Features

### 👤 User

* Create tickets
* View own tickets

### 🧑‍💻 Agent

* View assigned tickets
* Update ticket status

### 👑 Admin

* View all tickets
* Assign tickets to agents

---

## 🔁 Ticket Workflow

```
OPEN → IN_PROGRESS → RESOLVED
```

---

## 🔐 Key Concepts Implemented

* Role-Based Access Control (RBAC)
* Layered Architecture (DAO → Service → UI)
* Hibernate ORM Mapping (`@ManyToOne`)
* HQL Queries
* Transaction Management
* Session Handling

---

## ▶️ How to Run

```bash
mvn clean install
mvn exec:java -Dexec.mainClass=org.helpdesk.Main
```

---

## 📂 Project Structure

```
src/main/java/org/helpdesk/
├── dao/
├── entity/
├── service/
├── util/
└── Main.java
```

---

## 💡 Future Improvements

* Convert to Spring Boot REST API
* Add JWT Authentication
* Build frontend
* Add pagination & filtering

---

## 🚀 Author

Saravanan I
