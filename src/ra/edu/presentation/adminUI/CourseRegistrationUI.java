package ra.edu.presentation.adminUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.student.Student;
import ra.edu.business.service.enrollmentService.EnrollmentService;
import ra.edu.business.service.enrollmentService.EnrollmentServiceImp;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.BooleanValidator;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;

import java.util.ArrayList;
import java.util.List;

import static ra.edu.presentation.adminUI.AdminUI.FIRST_PAGE;
import static ra.edu.presentation.adminUI.AdminUI.PAGE_SIZE;
import static ra.edu.presentation.adminUI.CourseManagementUI.COURSE_SERVICE;
import static ra.edu.presentation.adminUI.CourseManagementUI.isEmptyOrPrintCourses;
import static ra.edu.presentation.adminUI.StudentManagementUI.STUDENT_SERVICE;
import static ra.edu.presentation.adminUI.StudentManagementUI.isEmptyOrPrintStudents;

public class CourseRegistrationUI{
    public final static EnrollmentService ENROLLMENT_SERVICE = new EnrollmentServiceImp();
    public static void courseRegistrationMenu() {
        int choice;
        do {
            PrintColor.printlnYellow("+=============================================================+");
            PrintColor.printlnYellow("|             COURSE REGISTRATION MANAGEMENT MENU             |");
            PrintColor.printlnYellow("+====+========================================================+");
            PrintColor.printlnYellow("| 1. | Display students by each course                        |");
            PrintColor.printlnYellow("| 2. | Add a student to a course                              |");
            PrintColor.printlnYellow("| 3. | Remove a student from a course (if haven't registered) |");
            PrintColor.printlnYellow("| 4. | Approve or reject student registrations                |");
            PrintColor.printlnRed("| 5. | Return to main menu                                    |");
            PrintColor.printlnYellow("+====+=====================================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 5);
            System.out.println();
            switch (choice) {
                case 1:
                    displayStudentsByCourse();
                    break;
                case 2:
                    addStudentToCourse();
                    break;
                case 3:
                    removeStudentFromCourse();
                    break;
                case 4:
                    approveStudentRegistration();
                    break;
                case 5:
                    PrintColor.printCyan("Returning to main menu");
                    System.out.println();
                    return;
                default:
                    PrintError.println("Invalid choice! Try again!");
            }
        } while (true);
    }

    public static void addStudentToCourse(){
        if(StudentManagementUI.isEmptyOrPrintStudents()){
            return;
        }
        int studentId = IntegerValidator.validate("Enter the student Id: ", new LengthContain(0, 1000));
        if(STUDENT_SERVICE.findById(studentId) == null){
            PrintError.println("Not found student");
            return;
        }
        if(isEmptyOrPrintCourses()){
            return;
        }
        int courseId = IntegerValidator.validate("Enter the course Id: ", new LengthContain(0, 1000));
        if(COURSE_SERVICE.findbyId(courseId) == null){
            PrintError.println("Not found course with id: " + courseId);
            return;
        }
        if(ENROLLMENT_SERVICE.addStudentToEnrollment(courseId, studentId)){
            PrintSuccess.println("Student has been added to the course with id: " + courseId);
            System.out.println();
        }else{
            PrintError.println("Can't add to the course with id: " + courseId);
            System.out.println();
        }
    }

    public static void removeStudentFromCourse(){
        if(isEmptyOrPrintStudents()){
            return;
        }
        int studentId = IntegerValidator.validate("Enter the student Id: ", new LengthContain(0, 1000));
        if(STUDENT_SERVICE.findById(studentId) == null){
            PrintError.println("Not found student");
            return;
        }

        if(CourseRegistrationUI.ENROLLMENT_SERVICE.dislayCurrentAccCourse(studentId).isEmpty()){
            PrintError.println("Student has not registered to any course");
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
        int courseId =  IntegerValidator.validate("Enter course Id: ", new LengthContain(0, 1000));
        System.out.println("-".repeat(200));
        boolean confirmDelete = BooleanValidator.validateBoolean("Do you sure to delete this enrollment? (true/false)\n");
        if(!confirmDelete){
            PrintSuccess.println("You have cancelled the deletion of the enrollment!");
            return;
        }
        if(CourseRegistrationUI.ENROLLMENT_SERVICE.removeStudentFromEnrollment(courseId, studentId)){
            PrintSuccess.println("Student has been successfully removed!");
            System.out.println();
        }else{
            PrintError.println("Can't remove from the course with id: " + courseId);
            System.out.println();
        }
    }

    public static void displayStudentsByCourse() {
        if(isEmptyOrPrintCourses()){
            return;
        }

        int courseId = IntegerValidator.validate("Enter the course Id: ", new LengthContain(0, 1000));
        if(COURSE_SERVICE.findbyId(courseId) == null){
            PrintError.println("Not found course with id: " + courseId);
            return;
        }


        Pagination<Student> firstPage = ENROLLMENT_SERVICE.studentByCourse(courseId, FIRST_PAGE, PAGE_SIZE);
        if(firstPage.getItems().isEmpty()){
            PrintError.println("No student found in the course with id: " + courseId);
            return;
        }
        int totalPage = firstPage.getTotalPages();
//        int totalPage =  ENROLLMENT_SERVICE.studentByCourse(courseId, FIRST_PAGE, PAGE_SIZE).getTotalPages();
        int currentPage = FIRST_PAGE;

        while(true){
            Pagination<Student> studentPage = ENROLLMENT_SERVICE.studentByCourse(courseId, currentPage, PAGE_SIZE);
            System.out.println("=".repeat(200));
            System.out.printf("|%-5s | %-30s | %-15s | %-10s | %-15s | %-30s | %-20s|%n",
                    "ID", "Full Name", "Date of Birth", "Sex", "Phone", "Email", "Created At");
            System.out.println("-".repeat(200));

            studentPage.getItems().forEach(student -> {
                System.out.printf("|%-5d | %-30s | %-15s | %-10s | %-15s | %-30s | %-20s|%n",
                        student.getId(),
                        student.getFullName(),
                        student.getDob() != null? student.getDob().toString() : "N/A",
                        student.isSex()? "Male" : "Female",
                        student.getPhone(),
                        student.getEmail(),
                        student.getCreatedAt() != null? student.getCreatedAt().toString() : "N/A"
                );
            });
            System.out.println("-".repeat(200));

            StudentManagementUI.printPagination(currentPage, totalPage);

            if(totalPage <= 1){
                return;
            }
            StringBuilder options = new StringBuilder();
            List<String> validChoices = new ArrayList<>();

            if (currentPage > AdminUI.FIRST_PAGE) {
                options.append(String.format("%-20s", "P.Prev"));
                validChoices.add("1");
            }
            if (totalPage > 1) {
                options.append(String.format("%-20s", "C.Choose"));
                validChoices.add("2");
            }
            if (currentPage < totalPage) {
                options.append(String.format("%-20s", "N.Next"));
                validChoices.add("3");
            }
            options.append(String.format("%-20s", "E.Exit"));
            validChoices.add("4");

            System.out.println(options.toString());

            String subChoice = StringValidator.validate("Enter choice: ", new LengthContain(0, 1));
            switch (subChoice.toLowerCase()) {
                case "p":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println();
                        PrintError.println("You are already on the first page.");
                        System.out.println();
                    }
                    break;
                case "c":
                    System.out.println();
                    int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(0, 1000));
                    if (pageChoice >= 1 && pageChoice <= totalPage) {
                        currentPage = pageChoice;
                    } else {
                        System.out.println();
                        PrintError.println("Invalid page number.");
                        System.out.println();
                    }
                    break;
                case "n":
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
                        PrintError.println("You are already on the last page.");
                        System.out.println();
                    }
                    break;
                case "e":
                    PrintSuccess.println("Exiting choice page");
                    System.out.println();
                    return;
                default:
                    PrintError.println("Invalid choice. Please choose between 1, 2, 3 and 4.");
                    System.out.println();
            }
        }
    }
    
    public static void approveStudentRegistration(){
        if(isEmptyOrPrintCourses()){
            return;
        }
        int courseId = IntegerValidator.validate("Enter the course Id: ", new LengthContain(0, 1000));
        if(COURSE_SERVICE.findbyId(courseId) == null){
            PrintError.println("Not found course with id: " + courseId);
            return;
        }

        Pagination<Student> pendingStudents = ENROLLMENT_SERVICE.studentByCourse(courseId, FIRST_PAGE, PAGE_SIZE);
        if(pendingStudents.getItems().isEmpty()){
            PrintError.println("No pending students found in this course");
            return;
        }

        System.out.println("Students pending approval in this course:");
        System.out.println("=".repeat(200));
        System.out.printf("|%-5s | %-30s | %-15s | %-10s | %-15s | %-30s | %-20s|%n",
                "ID", "Full Name", "Date of Birth", "Sex", "Phone", "Email", "Created At");
        System.out.println("-".repeat(200));
        pendingStudents.getItems().forEach(student -> {
            System.out.printf("|%-5d | %-30s | %-15s | %-10s | %-15s | %-30s | %-20s|%n",
                    student.getId(),
                    student.getFullName(),
                    student.getDob() != null? student.getDob().toString() : "N/A",
                    student.isSex()? "Male" : "Female",
                    student.getPhone(),
                    student.getEmail(),
                    student.getCreatedAt() != null? student.getCreatedAt().toString() : "N/A"
            );
        });

        int studentId = IntegerValidator.validate("Enter student Id to approve: ", new LengthContain(0, 1000));
        if(STUDENT_SERVICE.findById(studentId) == null){
            PrintError.println("Not found student");
            return;
        }

        boolean approve = BooleanValidator.validateBoolean("Do you want to approve this registration? (true/false)\n");
        if(ENROLLMENT_SERVICE.approveStudent(courseId, studentId)){
            if(approve){
                PrintSuccess.println("Student registration has been approved!");
            }else{
                PrintSuccess.println("Cancelled student registration approval!");
            }
        }
        System.out.println();
    }
}
