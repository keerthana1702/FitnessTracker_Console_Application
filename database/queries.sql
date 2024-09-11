CREATE DATABASE fitness_tracker;
USE fitness_tracker;
CREATE TABLE coach (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);
Alter table coach
ADD column email varchar(100);
INSERT INTO coach (username, password) VALUES ('coach1', 'password123');
SELECT * FROM coach;
CREATE TABLE fitness_program (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category ENUM('Men', 'Women', 'Children', 'Senior Citizens') NOT NULL,
    program_name VARCHAR(100) NOT NULL,
    description TEXT,
    duration VARCHAR(50), 
    coach_id INT, 
    FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE);
    
ALTER TABLE fitness_program
ADD COLUMN age_limit VARCHAR(50),           
ADD COLUMN min_qualification VARCHAR(100),  
ADD COLUMN price DECIMAL(10, 2);         

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE diet_plan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    program_id INT, 
    meal_time ENUM('Breakfast', 'Lunch', 'Dinner', 'Snack') NOT NULL,
    food_items VARCHAR(255),
    calories INT,
    FOREIGN KEY (program_id) REFERENCES fitness_program(id) ON DELETE CASCADE
);
CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,  
    program_id INT,  
    application_date DATE,
    status ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (program_id) REFERENCES fitness_program(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
SHOW tables;


