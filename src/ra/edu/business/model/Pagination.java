package ra.edu.business.model;

import java.util.List;

public abstract class Pagination<T>{
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;

    public Pagination(List<T> items, int currentPage, int totalPages, int totalItems){
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public Pagination(){
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

    public void setCurrentPage(int currentPage){
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
