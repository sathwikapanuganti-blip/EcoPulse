# EcoPulse System Architecture

"Monitor. Understand. Act. Together for a Greener Future."

---

## 1. High-Level Architecture Overview

EcoPulse is designed as a modular, decoupled full-stack platform consisting of:
1. **Interactive Client Application**: Built for accessibility across all demographics (children, adults, and elderly citizens), supporting instant multi-language translation and audio synthesis.
2. **REST API Gateway & Spring Boot Backend**: High-performance service managing authentication, business validation, resource waste lifecycle, and environmental caching.
3. **Environmental Data Layer**: Integrates real-time air quality and meteorological sensor feeds with internal caching.
4. **Relational Data Store**: MySQL database (with seamless in-memory H2 support for instant local setup).

```
+-----------------------------------------------------------------------------------+
|                           EcoPulse Client Tier                                   |
|  - Multi-Language Switcher (EN, HI, TE, ES, FR, DE)                               |
|  - All-Age Clarity Engine (Standard Detailed View vs Simple Kid & Elder View)    |
|  - Web Speech Audio Synthesis Engine (Listen to Environmental Summary)            |
|  - Leaflet.js OpenStreetMap Interactive Visualizer                                |
+-----------------------------------------+-----------------------------------------+
                                          |
                      JSON REST API / HTTP| CORS Enabled
                                          v
+-----------------------------------------------------------------------------------+
|                        Spring Boot 3.2 Backend Service                            |
|                                                                                   |
|  [Security & Auth Filter]  ──>  [REST Controllers]  ──>  [Global Exception Advice]|
|                                         |                                         |
|                                 [Service Layer]                                   |
|       +---------------------------------+---------------------------------+       |
|       | AirQualityService (15m Cache)   | EcoScoreService (Formula Calc)  |       |
|       | TrafficService (Mobility Model) | WaterWasteService (4-Stage)     |       |
|       | FoodWasteService (Donation Hub) | CommunityService (Society Stats)|       |
|       +---------------------------------+---------------------------------+       |
|                                         |                                         |
|                                 [Spring Data JPA]                                 |
+--------------------+--------------------+--------------------+--------------------+
                     |                                         |
                     v                                         v
   +------------------------------------+    +------------------------------------+
   | External Sensor Network (Live)     |    | Persistent Relational Storage      |
   | - Open-Meteo Air Quality API       |    | - MySQL 8.0 / Embedded H2 DB       |
   | - Open-Meteo Forecast Weather API  |    | - Connection Pool (HikariCP)       |
   +------------------------------------+    +------------------------------------+
```

---

## 2. Key Architectural Design Decisions

### A. Dual-Engine Resilience & Real-Time Data Rule
- **Compliance with Zero Mock Distortion Rule**: Real-time environmental metrics (PM2.5, PM10, CO, NO2, SO2, O3, Temperature, Humidity) are sourced from open satellite and terrestrial telemetry.
- **Client Resilience**: The frontend contains a dual-client adapter. When the Spring Boot backend is active, all requests pass through the backend cache. If the backend is not yet started, the frontend falls back to direct browser telemetry fetching, clearly labelling the active operational mode.

### B. Transparent EcoPulse Score Engine
Unlike proprietary black-box ratings, the EcoPulse Score ($0-100$) is computed with complete mathematical transparency:

$$\text{EcoPulse Score} = (S_{\text{Air}} \times 0.35) + (S_{\text{Traffic}} \times 0.25) + (S_{\text{Water}} \times 0.20) + (S_{\text{Food}} \times 0.20)$$

Where:
- $S_{\text{Air}} = \max(0, \min(100, 100 - (\text{AQI} \times 0.38)))$
- $S_{\text{Traffic}} = \max(0, 100 - \text{Intensity})$
- $S_{\text{Water}} = \max(20, 95 - (\text{Unresolved Leaks} \times 7))$
- $S_{\text{Food}} = \max(30, 90 - \text{Penalty})$

### C. Universal Demographic Accessibility
To bridge technical comprehension across generations:
1. **Simple View Mode**: Replaces complex pollutant graphs with large, colorful emotion indicators and clear 1-line health recommendations.
2. **Audio Read-Aloud**: Employs the native Web Speech API to synthesize local reports in the user's selected language.
3. **Multi-Language Dictionaries**: Native i18n support without bloated external dependencies.

---

## 3. Database Entity Relationship Model

- **User**: Authentication, role permissions (`ROLE_USER`, `ROLE_ADMIN`), community affiliation.
- **Community**: Apartment societies, colleges, campuses, and wards with collective metrics.
- **EnvironmentalReport**: Community problem filings (category, geolocation, status, photo URL).
- **WaterWasteReport**: Leaks and overflows with 4-stage pipeline:
  `REPORTED` ➔ `UNDER_REVIEW` ➔ `IN_PROGRESS` ➔ `RESOLVED`.
- **FoodWasteReport**: Surplus food listings for quick volunteer/shelter coordination.
- **AirQualityRecord**: Historical environmental snapshots.
- **TrafficRecord**: Urban mobility readings.
- **Alert**: Municipal and society broadcast notifications.
- **EcoImpact**: Citizen gamification log for water saved, food waste avoided, and transit taken.
- **EcoTip**: Multilingual repository of actionable sustainability guidance.
