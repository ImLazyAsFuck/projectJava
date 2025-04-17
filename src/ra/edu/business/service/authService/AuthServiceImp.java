package ra.edu.business.service.authService;

import ra.edu.business.dao.authDAO.AuthDAO;
import ra.edu.business.dao.authDAO.AuthDAOImp;
import ra.edu.business.model.User.Admin;
import ra.edu.business.model.User.Student;

public class AuthServiceImp implements AuthDAO{
    private final static AuthDAO authDAO = new AuthDAOImp();
    @Override
    public Admin loginAsAdmin(String username, String password){
        return authDAO.loginAsAdmin(username, password);
    }

    @Override
    public Student loginAsStudent(String username, String password){
        return authDAO.loginAsStudent(username, password);
    }

    @Override
    public Student changePassword(String username, String newPassword){
        return authDAO.changePassword(username, newPassword);
    }
}
