# Student Management System

This project is a Java-based Student Management System developed using Swing for the user interface and MySQL for the backend database. It provides a simple and efficient way to manage student records. 
The system allows users to add new students, update existing information, delete records, and view all stored student details in a tabular format. All database operations are performed using JDBC to ensure
smooth and reliable data handling.

## Features

- Add new student records  
- Update existing student details  
- Delete student information  
- View all student records in a structured table  
- Java Swing-based user interface  
- JDBC connectivity with MySQL  

## Project Structure

src/
├── Main.java
├── model/
│ └── Student.java
├── dao/
│ ├── DBConnection.java
│ └── StudentDAO.java
└── ui/
└── SwingAdminDashboard.java


## How to Run

1. Install Java and MySQL on your system.  
2. Create a MySQL database and update the connection details in `DBConnection.java`.  
3. Open the project in an IDE such as IntelliJ IDEA, Eclipse, or NetBeans.  
4. Build and run the project to launch the application.

## Future Enhancements

- User login and authentication  
- Search and filter features  
- Exporting records to Excel or PDF  
- Web-based version of the system  

