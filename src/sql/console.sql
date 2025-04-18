drop database if exists student_and_course_management;
create database student_and_course_management;
use student_and_course_management;

create table account (
                         a_id int primary key auto_increment,
                         a_username varchar(50) not null unique,
                         a_password varchar(255) not null,
                         a_status enum('ACTIVE', 'INACTIVE', 'BLOCKED') default('ACTIVE'),
                         a_role enum('ADMIN', 'STUDENT') default('STUDENT')
);

create table student (
                         s_id int primary key auto_increment,
                         a_id int not null,
                         s_full_name varchar(100) not null,
                         s_dob date not null,
                         s_email varchar(100) not null unique,
                         s_sex bit not null,
                         s_phone varchar(20),
                         s_created_at datetime default current_timestamp,
                         foreign key (a_id) references account(a_id)
);

create table course (
                        c_id int primary key auto_increment,
                        c_name varchar(100) not null unique,
                        c_duration int not null,
                        c_description varchar(255),
                        c_status enum('ACTIVE', 'INACTIVE', 'DELETE'),
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
create procedure login(in_u_name varchar(50), in_u_password varchar(255))
begin
    select a.a_id, a.a_username, a.a_password, a_status, a.a_role from account a
    where a.a_username = in_u_name and a.a_password = in_u_password;
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
    in page_size int,
    out totalItems int
)
begin
    declare offset_value int;

    set offset_value = (page_number - 1) * page_size;

    select count(c_id) into totalItems from course;

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
    in p_instructor varchar(100)
)
begin
    insert into course(c_name, c_duration, c_description, c_instructor)
    values (p_name, p_duration, p_description, p_instructor);
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
    select count(c_id) into course_exists from course where c_id = p_id;
    if course_exists = 0 then
        set return_code = 2;
    else
        select count(c_id) into name_conflict from course
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

delimiter //
create procedure delete_course(in_id int, out return_code int)
begin
    declare student_count int;

    if exists (select 1 from course where c_id = in_id) then
        select count(*) into student_count
        from enrollment
        where c_id = in_id;

        if student_count = 0 then
            delete from course where c_id = in_id;
            set return_code = 0;
        else
            set return_code = 2;
        end if;
    else
        set return_code = 1;
    end if;
end;
delimiter //

delimiter //
create procedure find_course_by_id(id int)
begin
    select c_id, c_name, c_duration, c_description, c_instructor, c_created_at from course
    where c_id = id;
end;
delimiter //

DELIMITER //

CREATE PROCEDURE find_courses_by_name(
    IN in_name VARCHAR(100),
    IN page_number INT,
    IN page_size INT,
    OUT totalItems INT
)
BEGIN
    DECLARE offset_value INT;
    SET offset_value = (page_number - 1) * page_size;
    SELECT COUNT(c_id) INTO totalItems
    FROM course
    WHERE LOWER(c_name) LIKE CONCAT('%', LOWER(in_name), '%');
    SELECT c_id, c_name, c_duration, c_description, c_instructor, c_created_at
    FROM course
    WHERE LOWER(c_name) LIKE CONCAT('%', LOWER(in_name), '%')
    ORDER BY c_created_at DESC
    LIMIT page_size OFFSET offset_value;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE sort_course_by_name(IN sort_type ENUM('asc', 'desc'))
BEGIN
    IF sort_type = 'asc' THEN
        SELECT c_id, c_name, c_duration, c_description, c_instructor, c_created_at
        FROM course
        ORDER BY c_name ASC;
    ELSE
        SELECT c_id, c_name, c_duration, c_description, c_instructor, c_created_at
        FROM course
        ORDER BY c_name DESC;
    END IF;
END;
//
DELIMITER ;

delimiter //
create procedure is_name_unique(in_name varchar(100))
begin
    select (c_id) from course
    where c_name = in_name;
end;
delimiter //

delimiter //
create procedure is_email_exists(in_email varchar(100))
begin
    select (s_id) from student
    where s_email = in_email;
end;
delimiter //

delimiter //
create procedure find_all_student()
begin
    select s_id, a_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at from student;
end;
delimiter //

delimiter //
create procedure find_student_by_page(page int, size int, totalItems int)
begin
    declare offset_value int;

    set offset_value = (page - 1) * size;

    select count(s_id) into totalItems from student;

    select s_id, a_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at
    from student
    order by s_created_at desc
    limit offset_value, size;
end;
delimiter //

delimiter //
# create procedure create_student()
delimiter //

delimiter //
# create procedure update_student(id int)
# begin
#     update student
#         set
# end;
delimiter //

# delimiter //
# create procedure delete_student(id int)
# begin
#
# end;
# delimiter //