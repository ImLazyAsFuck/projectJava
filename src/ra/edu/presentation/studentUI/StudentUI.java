package ra.edu.presentation.studentUI;

import ra.edu.utils.Input;

public class StudentUI{
    public static void studentMainMenu() {
        int choice;
        do {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View the list of courses");
            System.out.println("2. Register for a course");
            System.out.println("3. View registered courses");
            System.out.println("4. Cancel registration (if not yet started)");
            System.out.println("5. Change password");
            System.out.println("6. Log out");
            System.out.print("Enter your choice: ");
            choice = Input.input.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing the list of courses...");
                    break;
                case 2:
                    System.out.println("Registering for a course...");
                    break;
                case 3:
                    System.out.println("Viewing registered courses...");
                    break;
                case 4:
                    System.out.println("Canceling registration...");
                    break;
                case 5:
                    System.out.println("Changing password...");
                    break;
                case 6:
                    System.out.println("Log out...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again!");
            }
        } while (true);
    }
}
