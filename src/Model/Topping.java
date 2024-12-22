/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Topping {
    private int idBahan;
    private String namaTopping;
    private int hargaTopping;
    private int jumlahTopping;

    // Constructor
    public Topping(int idBahan, String namaTopping, int hargaTopping, int jumlahTopping) {
        this.idBahan = idBahan;
        this.namaTopping = namaTopping;
        this.hargaTopping = hargaTopping;
        this.jumlahTopping = jumlahTopping;
    }

    // Getters and Setters
    public int getIdBahan() {
        return idBahan;
    }

    public void setIdBahan(int idBahan) {
        this.idBahan = idBahan;
    }

    public String getNamaTopping() {
        return namaTopping;
    }

    public void setNamaTopping(String namaTopping) {
        this.namaTopping = namaTopping;
    }

    public int getHargaTopping() {
        return hargaTopping;
    }

    public void setHargaTopping(int hargaTopping) {
        this.hargaTopping = hargaTopping;
    }
    
    public int getJumlahTopping() {
        return jumlahTopping;
    }

    public void setJumlahTopping(int jumlahTopping) {
        this.jumlahTopping = jumlahTopping;
    }
}
