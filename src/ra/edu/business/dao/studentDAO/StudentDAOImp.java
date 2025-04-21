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
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Student student = null;

        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_student_by_id(?, ?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);

            rs = cs.executeQuery();

            if(rs.next()){
                student = new Student();
                student.setId(rs.getInt("s_id"));
                student.setFullName(rs.getString("s_full_name"));
                student.setEmail(rs.getString("s_email"));
                student.setPhone(rs.getString("s_phone"));
                student.setSex(rs.getBoolean("s_sex"));
                student.setDob(rs.getDate("s_dob").toLocalDate());
                student.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
            }

            int returnCode = cs.getInt(2);
            if(returnCode == -1){
                PrintError.println("Không tìm thấy sinh viên có ID = " + id);
            }

        }catch(SQLException e){
            PrintError.println("SQL Error while finding student by id: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown Error while finding student by id: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return student;
    }

    @Override
    public Pagination<Student> findByName(String name, int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Pagination<Student> pagination = new Pagination<>();
        List<Student> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_student_by_email(?,?,?,?)}");
            cs.setString(1, name);
            cs.setInt(2, page);
            cs.setInt(3, size);
            rs = cs.executeQuery();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("s_id"));
                student.setEmail(rs.getString("s_email"));
                student.setSex(rs.getBoolean("s_sex"));
                student.setDob(rs.getDate("s_dob").toLocalDate());
                student.setPhone(rs.getString("s_phone"));
                student.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(student);
            }
            int totalItems = cs.getInt(3);
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
        }catch(SQLException e){
            PrintError.println("Error while finding students by name: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while finding students by name: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
    }

    @Override
    public Pagination<Student> findByEmail(String email, int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Pagination<Student> pagination = new Pagination<>();
        List<Student> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_student_by_email(?,?,?,?)}");
            cs.setString(1, email);
            cs.setInt(2, page);
            cs.setInt(3, size);
            rs = cs.executeQuery();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("s_id"));
                student.setEmail(rs.getString("s_email"));
                student.setSex(rs.getBoolean("s_sex"));
                student.setDob(rs.getDate("s_dob").toLocalDate());
                student.setPhone(rs.getString("s_phone"));
                student.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(student);
            }
            int totalItems = cs.getInt(3);
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
        }catch(SQLException e){
            PrintError.println("Error while finding students by email: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while finding students by email: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
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
                s.setId(rs.getInt("s_id"));
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
    public boolean save(Account account, Student student) {
        Connection con = null;
        CallableStatement cs = null;
        System.out.println(account.getUsername());
        System.out.println(student.getFullName());
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
            cs.executeUpdate();
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
    public boolean save(Student student){
        return false;
    }

    @Override
    public boolean unlockStudent(int id){
        Connection con = null;
        CallableStatement cs = null;
        int returnCode = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call unblock_student(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            returnCode = cs.getInt(2);
            if(returnCode == 0){
                return true;
            }
        }catch(SQLException e){
            PrintError.println("Error while unlocking student: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while unlocking student: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }

    @Override
    public boolean isStudentLocked(int id){
        Connection con = null;
        CallableStatement cs = null;
        int returnCode = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call is_student_blocked(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            returnCode = cs.getInt(2);
            if(returnCode == 1){
                return true;
            }else if(returnCode == 0){
                return false;
            }else if(returnCode == -1){
                PrintError.println("Not found student");
                return true;
            }
        }catch(SQLException e){
            PrintError.println("Error while checking if student is locked: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while checking if student is locked: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return false;
    }

    @Override
    public List<Student> sortStudentByName(boolean typeSort){
        Connection con = null;
        CallableStatement cs = null;
        List<Student> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call sort_student_by_name(?)}");
            cs.setBoolean(1, typeSort);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                Student s = new Student();
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(s);
            }
            return list;
        }catch(SQLException e){
            PrintError.println("Error while sorting student: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while sorting student: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return list;
    }

    @Override
    public List<Student> sortStudentByEmail(boolean typeSort){
        Connection con = null;
        CallableStatement cs = null;
        List<Student> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call sort_student_by_name(?)}");
            cs.setBoolean(1, typeSort);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                Student s = new Student();
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(s);
            }
            return list;
        }catch(SQLException e){
            PrintError.println("Error while sorting student: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while sorting student: " + e.getMessage());
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return list;
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
            cs.setString(3, student.getEmail());
            cs.setDate(4, Date.valueOf(student.getDob()));
            cs.setString(5, student.getPhone());
            cs.setBoolean(6, student.isSex());
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
        int returnCode = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call delete_student(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            returnCode = cs.getInt(2);
            if(returnCode == 0){
                return true;
            }
        }catch(SQLException e){
            PrintError.println("Error while blocking student: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error blocking deleting student: " + e.getMessage());
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
                s.setId(rs.getInt("s_id"));
                s.setFullName(rs.getString("s_full_name"));
                s.setEmail(rs.getString("s_email"));
                s.setPhone(rs.getString("s_phone"));
                s.setSex(rs.getBoolean("s_sex"));
                s.setDob(rs.getDate("s_dob").toLocalDate());
                s.setCreatedAt(rs.getTimestamp("s_created_at").toLocalDateTime());
                list.add(s);
            }
            int totalItems = cs.getInt(3);
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
}
