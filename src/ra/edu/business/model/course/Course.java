package ra.edu.business.model.course;

import ra.edu.business.model.Inputable;
import ra.edu.business.model.LengthContain;
import ra.edu.business.service.courseService.CourseService;
import ra.edu.presentation.adminUI.CourseManagementUI;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.validate.BooleanValidator;
import ra.edu.validate.CourseValidator;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Course implements Inputable{
    private int id;
    private String name;
    private int duration;
    private CourseStatus status;
    private String description;
    private String instructor;
    private LocalDateTime createdAt;

    public Course(int id, String name, int duration, CourseStatus status, String description, String instructor, LocalDateTime createdAt){
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.status = status;
        this.description = description;
        this.instructor = instructor;
        this.createdAt = createdAt;
    }

    public Course(){
    }

    public CourseStatus getStatus(){
        return status;
    }

    public void setStatus(CourseStatus status){
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getInstructor(){
        return instructor;
    }

    public void setInstructor(String instructor){
        this.instructor = instructor;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public void inputData(){
        this.name = inputCourseName();
        int nameCheckResult = CourseManagementUI.COURSE_SERVICE.isNameUnique(name);
        if (nameCheckResult == 2) {
            handleDeletedCourseReactivation();
            return;
        }
        this.duration = inputCourseDuration();
        this.description = inputCourseDescription();
        this.instructor = inputCourseInstructor();
        this.createdAt = LocalDateTime.now();
    }

    private void handleDeletedCourseReactivation() {
        System.out.println("Course is already existed but got deleted. Do you want to active it? (true/false)");
        boolean confirm = BooleanValidator.validateBoolean("confirm (true/false): ");

        if (confirm) {
            boolean isActivated = CourseManagementUI.COURSE_SERVICE.activeCourse(this.name);
            if (isActivated) {
                PrintSuccess.println("Course: " + this.name + " has been activated");
            } else {
                PrintError.println("Can't activate course: " + this.name);
            }
        }
    }


    public String inputCourseName(){
        return StringValidator.validate("Enter the course name: ", new LengthContain(0, 100));
    }

    public String inputCourseDescription(){
        System.out.print("Enter the course description: ");
        return Input.input.nextLine();
    }

    public String inputCourseInstructor(){
        return StringValidator.validate("Enter the instructor: ", new LengthContain(0, 100));
    }

    public int inputCourseDuration(){
        return IntegerValidator.validate("Enter the course duration: ", new LengthContain(0, 255));
    }

//    public CourseStatus inputCourseStatus(){
//        return CourseValidator.validate();
//    }

    @Override
    public String toString() {
        return String.format(
                "|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
                id, name, duration, description, instructor, createdAt.toString()
        );
    }
}
