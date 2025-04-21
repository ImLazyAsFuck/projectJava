package ra.edu.business.model.course;

import ra.edu.business.model.Inputable;
import ra.edu.business.model.LengthContain;
import ra.edu.utils.Input;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Course implements Inputable{
    private int id;
    private String name;
    private int duration;
//    private
    private String description;
    private String instructor;
    private LocalDateTime createdAt;

    public Course(int id, String name, int duration, String description, String instructor, LocalDateTime createdAt){
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.instructor = instructor;
        this.createdAt = createdAt;
    }

    public Course(){
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
        this.duration = inputCourseDuration();
        this.description = inputCourseDescription();
        this.instructor = inputCourseInstructor();
        this.createdAt = LocalDateTime.now();
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

    @Override
    public String toString() {
        return String.format(
                "|%-10s | %-40s | %-20s | %-70s | %-20s | %-20s|%n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
                id, name, duration, description, instructor, createdAt.toString()
        );
    }
}
