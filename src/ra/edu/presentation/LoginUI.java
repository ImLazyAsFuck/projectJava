package ra.edu.presentation;

import ra.edu.business.model.LengthContain;
import ra.edu.business.model.User.Admin;
import ra.edu.business.model.User.Student;
import ra.edu.business.service.authService.AuthService;
import ra.edu.business.service.authService.AuthServiceImp;
import ra.edu.utils.Input;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.StringValidator;

import static ra.edu.presentation.AdminUI.showAdminMenu;

public class LoginUI{
    private final static AuthService AUTH_SERVICE = new AuthServiceImp();

    public static void displayLoginMenu(){
        int choice;
        do {
            System.out.println("========= TRAINING MANAGEMENT SYSTEM =========");
            System.out.println("1. Login as Administrator");
            System.out.println("2. Login as Student");
            System.out.println("3. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");
            choice = ChoiceValidator.validateChoice("Enter choice: ", 3);
            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    loginAsStudent();
                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 3);
    }

    public static void loginAsAdmin(){
        String username = StringValidator.validate("Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));
        Admin admin = null;
        admin = AUTH_SERVICE.loginAsAdmin(username, password);
        if (admin != null){
            Admin.currentUser = admin;
            showAdminMenu();
        }else{
            System.out.println("Username or password is incorrect!");
        }
    }

    public static void loginAsStudent(){
        String username = StringValidator.validate("Enter username: ", new LengthContain(0, 255));
        String password = StringValidator.validate("Enter password: ", new LengthContain(0, 255));
        Student student = null;
        student = AUTH_SERVICE.loginAsStudent(username, password);
        if (student != null){
            Student.currentUser = student;
//            showAdminMenu();
        }else{
            System.out.println("Username or password is incorrect!");
        }
    }
}
