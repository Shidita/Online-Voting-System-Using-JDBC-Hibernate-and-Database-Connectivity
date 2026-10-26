# Online Voting System Using JDBC, Hibernate and Database Connectivity

## Project Overview

The **Online Voting System** is a Java-based full-stack web application developed to provide a simple, secure, and efficient platform for conducting online elections. The system allows registered users to log in, view available candidates, cast their vote, and view voting results.

An administrator can manage candidates and monitor the voting process through the admin panel.

## Technologies Used

- Java
- JSP
- Java Servlets
- HTML5
- CSS3
- JavaScript
- JDBC
- Hibernate ORM
- H2 Database
- Apache Tomcat 11
- Maven

## Main Features

### Voter Features

- User registration
- Secure user login
- Session-based authentication
- View available candidates
- Cast vote
- Prevention of duplicate voting
- View live voting results
- Logout

### Admin Features

- Admin authentication
- Add candidates
- Delete candidates
- View candidates
- View voting results
- Manage the voting process

## Database Connectivity

The project demonstrates two database connectivity approaches.

### JDBC

JDBC is used for user-related database operations such as:

- User registration
- User login
- Authentication
- User information retrieval

### Hibernate

Hibernate ORM is used for:

- Candidate management
- Voting operations
- Vote retrieval
- Result generation

## System Architecture

```text
Web Browser
     |
     v
JSP / HTML / CSS / JavaScript
     |
     v
Apache Tomcat 11
     |
     v
Java Servlets
     |
     v
DAO Layer
     |
     +------------------+
     |                  |
     v                  v
   JDBC             Hibernate
     |                  |
     +--------+---------+
              |
              v
         H2 Database
```

## Project Working

### 1. User Registration

A new user enters the required registration details. The request is processed by the registration servlet and the user information is stored in the H2 database using JDBC.

### 2. User Login

The user enters their login credentials. The LoginServlet validates the information using JDBC. After successful authentication, an HTTP session is created.

### 3. Candidate Selection

After login, the voter can view the list of available candidates and select a candidate for voting.

### 4. Vote Casting

The voter submits the selected candidate. The VoteServlet processes the request and VoteDAO uses Hibernate to store the vote in the database.

### 5. Duplicate Vote Prevention

Before storing a vote, the system checks whether the user has already voted. If a previous vote exists, the system rejects the second voting attempt.

### 6. Voting Results

The system retrieves voting information from the database and displays the current results. JavaScript is used to dynamically refresh the displayed results.

### 7. Admin Panel

The administrator can add or delete candidates and view the voting results through the administrator dashboard.

## Security Features

The application includes:

- Password hashing
- Session-based authentication
- Role-based access control
- Authentication filter
- Prepared statements
- Duplicate-vote prevention
- Session timeout

## Project Structure

```text
online-voting-system-jdbc-hibernate/
│
├── src/
│   └── main/
│       ├── java/
│       ├── resources/
│       └── webapp/
│
├── pom.xml
└── README.md
```

## Database

The project uses **H2 Database** for storing:

- Users
- Candidates
- Votes

JDBC and Hibernate are used to communicate with the database according to the operation being performed.

## Client-Server Communication

```text
Client / Browser
       |
       | HTTP Request
       v
Apache Tomcat 11
       |
       v
Java Servlet
       |
       v
JDBC / Hibernate
       |
       v
H2 Database
       |
       v
Result
       |
       v
HTTP Response
       |
       v
Client / Browser
```

## Testing

The application was tested for:

- User registration
- Valid login
- Invalid login
- Candidate display
- Vote casting
- Duplicate voting prevention
- Admin login
- Add candidate
- Delete candidate
- Logout
- Voting result display

## How to Run

1. Install Java.
2. Install Apache Tomcat 11.
3. Install Maven.
4. Open the project in a Java IDE.
5. Allow Maven to download the required dependencies.
6. Configure Apache Tomcat 11.
7. Build and deploy the project.
8. Start the Tomcat server.
9. Open the application in a web browser.

## Project Objective

The main objective of this project is to demonstrate the development of a complete Java full-stack application with:

- Client-server communication
- Database connectivity
- JDBC
- Hibernate ORM
- Authentication
- Authorization
- Voting functionality
- Transaction handling
- Database management

## Project Information

**Project Title:** Online Voting System Using JDBC, Hibernate and Database Connectivity

**University:** Chandigarh University

**Academic Year:** 2026–2027

**Project Type:** Full-Stack Web Application
