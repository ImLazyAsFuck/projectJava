package ra.edu.presentation.adminUI;

import ra.edu.business.model.Account.Account;
import ra.edu.business.model.LengthContain;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.student.Student;
import ra.edu.business.service.studentService.StudentService;
import ra.edu.business.service.studentService.StudentServiceImp;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.validate.BooleanValidator;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;

import java.util.List;

import static ra.edu.presentation.adminUI.AdminUI.FIRST_PAGE;
import static ra.edu.presentation.adminUI.AdminUI.PAGE_SIZE;

public class StudentManagementUI{
    public final static StudentService STUDENT_SERVICE = new StudentServiceImp();

    public static void displayStudentManagement(){
        int choice;
        do{
            System.out.println("\n+===============================================================+");
            System.out.println("|                  STUDENT MANAGEMENT MENU                      |");
            System.out.println("+====+==========================================================+");
            System.out.println("| 1. | Display the list of students                             |");
            System.out.println("| 2. | Add a new student                                        |");
            System.out.println("| 3. | Edit student information (select attribute to edit)      |");
            System.out.println("| 4. | Delete a student (Soft delete from course)               |");
            System.out.println("| 5. | Search by name, email, id                                |");
            System.out.println("| 6. | Sort by name or email (ascending/descending)             |");
            System.out.println("| 7. | Return to main menu                                      |");
            System.out.println("+====+==========================================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 7);
            System.out.println();
            switch(choice){
                case 1:
                    displayStudentByPage();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    sortStudent();
                    break;
                case 7:
                    System.out.println("Returning to main menu");
                    return;
                default:
                    PrintError.println("Invalid choice! Try again!");
            }
        }while(true);
    }

    public static boolean isEmptyOrPrintStudents(){
        if(STUDENT_SERVICE.findAll().isEmpty()){
            PrintError.println("Student list is empty.");
            return true;
        }

        System.out.println("=".repeat(160));
        System.out.printf("|%-10s | %-30s | %-12s | %-10s | %-15s | %-30s | %-20s|%n",
                "Student Id", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
        System.out.println("-".repeat(160));

        STUDENT_SERVICE.findAll().forEach(student ->
                System.out.printf("|%-10d | %-30s | %-12s | %-10s | %-15s | %-30s | %-20s|%n",
                        student.getId(),
                        student.getFullName(),
                        student.getDob() != null? student.getDob().toString() : "N/A",
                        student.isSex()? "Male" : "Female",
                        student.getPhone(),
                        student.getEmail(),
                        student.getCreatedAt() != null? student.getCreatedAt().toString() : "N/A")
        );
        return false;
    }

    public static void displayStudentByPage(){
        Pagination<Student> firstPage = STUDENT_SERVICE.findPage(FIRST_PAGE, PAGE_SIZE);
        if(firstPage.getItems().isEmpty()){
            System.out.println();
            PrintSuccess.println("No students found.");
            System.out.println();
            return;
        }

        int totalPage = firstPage.getTotalPages();
        int currentPage = FIRST_PAGE;

        while(true){
            Pagination<Student> studentPage = STUDENT_SERVICE.findPage(currentPage, PAGE_SIZE);
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

            System.out.printf("Page %d/%d%n", currentPage, totalPage);
            printPagination(currentPage, totalPage);
            System.out.printf("%-20s%-20s%-20s%-20s\n", "1.Prev", "2.Choose", "3.Next", "4.Exit");

            int subChoice = ChoiceValidator.validateChoice("Enter choice: ", 4);
            switch(subChoice){
                case 1:
                    if(currentPage > 1){
                        currentPage--;
                    }else{
                        System.out.println();
                        PrintError.println("You are already on the first page.");
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println();
                    int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(0, 1000));
                    if(pageChoice >= 1 && pageChoice <= totalPage){
                        currentPage = pageChoice;
                    }else{
                        System.out.println();
                        PrintError.println("Invalid page number.");
                        System.out.println();
                    }
                    break;
                case 3:
                    if(currentPage < totalPage){
                        currentPage++;
                    }else{
                        PrintError.println("You are already on the last page.");
                        System.out.println();
                    }
                    break;
                case 4:
                    PrintSuccess.println("Exiting choice page");
                    System.out.println();
                    return;
                default:
                    PrintError.println("Invalid choice. Please choose between 1, 2, 3 and 4.");
                    System.out.println();
            }
        }
    }


    public static void printPagination(int currentPage, int totalPage){
        final int visibleRange = 1;
        final String CYAN = "\u001B[36m";
        final String RESET = "\u001B[0m";

        if(currentPage > 1){
            System.out.print("<< Prev | ");
        }

        boolean dotsBefore = false;
        boolean dotsAfter = false;

        for(int i = 1; i <= totalPage; i++){
            if(i == 1 || i == totalPage || (i >= currentPage - visibleRange && i <= currentPage + visibleRange)){
                if(i == currentPage){
                    System.out.print(CYAN + "[" + i + "]" + RESET + " ");
                }else{
                    System.out.print(i + " ");
                }
            }else if(i < currentPage - visibleRange && !dotsBefore){
                System.out.print("... ");
                dotsBefore = true;
            }else if(i > currentPage + visibleRange && !dotsAfter){
                System.out.print("... ");
                dotsAfter = true;
            }
        }

        if(currentPage < totalPage){
            System.out.println("| Next >>");
        }else{
            System.out.println();
        }
    }

    public static void addStudent(){
        Student student = new Student();
        Account account = new Account();
        account.inputData();
        student.inputData();
        if(STUDENT_SERVICE.save(account,student)){
            PrintSuccess.println("Student added.");
            System.out.println();
        }else{
            PrintError.println("Student can not added because unknown error.");
        }
    }


    public static void updateStudent(){
        if(isEmptyOrPrintStudents()){
            return;
        }
        int id = IntegerValidator.validate("Enter student id: ", new LengthContain(0, 1000));
        Student student = STUDENT_SERVICE.findById(id);
        if(student == null){
            PrintError.println("Student not found.");
            return;
        }
        int choice;
        while(true){
            System.out.println("============== Update Student Menu ==============");
            System.out.println("| 1. | Update name                              |");
            System.out.println("| 2. | Update date of birth                     |");
            System.out.println("| 3. | Update sex                               |");
            System.out.println("| 4. | Update phone number                      |");
            System.out.println("| 5. | Update email                             |");
            System.out.println("| 6. | Exit                                     |");
            System.out.println("=================================================");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 6);

            System.out.println();
            switch(choice){
                case 1:
                    student.setFullName(student.inputFullName());
                    PrintSuccess.println("Student name updated.");
                    break;
                case 2:
                    student.setDob(student.inputDob());
                    PrintSuccess.println("Student date of birth updated.");
                    break;
                case 3:
                    student.setSex(student.inputSex());
                    PrintSuccess.println("Student sex updated.");
                    break;
                case 4:
                    student.setPhone(student.inputPhone());
                    PrintSuccess.println("Student phone number updated.");
                    break;
                case 5:
                    student.setEmail(student.inputEmail());
                    PrintSuccess.println("Student email updated.");
                    break;
                case 6:
                    STUDENT_SERVICE.update(student);
                    System.out.println("Goodbye!\n");
                    return;
                default:
                    PrintError.println("Invalid choice! Please choose between 1 and 6.");
            }
            System.out.println();
        }
    }

    public static void deleteStudent(){
        if(isEmptyOrPrintStudents()){
            return;
        }
        int id = IntegerValidator.validate("Enter student id: ", new LengthContain(0, 1000));
        Student student = STUDENT_SERVICE.findById(id);
        if(student == null){
            PrintError.println("Student not found.");
            return;
        }
        if(STUDENT_SERVICE.findById(id) == null){
            PrintError.println("Course not found!");
            return;
        }
        if(STUDENT_SERVICE.isStudentLocked(id)){
            if(STUDENT_SERVICE.unlockStudent(id)) {
                PrintSuccess.println("Student unlocked successfully.");
            }else{
                PrintError.println("Course could not be unlocked.");
            }
            return;
        }
        boolean confirmation = BooleanValidator.validateBoolean("Do you sure to blocked this student?(true/false): ");
        if (!confirmation) {
            PrintSuccess.println("Student blocked canceled.");
            return;
        }
        if(STUDENT_SERVICE.delete(id)) {
            PrintSuccess.println("student blocked successfully.");
        }else{
            PrintError.println("Course could not be deleted.");
        }
    }

    public static void searchStudent(){
        if(isEmptyOrPrintStudents()){
            return;
        }
        while(true){
            System.out.println("+=============================================+");
            System.out.println("|               SEARCH STUDENT MENU           |");
            System.out.println("+====+========================================+");
            System.out.println("| 1. | Search Student by ID                   |");
            System.out.println("| 2. | Search Student by Name                 |");
            System.out.println("| 3. | Search Student by Email                |");
            System.out.println("+====+========================================+");
            int choice = ChoiceValidator.validateChoice("Enter choice: ", 3);
            System.out.println();
            switch(choice){
                case 1:
                    searchStudentById();
                    return;
                case 2:
                    searchStudentByName();
                    return;
                case 3:
                    searchStudentByEmail();
                    return;
                default:
                    PrintError.println("Invalid choice! Please choose between 1 and 4.");
            }
        }
    }

    public static void searchStudentById() {
        int id = IntegerValidator.validate("Enter student ID: ", new LengthContain(0, 1000));
        Student student = STUDENT_SERVICE.findById(id);

        if (student == null) {
            PrintError.println("Student not found.");
            System.out.println();
            return;
        }

//        PrintSuccess.println("Student found.");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                "ID", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        System.out.printf("| %-4d | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                student.getId(),
                student.getFullName(),
                student.getDob(),
                student.isSex() ? "Male" : "Female",
                student.getPhone(),
                student.getEmail(),
                student.getCreatedAt());

        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }

    public static void searchStudentByEmail(){
        String email = StringValidator.validate("Enter email: ", new LengthContain(0, 100));
        int currentPage = FIRST_PAGE;
        Pagination<Student> foundStudent = STUDENT_SERVICE.findByEmail(email, FIRST_PAGE, PAGE_SIZE);

        if (foundStudent == null) {
            PrintError.println("Student not found.");
            System.out.println();
            return;
        }

        int totalPage = foundStudent.getTotalPages();

        while(true){
            System.out.println("=".repeat(200));
            System.out.printf("%-5s | %-20s | %-10s | %-6s | %-12s | %-25s | %-20s\n",
                    "ID", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
            System.out.println("-----------------------------------------------------------------------------------------------");

            foundStudent.getItems().forEach(student ->
                    System.out.printf("%-5d | %-20s | %-10s | %-6s | %-12s | %-25s | %-20s\n",
                            student.getId(),
                            student.getFullName(),
                            student.getDob(),
                            student.isSex() ? "Male" : "Female",
                            student.getPhone(),
                            student.getEmail(),
                            student.getCreatedAt())
            );
            System.out.printf("Page %d/%d%n", currentPage, totalPage);
            printPagination(currentPage, totalPage);

            System.out.printf("%-20s%-20s%-20s%-20s\n", "1.prev", "2.choose page", "3.next", "4.exit");
            int subChoice = ChoiceValidator.validateChoice("Enter choice: ", 4);

            switch (subChoice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        PrintError.println("You are already on the first page.");
                    }
                    break;
                case 2:
                    int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(0, 1000));
                    if (pageChoice >= 1 && pageChoice <= totalPage) {
                        currentPage = pageChoice;
                    } else {
                        PrintError.println("Invalid page number.");
                    }
                    break;
                case 3:
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
                        PrintError.println("You are already on the last page.");
                    }
                    break;
                case 4:
                    PrintSuccess.println("Exiting choice page");
                    return;
                default:
                    PrintError.println("Invalid choice. Please choose between 1, 2, 3 and 4.");
            }
        }
    }

    public static void searchStudentByName(){

        String name = StringValidator.validate("Enter name: ", new LengthContain(0, 100));
        int currentPage = FIRST_PAGE;
        Pagination<Student> foundStudent = STUDENT_SERVICE.findByName(name, FIRST_PAGE, PAGE_SIZE);

        if (foundStudent == null) {
            PrintError.println("Student not found.");
            System.out.println();
            return;
        }

        int totalPage = foundStudent.getTotalPages();

        while(true){
            System.out.println("=".repeat(200));
            System.out.printf("%-5s | %-20s | %-10s | %-6s | %-12s | %-25s | %-20s\n",
                    "ID", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
            System.out.println("-----------------------------------------------------------------------------------------------");

            foundStudent.getItems().forEach(student ->
                    System.out.printf("%-5d | %-20s | %-10s | %-6s | %-12s | %-25s | %-20s\n",
                            student.getId(),
                            student.getFullName(),
                            student.getDob(),
                            student.isSex() ? "Male" : "Female",
                            student.getPhone(),
                            student.getEmail(),
                            student.getCreatedAt())
            );
            System.out.printf("Page %d/%d%n", currentPage, totalPage);
            printPagination(currentPage, totalPage);

            System.out.printf("%-20s%-20s%-20s%-20s\n", "1.prev", "2.choose page", "3.next", "4.exit");
            int subChoice = ChoiceValidator.validateChoice("Enter choice: ", 4);

            switch (subChoice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        PrintError.println("You are already on the first page.");
                    }
                    break;
                case 2:
                    int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(0, 1000));
                    if (pageChoice >= 1 && pageChoice <= totalPage) {
                        currentPage = pageChoice;
                    } else {
                        PrintError.println("Invalid page number.");
                    }
                    break;
                case 3:
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
                        PrintError.println("You are already on the last page.");
                    }
                    break;
                case 4:
                    PrintSuccess.println("Exiting choice page");
                    return;
                default:
                    PrintError.println("Invalid choice. Please choose between 1, 2, 3 and 4.");
            }
        }
    }

    public static void sortStudentsByEmail(){
        boolean typeSort = BooleanValidator.validateBoolean("Enter type sort (true is asc / false is desc):");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                "ID", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        STUDENT_SERVICE.sortStudentByEmail(typeSort).forEach(student ->
                System.out.printf("| %-4d | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                        student.getId(),
                        student.getFullName(),
                        student.getDob(),
                        student.isSex() ? "Male" : "Female",
                        student.getPhone(),
                        student.getEmail(),
                        student.getCreatedAt()));
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }

    public static void sortStudentsByName(){
        boolean typeSort = BooleanValidator.validateBoolean("Enter type sort (true is asc / false is desc):");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                "ID", "Full Name", "DOB", "Sex", "Phone", "Email", "Created At");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        STUDENT_SERVICE.sortStudentByName(typeSort).forEach(student ->
                System.out.printf("| %-4d | %-20s | %-10s | %-6s | %-12s | %-25s | %-19s |%n",
                        student.getId(),
                        student.getFullName(),
                        student.getDob(),
                        student.isSex() ? "Male" : "Female",
                        student.getPhone(),
                        student.getEmail(),
                        student.getCreatedAt()));
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }

    public static void sortStudent() {
        System.out.println("+=============================================+");
        System.out.println("|               SORT STUDENT MENU             |");
        System.out.println("+====+========================================+");
        System.out.println("| 1. | Sort Students by Name                  |");
        System.out.println("| 2. | Sort Students by Email                 |");
        System.out.println("+====+========================================+");

        int choice = ChoiceValidator.validateChoice("Enter your choice: ", 2);
        System.out.println();
        switch (choice) {
            case 1:
                sortStudentsByName();
                System.out.println();
                return;
            case 2:
                sortStudentsByEmail();
                System.out.println();
                return;
            default:
                PrintError.println("Invalid choice. Please choose between 1 and 2.");
        }
    }

}
