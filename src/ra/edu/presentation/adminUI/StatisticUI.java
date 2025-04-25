package ra.edu.presentation.adminUI;

import ra.edu.business.model.LengthContain;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.service.statisticService.StatisticService;
import ra.edu.business.service.statisticService.StatisticServiceImp;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.utils.Print.printColor.PrintColor;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ra.edu.presentation.adminUI.AdminUI.PAGE_SIZE;

public class StatisticUI{
    public final static StatisticService STATISTIC_SERVICE = new StatisticServiceImp();
    public static void displayStatisticMenu(){
        int choice;
        while(true){
            PrintColor.printlnYellow("+========================================================+");
            PrintColor.printlnYellow("|                  STATISTICS MANAGEMENT MENU            |");
            PrintColor.printlnYellow("+========================================================+");
            PrintColor.printlnYellow("| 1. | Display total number of courses and students      |");
            PrintColor.printlnYellow("| 2. | Display number of students in each course         |");
            PrintColor.printlnYellow("| 3. | Show top 5 courses with the most students         |");
            PrintColor.printlnYellow("| 4. | List all courses with more than 10 students       |");
            PrintColor.printlnRed("| 5. | Return to main menu                               |");
            PrintColor.printlnYellow("+========================================================+");

            choice = ChoiceValidator.validateChoice("Enter choice: ", 5);
            System.out.println();
            switch (choice){
                case 1:
                    displayTotalCourseAndStudent();
                    break;
                case 2:
                    displayStudentCountEachCourse();
                    break;
                case 3:
                    displayTopFiveCourseWithMostStudents();
                    break;
                case 4:
                    displayCourseHaveMore10Student();
                    break;
                case 5:
                    PrintColor.printlnCyan("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void displayTotalCourseAndStudent(){
        int choice;
        while(true){
            PrintColor.printlnBlue("+=============================================+");
            PrintColor.printlnBlue("|     COURSE & STUDENT STATISTICS MENU        |");
            PrintColor.printlnBlue("+=============================================+");
            PrintColor.printlnBlue("| 1. | Display total number of courses         |");
            PrintColor.printlnBlue("| 2. | Display total number of students        |");
            PrintColor.printlnRed("| 3. | Return to main menu                     |");
            PrintColor.printlnBlue("+=============================================+");

            choice = ChoiceValidator.validateChoice("Enter choice(1-3): ", 3);
            System.out.println();
            switch (choice){
                case 1:
                    displayTotalCourse();
                    System.out.println();
                    break;
                case 2:
                    displayTotalStudent();
                    System.out.println();
                    break;
                case 3:
                    PrintColor.printlnCyan("Returning to main menu...");
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid choice! Please choose from 1 to 3!");
                    break;
            }
        }
    }

    public static void displayTotalCourse(){
        int courseCount = STATISTIC_SERVICE.findTotalCourse();

        String horizontalBorder = "+----------------------+------------+";
        if (courseCount == 0) {
            System.out.println(horizontalBorder);
            PrintError.println("| There are no course in the system! |");
            System.out.println(horizontalBorder);
            System.out.println();
        } else {
            System.out.println(horizontalBorder);
            System.out.printf("| %-20s | %-10s |\n", "Statistic", "Value");
            System.out.println("+----------------------+------------+");
            System.out.printf("| %-20s | %-10d |\n", "Total Course", courseCount);
            System.out.println(horizontalBorder);
            System.out.println();
        }
    }

    public static void displayTotalStudent() {
        int studentCount = STATISTIC_SERVICE.findTotalStudent();

        String horizontalBorder = "+----------------------+------------+";
        if (studentCount == 0) {
            System.out.println(horizontalBorder);
            PrintError.println("| There are no students in the system! |");
            System.out.println(horizontalBorder);
            System.out.println();
        } else {
            System.out.println(horizontalBorder);
            System.out.printf("| %-20s | %-10s |\n", "Statistic", "Value");
            System.out.println("+----------------------+------------+");
            System.out.printf("| %-20s | %-10d |\n", "Total Students", studentCount);
            System.out.println(horizontalBorder);
            System.out.println();
        }
    }

    public static void displayCourseHaveMore10Student(){
        Pagination<Map<String, Integer>> firstPage = STATISTIC_SERVICE.findCoursesHaveMore10Student(AdminUI.FIRST_PAGE, PAGE_SIZE);
        if(firstPage.getItems().isEmpty()){
            PrintError.println("There are no courses with more than 10 students!");
            System.out.println();
        }
        int currentPage = AdminUI.FIRST_PAGE;
        int totalPage = firstPage.getTotalPages();
        while(true){
            Pagination<Map<String, Integer>> curPage = STATISTIC_SERVICE.findCoursesHaveMore10Student(currentPage, PAGE_SIZE);
            System.out.println("=".repeat(150));
            System.out.println("| Course Name                                           | Student Count |");
            System.out.println("-".repeat(150));
            curPage.getItems().forEach(entry -> {
                entry.forEach((courseName, studentCount) -> {
                    System.out.printf("| %-55s | %-13d |\n", courseName, studentCount);
                });
            });
            System.out.println("-".repeat(150));
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
                    PrintError.println("Invalid choice. Please choose P, C, N and E.");
                    System.out.println();
            }
        }
    }

    public static void displayStudentCountEachCourse(){
        Pagination<Map<String, Integer>> firstPage = STATISTIC_SERVICE.findStudentCountEachCourse(AdminUI.FIRST_PAGE, AdminUI.PAGE_SIZE);
        if(firstPage.getItems().isEmpty()){
            PrintError.println("There are no students in any course!");
            System.out.println();
        }
        int currentPage = AdminUI.FIRST_PAGE;
        int totalPage = firstPage.getTotalPages();
        while(true){
            Pagination<Map<String, Integer>> curPage = STATISTIC_SERVICE.findStudentCountEachCourse(currentPage, AdminUI.PAGE_SIZE);
            System.out.println("=".repeat(150));
            System.out.println("| Course Name                                           | Student Count |");
            System.out.println("-".repeat(150));
            curPage.getItems().forEach(entry -> {
                entry.forEach((courseName, studentCount) -> {
                    System.out.printf("| %-55s | %-13d |\n", courseName, studentCount);
                });
            });
            System.out.println("-".repeat(150));
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
                    PrintError.println("Invalid choice. Please choose P, C, N and E.");
                    System.out.println();
            }
        }
    }

    public static void displayTopFiveCourseWithMostStudents(){
        if(STATISTIC_SERVICE.findTop5CoursesByStudentCount().isEmpty()){
            PrintError.println("There are no courses with more than 10 students!");
            System.out.println();
        }
        System.out.println("=".repeat(77));
        System.out.println("| Course Name                                             | Student Count |");
        System.out.println("-".repeat(77));
        STATISTIC_SERVICE.findTop5CoursesByStudentCount().forEach(entry ->
                entry.forEach((courseName, studentCount) -> {
                    System.out.printf("| %-55s | %-13d |\n", courseName, studentCount);
                }));
        System.out.println("-".repeat(77));
    }
}
