/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Menu {
    private int id;
    private String namaMenu;
    private double harga;

    public Menu(int id, String namaMenu, double harga) {
        this.id = id;
        this.namaMenu = namaMenu;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public double getHarga() {
        return harga;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + namaMenu + '\'' +
                ", price=" + harga +
                '}';
    }
}
