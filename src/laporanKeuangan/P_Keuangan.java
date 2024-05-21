/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package laporanKeuangan;
//import com.itextpdf.text.DocWriter;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import laporanKeuangan.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader; 
//import javax.swing.text.Document;
import koneksi.koneksi;
/**
 *
 * @author YAVIE
 */
public class P_Keuangan extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    public P_Keuangan() {
        koneksi();
        initComponents();
        //tambahBarisBaru();
        tabel();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
            (screenSize.width - frameSize.width) / 3,
            (screenSize.height -frameSize.height) / 4);   
        
        
            cariss.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            carissActionPerformed(evt);
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
     
    t.addColumn("Tanggal");
    t.addColumn("Laba");
    t.addColumn("Pemasukan");
    t.addColumn("Pengeluaran");
    tabels.setModel(t);

    try {
        res = stat.executeQuery("SELECT * from v_laporan_keuangan");
        int rowCount = 0;
        while (res.next()) {
            t.addRow(new Object[]{
                    res.getString("tanggal"),
                    "Rp." +  res.getString("pemasukan"),
                    "Rp." +  res.getString("pengeluaran"),
                    "Rp." + res.getString("laba")
                    
            });
     
      if (rowCount == 0) {
                tabels.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
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
        String query = "SELECT * FROM v_laporan_keuangan WHERE tanggal LIKE ? OR pemasukan LIKE ? OR pengeluaran LIKE ? OR laba LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            // Perhatikan bahwa kita telah menyesuaikan jumlah parameter menjadi 5
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }
            res = pstmt.executeQuery();
            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("tanggal"),
                    res.getString("pemasukan"),
                    res.getString("pengeluaran"),
                    res.getString("laba"),
                    // Tambahkan kolom lainnya sesuai kebutuhan
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

        datess = new Tanggal.DateChooser();
        pdff = new javax.swing.JLabel();
        hapuss = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cariss = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        datess.setFocusTraversalPolicyProvider(true);
        datess.setTextRefernce(cariss);

        setBackground(new java.awt.Color(237, 237, 237));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pdff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laporanKeuangan/pdf (5).png"))); // NOI18N
        pdff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pdffMouseClicked(evt);
            }
        });
        add(pdff, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 45, 30, -1));

        hapuss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laporanKeuangan/delete (2).png"))); // NOI18N
        hapuss.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hapussMouseClicked(evt);
            }
        });
        add(hapuss, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 45, 30, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laporanKeuangan/calendar (2).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 40, 30));

        cariss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carissActionPerformed(evt);
            }
        });
        add(cariss, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 180, 30));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 870, 390));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laporanKeuangan/backroundLaporanKeuangan2.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void carissActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carissActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carissActionPerformed

    private void hapussMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hapussMouseClicked
   int row = tabels.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
            return;
        }
        int idLaporan = Integer.parseInt(tabels.getValueAt(row, 0).toString());

        // Hapus data dari database
        try {
            String query = "DELETE FROM v_laporan_keuangan WHERE tanggal = ?";
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
    }//GEN-LAST:event_hapussMouseClicked

    private void pdffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdffMouseClicked
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
        pilihTahun frameP = new pilihTahun(); // Pastikan nama frame dan constructor Pilih diubah sesuai
        frameP.setVisible(true);
    }
    }//GEN-LAST:event_pdffMouseClicked
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

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        datess.showPopup();
        
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cariss;
    private Tanggal.DateChooser datess;
    private javax.swing.JLabel hapuss;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pdff;
    private javax.swing.JTable tabels;
    // End of variables declaration//GEN-END:variables
}
