package Component.Transaction;

import Component.Transaction.PaymentPopup.PaymentListener;
import Model.OrderItem;
import View.FormTransaksi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LeftPanel extends JPanel {

    private JPanel orderSummaryPanel;
    private JLabel totalLabel;
    
    private FormTransaksi formTransaksi;
    private Map<Integer, OrderItem> orderItems = new HashMap<>();
    private int totalHarga = 0;
    
    public LeftPanel() {
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
    
    public void addOrderItem(int itemId, String itemName, double itemPrice) {
        // Check if the item already exists
        if (orderItems.containsKey(itemId)) {
            // Update quantity for existing item
            OrderItem existingItem = orderItems.get(itemId);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            // Create a new order item
            OrderItem newItem = new OrderItem(itemId, itemName, itemPrice);
            orderItems.put(itemId, newItem);
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

            JLabel nameLabel = new JLabel(item.getName() + " (Rp. " + item.getPrice() + ")");
            nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            firstPanel.add(nameLabel);

            firstPanel.add(Box.createHorizontalGlue());

            JLabel quantityLabel = new JLabel(item.getQuantity() + "x");
            quantityLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            firstPanel.add(quantityLabel);

            // Second Panel (Topping, Level)
            JPanel secondPanel = new JPanel();
            secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));
            secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            secondPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            secondPanel.add(new JLabel("Topping:"));
            JComboBox<String> toppingComboBox = new JComboBox<>(new String[]{"None", "Cheese", "Choco"});
            toppingComboBox.setSelectedItem(item.getTopping());
            toppingComboBox.addActionListener(e -> item.setTopping((String) toppingComboBox.getSelectedItem()));
            toppingComboBox.setMaximumSize(new Dimension(100, toppingComboBox.getPreferredSize().height + 5 * 2));
            toppingComboBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
            secondPanel.add(toppingComboBox);

            secondPanel.add(new JLabel("Level:"));
            JComboBox<String> levelComboBox = new JComboBox<>(new String[]{"Mild", "Medium", "Hot"});
            levelComboBox.setSelectedItem(item.getLevel());
            levelComboBox.addActionListener(e -> item.setLevel((String) levelComboBox.getSelectedItem()));
            levelComboBox.setMaximumSize(new Dimension(100, levelComboBox.getPreferredSize().height + 5 * 2));
            levelComboBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            secondPanel.add(levelComboBox);

            // Add panels to the order item panel
            orderItemPanel.add(firstPanel);
            orderItemPanel.add(secondPanel);

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
            totalHarga += item.getPrice() * item.getQuantity();
        }

        totalLabel.setText("Total: Rp. " + totalHarga);
    }



    public void setFormTransaksi(FormTransaksi formTransaksi) {
        this.formTransaksi = formTransaksi;
    }
}
