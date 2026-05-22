INSERT INTO Users (username, password_hash, email, full_name, role, is_active, created_at, updated_at)
VALUES
('admin_user', '$2a$10$7QJ8eJ9z8Q9z8Q9z8Q9z8O', 'admin@example.com', 'Admin User', 'ROLE_ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('teacher_user', '$2a$10$7QJ8eJ9z8Q9z8Q9z8Q9z8O', 'teacher@example.com', 'Teacher User', 'ROLE_TEACHER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
