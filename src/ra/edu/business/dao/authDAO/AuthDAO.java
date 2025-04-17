package ra.edu.business.dao.authDAO;

public interface AuthDAO{
    void loginAsAdmin(String username, String password);
    void loginAsStudent(String username, String password);
    void changePassword(String username, String newPassword);
}
