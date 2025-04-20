use project_java;

INSERT INTO account (a_username, a_password, a_role)
VALUES ('admin1', 'adminpass123', 'ADMIN');

INSERT INTO account (a_username, a_password, a_role)
VALUES
    ('student1', 'pass123', 'STUDENT'),
    ('student2', 'pass123', 'STUDENT'),
    ('student3', 'pass123', 'STUDENT'),
    ('student4', 'pass123', 'STUDENT'),
    ('student5', 'pass123', 'STUDENT'),
    ('student6', 'pass123', 'STUDENT'),
    ('student7', 'pass123', 'STUDENT'),
    ('student8', 'pass123', 'STUDENT'),
    ('student9', 'pass123', 'STUDENT');

INSERT INTO student (a_id, s_full_name, s_dob, s_email, s_sex, s_phone)
VALUES
    (2, 'Nguyễn Văn A', '2002-05-10', 'student1@gmail.com', 1, '0901123456'),
    (3, 'Trần Thị B', '2001-08-22', 'student2@gmail.com', 0, '0902234567'),
    (4, 'Lê Văn C', '2003-01-15', 'student3@gmail.com', 1, '0903345678'),
    (5, 'Phạm Thị D', '2000-11-03', 'student4@gmail.com', 0, '0904456789'),
    (6, 'Hoàng Văn E', '2002-03-27', 'student5@gmail.com', 1, '0905567890'),
    (7, 'Đặng Thị F', '2001-07-19', 'student6@gmail.com', 0, '0906678901'),
    (8, 'Vũ Văn G', '2003-09-01', 'student7@gmail.com', 1, '0907789012'),
    (9, 'Ngô Thị H', '2002-12-05', 'student8@gmail.com', 0, '0908890123'),
    (10, 'Bùi Văn I', '2001-04-30', 'student9@gmail.com', 1, '0909901234');
