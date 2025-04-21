package ra.edu.business.service.authService;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.student.Student;

public interface AuthService{
    Account loginAsAdmin(String username, String password);
    Account loginAsStudent(String username, String password);
    Account changePassword(int id, String newPassword);
}
