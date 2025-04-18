package ra.edu.validate;

import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

public class ChoiceValidator{
    public static int validateChoice(String message, int maxChoice) {
        while(true){
            try{
                System.out.print(message);
                int choice = Integer.parseInt(Input.input.nextLine());
                if(choice > maxChoice || choice <= 0){
                    throw new Exception();
                }
                return choice;
            }catch(Exception e){
                PrintError.println("Please enter choice between 1 and " + maxChoice + ".");
            }
        }
    }
}
