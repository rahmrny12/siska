package ComponentUI.Transaction;

import ComponentUI.MessageDialog;
import ComponentUI.Transaction.PaymentPopup.PaymentListener;
import Model.OrderItem;
import View.FormTransaksi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeftPanel extends JPanel {

    private Connection conn;
    
    private JPanel orderSummaryPanel;
    private JLabel totalLabel;
    private JTextField txtName;
    private JTextField txtPhone;
    
    private FormTransaksi formTransaksi;
    private Map<Integer, OrderItem> orderItems = new LinkedHashMap<>();
    private int totalHarga = 0;
    private Integer IDPelanggan = null;
    
    Map<String, Integer> dataTopping = new LinkedHashMap<>();
    Map<String, Integer> dataLevel = new LinkedHashMap<>();
    
    public LeftPanel() {
        conn = Helper.Database.OpenConnection();
        
        dataTopping.put("None", 0);
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
        // Check if the item already exists
        if (orderItems.containsKey(id)) {
            // Update quantity for existing item
            OrderItem existingItem = orderItems.get(id);
            existingItem.setKuantitas(existingItem.getKuantitas() + 1);
        } else {
            // Create a new order item
            OrderItem newItem = new OrderItem(id, nama, harga, jenis);
            orderItems.put(id, newItem);
        }

        // Update the UI to reflect changes
        refreshOrderSummaryPanel();
        updateTotal();
    }
    
    private void refreshOrderSummaryPanel() {
        orderSummaryPanel.removeAll();

        for (OrderItem item : orderItems.values()) {
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

                secondPanel.add(new JLabel("Topping:"));
                JComboBox<String> toppingComboBox = new JComboBox<>(dataTopping.keySet().toArray(new String[0]));
                toppingComboBox.setSelectedItem("None");
                toppingComboBox.setMaximumSize(new Dimension(100, toppingComboBox.getPreferredSize().height + 5 * 2));
                toppingComboBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
                toppingComboBox.addActionListener(e -> {
                    if (orderItems.containsKey(item.getId())) {
                        OrderItem existingItem = orderItems.get(item.getId());

                        // Replace the toppings list with the selected topping only
                        existingItem.getToppings().clear();
                        existingItem.getToppings().add((String) toppingComboBox.getSelectedItem());

                        
                        updateTotal();
                    }
                });
                secondPanel.add(toppingComboBox);

                secondPanel.add(new JLabel("Level:"));
                JComboBox<String> levelComboBox = new JComboBox<>(dataLevel.keySet().toArray(new String[0]));
                levelComboBox.setSelectedItem("None");
                levelComboBox.setMaximumSize(new Dimension(100, levelComboBox.getPreferredSize().height + 5 * 2));
                levelComboBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                levelComboBox.addActionListener(e -> {
                    if (orderItems.containsKey(item.getId())) {
                        OrderItem existingItem = orderItems.get(item.getId());

                        // Replace the toppings list with the selected topping only
                        existingItem.getLevels().clear();
                        existingItem.getLevels().add((String) levelComboBox.getSelectedItem());
                    
                        updateTotal();
                    }
                });
                secondPanel.add(levelComboBox);
                
                secondPanel.add(Box.createHorizontalGlue());

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
                    // Handle delete action
                    orderItems.remove(item.getId()); // Remove item from the list
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
    
    private void updateTotal() {
        totalHarga = 0;

        for (OrderItem item : orderItems.values()) {
            totalHarga += item.getHarga() * item.getKuantitas();
            
            for (String topping : item.getToppings()) {
                if (dataTopping.containsKey(topping)) {
                    totalHarga += dataTopping.get(topping);
                }
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
