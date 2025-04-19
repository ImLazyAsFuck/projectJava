package ra.edu.business.model.student;

import ra.edu.business.model.Inputable;

import java.time.LocalDate;

public class Student implements Inputable{
    private String fullName;
    private LocalDate dob;
    private boolean sex;
    private String phone;
    private String email;

    public Student(String fullName, LocalDate dob, boolean sex, String phone, String email){
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
    }

    public Student(){
    }

    @Override
    public void inputData(){

    }

    @Override
    public String toString(){
        return "";
    }
}
