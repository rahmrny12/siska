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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.Locale;

public class Struk {

    private static final HashMap<Integer, OrderItem> orderItems = new HashMap<>();

    public static String formatTanggalTransaksi(Date tanggalTransaksi) {
        // Define the Indonesian locale
        Locale indonesianLocale = new Locale("id", "ID");

        // Define the format you want (e.g., "dd MMMM yyyy", "dd/MM/yyyy", etc.)
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", indonesianLocale);

        // Format the date to Indonesian string format
        return sdf.format(tanggalTransaksi);
    }
    
    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        // Lebar: 58mm, Tinggi: 100mm
        double width = cmToPp(5.8); // 5.8 cm
        double height = cmToPp(10.0); // 10.0 cm
        paper.setSize(width, height);

        // Area cetak yang bisa digunakan
        paper.setImageableArea(0, 0, width, height);

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
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("      Seblak Mang Ujang      ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += 15;

                g2d.drawString(" Nama Menu             Price  ", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 10, y);
                y += 15;

                // Iterate through orderItems and display
                for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) {
                    OrderItem item = entry.getValue();
                    String line = String.format(" %-18s %7.2f", item.getNama(), item.getHarga());
                    g2d.drawString(line, 10, y);
                    y += yShift;

                    String qtyLine = String.format("      %d x %.2f = %.2f", item.getKuantitas(), item.getHarga(),
                            item.getKuantitas() * item.getHarga());
                    g2d.drawString(qtyLine, 10, y);
                    y += yShift;
                }

                g2d.drawString("-------------------------------------", 10, y);
                y += yShift;
                g2d.drawString(" Total:                   13000.00", 10, y);
                y += yShift;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return PAGE_EXISTS;
        }
    }

    // New method: Save receipt as PDF
    public static void saveAsPDF(String IDTransaksi, Date tanggalTransaksi, Time waktuTransaksi, String namaPelanggan, Map<Integer, OrderItem> orderItems, double totalHarga, double totalPembayaran, double totalKembalian) {
        String outputPath = "D:\\struk-" + IDTransaksi + ".pdf"; // Change path as needed
        String restaurantName = " Seblak Mang Ujang ", address = "Jl. Sumatera 104 (Sebrang KPRI, samping Masjid At-Taqwa)";
        
        Document document = new Document();

        // Set custom page size for small receipts
        document.setPageSize(new com.itextpdf.text.Rectangle(cmToPt(5.8), cmToPt(10.0)));
        document.setMargins(5, 5, 5, 5); // Small margins for better utilization
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Add Header with margin between text
            addCenteredParagraph(document, "-------------------------------------", fontSmall());
            addCenteredParagraph(document, restaurantName, fontLargeBold());
            addCenteredParagraph(document, address, fontSmall());

            // Add more space after the header
            addCenteredParagraph(document, "-------------------------------------", fontSmall());
            addCenteredParagraph(document, "Kode Transaksi: " + IDTransaksi, fontSmall());
            addCenteredParagraph(document, "Tanggal: " + formatTanggalTransaksi(tanggalTransaksi), fontSmall());
            addCenteredParagraph(document, "Waktu: " + waktuTransaksi, fontSmall());
            if (!namaPelanggan.isEmpty()) {
                addCenteredParagraph(document, "Nama Pelanggan: " + namaPelanggan, fontSmall());
            }
            addCenteredParagraph(document, "-------------------------------------", fontSmall());

            // Add Items Section with margin between items
            addCenteredParagraph(document, "Menu", fontMediumBold());

            // Start printing the order items
            int currentPageHeight = 0;  // To track the height of the page content
            int maxPageHeight = 700;    // Max height for content before starting a new page (this will vary depending on the font size)

            for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) {
                OrderItem item = entry.getValue();
                String itemLine = String.format("%d x %s @ %.2f", 
                    item.getKuantitas(), 
                    truncateText(item.getNama(), 15), // Truncate long names
                    item.getHarga());
                String totalLine = String.format("     Total: %.2f", 
                    item.getKuantitas() * item.getHarga());

                // Check if we need to create a new page based on content height
                if (currentPageHeight > maxPageHeight) {
                    document.newPage();  // Create a new page
                    currentPageHeight = 0;  // Reset the height tracker
                }

                // Add the item lines
                addCenteredParagraph(document, itemLine, fontSmall());
                addCenteredParagraph(document, totalLine, fontSmall());

                // Update current height (adjust based on font size and line spacing)
                currentPageHeight += 20;  // You can modify this based on your font size/spacing
            }

            addCenteredParagraph(document, "-------------------------------------", fontSmall());

            // Add Summary Section
            addCenteredParagraph(document, "Total:         Rp. " + String.format("%.2f", totalHarga) + "\n", fontMediumBold());
            addCenteredParagraph(document, "Bayar:      Rp. " + String.format("%.2f", totalPembayaran), fontSmall());
            addCenteredParagraph(document, "Kembali:     Rp. " + String.format("%.2f", totalKembalian), fontSmall());

            addCenteredParagraph(document, "-------------------------------------", fontSmall());

            // Add Footer with margin before it
            addCenteredParagraph(document, "Terimakasih telah memesan!", fontSmall());
            addCenteredParagraph(document, "Dari dapur kami, ke meja Anda dengan cinta.", fontSmall());
            
            openPDF(outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    
    public static void openPDF(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (pdfFile.exists()) {
                    desktop.open(pdfFile); // Open the PDF file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addCenteredParagraph(Document document, String text, com.itextpdf.text.Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);  // Center the text
        document.add(paragraph);
    }
    
    private static void addLeftParagraph(Document document, String text, com.itextpdf.text.Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);  // Center the text
        document.add(paragraph);
    }

    private static com.itextpdf.text.Font fontSmall() {
        return new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 7);
    }

    private static com.itextpdf.text.Font fontMedium() {
        return new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8);
    }

    private static com.itextpdf.text.Font fontMediumBold() {
        return new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.BOLD);
    }

    private static com.itextpdf.text.Font fontLargeBold() {
        return new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.BOLD);
    }

    private static float cmToPt(double cm) {
        return (float) (cm * 28.3465); // Convert cm to points
    }

    private static String truncateText(String text, int length) {
        if (text.length() <= length) {
            return text;
        }
        return text.substring(0, length - 1) + "…";
    }

    public static void main(String[] args) {
        // Example usage
//        try {
//            // Print the receipt
//            PrinterJob pj = PrinterJob.getPrinterJob();
//            Struk struk = new Struk();
//            pj.setPrintable(new BillPrintable(), struk.getPageFormat(pj));
//
//            if (pj.printDialog()) {
//                pj.print();
//            }
//        } catch (PrinterException ex) {
//            ex.printStackTrace();
//        }

        // Save as PDF with dynamic name
//        saveAsPDF("receipt_12345", restaurantName, address, orderItems, subtotal, tax, total);
    }
}
