package ra.edu.business.dao.studentDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;
import ra.edu.utils.Print.PrintError;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class StudentDAOImp implements StudentDAO{
    @Override
    public Student findById(int id){
        return null;
    }

    @Override
    public Pagination<Student> findByName(String name, int page, int size){
        return null;
    }

    @Override
    public Pagination<Student> findByEmail(String email, int page, int size){
        return null;
    }

    @Override
    public boolean isEmailExist(String email) {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{CALL is_email_exists(?)}");
            cs.setString(1, email);
            rs = cs.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("email_count");
                return count > 0;
            }
        } catch (Exception e) {
            PrintError.println("Unknown Error while checking if email exists: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }


    @Override
    public List<Student> findAll(){
        return List.of();
    }

    @Override
    public boolean save(Student student){
        return false;
    }

    @Override
    public boolean update(Student student){
        return false;
    }

    @Override
    public boolean delete(int id){
        return false;
    }

    @Override
    public Pagination<Student> findPage(int page, int size){
        return null;
    }
}
