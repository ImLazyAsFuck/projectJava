use project_java;

delimiter //
create procedure login(in_u_name varchar(50), in_u_password varchar(255))
begin
    select a.a_id, a.a_username, a.a_password, a_status, a.a_role from account a
    where a.a_username = in_u_name and a.a_password = in_u_password;
end;
delimiter //