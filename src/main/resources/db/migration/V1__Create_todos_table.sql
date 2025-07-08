-- -- Create todos table
-- CREATE TABLE IF NOT EXISTS todos (
--     id BIGSERIAL PRIMARY KEY,
--     title VARCHAR(255) NOT NULL,
--     description TEXT,
--     completed BOOLEAN NOT NULL DEFAULT FALSE,
--     priority VARCHAR(10) NOT NULL DEFAULT 'MEDIUM',
--     due_date TIMESTAMP,
--     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- -- Create index on completion status for better query performance
-- CREATE INDEX IF NOT EXISTS idx_todos_completed ON todos(completed);

-- -- Create index on priority for better query performance
-- CREATE INDEX IF NOT EXISTS idx_todos_priority ON todos(priority);

-- -- Create index on due_date for better query performance
-- CREATE INDEX IF NOT EXISTS idx_todos_due_date ON todos(due_date);

-- -- Create index on title for search functionality
-- CREATE INDEX IF NOT EXISTS idx_todos_title ON todos USING gin(to_tsvector('english', title));
