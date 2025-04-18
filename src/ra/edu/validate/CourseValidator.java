package ra.edu.validate;

import ra.edu.business.model.LengthContain;
import ra.edu.business.service.courseService.CourseService;
import ra.edu.business.service.courseService.CourseServiceImp;
import ra.edu.utils.Input;
import ra.edu.utils.Print.PrintError;

public class CourseValidator{
    public static String validate(String message, LengthContain lengthContain){
        CourseService courseService = new CourseServiceImp();
        while(true){
            try{
                System.out.print(message);
                String newString = Input.input.nextLine().trim().replace("\\s+", " ");
                if(newString.isEmpty()){
                    throw new IllegalArgumentException("String can't be empty");
                }else if(newString.length() < lengthContain.getMinLength()){
                    throw new IllegalArgumentException("String length must be at least " + lengthContain.getMinLength() + " characters");
                }else if(newString.length() > lengthContain.getMaxLength()){
                    throw new IllegalArgumentException("String length must be less than " + lengthContain.getMaxLength() + " characters");
                }else if(courseService.isNameUnique(newString)){
                    throw new IllegalArgumentException("Course name is already in use");
                }
                return newString;
            }catch(IllegalArgumentException e){
                PrintError.println(e.getMessage());
            }catch(Exception e){
                PrintError.println("Unknown exception! Please try again");
            }
        }
    }
}
