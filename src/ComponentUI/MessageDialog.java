/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ComponentUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author LENOVO
 */
public class MessageDialog {

    private final String title;
    private final String message;
    private final String[] buttonLabels;
    private final ActionListener[] actions;
    private JButton defaultButton;

    /**
     * Constructor to initialize the dialog components.
     *
     * @param title        Title of the dialog.
     * @param message      Message to display in the dialog.
     * @param buttonLabels Array of button labels.
     * @param actions      Array of ActionListener corresponding to each button (nullable).
     */
    public MessageDialog(String title, String message, String[] buttonLabels, ActionListener[] actions) {
        this.title = title;
        this.message = message;
        this.buttonLabels = buttonLabels;
        this.actions = actions != null ? actions : new ActionListener[buttonLabels.length];
    }

    /**
     * Show the custom dialog.
     */
    public void showDialog() {
        // Create a custom JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null); // Center on screen
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a custom JPanel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(129, 1, 1), getWidth(), getHeight(), new Color(255, 85, 85));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add a message label
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message.replace("\n", "<br>") + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add buttons for confirmation
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false); // Transparent panel to show background

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setBorder(new EmptyBorder(10, 20, 10, 20));
            button.addActionListener(e -> dialog.dispose()); // Default close action
            if (actions != null && i < actions.length && actions[i] != null) {
                button.addActionListener(actions[i]);
            }
            if (i == 0) { // Assign the first button as the default
                defaultButton = button;
            }
            buttonPanel.add(button);
        }

        // Add components to the background panel
        backgroundPanel.add(messageLabel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the custom panel to the dialog
        dialog.setContentPane(backgroundPanel);

        // Add Key Binding for the Enter key
        if (defaultButton != null) {
            backgroundPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "pressEnter");
            backgroundPanel.getActionMap().put("pressEnter", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    defaultButton.doClick(); // Trigger the default button
                }
            });
        }

        // Show the dialog
        dialog.setVisible(true);
    }
}