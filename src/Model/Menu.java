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
    private String jenis;

    public Menu(int id, String namaMenu, double harga, String jenis) {
        this.id = id;
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.jenis = jenis;
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
    
    public String getJenis() {
        return jenis;
    }
}
