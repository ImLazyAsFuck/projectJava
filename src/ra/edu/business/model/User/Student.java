package ra.edu.business.model.User;

import ra.edu.business.model.Inputable;

import java.time.LocalDate;

public class Student extends User implements Inputable{
    private String fullName;
    private String email;
    private LocalDate dob;
    private boolean sex;
    private String phone;
    private StudentStatus status;
    private LocalDate createdAt;

    public Student(String fullName, String email, LocalDate dob, boolean sex, String phone, StudentStatus status, LocalDate createdAt){
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Student(){
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
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

    public StudentStatus getStatus(){
        return status;
    }

    public void setStatus(StudentStatus status){
        this.status = status;
    }

    public LocalDate getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public void inputData(){

    }



    public void changePassword(String newPassword){
        super.setPassword(newPassword);
    }

    @Override
    public String toString(){
        return "";
    }
}
