
package Pengeluaran;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import mainOwner.MainOwner;

public class tambahData extends javax.swing.JPanel {
private Connection con;

    private Statement stat;
    private ResultSet res;
    private String t;
    
    private void autonumber(){  //untuk kodes (kode produknya biar otomatis)
            try {
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.Statement s=conn.createStatement();
                String sql = "SELECT * FROM pengeluaran ORDER BY id_pengeluaran DESC";
                ResultSet r = s.executeQuery(sql);
                if (r.next()) {
                    String no_transaksi = r.getString("id_pengeluaran").substring(2);
                    String PN = "" +(Integer.parseInt(no_transaksi)+1);
                    String Nol = "";
                    
                    if(PN.length()==1)
                        {Nol = "000";}
                    else if(PN.length()==2)
                    {Nol = "00";}
                    else if(PN.length()==3)
                    {Nol = "0";}
                    else if(PN.length()==4)
                    {Nol = "";}
                    kodePeng.setText("PN" + Nol + PN);
                } else {
                    kodePeng.setText("PN0001");
                }
                r.close();
                s.close();
            } catch (Exception e) {
    System.out.println("automatic error: " + e.getMessage());
            }
            }
    public tambahData() {
        koneksi();
        initComponents();
        
        kodePeng.setVisible(false);
// message semuanya
        

        kosongkan();
        autonumber();
                Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = dateFormat.format(date);
        Tanggal.setText(tanggal);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 3,
                (screenSize.height - frameSize.height) / 4);
    }
    private void koneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = koneksi.configDB();
            stat = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: ");
            e.printStackTrace(); // Cetak exception untuk debugging
        }
    }
    private void kosongkan() {
        kodePeng.setText("");
        Keterangan.setText("");
        Tanggal.setText("");
        Total.setText("");
    }
    
    private boolean isKeteranganExists(String ket, Connection con) {
    try {
        String query = "SELECT COUNT(*) FROM pengeluaran WHERE keterangan=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, ket);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simpan = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Keterangan = new javax.swing.JTextArea();
        kodePeng = new javax.swing.JTextField();
        Total = new javax.swing.JTextField();
        Tanggal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        simpan.setBackground(new java.awt.Color(10, 172, 42));
        simpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setBorder(null);
        simpan.setContentAreaFilled(false);
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        add(simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 130, 30));

        clear.setBackground(new java.awt.Color(11, 146, 215));
        clear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clear.setForeground(new java.awt.Color(255, 255, 255));
        clear.setBorder(null);
        clear.setContentAreaFilled(false);
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 490, 130, 30));

        Keterangan.setBackground(new java.awt.Color(255, 252, 252));
        Keterangan.setColumns(20);
        Keterangan.setLineWrap(true);
        Keterangan.setRows(1);
        Keterangan.setWrapStyleWord(true);
        jScrollPane1.setViewportView(Keterangan);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 300, 290, 100));

        kodePeng.setEditable(false);
        kodePeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodePengActionPerformed(evt);
            }
        });
        add(kodePeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 230, -1));

        Total.setBackground(new java.awt.Color(255, 252, 252));
        Total.setBorder(null);
        Total.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TotalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TotalFocusLost(evt);
            }
        });
        add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 235, 130, 20));

        Tanggal.setBackground(new java.awt.Color(255, 252, 252));
        Tanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Tanggal.setBorder(null);
        Tanggal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TanggalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TanggalFocusLost(evt);
            }
        });
        add(Tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 165, 130, 20));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pengeluaran/back.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pengeluaran/desainTambahPengeluaran.png"))); // NOI18N
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
     try {
        String kodePengeluaran = kodePeng.getText();
        String ket = Keterangan.getText().trim();
        String tanggal = Tanggal.getText().trim();
        String total = Total.getText().trim();

        if (t == null) {
            // Periksa apakah keterangan sudah ada dalam database
            if (isKeteranganExists(ket, con)) {
                JOptionPane.showMessageDialog(null, "Keterangan sudah ada dalam database.");
                return; // Langsung keluar dari metode simpan jika keterangan sudah ada
            }
            
            // Lakukan operasi penyimpanan data setelah validasi
            String insertQuery = "INSERT INTO pengeluaran(id_pengeluaran, tgl_pengeluaran, keterangan, total_pengeluaran) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPstmt.setString(1, kodePengeluaran);
                insertPstmt.setString(2, tanggal);
                insertPstmt.setString(3, ket);
                insertPstmt.setString(4, total);

                int affectedRows = insertPstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(tambahData.this);
                    mainInstance.showFormOwner(new Pengeluaran.pengeluaran(mainInstance));
                } else {
                    JOptionPane.showMessageDialog(null, "Data gagal disimpan, silakan coba lagi.");
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai !");
    }
    }//GEN-LAST:event_simpanActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
       kosongkan();
    }//GEN-LAST:event_clearActionPerformed

    private void TotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TotalFocusGained
        if(Total.getText().equals(" Hari...")) {
            Total.setText("");
            Total.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_TotalFocusGained

    private void TotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TotalFocusLost
        if(Total.getText().equals("")) {
            Total.setText(" Hari...");
            Total.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_TotalFocusLost

    private void TanggalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TanggalFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TanggalFocusGained

    private void TanggalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TanggalFocusLost
         // TODO add your handling code here:
    }//GEN-LAST:event_TanggalFocusLost

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
    mainInstance.showFormOwner(new Pengeluaran.pengeluaran(mainInstance));
    }//GEN-LAST:event_jLabel10MouseClicked

    private void kodePengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodePengActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodePengActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextArea Keterangan;
    private javax.swing.JTextField Tanggal;
    private javax.swing.JTextField Total;
    private javax.swing.JButton clear;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField kodePeng;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables

    
    
}
