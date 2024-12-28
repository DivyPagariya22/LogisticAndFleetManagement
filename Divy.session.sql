
DELETE FROM routes;

-- Reset the auto-increment if your routes table uses it
ALTER TABLE routes AUTO_INCREMENT = 1;

-- Insert new routes with balanced connections
-- Each city is connected to nearby cities to avoid a skewed graph
INSERT INTO routes (source_city_id, destination_city_id, distance, travel_time) VALUES
-- Delhi (1) connections
('1', '11', 250, 180),  -- Delhi - Chandigarh
('1', '10', 500, 360),  -- Delhi - Lucknow
('1', '9', 280, 200),   -- Delhi - Jaipur

-- Mumbai (2) connections
('2', '7', 150, 120),   -- Mumbai - Pune
('2', '12', 270, 180),  -- Mumbai - Surat
('2', '15', 400, 300),  -- Mumbai - Vadodara

-- Kolkata (3) connections
('3', '14', 550, 420),  -- Kolkata - Patna
('3', '10', 750, 540),  -- Kolkata - Lucknow

-- Chennai (4) connections
('4', '5', 350, 240),   -- Chennai - Bangalore
('4', '6', 600, 420),   -- Chennai - Hyderabad

-- Bangalore (5) connections
('5', '6', 570, 400),   -- Bangalore - Hyderabad
('5', '7', 840, 600),   -- Bangalore - Pune

-- Hyderabad (6) connections
('6', '7', 560, 420),   -- Hyderabad - Pune
('6', '13', 980, 720),  -- Hyderabad - Indore

-- Pune (7) connections
('7', '13', 580, 420),  -- Pune - Indore
('7', '15', 440, 300),  -- Pune - Vadodara

-- Ahmedabad (8) connections
('8', '12', 260, 180),  -- Ahmedabad - Surat
('8', '15', 120, 90),   -- Ahmedabad - Vadodara
('8', '9', 650, 480),   -- Ahmedabad - Jaipur

-- Jaipur (9) connections
('9', '11', 510, 360),  -- Jaipur - Chandigarh
('9', '13', 550, 420),  -- Jaipur - Indore

-- Lucknow (10) connections
('10', '14', 480, 360), -- Lucknow - Patna
('10', '11', 800, 600), -- Lucknow - Chandigarh

-- Additional cross-connections for better path options
('12', '13', 450, 330), -- Surat - Indore
('13', '15', 380, 280), -- Indore - Vadodara
('14', '10', 480, 360)  -- Patna - Lucknow
;