package ra.edu.utils.Print;

import ra.edu.presentation.adminUI.AdminUI;
import ra.edu.validate.IntegerValidator;
import ra.edu.validate.StringValidator;
import ra.edu.business.model.LengthContain;

import java.util.ArrayList;
import java.util.List;

public class Navigation{
    public static boolean navigate(int currentPage, int totalPage){
        if(totalPage <= 1){
            return false;
        }
        StringBuilder options = new StringBuilder();
        List<String> validChoices = new ArrayList<>();

        if (currentPage > AdminUI.FIRST_PAGE) {
            options.append(String.format("%-20s", "P.Prev"));
            validChoices.add("1");
        }
        if (totalPage > 1) {
            options.append(String.format("%-20s", "C.Choose"));
            validChoices.add("2");
        }
        if (currentPage < totalPage) {
            options.append(String.format("%-20s", "N.Next"));
            validChoices.add("3");
        }
        options.append(String.format("%-20s", "E.Exit"));
        validChoices.add("4");

        System.out.println(options.toString());

        String subChoice = StringValidator.validate("Enter choice: ", new LengthContain(0, 1));
        switch (subChoice.toLowerCase()) {
            case "p":
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.println();
                    PrintError.println("You are already on the first page.");
                    System.out.println();
                }
                break;
            case "c":
                System.out.println();
                int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(0, 1000));
                if (pageChoice >= 1 && pageChoice <= totalPage) {
                    currentPage = pageChoice;
                } else {
                    System.out.println();
                    PrintError.println("Invalid page number.");
                    System.out.println();
                }
                break;
            case "n":
                if (currentPage < totalPage) {
                    currentPage++;
                } else {
                    PrintError.println("You are already on the last page.");
                    System.out.println();
                }
                break;
            case "e":
                PrintSuccess.println("Exiting choice page");
                System.out.println();
                return true;
            default:
                PrintError.println("Invalid choice. Please choose between 1, 2, 3 and 4.");
                System.out.println();
        }
        return false;
    }
}
