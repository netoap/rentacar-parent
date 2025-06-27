--INSERT INTO users (username, password, email, active)
--VALUES ('admin', 'admin123', 'admin@rentacar.com', true)
--ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (user_id, role)
SELECT id, 'ROLE_ADMIN' FROM users WHERE username = 'admin'
ON CONFLICT DO NOTHING;
