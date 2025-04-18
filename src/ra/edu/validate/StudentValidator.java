package ra.edu.validate;

import ra.edu.business.model.LengthContain;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

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

    public static String studentNameValidate(String message, LengthContain lengthContain){
        while(true){
            try{
                System.out.print(message);
                String newString = Input.input.nextLine().trim().replace("\\s+", " ");
                if(newString.isEmpty()){
                    throw new IllegalArgumentException("Course name can't be empty");
                }else if(newString.length() < lengthContain.getMinLength()){
                    throw new IllegalArgumentException("Course name length must be at least " + lengthContain.getMinLength() + " characters");
                }else if(newString.length() > lengthContain.getMaxLength()){
                    throw new IllegalArgumentException("Course name length must be less than " + lengthContain.getMaxLength() + " characters");
                }
                return newString;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
