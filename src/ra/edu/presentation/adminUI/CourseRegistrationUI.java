package ra.edu.presentation.adminUI;

import ra.edu.utils.Input;

public class CourseRegistrationUI{
    public static void courseRegistrationMenu(String[] args) {
        int choice;
        do {
            System.out.println("\n===== COURSE REGISTRATION MANAGEMENT MENU =====");
            System.out.println("1. Display students by each course");
            System.out.println("2. Add a student to a course");
            System.out.println("3. Remove a student from a course");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");
            choice = Input.input.nextInt();
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
                    System.out.println("Invalid choice! Try again!");
            }
        } while (true);
    }
}
