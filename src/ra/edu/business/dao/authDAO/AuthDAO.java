package ra.edu.business.dao.authDAO;

import ra.edu.business.model.Account.Account;

public interface AuthDAO{
    Account loginAsAdmin(String username, String password);
    Account loginAsStudent(String username, String password);
    Account changePassword(String username, String newPassword);
}
