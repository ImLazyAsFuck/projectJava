package ra.edu.validate;

import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeValidator{
    public static LocalTime validate(String message){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss:mm:HH d-MM-yyyy");
        while(true){
            try{
                System.out.print(message);
                String input = Input.input.nextLine().trim();
                if(input.isEmpty()){
                    throw new IllegalArgumentException("Date can't be empty");
                }
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                return dateTime.toLocalTime();
            }catch(IllegalArgumentException e){
                PrintError.print("Invalid format. Please enter: ss:mm:hh d-MM-yyyy");
            }catch(Exception e){
                PrintError.print("Unknown exception! Please try again");
            }
        }
    }
}