package ra.edu.business.service.authService;

import ra.edu.business.dao.authDAO.AuthDAO;
import ra.edu.business.dao.authDAO.AuthDAOImp;
import ra.edu.business.model.Account.Account;

public class AuthServiceImp implements AuthService{
    private final static AuthDAO authDAO = new AuthDAOImp();
    @Override
    public Account loginAsAdmin(String username, String password){
        return authDAO.loginAsAdmin(username, password);
    }

    @Override
    public Account loginAsStudent(String username, String password){
        return authDAO.loginAsStudent(username, password);
    }

    @Override
    public Account changePassword(int id, String newPassword){
        return authDAO.changePassword(id, newPassword);
    }
}
