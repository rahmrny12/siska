/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ComponentUI;

import Helper.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class BuatLaporan extends javax.swing.JFrame {

    private Connection conn;
    
    private Integer IDUser = null;
    
    /**
     * Creates new form PaymentForm
     */
    public BuatLaporan() {
        initComponents();
        
        UserInfo user = UserInfo.getInstance();
        IDUser = user.getIDUser();
        
        this.setLocationRelativeTo(null);
        
        conn = Helper.Database.OpenConnection();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        txtTotalPengeluaran = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDeskripsiPengeluaran = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(144, 2, 2));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Buat Laporan");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total pengeluaran");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Deskripsi Pengeluaran");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tanggal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalPengeluaran)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(txtDeskripsiPengeluaran)
                            .addComponent(txtTanggal)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDeskripsiPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int totalPengeluaran = Integer.parseInt(txtTotalPengeluaran.getText());
        String deskripsiPengeluaran =(txtDeskripsiPengeluaran.getText());
        
        PreparedStatement stmt;
        
        try {
            // Membuat query mysql
            String query = "INSERT INTO `laporan_pengeluaran`(`id_user`, `tanggal_laporan`, `deskripsi_pengeluaran`, `total_pengeluaran`) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(query);
            
            java.sql.Date nowDate = new java.sql.Date(System.currentTimeMillis());
            
            if (IDUser == null) {
                stmt.setNull(1, java.sql.Types.INTEGER);  // Handle as NULL in SQL
            } else {
                stmt.setInt(1, IDUser);  // Set actual value if it's not null
            }
            
            stmt.setDate(2, nowDate);
            stmt.setString(3, deskripsiPengeluaran);
            stmt.setInt(4, totalPengeluaran);
            stmt.execute();
            
            // First, calculate total pendapatan from transaksi table
            String queryPendapatan = "SELECT SUM(total_harga) as total_pendapatan FROM transaksi WHERE tanggal_transaksi = ?";
            stmt = conn.prepareStatement(queryPendapatan);
            stmt.setDate(1, nowDate);  // Set the variable tanggalTransaksi as a parameter
            ResultSet rsPendapatan = stmt.executeQuery();
            int totalPendapatan = 0;
            if (rsPendapatan.next()) {
                totalPendapatan = rsPendapatan.getInt("total_pendapatan");
            }
            
            if (totalPendapatan == 0) {
                System.out.println("Belum ada transaksi hari ini.");
                return;
            }

            // Calculate laba bersih
            int labaBersih = totalPendapatan - totalPengeluaran;

            // Insert the data into laporan_laba
            String queryLaba = "INSERT INTO laporan_laba (tanggal_laporan, total_pendapatan, total_pengeluaran, laba_bersih, id_user) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(queryLaba);
            stmt.setDate(1, nowDate);  // Use the same date for laporan_laba
            stmt.setInt(2, totalPendapatan);
            stmt.setInt(3, totalPengeluaran);
            stmt.setInt(4, labaBersih);
            
            if (IDUser == null) {
                stmt.setNull(5, java.sql.Types.INTEGER);  // Handle as NULL in SQL
            } else {
                stmt.setInt(5, IDUser);  // Set actual value if it's not null
            }
            
            stmt.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuatLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuatLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuatLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuatLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuatLaporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtDeskripsiPengeluaran;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalPengeluaran;
    // End of variables declaration//GEN-END:variables
}