package ra.edu.business.dao.authDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Account.Account;
import ra.edu.business.model.Account.AccountStatus;
import ra.edu.business.model.Account.Role;
import ra.edu.utils.Print.PrintError;

import java.sql.*;

public class AuthDAOImp implements AuthDAO{

    @Override
    public Account loginAsAdmin(String username, String password){
        Connection con = null;
        CallableStatement cs = null;
        Account admin = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call login(?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                admin = new Account();
                admin.setId(rs.getInt("a_id"));
                admin.setUsername(rs.getString("a_username"));
                admin.setPassword(rs.getString("a_password"));
                admin.setStatus(AccountStatus.valueOf(rs.getString("a_status")));
                admin.setRole(Role.valueOf(rs.getString("a_role")));
            }
            if(admin == null){
                return null;
            }
            if(admin.getRole() == Role.STUDENT){
                PrintError.println("You don't have permission to login this account here!");
                return null;
            }else if(admin.getStatus() == AccountStatus.INACTIVE){
                PrintError.println("Your account has been inactive!");
                return null;
            }else if(admin.getStatus() == AccountStatus.BLOCKED){
                PrintError.println("Your account has been blocked!");
                return null;
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
    public Account loginAsStudent(String username, String password){
        Connection con = null;
        CallableStatement cs = null;
        Account student = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call login(?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                student = new Account();
                student.setId(rs.getInt("a_id"));
                student.setUsername(rs.getString("a_username"));
                student.setPassword(rs.getString("a_password"));
                student.setStatus(AccountStatus.valueOf(rs.getString("a_status")));
                student.setRole(Role.valueOf(rs.getString("a_role")));
            }
            if(student == null){
                return null;
            }
            if(student.getRole() == Role.ADMIN){
                PrintError.println("You don't have permission to login this account here!");
                return null;
            }else if(student.getStatus() == AccountStatus.INACTIVE){
                PrintError.println("Your account has been inactive!");
                return null;
            }else if(student.getStatus() == AccountStatus.BLOCKED){
                PrintError.println("Your account has been blocked!");
                return null;
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
    public Account changePassword(int id, String newPassword) {
        Connection con = null;
        CallableStatement cs = null;
        Account account = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call change_password(?, ?, ?, ?, ?, ?)}");

            cs.setInt(1, id);
            cs.setString(2, newPassword);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.registerOutParameter(6, Types.VARCHAR);

            cs.execute();

            account = new Account();
            account.setId(id);
            account.setUsername(cs.getString(3));
            account.setStatus(AccountStatus.valueOf(cs.getString(4)));
            account.setRole(Role.valueOf(cs.getString(5)));
            account.setPassword(cs.getString(6));

        } catch (SQLException e) {
            PrintError.println("Error while changing password");
        } catch (Exception e) {
            PrintError.println("Unknown error while changing password");
        } finally {
            ConnectionDB.closeConnection(con, cs);
        }
        return account;
    }
}
