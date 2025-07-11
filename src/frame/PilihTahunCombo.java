
package frame;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import combo_suggestion.ComboBoxSuggestion;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class PilihTahunCombo extends javax.swing.JFrame {
   private JTable tabels;
    /**
     * Creates new form PilihTahunCombo
     */
    public PilihTahunCombo() {
        initComponents();
        tahuns.setSelectedItem("2000");
        bulans.setSelectedItem("Januari");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        bulans = new combo_suggestion.ComboBoxSuggestion();
        tahuns = new combo_suggestion.ComboBoxSuggestion();
        jLabel2 = new javax.swing.JLabel();
        baliks = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bulans.setEditable(false);
        bulans.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "Septemper", "Oktober", "November", "Desember" }));
        getContentPane().add(bulans, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 150, -1));

        tahuns.setEditable(false);
        tahuns.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045" }));
        getContentPane().add(tahuns, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 150, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel2.setText("Pilih Tahun dan Bulan");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 30));

        baliks.setBackground(new java.awt.Color(255, 255, 255));
        baliks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                baliksMouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (40).png"))); // NOI18N

        javax.swing.GroupLayout baliksLayout = new javax.swing.GroupLayout(baliks);
        baliks.setLayout(baliksLayout);
        baliksLayout.setHorizontalGroup(
            baliksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(baliksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(baliksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        baliksLayout.setVerticalGroup(
            baliksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(baliksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(baliksLayout.createSequentialGroup()
                    .addGap(0, 1, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 1, Short.MAX_VALUE)))
        );

        getContentPane().add(baliks, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 30, 30));

        btn_clear.setBackground(new java.awt.Color(0, 204, 204));
        btn_clear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        btn_print.setBackground(new java.awt.Color(255, 0, 0));
        btn_print.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        getContentPane().add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setText("Tahun");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel4.setText("Bulan");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (49).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 210));

        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        setSize(new java.awt.Dimension(400, 210));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
                 tahuns.setSelectedItem("2000"); // Misalnya tahun default adalah 2022
        bulans.setSelectedItem("Januari"); 
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        String selectedMonth = bulans.getSelectedItem().toString();
        String selectedYear = tahuns.getSelectedItem().toString();
        
        // Menggunakan nama file yang sesuai
        String fileName = "BulanLap.pdf";

        // Memanggil metode createPDF dengan parameter fileName, selectedMonth, dan selectedYear
        createPDF(fileName, selectedMonth, selectedYear);
    }//GEN-LAST:event_btn_printActionPerformed

    private void baliksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baliksMouseClicked
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_baliksMouseClicked

    private void createPDF(String fileName, String selectedMonth, String selectedYear) {
        // Mendapatkan tanggal awal dan akhir dari bulan dan tahun yang dipilih
        String startDate = getStartDate(selectedMonth, selectedYear);
        String endDate = getEndDate(selectedMonth, selectedYear);

        // Mengelompokkan data berdasarkan bulan dan tahun
        Map<String, List<Object[]>> groupedData = new TreeMap<>();
        for (int i = 0; i < tabels.getRowCount(); i++) {
            String tgl = tabels.getValueAt(i, 1).toString();
            // Cek apakah tanggal berada dalam rentang yang dipilih
            if (tgl.compareTo(startDate) >= 0 && tgl.compareTo(endDate) <= 0) {
                String bulanTahun = getMonthYearFromDate(tgl);
                groupedData.putIfAbsent(bulanTahun, new ArrayList<>());
                // Menambahkan seluruh data baris ke dalam groupedData
                Object[] rowData = new Object[tabels.getColumnCount()];
                for (int j = 0; j < tabels.getColumnCount(); j++) {
                    rowData[j] = tabels.getValueAt(i, j);
                }
                groupedData.get(bulanTahun).add(rowData);
            }
        }
        // Membuat dokumen PDF
        Document doc = new Document();
        try {
            // Membuat file PDF dengan nama yang diberikan
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();

            // Judul
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Keuangan Laundry Visimala", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(Chunk.NEWLINE);

            // Membuat tabel untuk setiap bulan dan tahun
            for (Map.Entry<String, List<Object[]>> entry : groupedData.entrySet()) {
                String bulanTahun = entry.getKey();
                List<Object[]> data = entry.getValue();

                // Judul bulan dan tahun
                doc.add(new Paragraph("Bulan: " + bulanTahun));
                doc.add(Chunk.NEWLINE);

                // Tabel
                PdfPTable tbl = new PdfPTable(tabels.getColumnCount());
                tbl.setWidthPercentage(100); // Tabel mengisi lebar halaman

                // Mengisi data dari tabel
                for (Object[] row : data) {
                    for (Object cell : row) {
                        PdfPCell cellItem = new PdfPCell();
                        cellItem.setBorderWidth(2);
                        cellItem.setPhrase(new Phrase(cell.toString()));
                        tbl.addCell(cellItem);
                    }
                }

                // Menambahkan total laba, pemasukan, dan pengeluaran di bawah tabel
                PdfPCell totalCell = new PdfPCell(new Phrase("Total"));
                totalCell.setBorderWidth(2);
                totalCell.setColspan(2); // Menggabungkan 2 kolom
                tbl.addCell(totalCell);

                // Menghitung total laba, pemasukan, dan pengeluaran
                double totalLaba = calculateTotal(data, 2);
                double totalPemasukan = calculateTotal(data, 3);
                double totalPengeluaran = calculateTotal(data, 4);

                // Menambahkan total ke dalam tabel
                tbl.addCell(new PdfPCell());
                tbl.addCell(new PdfPCell());
                tbl.addCell(new PdfPCell(new Phrase(String.valueOf(totalLaba))));
                tbl.addCell(new PdfPCell(new Phrase(String.valueOf(totalPemasukan))));
                tbl.addCell(new PdfPCell(new Phrase(String.valueOf(totalPengeluaran))));

                doc.add(tbl);
                doc.add(Chunk.NEWLINE);
            }
            JOptionPane.showMessageDialog(null, "PDF Generated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something Went Wrong: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    private String getStartDate(String selectedMonth, String selectedYear) {
        return selectedYear + "-" + getMonthNumber(selectedMonth) + "-01";
    }

    private String getEndDate(String selectedMonth, String selectedYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(selectedYear));
        calendar.set(Calendar.MONTH, getMonthNumber(selectedMonth));
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return selectedYear + "-" + getMonthNumber(selectedMonth) + "-" + lastDay;
    }

    private int getMonthNumber(String monthName) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        try {
            Date date = monthFormat.parse(monthName);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getMonthYearFromDate(String date) {
        String[] parts = date.split("-");
        return parts[0] + "-" + parts[1];
    }

    private double calculateTotal(List<Object[]> data, int columnIndex) {
        double total = 0;
        for (Object[] row : data) {
            total += Double.parseDouble(row[columnIndex].toString());
        }
        return total;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PilihTahunCombo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PilihTahunCombo().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel baliks;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_print;
    private combo_suggestion.ComboBoxSuggestion bulans;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private combo_suggestion.ComboBoxSuggestion tahuns;
    // End of variables declaration//GEN-END:variables
}
