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

delimiter //
create procedure find_all_course()
begin
    select c_id, c_name, c_duration, c_description, c_instructor, c_created_at from course c;
end;
delimiter //

delimiter //
create procedure find_courses_by_page(
    in page_number int,
    in page_size int
)
begin
    declare offset_value int;
    set offset_value = (page_number - 1) * page_size;

    select c_id, c_name, c_duration, c_description, c_instructor, c_created_at
    from course
    order by c_created_at desc
    limit offset_value, page_size;
end;
delimiter //


delimiter ;

delimiter //
create procedure insert_course(
    in p_name varchar(100),
    in p_duration int,
    in p_description varchar(255),
    in p_instructor varchar(100),
    out return_code int
)
begin
    declare existing_count int;
    select count(*) into existing_count from course where c_name = p_name;
    if existing_count > 0 then
        set return_code = 1;
    else
        insert into course(c_name, c_duration, c_description, c_instructor)
        values (p_name, p_duration, p_description, p_instructor);
        set return_code = 0;
    end if;
end;
delimiter //

delimiter //

create procedure update_course(
    in p_id int,
    in p_name varchar(100),
    in p_duration int,
    in p_description varchar(255),
    in p_instructor varchar(100),
    out return_code int
)
begin
    declare course_exists int;
    declare name_conflict int;
    select count(*) into course_exists from course where c_id = p_id;
    if course_exists = 0 then
        set return_code = 2;
    else
        select count(*) into name_conflict from course
        where c_name = p_name and c_id != p_id;

        if name_conflict > 0 then
            set return_code = 1;
        else
            update course
            set c_name = p_name,
                c_duration = p_duration,
                c_description = p_description,
                c_instructor = p_instructor
            where c_id = p_id;
            set return_code = 0;
        end if;
    end if;
end //

delimiter ;
