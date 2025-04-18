package ra.edu.presentation.adminUI;

import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.validate.ChoiceValidator;

import static ra.edu.presentation.adminUI.CourseManagementUI.showCourseMenu;

public class AdminUI{
    public static void showAdminMenu() {
        int choice;
        do {
            System.out.println("========= ADMIN MENU =========");
            System.out.println("1. Manage Courses");
            System.out.println("2. Manage Students");
            System.out.println("3. Manage Course Registrations");
            System.out.println("4. Statistics by Course");
            System.out.println("5. Logout");
            System.out.println("================================");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 5);
            System.out.println();
            switch (choice) {
                case 1:
                    showCourseMenu();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    PrintSuccess.println("Logout successful!");
                    return;
                default:
                    PrintError.println("Invalid choice! Please choose either 1 or 5!");
            }
        } while (true);
    }


}
