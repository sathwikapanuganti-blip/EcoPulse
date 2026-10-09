# EcoPulse Backend

High-performance, production-grade Spring Boot 3 backend service for the EcoPulse Environmental Monitoring and Community Action Platform.

## Features
- **Live Environmental Data**: Integrates with Open-Meteo Air Quality & Weather API for real-time PM2.5, PM10, CO, NO2, SO2, O3, Temperature, and Humidity.
- **Transparent EcoPulse Score Engine**: Combines air quality (35%), traffic flow (25%), water conservation (20%), and food waste mitigation (20%) into a transparent 0-100 score.
- **Resource Management Modules**: REST endpoints for reporting and tracking Food Waste and Water Leaks with 4-stage lifecycle (Reported -> Under Review -> In Progress -> Resolved).
- **Security & Authorization**: Spring Security 6 with BCrypt password hashing and JWT token authentication.
- **Resilience**: 15-minute in-memory caching, graceful offline fallback, and Global Exception Handling (`@RestControllerAdvice`).

## Prerequisites
- Java 17+ or Java 21+
- Maven 3.8+ (or Maven wrapper)
- MySQL 8.0+ (Optional; defaults to embedded H2 for instant local zero-config run)

## Running Locally

### Option 1: In-Memory Database (Zero-config instant run)
```bash
mvn spring-boot:run
```
The server will start on `http://localhost:8080`.
H2 Web Console available at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ecopulsedb`, username: `sa`, password: blank).

### Option 2: With MySQL
Set environment variables:
```bash
export DB_URL=jdbc:mysql://localhost:3306/ecopulse_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
export DB_USERNAME=root
export DB_PASSWORD=your_password
mvn spring-boot:run
```

## Running Tests
```bash
mvn test
```
