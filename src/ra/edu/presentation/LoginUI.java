package ra.edu.presentation;

import ra.edu.validate.ChoiceValidator;

import static ra.edu.presentation.AdminUI.showAdminMenu;

public class LoginUI{


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
                    showAdminMenu();
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 3);
    }
}
