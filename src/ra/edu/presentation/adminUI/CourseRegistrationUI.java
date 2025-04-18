package ra.edu.presentation.adminUI;

import ra.edu.utils.Print.PrintError;
import ra.edu.validate.ChoiceValidator;

public class CourseRegistrationUI{
    public static void courseRegistrationMenu(String[] args) {
        int choice;
        do {
            System.out.println("\n===== COURSE REGISTRATION MANAGEMENT MENU =====");
            System.out.println("1. Display students by each course");
            System.out.println("2. Add a student to a course");
            System.out.println("3. Remove a student from a course");
            System.out.println("4. Return to main menu");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 4);
            switch (choice) {
                case 1:
                    System.out.println("Displaying students by each course...");
                    break;
                case 2:
                    System.out.println("Adding a student to a course...");
                    break;
                case 3:
                    System.out.println("Removing a student from a course...");
                    break;
                case 4:
                    System.out.println("Returning to main menu");
                    return;
                default:
                    PrintError.println("Invalid choice! Try again!");
            }
        } while (true);
    }
}
