drop database if exists StudentAndCourseManagement;
create database StudentAndCourseManagement;
use StudentAndCourseManagement;

CREATE TABLE Admin (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE Student (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         dob DATE NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         sex BIT NOT NULL, -- 1: Nam, 0: Nữ
                         phone VARCHAR(20),
                         password VARCHAR(255) NOT NULL,
                         status enum('ACTIVE', 'INACTIVE', 'BLOCK'),
                         create_at DATE DEFAULT(NOW())
);

CREATE TABLE Course (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        duration INT NOT NULL, -- thời lượng (giờ)
                        instructor VARCHAR(100) NOT NULL,
                        create_at DATE DEFAULT(NOW())
);

CREATE TABLE Enrollment (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            student_id INT NOT NULL,
                            course_id INT NOT NULL,
                            registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            status ENUM('WAITING', 'DENIED', 'CANCER', 'CONFIRMED') DEFAULT 'WAITING',
                            FOREIGN KEY (student_id) REFERENCES Student(id),
                            FOREIGN KEY (course_id) REFERENCES Course(id)
);