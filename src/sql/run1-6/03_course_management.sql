use project_java;

delimiter //
drop procedure if exists find_all_course;
create procedure find_all_course()
begin
    select c_id, c_name, c_duration, c_description, c_instructor, c_created_at from course c where c_status = 'ACTIVE';
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
    where c_status = 'ACTIVE'
    order by c_created_at desc
    limit offset_value, page_size;
end;
delimiter //


delimiter ;

delimiter //
drop procedure if exists insert_course;
create procedure insert_course(
    in p_name varchar(100),
    in p_duration int,
    in p_description varchar(255),
    in p_instructor varchar(100),
    out return_code int
)
begin
    declare existing_status varchar(20);

    select c_status
    into existing_status
    from course
    where c_name = p_name;

    if existing_status = 'DELETED' then
        update course
        set c_status      = 'ACTIVE'
        where c_name = p_name;
        set return_code = 0;
    elseif exists (select 1 from course where c_name = p_name) then
        set return_code = 1;
    else
        insert into course(c_name, c_duration, c_description, c_instructor, c_status)
        values (p_name, p_duration, p_description, p_instructor, 'ACTIVE');
        set return_code = 0;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists update_course;
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
    declare current_name varchar(100);

    select count(c_id) into course_exists from course where c_id = p_id;
    if course_exists = 0 then
        set return_code = 2;
    else
        select c_name into current_name from course where c_id = p_id;
        if current_name = p_name then
            update course
            set c_name        = p_name,
                c_duration    = p_duration,
                c_description = p_description,
                c_instructor  = p_instructor
            where c_id = p_id;
            set return_code = 0;
        else
            select count(c_id)
            into name_conflict
            from course
            where c_name = p_name;

            if name_conflict > 0 then
                set return_code = 1;
            else
                update course
                set c_name        = p_name,
                    c_duration    = p_duration,
                    c_description = p_description,
                    c_instructor  = p_instructor
                where c_id = p_id;
                set return_code = 0;
            end if;
        end if;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists delete_course;
create procedure delete_course(in_id int, out return_code int)
begin
    declare student_count int;
    declare course_status varchar(20);

    select c_status
    into course_status
    from course
    where c_id = in_id;

    if course_status is null or course_status = 'DELETED' then
        set return_code = 1;
    else
        select count(c_id)
        into student_count
        from enrollment
        where c_id = in_id;

        if student_count = 0 then
            update course set c_status = 'DELETED' where c_id = in_id;
            set return_code = 0;
        else
            set return_code = 2;
        end if;
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
    WHERE LOWER(c_name) LIKE CONCAT('%', LOWER(in_name), '%') and c_status = 'ACTIVE'
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
drop procedure if exists is_course_name_unique;
create procedure is_course_name_unique(
    in in_name varchar(100),
    out return_code int
)
begin
    declare course_status varchar(20);

    select c_status
    into course_status
    from course
    where c_name = in_name;

    if course_status is null then
        set return_code = 1; -- not found
    elseif course_status = 'DELETED' then
        set return_code = 2; -- found but deleted
    else
        set return_code = 0; -- found active
    end if;
end;
delimiter //

delimiter //
drop procedure if exists active_back_the_course;
create procedure active_back_the_course(in_c_name varchar(100))
begin
    update course
        set c_status = 'ACTIVE'
        where c_name = in_c_name;
end;
delimiter //