package ra.edu.business.dao.enrollmentDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.enrollment.Enrollment;
import ra.edu.business.model.enrollment.EnrollmentStatus;
import ra.edu.business.model.student.Student;
import ra.edu.utils.Print.PrintError;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentDAOimp implements EnrollmentDAO{
    @Override
    public Pagination<Student> studentByCourse(int c_id, int page, int size) {
        Pagination<Student> pagination = new Pagination<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();

        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call display_student_by_course(?,?,?,?)}");
            cs.setInt(1, c_id);
            cs.setInt(2, page);
            cs.setInt(3, size);
            cs.registerOutParameter(4, Types.INTEGER);

            rs = cs.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("s_id"));
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setSex(rs.getBoolean("s_sex"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(s);
            }
            int totalItems = cs.getInt(4);
            int totalPages = (int) Math.ceil((double) totalItems / size);
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            if(totalPages == 0){
                page = 1;
            }

            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
//            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
            pagination.setItems(list);
            return pagination;
        } catch(SQLException e) {
            PrintError.println("Error while fetching students: " + e.getMessage());
        } catch(Exception e) {
            PrintError.println("Unknown error while fetching students: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
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
                case 4: PrintError.println("Student is blocked.");
                    break;
                case 5: PrintError.println("Student is not active.");
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
                    PrintError.println("Student ID does not exist!");
                    break;
                case 2:
                    PrintError.println("Course ID does not exist!");
                    break;
                case 3:
                    PrintError.println("This student is not enrolled in this course!");
                    break;
                case 4:
                    PrintError.println("Cannot delete: Course registration has been confirmed!");
                    return false;
                default:
                    PrintError.println("Unknown error from stored procedure!");
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
            e.printStackTrace();
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

    @Override
    public List<Enrollment> findAll(){
        Connection con = null;
        CallableStatement cs = null;
        List<Enrollment> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_all_enrollment()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("e_id"));
                enrollment.setcId(rs.getInt("c_id"));
                enrollment.setStatus(EnrollmentStatus.valueOf(rs.getString("e_status")) );
                enrollment.setRegisteredAt(rs.getTimestamp("e_registered_at").toLocalDateTime());
                list.add(enrollment);
            }
            return list;
        }catch(SQLException e){
            PrintError.println("Error while finding all enrollments: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return list;
    }

    @Override
    public Pagination<Course> findCourseByStudentId(int s_id, int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        Pagination<Course> pagination = new Pagination<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_course_by_student_id(?,?,?,?)}");
            cs.setInt(1, s_id);
            cs.setInt(2, page);
            cs.setInt(3, size);
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            ResultSet rs = cs.executeQuery();
            List<Course> list = new ArrayList<>();
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setDuration(rs.getInt("c_duration"));
                course.setDescription(rs.getString("c_description"));
                course.setInstructor(rs.getString("c_instructor"));
                Timestamp timestamp = rs.getTimestamp("c_created_at");
                if(timestamp != null){
                    course.setCreatedAt(timestamp.toLocalDateTime());
                }
                list.add(course);
            }
            int totalItems = cs.getInt(4);
            int totalPages = (int) Math.ceil((double) totalItems / size);
            if(page > totalPages && totalPages > 0){
                page = totalPages;
            }
            if(totalPages == 0){
                page = 1;
            }
            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
            pagination.setItems(list);
            return pagination;
        }catch(SQLException e){
            PrintError.println("Error while finding courses by student id: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while finding courses by student id: " + e.getMessage());
        }
        finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
    }
}
