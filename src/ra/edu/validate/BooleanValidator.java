package ra.edu.validate;

import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

public class BooleanValidator{
    public static boolean validateBoolean(String message){
        while(true){
            try{
                System.out.print(message);
                String bool = Input.input.nextLine().trim();
                if (bool.isEmpty()) {
                    throw new IllegalArgumentException("Boolean cannot be empty!");
                }
                if(!bool.equalsIgnoreCase("true") && !bool.equalsIgnoreCase("false")){
                    throw new IllegalArgumentException("Boolean must be either true or false");
                }
                return Boolean.parseBoolean(bool);
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }
        }
    }
}
