package ra.edu.validate;

import ra.edu.business.model.enrollment.EnrollmentStatus;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

public class EnrollmentValidator{
    public static EnrollmentStatus enrollmentStatusValidate(String message){
        while(true){
            try{
                System.out.print(message);
                String newStatus = Input.input.nextLine().trim();
                if (newStatus.isEmpty()) {
                    throw new IllegalArgumentException("Status cannot be empty!");
                }
                switch (newStatus.toLowerCase()){
                    case "waiting":
                        return EnrollmentStatus.WAITING;
                    case "denied":
                        return EnrollmentStatus.DENIED;
                    case "cancelled":
                        return EnrollmentStatus.CANCELLED;
                    case "confirmed":
                        return EnrollmentStatus.CONFIRMED;
                    default:
                        throw new IllegalArgumentException("Unknown status! Please enter WAITING or DENIED or CANCELLED or CONFIRMED");
                }
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
