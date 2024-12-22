/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Bahan {
    private int IDBahan;
    private String namaBahan;
    private int hargaBahan;
    private int stokBahan;
    
    public Bahan(int IDBahan, String namaBahan, int hargaBahan, int stokBahan) {
        this.IDBahan = IDBahan;
        this.namaBahan = namaBahan;
        this.hargaBahan = hargaBahan;
        this.stokBahan = stokBahan;
    }

    // Getter and Setter for IDBahan
    public int getIDBahan() {
        return IDBahan;
    }

    public void setIDBahan(int IDBahan) {
        this.IDBahan = IDBahan;
    }

    // Getter and Setter for namaBahan
    public String getNamaBahan() {
        return namaBahan;
    }

    public void setNamaBahan(String namaBahan) {
        this.namaBahan = namaBahan;
    }

    // Getter and Setter for hargaBahan
    public int getHargaBahan() {
        return hargaBahan;
    }

    public void setHargaBahan(int hargaBahan) {
        this.hargaBahan = hargaBahan;
    }

    // Getter and Setter for stokBahan
    public int getStokBahan() {
        return stokBahan;
    }

    public void setStokBahan(int stokBahan) {
        this.stokBahan = stokBahan;
    }
}
