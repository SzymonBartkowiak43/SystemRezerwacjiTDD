# Reservation System - Your personal booking assistant 💼

The **Reservation System** is a cutting-edge platform designed to simplify appointment scheduling and salon management. It allows users to create salons, add employees and services, set up opening hours, and define employee work schedules. Once a salon is fully configured, clients can browse available services, choose an employee, and book appointments effortlessly. The system ensures smooth communication by sending email confirmations after booking and reminders a day before the scheduled visit.

With extensive unit and integration tests, this system is reliable, secure, and ready for production.

## Key Features:

-   **Salon Management**:
    
    -   Create and configure salons with detailed information.
    -   Add employees and define their working hours.
    -   Specify available services and pricing.
-   **Client Booking**:
    
    -   Search and browse salons by services and employees.
    -   Schedule appointments with instant email confirmations.
    -   Automatic email reminders a day before the appointment.
-   **User Accounts**:
    
    -   Dedicated accounts for both salon owners and clients.
    -   Secure authentication using JWT-based Spring Security.
-   **Notifications**:
    
    -   Email-based notifications for bookings and reminders.

## Technologies Used:

1.  **Spring Boot**: REST APIs, Security (JWT), MongoDB Data, Validation, Scheduling.
2.  **Java 21**: Cutting-edge features of the latest Java version.
3.  **PostgreSQL & pgAdmin**: Reliable relational database for data persistence.
4.  **Redis**: In-memory data store for caching, with Jedis & Redis-Commander.
5.  **Docker**: Simplified deployment using Docker & Docker-Compose.
6.  **Testcontainers**: Containerized database testing.
7.  **Wiremock**: Mocking external APIs during tests.
8.  **JUnit5, Mockito, AssertJ**: Comprehensive unit testing.
9.  **SpringBootTest, MockMvc, SpringSecurityTest**: Thorough integration tests.
10.  **Lombok**: Boilerplate code reduction.
11.  **Swagger**: API documentation and testing.
12.  **Thymeleaf, HTML, CSS**: Responsive and user-friendly frontend.
13.  **RestTemplate & HTTP**: Handling REST API calls and integrations.
14.  **Mail Sender**: Email-based notifications.
15.  **Git, GitHub/GitLab**: Version control and project collaboration.

-   **Swagger Interface**:  
    Explore and test the REST API endpoints using Swagger.  
    ![Swagger](https://github.com/user-attachments/assets/7c86a618-918f-470e-b498-82422ee7120e)
    
-   **Redis Dashboard**:  
    Monitor and manage cached data efficiently with Redis Commander.  
    ![Redis](https://github.com/user-attachments/assets/be401279-a203-4844-b1dc-410bda4cb30f)
    
-   **PgAdmin Interface**:  
    Visualize and manage the PostgreSQL database seamlessly.  
    ![PgAdmin](https://github.com/user-attachments/assets/c37349be-fcb9-4d84-a59b-cae484fd6601)
    
-   **Integration Test in Action**:  
    Comprehensive testing ensures reliability and robustness.  
    ![Integration Test](https://github.com/user-attachments/assets/ded0f54d-27c0-47bd-a10d-3f4104960cb4)
    
-   **Email Confirmation**:  
    Automatic email notifications keep users informed about their reservations.  
    ![Email Confirmation](https://github.com/user-attachments/assets/e7850874-e014-42bf-93ac-7584d91819f2)
