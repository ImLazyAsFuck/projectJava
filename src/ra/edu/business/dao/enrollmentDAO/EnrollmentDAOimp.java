package ra.edu.business.dao.enrollmentDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.student.Student;
import ra.edu.utils.Print.PrintError;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentDAOimp implements EnrollmentDAO{
    @Override
    public Pagination<Student> studentByCourse(int s_id, int page, int size, int c_id) {
        Pagination<Student> studentByCourse = new Pagination<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call display_student_by_course(?,?,?,?,?,?)}");
            cs.setInt(1, page);
            cs.setInt(2, size);
            cs.setInt(3, c_id);
            cs.setInt(4, s_id);
            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.registerOutParameter(6, java.sql.Types.INTEGER);

            boolean hasResultSet = cs.execute();
            int returnCode  = cs.getInt(5);
            int totalItems = cs.getInt(6);

            if (returnCode != 0) {
                switch (returnCode) {
                    case -1:
                        PrintError.println("Course with ID = " + c_id + " does not exist.");
                        break;
                    case -2:
                        PrintError.println("Invalid pagination parameters.");
                        break;
                    case -3:
                        PrintError.println("Student with ID = " + s_id + " does not exist.");
                        break;
                    case -4:
                        PrintError.println("No students found in the specified course.");
                        break;
                    default:
                        PrintError.println("Unknown error. Return code: " + returnCode);
                }
            }

            if (hasResultSet) {
                rs = cs.getResultSet();
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt(1));
                    student.setFullName(rs.getString(2));
                    student.setDob(rs.getDate(3).toLocalDate()); // nhớ convert từ SQL Date
                    student.setSex(rs.getBoolean(4));
                    student.setPhone(rs.getString(5));
                    student.setEmail(rs.getString(6));
                    student.setCreatedAt(rs.getTimestamp(7).toLocalDateTime());
                    students.add(student);
                }

                int totalPages = (int) Math.ceil((double) totalItems / size);

                if (page > totalPages && totalPages > 0) {
                    page = totalPages;
                }
                if (totalPages == 0) {
                    page = 1;
                }
                studentByCourse.setTotalItems(totalItems);
                studentByCourse.setTotalPages(totalPages);
                studentByCourse.setPageSize(size);
                studentByCourse.setItems(students);
            }
        } catch (SQLException e) {
            PrintError.println("Error while getting student by course: " + e.getMessage());
        } catch (Exception e) {
            PrintError.println("Unknown error while getting student by course: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }

        return studentByCourse;
    }


    @Override
    public boolean addStudentToEnrollment(int courseId, int studentId){
        Connection con = ConnectionDB.openConnection();
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call add_student_to_enrollment(?,?,?)}");
            cs.setInt(1, courseId);
            cs.setInt(2, studentId);
            cs.registerOutParameter(3, java.sql.Types.INTEGER);
            cs.execute();
            int return_code = cs.getInt(3);
            switch (return_code) {
                case 0: return true;
                case 1: PrintError.println("Student does not exist.");
                    break;
                case 2: PrintError.println("Course does not exist.");
                    break;
                case 3: PrintError.println("Student is already enrolled.");
                    break;
                default: PrintError.println("Unknown error.");
            }

        }catch (SQLException e){
            PrintError.println("Error while adding student to enrollment: " + e.getMessage());
        }catch (Exception e){
            PrintError.println("Unknown error while adding student to enrollment: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }

    @Override
    public boolean removeStudentFromEnrollment(int course, int studentId){
        Connection con = ConnectionDB.openConnection();
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call delete_student_from_course(?,?,?)}");
            cs.setInt(1, studentId);
            cs.setInt(2, course);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.execute();
            int resultCode = cs.getInt(3);
            switch (resultCode) {
                case 0:
                    return true;
                case 1:
                    PrintError.println("Student ID không tồn tại!");
                    break;
                case 2:
                    PrintError.println("Course ID không tồn tại!");
                    break;
                case 3:
                    PrintError.println("Sinh viên này không đăng ký khóa học này!");
                    break;
                case 4:
                    PrintError.println("Không thể xóa: Đăng ký khóa học đã được xác nhận!");
                    return false;
                default:
                    PrintError.println("Lỗi không xác định từ stored procedure!");
            }
        }catch(SQLException e){
            PrintError.println("Error while deleting student from enrollment: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while deleting student from enrollment: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }

    @Override
    public boolean isCourseExist(int courseId) {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call is_course_exists(?)}");
            cs.setInt(1, courseId);
            rs = cs.executeQuery();

            if (rs.next()) {
                exists = rs.getInt("exists_flag") == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Nhớ log lỗi cho ra trò đó!
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return exists;
    }

    @Override
    public List<Course> dislayCurrentAccCourse(int s_id){
        Connection con = null;
        CallableStatement cs = null;
        List<Course> courseList = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call display_current_acc_course(?)}");
            cs.setInt(1,  s_id);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setDuration(rs.getInt("c_duration"));
                course.setDescription(rs.getString("c_description"));
//                course.set(rs.getString("c_status"));
                course.setInstructor(rs.getString("c_instructor"));
                course.setCreatedAt(rs.getTimestamp("c_created_at").toLocalDateTime());
                courseList.add(course);
            }
            return courseList;
        }catch(SQLException e){
            PrintError.println("Error while getting student by course: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while getting student by course: " + e.getMessage());
        }
        return courseList;
    }

}
