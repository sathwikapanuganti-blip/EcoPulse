# EcoPulse Frontend

A responsive web application and dashboard for the EcoPulse Environmental Monitoring Platform.

## Features
- **All-Age Accessibility**: Dedicated toggle between **Standard Detailed View** and **Elder & Kid Friendly View** (large icons, high contrast, simplified explanations).
- **Web Speech Audio Narration**: 1-click `🔊 Listen to Today's Report` reads today's air quality, temperature, and health advisory in the user's chosen language.
- **Multi-Language Support (i18n)**: English (en), Hindi (hi), Telugu (te), Spanish (es), French (fr), German (de).
- **Real-Time Environmental Dashboard**:
  - Live Air Quality Index (AQI) with US EPA standard gauge.
  - Granular pollutant breakdown: PM2.5, PM10, CO, NO2, SO2, O3.
  - Temperature, humidity, and clear health advisory.
  - Transparent EcoPulse Score breakdown.
- **Resource Protection Modules**:
  - Food Waste Management & surplus food redistribution.
  - Water Waste Management & 4-stage pipe leak tracker (Reported -> Under Review -> In Progress -> Resolved).
- **Interactive Map**: OpenStreetMap + Leaflet.js with pins for user location, water leaks, food surplus, garbage dumping, and smog corridors.
- **Dual Engine Architecture**: Automatically connects to the Spring Boot REST API (`http://localhost:8080/api`), with seamless direct open API fallback if the backend is offline.

## Running Locally

Simply open `index.html` directly in any web browser, or serve it using any HTTP server:

```bash
# Using Python
python -m http.server 3000

# Using Node serve
npx serve -l 3000 .
```

Open your browser at `http://localhost:3000`.
