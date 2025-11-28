# 📅 Event Management System (EMS)

**Group A+ | Faculty of Computing & Information Technology (FCIT) | King Abdulaziz University**

> [cite_start]**Project Goal:** To transform the event management industry's inefficient manual processes into a streamlined, digital experience through a centralized web-based platform[cite: 65, 67].

---

## 👥 Team Members

| Name | Student ID |
| :--- | :--- |
| **Faisal Saad Aljuaid** | 2336231 |
| **Mohand Mohammed Saleh** | 2343215 |
| **Abdulelah Mohammed Moqbil** | 2338164 |
| **Ahmed Yousef Alsefari** | 2338720 |
| **Ibrahem Nezar Kasem Al-Shikh** | 2343158 |

---

## 📝 Problem Statement

[cite_start]The project addresses specific issues faced by our partner, "Eternal Events," regarding manual operations[cite: 58]:
* [cite_start]**Operational Inefficiency:** Existing systems rely on phone calls and in-person visits, leading to fragmented operations[cite: 59].
* [cite_start]**Customer Frustration:** Lack of self-service options forces customers to rely on staff availability for simple tasks like viewing or cancelling reservations[cite: 61].
* [cite_start]**Management Blind Spots:** Management lacks a real-time view of reservations and revenue, making reporting difficult[cite: 62].
* [cite_start]**Communication Gaps:** Manual confirmations often lead to scheduling conflicts and missed reminders[cite: 63].

---

## 🏗️ System Architecture & Design

### Architectural Pattern
[cite_start]The system is built on a **Layered MVC (Model-View-Controller)** architecture to ensure maintainability and scalability[cite: 277].
* [cite_start]**Model:** Manages business logic and interacts directly with the PostgreSQL database[cite: 278].
* [cite_start]**View:** A responsive, bilingual (Arabic/English) user interface[cite: 279].
* [cite_start]**Controller:** Processes user requests and updates the Model/View accordingly[cite: 280].
* [cite_start]**Security Layer (RBAC):** A specialized Role-Based Access Control layer securely separates Customer and Admin functionalities[cite: 281].

### Design Process
The design phase followed a user-centered approach:
1.  [cite_start]**Ideation:** Brainstorming core features and usability goals (clean layout, intuitive navigation)[cite: 80].
2.  [cite_start]**Wireframing:** Created text-based wireframes with annotations to outline key screens (Home, Services, Booking Flow)[cite: 83].
    * [cite_start]*Strategy:* Explored alternative flows such as "Horizontal Stepper" vs. "Vertical Sidebar" to determine the best user experience[cite: 89].
3.  [cite_start]**Prototyping:** Translated wireframes into high-fidelity interactive prototypes using Figma[cite: 222].

---

## ✨ Functional Features

### 👤 Customer Module
[cite_start]Designed for simplicity and transparency[cite: 75]:
* **User Authentication:** Secure registration and login.
* [cite_start]**Service Discovery:** Search bar with filters for [Event Type], [Date], and [Price][cite: 95, 114].
* [cite_start]**Booking Wizard:** A multi-step progress flow (Service -> Details -> Payment -> Done) to reduce cognitive load[cite: 86, 131].
* **Profile Management:**
    * [cite_start]View booking history and status (Confirmed/Pending)[cite: 153].
    * [cite_start]Edit or cancel reservations directly from the dashboard[cite: 154].
* [cite_start]**Notifications:** Automated alerts for booking confirmations and event reminders [cite: 158-159].

### 🛡️ Administrator Module
[cite_start]Designed for powerful oversight and management[cite: 76]:
* **Analytics Dashboard:**
    * [cite_start]Widgets for "Bookings Today", "Revenue", "Pending", and "Cancelled" [cite: 171-173].
    * [cite_start]Visual graphs showing booking trends per month[cite: 175].
* **Reservation Management:**
    * [cite_start]Editable tables to Confirm, Cancel, or Edit bookings without leaving the page[cite: 200, 204].
    * [cite_start]Filter reservations by Date, Service, or Customer[cite: 193].
* [cite_start]**Service Package Control:** Full CRUD (Create, Read, Update, Delete) capabilities to adjust service offerings and pricing[cite: 271].
* [cite_start]**Alerts:** Notifications for urgent issues like pending payments[cite: 182].

---

## 🛠️ Technical Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend** | Java | [cite_start]Core application logic[cite: 283]. |
| **Frontend** | HTML, CSS, JavaScript | [cite_start]Responsive and bilingual interface[cite: 284]. |
| **Database** | PostgreSQL | [cite_start]Data persistence, containerized with Docker[cite: 285]. |
| **IDE** | IntelliJ IDEA | [cite_start]Primary development environment[cite: 286]. |
| **Design** | Figma | [cite_start]High-fidelity prototyping[cite: 222]. |
| **VCS** | GitHub | [cite_start]Source control and collaboration[cite: 287]. |

---

## 🧪 Testing & Quality Assurance

### Testing Methodology
A comprehensive testing suite was implemented to ensure system reliability:
* [cite_start]**`BookingEventTest`:** Validates the core event booking logic and flow[cite: 290].
* [cite_start]**`ProfileAndReservationsTest`:** Ensures user data and booking history are accurate[cite: 291].
* [cite_start]**`ReservationManagementTest`:** Verifies admin controls and notification triggers[cite: 292].
* [cite_start]**`ServicePackagesTest`:** Confirms admins can successfully manage service packages[cite: 293].

### HCI Principles Applied
* [cite_start]**Visibility & Feedback:** Users receive immediate confirmation messages and see progress bars during booking[cite: 308].
* [cite_start]**Consistency:** Uniform card layouts and navigation structures across the application[cite: 309].
* [cite_start]**Error Prevention:** Validation checks (e.g., preventing double-booking) and clear confirmation prompts[cite: 310].

---

## 🤖 AI Tools Integration

[cite_start]The project leveraged multiple AI agents to accelerate development[cite: 226]:

1.  **ChatGPT:**
    * [cite_start]Used for accelerated ideation and generating text-based wireframes with annotations [cite: 228-229].
    * [cite_start]Assisted in exploring layout flows (Stepper vs. Card Flow)[cite: 230].
2.  **Gemini:**
    * [cite_start]Provided perspectives on UI/UX patterns and user flow optimization [cite: 233-234].
    * [cite_start]Generated content suggestions for notifications and interface elements[cite: 235].
3.  **DeepSeek:**
    * [cite_start]Supported code structure planning and backend architecture design[cite: 237].
    * [cite_start]Assisted in troubleshooting technical challenges during prototyping[cite: 239].

---

## 📸 Screenshots

*(Please upload the images from the project report to an `assets` folder and link them here)*

| **Customer Home** | **Service Selection** |
|:---:|:---:|
| ![Home](path/to/image) | ![Services](path/to/image) |
| [cite_start]*Hero banner and featured services [cite: 224]* | [cite_start]*Filterable service cards [cite: 224]* |

| **Booking Wizard** | **Admin Dashboard** |
|:---:|:---:|
| ![Booking](path/to/image) | ![Dashboard](path/to/image) |
| [cite_start]*Multi-step reservation flow [cite: 224]* | [cite_start]*Analytics widgets and charts [cite: 225]* |

---

## 🚀 Setup Instructions

1.  **Clone the Repo:**
    ```bash
    git clone [https://github.com/Ahmed-Alsefari/Event-Management-System-EMS-.git](https://github.com/Ahmed-Alsefari/Event-Management-System-EMS-.git)
    ```
2.  **Database Configuration:**
    * Ensure Docker is running.
    * Deploy the PostgreSQL container.
3.  **Build & Run:**
    * Open the project in IntelliJ IDEA.
    * Run the main application class.
4.  **Access:**
    * Visit `http://localhost:8080`.
    * [cite_start]**Demo Customer:** User: `user` / Pass: `123456`[cite: 224].
    * [cite_start]**Demo Admin:** User: `admin` / Pass: `123456`[cite: 224].

---

© 2025 Event Management System.
