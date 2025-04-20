use project_java;

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
create procedure create_student(
    in_username varchar(50),
    in_password varchar(255),
    in_fullname varchar(100),
    in_dob date,
    in_email varchar(100),
    in_sex bit,
    in_phone varchar(20)
)
begin
    declare new_a_id int;
    insert into account(a_username, a_password)
    values (in_username, in_password);

    select a_id into new_a_id from account
    where in_username = a_username;

    insert into student(
        a_id,
        s_full_name,
        s_email,
        s_dob,
        s_sex,
        s_phone
    ) values (new_a_id,
              in_fullname,
              in_email,
              in_dob,
              in_sex,
              in_phone);

end;
delimiter //

delimiter //
create procedure update_student(
    in_id int,
    in_full_name varchar(100),
    in_dob date,
    in_phone varchar(20),
    in_sex bit
)
begin
    update student
    set
        s_full_name = in_full_name,
        s_phone = in_phone,
        s_dob = in_dob,
        s_sex = in_sex
    where s_id = in_id;
end;
delimiter //

delimiter //

create procedure delete_student(
    in id int,
    out return_code int
)
begin
    declare account_id int default null;

    select a_id into account_id
    from student
    where s_id = id;

    if account_id is not null then
        update account set a_status = 'BLOCKED' where a_id = account_id;
        set return_code = 0;
    else
        set return_code = 1;
    end if;
end;

delimiter //

delimiter //
create procedure find_student_by_id(in_id int, return_code int)
begin
    declare cnt int;
    select (s_id) from student
    where s_id = in_id;
    if cnt = 0 then
        set return_code = 1;
    else
        select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at from student
        where s_id = in_id;
        set return_code = 0;
    end if;
end;
delimiter //

delimiter //
create procedure find_student_by_email(
    in_email varchar(100),
    page_number int,
    page_size int,
    out totalItems int
)
begin
    declare offset_value int;
    select count(s_id) into totalItems
    from student
    where s_email like concat('%', in_email, '%');

    set offset_value = (page_number - 1) * page_size;

    select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at from student
    where s_full_name like concat('%', in_email, '%')
    limit page_size offset offset_value;
end;
delimiter //

delimiter //
create procedure find_student_by_name(
    in_name varchar(100),
    in page_number int,
    in page_size int,
    out totalItems int
)
begin
    declare offset_value int;
    select count(s_id) into totalItems
    from student
    where s_full_name like concat('%', in_name, '%');

    set offset_value = (page_number - 1) * page_size;

    select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at from student
    where s_full_name like concat('%', in_name, '%')
    limit page_size offset offset_value;
end;
delimiter //

delimiter //
create procedure sort_student_by_name(type_sort bit)
begin
    select
        s_id,
        s_full_name,
        s_dob,
        s_email,
        s_sex,
        s_phone,
        s_created_at from student
        order by s_full_name;
end;
delimiter //