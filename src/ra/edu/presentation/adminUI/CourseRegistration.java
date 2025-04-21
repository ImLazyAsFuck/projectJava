package ra.edu.presentation.adminUI;

import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.ChoiceValidator;

public class CourseRegistration{
    public static void displayCourseRegistrationMenu() {
        System.out.println("+----------------------------------------------------+");
        System.out.println("|              Course Registration Menu              |");
        System.out.println("+----------------------------------------------------+");
        System.out.println("|  1  | Display students by course                   |");
        System.out.println("|  2  | Add student to course                        |");
        System.out.println("|  3  | Remove student from course                   |");
        System.out.println("|  4  | Return to main menu                          |");
        System.out.println("+----------------------------------------------------+");

        int choice = ChoiceValidator.validateChoice(">> Choose an option: ", 4);
        switch(choice){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                PrintColor.printCyan("Return to main menu");
                return;
            default:
                PrintError.println("Invalid choice. Please choose 1 to 4.");
        }
    }


}
