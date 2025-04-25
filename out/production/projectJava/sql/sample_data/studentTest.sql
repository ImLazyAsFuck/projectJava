USE project_java;

INSERT INTO account (a_username, a_password, a_role)
VALUES ('admin', 'Admin@123', 'ADMIN');

INSERT INTO account (a_username, a_password, a_status, a_role) VALUES
                                                                   ('anhtu01', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('binhnguyen02', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('camtu03', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('dungtran04', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('emily05', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('fuki06', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('giahan07', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('hieu08', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('hoa09', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('hoanglong10', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('kien11', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('lam12', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('minhthu13', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('ngoctran14', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('phuc15', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('quangvu16', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('rosemary17', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('son18', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('tuananh19', 'a@123456', 'ACTIVE', 'STUDENT'),
                                                                   ('yen20', 'a@123456', 'ACTIVE', 'STUDENT');

INSERT INTO student (a_id, s_full_name, s_dob, s_email, s_sex, s_phone) VALUES
                                                                            (2, 'Nguyễn Anh Tú', '2002-04-12', 'tuanh01@gmail.com', 1, '0901234561'),
                                                                            (3, 'Nguyễn Bình', '2001-11-05', 'binh02@gmail.com', 1, '0901234562'),
                                                                            (4, 'Lê Cẩm Tú', '2003-02-20', 'camtule@gmail.com', 0, '0901234563'),
                                                                            (5, 'Trần Văn Dũng', '2000-07-15', 'dungtv04@gmail.com', 1, '0901234564'),
                                                                            (6, 'Emily Nguyễn', '2002-08-08', 'emily05@gmail.com', 0, '0901234565'),
                                                                            (7, 'Phạm Phúc Kỳ', '2001-10-20', 'fuki06@gmail.com', 1, '0901234566'),
                                                                            (8, 'Nguyễn Gia Hân', '2003-03-25', 'giahan07@gmail.com', 0, '0901234567'),
                                                                            (9, 'Đinh Văn Hiếu', '2002-09-30', 'hieu08@gmail.com', 1, '0901234568'),
                                                                            (10, 'Trần Thị Hoa', '2001-06-12', 'hoa09@gmail.com', 0, '0901234569'),
                                                                            (11, 'Hoàng Long', '2000-12-01', 'long10@gmail.com', 1, '0901234570'),
                                                                            (12, 'Ngô Kiên', '2003-01-10', 'kien11@gmail.com', 1, '0901234571'),
                                                                            (13, 'Trần Văn Lâm', '2002-05-22', 'lam12@gmail.com', 1, '0901234572'),
                                                                            (14, 'Minh Thư', '2003-04-03', 'minhthu13@gmail.com', 0, '0901234573'),
                                                                            (15, 'Ngọc Trân', '2001-08-09', 'ngoctran14@gmail.com', 0, '0901234574'),
                                                                            (16, 'Nguyễn Phúc', '2000-11-17', 'phuc15@gmail.com', 1, '0901234575'),
                                                                            (17, 'Vũ Quang', '2002-10-10', 'quangvu16@gmail.com', 1, '0901234576'),
                                                                            (18, 'Rosemary Lê', '2001-03-30', 'rosemary17@gmail.com', 0, '0901234577'),
                                                                            (19, 'Trần Sơn', '2003-06-06', 'son18@gmail.com', 1, '0901234578'),
                                                                            (20, 'Tuan Anh', '2001-12-25', 'tuananh19@gmail.com', 1, '0901234579'),
                                                                            (21, 'Hoàng Yến', '2002-09-14', 'yen20@gmail.com', 0, '0901234580');
