/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

/**
 *
 * @author LENOVO
 */
import Model.OrderItem;
import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Struk {

    private static final HashMap<Integer, OrderItem> orderItems = new HashMap<>();

    // Populate order items (sample data)
    static {
        orderItems.put(0, new OrderItem(3, "Coffee", 3000.0, "Beverage"));
        orderItems.put(1, new OrderItem(8, "Sandwich", 10000.0, "Food"));
    }

    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = 1.50;
        double width = cmToPp(8);
        double height = cmToPp(5 + bodyHeight + 5); // Header + Body + Footer
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cmToPp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
    }

    protected static double cmToPp(double cm) {
        return toPpi(cm * 0.3936);
    }

    protected static double toPpi(double inch) {
        return inch * 72d;
    }

    public static class BillPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) return NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            try {
                int y = 20, yShift = 10;

                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                g2d.drawString("-------------------------------------", 12, y); y += yShift;
                g2d.drawString("      Seblak Mang Ujang      ", 12, y); y += yShift;
                g2d.drawString("-------------------------------------", 12, y); y += 15;

                g2d.drawString(" Nama Menu                Price  ", 10, y); y += yShift;
                g2d.drawString("-------------------------------------", 10, y); y += 15;

                // Iterate through orderItems and display
                for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) {
                    OrderItem item = entry.getValue();
                    String line = String.format(" %-20s %7.2f", item.getNama(), item.getHarga());
                    g2d.drawString(line, 10, y); y += yShift;

                    String qtyLine = String.format("      %d x %.2f = %.2f", item.getKuantitas(), item.getHarga()
                            , item.getKuantitas() * item.getHarga());
                    g2d.drawString(qtyLine, 10, y); y += yShift;
                }

                g2d.drawString("-------------------------------------", 10, y); y += yShift;
                g2d.drawString(" Total:                   13000.00", 10, y); y += yShift;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return PAGE_EXISTS;
        }
    }

    public static void main(String[] args) {
        try {
            PrinterJob pj = PrinterJob.getPrinterJob();
            Struk struk = new Struk();
            pj.setPrintable(new BillPrintable(), struk.getPageFormat(pj));

            if (pj.printDialog()) {
                pj.print();
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }
}
