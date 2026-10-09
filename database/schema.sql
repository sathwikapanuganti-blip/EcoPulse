-- ========================================================
-- EcoPulse: Database Schema (MySQL Compatible)
-- "Monitor. Understand. Act. Together for a Greener Future."
-- ========================================================

CREATE DATABASE IF NOT EXISTS ecopulse_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecopulse_db;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    community_id BIGINT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email (email),
    INDEX idx_user_username (username)
);

-- 2. Communities Table
CREATE TABLE IF NOT EXISTS communities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    type VARCHAR(50) NOT NULL DEFAULT 'RESIDENTIAL', -- RESIDENTIAL, COLLEGE, OFFICE, SOCIETY
    city VARCHAR(100) NOT NULL,
    area VARCHAR(100),
    total_members INT DEFAULT 1,
    eco_score INT DEFAULT 75,
    water_reduction_pct DECIMAL(5,2) DEFAULT 0.0,
    food_waste_reduced_kg DECIMAL(8,2) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Environmental Reports Table
CREATE TABLE IF NOT EXISTS environmental_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    category VARCHAR(50) NOT NULL, -- GARBAGE_DUMPING, PLASTIC, SMOKE, BAD_AIR, ILLEGAL_BURNING, OVERFLOWING_BINS, OTHER
    location VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 7),
    longitude DECIMAL(10, 7),
    severity VARCHAR(20) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, CRITICAL
    status VARCHAR(20) NOT NULL DEFAULT 'REPORTED', -- REPORTED, UNDER_REVIEW, IN_PROGRESS, RESOLVED
    description TEXT NOT NULL,
    image_url VARCHAR(500),
    admin_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_env_status (status),
    INDEX idx_env_category (category)
);

-- 4. Food Waste Reports Table
CREATE TABLE IF NOT EXISTS food_waste_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    location VARCHAR(255) NOT NULL,
    food_type VARCHAR(100) NOT NULL, -- COOKED_MEALS, VEGETABLES_FRUITS, BAKERY, PACKAGED, EVENT_BUFFET, OTHER
    approx_quantity VARCHAR(100) NOT NULL, -- e.g. "25 meals", "15 kg"
    quantity_meals INT DEFAULT 0,
    reason VARCHAR(255) NOT NULL, -- OVERCOOKING, EVENT_LEFTOVER, EXPIRY, CANCELLED_ORDER, OTHER
    description TEXT,
    event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image_url VARCHAR(500),
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE_FOR_DONATION', -- AVAILABLE_FOR_DONATION, PICKED_UP, COMPOSTED, DISPOSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_food_status (status)
);

-- 5. Water Waste Reports Table
CREATE TABLE IF NOT EXISTS water_waste_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    location VARCHAR(255) NOT NULL,
    waste_type VARCHAR(80) NOT NULL, -- LEAKING_PIPE, OVERFLOWING_TANK, OPEN_TAP, PIPE_BURST, EXCESSIVE_USAGE, OTHER
    severity VARCHAR(20) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, CRITICAL
    description TEXT NOT NULL,
    image_url VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'REPORTED', -- REPORTED, UNDER_REVIEW, IN_PROGRESS, RESOLVED
    estimated_litres_wasted_per_hour INT DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_water_status (status)
);

-- 6. Air Quality Cache / Records Table
CREATE TABLE IF NOT EXISTS air_quality_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(150) NOT NULL,
    latitude DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    aqi INT NOT NULL,
    aqi_standard VARCHAR(30) DEFAULT 'US_EPA',
    pm25 DECIMAL(6, 2),
    pm10 DECIMAL(6, 2),
    co DECIMAL(6, 2),
    no2 DECIMAL(6, 2),
    so2 DECIMAL(6, 2),
    o3 DECIMAL(6, 2),
    temperature DECIMAL(5, 2),
    humidity DECIMAL(5, 2),
    status_label VARCHAR(30) NOT NULL, -- SAFE, MODERATE, POOR, VERY_POOR, HAZARDOUS
    health_recommendation TEXT,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_aq_coords (latitude, longitude)
);

-- 7. Traffic Records Table
CREATE TABLE IF NOT EXISTS traffic_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(150) NOT NULL,
    latitude DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    congestion_level VARCHAR(30) NOT NULL, -- LOW, MODERATE, HIGH, SEVERE
    traffic_intensity INT DEFAULT 50, -- 0-100 scale
    pollution_risk VARCHAR(30) NOT NULL, -- LOW, MODERATE, ELEVATED, HIGH
    is_real_time BOOLEAN DEFAULT TRUE,
    data_source VARCHAR(80) DEFAULT 'COMMUNITY_SENSOR',
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. Alerts Table
CREATE TABLE IF NOT EXISTS alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL, -- AIR_QUALITY, TRAFFIC, WATER_WASTE, POLLUTION, SEVERE_WEATHER
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    severity VARCHAR(20) NOT NULL DEFAULT 'WARNING', -- INFO, WARNING, CRITICAL
    location VARCHAR(150),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. Personal Eco Impact Table
CREATE TABLE IF NOT EXISTS personal_eco_impact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    water_saved_litres INT DEFAULT 0,
    food_waste_avoided_kg DECIMAL(8,2) DEFAULT 0.0,
    plastic_avoided_count INT DEFAULT 0,
    public_transport_trips INT DEFAULT 0,
    recycling_activities_count INT DEFAULT 0,
    reports_submitted_count INT DEFAULT 0,
    eco_impact_score INT DEFAULT 10,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 10. Eco Tips Table
CREATE TABLE IF NOT EXISTS eco_tips (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL, -- SAVE_WATER, REDUCE_FOOD_WASTE, REDUCE_AIR_POLLUTION, REDUCE_PLASTIC, ENERGY_SAVING, SUSTAINABLE_TRANSPORT, RECYCLING, COMMUNITY_CLEANLINESS
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    easy_action TEXT NOT NULL,
    target_group VARCHAR(50) DEFAULT 'ALL_AGES', -- KIDS, ELDERS, FAMILIES, ALL_AGES
    language_code VARCHAR(10) DEFAULT 'en',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================================
-- Seed Initial Data
-- ========================================================

-- Initial Demo Communities
INSERT INTO communities (name, type, city, area, total_members, eco_score, water_reduction_pct, food_waste_reduced_kg)
VALUES 
('Green Valley Residency', 'RESIDENTIAL', 'Metropolis', 'Sector 4', 320, 84, 18.5, 142.0),
('Harmony College Campus', 'COLLEGE', 'Metropolis', 'North University Zone', 1250, 78, 14.0, 310.5),
('EcoTech Innovation Park', 'OFFICE', 'Metropolis', 'Cyber Hills', 850, 88, 22.0, 205.0),
('Civic Green Ward 14', 'SOCIETY', 'Metropolis', 'Riverside District', 2100, 71, 9.5, 480.0);

-- Initial Users (Passwords bcrypt hashed for 'ecopulse123' and 'admin123')
INSERT INTO users (username, email, password_hash, full_name, role, community_id)
VALUES
('ecouser', 'citizen@ecopulse.org', '$2a$10$w09ZlMv4q1q.P/ZcZk1Ueu0qH7k5.pUv2kI0s1b7n4X/ZJmB0m2sO', 'Eco Citizen', 'ROLE_USER', 1),
('admin', 'admin@ecopulse.org', '$2a$10$fJt9Lzqj69MvB8pD2UeG1eC7O1bWp.gC0vFhDkLz4A.vG2oT8sF0i', 'Community Green Warden', 'ROLE_ADMIN', 1);

-- Initial Eco Impact for Demo User
INSERT INTO personal_eco_impact (user_id, water_saved_litres, food_waste_avoided_kg, plastic_avoided_count, public_transport_trips, recycling_activities_count, reports_submitted_count, eco_impact_score)
VALUES (1, 145, 12.5, 38, 14, 9, 3, 85);

-- Initial Active Alerts
INSERT INTO alerts (type, title, message, severity, location, active)
VALUES
('AIR_QUALITY', 'Elevated PM2.5 in Industrial Belt', 'Air quality in the industrial and traffic hub has reached Poor levels (AQI 158). Sensitive groups and elders should wear masks outdoors.', 'WARNING', 'Industrial Sector', TRUE),
('WATER_WASTE', 'Main Tank Overflow Warning', 'Three residents reported fresh water overflowing near Block C. Maintenance dispatch initiated.', 'INFO', 'Sector 4 Community', TRUE);

-- Initial Practical Eco Tips
INSERT INTO eco_tips (category, title, description, easy_action, target_group, language_code)
VALUES
('SAVE_WATER', 'Turn off tap while brushing teeth', 'Leaving the tap running wastes up to 6 litres of drinking water every single minute.', 'Use a rinse cup instead of letting the tap run.', 'ALL_AGES', 'en'),
('REDUCE_FOOD_WASTE', 'Plan weekly meals before buying groceries', 'Up to 30% of household vegetables spoil before being eaten due to unorganized shopping.', 'Write a 3-day meal list before going to the market.', 'FAMILIES', 'en'),
('REDUCE_AIR_POLLUTION', 'Carpool or choose public transit', 'Private motor vehicle exhaust is the number one cause of local PM2.5 and ground-level ozone.', 'Take the metro or bus for your commute twice a week.', 'ALL_AGES', 'en'),
('REDUCE_PLASTIC', 'Carry a reusable cloth bag', 'Single-use plastic bags take over 400 years to decompose and choke city drains.', 'Keep a folded cloth bag in your backpack or vehicle at all times.', 'KIDS', 'en'),
('ENERGY_SAVING', 'Switch off appliances at the wall plug', 'Standby vampire power accounts for 5-10% of residential electricity bills.', 'Flip switch off when devices are fully charged.', 'ELDERS', 'en'),
('COMMUNITY_CLEANLINESS', 'Report open waste dumping immediately', 'Uncollected garbage attracts disease vectors and releases foul methane gas.', 'Snap a photo on EcoPulse so the municipality can clear it.', 'ALL_AGES', 'en');
