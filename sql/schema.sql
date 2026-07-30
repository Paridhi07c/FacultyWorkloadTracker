-- Faculty Workload Tracker - MySQL Schema
-- Run: mysql -u root -p < sql/schema.sql

CREATE DATABASE IF NOT EXISTS faculty_workload
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE faculty_workload;

CREATE TABLE IF NOT EXISTS faculty (
    faculty_id      INT          NOT NULL PRIMARY KEY,
    faculty_name    VARCHAR(100) NOT NULL,
    department      VARCHAR(100) NOT NULL,
    subject         VARCHAR(100) NOT NULL,
    hours_per_week  INT          NOT NULL CHECK (hours_per_week BETWEEN 1 AND 60),
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Sample seed data (optional)
INSERT IGNORE INTO faculty (faculty_id, faculty_name, department, subject, hours_per_week) VALUES
(101, 'Rahul Sharma', 'Computer Science', 'Java Programming', 12),
(102, 'Priya Singh', 'Information Technology', 'Database Management', 10);

CREATE INDEX idx_faculty_department ON faculty (department);
