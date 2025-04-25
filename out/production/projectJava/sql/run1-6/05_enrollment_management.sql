use project_java;

delimiter //
create procedure is_course_exists(in_id int)
begin
    declare course_count int;

    select count(c_id) into course_count
    from enrollment
    where c_id = in_id;

    if course_count > 0 then
        select 1 as exists_flag;
    else
        select 0 as exists_flag;
    end if;
end;
delimiter //;

delimiter //
create procedure is_student_exists(in_id int)
begin
    declare student_count int;

    select count(s_id) into student_count
    from enrollment
    where s_id = in_id;

    if student_count > 0 then
        select 1 as exists_flag;
    else
        select 0 as exists_flag;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists display_student_by_course;
create procedure display_student_by_course(
    in_c_id int,
    page int,
    size int,
    out totalItems int
)
begin
    declare offset_value int;
    set offset_value = (page - 1) * size;

    select count(s_id) into totalItems
    from enrollment
    where c_id = in_c_id;

    select s.s_id, s.s_full_name, s.s_email, s.s_dob, s.s_phone, s.s_sex, s.s_created_at
    from enrollment e
             join student s on e.s_id = s.s_id
    where e.c_id = in_c_id
    limit size offset offset_value;
end;
delimiter //

delimiter //
drop procedure if exists add_student_to_enrollment;
create procedure add_student_to_enrollment(
    in in_c_id int,
    in in_s_id int,
    out return_code int
)
begin
    declare current_status varchar(20);
    declare account_status varchar(20);
    set return_code = 0; -- success

    if not exists (select 1 from student where s_id = in_s_id) then
        set return_code = 1; -- not found student
    elseif not exists (select 1 from course where c_id = in_c_id) then
        set return_code = 2; -- not found course
    else
        select a.a_status
        into account_status
        from student s
                 join account a on s.a_id = a.a_id
        where s.s_id = in_s_id;

        if account_status in ('INACTIVE', 'BLOCKED') then
            set return_code = 5; -- student is inactive or blocked
        else
            select e_status
            into current_status
            from enrollment
            where s_id = in_s_id
              and c_id = in_c_id;

            if current_status = 'DENIED' then
                set return_code = 3; -- student is denied for this course
            elseif current_status = 'CANCER' then
                update enrollment
                set e_status = 'WAITING'
                where s_id = in_s_id
                  and c_id = in_c_id;
            elseif current_status is null then
                insert into enrollment (s_id, c_id)
                values (in_s_id, in_c_id);
            else
                set return_code = 4; -- student got confirmed to this enrollment
            end if;
        end if;
    end if;
end;
delimiter //


delimiter //
drop procedure if exists delete_student_from_course;
create procedure delete_student_from_course(
    in in_s_id int,
    in in_c_id int,
    out return_code int
)
begin
    set return_code = 0;

    if not exists (select 1 from student where s_id = in_s_id) then
        set return_code = 1;
    elseif not exists (select 1 from course where c_id = in_c_id) then
        set return_code = 2;
    elseif not exists (select 1 from enrollment where s_id = in_s_id and c_id = in_c_id) then
        set return_code = 3;
    elseif (select e_status from enrollment where s_id = in_s_id and c_id = in_c_id) = 'CONFIRMED' then
        set return_code = 4;
    else
        update enrollment
        set e_status = 'DENIED'
        where in_c_id = c_id
          and in_s_id = s_id;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists display_current_acc_course;
create procedure display_current_acc_course(in in_s_id int)
begin
    select c.c_id,
           c.c_name,
           c.c_duration,
           c.c_description,
           c.c_instructor,
           c.c_created_at
    from enrollment e
             inner join course c on e.c_id = c.c_id
    where e.s_id = in_s_id
      and e.e_status in ('WAITING', 'CONFIRMED');
end;
delimiter //

delimiter //
drop procedure if exists find_all_enrollment;
create procedure find_all_enrollment()
begin
    select e_id, s_id, c_id, e_status, e_registered_at from enrollment;
end;
delimiter //

delimiter //
drop procedure if exists find_course_by_student_id;
create procedure find_course_by_student_id(
    in_s_id int,
    page int,
    size int,
    out total_items int
)
begin
    declare offset_value int;
    set offset_value = (page - 1) * size;

    select count(c_id) into total_items
    from enrollment
    where s_id = in_s_id;

    select c.c_id, c_name, c_duration, c_description, c_instructor, c.c_status , c_created_at
    from enrollment e
             join course c on e.c_id = c.c_id
    where e.s_id = in_s_id
    order by c.c_id
    limit size offset offset_value;
end;
delimiter //

delimiter //
drop procedure if exists approve_student;
create procedure approve_student(
    in p_c_id int,
    in p_s_id int,
    out return_code int
)
begin
    declare v_status enum ('WAITING', 'DENIED', 'CANCER', 'CONFIRMED');
    declare account_status varchar(20);

    select a.a_status
    into account_status
    from student s
             join account a on s.a_id = a.a_id
    where s.s_id = p_s_id;

    if account_status in ('INACTIVE', 'BLOCKED') then
        set return_code = 6;
    else
        select e_status
        into v_status
        from enrollment
        where c_id = p_c_id
          and s_id = p_s_id;

        if v_status is null then
            set return_code = 1;
        elseif v_status = 'WAITING' then
            update enrollment
            set e_status = 'CONFIRMED'
            where c_id = p_c_id
              and s_id = p_s_id;

            set return_code = 0;
        elseif v_status = 'DENIED' then
            set return_code = 2;
        elseif v_status = 'CANCER' then
            set return_code = 3;
        elseif v_status = 'CONFIRMED' then
            set return_code = 4;
        else
            set return_code = 5;
        end if;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists student_cancel_enrollment;
create procedure student_cancel_enrollment(
    in in_c_id int,
    in in_s_id int,
    out return_code int
)
begin
    set return_code = 0;

    if not exists (select 1 from student where s_id = in_s_id) then
        set return_code = 1;
    elseif not exists (select 1 from course where c_id = in_c_id) then
        set return_code = 2;
    elseif not exists (select 1 from enrollment where s_id = in_s_id and c_id = in_c_id) then
        set return_code = 3;
    elseif (select e_status from enrollment where s_id = in_s_id and c_id = in_c_id) = 'CONFIRMED' then
        set return_code = 4;
    elseif ((select e_status from enrollment where s_id = in_s_id and c_id = in_c_id) = 'DENIED') then
        set return_code = 5;
    else
        update enrollment set e_status = 'CANCER'
        where s_id = in_s_id and c_id = in_c_id;
    end if;
end;
delimiter //