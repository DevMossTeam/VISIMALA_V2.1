package absensiowner;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
//import static testloginpdf.userId;

public class Pilih extends javax.swing.JFrame {
private String startDate;
private String endDate;

public Pilih() {
    initComponents();
}

private void setUserID(int userID) {
    IDlabel.setText("User ID: " + userID);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        date1 = new Tanggal.DateChooser();
        date2 = new Tanggal.DateChooser();
        IDlabel = new javax.swing.JLabel();
        pdf_btn = new javax.swing.JButton();
        clear_btn = new javax.swing.JButton();
        kembali = new javax.swing.JPanel();
        dateS = new javax.swing.JPanel();
        dateP = new javax.swing.JPanel();
        sampais = new javax.swing.JTextField();
        daris = new javax.swing.JTextField();
        bg = new javax.swing.JLabel();

        date1.setForeground(new java.awt.Color(153, 153, 255));
        date1.setDateFormat("yyyy-MM-dd");
        date1.setTextRefernce(daris);

        date2.setForeground(new java.awt.Color(0, 153, 153));
        date2.setDateFormat("yyyy-MM-dd");
        date2.setTextRefernce(sampais);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(IDlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        pdf_btn.setBackground(new java.awt.Color(255, 0, 0));
        pdf_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pdf_btn.setForeground(new java.awt.Color(255, 255, 255));
        pdf_btn.setBorder(null);
        pdf_btn.setContentAreaFilled(false);
        pdf_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdf_btnActionPerformed(evt);
            }
        });
        getContentPane().add(pdf_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 60, 20));

        clear_btn.setBackground(new java.awt.Color(0, 204, 204));
        clear_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clear_btn.setForeground(new java.awt.Color(255, 255, 255));
        clear_btn.setBorder(null);
        clear_btn.setContentAreaFilled(false);
        clear_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_btnActionPerformed(evt);
            }
        });
        getContentPane().add(clear_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 70, 20));

        kembali.setBackground(new java.awt.Color(255, 255, 255));
        kembali.setOpaque(false);
        kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembaliMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kembaliLayout = new javax.swing.GroupLayout(kembali);
        kembali.setLayout(kembaliLayout);
        kembaliLayout.setHorizontalGroup(
            kembaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        kembaliLayout.setVerticalGroup(
            kembaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 20, 20));

        dateS.setBackground(new java.awt.Color(255, 255, 255));
        dateS.setOpaque(false);
        dateS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateSMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dateSLayout = new javax.swing.GroupLayout(dateS);
        dateS.setLayout(dateSLayout);
        dateSLayout.setHorizontalGroup(
            dateSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        dateSLayout.setVerticalGroup(
            dateSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(dateS, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 20, 20));

        dateP.setBackground(new java.awt.Color(255, 255, 255));
        dateP.setOpaque(false);
        dateP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datePMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout datePLayout = new javax.swing.GroupLayout(dateP);
        dateP.setLayout(datePLayout);
        datePLayout.setHorizontalGroup(
            datePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        datePLayout.setVerticalGroup(
            datePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(dateP, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 20, 20));

        sampais.setBorder(null);
        getContentPane().add(sampais, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 170, 20));

        daris.setBorder(null);
        getContentPane().add(daris, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 170, 20));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensiowner/Group 357.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(400, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void datePMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datePMouseClicked
        date1.showPopup();
    }//GEN-LAST:event_datePMouseClicked

    private void dateSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateSMouseClicked
        date2.showPopup();
    }//GEN-LAST:event_dateSMouseClicked

    private void kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliMouseClicked
        dispose();
    }//GEN-LAST:event_kembaliMouseClicked

    private void clear_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_btnActionPerformed
            sampais.setText("");
    daris.setText("");
    }//GEN-LAST:event_clear_btnActionPerformed

    private void pdf_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdf_btnActionPerformed
        // Simpan tanggal dari daris dan sampais ke variabel startDate dan endDate
        startDate = daris.getText();
        endDate = sampais.getText();

        createPDF("Laporan Absensi.pdf", startDate, endDate);
    }//GEN-LAST:event_pdf_btnActionPerformed
private void createPDF(String path, String startDate, String endDate) {
    Map<String, List<Object[]>> groupedData = new TreeMap<>();

    try (Connection conn = koneksi.configDB()) {
        String query = "SELECT a.id_absensi, u.username, a.status_absensi, a.time_in, a.time_out " +
                       "FROM absensi a JOIN user u ON a.id_user = u.id_user " +
                       "WHERE a.time_in BETWEEN ? AND ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, startDate);
            pst.setString(2, endDate);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String tgl = rs.getString("time_in");
                    String bulanTahun = getMonthYearFromDate(tgl);
                    if (!groupedData.containsKey(bulanTahun)) {
                        groupedData.put(bulanTahun, new ArrayList<>());
                    }
                    groupedData.get(bulanTahun).add(new Object[]{
                        getDayFromDateTime(tgl),
                        rs.getString("username"),
                        rs.getString("status_absensi"),
                        getTimeWithoutSeconds(rs.getString("time_in")),
                        getTimeWithoutSeconds(rs.getString("time_out"))
                    });
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        return;
    }

    // Check if any data was fetched
    if (groupedData.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No data found for the given date range", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Membuat dokumen PDF
    Document doc = new Document();
    try {
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();

        // Judul
        com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
        Paragraph title = new Paragraph("Laporan Absensi Laundry Visimala", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
        doc.add(Chunk.NEWLINE);

        // Membuat tabel untuk setiap bulan dan tahun
        for (Map.Entry<String, List<Object[]>> entry : groupedData.entrySet()) {
            String bulanTahun = entry.getKey();
            List<Object[]> data = entry.getValue();

            // Judul bulan dan tahun
            doc.add(new Paragraph("Bulan: " + capitalizeFirstLetter(bulanTahun)));
            doc.add(Chunk.NEWLINE);

            // Tabel
            PdfPTable tbl = new PdfPTable(5);
            tbl.addCell(createCell("Tanggal", 12, Font.BOLD));
            tbl.addCell(createCell("Username", 12, Font.BOLD));
            tbl.addCell(createCell("Status Absensi", 12, Font.BOLD));
            tbl.addCell(createCell("Time In", 12, Font.BOLD));
            tbl.addCell(createCell("Time Out", 12, Font.BOLD));

            // Mengisi data dari tabel
            for (Object[] row : data) {
                tbl.addCell(createCell(row[0].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(capitalizeFirstLetter(row[1].toString()), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(capitalizeFirstLetter(row[2].toString()), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[3].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[4].toString(), 12, com.lowagie.text.Font.NORMAL));
            }

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

private String getDayFromDateTime(String dateTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
    Date date;
    try {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
    } catch (ParseException e) {
        date = new Date(); // Default to current date if parsing fails
    }
    return sdf.format(date);
}

private String getTimeWithoutSeconds(String dateTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
    Date date;
    try {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
    } catch (ParseException e) {
        date = new Date(); // Default to current date and time if parsing fails
    }
    return sdf.format(date);
}

private String capitalizeFirstLetter(String input) {
    String[] words = input.split(" ");
    StringBuilder capitalized = new StringBuilder();
    for (String word : words) {
        if (word.length() > 0) {
            capitalized.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
    }
    return capitalized.toString().trim();
}

private PdfPCell createCell(String content, float fontSize, int fontStyle) {
    com.lowagie.text.Font cellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize, fontStyle);
    PdfPCell cell = new PdfPCell(new Phrase(content, cellFont));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
}

//public static void main(String args[]) {
//    java.awt.EventQueue.invokeLater(new Runnable() {
//        public void run() {
//            new Pilih().setVisible(true);
//        }
//    });
//}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IDlabel;
    private javax.swing.JLabel bg;
    private javax.swing.JButton clear_btn;
    private javax.swing.JTextField daris;
    private Tanggal.DateChooser date1;
    private Tanggal.DateChooser date2;
    private javax.swing.JPanel dateP;
    private javax.swing.JPanel dateS;
    private javax.swing.JPanel kembali;
    private javax.swing.JButton pdf_btn;
    private javax.swing.JTextField sampais;
    // End of variables declaration//GEN-END:variables
}