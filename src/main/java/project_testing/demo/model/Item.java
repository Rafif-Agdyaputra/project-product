package project_testing.demo.model;

import java.util.*;

public class Item {

    private String id;
    private String name;
    private double price;
    private int stock;
    private List<String> categories;

    public Item(String id, String name, double price, int stock, List<String> categories) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categories = categories != null ? categories : Collections.emptyList();
    }
    
    public Item() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public List<String> getCategories() {return categories; }

    public void setId(String id) { 
        this.id = id; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setPrice(double price) { 
        this.price = price; 
    }
    
    public void setStock(int stock) { 
        this.stock = stock; 
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}