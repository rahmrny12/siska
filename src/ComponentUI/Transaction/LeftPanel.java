package ComponentUI.Transaction;

import ComponentUI.MessageDialog;
import ComponentUI.ToppingPopup;
import ComponentUI.Transaction.PaymentPopup.PaymentListener;
import Helper.KartuStok;
import Model.Bahan;
import Model.OrderItem;
import Model.Topping;
import View.FormTransaksi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class LeftPanel extends JPanel {

    private Connection conn;
    
    private JPanel orderSummaryPanel;
    private JLabel totalLabel;
    private JTextField txtName;
    private JTextField txtPhone;
    
    private FormTransaksi formTransaksi;
    private List<OrderItem> orderItems = new ArrayList<>();

    private int totalHarga = 0;
    private Integer IDPelanggan = null;
    
    Map<String, Integer> dataTopping = new LinkedHashMap<>();
    Map<String, Integer> dataLevel = new LinkedHashMap<>();
    
    public LeftPanel() {
        conn = Helper.Database.OpenConnection();
        
        dataTopping.put("Dumpling - Rp. 1000", 1000);
        dataTopping.put("Telur - Rp. 3000", 3000);
        dataTopping.put("Cuanki - Rp. 2000", 2000);
        
        dataLevel.put("None", 0);
        dataLevel.put("Level 1", 0);
        dataLevel.put("Level 2", 0);
        dataLevel.put("Level 3", 0);
        dataLevel.put("Level 4", 1000);
        dataLevel.put("Level 5", 1000);
        dataLevel.put("Level 6", 1000);
        
        orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS)); // Vertical layout
        orderSummaryPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Wrap orderSummaryPanel in a JScrollPane
        JScrollPane orderScrollPane = new JScrollPane(orderSummaryPanel);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScrollPane.getViewport().setAlignmentY(Component.TOP_ALIGNMENT);

        // Total Label
        totalLabel = new JLabel("Total: Rp. 0");
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set layout for the main panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tambahkan Pesanan"));
        setPreferredSize(new Dimension(300, 0));

        // Bottom Panel (Total and Pay Button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton payButton = new JButton("Bayar");
        payButton.setBackground(Color.RED);
        payButton.setForeground(Color.WHITE);
                
        payButton.addActionListener((ActionEvent e) -> {
            PaymentPopup paymentPopup = new PaymentPopup();
            paymentPopup.setTotalHarga(totalHarga);
            paymentPopup.setPaymentActionListener((int totalPembayaran, int totalKembalian) -> {
                // Create pelanggan jika belum ada
                String namaPelanggan;
                
                if (IDPelanggan == null) {
                    // Check if one of the fields is empty but not both
                    if ((txtName.getText().isEmpty() || txtPhone.getText().isEmpty()) 
                        && !(txtName.getText().isEmpty() && txtPhone.getText().isEmpty())) {
                        // Show warning dialog if one field is incomplete
                        String[] buttonLabels = {"OK"};

                        // Create a new instance of CustomDialog
                        MessageDialog dialog = new MessageDialog(
                            "Error",
                            "Kolom pelanggan tidak lengkap.",
                            buttonLabels,
                            null // Pass null for default behavior (close dialog)
                        );

                        // Show the dialog
                        dialog.showDialog();
                        return; // Stop further execution
                    }

                    // If both fields are empty, do nothing and keep IDPelanggan as null
                    if (!txtName.getText().isEmpty() && !txtPhone.getText().isEmpty()) {
                        createNewPelanggan();
                    }
                }
                
                namaPelanggan = txtName.getText();
                
                // Update FormTransaksi with payment data
                formTransaksi.submitTransaction(orderItems, totalPembayaran, totalKembalian, IDPelanggan, namaPelanggan);
                
                paymentPopup.dispose();
                orderItems.clear();
                orderSummaryPanel.removeAll();
                orderSummaryPanel.revalidate();
                orderSummaryPanel.repaint();
                IDPelanggan = null;
                txtName.setText("");
                txtPhone.setText("");
                
                totalLabel.setText("Total: Rp. 0");
            });
            paymentPopup.setVisible(true);
        });
        
        // Panel for text fields
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns, 5px gap
        customerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // "Nama Pelanggan" field
        JLabel nameLabel = new JLabel("Nama Pelanggan:");
        txtName = new JTextField();

        // "Nomor Telepon" field
        JLabel phoneLabel = new JLabel("Nomor Telepon:");
        txtPhone = new JTextField();
        txtPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String nomorTelepon = txtPhone.getText();
                if (!nomorTelepon.isEmpty()) {
                    // Perform search query
                    PreparedStatement stmt;
                    
                    try {
                        String query = "SELECT * FROM pelanggan WHERE no_telp = ?";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, nomorTelepon);

                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            // Populate Nama Pelanggan field
                            IDPelanggan = rs.getInt("id_pelanggan");
                            String namaPelanggan = rs.getString("nama");
                            txtName.setText(namaPelanggan);
                            txtName.setEnabled(false);
                        } else {
                            // Clear the name field if no match is found
                            IDPelanggan = null;
                            txtName.setEnabled(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Clear the name field if phone field is empty
                    txtName.setText("");
                }
            }
        });

        
        customerPanel.add(phoneLabel);
        customerPanel.add(txtPhone);
        customerPanel.add(nameLabel);
        customerPanel.add(txtName);

        // Add the textFieldPanel to the top of the bottomPanel
        bottomPanel.add(customerPanel, BorderLayout.NORTH);
        
        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(payButton, BorderLayout.SOUTH);

        // Add components to this panel
        add(orderScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void addOrderItem(int id, String nama, double harga, String jenis) {
        OrderItem newItem = new OrderItem(id, nama, harga, jenis);

        // Check if an item with the same id and jenis exists
        boolean itemExists = false;
        for (OrderItem existingItem : orderItems) {
            if (existingItem.getId() == id && jenis.equals("minuman")) {
                // Update quantity for existing item
                existingItem.setKuantitas(existingItem.getKuantitas() + 1);
                itemExists = true;
                break;
            }
        }

        // If the item doesn't exist, add it as a new item
        if (!itemExists) {
            orderItems.add(newItem);
        }

        // Update the UI to reflect changes
        refreshOrderSummaryPanel();
        updateTotal();
    }
    
    private void refreshOrderSummaryPanel() {
        orderSummaryPanel.removeAll();
        
        orderItems.sort(Comparator.comparing(item -> item.getJenis().equalsIgnoreCase("makanan") ? 1 : 2));

        for (OrderItem item : orderItems) {
            JPanel orderItemPanel = new JPanel();
            orderItemPanel.setLayout(new BoxLayout(orderItemPanel, BoxLayout.Y_AXIS));

            // First Panel (Name, Price, Quantity)
            JPanel firstPanel = new JPanel();
            firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.X_AXIS));
            firstPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel nameLabel = new JLabel(item.getNama() + " (Rp. " + item.getHarga() + ")");
            nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            firstPanel.add(nameLabel);

            firstPanel.add(Box.createHorizontalGlue());

            JLabel quantityLabel = new JLabel(item.getKuantitas() + "x");
            quantityLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
            firstPanel.add(quantityLabel);
            
            orderItemPanel.add(firstPanel);

            if (item.getJenis().equals("makanan")) {
                // Second Panel (Topping, Level)
                JPanel secondPanel = new JPanel();
                secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));
                secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                secondPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                //* awal pilih topping // 
                
                // Panel untuk menampilkan topping yang dipilih
                JPanel toppingPanel = new JPanel();
                toppingPanel.setLayout(new BoxLayout(toppingPanel, BoxLayout.Y_AXIS));

                // Load the current toppings (badges) first
                loadToppings(toppingPanel, item);
                
                //* akhir pilih topping //

                // Create a panel to hold the label and the combo box
                JPanel levelPanel = new JPanel();
                levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS)); // Vertical layout
                levelPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
                levelPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Add some left padding

                // Add the label
                JLabel levelLabel = new JLabel("Level:");
                levelLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align the label
                levelPanel.add(levelLabel);

                // Add some vertical spacing
                levelPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Space between label and combo box

                // Add the combo box
                JComboBox<String> levelComboBox = new JComboBox<>(dataLevel.keySet().toArray(new String[0]));
                levelComboBox.setSelectedItem("None");
                levelComboBox.setMaximumSize(new Dimension(200, levelComboBox.getPreferredSize().height + 10)); // Adjust max size
                levelComboBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Align the combo box
                levelComboBox.addActionListener(e -> {
                    for (OrderItem existingItem : orderItems) {
                        if (existingItem.getId() == item.getId()) {
                            // Replace the levels list with the selected level only
                            existingItem.getLevels().clear();
                            existingItem.getLevels().add((String) levelComboBox.getSelectedItem());

                            updateTotal();
                            break; // Exit the loop once the item is found and updated
                        }
                    }
                });
                levelPanel.add(levelComboBox);

                levelPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));  // 1/3 of the width

                secondPanel.add(levelPanel);
                secondPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Spacing between panels
                secondPanel.add(toppingPanel);

                orderItemPanel.add(secondPanel);
            }
            
            try {
                ImageIcon removeIcon = new ImageIcon(getClass().getResource("/Media/Close.png"));
                JButton removeButton = new JButton(removeIcon);
                removeButton.setContentAreaFilled(false); // Make button background transparent
                removeButton.setBorderPainted(false); // Remove button border
                removeButton.setFocusPainted(false); // Remove focus border
                removeButton.setPreferredSize(new Dimension(14, 14));
                removeButton.addActionListener(e -> {                       
                    orderItems.removeIf(existingItem -> existingItem.getId() == item.getId());
                    orderSummaryPanel.remove(orderItemPanel); // Remove panel from UI
                    orderSummaryPanel.revalidate();
                    orderSummaryPanel.repaint();
                    updateTotal(); // Update the total price or quantities
                });

                firstPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
                firstPanel.add(removeButton); // Add trash button to the panel
            } catch (Exception e) {
                System.err.println("Error loading trash icon: " + e.getMessage());
            }
            
            // Add a divider
            JSeparator divider = new JSeparator(SwingConstants.HORIZONTAL);
            divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            orderItemPanel.add(divider);

            // Add the order item panel to the order summary panel
            orderSummaryPanel.add(orderItemPanel);
        }
        
        orderSummaryPanel.revalidate();
        orderSummaryPanel.repaint();
    }

    private void loadToppings(JPanel toppingPanel, OrderItem item) {
        ButtonGroup removeButtonGroup = new ButtonGroup();
        
        toppingPanel.removeAll();
        
        // Add the label
        JLabel toppingLabel = new JLabel("Topping:");
        toppingLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align the label
        toppingPanel.add(toppingLabel);

        // Add some vertical spacing
        toppingPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Space between label and combo box
        
        for (Topping topping : item.getToppings()) {
            String namaTopping = topping.getNamaTopping(); // Nama topping
            int jumlahTopping = topping.getJumlahTopping(); // Jumlah topping
            int idTopping = topping.getIdBahan();
            
            // Membuat label untuk topping
            JLabel toppingBadgeLabel = new JLabel(namaTopping + " x" + jumlahTopping);
            toppingBadgeLabel.setOpaque(true);
            toppingBadgeLabel.setBackground(Color.WHITE);
            toppingBadgeLabel.setForeground(Color.RED);
            toppingBadgeLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            toppingBadgeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            toppingBadgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            toppingBadgeLabel.setPreferredSize(new Dimension(80, 20));
            
            // Tombol "X" untuk menghapus topping
            JToggleButton btnRemove = new JToggleButton("X"); // Menggunakan JToggleButton untuk "grouping"
            btnRemove.setPreferredSize(new Dimension(25, 25));
            btnRemove.setFont(new Font("Arial", Font.BOLD, 12));
            btnRemove.setForeground(Color.WHITE);
            btnRemove.setBackground(Color.RED);
            btnRemove.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            btnRemove.setFocusPainted(false);
//                    btnRemove.addMouseListener(new MouseAdapter() {
//                        @Override
//                        public void mouseClicked(MouseEvent e) {
//                            String[] confirmButtonLabels = {"OK", "NO"};
//
//                            ActionListener yesAction = ev -> {
//                                PreparedStatement stmt = null;
//
//                                try {
//                                    int idMenu = Integer.parseInt(txtIDMenu.getText());
//
//                                    // Query untuk menghapus topping berdasarkan id_menu dan id_topping
//                                    String query = "DELETE FROM `topping_menu` WHERE `id_menu` = ? AND `id_topping` = ?";
//                                    stmt = conn.prepareStatement(query);
//                                    stmt.setInt(1, idMenu);
//                                    stmt.setInt(2, idTopping); // Menggunakan ID topping untuk menghapus
//                                    stmt.executeUpdate();
//
//                                    loadToppingMenu(); // Memuat topping menu terbaru
//
//                                    String[] buttonLabels = {"OK"};
//                                    MessageDialog dialog = new MessageDialog(
//                                        "Informasi",
//                                        "Topping berhasil dihapus.",
//                                        buttonLabels,
//                                        null // Menutup dialog secara default
//                                    );
//                                    dialog.showDialog();
//                                } catch (SQLException ex) {
//                                    String[] buttonLabels = {"OK"};
//                                    MessageDialog dialog = new MessageDialog(
//                                        "Error",
//                                        "Gagal menghapus topping: " + ex.getMessage(),
//                                        buttonLabels,
//                                        null
//                                    );
//                                    dialog.showDialog();
//                                } finally {
//                                    if (stmt != null) {
//                                        try {
//                                            stmt.close();
//                                        } catch (SQLException ex) {
//                                            ex.printStackTrace();
//                                        }
//                                    }
//                                }
//                            };
//                            ActionListener noAction = ev -> {
//                                // Tidak melakukan apa-apa jika "NO"
//                                return;
//                            };
//
//                            // Menampilkan konfirmasi penghapusan topping
//                            MessageDialog dialog = new MessageDialog(
//                                "Konfirmasi Hapus",
//                                "Apakah Anda yakin ingin menghapus topping tersebut?",
//                                confirmButtonLabels,
//                                new ActionListener[]{yesAction, noAction}
//                            );
//                            dialog.showDialog();
//                        }
//                    });

            removeButtonGroup.add(btnRemove);

            // Panel untuk menampilkan topping
            JPanel toppingBadgePanel = new JPanel();
            toppingBadgePanel.setLayout(new BorderLayout());
            toppingBadgePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Bottom padding of 5px
            toppingPanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    // Dynamically set the child width to match the parent
                    int parentWidth = toppingPanel.getWidth();
                    toppingBadgePanel.setMaximumSize(new Dimension(parentWidth, 50)); // Match parent's width, keep height fixed
                    toppingBadgePanel.setPreferredSize(new Dimension(parentWidth, 50)); // Optional
                    toppingBadgePanel.revalidate();
                }
            });

            // Set the preferred size of the components so they fit their content
            toppingBadgeLabel.setPreferredSize(new Dimension(toppingBadgeLabel.getPreferredSize().width, toppingBadgeLabel.getPreferredSize().height)); // Optional: Set preferred size
            btnRemove.setPreferredSize(new Dimension(btnRemove.getPreferredSize().width, btnRemove.getPreferredSize().height)); // Optional: Set preferred size


            // Add the toppingLabel to the center
            toppingBadgePanel.add(toppingBadgeLabel, BorderLayout.CENTER);
            // Add the btnRemove button to the east (right side)
            toppingBadgePanel.add(btnRemove, BorderLayout.EAST);
            toppingBadgePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // 2/3 of the widthtoppingBadge.setMaximumSize(new Dimension(50, Integer.MAX_VALUE)); // 2/3 of the width

            // Add the panel to the main topping panel
            toppingPanel.add(toppingBadgePanel);
        }
        
        // Tombol untuk menambah topping
        JButton btnAddTopping = new JButton("Add Topping");
        btnAddTopping.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the button
        btnAddTopping.setMaximumSize(new Dimension(200, btnAddTopping.getPreferredSize().height + 10)); // Adjust max size

        btnAddTopping.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the BahanPopup form
                ToppingPopup addToppingPopup = new ToppingPopup();
                addToppingPopup.setVisible(true);

                // Set the action listener for the popup
                addToppingPopup.setAddToppingActionListener((Bahan topping, int jumlahTopping) -> {
                    // update stok bahan topping
                    try {
                        int idBahan = topping.getIDBahan(); // ID bahan untuk topping

                        // Check stock
                        String checkStockQuery = "SELECT stok_bahan FROM bahan WHERE id_bahan = ?";
                        PreparedStatement checkStockStmt = conn.prepareStatement(checkStockQuery);
                        checkStockStmt.setInt(1, idBahan);
                        ResultSet stockRs = checkStockStmt.executeQuery();
                        
                        if (stockRs.next()) {
                            int currentStock = stockRs.getInt("stok_bahan");
                            
                            if (currentStock < jumlahTopping) {
                                throw new SQLException("Stok tidak mencukupi untuk topping berikut: " + topping.getNamaBahan());
                            }

                            // Update stock
                            String updateStockQuery = "UPDATE bahan SET stok_bahan = stok_bahan - ? WHERE id_bahan = ?";
                            PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
                            updateStockStmt.setInt(1, jumlahTopping);
                            updateStockStmt.setInt(2, idBahan);
                            updateStockStmt.executeUpdate();

                            // Tambahkan ke kartu stok
                            String keterangan = "Transaksi";
                            int jumlahMasuk = 0;
                            int jumlahKeluar = jumlahTopping;
                            
                            KartuStok.insertKartuStok(conn, idBahan, keterangan, jumlahMasuk, jumlahKeluar, "transaksi");
                        } else {
                            throw new SQLException("Bahan dengan ID: " + idBahan + " tidak ditemukan.");
                        }
                    } catch (SQLException sqlEx) {
                        JOptionPane.showMessageDialog(null, "Gagal memeriksa stok topping. " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    item.addTopping(topping.getIDBahan(), topping.getNamaBahan(), topping.getHargaBahan(), jumlahTopping);

                    loadToppings(toppingPanel, item); // Reload toppings after adding a new one
                });
            }
        });

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Align to the left
        buttonWrapper.add(btnAddTopping);

        toppingPanel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE)); // 2/3 of the width
        
        // Add the button to the panel after loading the toppings
        toppingPanel.add(buttonWrapper);
        
        toppingPanel.revalidate();
        toppingPanel.repaint();
        
        updateTotal();
    }
    
    private void updateTotal() {
        totalHarga = 0;

        for (OrderItem item : orderItems) {
            totalHarga += item.getHarga() * item.getKuantitas();
            
            for (Topping topping : item.getToppings()) {
                totalHarga += topping.getHargaTopping() * topping.getJumlahTopping();
            }
            
            for (String level : item.getLevels()) {
                if (dataLevel.containsKey(level)) {
                    totalHarga += dataLevel.get(level);
                }
            }
        }

        totalLabel.setText("Total: Rp. " + totalHarga);
    }



    public void setFormTransaksi(FormTransaksi formTransaksi) {
        this.formTransaksi = formTransaksi;
    }

    private void createNewPelanggan() {
        PreparedStatement stmt;
        
        try {
            String query = "INSERT INTO `pelanggan`(`nama`, `no_telp`) VALUES (?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Replace with your values
            String namaPelanggan = txtName.getText();
            String nomorTelepon = txtPhone.getText();

            // Set the parameters
            stmt.setString(1, namaPelanggan);
            stmt.setString(2, nomorTelepon);

            // Execute the insert statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    IDPelanggan = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            String[] buttonLabels = {"OK"};

            // Create a new instance of CustomDialog without actions
            MessageDialog dialog = new MessageDialog(
                "Error",
                "Gagal menambahkan data.",
                buttonLabels,
                null // Pass null for default behavior (close dialog)
            );

            // Show the dialog
            dialog.showDialog();
        }
    }
}
