package ra.edu.business.model;

import ra.edu.utils.Print.PrintError;

import java.util.List;

public class Pagination<T>{
    private List<T> items;
    private int currentPage = 1;
    private int totalPages;
    private int totalItems;
    private int pageSize = 5;

    public Pagination(List<T> items, int currentPage, int totalPages, int totalItems, int pageSize){
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
    }

    public Pagination(){
    }

    public int getPageSize(){
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    public List<T> getItems(){
        return items;
    }

    public void setItems(List<T> items){
        this.items = items;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 1 || currentPage > totalPages) {
            PrintError.println("Invalid page number! Please enter between 1 and " + totalPages);
        }
        this.currentPage = currentPage;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }

    public int getTotalItems(){
        return totalItems;
    }

    public void setTotalItems(int totalItems){
        this.totalItems = totalItems;
    }
}
