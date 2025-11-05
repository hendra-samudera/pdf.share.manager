-- Tabel users dan authorities sudah dibuat di V1
-- Menambahkan admin user default hanya jika belum ada
INSERT INTO users (username, password, enabled) 
SELECT 'admin', '{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Memberikan otorisasi untuk admin hanya jika belum ada
INSERT INTO authorities (username, authority) 
SELECT 'admin', 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE username = 'admin' AND authority = 'ROLE_USER');

INSERT INTO authorities (username, authority) 
SELECT 'admin', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE username = 'admin' AND authority = 'ROLE_ADMIN');