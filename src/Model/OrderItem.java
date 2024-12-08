/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class OrderItem {
    private final int id;
    private final String nama;
    private final double harga;
    private final String jenis;
    private int kuantitas;
    private List<String> toppings; // Changed to List<String>
    private List<String> levels;   // Changed to List<String>

    public OrderItem(int id, String nama, double harga, String jenis) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
        this.kuantitas = 1;
        this.toppings = new ArrayList<>(); // Initialize with empty list
        this.levels = new ArrayList<>();   // Initialize with empty list
    }

    // Getters and setters
    public int getId() { return id; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getJenis() { return jenis; }
    public int getKuantitas() { return kuantitas; }
    public void setKuantitas(int kuantitas) { this.kuantitas = kuantitas; }

    public List<String> getToppings() { return toppings; }
    public void addTopping(String topping) { this.toppings.add(topping); }
    public void removeTopping(String topping) { this.toppings.remove(topping); }

    public List<String> getLevels() { return levels; }
    public void addLevel(String level) { this.levels.add(level); }
    public void removeLevel(String level) { this.levels.remove(level); }
}