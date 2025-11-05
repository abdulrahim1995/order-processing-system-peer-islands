<<<<<<< HEAD
# order-processing-system-peer-islands
Spring Boot backend for E-commerce order management
=======
# Order Processing - Assignment

## Objective
Build a backend for an E-commerce Order Processing System supporting order creation, retrieval, status updates, cancellation and a scheduled job to promote PENDING orders to PROCESSING every 5 minutes.

## Tech stack
- Java 8
- Spring Boot 2.7.x
- Spring Data JPA (H2 database)
- Spring Security (Basic Auth, in-memory users for the assignment)
- Scheduling with @Scheduled
- Unit testing with JUnit + Mockito

## Users (in-memory)
- user / password  (ROLE_USER)
- admin / adminpass (ROLE_ADMIN)

## Endpoints
- `POST /api/orders` — create order (USER or ADMIN)
- `GET /api/orders` — list orders, optional ?status=PROCESSING (USER or ADMIN)
- `GET /api/orders/{id}` — get by id (USER or ADMIN)
- `POST /api/orders/{id}/cancel` — cancel if PENDING (USER or ADMIN)
- `PUT /api/orders/{id}/status?status=SHIPPED` — update status (ADMIN only)

## How scheduled job works
A scheduler runs every 5 minutes and invokes a service method to promote all PENDING orders to PROCESSING.

## Run
- Build: `mvn clean package`
- Run: `mvn spring-boot:run`
- H2 console: http://localhost:8081/h2-console (JDBC URL: jdbc:h2:mem:ordersdb, user: sa)
