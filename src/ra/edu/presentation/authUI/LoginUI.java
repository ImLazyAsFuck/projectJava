package ra.edu.presentation.authUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.business.service.authService.AuthService;
import ra.edu.business.service.authService.AuthServiceImp;
import ra.edu.presentation.adminUI.AdminUI;
import ra.edu.presentation.studentUI.StudentUI;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.StringValidator;

public class LoginUI{
    public final static AuthService AUTH_SERVICE = new AuthServiceImp();

    public static void displayLoginMenu(){
        int choice;
        do {
            PrintColor.printlnGreen("+==============================================+");
            PrintColor.printlnGreen("|         TRAINING MANAGEMENT SYSTEM           |");
            PrintColor.printlnGreen("+====+=========================================+");
            PrintColor.printlnGreen("| 1. | Login as Administrator                  |");
            PrintColor.printlnGreen("| 2. | Login as Student                        |");
            PrintColor.printlnRed("| 3. | Exit                                    |");
            PrintColor.printlnGreen("+====+=========================================+");
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
                    PrintColor.printCyan("Good bye!");
                    System.out.println();
                    break;
                default:
                    System.out.println("Please choose between 1 and 3!");
            }
        } while (choice != 3);
    }

    public static void loginAsAdmin() {
        PrintColor.printlnGreen("+============================+");
        PrintColor.printlnGreen("|      ADMIN LOGIN FORM      |");
        PrintColor.printlnGreen("+============================+");
        String username = StringValidator.validate( "Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));

        Account account = AUTH_SERVICE.loginAsAdmin(username, password);
        Account.currentAccount = account;
        if (account != null) {
            welcomeTheAccount();
            AdminUI.showAdminMenu();
        } else {
            PrintError.println("\n+--------------------------------+");
            PrintError.println("| Incorrect username or password! |");
            PrintError.println("+--------------------------------+\n");
            System.out.println();
        }
    }


    public static void loginAsStudent() {
        PrintColor.printlnBlue("+=============================+");
        PrintColor.printlnBlue("|     STUDENT LOGIN FORM      |");
        PrintColor.printlnBlue("+=============================+");
        String username = StringValidator.validate("Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));

        Account account = AUTH_SERVICE.loginAsStudent(username, password);
        Account.currentAccount = account;
        if (account != null) {
            welcomeTheAccount();
            StudentUI.studentMainMenu();
        } else {
            PrintError.println("\n+-------------------------------+");
            PrintError.println("| Incorrect username or password! |");
            PrintError.println("+-------------------------------+");
            System.out.println();
        }
    }

    private static void welcomeTheAccount(){
        String message = "Welcome, " + Account.currentAccount.getUsername() + " has successfully logged in.";
        int width = 80;
        int padding = (width - 2 - message.length()) / 2;
        String centered = "|" + " ".repeat(padding) + message + " ".repeat(width - 2 - padding - message.length()) + "|";
        PrintSuccess.println("\n+" + "-".repeat(width - 2) + "+");
        PrintSuccess.println(centered);
        PrintSuccess.println("+" + "-".repeat(width - 2) + "+\n");
    }

}
