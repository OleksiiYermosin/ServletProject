package ua.training.model.entities;

import java.math.BigDecimal;

public class TaxiClass {

    private Long id;

    private String name;

    private BigDecimal multiplier;

    public TaxiClass(){}

    public TaxiClass(String name, BigDecimal multiplier) {
        this.name = name;
        this.multiplier = multiplier;
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

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }
}
