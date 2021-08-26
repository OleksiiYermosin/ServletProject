package ua.training.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class Order {

    private Long id;

    private User user;

    private OrderStatus orderStatus;

    private BigDecimal total;

    private LocalDate date;

    private String addressFrom;

    private String addressTo;

    private BigDecimal distance;

    private int peopleAmount;

    private Set<Taxi> taxi;

    private BigDecimal time;

    public Order(){}

    public Order(User user, OrderStatus orderStatus, BigDecimal total, String addressFrom, String addressTo, BigDecimal distance, int peopleAmount, BigDecimal time) {
        this.user = user;
        this.orderStatus = orderStatus;
        this.total = total;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.distance = distance;
        this.peopleAmount = peopleAmount;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public Set<Taxi> getTaxi() {
        return taxi;
    }

    public void setTaxi(Set<Taxi> taxi) {
        this.taxi = taxi;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }
}
