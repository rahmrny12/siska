/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Helper.KartuStok;
import Model.Menu;
import Model.OrderItem;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class FormTransaksi extends javax.swing.JFrame {

    private Connection conn;
    
    private List<Menu> dataMenu = new ArrayList<>();
    
    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        initComponents();
        
        conn = Helper.Database.OpenConnection();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        leftPanel.setFormTransaksi(this);
        
        loadDataMenu();
        
        menuList.setMenuActionListener((int id) -> {
            Menu selectedMenu = null;
            for (Menu menu : dataMenu) {
                if (menu.getId() == id) {
                    selectedMenu = menu;
                }
            }
            
            leftPanel.addOrderItem(selectedMenu.getId(), selectedMenu.getNamaMenu(), selectedMenu.getHarga(), selectedMenu.getJenis());
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuPanel = new javax.swing.JPanel();
        btnToTransaksi = new javax.swing.JButton();
        btnToRiwayatTransaksi = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        transaksi = new javax.swing.JPanel();
        topPanel = new Component.Transaction.TopPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuList = new Component.Transaction.MenuPanel();
        leftPanel = new Component.Transaction.LeftPanel();
        riwayatTransaksi = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRiwayatTransaksi = new javax.swing.JTable();
        jLabel69 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        setName("myFrame"); // NOI18N

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnToTransaksi.setBackground(new java.awt.Color(198, 40, 40));
        btnToTransaksi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnToTransaksi.setText("Transaksi");
        btnToTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToTransaksiActionPerformed(evt);
            }
        });

        btnToRiwayatTransaksi.setBackground(new java.awt.Color(198, 40, 40));
        btnToRiwayatTransaksi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToRiwayatTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnToRiwayatTransaksi.setText("Riwayat Transaksi");
        btnToRiwayatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToRiwayatTransaksiActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(204, 204, 204));
        btnLogout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(102, 102, 102));
        btnLogout.setText("Keluar");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnToTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToRiwayatTransaksi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnToTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToRiwayatTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(144, 2, 2));
        mainPanel.setLayout(new java.awt.CardLayout());

        jScrollPane1.setViewportView(menuList);

        javax.swing.GroupLayout transaksiLayout = new javax.swing.GroupLayout(transaksi);
        transaksi.setLayout(transaksiLayout);
        transaksiLayout.setHorizontalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 693, Short.MAX_VALUE)
            .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(transaksiLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                    .addContainerGap()))
        );
        transaksiLayout.setVerticalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
            .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(transaksiLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(transaksiLayout.createSequentialGroup()
                            .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        mainPanel.add(transaksi, "card2");

        riwayatTransaksi.setBackground(new java.awt.Color(140, 2, 2));

        tblRiwayatTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "Waktu", "ID Transaksi", "Jenis", "Harga"
            }
        ));
        tblRiwayatTransaksi.setRowHeight(40);
        jScrollPane2.setViewportView(tblRiwayatTransaksi);

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("Riwayat Transaksi");

        javax.swing.GroupLayout riwayatTransaksiLayout = new javax.swing.GroupLayout(riwayatTransaksi);
        riwayatTransaksi.setLayout(riwayatTransaksiLayout);
        riwayatTransaksiLayout.setHorizontalGroup(
            riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(riwayatTransaksiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        riwayatTransaksiLayout.setVerticalGroup(
            riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, riwayatTransaksiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        mainPanel.add(riwayatTransaksi, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        dispose(); // this will close current login box window

        //this will open a nextpage window.
        FormLogin page = new FormLogin();
        page.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnToTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToTransaksiActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(transaksi);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnToTransaksiActionPerformed

    private void btnToRiwayatTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToRiwayatTransaksiActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(riwayatTransaksi);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadTableRiwayatTransaksi();
    }//GEN-LAST:event_btnToRiwayatTransaksiActionPerformed

    public String generateTransactionId() {
        String transactionId = "TRX001"; // Default format if no previous transactions exist
        String query = "SELECT id_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1"; // Get the last transaction ID

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Get the last transaction ID (e.g., TRX001)
                String lastId = rs.getString("id_transaksi");

                // Extract the numeric part after 'TRX'
                String numericPart = lastId.substring(3); // Extract '001' from 'TRX001'

                // Increment the numeric part
                int incrementedValue = Integer.parseInt(numericPart) + 1;

                // Format the new ID with leading zeros if necessary
                transactionId = "TRX" + String.format("%03d", incrementedValue); // TRX002, TRX003, etc.
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it as needed
        }

        return transactionId;
    }

    
    public void submitTransaction(Map<Integer, OrderItem> orderItems, int totalPembayaran, int totalKembalian) {
        PreparedStatement stmt;
        String idTransaksi = generateTransactionId(); // Get the new transaction ID

        try {
            conn.setAutoCommit(false);
            
            String transaksiQuery = "INSERT INTO transaksi (id_transaksi, id_user, id_pelanggan, jenis_pesanan, total_harga, total_pembayaran, kembalian, tanggal_transaksi, waktu_transaksi, status_pesanan) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(transaksiQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, idTransaksi);
            stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setNull(3, java.sql.Types.INTEGER);
            stmt.setString(4, "dine_in");
            stmt.setDouble(5, calculateTotalHarga(orderItems));  // Sum of item prices
            stmt.setDouble(6, totalPembayaran);
            stmt.setDouble(7, totalKembalian);
            stmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));  // Current date
            stmt.setTime(9, new java.sql.Time(System.currentTimeMillis()));  // Current time
            stmt.setString(10, "diproses");

            stmt.executeUpdate();

            // Step 2: Insert into 'detail_transaksi' table
            for (OrderItem item : orderItems.values()) {
                String detailQuery = "INSERT INTO detail_transaksi (id_transaksi, id_menu, kuantitas, harga_jual, subtotal, topping, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(detailQuery);

                stmt.setString(1, idTransaksi);
                stmt.setInt(2, item.getId());  // Assuming item.getId() corresponds to the menu ID
                stmt.setInt(3, item.getKuantitas());
                stmt.setDouble(4, item.getHarga());
                stmt.setDouble(5, item.getHarga() * item.getKuantitas());
                
                String toppingString = String.join(",", item.getToppings());
                stmt.setString(6, toppingString);
                
                String levelString = String.join(",", item.getLevels());
                stmt.setString(7, levelString);

                stmt.executeUpdate();
                
                String fetchBahanQuery = "SELECT bm.id_bahan, b.nama_bahan, bm.jumlah_bahan " +
                         "FROM bahan_menu bm " +
                         "INNER JOIN bahan b ON bm.id_bahan = b.id_bahan " +
                         "WHERE bm.id_menu = ?";
                PreparedStatement fetchStmt = conn.prepareStatement(fetchBahanQuery);
                fetchStmt.setInt(1, item.getId());
                ResultSet rs = fetchStmt.executeQuery();

                while (rs.next()) {
                    int idBahan = rs.getInt("id_bahan");
                    String namaBahan = rs.getString("nama_bahan");
                    int jumlahPerPorsi = rs.getInt("jumlah_bahan");

                    // Calculate total quantity required
                    int totalRequired = jumlahPerPorsi * item.getKuantitas();

                    // Check stock
                    String checkStockQuery = "SELECT stok_bahan FROM bahan WHERE id_bahan = ?";
                    PreparedStatement checkStockStmt = conn.prepareStatement(checkStockQuery);
                    checkStockStmt.setInt(1, idBahan);
                    ResultSet stockRs = checkStockStmt.executeQuery();

                    if (stockRs.next()) {
                        int currentStock = stockRs.getInt("stok_bahan");

                        if (currentStock < totalRequired) {
                            throw new SQLException("Stok tidak mencukupi untuk bahan berikut: " + namaBahan);
                        }

                        // Update stock
                        String updateStockQuery = "UPDATE bahan SET stok_bahan = stok_bahan - ? WHERE id_bahan = ?";
                        PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
                        updateStockStmt.setInt(1, totalRequired);
                        updateStockStmt.setInt(2, idBahan);
                        updateStockStmt.executeUpdate();
                        
                        String keterangan = "Transaksi";
                        int jumlahMasuk = 0;
                        int jumlahKeluar = totalRequired;

                        KartuStok.insertKartuStok(conn, idBahan, keterangan, jumlahMasuk, jumlahKeluar, "transaksi");
                    } else {
                        throw new SQLException("Bahan dengan ID: " + idBahan + " tidak ditemukan.");
                    }
                }
            }
            
            conn.commit();

            // Show confirmation message
            JOptionPane.showMessageDialog(null,
                    "Pembayaran Berhasil dari transaksi!\nTotal Pembayaran: Rp. " + totalPembayaran +
                            "\nKembalian: Rp. " + totalKembalian,
                    "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {}
            
            JOptionPane.showMessageDialog(null, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private double calculateTotalHarga(Map<Integer, OrderItem> orderItems) {
        double totalHarga = 0;
        for (OrderItem item : orderItems.values()) {
            totalHarga += item.getHarga() * item.getKuantitas();
        }
        return totalHarga;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnToRiwayatTransaksi;
    private javax.swing.JButton btnToTransaksi;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private Component.Transaction.LeftPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private Component.Transaction.MenuPanel menuList;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel riwayatTransaksi;
    private javax.swing.JTable tblRiwayatTransaksi;
    private Component.Transaction.TopPanel topPanel;
    private javax.swing.JPanel transaksi;
    // End of variables declaration//GEN-END:variables

    private void loadDataMenu() {
        try {
            String query = "SELECT * FROM menu";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            
            while (res.next()) {
                int idMenu = res.getInt("id_menu");
                String namaMenu = res.getString("nama_menu");
                double harga = res.getDouble("harga");
                String jenis = res.getString("jenis");

                dataMenu.add(new Menu(idMenu, namaMenu, harga, jenis));
            }
            
            menuList.setDataMenu(dataMenu);
        } catch (Exception e) {}
    }

    private void loadTableRiwayatTransaksi() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tanggal");
        model.addColumn("Waktu");
        model.addColumn("ID Transaksi");
        model.addColumn("Jenis");
        model.addColumn("Total Transaksi");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT tanggal_transaksi, waktu_transaksi, id_transaksi, jenis_pesanan, total_harga FROM transaksi ORDER BY tanggal_transaksi DESC, waktu_transaksi DESC";
            Statement stm=conn.createStatement();
            ResultSet res=stm.executeQuery(query);
            while(res.next()){
                model.addRow(new Object[] {
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4).equals("dine_in") ? "Makan di tempat" : "Take Away",
                    "Rp. " + String.valueOf((int) res.getFloat(5))
                });
            }
            tblRiwayatTransaksi.setModel(model);
        }catch (Exception e) {}
    }
}