package ra.edu.business.dao.authDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.User.Admin;
import ra.edu.business.model.User.Student;
import ra.edu.utils.PrintError;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAOImp implements AuthDAO{

    @Override
    public Admin loginAsAdmin(String username, String password){
        Connection con = null;
        CallableStatement cs = null;
        Admin admin = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call loginAsAdmin(?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                admin = new Admin();
                admin.setId(rs.getInt("a_id"));
                admin.setUsername(rs.getString("a_username"));
                admin.setPassword(rs.getString("a_password"));
            }
            return admin;
        }catch(SQLException e){
            PrintError.println("Error while login as student" + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while login as student" + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return admin;
    }

    @Override
    public Student loginAsStudent(String username, String password){
        Connection con = null;
        CallableStatement cs = null;
        Student student = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call loginAsStudent(?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                student = new Student();
                student.setId(rs.getInt("s_id"));
                student.setUsername(rs.getString("s_username"));
                student.setPassword(rs.getString("s_password"));
            }
            return student;
        }catch(SQLException e){
            PrintError.println("Error while login as student" + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while login as student" + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return student;
    }

    @Override
    public Student changePassword(String username, String newPassword){
        return null;
    }
}
