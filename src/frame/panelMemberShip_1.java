/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader; 
import koneksi.koneksi;
/**
 *
 * @author YAVIE
 */
public class panelMemberShip_1 extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;    
    private static LocalDate lastExecutionDate = null;
       
    public panelMemberShip_1() {
        koneksi();
        initComponents();
        kosongkan();
        tabel();
        
        tanggals.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
            (screenSize.width - frameSize.width) / 3,
            (screenSize.height -frameSize.height) / 4);
        
//        tambahTanggalMember();
  //      batasWaktuSetmember();
    }
   
private void koneksi() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
        con = koneksi.configDB(); // Assuming configDB returns a Connection object
        stat = con.createStatement();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: " );
        e.printStackTrace(); // Cetak exception untuk debugging
    }
}

     private void kosongkan(){ 
        namas.setText("");
        alamats.setText("");
        hps.setText("");     
        
    }
    
    private void tabel(){
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
    t.addColumn("Id Member");
    t.addColumn("Nama Member");
    t.addColumn("Alamat");
    t.addColumn("No HP");
    t.addColumn("Tgl Pembuatan");
    t.addColumn("Tgl Kadulrasa");
    t.addColumn("Status Member");
    tabels.setModel(t);
    try {
        res = stat.executeQuery("SELECT * from member");
        int rowCount = 0;
 
        while (res.next()) {
            int statusMember = res.getInt("statusMember");
            if (statusMember == 0) {
                 t.addRow(new Object[]{
                        res.getString("id_member"),
                        res.getString("nama"),
                        res.getString("alamat"),
                        res.getString("no_hp"),
                        res.getString("tanggalPembuatan"),
                        res.getString("batas_waktu"),
                        "MASIH BERLAKU"
                });
            } else {
                 t.addRow(new Object[]{
                        res.getString("id_member"),
                        res.getString("nama"),
                        res.getString("alamat"),
                        res.getString("no_hp"),
                        res.getString("tanggalPembuatan"),
                        res.getString("batas_waktu"),
                        "SUDAH KADULRASA"
                });
            }
               
            if (rowCount == 0) {
                tabels.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
               tabels.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
                tabels.getColumnModel().getColumn(4).setCellRenderer(new CustomTableCellRenderer());
            }
            rowCount++;
        }
        // Menetapkan jumlah data ke JLabel
        //jLabel1member.setText("Jumlah Data: " + rowCount);
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
    model.setRowCount(0); // Clear existing rows

    try {
        // Use prepared statement to avoid SQL injection
        String query = "SELECT * FROM member WHERE id_member LIKE ? OR nama LIKE ? OR alamat LIKE ? OR no_hp LIKE ? OR batas_waktu LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }
            res = pstmt.executeQuery();

            while (res.next()) {
                int status = res.getInt("statusMember");
                String statusText;

                if (status == 0) {
                    statusText = "MASIH BERLAKU";
                } else {
                    statusText = "SUDAH KADALUARSA";
                }

                model.addRow(new Object[]{
                        res.getString("id_member"),
                        res.getString("nama"),
                        res.getString("alamat"),
                        res.getString("no_hp"),
                        res.getString("tanggalPembuatan"),
                        res.getString("batas_waktu"),
                        statusText
                        // Add other columns as needed
                });
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal mencari");
        // Handle or log the exception appropriately
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        caris = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        namas = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        simpans = new javax.swing.JButton();
        edits = new javax.swing.JButton();
        clears = new javax.swing.JButton();
        hapuss = new javax.swing.JButton();
        tanggals = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        alamats = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        hps = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        caris.setBackground(new java.awt.Color(77, 69, 69));
        caris.setForeground(new java.awt.Color(255, 255, 255));
        caris.setText("  Search . . .");
        caris.setBorder(null);
        caris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carisActionPerformed(evt);
            }
        });
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 190, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (35).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 360, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (26).png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (30).png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 530, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel4.setText("Table MemberShip");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        jLabel5.setText("Form MemberShip");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (30).png"))); // NOI18N
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 290, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Alamat");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("No HP");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Batas Waktu");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Nama ");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        namas.setBorder(null);
        namas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namasActionPerformed(evt);
            }
        });
        add(namas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 270, 20));

        jTextField3.setBorder(null);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 270, 20));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 1.png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 1.png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, -1, -1));

        simpans.setBackground(new java.awt.Color(10, 172, 42));
        simpans.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpans.setForeground(new java.awt.Color(255, 255, 255));
        simpans.setText("Simpan");
        simpans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpansActionPerformed(evt);
            }
        });
        add(simpans, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 90, -1));

        edits.setBackground(new java.awt.Color(255, 153, 0));
        edits.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        edits.setForeground(new java.awt.Color(255, 255, 255));
        edits.setText("Edit");
        edits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editsActionPerformed(evt);
            }
        });
        add(edits, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 530, 90, -1));

        clears.setBackground(new java.awt.Color(11, 146, 215));
        clears.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clears.setForeground(new java.awt.Color(255, 255, 255));
        clears.setText("Clear");
        clears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearsActionPerformed(evt);
            }
        });
        add(clears, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 530, 90, -1));

        hapuss.setBackground(new java.awt.Color(204, 0, 0));
        hapuss.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hapuss.setForeground(new java.awt.Color(255, 255, 255));
        hapuss.setText("Hapus");
        hapuss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapussActionPerformed(evt);
            }
        });
        add(hapuss, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 530, 90, -1));

        tanggals.setModel(new javax.swing.SpinnerDateModel());
        tanggals.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        add(tanggals, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 140, 30));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 530, 320));

        alamats.setBorder(null);
        alamats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatsActionPerformed(evt);
            }
        });
        add(alamats, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 270, 60));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 3.png"))); // NOI18N
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, -1, -1));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/solar_calendar-outline.png"))); // NOI18N
        jPanel1.add(jLabel11);

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        hps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (2).png"))); // NOI18N
        add(hps, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void namasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namasActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void simpansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpansActionPerformed
   try {
        // Get values from GUI components
        java.util.Date batas_waktu = (java.util.Date) tanggals.getValue();
        String nama = namas.getText();
        String alamat = alamats.getText();
        String no_hp = hps.getText();

        // Validate inputs
        if (nama.isEmpty() || alamat.isEmpty() || no_hp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama, Alamat, dan No HP tidak boleh kosong");
            return;
        }

        // Validate that no_hp contains only numeric values
        if (!no_hp.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Nomor HP harus berupa angka");
            return;
        }

        // Convert date to Timestamp
        Timestamp timestamp = new Timestamp(batas_waktu.getTime());

    if (t == null) {
       // New record insertion
       String insertQuery = "INSERT INTO member(nama, alamat, no_hp, tanggalDaftar, tanggalPembuatan, batas_waktu, statusMember) VALUES (?, ?, ?, ?, ?, ?, ?)";
       try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
           pstmt.setString(1, nama);
           pstmt.setString(2, alamat);
           pstmt.setString(3, no_hp);
           pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Assuming you want to set the current timestamp for "tanggalDaftar"
           pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
           pstmt.setTimestamp(6, timestamp);
           pstmt.setInt(7, 0);

           int affectedRows = pstmt.executeUpdate();
           if (affectedRows > 0) {
               JOptionPane.showMessageDialog(null, "Data baru berhasil disimpan");
//               batasWaktuSetmember();
           } else {
               JOptionPane.showMessageDialog(null, "Gagal menyimpan data baru");
           }
       }
   } else {
    // Update existing record
    String updateQuery = "UPDATE member SET nama=?, alamat=?, no_hp=?, batas_waktu=? WHERE id_member=?";
    try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
        pstmt.setString(1, nama);
        pstmt.setString(2, alamat);
        pstmt.setString(3, no_hp);
        pstmt.setTimestamp(4, timestamp);
        pstmt.setInt(5, Integer.parseInt(t));

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
//            batasWaktuSetmember();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal memperbarui data");
        }
    }
}

        // Refresh the table or perform any other necessary operations
        tabel();
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception stack trace for debugging
        JOptionPane.showMessageDialog(null, "Gagal Memperbarui Data: " + e.getMessage());
    }
    }//GEN-LAST:event_simpansActionPerformed
private void tampilkanDataBerdasarkanID(int idMember) {
  try {
        res = stat.executeQuery("SELECT * FROM member WHERE id_member='" + idMember + "'");
        while (res.next()) {
            // Assuming "tgl_pengeluaran" is a Date column in the database
            java.sql.Date sqlDate = res.getDate("batas_waktu");
            java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
            // Assuming that spinner_tanggal is a JSpinner
            tanggals.setValue(utilDate);
            // Replace these column names with actual column names in your table
            namas.setText(res.getString("nama"));
            alamats.setText(res.getString("alamat"));
            hps.setText(res.getString("no_hp"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    private void editsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editsActionPerformed
// Dapatkan baris yang dipilih
    int row = tabels.getSelectedRow(); 

    // Pastikan ada baris yang dipilih
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang akan diedit");
        return;
    }
    // Dapatkan ID Member dari kolom pertama (indeks 0)
    int idMember = Integer.parseInt(tabels.getValueAt(row, 0).toString());
    
    // Ambil nilai dari database dan tampilkan di JTextField
    tampilkanDataBerdasarkanID(idMember);

    // Simpan ID member yang akan diupdate (digunakan pada aksi btn_simpanActionPerformed)
    t = Integer.toString(idMember);
    
    }//GEN-LAST:event_editsActionPerformed

    private void hapussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapussActionPerformed
int row = tabels.getSelectedRow();

// Ensure a row is selected
if (row == -1) {
    JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
    return;
}

// Get the ID Member from the first column (index 0)
int idMember = Integer.parseInt(tabels.getValueAt(row, 0).toString());

try {
    // Delete data from the database
    String query = "DELETE FROM member WHERE id_member = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, idMember);
        pstmt.executeUpdate();
    }

    // Remove row from the table
    DefaultTableModel model = (DefaultTableModel) tabels.getModel();
    model.removeRow(row);

    // Update the number of data
    int rowCount = model.getRowCount();
    //jLabel1member.setText("Jumlah Data: " + rowCount);

    JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, e );
}
    }//GEN-LAST:event_hapussActionPerformed

    private void alamatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatsActionPerformed

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
        String kataKunci = caris.getText();
        cariData(kataKunci);
    }//GEN-LAST:event_carisActionPerformed

    private void clearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearsActionPerformed
        kosongkan();
        t = null;
    }//GEN-LAST:event_clearsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamats;
    private javax.swing.JTextField caris;
    private javax.swing.JButton clears;
    private javax.swing.JButton edits;
    private javax.swing.JButton hapuss;
    private javax.swing.JLabel hps;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField namas;
    private javax.swing.JButton simpans;
    private javax.swing.JTable tabels;
    private javax.swing.JSpinner tanggals;
    // End of variables declaration//GEN-END:variables
}
