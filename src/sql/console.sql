drop database if exists student_and_course_management;
create database student_and_course_management;
use student_and_course_management;

create table admin (
                       a_id int primary key auto_increment,
                       a_username varchar(50) not null unique,
                       a_password varchar(255) not null
);

create table student (
                         s_id int primary key auto_increment,
                         s_username varchar(50) not null unique,
                         s_password varchar(255) not null,
                         s_status enum('ACTIVE', 'INACTIVE', 'BLOCKED'),
                         s_full_name varchar(100) not null,
                         s_dob date not null,
                         s_email varchar(100) not null unique,
                         s_sex bit not null,
                         s_phone varchar(20),
                         s_created_at datetime default current_timestamp
);

create table course (
                        c_id int primary key auto_increment,
                        c_name varchar(100) not null unique,
                        c_duration int not null,
                        c_description varchar(255),
                        c_instructor varchar(100) not null,
                        c_created_at datetime default current_timestamp
);

create table enrollment (
                            e_id int primary key auto_increment,
                            s_id int not null,
                            c_id int not null,
                            e_status enum('WAITING', 'DENIED', 'CANCER', 'CONFIRMED') default 'WAITING',
                            e_registered_at datetime default current_timestamp,
                            foreign key (s_id) references student(s_id),
                            foreign key (c_id) references course(c_id)
);

delimiter //
create procedure loginAsAdmin(in_u_name varchar(50), in_u_password varchar(255))
begin
    select a.a_id, a.a_username, a.a_password from admin a
    where a.a_username = in_u_name and a.a_password = in_u_password;
end;
delimiter //


delimiter //
create procedure loginAsStudent(in_u_name varchar(50), in_u_password varchar(255))
begin
    select s.s_id, s.s_username, s.s_password from student s
    where s.s_username = in_u_name and s.s_password = in_u_password;
end;
delimiter //

