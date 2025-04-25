use project_java;

delimiter //
drop procedure if exists is_email_exists;
create procedure is_email_exists(in_email varchar(100))
begin
    select count(s_id) as email_count from student
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
drop procedure if exists find_student_by_page;
create procedure find_student_by_page(page int, size int, out totalItems int)
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
drop procedure if exists create_student;
create procedure create_student(
    in in_username varchar(50),
    in in_password varchar(255),
    in in_fullname varchar(100),
    in in_dob date,
    in in_email varchar(100),
    in in_sex bit,
    in in_phone varchar(20)
)
begin
    declare new_a_id int;

    insert into account(a_username, a_password)
    values (in_username, in_password);

    set new_a_id = last_insert_id();

    insert into student(
        a_id,
        s_full_name,
        s_email,
        s_dob,
        s_sex,
        s_phone
    ) values (
                 new_a_id,
                 in_fullname,
                 in_email,
                 in_dob,
                 in_sex,
                 in_phone
             );
end;
delimiter //


delimiter //
drop procedure if exists update_student;
create procedure update_student(
    in_id int,
    in_full_name varchar(100),
    in_email varchar(100),
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
        s_sex = in_sex,
        s_email = in_email
    where s_id = in_id;
end;
delimiter //

delimiter //
drop procedure if exists delete_student;
create procedure delete_student(
    in id int,
    out return_code int
)
begin
    declare account_id int default null;
    declare enrollment_count int default 0;

    select a_id
    into account_id
    from student
    where a_id = id;

    select count(*)
    into enrollment_count
    from enrollment e
             join student s on e.s_id = s.s_id
    where s.a_id = id and e_status = 'CONFIRMED';

    if account_id is null then
        set return_code = 1;
    elseif enrollment_count > 0 then
        set return_code = 2;
    else
        update account set a_status = 'BLOCKED' where a_id = account_id;
        set return_code = 0;
    end if;
end;
delimiter //

delimiter //
create procedure unblock_student(
    in id int,
    out return_code int
)
begin
    declare account_id int default null;

    select a_id into account_id
    from student
    where a_id = id;

    if account_id is not null then
        update account set a_status = 'ACTIVE' where a_id = account_id;
        set return_code = 0;
    else
        set return_code = 1;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists  find_student_by_id;
create procedure find_student_by_id(in_id int, out return_code int)
begin
    select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at from student
    where s_id = in_id;
    set return_code = 0;
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
create procedure is_student_blocked(
    in in_id int,
    out return_code int
)
begin
    declare v_status enum('ACTIVE', 'INACTIVE', 'BLOCKED');

    select a.a_status into v_status
    from student s
             join account a on s.a_id = a.a_id
    where s.s_id = in_id;

    if v_status is null then
        set return_code = -1;
    elseif v_status = 'BLOCKED' then
        set return_code = 1;
    else
        set return_code = 0;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists sort_student_by_name;
create procedure sort_student_by_name(type_sort bit)
begin
    if type_sort = 0 then
        select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at
        from student
        order by s_full_name asc;
    elseif type_sort = 1 then
        select s_id, s_full_name, s_dob, s_email, s_sex, s_phone, s_created_at
        from student
        order by s_full_name desc;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists sort_student_by_email;
create procedure sort_student_by_email(type_sort bit)
begin
    if type_sort = 0 then
        select s.s_id, s.s_full_name, s.s_dob, s.s_email, s.s_sex, s.s_phone, s.s_created_at
        from student s
                 join account a on s.a_id = a.a_id
        where a.a_status = 'ACTIVE'
        order by s.s_email asc;
    elseif type_sort = 1 then
        select s.s_id, s.s_full_name, s.s_dob, s.s_email, s.s_sex, s.s_phone, s.s_created_at
        from student s
                 join account a on s.a_id = a.a_id
        where a.a_status = 'ACTIVE'
        order by s.s_email desc;
    end if;
end;
delimiter //

delimiter //
drop procedure if exists find_student_by_acc_id;
create procedure find_student_by_acc_id(in acc_id int)
begin
    select s_id
    from student
    where a_id = acc_id;
end;
delimiter //
