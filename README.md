
# Library Management System

## Project Description
This project is a Library Management System API built using Spring Boot. The API allows librarians to manage books, patrons, and borrowing records.

## Requirements

### 1. Entities
The system includes the following main entities:
- **Book**: Attributes include ID, title, author, publication year, ISBN, and other relevant information.
- **Patron**: Contains details about the patron, including ID, name, and contact information.
- **Borrowing Record**: Tracks the association between books and patrons, with fields to record borrowing and return dates.

### 2. API Endpoints
The API provides several RESTful endpoints for managing books, patrons, and borrowing records.

#### Book Management Endpoints:
- `GET /api/books`: Retrieve a list of all books.
- `GET /api/books/{id}`: Retrieve details of a specific book by its ID.
- `POST /api/books`: Add a new book to the library.
- `PUT /api/books/{id}`: Update an existing book's information.
- `DELETE /api/books/{id}`: Remove a book from the library.

#### Patron Management Endpoints:
- `GET /api/patrons`: Retrieve a list of all patrons.
- `GET /api/patrons/{id}`: Retrieve details of a specific patron by ID.
- `POST /api/patrons`: Add a new patron to the system.
- `PUT /api/patrons/{id}`: Update an existing patron's information.
- `DELETE /api/patrons/{id}`: Remove a patron from the system.

#### Borrowing Endpoints:
- `POST /api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
- `PUT /api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book by a patron.

### 3. Data Storage
Use an appropriate relational database (e.g., PostgreSQL, H2, or MySQL) to persist book, patron, and borrowing record details. Set up the necessary relationships between entities, such as a one-to-many relationship between books and borrowing records.

### 4. Validation and Error Handling
- Implement input validation for API requests to ensure required fields and data formats are correctly provided.
- Handle exceptions gracefully, returning appropriate HTTP status codes and error messages for various scenarios.

### 5. Security (Optional)
For additional credit, implement basic authentication or JWT-based authorization to protect the API endpoints from unauthorized access.

### 6. Aspect-Oriented Programming (AOP) for Logging (Optional)
Use Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics for critical operations like adding or updating books and patron transactions.

### 7. Caching (Optional)
Utilize Spring’s caching mechanisms to improve system performance by caching frequently accessed data, such as book details and patron information.

### 8. Transaction Management
Implement declarative transaction management using Spring's `@Transactional` annotation to ensure data integrity during critical operations.

### 9. Testing
- Write unit tests to validate the functionality of API endpoints.
- Use testing frameworks such as JUnit, Mockito, and SpringBootTest for thorough testing.

### 10. Documentation
Provide clear documentation for running the application, interacting with the API endpoints, and using authentication (if implemented).

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 21
- Maven
- PostgreSQL or another relational database



