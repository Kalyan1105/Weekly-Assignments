package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String name;
    private double price;
    private String category;
    private boolean available;

    public MenuItem() {}

    public MenuItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return id + " | " + name + " | ₹" + price + " | " + category + " | Available: " + available;
    }
}