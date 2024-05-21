package Deposit;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import mainOwner.MainOwner;

public class DaftarDeposit extends javax.swing.JPanel {
    private Connection connection;

    public DaftarDeposit() {
        initComponents();
         try {
        // Inisialisasi koneksi database
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Penanganan kesalahan koneksi database
    }
    }
    
     private void clearFields() {
        txt_nama.setText("");
        txt_NoHp.setText("");
        txt_alamat.setText("");
        txt_saldo.setText("");
    }
     
    
  private String generateUniqueCode(String prefix, String tableName, String columnName) {
   String uniqueCode = null;
    try {
        // Persiapkan statement SQL untuk mendapatkan nilai auto_increment terbaru
        String sql = "SELECT MAX(" + columnName + ") FROM " + tableName;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        int nextId = 1;
        if (resultSet.next()) {
            String lastCode = resultSet.getString(1);
            if (lastCode != null && !lastCode.isEmpty()) {
                // Ekstrak angka dari kode terakhir dan tambahkan 1
                int lastId = Integer.parseInt(lastCode.replaceAll("[^0-9]", ""));
                nextId = lastId + 1;
            }
        }

        // Menggunakan nilai nextId untuk membuat kode yang unik
        uniqueCode = prefix + String.format("%03d", nextId);

        // Loop untuk memastikan kode unik
        while (isCodeExist(uniqueCode, tableName, columnName)) {
            nextId++;
            uniqueCode = prefix + String.format("%03d", nextId);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Penanganan kesalahan
    }
    return uniqueCode;
}

   
   private boolean isCodeExist(String code, String tableName, String columnName) {
    try {
        // Persiapkan statement SQL untuk memeriksa apakah kode sudah ada dalam database
        String sql = "SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, code);
        ResultSet resultSet = statement.executeQuery();
        
        // Jika hasil query mengandung data, berarti kode sudah ada
        return resultSet.next();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Penanganan kesalahan
    }
    return false;
}
   


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_kembali = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        txt_NoHp = new javax.swing.JTextField();
        btn_clear = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat = new javax.swing.JTextArea();
        txt_saldo = new javax.swing.JTextField();
        kodePelanggan = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 523, -1, -1));

        txt_nama.setBackground(new java.awt.Color(255, 252, 252));
        txt_nama.setToolTipText("");
        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 116, 288, 22));

        txt_NoHp.setBackground(new java.awt.Color(255, 252, 252));
        txt_NoHp.setToolTipText("");
        txt_NoHp.setBorder(null);
        add(txt_NoHp, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 188, 288, 22));

        btn_clear.setBorder(null);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 472, 143, 42));

        btn_simpan.setBorder(null);
        btn_simpan.setContentAreaFilled(false);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 472, 141, 42));

        txt_alamat.setColumns(20);
        txt_alamat.setRows(5);
        txt_alamat.setBorder(null);
        jScrollPane1.setViewportView(txt_alamat);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, 300, 100));

        txt_saldo.setBackground(new java.awt.Color(255, 252, 252));
        txt_saldo.setBorder(null);
        add(txt_saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 263, 284, 23));

        kodePelanggan.setText("jLabel1");
        add(kodePelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/DaftarDeposit.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
   txt_nama.setText("");
   txt_NoHp.setText("");
   txt_alamat.setText("");
   txt_saldo.setText("");
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
    String nama = txt_nama.getText();
    String noHp = txt_NoHp.getText();
    String alamat = txt_alamat.getText();
    String saldo = txt_saldo.getText();

     // Validasi input kosong
    if (nama.isEmpty() || noHp.isEmpty() || alamat.isEmpty() || saldo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mohon Lengkapi Data.");
        return;
    }
    
    // Validasi untuk nomor HP hanya mengandung angka
    if (!noHp.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Format Tidak Valid");
        return;
    }

    // Validasi saldo adalah angka
    try {
        int saldoInt = Integer.parseInt(saldo);
        if (saldoInt == 0) {
            JOptionPane.showMessageDialog(this, "Mohon Isi Saldo dengan Angka Lebih dari 0");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Mohon Isi Saldo dengan Angka");
        return;
    }

    String kodePelanggan = generateUniqueCode("KP", "pelanggan", "kode_pelanggan");
    String idDepo = generateUniqueCode("DPT", "deposit", "id_depo");
    String waktuDepo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    try {
        if (connection == null) {
            System.out.println("Koneksi ke database belum diinisialisasi.");
            return;
        }

        String sqlPelanggan = "INSERT INTO pelanggan (nama, No_Hp, alamat, kode_pelanggan) VALUES (?, ?, ?, ?)";
        PreparedStatement statementPelanggan = connection.prepareStatement(sqlPelanggan);
        statementPelanggan.setString(1, nama);
        statementPelanggan.setString(2, noHp);
        statementPelanggan.setString(3, alamat);
        statementPelanggan.setString(4, kodePelanggan);
        statementPelanggan.executeUpdate();
        statementPelanggan.close();

        String sqlDeposit = "INSERT INTO deposit (id_depo, kode_pelanggan, jumlah_depo, waktu_depo) VALUES (?, ?, ?, ?)";
        PreparedStatement statementDeposit = connection.prepareStatement(sqlDeposit);
        statementDeposit.setString(1, idDepo);
        statementDeposit.setString(2, kodePelanggan);
        statementDeposit.setString(3, saldo);
        statementDeposit.setString(4, waktuDepo);
        statementDeposit.executeUpdate();
        statementDeposit.close();

        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");

        clearFields();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menyimpan data");
    }

        
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
       
        MainOwner mainDepo = (MainOwner) SwingUtilities.getWindowAncestor(this);
        mainDepo.showFormOwner(new Deposit.TableDeposit(mainDepo));  
    }//GEN-LAST:event_btn_kembaliMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel kodePelanggan;
    private javax.swing.JTextField txt_NoHp;
    private javax.swing.JTextArea txt_alamat;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_saldo;
    // End of variables declaration//GEN-END:variables
}
