-- Schema initialization for H2 Database with R2DBC
-- This script creates the necessary tables for the hex4j-webflux application

-- Drop table if exists (for development)
DROP TABLE IF EXISTS roles;

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert some sample data for testing
INSERT INTO roles (name, created_at) VALUES 
    ('ADMIN', CURRENT_TIMESTAMP),
    ('USER', CURRENT_TIMESTAMP),
    ('MODERATOR', CURRENT_TIMESTAMP);