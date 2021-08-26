package ua.training.model.entities;

public class Taxi {

    private Long id;

    private String info;

    private TaxiClass taxiClass;

    private int capacity;

    private TaxiStatus taxiStatus;

    private Driver driver;

    public Taxi(){}

    public Taxi(String info, TaxiClass taxiClass, int capacity, TaxiStatus taxiStatus, Driver driver) {
        this.info = info;
        this.taxiClass = taxiClass;
        this.capacity = capacity;
        this.taxiStatus = taxiStatus;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public TaxiClass getTaxiClass() {
        return taxiClass;
    }

    public void setTaxiClass(TaxiClass taxiClass) {
        this.taxiClass = taxiClass;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TaxiStatus getTaxiStatus() {
        return taxiStatus;
    }

    public void setTaxiStatus(TaxiStatus taxiStatus) {
        this.taxiStatus = taxiStatus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", taxiClass=" + taxiClass +
                ", capacity=" + capacity +
                ", taxiStatus=" + taxiStatus +
                ", driver=" + driver +
                '}';
    }
}
