/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import ComponentUI.BuatLaporan;
import ComponentUI.MessageDialog;
import Helper.UserInfo;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class FormLaporan extends javax.swing.JFrame {

    private Connection conn;
    
    private HashMap<String, Object[]> dataStokBahan = new HashMap<>();
    private Integer IDUser = null;
    
    public FormLaporan() {
        initComponents();
        
        UserInfo user = UserInfo.getInstance();
        IDUser = user.getIDUser();
        
        lblUsername.setText(user.getUsername());
        lblRole.setText(user.getRole());
        
        conn = Helper.Database.OpenConnection();
        
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dashboard);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadDataProfit();
        loadDataProduk();
    }
    private void loadTableLaporanLaba(Integer bulan, Integer tahun) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Laporan Laba");
        model.addColumn("Tanggal laporan");
        model.addColumn("Total Pendapatan");
        model.addColumn("Total pengeluaran");
        model.addColumn("Laba Bersih");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM laporan_laba";
        if (bulan != null || tahun != null) {
            query += " WHERE";
            if (bulan != null) {
                query += " MONTH(tanggal_laporan) = ?";
            }
            if (tahun != null) {
                query += (bulan != null ? " AND" : "") + " YEAR(tanggal_laporan) = ?";
            }
        }
        PreparedStatement ps = conn.prepareStatement(query);
        int index = 1;
        if (bulan != null) {
            ps.setInt(index++, bulan);
        }
        if (tahun != null) {
            ps.setInt(index++, tahun);
        }
        ResultSet res = ps.executeQuery();
            while(res.next()){
                model.addRow(new Object[] {res.getString(1),
                    res.getString(2),res.getString(3),res.getString(4),res.getString(5)});
                }
            tblLaporanLaba.setModel(model);
        }catch (Exception e) {
        e.printStackTrace();                
        }                           
    }
    
    public void loadYears(JComboBox<String> comboBox) {
        // Bersihkan data ComboBox sebelum diisi
        try {
            comboBox.removeAllItems();

            // Ambil tahun saat ini
            int tahunSekarang = Year.now().getValue();

            // Tambahkan 5 tahun sebelumnya, tahun sekarang, dan 5 tahun ke depan ke ComboBox
            for (int i = -5; i <= 5; i++) {
                comboBox.addItem(String.valueOf(tahunSekarang + i));
            }
            
            comboBox.setSelectedItem(String.valueOf(tahunSekarang));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTableRiwayatTransaksi(Integer bulan, Integer tahun) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Transaksi");
        model.addColumn("Jenis pemesanan");
        model.addColumn("Total Harga");
        model.addColumn("Total Pembayaran");
        model.addColumn("Kembalian");
        model.addColumn("Tanggal Transaksi");
        

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT id_transaksi, jenis_pesanan, total_harga, total_pembayaran, kembalian, tanggal_transaksi FROM transaksi";
            if (bulan != null || tahun != null) {
            query += " WHERE";
            if (bulan != null) {
                query += " MONTH(tanggal_transaksi) = ?";
            }
            if (tahun != null) {
                query += (bulan != null ? " AND" : "") + " YEAR(tanggal_transaksi) = ?";
            }
        }
        PreparedStatement ps = conn.prepareStatement(query);
        int index = 1;
        if (bulan != null) {
            ps.setInt(index++, bulan);
        }
        if (tahun != null) {
            ps.setInt(index++, tahun);
        }

        ResultSet res = ps.executeQuery();

            while(res.next()){
                model.addRow(new Object[] {res.getString(1),
                    res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6)});
                }
            tblRiwayatTransaksi.setModel(model);
        }catch (Exception e) {
        e.printStackTrace();                
        }                           
    }
    
    private void loadTableLaporanKeluar(Integer bulan, Integer tahun) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tanggal Laporan");
        model.addColumn("Total pengeluaran");
        model.addColumn("Deskripsi Pengeluaran");
        

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM total_per_tanggal";
            if (bulan != null || tahun != null) {
                query += " WHERE";
                if (bulan != null) {
                    query += " MONTH(tanggal_laporan) = ?";
                }
                if (tahun != null) {
                    query += (bulan != null ? " AND" : "") + " YEAR(tanggal_laporan) = ?";
                }
            }
            PreparedStatement ps = conn.prepareStatement(query);
            int index = 1;
            if (bulan != null) {
                ps.setInt(index++, bulan);
            }
            if (tahun != null) {
                ps.setInt(index++, tahun);
            }
            ResultSet res = ps.executeQuery();
            while(res.next()){
                model.addRow(new Object[] {res.getString(1),
                    res.getString(2),res.getString(3),});
                }
            tblLaporanPengeluaran.setModel(model);
        }catch (Exception e) {
        e.printStackTrace();                
        }                           
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        btnToDashboard = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnToRiwayatTransaksi = new javax.swing.JButton();
        btnToKartuStok = new javax.swing.JButton();
        btnToLaporanPengeluaran = new javax.swing.JButton();
        btnToLaporanLaba = new javax.swing.JButton();
        btnToStokMasuk = new javax.swing.JButton();
        panelUserProfile1 = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        riwayatTransaksi = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRiwayatTransaksi = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        btnBuatLaporan = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        filterTahunTransaksi = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        filterBulanTransaksi = new javax.swing.JComboBox<>();
        laporanKeluar = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblLaporanPengeluaran = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        filterBulanKeluar = new javax.swing.JComboBox<>();
        filterTahunKeluar = new javax.swing.JComboBox<>();
        laporanLaba = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLaporanLaba = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        filterBulanLaba = new javax.swing.JComboBox<>();
        filterTahunLaba = new javax.swing.JComboBox<>();
        laporanStokMasuk = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblMenu4 = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        cbBulan = new javax.swing.JComboBox<>();
        cbTahun = new javax.swing.JComboBox<>();
        kartuStok = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        filterBulanKartuStok = new javax.swing.JComboBox<>();
        filterTahunKartuStok = new javax.swing.JComboBox<>();
        btnEkspor = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        txtPilihBahan = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblKartuStok = new javax.swing.JTable();
        dashboard = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        chartContainerPanel = new javax.swing.JPanel();
        panelProduk = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodyPanel.setBackground(new java.awt.Color(140, 2, 2));

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnToDashboard.setBackground(new java.awt.Color(198, 40, 40));
        btnToDashboard.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnToDashboard.setText("Dashboard");
        btnToDashboard.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToDashboardActionPerformed(evt);
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

        btnToRiwayatTransaksi.setBackground(new java.awt.Color(198, 40, 40));
        btnToRiwayatTransaksi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToRiwayatTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnToRiwayatTransaksi.setText("Riwayat Transaksi");
        btnToRiwayatTransaksi.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToRiwayatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToRiwayatTransaksiActionPerformed(evt);
            }
        });

        btnToKartuStok.setBackground(new java.awt.Color(198, 40, 40));
        btnToKartuStok.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToKartuStok.setForeground(new java.awt.Color(255, 255, 255));
        btnToKartuStok.setText("Kartu Stok");
        btnToKartuStok.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToKartuStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToKartuStokActionPerformed(evt);
            }
        });

        btnToLaporanPengeluaran.setBackground(new java.awt.Color(198, 40, 40));
        btnToLaporanPengeluaran.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToLaporanPengeluaran.setForeground(new java.awt.Color(255, 255, 255));
        btnToLaporanPengeluaran.setText("Laporan Keluar");
        btnToLaporanPengeluaran.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToLaporanPengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToLaporanPengeluaranActionPerformed(evt);
            }
        });

        btnToLaporanLaba.setBackground(new java.awt.Color(198, 40, 40));
        btnToLaporanLaba.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToLaporanLaba.setForeground(new java.awt.Color(255, 255, 255));
        btnToLaporanLaba.setText("Laporan Laba");
        btnToLaporanLaba.setPreferredSize(new java.awt.Dimension(136, 100));
        btnToLaporanLaba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToLaporanLabaActionPerformed(evt);
            }
        });

        btnToStokMasuk.setBackground(new java.awt.Color(198, 40, 40));
        btnToStokMasuk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToStokMasuk.setForeground(new java.awt.Color(255, 255, 255));
        btnToStokMasuk.setText("Laporan S. Masuk");
        btnToStokMasuk.setPreferredSize(new java.awt.Dimension(131, 21));
        btnToStokMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToStokMasukActionPerformed(evt);
            }
        });

        panelUserProfile1.setBackground(new java.awt.Color(255, 255, 255));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("Username");

        lblRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRole.setText("Role");

        javax.swing.GroupLayout panelUserProfile1Layout = new javax.swing.GroupLayout(panelUserProfile1);
        panelUserProfile1.setLayout(panelUserProfile1Layout);
        panelUserProfile1Layout.setHorizontalGroup(
            panelUserProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserProfile1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelUserProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelUserProfile1Layout.setVerticalGroup(
            panelUserProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserProfile1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsername)
                .addGap(0, 0, 0)
                .addComponent(lblRole)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnToDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToRiwayatTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToLaporanPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToLaporanLaba, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(panelUserProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnToDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToRiwayatTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToLaporanPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToLaporanLaba, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                        .addComponent(panelUserProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        mainPanel.setBackground(new java.awt.Color(140, 2, 2));
        mainPanel.setLayout(new java.awt.CardLayout());

        riwayatTransaksi.setBackground(new java.awt.Color(144, 2, 2));

        tblRiwayatTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblRiwayatTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Tanggal", "Jenis", "Harga", "Bayar", "Kembalian"
            }
        ));
        tblRiwayatTransaksi.setRowHeight(40);
        tblRiwayatTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRiwayatTransaksiMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblRiwayatTransaksi);

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Riwayat Transaksi");

        btnBuatLaporan.setBackground(new java.awt.Color(204, 204, 204));
        btnBuatLaporan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnBuatLaporan.setForeground(new java.awt.Color(51, 51, 51));
        btnBuatLaporan.setText("Buat Laporan");
        btnBuatLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatLaporanActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Bulan");

        filterTahunTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterTahunTransaksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        filterTahunTransaksi.setPreferredSize(new java.awt.Dimension(64, 22));
        filterTahunTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTahunTransaksiActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Tahun");

        filterBulanTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterBulanTransaksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        filterBulanTransaksi.setPreferredSize(new java.awt.Dimension(64, 22));
        filterBulanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBulanTransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout riwayatTransaksiLayout = new javax.swing.GroupLayout(riwayatTransaksi);
        riwayatTransaksi.setLayout(riwayatTransaksiLayout);
        riwayatTransaksiLayout.setHorizontalGroup(
            riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riwayatTransaksiLayout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterBulanTransaksi, 0, 252, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterTahunTransaksi, 0, 252, Short.MAX_VALUE)
                .addGap(223, 223, 223))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, riwayatTransaksiLayout.createSequentialGroup()
                .addGroup(riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(riwayatTransaksiLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuatLaporan))
                    .addGroup(riwayatTransaksiLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(24, 24, 24))
        );
        riwayatTransaksiLayout.setVerticalGroup(
            riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riwayatTransaksiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(riwayatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterBulanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterTahunTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(btnBuatLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        mainPanel.add(riwayatTransaksi, "card2");

        laporanKeluar.setBackground(new java.awt.Color(144, 2, 2));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Bulan");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Tahun");

        tblLaporanPengeluaran.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLaporanPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tanggal", "Deskripsi", "Total"
            }
        ));
        tblLaporanPengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaporanPengeluaranMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblLaporanPengeluaran);

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Laporan Pengeluaran");

        filterBulanKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterBulanKeluar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        filterBulanKeluar.setPreferredSize(new java.awt.Dimension(64, 22));
        filterBulanKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBulanKeluarActionPerformed(evt);
            }
        });

        filterTahunKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterTahunKeluar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        filterTahunKeluar.setPreferredSize(new java.awt.Dimension(64, 22));
        filterTahunKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTahunKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout laporanKeluarLayout = new javax.swing.GroupLayout(laporanKeluar);
        laporanKeluar.setLayout(laporanKeluarLayout);
        laporanKeluarLayout.setHorizontalGroup(
            laporanKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laporanKeluarLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(laporanKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addGap(24, 24, 24))
            .addGroup(laporanKeluarLayout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterBulanKeluar, 0, 232, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterTahunKeluar, 0, 233, Short.MAX_VALUE)
                .addGap(225, 225, 225))
        );
        laporanKeluarLayout.setVerticalGroup(
            laporanKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanKeluarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(laporanKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterBulanKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterTahunKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        mainPanel.add(laporanKeluar, "card2");

        laporanLaba.setBackground(new java.awt.Color(144, 2, 2));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Bulan");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Tahun");

        tblLaporanLaba.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblLaporanLaba.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Tanggal", "Pendapatan", "Pengeluaran", "Laba bersih"
            }
        ));
        tblLaporanLaba.setRowHeight(40);
        tblLaporanLaba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaporanLabaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLaporanLaba);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Laporan Laba");

        filterBulanLaba.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterBulanLaba.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        filterBulanLaba.setPreferredSize(new java.awt.Dimension(64, 22));
        filterBulanLaba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBulanLabaActionPerformed(evt);
            }
        });

        filterTahunLaba.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterTahunLaba.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        filterTahunLaba.setPreferredSize(new java.awt.Dimension(64, 22));
        filterTahunLaba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTahunLabaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout laporanLabaLayout = new javax.swing.GroupLayout(laporanLaba);
        laporanLaba.setLayout(laporanLabaLayout);
        laporanLabaLayout.setHorizontalGroup(
            laporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanLabaLayout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterBulanLaba, 0, 110, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterTahunLaba, 0, 110, Short.MAX_VALUE)
                .addGap(382, 382, 382))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laporanLabaLayout.createSequentialGroup()
                .addGroup(laporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(laporanLabaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, laporanLabaLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        laporanLabaLayout.setVerticalGroup(
            laporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanLabaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(laporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterBulanLaba, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterTahunLaba, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        mainPanel.add(laporanLaba, "card2");

        laporanStokMasuk.setBackground(new java.awt.Color(144, 2, 2));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Bulan");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Tahun");

        tblMenu4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblMenu4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tanggal", "Bahan", "Harga Pasar", "Harga Beli"
            }
        ));
        tblMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenu4MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblMenu4);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Laporan Stok Masuk");

        cbBulan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbBulan.setPreferredSize(new java.awt.Dimension(64, 22));
        cbBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBulanActionPerformed(evt);
            }
        });

        cbTahun.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        cbTahun.setPreferredSize(new java.awt.Dimension(64, 22));
        cbTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTahunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout laporanStokMasukLayout = new javax.swing.GroupLayout(laporanStokMasuk);
        laporanStokMasuk.setLayout(laporanStokMasukLayout);
        laporanStokMasukLayout.setHorizontalGroup(
            laporanStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanStokMasukLayout.createSequentialGroup()
                .addGroup(laporanStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laporanStokMasukLayout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(laporanStokMasukLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE))
                    .addGroup(laporanStokMasukLayout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        laporanStokMasukLayout.setVerticalGroup(
            laporanStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanStokMasukLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(laporanStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        mainPanel.add(laporanStokMasuk, "card2");

        kartuStok.setBackground(new java.awt.Color(144, 2, 2));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Bulan");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Tahun");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Kartu Stok");

        filterBulanKartuStok.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterBulanKartuStok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        filterBulanKartuStok.setPreferredSize(new java.awt.Dimension(64, 22));
        filterBulanKartuStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBulanKartuStokActionPerformed(evt);
            }
        });

        filterTahunKartuStok.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterTahunKartuStok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        filterTahunKartuStok.setPreferredSize(new java.awt.Dimension(64, 22));
        filterTahunKartuStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTahunKartuStokActionPerformed(evt);
            }
        });

        btnEkspor.setBackground(new java.awt.Color(204, 204, 204));
        btnEkspor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEkspor.setForeground(new java.awt.Color(51, 51, 51));
        btnEkspor.setText("Cetak");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Bahan");

        txtPilihBahan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPilihBahan.setPreferredSize(new java.awt.Dimension(64, 22));
        txtPilihBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPilihBahanActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        tblKartuStok.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblKartuStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Tanggal", "Jenis", "Harga", "Bayar", "Kembalian"
            }
        ));
        tblKartuStok.setRowHeight(40);
        tblKartuStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKartuStokMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblKartuStok);

        jPanel1.add(jScrollPane8, "card2");

        javax.swing.GroupLayout kartuStokLayout = new javax.swing.GroupLayout(kartuStok);
        kartuStok.setLayout(kartuStokLayout);
        kartuStokLayout.setHorizontalGroup(
            kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuStokLayout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kartuStokLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(kartuStokLayout.createSequentialGroup()
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPilihBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterBulanKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterTahunKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEkspor)))
                .addGap(19, 19, 19))
        );
        kartuStokLayout.setVerticalGroup(
            kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuStokLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPilihBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kartuStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filterBulanKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filterTahunKartuStok, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEkspor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(kartuStok, "card2");

        dashboard.setBackground(new java.awt.Color(144, 2, 2));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Dashboard");

        chartContainerPanel.setLayout(new java.awt.BorderLayout());

        panelProduk.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(chartContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(panelProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartContainerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );

        mainPanel.add(dashboard, "card2");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addComponent(menuPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnToDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToDashboardActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dashboard);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadDataProfit();
        loadDataProduk();
    }//GEN-LAST:event_btnToDashboardActionPerformed

    private void loadDataProfit() {
        // Membuat dataset untuk chart
        DefaultCategoryDataset dataset = createDatasetProfit();
        
        // Membuat chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Laba Bersih per Tanggal", // Judul chart
                "Tanggal", // Label sumbu X
                "Laba Bersih", // Label sumbu Y
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Orientasi plot vertikal
                true, // Menampilkan legend
                true, // Menampilkan tooltips
                false // Tidak menampilkan URL
        );
        
        // Menambahkan chart ke dalam panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
//        setContentPane(chartPanel);
        chartContainerPanel.add(chartPanel, BorderLayout.CENTER);
        chartContainerPanel.validate();
        chartContainerPanel.repaint();
    }

    private DefaultCategoryDataset createDatasetProfit() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // SQL query untuk mengambil data dari tabel laporan_laba
        String sql = "SELECT tanggal_laporan, laba_bersih FROM laporan_laba ORDER BY tanggal_laporan";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Mengambil data dari hasil query dan menambahkannya ke dataset
            while (rs.next()) {
                String tanggal = rs.getString("tanggal_laporan");
                int labaBersih = rs.getInt("laba_bersih");

                // Menambahkan data ke dataset (Label untuk sumbu Y adalah "Laba Bersih" dan tanggal untuk sumbu X)
                dataset.addValue(labaBersih, "Laba Bersih", tanggal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }
    
    private void loadDataProduk() {
        // Membuat dataset untuk chart
        DefaultCategoryDataset dataset = createDatasetProduk();
        
        // Membuat chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Produk Terjual Per Tanggal", // Judul chart
                "Nama Menu", // Label sumbu X
                "Jumlah Terjual", // Label sumbu Y
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Orientasi plot vertikal
                true, // Menampilkan legend
                true, // Menampilkan tooltips
                false // Tidak menampilkan URL
        );
        
        // Menambahkan chart ke dalam panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
//        setContentPane(chartPanel);
        panelProduk.add(chartPanel, BorderLayout.CENTER);
        panelProduk.validate();
        panelProduk.repaint();
    }

    private DefaultCategoryDataset createDatasetProduk() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // SQL query untuk mengambil data dari tabel laporan_laba
        String sql = "SELECT transaksi.tanggal_transaksi, menu.nama_menu, SUM(detail_transaksi.kuantitas) AS total FROM transaksi INNER JOIN detail_transaksi ON transaksi.id_transaksi=detail_transaksi.id_transaksi INNER JOIN menu ON detail_transaksi.id_menu = menu.id_menu group BY menu.id_menu order by total DESC";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Mengambil data dari hasil query dan menambahkannya ke dataset
            while (rs.next()) {
                String nama_menu = rs.getString("nama_menu");
                int kuantitas = rs.getInt ("total");

                // Menambahkan data ke dataset (Label untuk sumbu Y adalah "Laba Bersih" dan tanggal untuk sumbu X)
                dataset.addValue(kuantitas, "nama_menu", nama_menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }
    
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        dispose(); // this will close current login box window

        //this will open a nextpage window.
        FormLogin page = new FormLogin();
        page.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tblLaporanLabaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaporanLabaMouseClicked
        int baris = tblLaporanLaba.rowAtPoint(evt.getPoint());
        String id = tblLaporanLaba.getValueAt(baris, 0).toString();
        String tanggal = tblLaporanLaba.getValueAt(baris, 1).toString();
        String pendapatan = tblLaporanLaba.getValueAt(baris, 2).toString();
        String pengeluran = tblLaporanLaba.getValueAt(baris, 3).toString();
        String labaBersih = tblLaporanLaba.getValueAt(baris, 4).toString();
    }//GEN-LAST:event_tblLaporanLabaMouseClicked

    private void filterBulanLabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBulanLabaActionPerformed
        int bulanIndex = filterBulanLaba.getSelectedIndex() + 1;
        Integer tahunDipilih = null;
        if (filterTahunLaba.getSelectedItem() != null) {
            tahunDipilih = Integer.parseInt(filterTahunLaba.getSelectedItem().toString());
        }

        // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
        loadTableLaporanLaba(bulanIndex, tahunDipilih);

    }//GEN-LAST:event_filterBulanLabaActionPerformed

    private void filterTahunLabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTahunLabaActionPerformed
        if (filterTahunLaba.getSelectedItem() != null) {
            int tahunDipilih = Integer.parseInt(filterTahunLaba.getSelectedItem().toString());
            // Ambil nilai bulan dari ComboBox bulan
            Integer bulanIndex = null;
            if (filterBulanLaba.getSelectedIndex() >= 0) {
                bulanIndex = filterBulanLaba.getSelectedIndex() + 1;
            }

            // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
            loadTableLaporanLaba(bulanIndex, tahunDipilih);
        }
    }//GEN-LAST:event_filterTahunLabaActionPerformed

    private void btnToRiwayatTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToRiwayatTransaksiActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(riwayatTransaksi);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadYears(filterTahunTransaksi);
        loadTableRiwayatTransaksi(null,null);
    }//GEN-LAST:event_btnToRiwayatTransaksiActionPerformed

    private void btnToKartuStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToKartuStokActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(kartuStok);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        txtPilihBahan.revalidate();
        txtPilihBahan.repaint();
        
        loadYears(filterTahunKartuStok);
        loadDataStokBahan();
        loadKartuStok();
    }//GEN-LAST:event_btnToKartuStokActionPerformed

    private void loadDataStokBahan() {
        dataStokBahan.clear();
        txtPilihBahan.removeAllItems();
               
        try {
            String query = "SELECT * FROM bahan";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                int idBahan = res.getInt("id_bahan");
                String namaBahan = res.getString("nama_bahan");
                int stokSistem = res.getInt("stok_bahan");
                String satuanSistem = res.getString("satuan");

                // Menyimpan data ke HashMap
                dataStokBahan.put(namaBahan, new Object[]{idBahan, stokSistem, satuanSistem});

                // Menambahkan nama bahan ke combobox
                txtPilihBahan.addItem(namaBahan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadKartuStok() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tanggal");
        model.addColumn("Keterangan");
        model.addColumn("Jumlah Masuk");
        model.addColumn("Jumlah Keluar");
        model.addColumn("Saldo");
        
        String namaBahan = (String) txtPilihBahan.getSelectedItem();
        Object[] data = dataStokBahan.get(namaBahan);
        int idBahan = (int) data[0]; // ID bahan
        
        try {
            //Menampilkan data kedalam tabel
            String query = "SELECT tanggal, keterangan, jumlah_masuk, jumlah_keluar, saldo FROM kartu_stok WHERE id_bahan = ? ORDER BY tanggal ASC";
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idBahan);
            
            ResultSet res = ps.executeQuery();
            
            boolean hasData = false;
            while(res.next()){
                hasData = true;
                
                Object[] row = {
                    res.getDate("tanggal"),
                    res.getString("keterangan"),
                    res.getInt("jumlah_masuk"),
                    res.getInt("jumlah_keluar"),
                    res.getInt("saldo")
                };
                model.addRow(row);
                tblKartuStok.setModel(model);
            }
            
            if (!hasData) {
                Object[] emptyRow = {"-", "No data available", 0, 0, 0};
                model.addRow(emptyRow);
                tblKartuStok.setVisible(false);
            } else {
                tblKartuStok.setVisible(true);
            }
            
            /**
             * @param args the command line arguments
             */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        tblKartuStok.revalidate();
        tblKartuStok.repaint();
    }
    
    
    private void btnToLaporanPengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToLaporanPengeluaranActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(laporanKeluar);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadYears(filterTahunKeluar);
        loadTableLaporanKeluar(null,null);
    }//GEN-LAST:event_btnToLaporanPengeluaranActionPerformed

    private void btnToLaporanLabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToLaporanLabaActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(laporanLaba);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadYears(filterTahunLaba);
        loadTableLaporanLaba(null,null);
    }//GEN-LAST:event_btnToLaporanLabaActionPerformed

    private void btnToStokMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToStokMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnToStokMasukActionPerformed

    private void tblRiwayatTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRiwayatTransaksiMouseClicked
        int baris = tblRiwayatTransaksi.rowAtPoint(evt.getPoint());
        String id = tblRiwayatTransaksi.getValueAt(baris, 0).toString();
        String jenis = tblRiwayatTransaksi.getValueAt(baris, 1).toString();
        String harga = tblRiwayatTransaksi.getValueAt(baris, 2).toString();
        String bayar = tblRiwayatTransaksi.getValueAt(baris, 3).toString();
        String kembalian = tblRiwayatTransaksi.getValueAt(baris, 4).toString();
        String tanggal = tblRiwayatTransaksi.getValueAt(baris, 5).toString();
    }//GEN-LAST:event_tblRiwayatTransaksiMouseClicked

    private void filterBulanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBulanTransaksiActionPerformed
        int bulanIndex = filterBulanTransaksi.getSelectedIndex() + 1;
        // Ambil nilai tahun dari ComboBox tahun
        Integer tahunDipilih = null;
        if (filterTahunTransaksi.getSelectedItem() != null) {
            tahunDipilih = Integer.parseInt(filterTahunTransaksi.getSelectedItem().toString());
        }

        // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
        loadTableRiwayatTransaksi(bulanIndex, tahunDipilih);
    }//GEN-LAST:event_filterBulanTransaksiActionPerformed

    private void filterTahunTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTahunTransaksiActionPerformed
        if (filterTahunTransaksi.getSelectedItem() != null) {
            int tahunDipilih = Integer.parseInt(filterTahunTransaksi.getSelectedItem().toString());
            // Ambil nilai bulan dari ComboBox bulan
            Integer bulanIndex = null;
            if (filterBulanTransaksi.getSelectedIndex() >= 0) {
                bulanIndex = filterBulanTransaksi.getSelectedIndex() + 1;
            }

            // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
            loadTableRiwayatTransaksi(bulanIndex, tahunDipilih);
        }
    }//GEN-LAST:event_filterTahunTransaksiActionPerformed

    private void tblLaporanPengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaporanPengeluaranMouseClicked
        int baris = tblLaporanPengeluaran.rowAtPoint(evt.getPoint());
        String tanggalLaporan = tblLaporanPengeluaran.getValueAt(baris, 0).toString();
        String deskripsiPengeluaran = tblLaporanPengeluaran.getValueAt(baris, 1).toString();
        String totalpengeluran = tblLaporanPengeluaran.getValueAt(baris, 2).toString();
    }//GEN-LAST:event_tblLaporanPengeluaranMouseClicked

    private void filterBulanKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBulanKeluarActionPerformed
        int bulanIndex = filterBulanKeluar.getSelectedIndex() + 1;
        // Ambil nilai tahun dari ComboBox tahun
        Integer tahunDipilih = null;
        if (filterTahunKeluar.getSelectedItem() != null) {
            tahunDipilih = Integer.parseInt(filterTahunKeluar.getSelectedItem().toString());
        }

        // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
        loadTableLaporanKeluar(bulanIndex, tahunDipilih);
    }//GEN-LAST:event_filterBulanKeluarActionPerformed

    private void filterTahunKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTahunKeluarActionPerformed
        if (filterTahunKeluar.getSelectedItem() != null) {
            int tahunDipilih = Integer.parseInt(filterTahunKeluar.getSelectedItem().toString());
            // Ambil nilai bulan dari ComboBox bulan
            Integer bulanIndex = null;
            if (filterBulanKeluar.getSelectedIndex() >= 0) {
                bulanIndex = filterBulanKeluar.getSelectedIndex() + 1;
            }

            // Panggil metode untuk memuat tabel dengan filter bulan dan tahun
            loadTableLaporanKeluar(bulanIndex, tahunDipilih);
        }
    }//GEN-LAST:event_filterTahunKeluarActionPerformed

    private void tblMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenu4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMenu4MouseClicked

    private void cbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBulanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBulanActionPerformed

    private void cbTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTahunActionPerformed

    private void btnBuatLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatLaporanActionPerformed
        BuatLaporan BuatLaporan = new BuatLaporan();
        BuatLaporan.setVisible(true);
    }//GEN-LAST:event_btnBuatLaporanActionPerformed

    private void filterBulanKartuStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBulanKartuStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterBulanKartuStokActionPerformed

    private void filterTahunKartuStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTahunKartuStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTahunKartuStokActionPerformed

    private void txtPilihBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPilihBahanActionPerformed
        loadKartuStok();
    }//GEN-LAST:event_txtPilihBahanActionPerformed

    private void tblKartuStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKartuStokMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKartuStokMouseClicked

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEksporActionPerformed

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
            java.util.logging.Logger.getLogger(FormLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLaporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FormLaporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton btnBuatLaporan;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnToDashboard;
    private javax.swing.JButton btnToKartuStok;
    private javax.swing.JButton btnToLaporanLaba;
    private javax.swing.JButton btnToLaporanPengeluaran;
    private javax.swing.JButton btnToRiwayatTransaksi;
    private javax.swing.JButton btnToStokMasuk;
    private javax.swing.JComboBox<String> cbBulan;
    private javax.swing.JComboBox<String> cbTahun;
    private javax.swing.JPanel chartContainerPanel;
    private javax.swing.JPanel dashboard;
    private javax.swing.JComboBox<String> filterBulanKartuStok;
    private javax.swing.JComboBox<String> filterBulanKeluar;
    private javax.swing.JComboBox<String> filterBulanLaba;
    private javax.swing.JComboBox<String> filterBulanTransaksi;
    private javax.swing.JComboBox<String> filterTahunKartuStok;
    private javax.swing.JComboBox<String> filterTahunKeluar;
    private javax.swing.JComboBox<String> filterTahunLaba;
    private javax.swing.JComboBox<String> filterTahunTransaksi;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel kartuStok;
    private javax.swing.JPanel laporanKeluar;
    private javax.swing.JPanel laporanLaba;
    private javax.swing.JPanel laporanStokMasuk;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel panelProduk;
    private javax.swing.JPanel panelUserProfile1;
    private javax.swing.JPanel riwayatTransaksi;
    private javax.swing.JTable tblKartuStok;
    private javax.swing.JTable tblLaporanLaba;
    private javax.swing.JTable tblLaporanPengeluaran;
    private javax.swing.JTable tblMenu4;
    private javax.swing.JTable tblRiwayatTransaksi;
    private javax.swing.JComboBox<String> txtPilihBahan;
    // End of variables declaration//GEN-END:variables
}
