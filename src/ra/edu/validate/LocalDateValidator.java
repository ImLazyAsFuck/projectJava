package ra.edu.validate;

import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateValidator{
    public static LocalDate validate(String message){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            try{
                System.out.print(message);
                String input = Input.input.nextLine().trim();
                if(input.isEmpty()){
                    throw new IllegalArgumentException("Date time can't be empty");
                }
                return LocalDate.parse(input, formatter);
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(DateTimeParseException e){
                PrintError.println("Invalid date format! Please enter: dd-MM-yyyy format");
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
