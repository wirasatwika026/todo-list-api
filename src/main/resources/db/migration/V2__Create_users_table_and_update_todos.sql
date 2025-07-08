-- -- Create users table
-- CREATE TABLE IF NOT EXISTS users (
--     id BIGSERIAL PRIMARY KEY,
--     username VARCHAR(50) UNIQUE NOT NULL,
--     email VARCHAR(100) UNIQUE NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     first_name VARCHAR(50),
--     last_name VARCHAR(50),
--     role VARCHAR(20) NOT NULL DEFAULT 'USER',
--     enabled BOOLEAN NOT NULL DEFAULT TRUE,
--     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- -- Add user_id column to todos table
-- ALTER TABLE todos ADD COLUMN IF NOT EXISTS user_id BIGINT;

-- -- Add foreign key constraint
-- ALTER TABLE todos ADD CONSTRAINT IF NOT EXISTS fk_todos_user_id 
--     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- -- Create indexes for better performance
-- CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
-- CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
-- CREATE INDEX IF NOT EXISTS idx_todos_user_id ON todos(user_id);

-- -- Insert default admin user (password: admin123)
-- INSERT INTO users (username, email, password, first_name, last_name, role, enabled)
-- VALUES ('admin', 'admin@todoapp.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPHOhPOse', 'Admin', 'User', 'ADMIN', true)
-- ON CONFLICT (username) DO NOTHING;
