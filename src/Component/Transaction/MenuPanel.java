package Component.Transaction;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public interface MenuActionListener {
        void onItemAdded(String itemName, int itemPrice);
    }
    
    private MenuActionListener actionListener;
    
    public MenuPanel() {
        setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns for menu items
        
        

        // Example menu items
        String[][] menuItems = {
            {"Seblak Original", "12000", "seblak1.jpg"},
            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
            {"Seblak Rafael", "8000", "seblak3.jpg"},
            {"Seblak Spicy", "10000", "seblak4.jpg"},
            {"Seblak Original", "12000", "seblak1.jpg"},
            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
            {"Seblak Rafael", "8000", "seblak3.jpg"},
            {"Seblak Spicy", "10000", "seblak4.jpg"},
            {"Seblak Original", "12000", "seblak1.jpg"},
            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
            {"Seblak Rafael", "8000", "seblak3.jpg"},
            {"Seblak Spicy", "10000", "seblak4.jpg"},
        };
         
        JTextArea orderSummary = new JTextArea();
        orderSummary.setEditable(false);

        JLabel totalLabel = new JLabel("Total: Rp. 0");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        for (String[] menuItem : menuItems) {
            add(createMenuCard(menuItem[0], menuItem[1], menuItem[2], orderSummary, totalLabel));
        }
        
        add(new JLabel());
    }

    private JPanel createMenuCard(String name, String price, String image, JTextArea orderSummary, JLabel totalLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel priceLabel = new JLabel("Rp. " + price);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onItemAdded(name, Integer.parseInt(price));
            }
        });
        
        // Add components to the card
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(priceLabel, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }
    
    public void setMenuActionListener(MenuActionListener listener) {
        this.actionListener = listener;
    }
}