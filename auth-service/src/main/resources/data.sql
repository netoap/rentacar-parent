INSERT INTO users (username, password, email, active)
VALUES ('admin', '$2a$10$7sBJLd8iThPEF6XpIjdLM.q9K7e8tsZo2v3tLw0Dd1XWdAiK/FwbS', 'admin@rentacar.com', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (user_id, role)
SELECT id, 'ROLE_ADMIN' FROM users WHERE username = 'admin'
ON CONFLICT DO NOTHING;
