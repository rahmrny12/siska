package Component.Transaction;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Model.Menu;

public class MenuPanel extends JPanel {

    private Connection conn;
    private List<Model.Menu> dataMenu = new ArrayList<>();
    
    public interface MenuActionListener {
        void onItemAdded(int id);
    }
    
    private MenuActionListener actionListener;
    
    public void setMenuActionListener(MenuActionListener listener) {
        this.actionListener = listener;
    }
    
    public void setDataMenu(List<Menu> dataMenu) {
        this.dataMenu = dataMenu;
        
        for (Menu menuItem : dataMenu) {
            int id = menuItem.getId();
            String name = menuItem.getNamaMenu();
            int price = (int) menuItem.getHarga();

            add(createMenuCard(id, name, price));
        }
        
        add(new JLabel());
    }
    
    public MenuPanel() {
        setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns for menu items
        
        conn = Helper.Database.OpenConnection();

        // Example menu items
//        String[][] menuItems = {
//            {"Seblak Original", "12000", "seblak1.jpg"},
//            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
//            {"Seblak Rafael", "8000", "seblak3.jpg"},
//            {"Seblak Spicy", "10000", "seblak4.jpg"},
//            {"Seblak Original", "12000", "seblak1.jpg"},
//            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
//            {"Seblak Rafael", "8000", "seblak3.jpg"},
//            {"Seblak Spicy", "10000", "seblak4.jpg"},
//            {"Seblak Original", "12000", "seblak1.jpg"},
//            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
//            {"Seblak Rafael", "8000", "seblak3.jpg"},
//            {"Seblak Spicy", "10000", "seblak4.jpg"},
//            {"Seblak Rafael", "8000", "seblak3.jpg"},
//            {"Seblak Spicy", "10000", "seblak4.jpg"},
//            {"Seblak Original", "12000", "seblak1.jpg"},
//            {"Seblak Tlg Bds", "13000", "seblak2.jpg"},
//            {"Seblak Rafael", "8000", "seblak3.jpg"},
//            {"Seblak Spicy", "10000", "seblak4.jpg"},
//        };
         
        JTextArea orderSummary = new JTextArea();
        orderSummary.setEditable(false);

        JLabel totalLabel = new JLabel("Total: Rp. 0");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    private JPanel createMenuCard(int id, String name, int price) {
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
                actionListener.onItemAdded(id);
            }
        });
        
        // Add components to the card
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(priceLabel, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }
}