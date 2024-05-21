
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

public class panelKeuangan_1 extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    /**
     * Creates new form panelKeuangan
     */
    public panelKeuangan_1() {
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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 860, -1));

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
            .addGroup(ttglLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        add(ttgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 30, 30));
        add(tagl, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 160, -1));

        pdff.setBackground(new java.awt.Color(255, 0, 0));
        pdff.setForeground(new java.awt.Color(255, 255, 255));
        pdff.setText("PDF");
        pdff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdffActionPerformed(evt);
            }
        });
        add(pdff, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 70, 90, -1));

        hapuss.setBackground(new java.awt.Color(237, 17, 17));
        hapuss.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hapuss.setForeground(new java.awt.Color(255, 255, 255));
        hapuss.setText("Hapus");
        hapuss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapussActionPerformed(evt);
            }
        });
        add(hapuss, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (9).png"))); // NOI18N
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
}
    }//GEN-LAST:event_pdffActionPerformed
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
}
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
    private Tanggal.DateChooser date;
    private javax.swing.JButton hapuss;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton pdff;
    private javax.swing.JTable tabels;
    private javax.swing.JTextField tagl;
    private javax.swing.JPanel ttgl;
    // End of variables declaration//GEN-END:variables
}
