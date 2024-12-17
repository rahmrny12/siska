import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelHighlightExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel Highlight Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 100));
        panel.setOpaque(true); // Ensure the panel is opaque
        panel.setBackground(Color.WHITE);

        // Add a mouse listener for highlighting
        panel.addMouseListener(new MouseAdapter() {
            Color originalColor = panel.getBackground();
            Color highlightColor = new Color(230, 230, 250);

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(highlightColor);
                panel.repaint(); // Force repaint
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(originalColor);
                panel.repaint(); // Force repaint
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
