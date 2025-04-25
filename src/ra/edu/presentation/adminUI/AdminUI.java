package ra.edu.presentation.adminUI;

import ra.edu.business.model.Account.Account;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.ChoiceValidator;


public class AdminUI{
    public final static int PAGE_SIZE = 5;
    public final static int FIRST_PAGE = 1;
    public static void showAdminMenu() {
        int choice;
        do {
            PrintColor.printlnCyan("\n+========================================+");
            PrintColor.printCyan("|             ");
            PrintColor.printCyan("ADMIN MENU");
            PrintColor.printCyan("                 |\n");
            PrintColor.printlnCyan("+====+===================================+");
            PrintColor.printlnCyan("| 1. | Manage Courses                    |");
            PrintColor.printlnCyan("| 2. | Manage Students                   |");
            PrintColor.printlnCyan("| 3. | Manage Course Registrations       |");
            PrintColor.printlnCyan("| 4. | Statistics by Course              |");
            PrintColor.printlnRed("| 5. | Logout                            |");
            PrintColor.printlnCyan("+====+===================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 5);
            System.out.println();
            switch (choice) {
                case 1:
                    CourseManagementUI.showCourseMenu();
                    break;
                case 2:
                    StudentManagementUI.displayStudentManagement();
                    break;
                case 3:
                    CourseRegistrationUI.courseRegistrationMenu();
                    break;
                case 4:
                    StatisticUI.displayStatisticMenu();
                    break;
                case 5:
                    PrintColor.printCyan("Logout successful!");
                    System.out.println();
                    return;
                default:
                    PrintError.println("Invalid choice! Please choose either 1 or 5!");
            }
        } while (true);
    }
}
