package Component.Transaction;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    private JTextArea orderSummary;
    private JLabel totalLabel;
    
    public LeftPanel() {
        orderSummary = new JTextArea();
        orderSummary.setEditable(false);

        totalLabel = new JLabel("Total: Rp. 0");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tambahkan Pesanan"));
        setPreferredSize(new Dimension(200, 0));

        // Configure order summary
        JScrollPane orderScrollPane = new JScrollPane(orderSummary);

        // Bottom Panel (Total and Pay Button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton payButton = new JButton("Bayar");
        payButton.setBackground(Color.RED);
        payButton.setForeground(Color.WHITE);

        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(payButton, BorderLayout.SOUTH);

        // Add components to this panel
        add(orderScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public JTextArea getOrderSummary() {
        return orderSummary;
    }

    public JLabel getTotalLabel() {
        return totalLabel;
    }
}
