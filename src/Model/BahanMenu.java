/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class BahanMenu {
    private String namaBahan;
    private int stokSaatIni;
    private int stokDibutuhkan;

    public BahanMenu(String namaBahan, int stokSaatIni, int stokDibutuhkan) {
        this.namaBahan = namaBahan;
        this.stokSaatIni = stokSaatIni;
        this.stokDibutuhkan = stokDibutuhkan;
    }

    // Getter dan Setter
    public String getNamaBahan() {
        return namaBahan;
    }

    public int getStokSaatIni() {
        return stokSaatIni;
    }

    public int getStokDibutuhkan() {
        return stokDibutuhkan;
    }
}
