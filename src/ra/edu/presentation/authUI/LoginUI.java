package ra.edu.presentation.authUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.business.service.authService.AuthService;
import ra.edu.business.service.authService.AuthServiceImp;
import ra.edu.presentation.adminUI.AdminUI;
import ra.edu.presentation.studentUI.StudentUI;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.StringValidator;

public class LoginUI{
    private final static AuthService AUTH_SERVICE = new AuthServiceImp();

    public static void displayLoginMenu(){
        int choice;
        do {
            System.out.println("========= TRAINING MANAGEMENT SYSTEM =========");
            System.out.println("1. Login as Administrator");
            System.out.println("2. Login as Student");
            System.out.println("3. Exit");
            System.out.println("==============================================");
            choice = ChoiceValidator.validateChoice("Enter your choice: ", 3);
            System.out.println();
            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    loginAsStudent();
                    break;
                case 3:
                    System.out.println("Good bye!");
                    break;
                default:
                    System.out.println("Please choose between 1 and 3!");
            }
        } while (choice != 3);
    }

    public static void loginAsAdmin(){
        String username = StringValidator.validate("Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));
        Account account = AUTH_SERVICE.loginAsAdmin(username, password);
        if (account != null) {
            System.out.println();
            PrintSuccess.println("Logged in successfully!");
            System.out.println();
            AdminUI.showAdminMenu();
        }else{
            System.out.println();
            PrintError.println("Incorrect username or password!");
            System.out.println();
        }
    }

    public static void loginAsStudent(){
        String username = StringValidator.validate("Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));
        Account account = AUTH_SERVICE.loginAsStudent(username, password);
        if (account != null) {
            PrintSuccess.println("Logged in successfully!");
            StudentUI.studentMainMenu();
        }else{
            PrintError.println("Incorrect username or password!");
        }
    }
}
