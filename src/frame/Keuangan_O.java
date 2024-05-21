
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
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

public class Keuangan_O extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    /**
     * Creates new form panelKeuangan
     */
    public Keuangan_O() {
        koneksi();
        initComponents();
        //tambahBarisBaru();
        tabel();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
            (screenSize.width - frameSize.width) / 3,
            (screenSize.height -frameSize.height) / 4);   
        
        
            caris.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            carisActionPerformed(evt);
        }
    });

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
    tabels.setShowGrid(true);

    // Set show horizontal lines dan show vertical lines
    tabels.setShowHorizontalLines(true);
    tabels.setShowVerticalLines(true);

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
    tabels.setModel(t);

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
                tabels.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
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

    for (int i = 0; i < tabels.getColumnCount(); i++) {
        tabels.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Set rata tengah (centered) untuk header kolom
    JTableHeader header = tabels.getTableHeader();
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
private void cariData(String kataKunci) {
    DefaultTableModel model = (DefaultTableModel) tabels.getModel();
    model.setRowCount(0); // Bersihkan baris yang sudah ada

    try {
        String query = "SELECT * FROM laporan_keuangan WHERE id_laporan LIKE ? OR tgl_laporan LIKE ? OR total LIKE ? OR pemasukan LIKE ? OR pengeluaran LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            // Perhatikan bahwa kita telah menyesuaikan jumlah parameter menjadi 5
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }
            res = pstmt.executeQuery();
            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("id_laporan"),
                    res.getString("tgl_laporan"),
                    res.getString("total"),
                    res.getString("pemasukan"),
                    res.getString("pengeluaran"), // Tambahkan kolom lainnya sesuai kebutuhan
                });
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
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

        date = new Tanggal.DateChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        caris = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        ttgl = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tagl = new javax.swing.JTextField();
        pdff = new javax.swing.JButton();
        hapuss = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        date.setForeground(new java.awt.Color(103, 148, 123));
        date.setDateFormat("dd-MMM-yyyy");
        date.setTextRefernce(tagl);

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/delete (2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/pdf (5).png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 60, 120));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/application.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 60, 50));

        caris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carisActionPerformed(evt);
            }
        });
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 180, 30));

        tabels.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabels);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 900, 420));

        ttgl.setBackground(new java.awt.Color(255, 255, 255));
        ttgl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ttglMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/calendar (2).png"))); // NOI18N

        javax.swing.GroupLayout ttglLayout = new javax.swing.GroupLayout(ttgl);
        ttgl.setLayout(ttglLayout);
        ttglLayout.setHorizontalGroup(
            ttglLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ttglLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );
        ttglLayout.setVerticalGroup(
            ttglLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        add(ttgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, 30, 30));

        tagl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taglActionPerformed(evt);
            }
        });
        add(tagl, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 160, 30));

        pdff.setBackground(new java.awt.Color(237, 17, 17));
        pdff.setForeground(new java.awt.Color(255, 255, 255));
        pdff.setText("PDF");
        pdff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdffActionPerformed(evt);
            }
        });
        add(pdff, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, 90, 30));

        hapuss.setBackground(new java.awt.Color(237, 17, 17));
        hapuss.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hapuss.setForeground(new java.awt.Color(255, 255, 255));
        hapuss.setText("Hapus");
        hapuss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapussActionPerformed(evt);
            }
        });
        add(hapuss, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 90, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (19).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void ttglMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttglMouseClicked
        // TODO add your handling code here:
        date.showPopup();
    }//GEN-LAST:event_ttglMouseClicked

    private void hapussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapussActionPerformed
   int row = tabels.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
            return;
        }
        int idLaporan = Integer.parseInt(tabels.getValueAt(row, 0).toString());

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
            // TODO add your handling code here:
    }//GEN-LAST:event_hapussActionPerformed

    private void pdffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdffActionPerformed
     int choice = JOptionPane.showOptionDialog(null, "Pilih periode waktu:", "Pilih Periode",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            new String[]{"Rentang Waktu", "Per Bulan"}, // Pilihan: Per minggu atau per bulan
            "Per Minggu");

    if (choice == JOptionPane.YES_OPTION) { // Jika dipilih "Per Minggu"
        // Buka jendela untuk memilih periode mingguan
        Pilih framePilih = new Pilih(); // Pastikan nama frame dan constructor Pilih diubah sesuai
        framePilih.setVisible(true);
        
    } else if (choice == JOptionPane.NO_OPTION) { // Jika dipilih "Per Bulan"
        // Tampilkan frame untuk memilih tahun
        PilihTahunCombo frameP = new PilihTahunCombo(); // Pastikan nama frame dan constructor Pilih diubah sesuai
        frameP.setVisible(true);
    }
    
        /*   int choice = JOptionPane.showOptionDialog(null, "Pilih periode waktu:", "Pilih Periode",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            new String[]{"Rentang Waktu", "Per Bulan"}, // Pilihan: Per minggu atau per bulan
            "Per Minggu");

    if (choice == JOptionPane.YES_OPTION) { // Jika dipilih "Per Minggu"
        // Buka jendela untuk memilih periode mingguan
        Pilih framePilih = new Pilih(); // Pastikan nama frame dan constructor Pilih diubah sesuai
        framePilih.setVisible(true);
        
    } else if (choice == JOptionPane.NO_OPTION) { // Jika dipilih "Per Bulan"
        pilihtahun frameP = new pilihtahun(); // Pastikan nama frame dan constructor Pilih diubah sesuai
        frameP.setVisible(true);
        
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

        // Membuat file PDF dengan nama "LaporanKeuangan.pdf"
        PdfWriter.getInstance(doc, new FileOutputStream(path + File.separator + "LaporanKeuanganPerbulan.pdf"));
        doc.open();

        // Judul
        com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
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
            PdfPTable tbl = new PdfPTable(5);
            tbl.addCell(createCell("No", 12, Font.BOLD));
            tbl.addCell(createCell("Tanggal", 12, Font.BOLD));
            tbl.addCell(createCell("Laba", 12, Font.BOLD));
            tbl.addCell(createCell("Pemasukan", 12, Font.BOLD));
            tbl.addCell(createCell("Pengeluaran", 12, Font.BOLD));

            // Mengisi data dari tabel
            for (Object[] row : data) {
                tbl.addCell(createCell(row[0].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[1].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[2].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[3].toString(), 12, com.lowagie.text.Font.NORMAL));
                tbl.addCell(createCell(row[4].toString(), 12, com.lowagie.text.Font.NORMAL));
            }

            // Menambahkan total laba, pemasukan, dan pengeluaran di bawah tabel
            tbl.addCell(createCell("Total", 12, Font.BOLD));
            tbl.addCell(createCell("", 12, Font.BOLD));
            tbl.addCell(createCell(calculateTotal(data, 2), 12, Font.BOLD)); // Kolom laba
            tbl.addCell(createCell(calculateTotal(data, 3), 12, Font.BOLD)); // Kolom pemasukan
            tbl.addCell(createCell(calculateTotal(data, 4), 12, Font.BOLD)); // Kolom pengeluaran

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
} */
    }//GEN-LAST:event_pdffActionPerformed

    private void taglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taglActionPerformed
//
//        String kataKunci = tagl.getText();
//        cariData(kataKunci);
//        
    }//GEN-LAST:event_taglActionPerformed

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
        // TODO add your handling code here:
        
        String kataKunci = caris.getText();
        cariData(kataKunci);
        
    }//GEN-LAST:event_carisActionPerformed
// Fungsi untuk menghitung total jumlah dalam satu kolom dari data
/*private String calculateTotal(List<Object[]> data, int columnIndex) {
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

// Fungsi untuk mendapatkan path penyimpanan PDF dari pengguna
private String getPathFromUser() {
    String path = "";
    JFileChooser j = new JFileChooser();
    j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int x = j.showSaveDialog(this);

    if (x == JFileChooser.APPROVE_OPTION) {
        path = j.getSelectedFile().getPath();
    }

    return path;
} */
public JTable getTabels() {
    return tabels;
}

public String getPathForPDF() {
    String path = "";
    JFileChooser j = new JFileChooser();
    j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int x = j.showSaveDialog(this);

    if (x == JFileChooser.APPROVE_OPTION) {
        path = j.getSelectedFile().getPath();
    }

    return path;
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caris;
    private Tanggal.DateChooser date;
    private javax.swing.JButton hapuss;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton pdff;
    private javax.swing.JTable tabels;
    private javax.swing.JTextField tagl;
    private javax.swing.JPanel ttgl;
    // End of variables declaration//GEN-END:variables
}
