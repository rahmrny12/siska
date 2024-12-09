/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Component.BahanPopup;
import Helper.KartuStok;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class FormMasterData extends javax.swing.JFrame {

    private Connection conn;
    private HashMap<String, Object[]> dataStokBahan = new HashMap<>();
    
    public FormMasterData() {
        initComponents();
        
        conn = Helper.Database.OpenConnection();
        
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataMenu);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        panelContainerBahan.setVisible(false);
        
        loadTableMenu();
    }
    
    private void clearInputMenu() {
        txtIDMenu.setText("");
        txtNamaMenu.setText("");
        txtJenisMenu.setSelectedIndex(0);
        txtHargaMenu.setText("");
    }
    
    private void clearInputBahan() {
        txtIDBahan.setText("");
        txtNamaBahan.setText("");
        txtSatuan.setText("");
    }
    
    private void clearInputSupplier() {
        txtIDSupplier.setText("");
        txtNamaSupplier.setText("");
        txtAlamat.setText("");
        txtNoTelepon.setText("");
    }
    
    private void clearInputUser() {
        txtIDUser.setText("");
        txtNama.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtRole.setSelectedIndex(0);
    }
    
    private void loadTableMenu() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Menu");
        model.addColumn("Nama");
        model.addColumn("Jenis");
        model.addColumn("Harga");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM menu ORDER BY jenis, nama_menu";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                model.addRow(new Object[] {
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)
                });
            }
            tblMenu.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearInputMenu();
        }
    }
    
    private void loadTableBahan() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Bahan");
        model.addColumn("Nama");
        model.addColumn("Stok Bahan");
        model.addColumn("Satuan");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM bahan";
            Statement stm=conn.createStatement();
            ResultSet res=stm.executeQuery(query);
            while(res.next()){
                model.addRow(new Object[] {res.getString(1),
                    res.getString(2),res.getString(3),res.getString(4)});
                }
            tblBahan.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearInputBahan();
        }    
    }
    
    private void loadDataStokBahan() {
        dataStokBahan.clear();
        txtPilihBahan.removeAllItems();
        
        setupRemoveButtonColumn();
        
        try {
            String query = "SELECT * FROM bahan";
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                String idBahan = res.getString("id_bahan");
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
    
    private void loadTableSupplier() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Supplier");
        model.addColumn("Nama Supplier");
        model.addColumn("Alamat");
        model.addColumn("No Telepon");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM supplier";
            Statement stm=conn.createStatement();
            ResultSet res=stm.executeQuery(query);
            while(res.next()){
                model.addRow(new Object[] {res.getString(1),
                    res.getString(2),res.getString(3),res.getString(4)});
                }
            tblSupplier.setModel(model);
        } catch (Exception e) {            e.printStackTrace();        } finally {
            clearInputSupplier();
        }
    }
    
    private void loadTableUser() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID User");
        model.addColumn("Nama");
        model.addColumn("Username");
        model.addColumn("Email");
        model.addColumn("Password");
        model.addColumn("Role");

        //Menampilkan data kedalam tabel
        try {
            String query = "SELECT * FROM user";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                model.addRow(new Object[] {
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6)
                });
            }
            
            tblUser.setModel(model);
            
            tblUser.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (value != null) {
                        // Mengubah password menjadi bintang
                        StringBuilder password = new StringBuilder();
                        for (int i = 0; i < value.toString().length(); i++) {
                            password.append("*");
                        }
                        value = password.toString();
                    }
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearInputUser();
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
        btnToMenu = new javax.swing.JButton();
        btnToUser = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnToBahan = new javax.swing.JButton();
        btnToSupplier = new javax.swing.JButton();
        btnToStokOpname = new javax.swing.JButton();
        btnToStokMasuk = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        dataUser = new javax.swing.JPanel();
        txtIDUser = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        txtRole = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnTambahUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        btnHapusUser = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        dataMenu = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtIDMenu = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtNamaMenu = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtHargaMenu = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        txtJenisMenu = new javax.swing.JComboBox<>();
        btnTambahMenu = new javax.swing.JButton();
        btnEditMenu = new javax.swing.JButton();
        btnHapusMenu = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        panelContainerBahan = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        panelBahan = new javax.swing.JPanel();
        btnAddBahan = new javax.swing.JLabel();
        dataSupplier = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        txtIDSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        txtNoTelepon = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        btnTambahSupplier = new javax.swing.JButton();
        editSupplier = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txtAlamat = new javax.swing.JTextField();
        dataBahan = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBahan = new javax.swing.JTable();
        txtIDBahan = new javax.swing.JTextField();
        txtNamaBahan = new javax.swing.JTextField();
        txtSatuan = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        btnTambahBahan = new javax.swing.JButton();
        editBahan = new javax.swing.JButton();
        btnhHapusBahan = new javax.swing.JButton();
        dataStokOpname = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblStokOpname = new javax.swing.JTable();
        btnAddStokOpname = new javax.swing.JButton();
        txtPilihBahan = new javax.swing.JComboBox<>();
        txtStokFisik = new javax.swing.JSpinner();
        labelStokSistem = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        txtTanggalStopOpname = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        labelSatuanSistem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 500));
        setSize(new java.awt.Dimension(700, 500));

        bodyPanel.setBackground(new java.awt.Color(140, 2, 2));

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnToMenu.setBackground(new java.awt.Color(198, 40, 40));
        btnToMenu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnToMenu.setText("Data Menu");
        btnToMenu.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToMenuActionPerformed(evt);
            }
        });

        btnToUser.setBackground(new java.awt.Color(198, 40, 40));
        btnToUser.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToUser.setForeground(new java.awt.Color(255, 255, 255));
        btnToUser.setText("Data User");
        btnToUser.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToUserActionPerformed(evt);
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

        btnToBahan.setBackground(new java.awt.Color(198, 40, 40));
        btnToBahan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnToBahan.setText("Data Bahan");
        btnToBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToBahanActionPerformed(evt);
            }
        });

        btnToSupplier.setBackground(new java.awt.Color(198, 40, 40));
        btnToSupplier.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnToSupplier.setText("Supplier");
        btnToSupplier.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToSupplierActionPerformed(evt);
            }
        });

        btnToStokOpname.setBackground(new java.awt.Color(198, 40, 40));
        btnToStokOpname.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToStokOpname.setForeground(new java.awt.Color(255, 255, 255));
        btnToStokOpname.setText("Stok Opname");
        btnToStokOpname.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToStokOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToStokOpnameActionPerformed(evt);
            }
        });

        btnToStokMasuk.setBackground(new java.awt.Color(198, 40, 40));
        btnToStokMasuk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToStokMasuk.setForeground(new java.awt.Color(255, 255, 255));
        btnToStokMasuk.setText("Stok Masuk");
        btnToStokMasuk.setPreferredSize(new java.awt.Dimension(95, 21));
        btnToStokMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToStokMasukActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnToMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnToStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnToStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnToUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnToMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToUser, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(140, 2, 2));
        mainPanel.setLayout(new java.awt.CardLayout());

        dataUser.setBackground(new java.awt.Color(140, 2, 2));
        dataUser.setMinimumSize(new java.awt.Dimension(666, 360));

        txtIDUser.setForeground(new java.awt.Color(51, 51, 51));
        txtIDUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDUserActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("ID User");

        jLabel65.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Nama");

        txtNama.setForeground(new java.awt.Color(51, 51, 51));
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        jLabel66.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Email");

        txtEmail.setForeground(new java.awt.Color(51, 51, 51));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("Data User");

        jLabel67.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Username");

        txtUsername.setForeground(new java.awt.Color(51, 51, 51));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel68.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Password");

        jLabel70.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Role");

        txtRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "kasir", "admin", "owner" }));

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID User", "Nama User", "Email", "Username", "Role"
            }
        ));
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblUser);

        btnTambahUser.setText("TAMBAH");
        btnTambahUser.setPreferredSize(new java.awt.Dimension(65, 21));
        btnTambahUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahUserActionPerformed(evt);
            }
        });

        btnEditUser.setText("EDIT");
        btnEditUser.setPreferredSize(new java.awt.Dimension(65, 21));
        btnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserActionPerformed(evt);
            }
        });

        btnHapusUser.setText("HAPUS");
        btnHapusUser.setPreferredSize(new java.awt.Dimension(65, 21));
        btnHapusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusUserActionPerformed(evt);
            }
        });

        txtPassword.setText("jPasswordField1");

        javax.swing.GroupLayout dataUserLayout = new javax.swing.GroupLayout(dataUser);
        dataUser.setLayout(dataUserLayout);
        dataUserLayout.setHorizontalGroup(
            dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataUserLayout.createSequentialGroup()
                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dataUserLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dataUserLayout.createSequentialGroup()
                                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIDUser))
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmail))
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNama)))
                                .addGap(56, 56, 56)
                                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUsername))
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPassword))
                                    .addGroup(dataUserLayout.createSequentialGroup()
                                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(dataUserLayout.createSequentialGroup()
                                .addComponent(btnTambahUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapusUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(dataUserLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dataUserLayout.setVerticalGroup(
            dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataUserLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataUserLayout.createSequentialGroup()
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDUser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dataUserLayout.createSequentialGroup()
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnTambahUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        mainPanel.add(dataUser, "card3");

        dataMenu.setBackground(new java.awt.Color(144, 2, 2));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("ID Menu");

        txtIDMenu.setForeground(new java.awt.Color(153, 153, 153));
        txtIDMenu.setEnabled(false);
        txtIDMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDMenuActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Nama Menu");

        txtNamaMenu.setForeground(new java.awt.Color(51, 51, 51));
        txtNamaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaMenuActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Jenis Menu");

        txtHargaMenu.setForeground(new java.awt.Color(51, 51, 51));
        txtHargaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaMenuActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Harga Menu");

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Menu", "Nama Menu", "Jenis", "Harga"
            }
        ));
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblMenu);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Data Menu");

        txtJenisMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "makanan", "minuman", " " }));
        txtJenisMenu.setPreferredSize(new java.awt.Dimension(64, 22));

        btnTambahMenu.setText("TAMBAH");
        btnTambahMenu.setPreferredSize(new java.awt.Dimension(65, 21));
        btnTambahMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahMenuActionPerformed(evt);
            }
        });

        btnEditMenu.setText("EDIT");
        btnEditMenu.setPreferredSize(new java.awt.Dimension(65, 21));
        btnEditMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMenuActionPerformed(evt);
            }
        });

        btnHapusMenu.setText("HAPUS");
        btnHapusMenu.setPreferredSize(new java.awt.Dimension(65, 21));
        btnHapusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMenuActionPerformed(evt);
            }
        });

        btnBatal.setText("BATAL");
        btnBatal.setPreferredSize(new java.awt.Dimension(65, 21));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        panelContainerBahan.setBackground(new java.awt.Color(144, 2, 2));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Bahan");

        panelBahan.setBackground(new java.awt.Color(144, 2, 2));
        panelBahan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAddBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/Plus.png"))); // NOI18N
        btnAddBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddBahanMouseClicked(evt);
            }
        });
        panelBahan.add(btnAddBahan);

        javax.swing.GroupLayout panelContainerBahanLayout = new javax.swing.GroupLayout(panelContainerBahan);
        panelContainerBahan.setLayout(panelContainerBahanLayout);
        panelContainerBahanLayout.setHorizontalGroup(
            panelContainerBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerBahanLayout.createSequentialGroup()
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        panelContainerBahanLayout.setVerticalGroup(
            panelContainerBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerBahanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelContainerBahanLayout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout dataMenuLayout = new javax.swing.GroupLayout(dataMenu);
        dataMenu.setLayout(dataMenuLayout);
        dataMenuLayout.setHorizontalGroup(
            dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataMenuLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelContainerBahan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dataMenuLayout.createSequentialGroup()
                        .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dataMenuLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtJenisMenu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaMenu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDMenu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHargaMenu, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(dataMenuLayout.createSequentialGroup()
                        .addComponent(btnTambahMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(24, 24, 24))
            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dataMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEditMenu, btnHapusMenu, btnTambahMenu});

        dataMenuLayout.setVerticalGroup(
            dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataMenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataMenuLayout.createSequentialGroup()
                        .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dataMenuLayout.createSequentialGroup()
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dataMenuLayout.createSequentialGroup()
                                .addComponent(txtIDMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHargaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelContainerBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addGroup(dataMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnTambahMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(btnEditMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHapusMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );

        dataMenuLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEditMenu, btnHapusMenu, btnTambahMenu});

        mainPanel.add(dataMenu, "card2");

        dataSupplier.setBackground(new java.awt.Color(144, 2, 2));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Data Supplier");

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Menu", "Nama Menu", "Alamat", "No Telepon"
            }
        ));
        tblSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplierMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblSupplier);

        txtIDSupplier.setEnabled(false);
        txtIDSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDSupplierActionPerformed(evt);
            }
        });

        txtNamaSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupplierActionPerformed(evt);
            }
        });

        txtNoTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTeleponActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("ID Supplier");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Nama Supplier");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Alamat");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("No Telepon");

        btnTambahSupplier.setText("TAMBAH");
        btnTambahSupplier.setPreferredSize(new java.awt.Dimension(65, 21));
        btnTambahSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSupplierActionPerformed(evt);
            }
        });

        editSupplier.setText("EDIT");
        editSupplier.setPreferredSize(new java.awt.Dimension(65, 21));
        editSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSupplierActionPerformed(evt);
            }
        });

        jButton8.setText("HAPUS");
        jButton8.setPreferredSize(new java.awt.Dimension(65, 21));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataSupplierLayout = new javax.swing.GroupLayout(dataSupplier);
        dataSupplier.setLayout(dataSupplierLayout);
        dataSupplierLayout.setHorizontalGroup(
            dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataSupplierLayout.createSequentialGroup()
                .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dataSupplierLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dataSupplierLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dataSupplierLayout.createSequentialGroup()
                                .addComponent(btnTambahSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dataSupplierLayout.createSequentialGroup()
                                .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(dataSupplierLayout.createSequentialGroup()
                                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(dataSupplierLayout.createSequentialGroup()
                                        .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(24, 24, 24)))
                                .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtIDSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
        );
        dataSupplierLayout.setVerticalGroup(
            dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSupplierLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSupplierLayout.createSequentialGroup()
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(dataSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(btnTambahSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(editSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );

        mainPanel.add(dataSupplier, "card4");

        dataBahan.setBackground(new java.awt.Color(144, 2, 2));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Data Bahan");

        tblBahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Menu", "Nama Menu", "Stok bahan", "Satuan"
            }
        ));
        tblBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBahanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBahan);

        txtIDBahan.setEnabled(false);
        txtIDBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDBahanActionPerformed(evt);
            }
        });

        txtNamaBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBahanActionPerformed(evt);
            }
        });

        txtSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSatuanActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("ID Bahan");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Nama Bahan");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Satuan");

        btnTambahBahan.setText("TAMBAH");
        btnTambahBahan.setPreferredSize(new java.awt.Dimension(65, 21));
        btnTambahBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBahanActionPerformed(evt);
            }
        });

        editBahan.setText("EDIT");
        editBahan.setPreferredSize(new java.awt.Dimension(65, 21));
        editBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBahanActionPerformed(evt);
            }
        });

        btnhHapusBahan.setText("HAPUS");
        btnhHapusBahan.setPreferredSize(new java.awt.Dimension(65, 21));
        btnhHapusBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhHapusBahanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataBahanLayout = new javax.swing.GroupLayout(dataBahan);
        dataBahan.setLayout(dataBahanLayout);
        dataBahanLayout.setHorizontalGroup(
            dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBahanLayout.createSequentialGroup()
                .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dataBahanLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dataBahanLayout.createSequentialGroup()
                                .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIDBahan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(dataBahanLayout.createSequentialGroup()
                                .addComponent(btnTambahBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnhHapusBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(dataBahanLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dataBahanLayout.setVerticalGroup(
            dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBahanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataBahanLayout.createSequentialGroup()
                        .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(dataBahanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(btnTambahBahan, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                            .addComponent(editBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnhHapusBahan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        mainPanel.add(dataBahan, "card4");

        dataStokOpname.setBackground(new java.awt.Color(140, 2, 2));
        dataStokOpname.setMinimumSize(new java.awt.Dimension(666, 360));

        jLabel71.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Bahan:");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Data Stok Opname");

        jLabel75.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Stok Fisik:");

        tblStokOpname.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Bahan", "Nama Bahan", "Stok Sistem", "Stok Fisik", "Selisih", "Aksi"
            }
        ));
        tblStokOpname.setRowHeight(40);
        tblStokOpname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStokOpnameMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblStokOpname);

        btnAddStokOpname.setText("TAMBAH");
        btnAddStokOpname.setPreferredSize(new java.awt.Dimension(65, 21));
        btnAddStokOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStokOpnameActionPerformed(evt);
            }
        });

        txtPilihBahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtPilihBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPilihBahanActionPerformed(evt);
            }
        });

        labelStokSistem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        labelStokSistem.setForeground(new java.awt.Color(255, 255, 255));
        labelStokSistem.setText("Stok Sistem: -");

        jLabel76.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Tanggal");

        txtTanggalStopOpname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTanggalStopOpname.setEnabled(false);

        btnSubmit.setText("SUBMIT");
        btnSubmit.setPreferredSize(new java.awt.Dimension(65, 21));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        labelSatuanSistem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        labelSatuanSistem.setForeground(new java.awt.Color(255, 255, 255));
        labelSatuanSistem.setText("Satuan: -");

        javax.swing.GroupLayout dataStokOpnameLayout = new javax.swing.GroupLayout(dataStokOpname);
        dataStokOpname.setLayout(dataStokOpnameLayout);
        dataStokOpnameLayout.setHorizontalGroup(
            dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataStokOpnameLayout.createSequentialGroup()
                .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dataStokOpnameLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dataStokOpnameLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                            .addComponent(jLabel71)
                            .addGroup(dataStokOpnameLayout.createSequentialGroup()
                                .addComponent(jLabel75)
                                .addGap(18, 18, 18)
                                .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddStokOpname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataStokOpnameLayout.createSequentialGroup()
                                        .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtStokFisik)
                                            .addComponent(txtPilihBahan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelStokSistem)
                                            .addGroup(dataStokOpnameLayout.createSequentialGroup()
                                                .addComponent(labelSatuanSistem)
                                                .addGap(54, 54, 54)
                                                .addComponent(jLabel76)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTanggalStopOpname)))))))))
                .addGap(24, 24, 24))
        );
        dataStokOpnameLayout.setVerticalGroup(
            dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataStokOpnameLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPilihBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelStokSistem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dataStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStokFisik, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggalStopOpname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSatuanSistem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        mainPanel.add(dataStokOpname, "card3");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
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

    private void txtIDMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDMenuActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtIDMenuActionPerformed

    private void txtNamaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaMenuActionPerformed

    private void txtHargaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaMenuActionPerformed

    private void btnToUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToUserActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataUser);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadTableUser();
    }//GEN-LAST:event_btnToUserActionPerformed

    private void btnToMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToMenuActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataMenu);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadTableMenu();
    }//GEN-LAST:event_btnToMenuActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        dispose(); // this will close current login box window

        //this will open a nextpage window.
        FormLogin page = new FormLogin();
        page.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnToBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToBahanActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataBahan);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadTableBahan();
    }//GEN-LAST:event_btnToBahanActionPerformed

    private void btnTambahMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMenuActionPerformed
        // Mengambil data input      
        String namaMenu = txtNamaMenu.getText();
        String jenisMenu = (String) txtJenisMenu.getSelectedItem();
        String hargaMenu = txtHargaMenu.getText();
        
        // Melakukan validasi input
        if (namaMenu.isEmpty() || jenisMenu == null || hargaMenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            Integer.valueOf(hargaMenu);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kolom harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        PreparedStatement stmt;
        
        try {
            // Membuat query mysql
            String query = "INSERT INTO `menu`(`nama_menu`, `jenis`, `harga`) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaMenu);
            stmt.setString(2, jenisMenu);
            stmt.setString(3, hargaMenu);
            stmt.execute();
        
            // refresh tabel menu   
            loadTableMenu();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahMenuActionPerformed

    private void btnEditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMenuActionPerformed
        // Mengambil data input
        String IDMenu = txtIDMenu.getText();
        String namaMenu = txtNamaMenu.getText();
        String jenisMenu = (String) txtJenisMenu.getSelectedItem();
        String hargaMenu = txtHargaMenu.getText();

        // Melakukan validasi input
        if (IDMenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (namaMenu.isEmpty() || jenisMenu == null || hargaMenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            Integer.valueOf(hargaMenu);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kolom harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "UPDATE `menu` SET `nama_menu` = ?, `jenis` = ?, `harga` = ? WHERE `id_menu` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaMenu);
            stmt.setString(2, jenisMenu);
            stmt.setString(3, hargaMenu);
            stmt.setInt(4, Integer.parseInt(IDMenu));
            stmt.executeUpdate();
        
            loadTableMenu();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditMenuActionPerformed

    private void btnHapusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMenuActionPerformed
        String IDMenu = txtIDMenu.getText();
        
        if (IDMenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Konfirmasi sebelum menghapus
        int confirmation = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus menu tersebut?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.NO_OPTION) {
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            String query = "DELETE FROM `menu` WHERE `id_menu` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(IDMenu));
            stmt.executeUpdate();
            
            loadTableMenu();
        } catch (SQLException ex) {}
    }//GEN-LAST:event_btnHapusMenuActionPerformed

    private void tblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMouseClicked
        int baris = tblMenu.rowAtPoint(evt.getPoint());
        String idMenu = tblMenu.getValueAt(baris, 0).toString();
        txtIDMenu.setText(idMenu);
        
        txtNamaMenu.setText((String) tblMenu.getValueAt(baris, 1));
        txtJenisMenu.setSelectedItem((String) tblMenu.getValueAt(baris, 2));
        txtHargaMenu.setText((String) tblMenu.getValueAt(baris, 3));
        
        loadBahanMenu();
        
        panelContainerBahan.setVisible(true);
    }//GEN-LAST:event_tblMenuMouseClicked

    private void tblBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBahanMouseClicked
            int baris = tblBahan.rowAtPoint(evt.getPoint());
        String IDBahan = tblBahan.getValueAt(baris, 0).toString();
        txtIDBahan.setText(IDBahan);
        
        txtNamaBahan.setText((String) tblBahan.getValueAt(baris, 1));
        txtSatuan.setText((String) tblBahan.getValueAt(baris, 3));
    }//GEN-LAST:event_tblBahanMouseClicked

    private void txtIDBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDBahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDBahanActionPerformed

    private void txtNamaBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBahanActionPerformed

    private void txtSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanActionPerformed

    private void btnTambahBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBahanActionPerformed
        String namaBahan = txtNamaBahan.getText();
        String satuan = txtSatuan.getText();
        
        // Melakukan validasi input
        if (namaBahan.isEmpty() || satuan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "INSERT INTO `bahan`(`nama_bahan`, `satuan`) VALUES (?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaBahan);
            stmt.setString(2, satuan);
            stmt.execute();
            
            loadTableBahan();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_btnTambahBahanActionPerformed

    private void editBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBahanActionPerformed
     // Mengambil data input
        String IDBahan = txtIDBahan.getText();
        String namaBahan = txtNamaBahan.getText();
        String satuan = txtSatuan.getText();

        // Melakukan validasi input
        if (IDBahan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih Bahan yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (namaBahan.isEmpty() || satuan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "UPDATE `bahan` SET `nama_bahan` = ?, `satuan` = ? WHERE `id_bahan` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaBahan);
            stmt.setString(2, satuan);
            stmt.setInt(3, Integer.parseInt(IDBahan));
            stmt.executeUpdate();
        
            loadTableBahan();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }//GEN-LAST:event_editBahanActionPerformed

    private void btnhHapusBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhHapusBahanActionPerformed
        String IDBahan = txtIDBahan.getText();
        
        if (IDBahan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih Bahan yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Konfirmasi sebelum menghapus
        int confirmation = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus Bahan tersebut?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.NO_OPTION) {
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            String query = "DELETE FROM `Bahan` WHERE `id_bahan` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(IDBahan));
            stmt.executeUpdate();
            
            loadTableBahan();
        } catch (SQLException ex) {}
    }//GEN-LAST:event_btnhHapusBahanActionPerformed

    private void btnToSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToSupplierActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataSupplier);
        mainPanel.repaint();
        mainPanel.revalidate();
       
        loadTableSupplier();
    }//GEN-LAST:event_btnToSupplierActionPerformed

    private void btnToStokOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToStokOpnameActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataStokOpname);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        loadDataStokBahan();
    }//GEN-LAST:event_btnToStokOpnameActionPerformed

    private void tblSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplierMouseClicked
            int baris = tblSupplier.rowAtPoint(evt.getPoint());
        String IDSupplier = tblSupplier.getValueAt(baris, 0).toString();
        txtIDSupplier.setText(IDSupplier);
        
        txtNamaSupplier.setText((String) tblSupplier.getValueAt(baris, 1));
        txtAlamat.setText((String) tblSupplier.getValueAt(baris, 2));
        txtNoTelepon.setText((String) tblSupplier.getValueAt(baris, 3));
    }//GEN-LAST:event_tblSupplierMouseClicked

    private void txtIDSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDSupplierActionPerformed

    private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierActionPerformed

    private void txtNoTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTeleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTeleponActionPerformed

    private void btnTambahSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSupplierActionPerformed
        String namaSupplier = txtNamaSupplier.getText();
        String alamat = txtAlamat.getText();
        String noTelepon = txtNoTelepon.getText();
        
        // Melakukan validasi input        
        if (namaSupplier.isEmpty() || alamat.isEmpty() || noTelepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            Integer.valueOf(noTelepon);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kolom nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "INSERT INTO `supplier`(`nama_supplier`, `alamat`, `no_telp`) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaSupplier);
            stmt.setString(2, alamat);
            stmt.setString(3, noTelepon);
            stmt.execute();
            
            loadTableSupplier();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahSupplierActionPerformed

    private void editSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSupplierActionPerformed
        // Mengambil data input
        String IDSupplier = txtIDSupplier.getText();
        String namaSupplier = txtNamaSupplier.getText();
        String alamat =  txtAlamat.getText();
        String noTelepon = txtNoTelepon.getText();

        // Melakukan validasi input
        if (IDSupplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (namaSupplier.isEmpty() || alamat == null || noTelepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            Integer.valueOf(noTelepon);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kolom nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "UPDATE `supplier` SET `nama_supplier` = ?, `alamat` = ?, `no_telp` = ? WHERE `id_supplier` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, namaSupplier);
            stmt.setString(2, alamat);
            stmt.setString(3, noTelepon);
            stmt.setInt(4, Integer.parseInt(IDSupplier));
            stmt.executeUpdate();
        
            loadTableSupplier();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_editSupplierActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String IDSupplier = txtIDSupplier.getText();
        
        if (IDSupplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Konfirmasi sebelum menghapus
        int confirmation = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus menu tersebut?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.NO_OPTION) {
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            String query = "DELETE FROM `supplier` WHERE `id_supplier` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(IDSupplier));
            stmt.executeUpdate();
            
            loadTableSupplier();
        } catch (SQLException ex) {}
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void btnHapusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusUserActionPerformed
        String IDUser = txtIDUser.getText();
        
        if (IDUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Konfirmasi sebelum menghapus
        int confirmation = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus user tersebut?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.NO_OPTION) {
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            String query = "DELETE FROM `user` WHERE `id_user` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(IDUser));
            stmt.executeUpdate();
            
            loadTableUser();
        } catch (SQLException ex) {}
    }//GEN-LAST:event_btnHapusUserActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        // Mengambil data input
        String IDUser = txtIDUser.getText();
        String nama = txtNama.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = (String) txtRole.getSelectedItem();

        // Melakukan validasi input
        if (IDUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (nama.isEmpty() ||
            username.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            // SQL query to check credentials
            String query = "UPDATE `user` SET `nama` = ?, `username` = ?, `email` = ?, `password` = ?, `role` = ? WHERE `id_user` = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.setString(6, IDUser);
            stmt.executeUpdate();
        
            loadTableUser();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnTambahUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahUserActionPerformed
        // Mengambil data input      
        String nama = txtNama.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = (String) txtRole.getSelectedItem();
        
        // Melakukan validasi input
        if (nama.isEmpty() ||
            username.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PreparedStatement stmt;
        
        try {
            // Membuat query mysql
            String query = "INSERT INTO `user`(`nama`, `username`, `email`, `password`, `role`) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.execute();
        
            // refresh tabel menu   
            loadTableUser();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahUserActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        int baris = tblUser.rowAtPoint(evt.getPoint());
        txtIDUser.setText((String) tblUser.getValueAt(baris, 0));
        txtNama.setText((String) tblUser.getValueAt(baris, 1));
        txtUsername.setText((String) tblUser.getValueAt(baris, 2));
        txtEmail.setText((String) tblUser.getValueAt(baris, 3));
        txtPassword.setText((String) tblUser.getValueAt(baris, 4));
        txtRole.setSelectedItem((String) tblUser.getValueAt(baris, 5));
    }//GEN-LAST:event_tblUserMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtIDUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDUserActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearInputMenu();
        panelContainerBahan.setVisible(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnAddBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddBahanMouseClicked
        showAddBahanPopup();
    }//GEN-LAST:event_btnAddBahanMouseClicked

    private void btnToStokMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToStokMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnToStokMasukActionPerformed

    private void tblStokOpnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStokOpnameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStokOpnameMouseClicked

    private void setupRemoveButtonColumn() {
        DefaultTableModel model = (DefaultTableModel) new DefaultTableModel();
        model.addColumn("ID Bahan");
        model.addColumn("Nama Bahan");
        model.addColumn("Stok Sistem");
        model.addColumn("Stok Fisik");
        model.addColumn("Selisih");
        model.addColumn("Aksi");
        
        tblStokOpname.setModel(model);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        // Set the date in the text field
        txtTanggalStopOpname.setText(currentDate);
        
        TableColumn column = tblStokOpname.getColumnModel().getColumn(5);
        column.setCellRenderer(new ButtonRenderer());
        column.setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setMargin(new Insets(5, 10, 5, 10));
            setPreferredSize(new Dimension(100, 30));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Batal");
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = "Batal";
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Remove row
                DefaultTableModel model = (DefaultTableModel) tblStokOpname.getModel();
                int rowToRemove = tblStokOpname.getSelectedRow();
                
                if (rowToRemove != -1 && model.getRowCount() > 0) {
                    model.removeRow(rowToRemove);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    private void btnAddStokOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStokOpnameActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblStokOpname.getModel();
        String namaBahan = (String) txtPilihBahan.getSelectedItem();
        int stokFisik = (int) txtStokFisik.getValue();

        // Mendapatkan data dari HashMap
        Object[] data = dataStokBahan.get(namaBahan);
        String idBahan = (String) data[0]; // ID bahan
        int stokSistem = (int) data[1]; // Stok sistem
        int selisih = stokFisik - stokSistem;

        // Menambahkan data ke tabel
        model.addRow(new Object[]{
                idBahan,
                namaBahan,
                stokSistem,
                stokFisik,
                selisih
        });

        // Reset spinner
        txtStokFisik.setValue(0);
    }//GEN-LAST:event_btnAddStokOpnameActionPerformed

    private void txtPilihBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPilihBahanActionPerformed
        String selectedBahan = (String) txtPilihBahan.getSelectedItem();

        if (selectedBahan != null && dataStokBahan.containsKey(selectedBahan)) {
            // Ambil stok sistem dari HashMap
            Object[] bahanData = dataStokBahan.get(selectedBahan);
            int stokSistem = (int) bahanData[1];
            String satuanSistem = (String) bahanData[2];
            
            labelStokSistem.setText("Stok Sistem: " + String.valueOf(stokSistem));
            labelSatuanSistem.setText("Satuan Sistem: " + satuanSistem);
        }
    }//GEN-LAST:event_txtPilihBahanActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblStokOpname.getModel();
    
        // Assuming 'id_user' is temporarily set to null
        String idUser = null; // Temporarily null or set it as necessary
        String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); // Today's date

        // Start transaction
        try {
            conn.setAutoCommit(false); // Disable auto-commit to begin the transaction

            // Step 1: Insert into the stok_opname table
            String insertOpnameQuery = "INSERT INTO stok_opname (tanggal, id_user, waktu) VALUES (?, ?, ?)";
            try (PreparedStatement stmtOpname = conn.prepareStatement(insertOpnameQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmtOpname.setString(1, tanggal);
                stmtOpname.setString(2, idUser); // idUser is null for now
                java.sql.Time currentTime = new java.sql.Time(System.currentTimeMillis());
                stmtOpname.setTime(3, currentTime);

                // Execute the query and get the generated id_opname
                int rowsAffected = stmtOpname.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = stmtOpname.getGeneratedKeys();
                    if (rs.next()) {
                        int idOpname = rs.getInt(1); // This is the id_opname we just inserted

                        // Step 2: Insert each detail into stok_opname_detail
                        String insertDetailQuery = "INSERT INTO stok_opname_detail (id_opname, id_bahan, stok_awal, stok_akhir, selisih) VALUES (?, ?, ?, ?, ?)";
                        String updateBahanQuery = "UPDATE bahan SET stok_bahan = stok_bahan + ? WHERE id_bahan = ?";
                        try (PreparedStatement stmtDetail = conn.prepareStatement(insertDetailQuery);
                                PreparedStatement stmtUpdateBahan = conn.prepareStatement(updateBahanQuery)) {
                            
                            for (int row = 0; row < model.getRowCount(); row++) {
                                int idBahan = Integer.parseInt(model.getValueAt(row, 0).toString()); // Assuming ID is in column 0
                                int stokAwal = Integer.parseInt(model.getValueAt(row, 2).toString()); // Assuming Stok Sistem is in column 2
                                int stokAkhir = Integer.parseInt(model.getValueAt(row, 3).toString()); // Assuming Stok Fisik is in column 3
                                int selisih = stokAkhir - stokAwal; // Calculate selisih

                                // Set values for the stok_opname_detail table
                                stmtDetail.setInt(1, idOpname);   // Use the id_opname generated earlier
                                stmtDetail.setInt(2, idBahan);
                                stmtDetail.setInt(3, stokAwal);
                                stmtDetail.setInt(4, stokAkhir);
                                stmtDetail.setInt(5, selisih);

                                // Execute the insert query for the detail record
                                stmtDetail.executeUpdate();
                                
                                stmtUpdateBahan.setInt(1, selisih);  // Add or subtract the selisih value
                                stmtUpdateBahan.setInt(2, idBahan);  // ID of the bahan being updated

                                // Execute the update query for the bahan table
                                stmtUpdateBahan.executeUpdate();
                                
                                // Kartu stok
                                String keterangan = "Stok Opname (Selisih)";
                                int jumlahMasuk = (selisih > 0) ? selisih : 0;
                                int jumlahKeluar = (selisih < 0) ? Math.abs(selisih) : 0;

                                KartuStok.insertKartuStok(conn, idBahan, keterangan, jumlahMasuk, jumlahKeluar, "stok_opname");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            conn.rollback(); // Rollback if any detail insertion fails
                            return;
                        }
                    }
                } else {
                    throw new SQLException("Failed to insert into stok_opname");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback(); // Rollback if any error occurs during opname insertion
                return;
            }

            // Commit the transaction after successful inserts
            conn.commit();

            // Step 3: Clear all rows from the table after insertion
            model.setRowCount(0); // Clears all rows from the table

            // Optionally, show a message when done
            JOptionPane.showMessageDialog(null, "Berhasil melakukan stok opname.");

        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback transaction in case of any error
            } catch (SQLException ex) {}
        } finally {
            try {
                conn.setAutoCommit(true); // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
        
    private void loadBahanMenu() {
        int IDMenu = Integer.parseInt(txtIDMenu.getText());
        
        panelBahan.removeAll();

        try {
            String query = "SELECT bm.id_bahan, b.nama_bahan FROM bahan_menu bm " +
                           "JOIN bahan b ON bm.id_bahan = b.id_bahan " +
                           "WHERE bm.id_menu = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, IDMenu); // Replace with the selected menu ID
            ResultSet res = stmt.executeQuery();

            // menampilkan icon plus
            JLabel btnAddBahan = new JLabel();
            btnAddBahan.setIcon(new ImageIcon(getClass().getResource("/Media/Plus.png")));
            btnAddBahan.setHorizontalTextPosition(SwingConstants.CENTER);
            btnAddBahan.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnAddBahan.setPreferredSize(new Dimension(32, 32));
            btnAddBahan.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showAddBahanPopup();
                }
            });
            
            ButtonGroup removeButtonGroup = new ButtonGroup();
            
            while (res.next()) {
                String namaBahan = res.getString("nama_bahan");
                int IDBahan = res.getInt("id_bahan");
                
                // Create a badge for each bahan
                JLabel badge = new JLabel(namaBahan);
                badge.setOpaque(true);
                badge.setBackground(Color.WHITE);
                badge.setForeground(Color.RED);
                badge.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                badge.setFont(new Font("Arial", Font.PLAIN, 12));
                badge.setHorizontalAlignment(SwingConstants.CENTER);
                badge.setPreferredSize(new Dimension(80, 20));

                // Create a remove button with an "X" symbol
                JToggleButton btnRemove = new JToggleButton("X"); // Using JToggleButton for the "grouping"
                btnRemove.setPreferredSize(new Dimension(25, 25)); // Set the size of the remove button
                btnRemove.setFont(new Font("Arial", Font.BOLD, 12));
                btnRemove.setForeground(Color.WHITE);
                btnRemove.setBackground(Color.RED);
                btnRemove.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                btnRemove.setFocusPainted(false);
                btnRemove.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int confirmation = JOptionPane.showConfirmDialog(
                            null, 
                            "Apakah Anda yakin ingin menghapus bahan tersebut?", 
                            "Konfirmasi Hapus", 
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirmation == JOptionPane.YES_OPTION) {
                            PreparedStatement stmt = null;

                            try {
                                int IDMenu = Integer.parseInt(txtIDMenu.getText());
                                
                                String query = "DELETE FROM `bahan_menu` WHERE `id_menu` = ? AND `id_bahan` = ?";
                                stmt = conn.prepareStatement(query);
                                stmt.setInt(1, IDMenu);
                                stmt.setInt(2, IDBahan);
                                stmt.executeUpdate();

                                loadBahanMenu();
                                JOptionPane.showMessageDialog(btnAddBahan.getParent(), "Data berhasil ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(btnAddBahan.getParent(), "Gagal mengubah data. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            } finally {
                                // Ensure the statement is closed
                                if (stmt != null) {
                                    try {
                                        stmt.close();
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                });

                // Add remove button to the ButtonGroup
                removeButtonGroup.add(btnRemove);
                
                JPanel badgePanel = new JPanel();
                badgePanel.setLayout(new BorderLayout());
                badgePanel.add(badge, BorderLayout.CENTER); // Add badge to center
                badgePanel.add(btnRemove, BorderLayout.EAST); // Add "X" button to the right side of the badge


                // Add badge to the panel
                panelBahan.add(badgePanel);
            }
            
            panelBahan.add(btnAddBahan);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            panelBahan.revalidate(); // Refresh the panel to show the badges
            panelBahan.repaint();
        }
    }
    
    private void showAddBahanPopup() {
        // Open the BahanPopup form
        BahanPopup addBahanPopup = new BahanPopup();
        addBahanPopup.setVisible(true);

        // Set the action listener for the popup
        addBahanPopup.setAddBahanActionListener((String IDBahan, int jumlahBahan) -> {
            String IDMenu = txtIDMenu.getText(); // Get IDMenu from the JTextField

            if (IDMenu.isEmpty()) {
                JOptionPane.showMessageDialog(btnAddBahan.getParent(), "Pilih menu yang ingin diubah.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PreparedStatement stmt = null;

            try {
                // SQL query to insert data into bahan_menu
                String query = "INSERT INTO `bahan_menu`(`id_menu`, `id_bahan`, `jumlah_bahan`) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, IDMenu);
                stmt.setString(2, IDBahan);
                stmt.setInt(3, jumlahBahan);
                stmt.execute();

                // Refresh the table to reflect the new data
                loadBahanMenu();
                JOptionPane.showMessageDialog(btnAddBahan.getParent(), "Data berhasil ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(btnAddBahan.getParent(), "Gagal menambahkan data. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Ensure the statement is closed
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
                
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
            java.util.logging.Logger.getLogger(FormMasterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMasterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMasterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMasterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMasterData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel btnAddBahan;
    private javax.swing.JButton btnAddStokOpname;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEditMenu;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnHapusMenu;
    private javax.swing.JButton btnHapusUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnTambahBahan;
    private javax.swing.JButton btnTambahMenu;
    private javax.swing.JButton btnTambahSupplier;
    private javax.swing.JButton btnTambahUser;
    private javax.swing.JButton btnToBahan;
    private javax.swing.JButton btnToMenu;
    private javax.swing.JButton btnToStokMasuk;
    private javax.swing.JButton btnToStokOpname;
    private javax.swing.JButton btnToSupplier;
    private javax.swing.JButton btnToUser;
    private javax.swing.JButton btnhHapusBahan;
    private javax.swing.JPanel dataBahan;
    private javax.swing.JPanel dataMenu;
    private javax.swing.JPanel dataStokOpname;
    private javax.swing.JPanel dataSupplier;
    private javax.swing.JPanel dataUser;
    private javax.swing.JButton editBahan;
    private javax.swing.JButton editSupplier;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelSatuanSistem;
    private javax.swing.JLabel labelStokSistem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel panelBahan;
    private javax.swing.JPanel panelContainerBahan;
    private javax.swing.JTable tblBahan;
    private javax.swing.JTable tblMenu;
    private javax.swing.JTable tblStokOpname;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHargaMenu;
    private javax.swing.JTextField txtIDBahan;
    private javax.swing.JTextField txtIDMenu;
    private javax.swing.JTextField txtIDSupplier;
    private javax.swing.JTextField txtIDUser;
    private javax.swing.JComboBox<String> txtJenisMenu;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNamaBahan;
    private javax.swing.JTextField txtNamaMenu;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JComboBox<String> txtPilihBahan;
    private javax.swing.JComboBox<String> txtRole;
    private javax.swing.JTextField txtSatuan;
    private javax.swing.JSpinner txtStokFisik;
    private javax.swing.JTextField txtTanggalStopOpname;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
