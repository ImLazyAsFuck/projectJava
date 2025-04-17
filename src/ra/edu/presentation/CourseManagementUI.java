package ra.edu.presentation;

import ra.edu.business.model.LengthContain;
import ra.edu.business.model.course.Course;
import ra.edu.business.service.courseService.CourseService;
import ra.edu.business.service.courseService.CourseServiceImp;
import ra.edu.utils.PrintError;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;

public class CourseManagementUI{
    private final static CourseService COURSE_SERVICE = new CourseServiceImp();
    private final static int PAGE_SIZE = 10;
    private final static int FIRST_PAGE = 10;

    private static int currentPage = 1;
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
                    addNewCourse();
                    break;
                case 3:
                    updateCourse();
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
        if(COURSE_SERVICE.findPage(FIRST_PAGE, PAGE_SIZE).isEmpty()){
            System.out.println("Course list ís empty.");
            return;
        }
        COURSE_SERVICE.findAll().forEach(System.out::println);
    }

    public static void addNewCourse() {
        Course course = new Course();
        course.inputData();
        if(COURSE_SERVICE.save(course)){
            System.out.println("Course added.");
        }else{
            PrintError.println("Course cannot be added because course already exists.");
        };
    }

    public static void updateCourse() {
        Course course = new Course();
        course.inputData();
        if(COURSE_SERVICE.update(course)){
            System.out.println("Course updated.");
        }
    }
}
