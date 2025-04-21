package ra.edu.business.dao.enrollmentDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;
import ra.edu.utils.Print.PrintError;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EnrollmentDAOimp implements EnrollmentDAO{
    @Override
    public Pagination<Student> studentByCourse(String name){
        Pagination<Student> studentByCourse = new Pagination<>();
        Connection con = null;
        CallableStatement cs = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{}");
        }catch(SQLException e){
            PrintError.println("Error while getting student by course: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while getting student by course: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return studentByCourse;
    }

    @Override
    public boolean addStudentToEnrollment(int courseId, int studentId){
        return false;
    }

    @Override
    public boolean removeStudentFromEnrollment(int course, int studentId){
        return false;
    }

    @Override
    public boolean isCourseExist(int courseId){
        return false;
    }
}
