package ra.edu.validate;

import ra.edu.business.model.LengthContain;
import ra.edu.business.service.studentService.StudentService;
import ra.edu.business.service.studentService.StudentServiceImp;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

public class StudentValidator{
    private final static StudentService STUDENT_SERVICE = new StudentServiceImp();
    public static String emailValidate(String message, String regex){
        while(true){
            try{
                System.out.print(message);
                String newEmail = Input.input.nextLine().trim();
                if(newEmail.isEmpty()){
                    throw new IllegalArgumentException("Email can't be empty");
                }
                if(!newEmail.matches(regex)){
                    throw new IllegalArgumentException("Invalid email! Please enter a email like a@domain.com");
                }
                if(STUDENT_SERVICE.isEmailExist(newEmail)){
                    throw new IllegalArgumentException("Email already exist");
                }

                return newEmail;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }

    public static String phoneValidate(String message, String regex){
        while(true){
            try{
                System.out.print(message);
                String newPhone = Input.input.nextLine().trim();
                if(newPhone.isEmpty()){
                    throw new IllegalArgumentException("Phone can't be empty");
                }
                if(!newPhone.matches(regex)){
                    throw new IllegalArgumentException("Invalid phone number! Please enter a phone number that start with 0 and 9 digits");
                }
                return newPhone;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }

    public static String studentNameValidate(String message, LengthContain lengthContain) {
        while (true) {
            try {
                System.out.print(message);
                String newString = Input.input.nextLine().trim().replaceAll("\\s+", " ");

                if (newString.isEmpty()) {
                    throw new IllegalArgumentException("Student name can't be empty");
                } else if (newString.length() < lengthContain.getMinLength()) {
                    throw new IllegalArgumentException("Student name must be at least " + lengthContain.getMinLength() + " characters");
                } else if (newString.length() > lengthContain.getMaxLength()) {
                    throw new IllegalArgumentException("Student name must be less than " + lengthContain.getMaxLength() + " characters");
                }

                return newString;
            } catch (IllegalArgumentException e) {
                PrintError.println(e.getMessage());
            } catch (Exception e) {
                PrintError.println("Unknown exception! Please try again.");
            }
        }
    }

    public static String passwordValidate(String message) {
        while(true){
            try{
                System.out.print(message);
                String vPassword = Input.input.nextLine().trim();
                if (vPassword.isEmpty()) {
                    throw new IllegalArgumentException("Password cannot be empty!");
                }
                if (vPassword.length() < 8) {
                    throw new IllegalArgumentException("Password must be at least 8 characters!");
                }
                if (!vPassword.matches(".*[@_-].*")) {
                    throw new IllegalArgumentException("Password must contain at least one of the following: '@', '-', '_'");
                }
                if (!vPassword.matches(".*[a-zA-Z].*")) {
                    throw new IllegalArgumentException("Password must contain at least one letter (a-z or A-Z)!");
                }
                if (!vPassword.matches(".*[0-9].*")) {
                    throw new IllegalArgumentException("Password must contain at least one number!");
                }
                return vPassword;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }
        }
    }
}
