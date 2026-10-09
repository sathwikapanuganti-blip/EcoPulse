/**
 * EcoPulse Main Application Controller
 * Coordinates UI rendering, real-time data flow, speech synthesis, and community interaction.
 */

const AppState = {
  currentLat: 28.6139,
  currentLon: 77.2090,
  currentLocationName: "Delhi Central Area",
  airData: null,
  scoreData: null,
  trafficData: null,
  viewMode: localStorage.getItem("ecopulse_view_mode") || "standard", // standard | simple
  userRole: localStorage.getItem("ecopulse_role") || "CITIZEN",       // CITIZEN | ADMIN
  currentLanguage: localStorage.getItem("ecopulse_lang") || "en"
};

// City Coordinate Lookup for Instant Search without proprietary keys
const CITY_GEO_LOOKUP = {
  "delhi": { lat: 28.6139, lon: 77.2090, name: "Delhi, India" },
  "new delhi": { lat: 28.6139, lon: 77.2090, name: "New Delhi, India" },
  "mumbai": { lat: 19.0760, lon: 72.8777, name: "Mumbai, India" },
  "hyderabad": { lat: 17.3850, lon: 78.4867, name: "Hyderabad, India" },
  "bengaluru": { lat: 12.9716, lon: 77.5946, name: "Bengaluru, India" },
  "bangalore": { lat: 12.9716, lon: 77.5946, name: "Bengaluru, India" },
  "chennai": { lat: 13.0827, lon: 80.2707, name: "Chennai, India" },
  "kolkata": { lat: 22.5726, lon: 88.3639, name: "Kolkata, India" },
  "london": { lat: 51.5074, lon: -0.1278, name: "London, UK" },
  "new york": { lat: 40.7128, lon: -74.0060, name: "New York, USA" },
  "paris": { lat: 48.8566, lon: 2.3522, name: "Paris, France" },
  "berlin": { lat: 52.5200, lon: 13.4050, name: "Berlin, Germany" },
  "madrid": { lat: 40.4168, lon: -3.7038, name: "Madrid, Spain" },
  "tokyo": { lat: 35.6762, lon: 139.6503, name: "Tokyo, Japan" }
};

document.addEventListener("DOMContentLoaded", async () => {
  // 1. Initialize multi-language system
  if (typeof setLanguage === "function") {
    setLanguage(AppState.currentLanguage);
  }

  // 2. Initialize view mode (standard vs simple)
  applyViewModeUI();

  // 3. Initialize user role and authentication UI
  applyRoleUI();
  syncUserHeaderUI();

  // 4. Check backend connectivity
  await ApiClient.checkBackendHealth();

  // 5. Try detecting user location automatically or use default
  await loadDashboardData(AppState.currentLat, AppState.currentLon, AppState.currentLocationName);

  // 6. Initialize Leaflet Map
  if (typeof EcoMap !== "undefined") {
    EcoMap.init("ecoMapContainer", AppState.currentLat, AppState.currentLon);
  }

  // 7. Load ancillary feeds
  loadAlerts();
  loadFoodWasteFeed();
  loadWaterWasteFeed();
  loadReportsFeed();
  renderEcoTips();
  loadCommunityDashboard();
  loadPersonalImpactUI();
});

/**
 * Fetch and render all live dashboard components
 */
async function loadDashboardData(lat, lon, locName) {
  AppState.currentLat = lat;
  AppState.currentLon = lon;
  AppState.currentLocationName = locName;

  document.getElementById("displayCurrentLocation").textContent = locName;

  // 1. Fetch live air quality & weather
  const aq = await ApiClient.getAirQuality(lat, lon, locName);
  AppState.airData = aq;
  renderAirQuality(aq);

  // 2. Fetch traffic & mobility data
  const traffic = await ApiClient.getTraffic(lat, lon, locName);
  AppState.trafficData = traffic;
  renderTraffic(traffic);

  // 3. Fetch EcoPulse Composite Score
  const score = await ApiClient.getEcoScore(lat, lon, locName, aq.aqi);
  AppState.scoreData = score;
  renderEcoScore(score);

  // 4. Fetch nearby comparisons
  const nearby = await ApiClient.getNearbyComparison(lat, lon);
  renderNearbyComparison(nearby);

  // 5. Update map position
  if (typeof EcoMap !== "undefined" && EcoMap.setUserLocation) {
    EcoMap.setUserLocation(lat, lon, locName);
  }
}

/**
 * Render Air Quality and Weather metrics
 */
function renderAirQuality(aq) {
  if (!aq) return;

  const aqiCircle = document.getElementById("aqiCircle");
  const aqiValue = document.getElementById("aqiValue");
  const aqiStatusBadge = document.getElementById("aqiStatusBadge");
  const aqiDescription = document.getElementById("aqiDescription");
  const healthAdvisoryBox = document.getElementById("healthAdvisoryBox");
  const healthAdvisoryText = document.getElementById("healthAdvisoryText");

  aqiValue.textContent = aq.aqi || "--";
  aqiDescription.textContent = aq.statusDescription || "";
  healthAdvisoryText.textContent = aq.healthRecommendation || "";

  // Assign clean class names and styling based on EPA standard
  let cssClass = "aqi-safe";
  let statusText = t("safeLabel") || "Safe & Fresh";
  let emoji = "😊";

  if (aq.aqi > 200) {
    cssClass = "aqi-hazardous";
    statusText = t("hazardousLabel") || "Emergency Hazard";
    emoji = "🛑";
  } else if (aq.aqi > 150) {
    cssClass = "aqi-verypoor";
    statusText = t("veryPoorLabel") || "Very Poor (Hazardous)";
    emoji = "😷";
  } else if (aq.aqi > 100) {
    cssClass = "aqi-poor";
    statusText = t("poorLabel") || "Poor (Unhealthy)";
    emoji = "😐";
  } else if (aq.aqi > 50) {
    cssClass = "aqi-moderate";
    statusText = t("moderateLabel") || "Moderate (Fair)";
    emoji = "🙂";
  }

  aqiCircle.className = `aqi-number-circle ${cssClass}`;
  aqiStatusBadge.className = `aqi-status-badge ${cssClass}`;
  aqiStatusBadge.textContent = statusText;

  healthAdvisoryBox.className = `health-advisory-box ${cssClass.replace('aqi-', '')}`;

  // Weather & Pollutant Values
  document.getElementById("valTemperature").textContent = aq.temperature ? `${Math.round(aq.temperature)}°C` : "--";
  document.getElementById("valHumidity").textContent = aq.humidity ? `${Math.round(aq.humidity)}%` : "--";
  document.getElementById("valLiveFlag").textContent = aq.isLive ? "Live Sensor" : "Verified Archive";
  document.getElementById("displayDataSource").textContent = aq.dataSource || "Open-Meteo Network";
  document.getElementById("aqiLastUpdated").textContent = `${t("lastUpdated") || "Last updated"}: ${aq.lastUpdated || 'Just now'}`;

  document.getElementById("valPM25").textContent = aq.pm25 !== undefined ? aq.pm25 : "--";
  document.getElementById("valPM10").textContent = aq.pm10 !== undefined ? aq.pm10 : "--";
  document.getElementById("valCO").textContent = aq.co !== undefined ? aq.co : "--";
  document.getElementById("valNO2").textContent = aq.no2 !== undefined ? aq.no2 : "--";
  document.getElementById("valSO2").textContent = aq.so2 !== undefined ? aq.so2 : "--";
  document.getElementById("valO3").textContent = aq.o3 !== undefined ? aq.o3 : "--";

  // Update simple mode emojis and guidance
  const kidEmoji = document.getElementById("kidEmojiBadge");
  const elderEmoji = document.getElementById("elderEmojiBadge");
  if (kidEmoji) kidEmoji.textContent = emoji;
  if (elderEmoji) elderEmoji.textContent = aq.aqi > 100 ? "⚠️" : "🫁";
}

/**
 * Render EcoPulse Transparent Score
 */
function renderEcoScore(score) {
  if (!score) return;

  const circle = document.getElementById("ecoScoreCircle");
  circle.textContent = score.score;

  document.getElementById("ecoScoreStatus").textContent = score.status;
  document.getElementById("ecoScoreExplanation").textContent = score.explanation;
  document.getElementById("formulaDisplay").textContent = score.formula;

  if (score.factorScores) {
    const airVal = score.factorScores["Air Quality"] || 75;
    const trafficVal = score.factorScores["Traffic Flow"] || 65;
    const waterVal = score.factorScores["Water Waste Control"] || 85;
    const foodVal = score.factorScores["Food Waste Control"] || 80;

    document.getElementById("scoreFactorAir").textContent = `${airVal}/100`;
    document.getElementById("barFactorAir").style.width = `${airVal}%`;

    document.getElementById("scoreFactorTraffic").textContent = `${trafficVal}/100`;
    document.getElementById("barFactorTraffic").style.width = `${trafficVal}%`;

    document.getElementById("scoreFactorWater").textContent = `${waterVal}/100`;
    document.getElementById("barFactorWater").style.width = `${waterVal}%`;

    document.getElementById("scoreFactorFood").textContent = `${foodVal}/100`;
    document.getElementById("barFactorFood").style.width = `${foodVal}%`;
  }
}

/**
 * Render Traffic Analysis
 */
function renderTraffic(tr) {
  if (!tr) return;
  document.getElementById("trafficCongestion").textContent = tr.congestionLevel || "MODERATE";
  document.getElementById("trafficIntensityVal").textContent = `${tr.trafficIntensity || 50}%`;
  document.getElementById("trafficRiskVal").textContent = tr.pollutionRisk || "Moderate";
  document.getElementById("trafficAdviceText").textContent = tr.advice || "";
  document.getElementById("trafficSourceBadge").textContent = tr.dataSource || "Mobility Sensor";
}

/**
 * Render Nearby Pollution Comparison Cards
 */
function renderNearbyComparison(list) {
  const container = document.getElementById("nearbyListContainer");
  if (!container || !list) return;

  container.innerHTML = list.map(item => {
    let badgeClass = "aqi-safe";
    if (item.aqi > 150) badgeClass = "aqi-verypoor";
    else if (item.aqi > 100) badgeClass = "aqi-poor";
    else if (item.aqi > 50) badgeClass = "aqi-moderate";

    return `
      <div class="nearby-row">
        <div>
          <div class="nearby-name">📍 ${item.locationName}</div>
          <small class="muted">PM2.5: ${item.pm25} µg/m³ • Temp: ${item.temperature}°C</small>
        </div>
        <div class="nearby-aqi-tag ${badgeClass}">
          ${item.aqi} AQI • ${item.statusLabel}
        </div>
      </div>
    `;
  }).join("");
}

/**
 * Food Waste Feed
 */
async function loadFoodWasteFeed() {
  const container = document.getElementById("foodWasteList");
  if (!container) return;

  const list = await ApiClient.getFoodWaste();
  container.innerHTML = list.map(item => `
    <div style="background: #f8fafc; border: 1px solid var(--border); border-radius: 10px; padding: 14px;">
      <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 6px;">
        <strong style="color: #065f46; font-size: 1rem;">📍 ${item.location}</strong>
        <span class="status-badge ${item.status === 'AVAILABLE_FOR_DONATION' ? 'resolved' : 'under_review'}">
          ${item.status === 'AVAILABLE_FOR_DONATION' ? '🍲 Available for Pickup' : 'Claimed'}
        </span>
      </div>
      <div style="font-size: 0.92rem; font-weight: 600; color: #1e293b;">
        📦 ${item.approxQuantity} (${item.foodType})
      </div>
      <p style="font-size: 0.86rem; color: var(--text-muted); margin-top: 4px;">${item.reason}</p>
      <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
        <small class="muted">Reported: ${item.createdAt}</small>
        <button class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.78rem;" onclick="alert('Contacting community food dispatcher for pickup: ' + '${item.location}')">
          🤝 Coordinate Pickup
        </button>
      </div>
    </div>
  `).join("");
}

/**
 * Water Waste Feed (4-stage status pipeline)
 */
async function loadWaterWasteFeed() {
  const container = document.getElementById("waterWasteList");
  if (!container) return;

  const list = await ApiClient.getWaterWaste();
  container.innerHTML = list.map(item => {
    let statusClass = "reported";
    if (item.status === "RESOLVED") statusClass = "resolved";
    else if (item.status === "IN_PROGRESS") statusClass = "in_progress";
    else if (item.status === "UNDER_REVIEW") statusClass = "under_review";

    return `
      <div style="background: #f8fafc; border: 1px solid var(--border); border-radius: 10px; padding: 14px;">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 6px;">
          <strong style="color: #0369a1; font-size: 1rem;">💧 ${item.location}</strong>
          <span class="status-badge ${statusClass}">${item.status}</span>
        </div>
        <div style="font-size: 0.88rem; font-weight: 600;">
          Type: ${item.wasteType.replace('_', ' ')} • Severity: <span style="color: #ea580c;">${item.severity}</span>
        </div>
        <p style="font-size: 0.86rem; color: var(--text-muted); margin-top: 4px;">${item.description}</p>
        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
          <small class="muted">Logged: ${item.createdAt}</small>
          <button class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.78rem;" onclick="alert('Viewing plumber dispatch log for: ' + '${item.location}')">
            🔧 Track Repair
          </button>
        </div>
      </div>
    `;
  }).join("");
}

/**
 * Community Environmental Reports Feed
 */
async function loadReportsFeed() {
  const container = document.getElementById("reportsFeedContainer");
  if (!container) return;

  const list = await ApiClient.getReports();
  container.innerHTML = list.map(item => {
    let statusClass = "reported";
    if (item.status === "RESOLVED") statusClass = "resolved";
    else if (item.status === "IN_PROGRESS") statusClass = "in_progress";

    return `
      <div style="background: #ffffff; border: 1px solid var(--border); border-radius: 12px; padding: 16px; box-shadow: var(--shadow);">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
          <div>
            <span class="status-badge" style="background: #f1f5f9; color: #475569; margin-right: 8px;">
              🏷️ ${item.category.replace('_', ' ')}
            </span>
            <strong>📍 ${item.location}</strong>
          </div>
          <span class="status-badge ${statusClass}">${item.status}</span>
        </div>
        <p style="font-size: 0.92rem; color: var(--text-main); margin: 6px 0;">${item.description}</p>
        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px; font-size: 0.82rem; color: var(--text-muted);">
          <span>Severity: <strong>${item.severity}</strong> • Reported: ${item.createdAt}</span>
          <button class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.78rem;" onclick="showToast('Thank you for verifying this community report!', 'success')">
            👍 Upvote Report
          </button>
        </div>
      </div>
    `;
  }).join("");

  // Also refresh admin table if admin view is active
  if (AppState.userRole === "ADMIN") {
    renderAdminPortal(list);
  }
}

/**
 * Admin Portal Table & Dashboard
 */
async function renderAdminPortal(list) {
  const container = document.getElementById("adminReportsTableContainer");
  if (!container) return;

  // Load backend analytics if online
  try {
    const stats = await ApiClient.getAdminAnalytics();
    if (stats) {
      document.getElementById("adminStatTotal").textContent = stats.totalReports ?? list.length;
      document.getElementById("adminStatResolved").textContent = stats.resolvedReports ?? 0;
      document.getElementById("adminStatInProgress").textContent = stats.inProgressReports ?? 0;
      document.getElementById("adminStatUsers").textContent = stats.totalUsers ?? 2;
    } else {
      const resolved = list.filter(r => r.status === 'RESOLVED').length;
      const inProg = list.filter(r => r.status === 'IN_PROGRESS').length;
      document.getElementById("adminStatTotal").textContent = list.length;
      document.getElementById("adminStatResolved").textContent = resolved;
      document.getElementById("adminStatInProgress").textContent = inProg;
      document.getElementById("adminStatUsers").textContent = 2;
    }
  } catch (e) {}

  container.innerHTML = `
    <table style="width: 100%; border-collapse: collapse; text-align: left; font-size: 0.9rem;">
      <thead>
        <tr style="background: #f8fafc; border-bottom: 2px solid var(--border);">
          <th style="padding: 10px;">ID</th>
          <th style="padding: 10px;">Category</th>
          <th style="padding: 10px;">Location</th>
          <th style="padding: 10px;">Severity</th>
          <th style="padding: 10px;">Current Status</th>
          <th style="padding: 10px;">Action</th>
        </tr>
      </thead>
      <tbody>
        ${list.map(r => `
          <tr style="border-bottom: 1px solid var(--border);">
            <td style="padding: 10px;">#${r.id}</td>
            <td style="padding: 10px;">${r.category}</td>
            <td style="padding: 10px;"><strong>${r.location}</strong></td>
            <td style="padding: 10px;">${r.severity}</td>
            <td style="padding: 10px;"><span class="status-badge ${r.status ? r.status.toLowerCase() : 'reported'}">${r.status || 'REPORTED'}</span></td>
            <td style="padding: 10px;">
              <select onchange="updateReportStatusAdmin(${r.id}, this.value)" style="padding: 4px 8px; border-radius: 6px; font-size: 0.85rem;">
                <option value="REPORTED" ${r.status === 'REPORTED' ? 'selected' : ''}>REPORTED</option>
                <option value="UNDER_REVIEW" ${r.status === 'UNDER_REVIEW' ? 'selected' : ''}>UNDER REVIEW</option>
                <option value="IN_PROGRESS" ${r.status === 'IN_PROGRESS' ? 'selected' : ''}>IN PROGRESS</option>
                <option value="RESOLVED" ${r.status === 'RESOLVED' ? 'selected' : ''}>RESOLVED</option>
              </select>
            </td>
          </tr>
        `).join("")}
      </tbody>
    </table>
  `;
}

async function updateReportStatusAdmin(id, newStatus) {
  try {
    await ApiClient.updateReportStatusAdmin(id, newStatus, "Status updated by Warden");
  } catch (e) {}

  const reports = JSON.parse(localStorage.getItem("ecopulse_env_reports") || "[]");
  const target = reports.find(r => r.id === id);
  if (target) {
    target.status = newStatus;
    localStorage.setItem("ecopulse_env_reports", JSON.stringify(reports));
  }
  showToast(`Report #${id} marked as ${newStatus}`, "success");
  loadReportsFeed();
}

async function handleAdminBroadcastAlert(e) {
  e.preventDefault();
  const alertData = {
    type: document.getElementById("admAlertType").value,
    title: document.getElementById("admAlertTitle").value,
    message: document.getElementById("admAlertMsg").value,
    location: document.getElementById("admAlertLoc").value,
    severity: "WARNING",
    active: true
  };

  await ApiClient.createAdminAlert(alertData);
  showToast("Emergency alert broadcasted successfully!", "success");
  e.target.reset();
  loadAlerts();
}

/**
 * Filterable Eco Tips
 */
async function renderEcoTips() {
  const container = document.getElementById("ecoTipsContainer");
  if (!container) return;

  const catFilter = document.getElementById("tipCategoryFilter")?.value || "ALL";
  const ageFilter = document.getElementById("tipAgeFilter")?.value || "ALL";

  const allTips = [
    { cat: "SAVE_WATER", age: "KIDS", title: "Cup & Brush Technique", desc: "Leaving the tap running wastes up to 6 litres a minute.", action: "Use a small rinse glass when brushing teeth!" },
    { cat: "SAVE_WATER", age: "ELDERS", title: "Early Morning Garden Watering", desc: "Midday sun evaporates 30% of hose water before reaching roots.", action: "Water plants before 7:30 AM or with kitchen rinse water." },
    { cat: "REDUCE_FOOD_WASTE", age: "FAMILIES", title: "Plan 3-Day Grocery List", desc: "30% of fresh vegetables spoil because of unorganized buying.", action: "Check fridge inventory before market trips." },
    { cat: "REDUCE_AIR_POLLUTION", age: "ALL_AGES", title: "Kill Engine at 30s Red Signals", desc: "Idling for 10 seconds uses more gas than restarting.", action: "Cut the car or bike ignition at long traffic signals." },
    { cat: "REDUCE_PLASTIC", age: "KIDS", title: "Carry Cloth Water Flask", desc: "Disposable water bottles generate mountain loads of microplastics.", action: "Take a colorful steel flask to school and sports!" },
    { cat: "ENERGY_SAVING", age: "ELDERS", title: "Wall Switch Night Check", desc: "Standby vampire current takes up to 10% of monthly power.", action: "Switch off wall sockets for microwave and TVs at bedtime." },
    { cat: "SUSTAINABLE_TRANSPORT", age: "ALL_AGES", title: "Walk Trips Under 1.5 km", desc: "Short car trips emit triple the cold start pollution.", action: "Use walking or cycling for neighborhood grocery errands." },
    { cat: "RECYCLING", age: "FAMILIES", title: "Rinse Before Blue Bin", desc: "Sauce residue ruins whole bales of clean paper recycling.", action: "Give plastic milk pouches and tins a quick 2-second rinse." }
  ];

  const filtered = allTips.filter(t => {
    const matchCat = catFilter === "ALL" || t.cat === catFilter;
    const matchAge = ageFilter === "ALL" || t.age === ageFilter || t.age === "ALL_AGES";
    return matchCat && matchAge;
  });

  container.innerHTML = filtered.map(t => `
    <div style="background: #f8fafc; border: 1px solid var(--border); border-radius: 12px; padding: 18px;">
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
        <span class="status-badge" style="background: #e0f2fe; color: #0369a1;">${t.cat.replace('_', ' ')}</span>
        <span class="status-badge" style="background: #fef3c7; color: #92400e;">${t.age}</span>
      </div>
      <h4 style="font-size: 1.05rem; margin-bottom: 6px; color: #065f46;">${t.title}</h4>
      <p style="font-size: 0.88rem; color: var(--text-muted); margin-bottom: 10px;">${t.desc}</p>
      <div style="background: #ecfdf5; border-left: 3px solid #10b981; padding: 8px 12px; border-radius: 4px; font-size: 0.85rem; font-weight: 600; color: #047857;">
        👉 Action: ${t.action}
      </div>
    </div>
  `).join("");
}

/**
 * Community / Society Dashboard
 */
async function loadCommunityDashboard() {
  const stats = await ApiClient.getCommunityStats();
  document.getElementById("communityName").textContent = stats.name;
  document.getElementById("commWaterReduction").textContent = `${stats.waterReductionPct}%`;
  document.getElementById("commFoodSaved").textContent = `${stats.foodWasteReducedKg} kg`;
  document.getElementById("commTotalReports").textContent = stats.totalReports;
  document.getElementById("commResolvedReports").textContent = stats.resolvedReports;
  document.getElementById("commEcoScore").textContent = `${stats.ecoScore} / 100`;
  document.getElementById("commHighlightMsg").textContent = stats.highlightMessage;
}

/**
 * Personal Eco Impact UI
 */
function loadPersonalImpactUI() {
  const impact = ApiClient.getPersonalImpact();
  document.getElementById("impactWater").textContent = `${impact.waterSavedLitres} L`;
  document.getElementById("impactFood").textContent = `${impact.foodWasteAvoidedKg} kg`;
  document.getElementById("impactPlastic").textContent = `${impact.plasticAvoidedCount} Items`;
  document.getElementById("impactTransit").textContent = `${impact.publicTransportTrips}`;
  document.getElementById("impactRecycling").textContent = `${impact.recyclingActivitiesCount}`;
  document.getElementById("impactScore").textContent = `${impact.ecoImpactScore} pts`;
  document.getElementById("userImpactBadge").textContent = impact.impactBadge;
}

/**
 * Active Alerts
 */
async function loadAlerts() {
  const container = document.getElementById("alertsContainer");
  if (!container) return;

  const alerts = await ApiClient.getActiveAlerts();
  container.innerHTML = alerts.map(a => `
    <div style="background: #fff7ed; border-left: 5px solid #ea580c; border-radius: 8px; padding: 12px 18px; margin-bottom: 10px; display: flex; justify-content: space-between; align-items: center;">
      <div>
        <strong style="color: #9a3412;">⚠️ Environmental Alert: ${a.title}</strong>
        <p style="font-size: 0.88rem; color: #7c2d12; margin-top: 2px;">${a.message}</p>
      </div>
      <span class="status-badge" style="background: #fed7aa; color: #9a3412;">Active</span>
    </div>
  `).join("");
}

/**
 * Geolocation Detection
 */
function detectUserLocation() {
  if (!navigator.geolocation) {
    showToast("Geolocation is not supported by your browser.", "error");
    return;
  }

  showToast("Detecting your GPS location...", "success");
  navigator.geolocation.getCurrentPosition(
    position => {
      const lat = position.coords.latitude;
      const lon = position.coords.longitude;
      loadDashboardData(lat, lon, "Detected GPS Location");
      showToast("Updated with live satellite & sensor data!", "success");
    },
    error => {
      console.warn("Geolocation denied or unavailable:", error);
      showToast("Location permission denied. You can search any city in the search bar!", "error");
    },
    { timeout: 8000 }
  );
}

/**
 * City Search Handling
 */
async function handleCitySearch(event) {
  event.preventDefault();
  const input = document.getElementById("citySearchInput");
  const query = input.value.trim().toLowerCase();
  if (!query) return;

  if (CITY_GEO_LOOKUP[query]) {
    const city = CITY_GEO_LOOKUP[query];
    await loadDashboardData(city.lat, city.lon, city.name);
    showToast(`Loaded live environment for ${city.name}`, "success");
  } else {
    // Dynamic Geocoding via open Nominatim API
    showToast(`Searching coordinates for "${input.value}"...`, "success");
    try {
      const res = await fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`);
      const data = await res.json();
      if (data && data.length > 0) {
        const first = data[0];
        const lat = parseFloat(first.lat);
        const lon = parseFloat(first.lon);
        await loadDashboardData(lat, lon, first.display_name.split(',')[0]);
        showToast(`Loaded live data for ${first.display_name.split(',')[0]}`, "success");
      } else {
        showToast("City not found. Try searching Delhi, Mumbai, London, New York...", "error");
      }
    } catch (e) {
      showToast("Search network error. Loading default district.", "error");
    }
  }
}

/**
 * Speech Audio Narration Trigger
 */
function triggerReadAloud() {
  if (typeof VoiceAssistant !== "undefined" && AppState.airData) {
    VoiceAssistant.readDashboardSummary(AppState.airData, AppState.scoreData, currentLanguage);
  }
}

/**
 * Toggle View Mode: Standard vs Kid & Elder Simple View
 */
function toggleViewMode() {
  AppState.viewMode = AppState.viewMode === "standard" ? "simple" : "standard";
  localStorage.setItem("ecopulse_view_mode", AppState.viewMode);
  applyViewModeUI();
}

function applyViewModeUI() {
  const btn = document.getElementById("viewModeToggleBtn");
  if (AppState.viewMode === "simple") {
    document.body.classList.add("mode-simple");
    if (btn) btn.innerHTML = "📊 " + (t("modeStandard") || "Standard View");
  } else {
    document.body.classList.remove("mode-simple");
    if (btn) btn.innerHTML = "🧒 " + (t("modeSimple") || "Elder & Kid View");
  }
}

/**
 * Toggle User Role: Citizen vs Admin
 */
function toggleUserRole() {
  AppState.userRole = AppState.userRole === "CITIZEN" ? "ADMIN" : "CITIZEN";
  localStorage.setItem("ecopulse_role", AppState.userRole);
  applyRoleUI();
}

function applyRoleUI() {
  const indicator = document.getElementById("roleIndicator");
  const toggleBtnText = document.getElementById("roleToggleText");
  const adminTab = document.getElementById("adminNavTab");
  const adminSec = document.getElementById("admin-sec");

  if (AppState.userRole === "ADMIN") {
    indicator.textContent = "🛡️ Admin Warden Mode";
    indicator.style.color = "#fca5a5";
    toggleBtnText.textContent = "👤 Switch to Citizen";
    if (adminTab) adminTab.style.display = "block";
    if (adminSec) adminSec.style.display = "block";
    loadReportsFeed();
  } else {
    indicator.textContent = "👤 Citizen Mode";
    indicator.style.color = "#6ee7b7";
    toggleBtnText.textContent = "🛡️ Switch to Admin";
    if (adminTab) adminTab.style.display = "none";
    if (adminSec) adminSec.style.display = "none";
  }
}

/**
 * Map Filter
 */
function filterMap(cat) {
  document.querySelectorAll(".map-filter-btn").forEach(b => b.classList.remove("active"));
  event.target.classList.add("active");
  if (typeof EcoMap !== "undefined") {
    EcoMap.filterCategory(cat);
  }
}

/**
 * Form Submit Handlers
 */
async function handleReportSubmit(e) {
  e.preventDefault();
  const report = {
    category: document.getElementById("repCategory").value,
    location: document.getElementById("repLocation").value,
    severity: document.getElementById("repSeverity").value,
    description: document.getElementById("repDescription").value,
    imageUrl: document.getElementById("repImageUrl").value,
    latitude: AppState.currentLat,
    longitude: AppState.currentLon
  };

  await ApiClient.submitReport(report);
  closeModal("reportProblemModal");
  showToast("Report submitted to community wardens successfully!", "success");
  e.target.reset();
  loadReportsFeed();
  loadPersonalImpactUI();
}

async function handleFoodWasteSubmit(e) {
  e.preventDefault();
  const food = {
    location: document.getElementById("foodLocation").value,
    foodType: document.getElementById("foodType").value,
    approxQuantity: document.getElementById("foodQty").value,
    reason: document.getElementById("foodReason").value,
    description: document.getElementById("foodDesc").value
  };

  await ApiClient.submitFoodWaste(food);
  closeModal("foodWasteModal");
  showToast("Surplus food posted! Notified local shelters.", "success");
  e.target.reset();
  loadFoodWasteFeed();
  loadPersonalImpactUI();
}

async function handleWaterWasteSubmit(e) {
  e.preventDefault();
  const water = {
    location: document.getElementById("waterLocation").value,
    wasteType: document.getElementById("waterType").value,
    severity: document.getElementById("waterSeverity").value,
    description: document.getElementById("waterDesc").value
  };

  await ApiClient.submitWaterWaste(water);
  closeModal("waterWasteModal");
  showToast("Water leakage recorded! Society maintenance notified.", "success");
  e.target.reset();
  loadWaterWasteFeed();
  loadPersonalImpactUI();
}

function handlePersonalImpactSubmit(e) {
  e.preventDefault();
  const actionType = document.getElementById("actionType").value;
  const amount = parseFloat(document.getElementById("actionAmount").value) || 1;

  ApiClient.addPersonalImpact(actionType, amount);
  closeModal("logImpactModal");
  showToast("Green points added! Your Eco Score increased.", "success");
  e.target.reset();
  loadPersonalImpactUI();
}

/**
 * Navigation Helper
 */
function navigateToSection(secId) {
  document.querySelectorAll(".sub-nav button").forEach(b => b.classList.remove("active"));
  if (event && event.target && event.target.tagName === "BUTTON") {
    event.target.classList.add("active");
  }

  if (secId === 'home') {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    return;
  }
  const el = document.getElementById(secId);
  if (el) {
    el.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}

/**
 * Modal and Toast Helpers
 */
function openModal(id) {
  const el = document.getElementById(id);
  if (el) el.classList.add("active");
}

function closeModal(id) {
  const el = document.getElementById(id);
  if (el) el.classList.remove("active");
}

function showToast(message, type = "success") {
  const container = document.getElementById("toastContainer");
  if (!container) return;

  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${type === 'success' ? '✅' : '⚠️'}</span><span>${message}</span>`;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = "0";
    setTimeout(() => toast.remove(), 300);
  }, 4000);
}

/**
 * Authentication and Profile Portal Controls
 */
function switchAuthTab(tab) {
  const tabLogin = document.getElementById("authTabLogin");
  const tabRegister = document.getElementById("authTabRegister");
  const tabProfile = document.getElementById("authTabProfile");

  if (tabLogin) tabLogin.classList.toggle("active", tab === "login");
  if (tabRegister) tabRegister.classList.toggle("active", tab === "register");
  if (tabProfile) tabProfile.classList.toggle("active", tab === "profile");

  const paneLogin = document.getElementById("authPaneLogin");
  const paneRegister = document.getElementById("authPaneRegister");
  const paneProfile = document.getElementById("authPaneProfile");

  if (paneLogin) paneLogin.style.display = tab === "login" ? "block" : "none";
  if (paneRegister) paneRegister.style.display = tab === "register" ? "block" : "none";
  if (paneProfile) paneProfile.style.display = tab === "profile" ? "block" : "none";

  if (tab === "profile") {
    loadUserProfileData();
  }
}

function fillDemoLogin(username, password) {
  const u = document.getElementById("loginUsername");
  const p = document.getElementById("loginPassword");
  if (u) u.value = username;
  if (p) p.value = password;
}

async function handleUserLogin(e) {
  e.preventDefault();
  const u = document.getElementById("loginUsername").value.trim();
  const p = document.getElementById("loginPassword").value;

  try {
    const res = await ApiClient.login(u, p);
    showToast(`Welcome back, ${res.fullName || res.username}!`, "success");
    syncUserHeaderUI();
    AppState.userRole = res.role === "ROLE_ADMIN" ? "ADMIN" : "CITIZEN";
    applyRoleUI();
    switchAuthTab("profile");
  } catch (err) {
    showToast(err.message || "Login failed", "error");
  }
}

async function handleUserRegister(e) {
  e.preventDefault();
  const fullName = document.getElementById("regFullName").value.trim();
  const username = document.getElementById("regUsername").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const password = document.getElementById("regPassword").value;
  const communityId = parseInt(document.getElementById("regCommunityId").value) || 1;

  try {
    const res = await ApiClient.register({ fullName, username, email, password, communityId });
    showToast(`Account created for ${res.username}! You are now signed in.`, "success");
    syncUserHeaderUI();
    AppState.userRole = "CITIZEN";
    applyRoleUI();
    switchAuthTab("profile");
  } catch (err) {
    showToast(err.message || "Registration failed", "error");
  }
}

async function handleUserLogout() {
  await ApiClient.logout();
  showToast("You have been signed out.", "success");
  syncUserHeaderUI();
  AppState.userRole = "CITIZEN";
  localStorage.setItem("ecopulse_role", "CITIZEN");
  applyRoleUI();
  switchAuthTab("login");
}

async function loadUserProfileData() {
  const profile = await ApiClient.getUserProfile();
  const nameEl = document.getElementById("profDisplayName");
  const badgeEl = document.getElementById("profRoleBadge");
  const userEl = document.getElementById("profUsername");
  const emailEl = document.getElementById("profEmail");
  const commEl = document.getElementById("profCommunity");
  const tokEl = document.getElementById("profTokenStatus");

  if (nameEl) nameEl.textContent = profile.fullName || profile.username || "Eco Citizen";
  if (badgeEl) badgeEl.textContent = profile.role || "ROLE_USER";
  if (userEl) userEl.textContent = profile.username || "ecouser";
  if (emailEl) emailEl.textContent = profile.email || "citizen@ecopulse.org";
  if (commEl) commEl.textContent = profile.communityId === 2 ? "Harmony College Campus" : "Green Valley Residency";
  if (tokEl) tokEl.textContent = localStorage.getItem("ecopulse_token") ? "Connected (JWT Active)" : "Session Active";

  // Load user's submitted reports
  const container = document.getElementById("profReportsContainer");
  if (container) {
    const reports = await ApiClient.getReports();
    if (reports && reports.length > 0) {
      container.innerHTML = reports.slice(0, 5).map(r => `
        <div style="background: white; border: 1px solid var(--border); border-radius: 6px; padding: 8px 12px; font-size: 0.85rem; display: flex; justify-content: space-between; align-items: center;">
          <div><strong>${r.category}</strong> - ${r.location}</div>
          <span class="status-badge ${r.status ? r.status.toLowerCase() : 'reported'}">${r.status || 'REPORTED'}</span>
        </div>
      `).join("");
    } else {
      container.innerHTML = `<div style="font-size: 0.85rem; color: var(--text-muted);">No reports submitted yet.</div>`;
    }
  }
}

function syncUserHeaderUI() {
  const user = localStorage.getItem("ecopulse_username");
  const headerBtn = document.getElementById("userHeaderName");
  if (headerBtn) {
    if (user) {
      headerBtn.textContent = `👤 ${user}`;
    } else {
      headerBtn.textContent = `👤 Sign In / Profile`;
    }
  }
}
