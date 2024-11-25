package Component.Transaction;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.RED);

        String[] filters = {"Topping", "Level", "Request"};
        for (String filter : filters) {
            JButton filterButton = new JButton(filter);
            filterButton.setPreferredSize(new Dimension(100, 30));
            add(filterButton);
        }
    }
}
