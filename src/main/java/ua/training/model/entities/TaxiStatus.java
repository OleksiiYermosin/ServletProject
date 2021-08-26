package ua.training.model.entities;

public class TaxiStatus {

    private Long id;

    private String name;

    public TaxiStatus(){}

    public TaxiStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
