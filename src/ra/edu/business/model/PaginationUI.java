package ra.edu.business.model;

import ra.edu.utils.Print.PrintError;
import ra.edu.utils.Print.PrintSuccess;
import ra.edu.validate.ChoiceValidator;
import ra.edu.validate.IntegerValidator;

import java.util.List;
import java.util.function.Consumer;

public class PaginationUI {

    public static <T> void printPaginatedResult(
            PaginationService<T> service,
            int pageSize,
            String[] headers,
            String format,
            Consumer<T> printer
    ) {
        int currentPage = 1;
        Pagination<T> firstPage = service.getPage(currentPage, pageSize);
        if (firstPage.getItems().isEmpty()) {
            PrintError.println("No data found.");
            return;
        }

        int totalPage = firstPage.getTotalPages();

        while (true) {
            Pagination<T> page = service.getPage(currentPage, pageSize);
            System.out.println("=".repeat(200));
            System.out.printf(format, (Object[]) headers);
            System.out.println("-".repeat(200));

            page.getItems().forEach(printer);

            System.out.printf("Page %d/%d%n", currentPage, totalPage);
            printPagination(currentPage, totalPage);
            System.out.printf("%-20s%-20s%-20s%-20s\n", "1.Prev", "2.Choose", "3.Next", "4.Exit");

            int subChoice = ChoiceValidator.validateChoice("Enter choice: ", 4);
            switch (subChoice) {
                case 1:
                    if (currentPage > 1) currentPage--;
                    else PrintError.println("You are already on the first page.");
                    break;
                case 2:
                    int pageChoice = IntegerValidator.validate("Enter your page: ", new LengthContain(1, 1000));
                    if (pageChoice >= 1 && pageChoice <= totalPage) currentPage = pageChoice;
                    else PrintError.println("Invalid page number.");
                    break;
                case 3:
                    if (currentPage < totalPage) currentPage++;
                    else PrintError.println("You are already on the last page.");
                    break;
                case 4:
                    PrintSuccess.println("Exiting page viewer...");
                    return;
                default:
                    PrintError.println("Invalid choice.");
            }
        }
    }

    private static void printPagination(int currentPage, int totalPage) {
        System.out.print("Pages: ");
        for (int i = 1; i <= totalPage; i++) {
            if (i == currentPage) System.out.print("[" + i + "] ");
            else System.out.print(i + " ");
        }
        System.out.println();
    }

    public interface PaginationService<T> {
        Pagination<T> getPage(int pageNumber, int pageSize);
    }
}
