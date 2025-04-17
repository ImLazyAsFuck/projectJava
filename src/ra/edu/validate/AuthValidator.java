package ra.edu.validate;

import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

public class AuthValidator{
    public static String validatePassword(String message, String oldPassword){
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
                if(oldPassword.equals(vPassword)){
                    throw new IllegalArgumentException("new password cannot be the same as the old password!");
                }
                return vPassword;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }

    public static String confirmPassword(String message, String password){
        while(true){
            try{
                System.out.print(message);
                String cPassword = Input.input.nextLine();
                if (cPassword.isEmpty()) {
                    throw new IllegalArgumentException("Confirm password cannot be empty!");
                }
                if(!cPassword.equals(password)){
                    throw new IllegalArgumentException("Confirm password do not match!");
                }
                return cPassword;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
