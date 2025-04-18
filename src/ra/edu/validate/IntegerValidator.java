package ra.edu.validate;

import ra.edu.business.model.LengthContain;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;


public class IntegerValidator{
    public static int validate(String message, LengthContain length){
        while(true){
            try{
                System.out.print(message);
                int integer = Integer.parseInt(Input.input.nextLine());
                if(integer < length.getMinLength()){
                    throw new IllegalArgumentException("Integer can't be smaller than " + length.getMinLength());
                }
                if(integer > length.getMaxLength()){
                    throw new IllegalArgumentException("Integer can't be greater than " + length.getMinLength());
                }
                return integer;
            }catch(NumberFormatException e){
                PrintError.println("Please enter an number");
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
