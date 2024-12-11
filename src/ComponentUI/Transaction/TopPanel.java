package ComponentUI.Transaction;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class TopPanel extends JPanel {
    public TopPanel(ActionListener filterActionListener) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.RED);

        // Array for filter buttons: Makanan and Minuman
        String[] filters = {"makanan", "minuman"};
        
        // Loop to create filter buttons
        for (String filter : filters) {
            JButton filterButton = new JButton(filter.toUpperCase()); // Capitalize the text
            filterButton.setPreferredSize(new Dimension(100, 30));

            // Make the button opaque and remove default border
            filterButton.setOpaque(false);
            filterButton.setContentAreaFilled(false);
            filterButton.setBorderPainted(false);

            // Apply rounded corners directly using custom paint
            filterButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    // Cast the button to JButton to access properties
                    JButton button = (JButton) c;

                    // Create rounded rectangle path
                    int radius = 15; // Radius for rounded corners
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Fill the button with the background color
                    g2d.setColor(button.getBackground());
                    g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), radius, radius);

                    // Set text color and paint the text
                    g2d.setColor(button.getForeground()); // Get the text color (foreground)
                    FontMetrics fm = g.getFontMetrics();
                    int x = (button.getWidth() - fm.stringWidth(button.getText())) / 2;
                    int y = (button.getHeight() + fm.getAscent()) / 2;
                    g2d.drawString(button.getText(), x, y);
                }
            });

            // Set text color and background
            filterButton.setForeground(Color.BLACK); // Set the text color
            filterButton.setBackground(Color.WHITE); // Set background color (it will show through the rounded corners)

            // Change the cursor to hand pointer when hovered
            filterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add action listener for each button
            filterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    filterActionListener.actionPerformed(e);  // Notify listener of selected filter
                }
            });

            // Add button to panel
            add(filterButton);
        }
    }
}
