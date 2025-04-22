package ra.edu.presentation.adminUI;

import ra.edu.business.model.LengthContain;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.service.courseService.CourseService;
import ra.edu.business.service.courseService.CourseServiceImp;
import ra.edu.business.service.studentService.StudentService;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.*;

import static ra.edu.presentation.adminUI.AdminUI.FIRST_PAGE;
import static ra.edu.presentation.adminUI.AdminUI.PAGE_SIZE;

public class CourseManagementUI{
    public final static CourseService COURSE_SERVICE = new CourseServiceImp();

    private static int currentPage = FIRST_PAGE;
    public static void showCourseMenu() {
        int choice;
        do {
            System.out.println("+=============================================+");
            System.out.println("|         COURSE MANAGEMENT SYSTEM            |");
            System.out.println("+====+========================================+");
            System.out.println("| 1 | Show all courses                        |");
            System.out.println("| 2 | Add new course                          |");
            System.out.println("| 3 | Update course (choose attribute to edit)|");
            System.out.println("| 4 | Delete course (with confirmation)       |");
            System.out.println("| 5 | Search by name (have pagination)        |");
            System.out.println("| 6 | Sort by name (ascending/descending)     |");
            System.out.println("| 7 | Return to management menu               |");
            System.out.println("+====+========================================+");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 7);

            System.out.println();
            switch (choice) {
                case 1:
                    displayCourse();
                    break;
                case 2:
                    addNewCourse();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    findbyName();
                    break;
                case 6:
                    sortCourseByName();
                    break;
                case 7:
                    PrintColor.printCyan("Return to main menu.");
                    return;
                default:
                    PrintError.println("Invalid choice! Please choose between 1 and 7.");
            }
        } while (true);
    }

    public static void findbyName() {
        if(isEmptyOrPrintCourses()){
            return;
        }
        String searchName = StringValidator.validate("Enter search name: ", new LengthContain(0, 100));

        currentPage = FIRST_PAGE;
        Pagination<Course> firstPage = COURSE_SERVICE.searchByName(searchName, FIRST_PAGE, PAGE_SIZE);

        if (firstPage.getItems().isEmpty()) {
            PrintError.println("No courses found for name: " + searchName);
            return;
        }

        int totalPage = firstPage.getTotalPages();

        while (true) {
            Pagination<Course> coursePage = COURSE_SERVICE.searchByName(searchName, currentPage, PAGE_SIZE);
            System.out.println("=".repeat(200));
            System.out.printf("|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n",
                    "Course Id", "Course Name", "Duration", "Description", "Instructor", "Created At");
            System.out.println("-".repeat(200));

            coursePage.getItems().forEach(course ->
                    System.out.printf("|%-10d | %-40s | %-20d | %-70s | %-20s | %-20s|%n",
                            course.getId(), course.getName(), course.getDuration(),
                            course.getDescription(), course.getInstructor(),
                            course.getCreatedAt() != null ? course.getCreatedAt().toString() : "N/A")
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

    public static void deleteCourse() {
        if(isEmptyOrPrintCourses()){
            return;
        }
        int id = IntegerValidator.validate("Enter Id: ", new LengthContain(0, 255));
        if(COURSE_SERVICE.findbyId(id) == null){
            PrintError.println("Course not found!");
            return;
        }
        boolean confirmation = BooleanValidator.validateBoolean("Do you sure to delete this course?(true/false): ");
        if (!confirmation) {
            PrintSuccess.println("Course deletion canceled.");
            return;
        }
        if(COURSE_SERVICE.delete(id)) {
            PrintSuccess.println("Course deleted successfully.");
        }else{
            PrintError.println("Course could not be deleted.");
        }
    }

    public static boolean isEmptyOrPrintCourses() {
        if (COURSE_SERVICE.findAll().isEmpty()) {
            PrintError.println("Course list is empty.");
            return true;
        }
        System.out.println("=".repeat(200));
        System.out.printf("|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n",
                "Course Id", "Course Name", "Duration", "Description", "Instructor", "Created At");
        System.out.println("-".repeat(200));
        COURSE_SERVICE.findAll().forEach(course ->
                System.out.printf("|%-10d | %-40s | %-20d | %-70s | %-20s | %-20s|%n",
                        course.getId(), course.getName(), course.getDuration(),
                        course.getDescription(), course.getInstructor(),
                        course.getCreatedAt() != null ? course.getCreatedAt().toString() : "N/A")
        );
        return false;
    }

    public static void findbyid(){
        if(isEmptyOrPrintCourses()){
            return;
        };
        int id = IntegerValidator.validate("Enter Id: ", new LengthContain(0, 255));
        if(COURSE_SERVICE.findbyId(id) != null) {
            PrintSuccess.println("Course found.");System.out.println("-------------------------------------------------------------------------");
            System.out.printf("|%-10s | %-40s |%n",
                    "Course Id", "Course Name");
            System.out.println("-------------------------------------------------------------------------");
            Course foundCourse = COURSE_SERVICE.findbyId(id);
            System.out.printf("|%-10d | %-40s|", foundCourse.getId(), foundCourse.getName());
        }else{
            PrintError.println("Not found course with id: ." + id);
        };
    }

    public static void displayCourse() {
        Pagination<Course> firstPage = COURSE_SERVICE.findPage(FIRST_PAGE, PAGE_SIZE);
        if (firstPage.getItems().isEmpty()) {
            System.out.println();
            PrintError.println("Course list is empty.");
            System.out.println();
            return;
        }
        int totalPage = firstPage.getTotalPages();
        currentPage = FIRST_PAGE;
        while(true){
            Pagination<Course> coursePage = COURSE_SERVICE.findPage(currentPage, PAGE_SIZE);
            System.out.println("=".repeat(200));
            System.out.printf("|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n",
                    "Course Id", "Course Name", "Duration", "Description", "Instructor", "Created At");
            System.out.println("-".repeat(200));

            coursePage.getItems().forEach(course ->
                    System.out.printf("|%-10d | %-40s | %-20d | %-70s | %-20s | %-20s|%n",
                            course.getId(), course.getName(), course.getDuration(),
                            course.getDescription(), course.getInstructor(),
                            course.getCreatedAt() != null ? course.getCreatedAt().toString() : "N/A")
            );
            System.out.printf("Page %d/%d%n", currentPage, totalPage);
            printPagination(currentPage, totalPage);

            System.out.printf("%-20s%-20s%-20s%-20s\n", "1.Prev", "2.Choose", "3.Next", "4.Exit");

            int subChoice = ChoiceValidator.validateChoice("Enter choice: ", 4);
            switch (subChoice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println();
                        PrintError.println("You are already on the first page.");
                        System.out.println();
                    }
                    break;
                case 2:
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
                case 3:
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
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

    public static void printPagination(int currentPage, int totalPage) {
        final int visibleRange = 1;
        final String CYAN = "\u001B[36m";
        final String RESET = "\u001B[0m";

        if (currentPage > 1) {
            System.out.print("<< Prev | ");
        }

        boolean dotsBefore = false;
        boolean dotsAfter = false;

        for (int i = 1; i <= totalPage; i++) {
            if (i == 1 || i == totalPage || (i >= currentPage - visibleRange && i <= currentPage + visibleRange)) {
                if (i == currentPage) {
                    System.out.print(CYAN + "[" + i + "]" + RESET + " ");
                } else {
                    System.out.print(i + " ");
                }
            } else if (i < currentPage - visibleRange && !dotsBefore) {
                System.out.print("... ");
                dotsBefore = true;
            } else if (i > currentPage + visibleRange && !dotsAfter) {
                System.out.print("... ");
                dotsAfter = true;
            }
        }

        if (currentPage < totalPage) {
            System.out.println("| Next >>");
        } else {
            System.out.println();
        }
    }

    public static void addNewCourse() {
        Course course = new Course();
        course.inputData();
        if(COURSE_SERVICE.save(course)){
            System.out.println("Course added.");
            System.out.println();
        }else{
            PrintError.println("Course cannot be added because unknown error.");
        };
    }

    public static void updateCourse() {
        if(isEmptyOrPrintCourses()){
            return;
        };
        int id = IntegerValidator.validate("Enter id: ", new LengthContain(0, 1000));
        if(COURSE_SERVICE.findbyId(id) == null){
            PrintError.println("Course not found.");
            return;
        }
        Course course = COURSE_SERVICE.findbyId(id);
        int choice;
        while(true){
            System.out.println("============== Update Course Menu ==============");
            System.out.println("| 1. | Update name                              |");
            System.out.println("| 2. | Update description                       |");
            System.out.println("| 3. | Update duration                          |");
            System.out.println("| 4. | Update instructor                        |");
            System.out.println("| 5. | Exit                                     |");
            System.out.println("===============================================");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 5);

            System.out.println();
            switch(choice){
                case 1:
                    course.setName(course.inputCourseName());
                    PrintSuccess.println("Course name updated.");
                    System.out.println();
                    break;
                case 2:
                    course.setDuration(course.inputCourseDuration());
                    PrintSuccess.println("Course duration updated.");
                    System.out.println();
                    break;
                case 3:
                    course.setDescription(course.inputCourseDescription());
                    PrintSuccess.println("Course description updated");
                    System.out.println();
                    break;
                case 4:
                    course.setInstructor(course.inputCourseInstructor());
                    PrintSuccess.println("Course instructor updated.");
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    COURSE_SERVICE.update(course);
                    System.out.println();
                    return;
                default:
                    PrintError.println("Invalid choice! please choose between 1 and 5.");
            }
        }
    }

    public static void sortCourseByName(){
        if(COURSE_SERVICE.findAll().isEmpty()){
            PrintError.println("Course list is empty.");
            return;
        }
        boolean typeSort = BooleanValidator.validateBoolean("Enter sort type(true is asc or false is desc): ");
        System.out.println("=".repeat(200));
        System.out.printf("|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n",
                "Course Id", "Course Name", "Duration", "Description", "Instructor", "Created At");
        System.out.println("-".repeat(200));
        COURSE_SERVICE.sortCourseByName(typeSort).forEach(course ->
                System.out.printf("|%-10d | %-40s | %-20d | %-70s | %-20s | %-20s|%n",
                        course.getId(), course.getName(), course.getDuration(),
                        course.getDescription(), course.getInstructor(),
                        course.getCreatedAt() != null ? course.getCreatedAt().toString() : "N/A")
        );
        System.out.println();
    }


}
