# EcoPulse: Real-Time Environmental Monitoring and Community Action Platform

> **"Monitor. Understand. Act. Together for a Greener Future."**  
> *"Know your environment. Understand the problem. Take action."*

---

## 1. Project Overview

**EcoPulse** is an environmental and community-awareness platform that empowers citizens, families, residential societies, colleges, and workplaces to monitor their immediate environmental conditions and collaborate to reduce air pollution, water wastage, and food waste.

Unlike traditional environmental monitoring dashboards that present confusing raw sensor metrics, EcoPulse is designed with **universal demographic accessibility**:
- **From Children to Seniors**: A dedicated toggle switches between a **Comprehensive Standard View** and a **Simplified Visual View** (large icons, high contrast, non-technical explanations).
- **Voice Narration (Read-Aloud)**: Integrates one-click audio playback so seniors and young children can listen to the daily environmental report spoken aloud in their preferred language.
- **Multi-Language Specifications**: Full internationalization supporting **English, Hindi (हिंदी), Telugu (తెలుగు), Spanish (Español), French (Français), and German (Deutsch)**.

---

## 2. Problem Statement

Urban communities face accelerating environmental and resource pressures:
1. **Air Pollution & Smog**: High levels of fine particulate matter (PM2.5, PM10) and vehicle emissions (CO, NO2) cause respiratory illness, yet citizens frequently lack localized, actionable advice.
2. **Clean Water Wastage**: Leaking distribution pipes, unattended open taps, and overflowing overhead tanks lose thousands of litres of treated municipal water daily.
3. **Food Waste**: Massive quantities of nutritious food are discarded after catering events and household surplus, while local shelters face shortages.
4. **Information Disconnect**: Environmental data is either hidden behind technical jargon or fails to offer immediate avenues for collective community remediation.

---

## 3. Objectives

- Deliver **real-time environmental intelligence** (AQI, PM2.5, PM10, CO, NO2, SO2, O3, Temperature, Humidity).
- Establish a **transparent composite EcoPulse Score** combining air quality, traffic mobility, and resource loss.
- Provide a **4-stage citizen-warden remediation pipeline** for water leaks and neighborhood environmental hazards (`Reported` ➔ `Under Review` ➔ `In Progress` ➔ `Resolved`).
- Enable **surplus food sharing** to eliminate food waste.
- Foster community engagement through **personal eco action logging** and society-wide sustainability benchmarks.
- Ensure **zero barriers to comprehension** for young students, working adults, and senior citizens.

---

## 4. Key Features

### 📍 Location-Based Environment Dashboard
- **Auto-Detection & Fallback**: Automatically detects latitude and longitude via browser GPS; provides an instant search bar for any city worldwide with graceful fallback.
- **Air Quality Gauge**: Visual color-coded index adhering to the **US EPA AQI Standard (0-500)**:
  - 🟢 **Safe & Fresh** (0 - 50)
  - 🟡 **Moderate** (51 - 100)
  - 🟠 **Poor** (101 - 150)
  - 🔴 **Very Poor** (151 - 200)
  - 🟣 **Hazardous** (201+)
- **Live Pollutant Breakdown**: Instant readings for PM2.5, PM10, CO, NO2, SO2, and Ozone.
- **Actionable Health Recommendations**: Contextual medical and daily activity tips.

### 🧒 Universal Age Accessibility ("Older to Younger")
- **Standard View**: Full scientific measurements, pollutant graphs, and fine parameters.
- **Simple View**: Large friendly emojis (😊, 😐, 😷, 🛑), warm visual contrast, simplified 1-sentence explanations.
  - *Kid explanation*: "PM2.5 are tiny dust specks smaller than a hair! When the smiley is green 😊, it is safe to play football outside!"
  - *Elder reminder*: "Air quality is comfortable. Morning walks before 8:00 AM are recommended. Stay well hydrated."
- **🔊 Voice Synthesis (Read-Aloud)**: Native Web Speech API integration that narrates the current local report in the user's selected language.

### 🌐 Multi-Language Support
Instant dynamic switching across 6 major languages:
- **English (EN)**
- **हिंदी (Hindi - HI)**
- **తెలుగు (Telugu - TE)**
- **Español (Spanish - ES)**
- **Français (French - FR)**
- **Deutsch (German - DE)**

### 📊 Transparent EcoPulse Score
A mathematically open, transparent score ($0-100$) reflecting neighborhood ecological balance:
$$\text{EcoPulse Score} = (\text{Air Quality} \times 35\%) + (\text{Traffic Flow} \times 25\%) + (\text{Water Control} \times 20\%) + (\text{Food Mitigation} \times 20\%)$$

### 🚗 Traffic & Mobility Impact Analysis
Backend abstraction estimating congestion, vehicle volume, and vehicular emission risk with commuter guidance.

### 🍲 Food Waste Mitigation Module
- Report event leftovers, grocery surplus, and caterer over-preparation.
- Real-time community board connecting surplus meal donors with volunteers and food pantries.

### 💧 Water Waste Management
- Report leaking pipes, overflowing rooftop tanks, or burst lines.
- 4-stage lifecycle tracker (`REPORTED` ➔ `UNDER_REVIEW` ➔ `IN_PROGRESS` ➔ `RESOLVED`).

### 🗺️ Interactive Environmental Map
- Powered by Leaflet.js & OpenStreetMap (zero proprietary API keys required).
- Interactive markers for user location, water leaks, surplus food pickups, garbage dumps, and smog corridors with list fallback.

### 🏆 Personal Eco Impact & Society Dashboard
- Log daily green actions: water saved (L), food waste avoided (kg), single-use plastics refused, and public transit trips taken.
- Earn badges from *Green Pioneer* to *Planet Champion*.
- Track apartment society statistics: total reports resolved, water loss reduced (%), and food salvaged.

### 🛡️ Admin & Green Warden Portal
- Role-based moderation allowing community wardens to inspect incoming issues, assign technicians, update resolution statuses, and broadcast environmental alerts.

---

## 5. Technology Stack

### Backend
- **Language**: Java 17 / 21
- **Framework**: Spring Boot 3.2.4
- **Modules**:
  - `spring-boot-starter-web` (REST APIs)
  - `spring-boot-starter-data-jpa` (Hibernate ORM)
  - `spring-boot-starter-security` (Stateless Security Filter Chain)
  - `spring-boot-starter-validation` (Bean Validation)
  - `spring-boot-starter-cache` (In-memory telemetry caching)
- **Security**: JJWT (io.jsonwebtoken 0.12.5), BCrypt Password Hashing
- **Database**: MySQL 8.0 (with embedded H2 database support for zero-config local runs)
- **Build Tool**: Apache Maven

### Frontend
- **Interface**: Modern HTML5, Semantic CSS3, Vanilla ES6+ JavaScript
- **Mapping**: Leaflet.js 1.9.4 & OpenStreetMap
- **Voice Engine**: Web Speech API (`window.speechSynthesis`)
- **Architecture**: Dual-Engine (Connects to Spring Boot backend or operates standalone via direct Open-Meteo telemetry)

### DevOps & Deployment
- **Containerization**: Docker, Docker Compose
- **Compatibility**: Ready for deployment on AWS, Azure, Render, Railway, Vercel, and Netlify

---

## 6. System Architecture

```
+───────────────────────────────────────────────────────────────────+
│                       EcoPulse Web Client                         │
│  - Multi-Language Switcher (EN, HI, TE, ES, FR, DE)               │
│  - Clarity Modes (Standard vs Kid & Elder View)                   │
│  - Web Speech Narration Engine                                    │
│  - Interactive Leaflet.js OpenStreetMap Canvas                    │
+─────────────────────────────────┬─────────────────────────────────+
                                  │ JSON REST / CORS
                                  ▼
+───────────────────────────────────────────────────────────────────+
│                   Spring Boot 3.2 REST API                        │
│                                                                   │
│  [Security Filter & JWT] ──> [Controllers] ──> [Exception Handler]│
│                                     │                             │
│                              [Service Layer]                      │
│     • AirQualityService (15m Cache)  • EcoScoreService (Formula)  │
│     • TrafficService (Mobility)      • WaterWasteService (Track)  │
│     • FoodWasteService (Donations)   • CommunityService (Stats)   │
│                                     │                             │
│                            [Spring Data JPA]                      │
+──────────────────┬──────────────────────────────────┬─────────────+
                   │                                  │
                   ▼                                  ▼
    +──────────────────────────────+   +────────────────────────────+
    |  Open-Meteo Sensor Network   |   |   MySQL 8.0 / H2 Database  |
    |  - Real-time Air Quality     |   |   - Users, Reports, Leaks  |
    |  - Forecast Meteorology      |   |   - Food Sharing, Alerts   |
    +──────────────────────────────+   +────────────────────────────+
```

---

## 7. Database Design

Complete SQL script located at [`database/schema.sql`](file:///c:/Users/LUCKY/OneDrive/Desktop/eco/EcoPulse/database/schema.sql).

### Key Entities
| Entity | Description | Key Attributes |
|---|---|---|
| `users` | Citizen and Warden credentials | `id`, `username`, `email`, `password_hash`, `role`, `community_id` |
| `communities` | Society / Campus entities | `id`, `name`, `type`, `city`, `total_members`, `eco_score`, `water_reduction_pct` |
| `environmental_reports` | Public issue filings | `id`, `category`, `location`, `latitude`, `longitude`, `severity`, `status`, `image_url` |
| `water_waste_reports` | Clean water loss filings | `id`, `waste_type`, `severity`, `status`, `estimated_litres_wasted_per_hour` |
| `food_waste_reports` | Surplus meal listings | `id`, `food_type`, `approx_quantity`, `quantity_meals`, `reason`, `status` |
| `air_quality_records` | Environmental snapshots | `id`, `aqi`, `pm25`, `pm10`, `co`, `no2`, `so2`, `o3`, `temperature`, `humidity` |
| `traffic_records` | Mobility estimates | `id`, `congestion_level`, `traffic_intensity`, `pollution_risk`, `is_real_time` |
| `alerts` | Broadcast risk warnings | `id`, `type`, `title`, `message`, `severity`, `active` |
| `personal_eco_impact` | Citizen action logs | `id`, `user_id`, `water_saved_litres`, `food_waste_avoided_kg`, `eco_impact_score` |
| `eco_tips` | Practical guidance entries | `id`, `category`, `title`, `description`, `easy_action`, `target_group`, `language_code` |

---

## 8. REST API Documentation

### Authentication Endpoints
- `POST /api/auth/register` - Create citizen account
- `POST /api/auth/login` - Authenticate and receive JWT Bearer token

### Environmental Endpoints
- `GET /api/environment/air-quality?lat={lat}&lon={lon}&locationName={name}` - Fetch live AQI and pollutant concentrations
- `GET /api/environment/nearby?lat={lat}&lon={lon}` - Retrieve adjacent sector comparisons
- `GET /api/environment/eco-score?lat={lat}&lon={lon}` - Calculate composite EcoPulse Score

### Mobility & Traffic
- `GET /api/traffic?lat={lat}&lon={lon}&locationName={name}` - Retrieve traffic risk index

### Community Problem Reports
- `POST /api/reports` - Submit an environmental problem
- `GET /api/reports` - List all community reports
- `GET /api/reports/nearby` - List reports near coordinates
- `GET /api/reports/my?userId={id}` - List reports by logged-in citizen

### Food Waste & Redistribution
- `POST /api/food-waste` - Log surplus food or waste
- `GET /api/food-waste` - List available surplus meals for pickup

### Water Waste Management
- `POST /api/water-waste` - Report clean water leakage or tank overflow
- `GET /api/water-waste` - List all active water leaks
- `PUT /api/water-waste/{id}/status?status={status}` - Advance leak lifecycle

### Alerts & Community Stats
- `GET /api/alerts` - List active environmental alerts
- `GET /api/community/dashboard?communityId={id}` - Fetch society progress metrics
- `GET /api/eco-impact?userId={id}` - Fetch citizen personal eco score
- `POST /api/eco-impact` - Log personal water, food, or transit saving actions
- `GET /api/eco-tips?category={cat}&targetGroup={group}&lang={code}` - Filterable tips

### Admin Endpoints (Requires `ROLE_ADMIN`)
- `GET /api/admin/reports` - Warden overview of all reports
- `PUT /api/admin/reports/{id}/status?status={status}` - Update status (`REPORTED`, `UNDER_REVIEW`, `IN_PROGRESS`, `RESOLVED`)

---

## 9. Environment Variables

| Variable | Description | Default (Local Dev) |
|---|---|---|
| `SERVER_PORT` | Port for Spring Boot backend | `8080` |
| `DB_URL` | JDBC Connection URL | `jdbc:h2:mem:ecopulsedb...` (Embedded H2) |
| `DB_USERNAME` | Database username | `sa` |
| `DB_PASSWORD` | Database password | `""` |
| `JWT_SECRET` | Secret key for signing JWT tokens | Preconfigured 256-bit key |
| `AIR_QUALITY_API_KEY` | Optional commercial key | *(Not required; uses Open-Meteo)* |

---

## 10. Running the Application Locally

### Option A: Instant Zero-Config Run (Frontend Only or Dual Mode)
1. Navigate to the `frontend/` folder.
2. Double-click `index.html` or open it directly in Google Chrome, Microsoft Edge, or Firefox.
3. The application will initialize, detect your location, and connect directly to the Open-Meteo environmental satellite network with full interactive functionality!

To serve via a local web server:
```powershell
# In PowerShell (inside frontend directory)
python -m http.server 3000
```
Open `http://localhost:3000` in your web browser.

---

### Option B: Running with Spring Boot Backend

#### 1. Start Spring Boot Backend
Open a terminal in the `backend/` folder:
```powershell
mvn spring-boot:run
```
The backend initializes on `http://localhost:8080`.
Embedded H2 database console is accessible at: `http://localhost:8080/h2-console`.

#### 2. Open Frontend
Open `frontend/index.html` in your browser. The top system status bar will instantly transition to:
`🟢 Connected to Spring Boot Backend (Port 8080)`.

---

### Option C: Running via Docker Compose

Run the entire stack (MySQL 8.0, Spring Boot Backend, and Nginx Web Frontend) with a single command:
```bash
docker-compose up --build
```
- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080`
- MySQL: `localhost:3306`

---

## 11. GitHub Repository Setup

To push this project to GitHub:

```bash
# 1. Initialize Git repository
git init

# 2. Stage all project files (.gitignore ensures secrets and targets are excluded)
git add .

# 3. Create initial commit
git commit -m "Initial EcoPulse project: Complete real-time environmental platform"

# 4. Set main branch
git branch -M main

# 5. Add your remote GitHub repository URL
git remote add origin https://github.com/YOUR_USERNAME/EcoPulse.git

# 6. Push code to GitHub
git push -u origin main
```

---

## 12. Future Enhancements

1. **IoT Sensor Gateway**: MQTT broker integration for residential ESP32 / Arduino water flow meters.
2. **Mobile App**: Cross-platform React Native / Flutter build using the same backend REST endpoints.
3. **AI Vision Classifier**: Automated image recognition to classify garbage dumping into dry, wet, and hazardous recycling streams.
4. **Municipal Work Order Integration**: Direct webhook sync with municipal civic maintenance systems.

---

## 13. Contributors

- **EcoPulse Core Development Team**  
- Developed for environmental awareness and community-driven action across all generations.
