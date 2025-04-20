package ra.edu.business.dao.studentDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Account.Account;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;
import ra.edu.utils.Print.PrintError;

import java.sql.*;
import java.util.ArrayList;
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
        ResultSet rs;

        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{CALL is_email_exists(?)}");
            cs.setString(1, email);
            rs = cs.executeQuery();

            if(rs.next()){
                int count = rs.getInt("email_count");
                return count > 0;
            }
        }catch(SQLException e){
            PrintError.println("Error while checking email existence" + e.getMessage());
        } catch (Exception e) {
            PrintError.println("Unknown Error while checking if email exists: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }


    @Override
    public List<Student> findAll(){
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs;
        List<Student> list = new ArrayList<Student>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_all_student()}");
            rs = cs.executeQuery();
            while(rs.next()){
                Student s = new Student();
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                s.setSex(rs.getBoolean("s_sex"));
                list.add(s);
            }
            return list;
        }catch(SQLException e){
            PrintError.println("Error while checking if all students are found: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown Error while checking if all students are found: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return list;
    }

    @Override
    public boolean save(Student student){
        return false;
    }

    @Override
    public boolean save(Account account, Student student) {
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call create_student(?,?,?,?,?,?,?)}");
            cs.setString(1, account.getUsername());
            cs.setString(2, account.getPassword());
            cs.setString(3, student.getFullName());
            cs.setDate(4, Date.valueOf(student.getDob()));
            cs.setString(5, student.getEmail());
            cs.setBoolean(6, student.isSex());
            cs.setString(7, student.getPhone());
            cs.execute();
            return true;
        } catch(SQLException e) {
            PrintError.println("Error while saving student: " + e.getMessage());
        } catch(RuntimeException e) {
            PrintError.println("Unknown error while saving student: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }


    @Override
    public boolean update(Student student){
        Connection con = null;
        CallableStatement cs = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call update_student(?,?,?,?,?,?)}");
            cs.setInt(1, student.getId());
            cs.setString(2, student.getFullName());
            cs.setString(2, student.getEmail());
            cs.setDate(3, Date.valueOf(student.getDob()));
            cs.setString(4, student.getPhone());
            cs.setBoolean(5, student.isSex());
            cs.executeUpdate();
            return true;
        }catch(SQLException e){
            PrintError.println("Error while updating student: " + e.getMessage());
            return false;
        }catch(Exception e){
            PrintError.println("Unknown error while updating student: " + e.getMessage());
            return false;
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
    }

    @Override
    public boolean delete(int id){
        Connection con = null;
        CallableStatement cs = null;
        int return_code = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call delete_student(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            return_code = cs.getInt(2);
            if(return_code == 0){
                return true;
            }
        }catch(SQLException e){
            PrintError.println("Error while deleting student: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while deleting student: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }

    @Override
    public Pagination<Student> findPage(int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();
        Pagination<Student> pagination = new Pagination<>();
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_student_by_page(?,?,?)}");
            cs.setInt(1, page);
            cs.setInt(2, size);
            cs.registerOutParameter(3, Types.INTEGER);

            rs = cs.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(s);
            }

            pagination.setTotalItems(cs.getInt(3));
            pagination.setItems(list);
            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
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

}
