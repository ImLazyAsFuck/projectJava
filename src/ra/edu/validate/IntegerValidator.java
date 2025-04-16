package ra.edu.validate;



import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

import java.util.Scanner;

public class IntegerValidator{
    public static int validate(String message){
        while(true){
            try{
                System.out.print(message);
                int integer = Integer.parseInt(Input.input.nextLine());
            }catch(NumberFormatException e){
                PrintError.println("Please enter an number");
            }catch(IllegalArgumentException e){

            }
        }
    }
}
