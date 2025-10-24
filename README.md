# School Management System (SMS-02)

A comprehensive Java-based School Management System designed for modern educational institutions. This project features intuitive role-based dashboards, robust CRUD modules, and seamless management for students, faculty, staff, and administrators.

## Technologies Used

- Java 21
- Springboot 3.x
- Spring Security 6 + JWT
- MySQL
- REST APIs
- GCP server
- Docker
- Spring AOP
- Jasper Report
- CQRS pattern 
---
## Features

- **Multi-role Authentication:**  
  - Separate login/signup for Admin, Student, and Faculty users.
  - Secure password handling and role switching (where applicable).

- **Admin Dashboard:**  
  - Admissions Management (UG/PG, detailed student info, multi-entry forms)
  - Library Module (Books, Librarian assignments)
  - Hostel Management (Room allocation, occupancy)
  - Staff Management (CRUD, designations, department mapping)
  - Student & Faculty overview

- **Student Dashboard:**  
  - Personalized welcome and info banner
  - Course enrollment, timetable, and attendance tracking
  - Fee payment status & downloads
  - Upcoming exams, assignments, and results
  - Downloadable report cards & invoices
  - Notices and circulars

- **Faculty Dashboard:**  
  - Assigned classes & subjects
  - Scheduling and attendance tools
  - Assignment and exam management
  - Student performance analytics
  - Notice board and communication tools

- **Parent Dashboard:**  
  - Real-time student information
  - Attendance records and academic status
  - Fee payment and results overview

---

## System Modules

### 1. Authentication Pages

#### User Pages (Student/Faculty/parent/Admin)
- **Login:** Email, Password, Role Switch
- **Signup:** Name, Email, Mobile, Password, Confirm Password

#### Admin Page
- **Login:** Email, Password (Role fixed as Admin)

---

### 2. Admin Dashboard

#### 2.1 Admissions Module
- **Create New Admission:**  
  - Personal Details: Name, DOB, Gender, Blood Group, Nationality, Religion, Aadhar, Email, Mobile, Profile Photo
  - Academic: Level (UG/PG), Course (mandatory), Parent/Guardian (optional)
  - Address: Multiple entries supported
  - Previous Education: Multiple entries supported
  - Hostel Option: Room allocation if opted

- **List Admissions:**  
  - Filterable table view (Course, Level, Date, etc.)
  - Actions: View, Edit, Delete

#### 2.2 Library Module
- **Manage Books:** CRUD (Name, Author, ISBN, Category, Availability)
- **Assign Librarian:** Link staff to library roles

#### 2.3 Hostel Module
- **Room Allocation:** Assign rooms by Student ID
- **Occupancy View:** Current room status

#### 2.4 Staff Management
- **CRUD:** Name, Email, Mobile, Designation, Department
- **Designations:** CLERK, LIBRARIAN, LAB_ASSISTANT, SECURITY, PEON, ACCOUNTANT
- **Assign Professors:** Map to departments

---

### 3. User Dashboards

#### 3.1 Student Dashboard

| Section               | Description                                                                                 |
|-----------------------|---------------------------------------------------------------------------------------------|
| Welcome Banner        | EG: "Welcome Asha (UG - B.Tech 2023 - Section A)"                                          |
| Subjects Enrolled     | List of courses, teachers, syllabus PDFs                                                    |
| Attendance            | Percentage, subject-wise monthly chart/bar                                                  |
| CGPA/Marks            | Current CGPA or average marks                                                              |
| Fee Status            | Paid/Unpaid, invoice download, online payment                                              |
| Upcoming Exams/Tasks  | Next exams and assignments, with deadlines and status (Pending, Submitted, Graded)         |
| Timetable             | Daily/weekly view, timings, subjects, teachers                                             |
| Notices/Announcements | Latest circulars from admin or teachers                                                    |
| Assignments/Homework  | List, submission status, upload option                                                     |
| Results & Marks       | View/download report card                                                                  |
| Profile               | Personal details, request updates                                                          |

#### 3.2 Faculty Dashboard

| Section                  | Description                                          |
|--------------------------|------------------------------------------------------|
| Today’s Schedule         | View lectures and assignments for the day            |
| Assigned Classes/Subjects| List of batches and subjects taught                  |
| Student Lists            | Roster for each class                                |
| Attendance Sheet         | Daily marking, export options                        |
| Assignment/Exam Mgmt     | Add, edit, grade assignments and exams               |
| Performance Analytics    | Student performance graphs                           |
| Notice Board             | Announcements from admin                             |

#### 3.3 Parent Dashboard

| Section            | Description                                 |
|--------------------|---------------------------------------------|
| Student Profile    | Real-time info, academic and hostel status  |
| Attendance Record  | Monthly/subject-wise breakdown              |
| Fee Payment        | Status, download invoices, pay online       |
| Results            | Latest marks, exam results, report cards    |

---

  


