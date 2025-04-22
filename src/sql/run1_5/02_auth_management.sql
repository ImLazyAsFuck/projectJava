use project_java;

delimiter //
create procedure login(in_u_name varchar(50), in_u_password varchar(255))
begin
    select a.a_id, a.a_username, a.a_password, a_status, a.a_role from account a
    where a.a_username = in_u_name and a.a_password = in_u_password;
end;
delimiter //


delimiter //
drop procedure if exists change_password;
create procedure change_password(
    in in_id int,
    in in_password varchar(255),
    out out_username varchar(255),
    out out_status varchar(255),
    out out_role varchar(50),
    out out_password varchar(255)
)
begin
    update account
    set a_password = in_password
    where a_id = in_id;

    select a_username, a_password, a_status, a_role
    into out_username, out_password, out_status, out_role
    from account
    where a_id = in_id;
end;
delimiter //
