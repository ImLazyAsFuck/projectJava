package ra.edu.presentation;

import ra.edu.utils.Input;
import ra.edu.utils.PrintError;
import ra.edu.validate.ChoiceValidator;

public class CourseManagementUI{
    public static void showCourseMenu() {
        int choice;
        do {
            System.out.println("========= COURSE MANAGEMENT =========");
            System.out.println("1. Show all courses");
            System.out.println("2. Add new course");
            System.out.println("3. Update course (choose attribute to edit)");
            System.out.println("4. Delete course (with confirmation)");
            System.out.println("5. Search by name (partial match)");
            System.out.println("6. Sort by name or ID (ascending/descending)");
            System.out.println("7. Return to admin menu");
            System.out.println("======================================");
            System.out.print("Nhập lựa chọn: ");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 7);

            switch (choice) {
                case 1:
                    displayCourse();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("Quay về menu Admin.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 7);
    }

    public static void displayCourse() {
        
    }

}
