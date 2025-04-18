package ra.edu.presentation.adminUI;

import ra.edu.utils.Print.PrintError;
import ra.edu.validate.ChoiceValidator;

public class StudentManagementUI{
    public static void displayStudentManagement(){
        int choice;
        do {
            System.out.println("\n===== STUDENT MANAGEMENT MENU =====");
            System.out.println("1. Display the list of students");
            System.out.println("2. Add a new student");
            System.out.println("3. Edit student information (Show menu to select attribute to edit)");
            System.out.println("4. Delete a student (Soft delete from course)");
            System.out.println("5. Search by name, email");
            System.out.println("6. Sort by ID (ascending/descending)");
            System.out.println("7. Return to main menu");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 7);
            switch (choice) {
                case 1:
                    System.out.println("Displaying the list of students...");
                    break;
                case 2:
                    System.out.println("Adding a new student...");
                    break;
                case 3:
                    System.out.println("Editing student information...");
                    break;
                case 4:
                    System.out.println("Deleting a student...");
                    break;
                case 5:
                    System.out.println("Searching by name, email, or ID...");
                    break;
                case 6:
                    System.out.println("Sorting by ID...");
                    break;
                case 7:
                    System.out.println("Returning to main menu");
                    return;
                default:
                    PrintError.println("Invalid choice! Try again!");
            }
        } while (true);
    }


}
