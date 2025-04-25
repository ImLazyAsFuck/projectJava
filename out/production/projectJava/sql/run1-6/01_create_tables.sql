drop database if exists project_java;
create database project_java;
use project_java;

create table account (
                         a_id int primary key auto_increment,
                         a_username varchar(50) not null unique,
                         a_password varchar(255) not null,
                         a_status enum('ACTIVE', 'INACTIVE', 'BLOCKED') not null default('ACTIVE'),
                         a_role enum('ADMIN', 'STUDENT')  not null default('STUDENT')
);

drop table if exists student;
create table student (
                         s_id int primary key auto_increment,
                         a_id int not null unique,
                         s_full_name varchar(100) not null,
                         s_dob date not null,
                         s_email varchar(100) not null unique,
                         s_sex bit not null,
                         s_phone varchar(20),
                         s_created_at datetime default(current_timestamp),
                         foreign key (a_id) references account(a_id)
);

create table course (
                        c_id int primary key auto_increment,
                        c_name varchar(100) not null unique,
                        c_duration int not null,
                        c_description varchar(255),
                        c_status enum('ACTIVE', 'DELETED'),
                        c_instructor varchar(100) not null,
                        c_created_at datetime default(current_timestamp)
);

drop table if exists enrollment;
create table enrollment (
                            e_id int primary key auto_increment,
                            s_id int not null,
                            c_id int not null,
                            e_status enum('WAITING', 'DENIED', 'CANCER', 'CONFIRMED') default 'WAITING',
                            e_registered_at datetime default current_timestamp,
                            foreign key (s_id) references student(s_id),
                            foreign key (c_id) references course(c_id)
);
