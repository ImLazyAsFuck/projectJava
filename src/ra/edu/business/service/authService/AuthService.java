package ra.edu.business.service.authService;

import ra.edu.business.model.User.Admin;
import ra.edu.business.model.User.Student;

public interface AuthService{
    Admin loginAsAdmin(String username, String password);
    Student loginAsStudent(String username, String password);
    Student changePassword(String username, String newPassword);
}
