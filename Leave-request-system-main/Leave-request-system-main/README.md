# Online Leave Request Management System

A modern, full-stack web application designed to streamline the employee leave application and management workflow. The system features a secure role-based access control system for employees and administrators.

---

## 🚀 Architecture & Tech Stack

The application is built using a modern decoupled architecture:

### 🖥️ Frontend (React & Vite)
- **Framework:** React 19 & Vite (for lightning-fast development and building)
- **Routing:** React Router DOM (v7)
- **API Client:** Axios (configured with interceptors for JWT authorization)
- **Icons:** Lucide React
- **Styling:** Vanilla CSS (curated, modern dashboard layouts with harmonized colors and responsive flex/grid layouts)

### ⚙️ Backend (Spring Boot & Java)
- **Framework:** Spring Boot 3.x (Java 17)
- **Security:** Spring Security & JWT (JSON Web Tokens) for stateless authentication
- **Database / ORM:** MySQL & Spring Data JPA (Hibernate)
- **Database Migration:** Pre-loaded seed data using SQL scripts

---

## 🌟 Key Features

### 🔐 Authentication & Roles
- Secure User Registration and Login with JWT authentication.
- Role-based authorization: **EMPLOYEE** and **ADMIN**.

### 💼 Employee Portal
- **Dashboard:** At-a-glance metrics showing total applied leaves, approved leaves, pending requests, and rejected requests.
- **Apply for Leave:** Form to request leave by specifying leave type (Casual, Sick, Vacation, Emergency, etc.), start/end dates, and a description reason.
- **My Leaves:** Tabular list of all applied leaves with their current real-time status and administrator feedback remarks.

### 👑 Admin Portal
- **Admin Dashboard:** Overview of total employee strength, total leave requests, and pending approval queues.
- **Manage Leaves:** Dedicated panel to view all pending leave applications from all employees.
- **Approval Actions:** Action buttons to Approve or Reject a leave request along with a custom text remark/comment sent back to the employee.

---

## 📂 Project Structure

```
Online Leave Request System/
├── backend/                  # Spring Boot API Application
│   ├── src/                  # Java source files and resources
│   ├── pom.xml               # Maven configuration & dependencies
│   └── mvnw.cmd              # Maven wrapper
├── frontend/                 # React + Vite Single Page Application (SPA)
│   ├── src/                  # React components, pages, context, and styles
│   ├── package.json          # Node dependencies & scripts
│   └── vite.config.js        # Vite configuration
└── .gitignore                # Global git ignore file
```

---

## ⚙️ Installation & Getting Started

### 📋 Prerequisites
- **Java Development Kit (JDK):** Version 17 or higher
- **Node.js:** Version 18 or higher & npm
- **Database:** MySQL Server running locally

---

### 1️⃣ Database Setup
1. Open your MySQL client (Command Line, Workbench, or phpMyAdmin).
2. Create a new database named `leave_management_db`:
   ```sql
   CREATE DATABASE leave_management_db;
   ```
3. Update database credentials in the backend configuration if necessary:
   - Edit [application.properties](file:///c:/Users/prash_m0k620w/Downloads/Online%20Leave%20Request%20System/backend/src/main/resources/application.properties):
     ```properties
     spring.datasource.username=YOUR_MYSQL_USERNAME
     spring.datasource.password=YOUR_MYSQL_PASSWORD
     ```

---

### 2️⃣ Run the Backend (Spring Boot)
1. Open a terminal and navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Start the Spring Boot application using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(On Windows Command Prompt, use `mvnw.cmd spring-boot:run`)*
3. The server will run on `http://localhost:8080`.

---

### 3️⃣ Run the Frontend (React)
1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install the required Node packages:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. The React application will open on `http://localhost:5173`.
