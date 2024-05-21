package frame;

//import com.itextpdf.text.DocWriter;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import frame.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader; 
//import javax.swing.text.Document;
import koneksi.koneksi;

public class PanelLaporanKeuangan extends javax.swing.JPanel {

    private static boolean isRecordExistsForDate(Connection conn, LocalDate today) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;

    
    public PanelLaporanKeuangan() {
        koneksi();
        initComponents();
        //tambahBarisBaru();
        tabel();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
            (screenSize.width - frameSize.width) / 3,
            (screenSize.height -frameSize.height) / 4);   
    }    
    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
          
    
    private void koneksi() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
        con = koneksi.configDB(); // Assuming configDB returns a Connection object
        stat = con.createStatement();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: " + e.getMessage());
        e.printStackTrace(); // Cetak exception untuk debugging
    }
}
    
    private void tabel() {
        
        // Set show grid untuk menampilkan garis pembatas
    tabel.setShowGrid(true);

    // Set show horizontal lines dan show vertical lines
    tabel.setShowHorizontalLines(true);
    tabel.setShowVerticalLines(true);

     DefaultTableModel t = new DefaultTableModel() {
        // ... (kode yang ada sebelumnya)

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Membuat sel tidak dapat diedit langsung di tabel
        }
    };
     
    t.addColumn("No");
    t.addColumn("Tanggal");
    t.addColumn("Laba");
    t.addColumn("Pemasukan");
    t.addColumn("Pengeluaran");
    tabel.setModel(t);

    try {
        res = stat.executeQuery("SELECT * from laporan_keuangan");
        int rowCount = 0;
        while (res.next()) {
            t.addRow(new Object[]{
                    res.getString("id_laporan"),
                    res.getString("tgl_laporan"),
                    "Rp." +  res.getString("total"),
                    "Rp." +  res.getString("pemasukan"),
                    "Rp." + res.getString("pengeluaran")
                    
            });
     
      if (rowCount == 0) {
                tabel.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
                tabel.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                tabel.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
                tabel.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
                tabel.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
            }

            rowCount++;
        }
        // Menetapkan jumlah data ke JLabel
        //jLabel1.setText("Jumlah Data: " + rowCount);
    } catch (Exception e) {
        Component rootPane = null;
        JOptionPane.showMessageDialog(rootPane, e);
    }

    // Set rata tengah (centered) untuk semua sel di tabel
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

    for (int i = 0; i < tabel.getColumnCount(); i++) {
        tabel.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Set rata tengah (centered) untuk header kolom
    JTableHeader header = tabel.getTableHeader();
    header.setDefaultRenderer(centerRenderer);
    header.setPreferredSize(new Dimension(100, 40)); // Sesuaikan dimensi header jika diperlukan
    header.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14)); // Sesuaikan font header jika diperlukan
}
 
// Kelas renderer khusus untuk baris pertama
class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Mengubah warna latar belakang untuk baris pertama
        if (row == 0) {
            cellComponent.setBackground(new java.awt.Color(23, 233, 184)); // Ganti warna sesuai kebutuhan
        } else {
            cellComponent.setBackground(table.getBackground());
        }

        return cellComponent;
    }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        btn_pdf = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(tabel);
        if (tabel.getColumnModel().getColumnCount() > 0) {
            tabel.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 910, 420));

        jPanel1.setPreferredSize(new java.awt.Dimension(909, 51));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/solar_calendar-outline.png"))); // NOI18N

        btn_hapus.setBackground(new java.awt.Color(255, 0, 51));
        btn_hapus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.setPreferredSize(new java.awt.Dimension(85, 29));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/material-symbols_download.png"))); // NOI18N

        btn_pdf.setBackground(new java.awt.Color(204, 0, 51));
        btn_pdf.setText("PDF Ganerate");
        btn_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_pdf)
                .addGap(81, 81, 81))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_pdf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 910, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
   int row = tabel.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
            return;
        }
        int idLaporan = Integer.parseInt(tabel.getValueAt(row, 0).toString());

        // Hapus data dari database
        try {
            String query = "DELETE FROM laporan_keuangan WHERE id_laporan = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, idLaporan);
                pstmt.executeUpdate();
            }

            // Refresh tabel setelah penghapusan
            tabel();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pdfActionPerformed
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream(path + File.separator + "LaporanKeuangan.pdf"));
                doc.open();

                // Judul
                com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
                Paragraph title = new Paragraph("Laporan Keuangan Laundry Visimala", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                doc.add(title);
                doc.add(Chunk.NEWLINE);

                // Tabel
                PdfPTable tbl = new PdfPTable(5);
                tbl.addCell(createCell("No", 12, Font.BOLD));
                tbl.addCell(createCell("Tanggal", 12, Font.BOLD));
                tbl.addCell(createCell("Laba", 12, Font.BOLD));
                tbl.addCell(createCell("Pemasukan", 12, Font.BOLD));
                tbl.addCell(createCell("Pengeluaran", 12, Font.BOLD));

                // Mengisi data dari tabel
                for (int i = 0; i < tabel.getRowCount(); i++) {
                    String No = tabel.getValueAt(i, 0).toString();
                    String Tgl = tabel.getValueAt(i, 1).toString();
                    String Lb = tabel.getValueAt(i, 2).toString();
                    String Pmsk = tabel.getValueAt(i, 3).toString();
                    String Pnglr = tabel.getValueAt(i, 4).toString();
                
tbl.addCell(createCell(No, 12, com.lowagie.text.Font.NORMAL));
tbl.addCell(createCell(Tgl, 12, com.lowagie.text.Font.NORMAL));
tbl.addCell(createCell(Lb, 12, com.lowagie.text.Font.NORMAL));
tbl.addCell(createCell(Pmsk, 12, com.lowagie.text.Font.NORMAL));
tbl.addCell(createCell(Pnglr, 12, com.lowagie.text.Font.NORMAL));
                }

                doc.add(tbl);
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
    
        
 /*       
    String path = "";
JFileChooser j = new JFileChooser();
j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
int x = j.showSaveDialog(this);

if (x == JFileChooser.APPROVE_OPTION) {
    path = j.getSelectedFile().getPath();
    Document doc = new Document() {};
    try {
        PdfWriter.getInstance(doc, new FileOutputStream(path + File.separator + "abc.pdf"));
        doc.open();
        PdfPTable tbl = new PdfPTable(5);
        tbl.addCell("No");
        tbl.addCell("Tanggal");
        tbl.addCell("Laba");
        tbl.addCell("Pemasukan");
        tbl.addCell("Pengeluaran");
        for (int i = 0; i < tabel.getRowCount(); i++) {
            String No = tabel.getValueAt(i, 0).toString();
            String Tgl = tabel.getValueAt(i, 1).toString();
            String Lb = tabel.getValueAt(i, 2).toString();
            String Pmsk = tabel.getValueAt(i, 3).toString();
            String Pnglr = tabel.getValueAt(i, 4).toString();
            tbl.addCell(No);
            tbl.addCell(Tgl);
            tbl.addCell(Lb);
            tbl.addCell(Pmsk);
            tbl.addCell(Pnglr);
        }
        doc.add(tbl);
        JOptionPane.showMessageDialog(null, "PDF Generated");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Something Went Wrong: " + e.getMessage());
        e.printStackTrace(); // Print the stack trace for debugging
    } finally {
        if (doc != null) {
            doc.close();
        }
    }
} */
    }//GEN-LAST:event_btn_pdfActionPerformed

   private static void insertLaporanKeuangan(Connection conn) {
    LocalDate today = LocalDate.now();

    // Check if a record already exists for the current date
    if (isRecordExistsForDate(conn, today)) {
        System.out.println("Record already exists for today. Skipping insertion.");
        return;
    }

    String insertQuery = "INSERT INTO laporan_keuangan (tgl_laporan, total, pemasukan, pengeluaran) VALUES (?, ?, ?, ?)";
    try (java.sql.PreparedStatement insertPst = conn.prepareStatement(insertQuery)) {
        insertPst.setInt(1, 0);
        insertPst.setInt(2, 0);
        insertPst.setInt(3, 0);
        insertPst.setDate(4, java.sql.Date.valueOf(today));

        int rowsAffected = insertPst.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Insert successful");
        } else {
            System.out.println("Insert failed");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_pdf;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable tabel;
    // End of variables declaration//GEN-END:variables
}
