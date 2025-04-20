package ra.edu.business.model.student;

import ra.edu.business.model.Inputable;
import ra.edu.business.model.LengthContain;
import ra.edu.validate.BooleanValidator;
import ra.edu.validate.LocalDateValidator;
import ra.edu.validate.StringValidator;
import ra.edu.validate.StudentValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student implements Inputable{
    private int id;
    private String fullName;
    private LocalDate dob;
    private boolean sex;
    private String phone;
    private String email;
    private LocalDateTime createdAt;

    public Student(int id, String fullName, LocalDate dob, boolean sex, String phone, String email, LocalDateTime createdAt){
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Student(){
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public LocalDate getDob(){
        return dob;
    }

    public void setDob(LocalDate dob){
        this.dob = dob;
    }

    public boolean isSex(){
        return sex;
    }

    public void setSex(boolean sex){
        this.sex = sex;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public void inputData(){
        this.fullName = inputFullName();
        this.email = inputEmail();
        this.phone = inputPhone();
        this.sex = inputSex();
        this.dob = inputDob();
    }

    public String inputFullName(){
        return StudentValidator.studentNameValidate("Enter student name: ", new LengthContain(0, 100));
    }

    public LocalDate inputDob(){
        return LocalDateValidator.validate("Enter date of birth (dd/MM/yyyy): ");
    }

    public boolean inputSex(){
        return BooleanValidator.validateBoolean("Enter sex (true for male/false for female): ");
    }

    public String inputPhone(){
        return StudentValidator.phoneValidate("Enter phone number : ", "^0(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$");
    }

    public String inputEmail(){
        return StudentValidator.emailValidate("Enter email (e.g., example@gmail.com): ", "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Override
    public String toString(){
        return "";
    }
}
