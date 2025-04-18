package ra.edu.validate;

import ra.edu.business.model.Account.AccountStatus;
import ra.edu.utils.Input;
import ra.edu.utils.PrintError;

public class AccountValidator{
    public static AccountStatus validateStudentStatus(String message){
        while(true){
            try{
                System.out.print(message);
                String newStatus = Input.input.nextLine().trim();
                if(!newStatus.isEmpty()){
                    throw new IllegalArgumentException("Status can't be empty");
                }
                return switch(newStatus.toLowerCase()){
                    case "active" -> AccountStatus.ACTIVE;
                    case "inactive" -> AccountStatus.INACTIVE;
                    case "blocked" -> AccountStatus.BLOCKED;
                    default ->
                            throw new IllegalArgumentException("Invalid status! Please enter ACTIVE or INACTIVE or BLOCKED");
                };
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
