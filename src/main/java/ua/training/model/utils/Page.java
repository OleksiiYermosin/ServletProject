package ua.training.model.utils;

import java.util.Set;

public class Page<T> {

    private Set<T> entity;

    private Integer currentPage;

    private Integer firstPage;

    private Integer lastPage;

    private Integer previousPage;

    private Integer nextPage;

    private String sortField;

    private String sortDirection;

    private boolean isSearchByDate;

    private String date;

    private boolean isSearchByName;

    private String name;

    private String surname;

    public Page() {
    }

    public Page(Set<T> entity, Integer currentPage, Integer firstPage, Integer lastPage, String sortField, String sortDirection) {
        this.entity = entity;
        this.currentPage = currentPage;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        if (currentPage - 1 >= firstPage) {
            this.previousPage = currentPage - 1;
        }
        if(currentPage + 1 <= lastPage){
            this.nextPage = currentPage + 1;
        }
    }

    public Page(Set<T> entity, Integer currentPage, Integer firstPage, Integer lastPage,
                String sortField, String sortDirection, boolean isSearchByDate, String date, boolean isSearchByName,
                String name, String surname) {
        this.entity = entity;
        this.currentPage = currentPage;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        if (currentPage - 1 >= firstPage) {
            this.previousPage = currentPage - 1;
        }
        if(currentPage + 1 <= lastPage){
            this.nextPage = currentPage + 1;
        }
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.isSearchByDate = isSearchByDate;
        this.date = date;
        this.isSearchByName = isSearchByName;
        this.name = name;
        this.surname = surname;
    }

    public Set<T> getEntity() {
        return entity;
    }

    public void setEntity(Set<T> entity) {
        this.entity = entity;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(Integer previousPage) {
        this.previousPage = previousPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public boolean isSearchByDate() {
        return isSearchByDate;
    }

    public void setSearchByDate(boolean searchByDate) {
        isSearchByDate = searchByDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSearchByName() {
        return isSearchByName;
    }

    public void setSearchByName(boolean searchByName) {
        isSearchByName = searchByName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
