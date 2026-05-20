package org.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Cashier implements Serializable {
    private final UUID id;
    private String name;
    private BigDecimal salary;

    public Cashier(String name, BigDecimal salary) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.salary = salary;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
