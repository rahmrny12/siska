package ComponentUI.Transaction;

import Model.BahanMenu;
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
            String nama = menuItem.getNamaMenu();
            int harga = (int) menuItem.getHarga();
            String jenis = menuItem.getJenis();
            List<BahanMenu> bahan = menuItem.getListBahan();

            add(createMenuCard(id, nama, harga, jenis, bahan));
        }
        
        add(new JLabel());
    }
    
    public MenuPanel() {
        setLayout(new GridLayout(0, 3, 10, 10)); // 2 columns for menu items
        
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
    
    private JPanel createMenuCard(int id, String nama, int harga, String jenis, List<BahanMenu> listBahan) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(0, 1, 10, 10)); // One column with gaps of 10px between items
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(nama);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        menuPanel.add(nameLabel);

        JLabel priceLabel = new JLabel("Rp. " + harga);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        menuPanel.add(priceLabel);
        
        for (BahanMenu bahan : listBahan) {
            if (bahan.getStokSaatIni() < bahan.getStokDibutuhkan()) {
                String bahanText = bahan.getNamaBahan() + " (tersedia: " + bahan.getStokSaatIni() + ", butuh: " + bahan.getStokDibutuhkan() + ")";
                JLabel bahanLabel = new JLabel(bahanText);
                bahanLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                bahanLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bahanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Menambahkan label bahan ke panel
                menuPanel.add(bahanLabel);
                menuPanel.setBackground(Color.PINK);
                card.setBackground(Color.PINK);
            }
        }

        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onItemAdded(id);
            }
        });
        
        // Add components to the card
        card.add(new JLabel(""), BorderLayout.SOUTH);
        card.add(menuPanel, BorderLayout.CENTER);
        card.add(new JLabel(""), BorderLayout.SOUTH);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }
}