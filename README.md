# 🎉 Event Management System (EMS)
A complete web-based platform designed for **Eternal Events** to centralize and automate event reservations.  
The system provides an intuitive interface for customers and an advanced dashboard for administrators to manage all bookings, services, and pricing.

---

## 🚀 Overview
The **Event Management System (EMS)** solves the problems caused by manual event booking processes, such as:

- No centralized view for reservations  
- Difficulty modifying or cancelling bookings  
- Communication errors  
- Slow manual operations  

The EMS provides:

- Online booking  
- Customer dashboard  
- Admin control panel  
- Automated notifications (email/SMS)  
- Service & pricing management  
- Error-free digital workflow  

---

## ⭐ Features

### 👤 Customer Features
- Create an account  
- Book events online  
- Modify or cancel reservations  
- View all previous and upcoming reservations  
- Receive confirmation & reminder notifications  

### 🛠️ Admin Features
- View all customer reservations  
- Edit reservation status (Pending → Confirmed/Cancelled)  
- Manage service packages & pricing  
- Send updates or notifications  
- Generate reports  

### 🔔 Automated System Features
- Time-based reminder notifications  
- Alerts for upcoming reservations  
- Error handling for double-booking  

---

## 🧱 System Architecture (MVC)

This project follows the **Model–View–Controller architecture** for clean separation of concerns.

### 🖥️ View
- Customer UI (booking, editing, cancelling)
- Admin UI (reservations, pricing, reports)

### 🎮 Controller
- Processing booking requests  
- Handling modifications & cancellations  
- Passing requests between UI and Model  

### 🧩 Model
- Handles data logic  
- Manages reservations, customers, services  
- Connects directly to PostgreSQL database  

### 🗄️ Database
Stores all:
- Users  
- Reservations  
- Services & packages  
- Reports  

---

## 🛡️ Role-Based Access Control (RBAC)

| Role | Permissions |
|------|-------------|
| **Customer** | Book, edit, cancel reservations + receive notifications |
| **Admin** | Manage reservations, pricing, services, reports |
| **System** | Automated reminders & notifications |

---

## 🛠️ Implementation Details

### 🧑‍💻 Technologies Used
- **Java** (IntelliJ)
- **PostgreSQL** (inside Docker)
- **MVC Architecture**
- **Email/SMS Notification Services**
- **RBAC Access Separation**

### 📦 Core Modules
- Booking Module  
- User Profile & Dashboard  
- Reservation Management  
- Service Packages Manager  
- Notification System  

---

## 🧪 Testing

The system was fully tested using JUnit.  
### ✔ BookingEventTest
- Validates new booking creation  
- Checks linking users ⇄ bookings  
- Tests cancellation logic  

### ✔ ProfileAndReservationsTest
- Validates profile updates  
- Ensures reservation history is correct  

### ✔ ReservationManagementTest
- Admin views & updates reservation states  
- Ensures notification triggers  

### ✔ ServicePackagesTest
- Tests adding/editing service packages  
- Price updates & activation toggles  

---

## 👥 Team Members
| Name | ID | Role |
|------|------|------|
| **Faisal Saad Aljuaid** | 2336231 | Requirements & Documentation |
| **Mohand Mohammed Saleh** | 2343215 | System Architecture |
| **Abdulelah Mohammed Moqbil** | 2338164 | Backend & Database |
| **Ahmed Yousef Alsefari** | 2338720 | User Interface Development |
| **Ibrahem Nezar Kasem Al-Shikh** | 2343158 | Authentication & Notifications |

---

## 🔮 Future Enhancements
- Integrate **online payment gateway**  
- Advanced analytics dashboard  
- Mobile application (iOS & Android)  
- Customer reviews & photo uploads  
- Vendor integration APIs  
- Push notifications for mobile app  

---

## 🚀 How to Run the Event Management System (EMS)

Follow the steps below to set up and run the EMS application on your machine.

* * * * *

1️⃣ Requirements
----------------

Make sure the following tools are installed:

-   Java 17+

-   IntelliJ IDEA (or any Spring Boot-compatible IDE)

-   Docker Desktop

-   PostgreSQL Docker Image

-   Git

* * * * *

2️⃣ Clone the Repository
------------------------

Open the terminal and run:

git clone https://github.com/Ahmed-Alsefari/Event-Management-System-EMS.git\
cd Event-Management-System-EMS

* * * * *

3️⃣ Start the PostgreSQL Database (Docker)
------------------------------------------

### Option A --- Using docker-compose (if included in the project)

docker-compose up -d

### Option B --- Run PostgreSQL manually

docker run --name EMS_DB\
-e POSTGRES_USER=postgres\
-e POSTGRES_PASSWORD=12345\
-e POSTGRES_DB=ems_db\
-p 5432:5432\
-d postgres

* * * * *

4️⃣ Configure Database Connection
---------------------------------

Open `application.properties` and ensure these settings are correct:

spring.datasource.url=jdbc:postgresql://localhost:5432/ems_db\
spring.datasource.username=postgres\
spring.datasource.password=12345

spring.jpa.hibernate.ddl-auto=update\
spring.jpa.show-sql=true

Update the username/password if you use different credentials.

* * * * *

5️⃣ Run the Application
-----------------------

### Option A --- Run from IntelliJ IDEA

1.  Open the project in IntelliJ

2.  Navigate to: `src/main/java/com/ems/EmsApplication.java`

3.  Click **Run ▶**

### Option B --- Run from terminal

./mvnw spring-boot:run

* * * * *

6️⃣ Access the Application
--------------------------

Once the server is running, open your browser:

### Customer Interface

http://localhost:8080/

### Admin Dashboard

http://localhost:8080/admin

* * * * *

7️⃣ Default Login Accounts (Example)
------------------------------------

Use any accounts stored in your database.

Admin Example:\
Email: admin@ems.com\
Password: admin123

User Example:\
Email: user@ems.com\
Password: user123

(Replace these with your actual data if different.)

* * * * *

8️⃣ Run Automated Tests (JUnit)
-------------------------------

### From IntelliJ:

Right-click the **test** folder → **Run All Tests**

### From terminal:

./mvnw test


© 2025 Event Management System (EMS)
