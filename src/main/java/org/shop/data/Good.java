package org.shop.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Good {
    private final UUID id;
    private String name;
    private BigDecimal deliveryPrice;
    private GoodCategories category;
    private LocalDate expiryDate;
    private int quantity;

    public Good(String name, BigDecimal deliveryPrice, GoodCategories category, LocalDate expiryDate, int quantity) {
        this.id = UUID.randomUUID();
        setName(name);
        setDeliveryPrice(deliveryPrice);
        setCategory(category);
        setExpiryDate(expiryDate);
        setQuantity(quantity);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        this.name = name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        if (deliveryPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Delivery price must be greater than zero");
        }

        this.deliveryPrice = deliveryPrice;
    }

    public GoodCategories getCategory() {
        return category;
    }

    public void setCategory(GoodCategories category) {
        this.category = category;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if (!expiryDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry Date must be after today");
        }

        this.expiryDate = expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", category=" + category +
                ", expiryDate=" + expiryDate +
                ", quantity=" + quantity +
                '}';
    }
}
