-- Default admin user
-- Email: admin@company.com | Password: admin123
INSERT IGNORE INTO users (name, email, password, role) VALUES
('Admin User', 'admin@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');

-- Default regular user
-- Email: john@company.com | Password: user123
INSERT IGNORE INTO users (name, email, password, role) VALUES
('John Doe', 'john@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER');
