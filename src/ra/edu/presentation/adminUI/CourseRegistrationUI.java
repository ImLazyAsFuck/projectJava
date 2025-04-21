package ra.edu.presentation.adminUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.business.service.enrollmentService.EnrollmentService;
import ra.edu.business.service.enrollmentService.EnrollmentServiceImp;
import ra.edu.utils.Print.PrintError;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;

import static ra.edu.presentation.adminUI.CourseManagementUI.COURSE_SERVICE;
import static ra.edu.presentation.adminUI.StudentManagementUI.STUDENT_SERVICE;

public class CourseRegistrationUI{
    public final static EnrollmentService ENROLLMENT_SERVICE = new EnrollmentServiceImp();
    public static void courseRegistrationMenu(String[] args) {
        int choice;
        do {
            System.out.println("\n+===========================================================+");
            System.out.println("|           COURSE REGISTRATION MANAGEMENT MENU            |");
            System.out.println("+====+=====================================================+");
            System.out.println("| 1. | Display students by each course                     |");
            System.out.println("| 2. | Add a student to a course                           |");
            System.out.println("| 3. | Remove a student from a course                      |");
            System.out.println("| 4. | Return to main menu                                 |");
            System.out.println("+====+=====================================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 4);
            System.out.println();
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


    public static void displayStudentByCourse(){
//       if(isEmptyOrPrintCourses()){
//           return;
//       }
//       int currentPage = 1;
//       int coutseId = IntegerValidator.validate("Enter course Id: ", new LengthContain(0, 1000));
//       int studentId = STUDENT_SERVICE.findStudentById(Account.currentAccount.getId());
//       if(studentId == -1){
//           PrintError.println("Student not found! Try again!");
//       }
//       ENROLLMENT_SERVICE.studentByCourse(studentId, currentPage, size, coutseId);
    }
}
