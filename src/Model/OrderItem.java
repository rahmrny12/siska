/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class OrderItem {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String topping;
    private String level;

    public OrderItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = 1; // Default quantity
        this.topping = "None"; // Default topping
        this.level = "Mild"; // Default level
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getTopping() { return topping; }
    public void setTopping(String topping) { this.topping = topping; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}