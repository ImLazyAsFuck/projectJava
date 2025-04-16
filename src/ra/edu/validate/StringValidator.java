package ra.edu.validate;

import ra.edu.business.model.LengthContain;
import ra.edu.utils.Input;
import ra.edu.utils.PrintError;


public class StringValidator{
    public static String validate(String message, LengthContain lengthContain){
        while(true){
            try{
                System.out.print(message);
                String newString = input.nextLine().trim();
                if(newString.isEmpty()){
                    throw new IllegalArgumentException("String can't be empty");
                }else if(newString.length() < lengthContain.getMinLength()){
                    throw new IllegalArgumentException("String length must be at least " + lengthContain.getMinLength() + " characters");
                }else if(newString.length() > lengthContain.getMaxLength()){
                    throw new IllegalArgumentException("String length must be less than " + lengthContain.getMaxLength() + " characters");
                }
                return newString;
            }catch(Exception e){
                PrintError.println(e.getMessage());
            }
        }
    }
}
