package ra.edu.business.dao.authDAO;

import java.sql.CallableStatement;
import java.sql.Connection;

public class AuthDAOImp implements AuthDAO{

    @Override
    public void loginAsAdmin(String username, String password){
        Connection con = null;
        CallableStatement cs = null;

    }

    @Override
    public void loginAsStudent(String username, String password){

    }

    @Override
    public void changePassword(String username, String newPassword){

    }
}
