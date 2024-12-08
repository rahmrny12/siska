package Component.Transaction;

import Component.Transaction.PaymentPopup.PaymentListener;
import Model.OrderItem;
import View.FormTransaksi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeftPanel extends JPanel {

    private JPanel orderSummaryPanel;
    private JLabel totalLabel;
    
    private FormTransaksi formTransaksi;
    private Map<Integer, OrderItem> orderItems = new HashMap<>();
    private int totalHarga = 0;
    
    Map<String, Integer> dataTopping = new LinkedHashMap<>();
    Map<String, Integer> dataLevel = new LinkedHashMap<>();
    
    public LeftPanel() {
        dataTopping.put("None", 0);
        dataTopping.put("Dumpling", 1000);
        dataTopping.put("Telur", 3000);
        dataTopping.put("Cuanki", 2000);
        
        dataLevel.put("None", 0);
        dataLevel.put("Level 1", 1000);
        dataLevel.put("Level 2", 2000);
        dataLevel.put("Level 3", 3000);
        
        orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS)); // Vertical layout
        orderSummaryPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Wrap orderSummaryPanel in a JScrollPane
        JScrollPane orderScrollPane = new JScrollPane(orderSummaryPanel);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScrollPane.getViewport().setAlignmentY(Component.TOP_ALIGNMENT);

        // Total Label
        totalLabel = new JLabel("Total: Rp. 0");
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
                // Update FormTransaksi with payment data
                formTransaksi.submitTransaction(orderItems, totalPembayaran, totalKembalian);
                
                paymentPopup.dispose();
                orderItems.clear();
                orderSummaryPanel.removeAll();
                orderSummaryPanel.revalidate();
                orderSummaryPanel.repaint();
                
                totalLabel.setText("Total: Rp. 0");
            });
            paymentPopup.setVisible(true);
        });
        
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
            quantityLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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

                orderItemPanel.add(secondPanel);
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
}
