package ra.edu.presentation.studentUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.presentation.adminUI.CourseManagementUI;
import ra.edu.presentation.adminUI.CourseRegistrationUI;
import ra.edu.presentation.adminUI.StudentManagementUI;
import ra.edu.presentation.authUI.LoginUI;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.*;

import static ra.edu.presentation.adminUI.CourseManagementUI.COURSE_SERVICE;

public class StudentUI{
    public static void studentMainMenu() {
        int choice;
        do {
            if(Account.currentAccount == null){
                PrintError.println("Please login first!");
                return;
            }
            PrintColor.printlnPurple("\n+=============================================================+");
            PrintColor.printlnPurple("|                         STUDENT MENU                        |");
            PrintColor.printlnPurple("+====+========================================================+");
            PrintColor.printlnPurple("| 1. | View the list of courses                               |");
            PrintColor.printlnPurple("| 2. | Register for a course                                  |");
            PrintColor.printlnPurple("| 3. | View registered courses                                |");
            PrintColor.printlnPurple("| 4. | Cancel registration (if not yet started)               |");
            PrintColor.printlnPurple("| 5. | Change password                                        |");
            PrintError.println("| 6. | Log out                                                |");
            PrintColor.printlnPurple("+====+========================================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 6);
            System.out.println();
            switch (choice) {
                case 1:
                    CourseManagementUI.displayCourse();
                    break;
                case 2:
                    addToEnrollment();
                    break;
                case 3:
                    displayRegisterCourse();
                    break;
                case 4:
                    removeStudentFromEnrollment();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    PrintColor.printCyan("Return to menu");
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid choice! Please choose from 1 to 6.!");
            }
        } while (true);
    }

    public static void addToEnrollment(){
        int studentId = StudentManagementUI.STUDENT_SERVICE.findStudentById(Account.currentAccount.getId());
        if(studentId == -1){
            System.out.println("Student not exists! Try again!");
            return;
        }
        if(CourseManagementUI.isEmptyOrPrintCourses()){
            return;
        }
        int courseId = IntegerValidator.validate("Enter course Id: ", new LengthContain(0, 1000));
        if(CourseRegistrationUI.ENROLLMENT_SERVICE.addStudentToEnrollment(courseId, studentId)){
            PrintSuccess.println("You has been successfully registered!");
            System.out.println();
        }else{
            PrintError.println("Can't register to the course with id: " + courseId);
            System.out.println();
        }
    }

    public static void removeStudentFromEnrollment(){
        if(COURSE_SERVICE.findAll().isEmpty()){
            PrintError.println("You have not enrolled any courses!");
            return;
        }
        if(Account.currentAccount == null){
            PrintError.println("Please login to change password");
            return;
        }
        int studentId = StudentManagementUI.STUDENT_SERVICE.findStudentById(Account.currentAccount.getId());
        if(studentId == -1){
            PrintError.println("Student not exists! Try again!");
            return;
        }
        displayRegisterCourse();
        int courseId =  IntegerValidator.validate("Enter course Id: ", new LengthContain(0, 1000));
        if(CourseRegistrationUI.ENROLLMENT_SERVICE.studentCancelfromEnrollment(courseId, studentId)){
            PrintSuccess.println("You has been successfully removed!");
            System.out.println();
        }else{
            PrintError.println("Can't remove from the course with id: " + courseId);
            System.out.println();
        }
    }

    public static void changePassword(){
        if(Account.currentAccount == null){
            PrintError.println("Please login to change password");
            return;
        }
        String oldPassword = AuthValidator.confirmPassword("Enter password: ", Account.currentAccount.getPassword());
        String newPassword = AuthValidator.validatePassword("Enter new password: ", oldPassword);
        Account.currentAccount = LoginUI.AUTH_SERVICE.changePassword(Account.currentAccount.getId(), newPassword);
        PrintSuccess.println("You have successfully changed your password!");
    }

    public static void displayRegisterCourse(){
        if(Account.currentAccount == null){
            PrintError.println("Please login to register course");
            return;
        }
        if(COURSE_SERVICE.findAll().isEmpty()){
            PrintError.println("No course found!");
            return;
        }
        int studentId = StudentManagementUI.STUDENT_SERVICE.findStudentById(Account.currentAccount.getId());
        if(CourseRegistrationUI.ENROLLMENT_SERVICE.dislayCurrentAccCourse(studentId).isEmpty()){
            PrintError.println("Course not found!");
            return;
        }
        System.out.println("=".repeat(200));
        System.out.printf("|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n",
                "Course Id", "Course Name", "Duration", "Description", "Instructor", "Created At");
        System.out.println("-".repeat(200));
        CourseRegistrationUI.ENROLLMENT_SERVICE.dislayCurrentAccCourse(studentId).forEach(
                course ->
                        System.out.printf("|%-10d | %-40s | %-20d | %-70s | %-20s | %-20s|%n",
                                course.getId(), course.getName(), course.getDuration(),
                                course.getDescription(), course.getInstructor(),
                                course.getCreatedAt() != null ? course.getCreatedAt().toString() : "N/A")
        );
        System.out.println("-".repeat(200));
    }
}
