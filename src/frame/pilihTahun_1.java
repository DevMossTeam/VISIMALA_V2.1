
package frame;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import combo_suggestion.ComboBoxSuggestion;
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
public class pilihTahun_1 extends javax.swing.JFrame {

    private JTable tabels;
    public pilihTahun_1() {
        initComponents();
        tahuns.setSelectedItem("2000");
        bulans.setSelectedItem("Januari");
    }
        private PdfPCell createCell(String content, float fontSize, int fontStyle) {
        com.lowagie.text.Font cellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize, fontStyle);
        PdfPCell cell = new PdfPCell(new Phrase(content, cellFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pdff = new javax.swing.JButton();
        clears = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        bulans = new combo_suggestion.ComboBoxSuggestion();
        tahuns = new combo_suggestion.ComboBoxSuggestion();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pdff.setBackground(new java.awt.Color(255, 0, 0));
        pdff.setForeground(new java.awt.Color(255, 255, 255));
        pdff.setText("Print");
        pdff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdffActionPerformed(evt);
            }
        });
        getContentPane().add(pdff, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 70, -1));

        clears.setBackground(new java.awt.Color(0, 204, 204));
        clears.setForeground(new java.awt.Color(255, 255, 255));
        clears.setText("Clear");
        clears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearsActionPerformed(evt);
            }
        });
        getContentPane().add(clears, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (40).png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, -1, -1));

        bulans.setBorder(null);
        bulans.setEditable(false);
        bulans.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        getContentPane().add(bulans, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 140, 20));

        tahuns.setBorder(null);
        tahuns.setEditable(false);
        tahuns.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045" }));
        getContentPane().add(tahuns, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 68, 140, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel4.setText("Bulan");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setText("Tahun");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel2.setText("Pilih Tahun dan Bulan");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (50).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, -1));

        setSize(new java.awt.Dimension(400, 210));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pdffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdffActionPerformed
         
        String selectedYear = tahuns.getSelectedItem().toString();
        System.out.println("selectedYear: "+selectedYear);

        // Mengecek apakah tahun dan bulan telah dipilih
        if ( selectedYear.equals("")) {
            JOptionPane.showMessageDialog(null, "Silakan pilih tahun dan bulan terlebih dahulu.");
            return;
        }

        // Menggunakan nama file yang sesuai
        String fileName = "LaporanKeuanganPerbulan.pdf";

        // Memanggil metode createPDF dengan parameter fileName, selectedMonth, dan selectedYear
        createPDF(fileName, selectedYear);
    }//GEN-LAST:event_pdffActionPerformed

    private void clearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearsActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jLabel7MouseClicked
    private void createPDF(String fileName, String selectedYear) {
            // Mengakses tabel dari panel P_Keuangan
    panelKeuangan P_Keuangan = new panelKeuangan(); 

    // Mendapatkan data dari tabel di panelKeuangan
    JTable tabels = P_Keuangan.getTabels();
    // Pastikan tabel tidak null sebelum mengambil datanya
    if (tabels != null) {
        // Dapatkan model tabel
        DefaultTableModel model = (DefaultTableModel) tabels.getModel();

        // Lanjutkan dengan operasi yang Anda lakukan dengan data tabel di sini
        // Misalnya, mengelompokkan data, membuat dokumen PDF, dll.
        // ...
    } else {
        // Tampilkan pesan bahwa tabel keuangan tidak tersedia
        JOptionPane.showMessageDialog(null, "Tabel keuangan belum diinisialisasi atau tidak tersedia.");
        return;
    }
        if (tabels == null) {
            JOptionPane.showMessageDialog(null, "Data tabel tidak tersedia.");
            return;
        }

        // Mengelompokkan data berdasarkan bulan dan tahun
        Map<String, List<Object[]>> groupedData = new TreeMap<>();
        for (int i = 0; i < tabels.getRowCount(); i++) {
            String tgl = tabels.getValueAt(i, 1).toString();
            String bulanTahun = getMonthYearFromDate(tgl); // Fungsi untuk mendapatkan bulan dan tahun dari tanggal
            if (!groupedData.containsKey(bulanTahun)) {
                groupedData.put(bulanTahun, new ArrayList<>());
            }
            groupedData.get(bulanTahun).add(new Object[]{
                    tabels.getValueAt(i, 0),
                    tabels.getValueAt(i, 1),
                    tabels.getValueAt(i, 2),
                    tabels.getValueAt(i, 3),
                    tabels.getValueAt(i, 4)
            });
        }

        // Membuat dokumen PDF
        Document doc = new Document();
        try {
            // Mendapatkan path penyimpanan PDF dari pengguna
            String path = getPathFromUser();

            // Membuat file PDF dengan nama yang diberikan
            PdfWriter.getInstance(doc, new FileOutputStream(path + File.separator + fileName));
            doc.open();

            // Judul
            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, java.awt.Font.BOLD);
            Paragraph title = new Paragraph("Laporan Keuangan Laundry Visimala", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(Chunk.NEWLINE);

            // Menambahkan informasi tahun dan bulan
            doc.add(new Paragraph("Tahun: " + selectedYear));
            
            doc.add(Chunk.NEWLINE);

            // Membuat tabel untuk setiap bulan dan tahun
            for (Map.Entry<String, List<Object[]>> entry : groupedData.entrySet()) {
                String bulanTahun = entry.getKey();
                List<Object[]> data = entry.getValue();

                // Judul bulan dan tahun
                doc.add(new Paragraph("Bulan: " + bulanTahun));
                doc.add(Chunk.NEWLINE);

                // Tabel
                PdfPTable tbl = new PdfPTable(5);
                tbl.addCell(createCell("No", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell("Tanggal", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell("Laba", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell("Pemasukan", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell("Pengeluaran", 12, java.awt.Font.BOLD));

                // Mengisi data dari tabel
                for (Object[] row : data) {
                    tbl.addCell(createCell(row[0].toString(), 12, com.lowagie.text.Font.NORMAL));
                    tbl.addCell(createCell(row[1].toString(), 12, com.lowagie.text.Font.NORMAL));
                    tbl.addCell(createCell(row[2].toString(), 12, com.lowagie.text.Font.NORMAL));
                    tbl.addCell(createCell(row[3].toString(), 12, com.lowagie.text.Font.NORMAL));
                    tbl.addCell(createCell(row[4].toString(), 12, com.lowagie.text.Font.NORMAL));
                }

                // Menambahkan total laba, pemasukan, dan pengeluaran di bawah tabel
                tbl.addCell(createCell("Total", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell("", 12, java.awt.Font.BOLD));
                tbl.addCell(createCell(calculateTotal(data, 2), 12, java.awt.Font.BOLD)); // Kolom laba
                tbl.addCell(createCell(calculateTotal(data, 3), 12, java.awt.Font.BOLD)); // Kolom pemasukan
                tbl.addCell(createCell(calculateTotal(data, 4), 12, java.awt.Font.BOLD)); // Kolom pengeluaran

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

    // Fungsi untuk menghitung total jumlah dalam satu kolom dari data
    private String calculateTotal(List<Object[]> data, int columnIndex) {
        double total = 0;
        for (Object[] row : data) {
            String value = row[columnIndex].toString().replace("Rp.", "").trim(); // Menghilangkan "Rp." dan spasi
            total += Double.parseDouble(value);
        }
        return String.valueOf(total);
    }

    // Fungsi untuk mendapatkan bulan dan tahun dari tanggal
    private String getMonthYearFromDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Date tanggal;
        try {
            tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            tanggal = new Date(); // Default to current date if parsing fails
        }
        return sdf.format(tanggal);
    }

    private String getPathFromUser() {
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        return path;
    }

    public void setTabels(JTable tabels) {
        this.tabels = tabels;
    }

    public JTable getTabels() {
        return tabels;
    }
    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pilihTahun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pilihTahun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pilihTahun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pilihTahun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pilihTahun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combo_suggestion.ComboBoxSuggestion bulans;
    private javax.swing.JButton clears;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton pdff;
    private combo_suggestion.ComboBoxSuggestion tahuns;
    // End of variables declaration//GEN-END:variables
}
