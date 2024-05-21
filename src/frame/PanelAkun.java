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


public class PanelAkun extends javax.swing.JPanel {
private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    
    public PanelAkun() {
        koneksi();
        initComponents();
        kosongkan();
    }
    
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
     
     
    private void kosongkan(){ 
        txt_nama.setText("");
        txt_Jabatan.setText("");
        txt_Email.setText("");
        
    }
    
    
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        txt_Jabatan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btn_hapus = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/add 1.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel3.setText("Form Akun");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 160, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (22).png"))); // NOI18N
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 280, 70));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel5.setText("Table Akun");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, -1, 30));

        jTextField1.setBackground(new java.awt.Color(128, 102, 102));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("  Search ...");
        jTextField1.setPreferredSize(new java.awt.Dimension(48, 22));
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 140, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (22).png"))); // NOI18N
        jLabel6.setText("jLabel6");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 520, 90));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Nama");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 260, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Jabatan");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        txt_Email.setBorder(null);
        add(txt_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 260, 30));

        txt_Jabatan.setBorder(null);
        txt_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_JabatanActionPerformed(evt);
            }
        });
        add(txt_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 270, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Email");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        btn_clear.setBackground(new java.awt.Color(0, 102, 255));
        btn_clear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 100, -1));

        btn_simpan.setBackground(new java.awt.Color(51, 153, 0));
        btn_simpan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 540, 110, -1));

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 160, 520, 290));

        btn_hapus.setBackground(new java.awt.Color(204, 0, 51));
        btn_hapus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 540, -1, -1));

        btn_edit.setBackground(new java.awt.Color(255, 102, 0));
        btn_edit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 540, 90, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 3.png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 2.png"))); // NOI18N
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 1.png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (20).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
      try {
        String username = txt_nama.getText();
        String email = txt_Jabatan.getText();
        String jabatan = txt_Email.getText();

        // Check if required fields are not null or empty
        if (username.isEmpty() || jabatan.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username, Jabatan, and Email cannot be null or empty");
            return; // Exit the method if validation fails
        }

        // Check if the data with the specified username already exists
        String checkQuery = "SELECT id_Pegawai FROM user WHERE Username=?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Data with the given username already exists, perform update
                String updateQuery = "UPDATE user SET Username=?, Jabatan=?, Email=? WHERE Username=?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, jabatan);
                    updateStmt.setString(2, txt_Email.getText());
                    updateStmt.setString(3, username);
                    updateStmt.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Berhasil Memperbarui Data");
            } else {
                // Data with the given username does not exist, perform insert
// Data with the given username does not exist, perform insert
        String insertQuery = "INSERT INTO user(Username, jabatan, email,) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, username);
            insertStmt.setString(2, jabatan);
            insertStmt.setString(3, "Pegawai"); // Set jabatan to "Pegawai"
            insertStmt.setString(4, txt_Email.getText());
            int affectedRows = insertStmt.executeUpdate(); 

            if (affectedRows > 0) {
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idUser = generatedKeys.getInt(1);
                    // Handle the ID if needed
                    System.out.println("ID User baru: " + idUser);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data");

            }
        }
        tabel();
        kosongkan();
        // Additional logic as needed
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal memperbarui data! " + e.getMessage());
    }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txt_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_JabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_JabatanActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        kosongkan();
        t = null;
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int row = tabel.getSelectedRow();

// Ensure a row is selected
if (row == -1) {
    JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
    return;
}

// Get the ID Member from the first column (index 0)
int idPegawai = Integer.parseInt(tabel.getValueAt(row, 0).toString());

try {
    // Delete data from the database
    String query = "DELETE FROM User WHERE id_Pegawai = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, idPegawai);
        pstmt.executeUpdate();
    }

    // Remove row from the table
    DefaultTableModel model = (DefaultTableModel) tabel.getModel();
    model.removeRow(row);

    // Update the number of data
    int rowCount = model.getRowCount();
    jLabel7.setText("Jumlah Data: " + rowCount);

    JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, e );
}
   
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Jabatan;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
