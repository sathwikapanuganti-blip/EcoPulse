/**
 * EcoPulse Interactive Map Engine
 * Powered by Leaflet.js & OpenStreetMap (No secret API keys needed)
 * Displays: User location pin, environmental problem reports, water leaks, food donations & pollution hotspots
 */

const EcoMap = {
  mapInstance: null,
  markerLayers: {
    user: null,
    pollution: [],
    water: [],
    food: [],
    garbage: []
  },
  currentFilter: "ALL",

  init(containerId = "ecoMapContainer", centerLat = 28.6139, centerLon = 77.2090) {
    const container = document.getElementById(containerId);
    if (!container) return;

    if (typeof L === "undefined") {
      console.warn("Leaflet library not loaded, showing fallback list");
      this.showFallbackList();
      return;
    }

    if (this.mapInstance) {
      this.mapInstance.remove();
    }

    try {
      this.mapInstance = L.map(containerId, {
        center: [centerLat, centerLon],
        zoom: 13,
        zoomControl: true,
        scrollWheelZoom: false
      });

      // Free, open OpenStreetMap tile layer
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 18
      }).addTo(this.mapInstance);

      // Add user location pin
      this.setUserLocation(centerLat, centerLon, "Your Current Location");

      // Load mock and real markers
      this.renderSampleMarkers(centerLat, centerLon);

    } catch (e) {
      console.error("Map initialization failed:", e);
      this.showFallbackList();
    }
  },

  setUserLocation(lat, lon, label = "Your Location") {
    if (!this.mapInstance) return;

    if (this.markerLayers.user) {
      this.mapInstance.removeLayer(this.markerLayers.user);
    }

    const userIcon = L.divIcon({
      className: "custom-map-pin user-pin",
      html: `<div class="pin-pulse"></div><div class="pin-badge user-badge">📍 ${label}</div>`,
      iconSize: [40, 40],
      iconAnchor: [20, 20]
    });

    this.markerLayers.user = L.marker([lat, lon], { icon: userIcon })
      .addTo(this.mapInstance)
      .bindPopup(`<b>📍 ${label}</b><br>Coordinates: ${lat.toFixed(4)}, ${lon.toFixed(4)}`)
      .openPopup();

    this.mapInstance.panTo([lat, lon]);
  },

  renderSampleMarkers(baseLat, baseLon) {
    if (!this.mapInstance) return;

    // Clear previous markers
    ["pollution", "water", "food", "garbage"].forEach(k => {
      this.markerLayers[k].forEach(m => this.mapInstance.removeLayer(m));
      this.markerLayers[k] = [];
    });

    const items = [
      {
        type: "water",
        lat: baseLat + 0.008,
        lon: baseLon + 0.006,
        title: "💧 Main Pipe Leakage",
        desc: "Severity: Critical (~35 L/hr)<br>Status: In Progress",
        badge: "💧 Water Leak"
      },
      {
        type: "food",
        lat: baseLat - 0.007,
        lon: baseLon + 0.009,
        title: "🍲 Surplus Buffet Food Available",
        desc: "35 Fresh Meals ready for pickup by local shelter<br>Status: Available",
        badge: "🍲 Surplus Food"
      },
      {
        type: "garbage",
        lat: baseLat + 0.012,
        lon: baseLon - 0.008,
        title: "🗑️ Plastic & Garbage Dumped",
        desc: "Unsegregated waste on footpath<br>Status: Reported to Warden",
        badge: "🗑️ Garbage"
      },
      {
        type: "pollution",
        lat: baseLat - 0.011,
        lon: baseLon - 0.012,
        title: "⚠️ High Traffic Smog Corridor",
        desc: "Elevated PM2.5 detected near truck highway ring<br>AQI: 165 (Very Poor)",
        badge: "⚠️ Smog Zone"
      }
    ];

    items.forEach(item => {
      const icon = L.divIcon({
        className: `custom-map-pin ${item.type}-pin`,
        html: `<div class="pin-badge ${item.type}-badge">${item.badge}</div>`,
        iconSize: [36, 36],
        iconAnchor: [18, 18]
      });

      const marker = L.marker([item.lat, item.lon], { icon: icon })
        .addTo(this.mapInstance)
        .bindPopup(`<b>${item.title}</b><p>${item.desc}</p>`);

      this.markerLayers[item.type].push(marker);
    });
  },

  filterCategory(category) {
    this.currentFilter = category;
    const allCategories = ["pollution", "water", "food", "garbage"];
    allCategories.forEach(cat => {
      const markers = this.markerLayers[cat];
      markers.forEach(m => {
        if (category === "ALL" || category.toLowerCase() === cat) {
          if (!this.mapInstance.hasLayer(m)) this.mapInstance.addLayer(m);
        } else {
          if (this.mapInstance.hasLayer(m)) this.mapInstance.removeLayer(m);
        }
      });
    });
  },

  showFallbackList() {
    const container = document.getElementById("ecoMapContainer");
    if (!container) return;
    container.innerHTML = `
      <div class="map-fallback-view">
        <h4>🗺️ Environmental Reports & Hotspots List</h4>
        <p class="muted">Interactive map fallback: Below are active community verified coordinates.</p>
        <div class="fallback-list-cards">
          <div class="fallback-card water">
            <span class="badge">💧 Water Leak</span>
            <strong>Central Line Leakage (Block C)</strong>
            <p>Severity: Critical. Estimated loss ~35 L/hr. Status: Under repair.</p>
          </div>
          <div class="fallback-card food">
            <span class="badge">🍲 Food Sharing</span>
            <strong>35 Surplus Dinner Meals (Community Hall)</strong>
            <p>Packed & refrigerated. Free for local shelter volunteers.</p>
          </div>
          <div class="fallback-card garbage">
            <span class="badge">🗑️ Waste Dumping</span>
            <strong>Plastic Waste Pile (Near Play Area)</strong>
            <p>Action requested. Scheduled for municipal cleanup tomorrow morning.</p>
          </div>
          <div class="fallback-card pollution">
            <span class="badge">⚠️ Traffic & Smog</span>
            <strong>Industrial Outer Ring Road Corridor</strong>
            <p>AQI 162. High diesel truck particulate concentration.</p>
          </div>
        </div>
      </div>
    `;
  }
};
