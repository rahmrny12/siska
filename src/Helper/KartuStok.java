/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class KartuStok {
    public static void insertKartuStok(Connection conn, int idBahan, String keterangan, int jumlahMasuk, int jumlahKeluar, String type) throws SQLException {
        // Query untuk mendapatkan saldo terakhir bahan
        String queryBahan = "SELECT stok_bahan FROM bahan WHERE id_bahan = ?";
        
        try (PreparedStatement stmtBahan = conn.prepareStatement(queryBahan)) {
            stmtBahan.setInt(1, idBahan);
            ResultSet rs = stmtBahan.executeQuery();
            int saldoBaru = 0;
            
            if (rs.next()) {
//                int IDKartuStok = rs.getInt("id_kartu_stok");
                saldoBaru = rs.getInt("stok_bahan");
//                saldoBaru = saldoSebelum + jumlahMasuk - jumlahKeluar;
//                int jumlahKeluarLama = rs.getInt("jumlah_keluar");
//                Date tanggal = rs.getDate("tanggal");
//                String keteranganSebelum = rs.getString("keterangan");
            }

//                if (type.equals("transaksi") && keteranganSebelum.equals("Transaksi")) {
//                    String insertKartuStok = "UPDATE kartu_stok SET jumlah_keluar = ?, saldo = ?"
//                            + " WHERE id_kartu_stok = ?";
//
//                    try (PreparedStatement stmtUpdate = conn.prepareStatement(insertKartuStok)) {
//                        stmtUpdate.setInt(1, jumlahKeluarLama + jumlahKeluar);
//                        stmtUpdate.setInt(2, saldoBaru);
//                        stmtUpdate.setInt(3, IDKartuStok);
//  
//                        stmtUpdate.executeUpdate();
//                    }
//                } else {
                    // Insert mutasi ke kartu stok
                    String insertKartuStok = "INSERT INTO kartu_stok (id_bahan, tanggal, keterangan, jumlah_masuk, jumlah_keluar, saldo) " +
                                             "VALUES (?, NOW(), ?, ?, ?, ?)";

                    try (PreparedStatement stmtInsert = conn.prepareStatement(insertKartuStok)) {
                        stmtInsert.setInt(1, idBahan);
                        stmtInsert.setString(2, keterangan);
                        stmtInsert.setInt(3, jumlahMasuk);
                        stmtInsert.setInt(4, jumlahKeluar);
                        stmtInsert.setInt(5, saldoBaru);

                        stmtInsert.executeUpdate();
                    }
//                }
            
        }
    }
}
