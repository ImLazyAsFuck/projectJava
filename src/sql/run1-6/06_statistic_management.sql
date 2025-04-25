use project_java;

delimiter //
drop procedure if exists find_total_course;
create procedure find_total_course()
begin
    select count(c_id) from course;
end;
delimiter //

delimiter //
drop procedure if exists find_total_student;
create procedure find_total_student()
begin
    select count(s_id) from student;
end;
delimiter //

delimiter //
drop procedure if exists find_courses_have_more_10_student;
create procedure find_courses_have_more_10_student(page int, size int, out total_items int)
begin
    declare offset_value int;
    set offset_value = (page - 1) * size;

    select count(*)
    into total_items
    from (select c.c_id
          from course c
                   join enrollment e on c.c_id = e.c_id
          group by c.c_id, c.c_name
          having count(e.s_id) > 10) as count_table;

    select c.c_name, count(e.s_id) as student_count
    from course c
             join enrollment e on c.c_id = e.c_id
    group by c.c_id, c.c_name
    having count(e.s_id) > 10
    limit size offset offset_value;
end;
delimiter //

delimiter //
drop procedure if exists find_student_count_each_course;
create procedure find_student_count_each_course(page int, size int, out total_items int)
begin
    declare offset_value int;
    set offset_value = (page - 1) * size;

    select count(*)
    into total_items
    from (select c.c_name, count(e.s_id) as student_count
          from course c
                   inner join enrollment e on c.c_id = e.c_id
          group by c.c_id, c.c_name) as count_table;

    select c.c_name, count(e.s_id) as student_count
    from course c
             inner join enrollment e on c.c_id = e.c_id
    group by c.c_id, c.c_name
    limit size offset offset_value;
end;
delimiter //

delimiter //
drop procedure if exists find_top5_courses_by_student_count;
create procedure find_top5_courses_by_student_count()
begin
    select c.c_name, count(e.s_id) as student_count
    from course c
             join enrollment e on c.c_id = e.c_id
    group by c.c_id, c.c_name
    order by student_count desc
    limit 5;
end;
delimiter //
