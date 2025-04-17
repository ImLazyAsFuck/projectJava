package ra.edu.business.dao.authDAO;

import ra.edu.business.model.User.Admin;
import ra.edu.business.model.User.Student;

public interface AuthDAO{
    Admin loginAsAdmin(String username, String password);
    Student loginAsStudent(String username, String password);
    Student changePassword(String username, String newPassword);
}
