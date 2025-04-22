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
    in page int,
    in size int,
    in in_c_id int,
    in in_s_id int,
    out return_code int,
    out totalItems int
)
begin
    declare offset_value int default (page - 1) * size;
    declare row_count int default 0;

    if not exists (select 1 from course where c_id = in_c_id) then
        set return_code = -1;
    else
        if in_s_id is not null then
            if in_s_id <= 0 or not exists (select 1 from student where s_id = in_s_id) then
                set return_code = -3;
            else
                if page <= 0 or size <= 0 then
                    set return_code = -2;
                else
                    select count(s_id) into totalItems
                    from enrollment e
                             join student s on e.s_id = s.s_id
                    where e.c_id = in_c_id
                      and (in_s_id is null or s.s_id = in_s_id);

                    select
                        s.s_id,
                        s.s_full_name,
                        s.s_email,
                        s.s_phone,
                        s.s_dob,
                        s.s_sex,
                        s.s_created_at,
                        e.e_status,
                        e.e_registered_at
                    from enrollment e
                             join student s on e.s_id = s.s_id
                    where e.c_id = in_c_id
                      and (in_s_id is null or s.s_id = in_s_id)
                    limit size offset offset_value;

                    set row_count = found_rows();
                    if row_count = 0 then
                        set return_code = -4;
                    else
                        set return_code = 0;
                    end if;
                end if;
            end if;
        else
            if page <= 0 or size <= 0 then
                set return_code = -2;
            else
                select
                    s.s_id,
                    s.s_full_name,
                    s.s_email,
                    s.s_phone,
                    s.s_dob,
                    s.s_sex,
                    s.s_created_at,
                    e.e_status,
                    e.e_registered_at
                from enrollment e
                         join student s on e.s_id = s.s_id
                where e.c_id = in_c_id
                  and (in_s_id is null or s.s_id = in_s_id)
                limit size offset offset_value;

                set row_count = found_rows();
                if row_count = 0 then
                    set return_code = -4;
                else
                    set return_code = 0;
                end if;
            end if;
        end if;
    end if;
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
    set return_code = 0;

    if not exists (select 1 from student where s_id = in_s_id) then
        set return_code = 1;
    elseif not exists (select 1 from course where c_id = in_c_id) then
        set return_code = 2;
    elseif exists (select 1 from enrollment where s_id = in_s_id and c_id = in_c_id) then
        set return_code = 3;
    else
        insert into enrollment (s_id, c_id)
        values (in_s_id, in_c_id);
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
        delete from enrollment where s_id = in_s_id and c_id = in_c_id;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists display_current_acc_course;
create procedure display_current_acc_course(in in_s_id int)
begin
    select
        c.c_id,
        c.c_name,
        c.c_duration,
        c.c_description,
#         c.c_status,
        c.c_instructor,
        c.c_created_at
    from enrollment e
             join course c on e.c_id = c.c_id
    where e.s_id = in_s_id;
end;
delimiter //