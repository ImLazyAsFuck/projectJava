package ra.edu.validate;

import ra.edu.business.model.student.StudentStatus;
import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

public class StudentValidator{
    public static String emailValidate(String message, String regex){
        while(true){
            try{
                System.out.print(message);
                String newEmail = Input.input.nextLine().trim();
                if(!newEmail.isEmpty()){
                    throw new IllegalArgumentException("Email can't be empty");
                }
                if(!newEmail.matches(regex)){
                    throw new IllegalArgumentException("Invalid email! Please enter a email like a@domain.com");
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
                if(!newPhone.isEmpty()){
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

    public static StudentStatus validateStudentStatus(String message){
        while(true){
            try{
                System.out.print(message);
                String newStatus = Input.input.nextLine().trim();
                if(!newStatus.isEmpty()){
                    throw new IllegalArgumentException("Status can't be empty");
                }
                switch(newStatus.toLowerCase()){
                    case "active":
                        return StudentStatus.ACTIVE;
                    case "inactive":
                        return StudentStatus.INACTIVE;
                    case "blocked":
                        return StudentStatus.BLOCKED;
                    default:
                        throw new IllegalArgumentException("Invalid status! Please enter ACTIVE or INACTIVE or BLOCKED");
                }
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
