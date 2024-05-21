package Deposit;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import mainOwner.MainOwner;


public class TopUpDeposit extends javax.swing.JPanel {
    private mainKaryawan.MainKaryawan mainInstance;
//    private String iddepo;
    private String nama;
    private String nomorHp;
    
    private static final String URL = "jdbc:mysql://localhost:3306/project_laundryku"; // Update with your database URL
    private static final String USER = "root"; // Update with your database username
    private static final String PASSWORD = ""; // Update with your database password


    public TopUpDeposit() {
        this.mainInstance =  mainInstance;
//        this.iddepo = iddepo;
        this.nama = nama;
        this.nomorHp = nomorHp;
        initComponents();
        
         iddepo.setVisible(false);
        checkSaldoField(); // Call checkSaldoField() when the panel is created
        txt_saldo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkSaldoField(); // Call checkSaldoField() when the text in txt_saldo changes
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkSaldoField(); // Call checkSaldoField() when the text in txt_saldo changes
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkSaldoField(); // Call checkSaldoField() when the text in txt_saldo changes
            }
        });
        txt_nama.setText(nama);
        txt_NomorHp.setText(nomorHp);
    }
    
     private void checkSaldoField() {
        String saldoText = txt_saldo.getText().trim();
        if (saldoText.isEmpty() || !saldoText.matches("\\d+")) {
            lbl_simpan.setVisible(false); // Hide lbl_simpan if saldo is empty or not valid
        } else {
            lbl_simpan.setVisible(true);
        }
    }
     
      
    private void clearFields() {
    txt_NomorHp.setText("");
    txt_nama.setText("");
    txt_saldo.setText("");
}
  
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        iddepo = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JLabel();
        txt_NomorHp = new javax.swing.JTextField();
        btn_clear = new javax.swing.JButton();
        txt_saldo = new javax.swing.JTextField();
        lbl_simpan = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iddepo.setText("jLabel1");
        add(iddepo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, -1, -1));

        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 522, -1, -1));

        txt_NomorHp.setBackground(new java.awt.Color(255, 252, 252));
        txt_NomorHp.setToolTipText("");
        txt_NomorHp.setBorder(null);
        txt_NomorHp.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_NomorHp.setEnabled(false);
        add(txt_NomorHp, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 167, 280, 28));

        btn_clear.setBorder(null);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(794, 434, 142, 40));

        txt_saldo.setBackground(new java.awt.Color(255, 252, 252));
        txt_saldo.setBorder(null);
        txt_saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saldoActionPerformed(evt);
            }
        });
        add(txt_saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 324, 284, 26));

        lbl_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/Simpan.png"))); // NOI18N
        lbl_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_simpanMouseClicked(evt);
            }
        });
        add(lbl_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 435, 150, -1));

        txt_nama.setBorder(null);
        txt_nama.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_nama.setEnabled(false);
        add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(648, 245, 280, 26));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/TopUp.png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
    txt_nama.setText("");
    txt_NomorHp.setText("");
    txt_saldo.setText("");
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
      
        MainOwner mainDepo = (MainOwner) SwingUtilities.getWindowAncestor(this);
        mainDepo.showFormOwner(new Deposit.TableDeposit(mainDepo));  
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void lbl_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_simpanMouseClicked
    String nama = txt_nama.getText();
    String nomorHp = txt_NomorHp.getText();
    String saldoText = txt_saldo.getText().trim();
    
    if (saldoText.isEmpty() || !saldoText.matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Saldo tidak valid");
        return;
    }
    
    int saldo = Integer.parseInt(saldoText);
    
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
        if (connection == null) {
            System.out.println("Koneksi ke database belum diinisialisasi.");
            return;
        }
        
        // Update saldo pelanggan berdasarkan nama dan nomor HP
        String sqlUpdateSaldo = "UPDATE deposit SET jumlah_depo = jumlah_depo + ? WHERE kode_pelanggan = (SELECT kode_pelanggan FROM pelanggan WHERE nama = ? AND No_Hp = ?)";
        PreparedStatement statementUpdateSaldo = connection.prepareStatement(sqlUpdateSaldo);
        statementUpdateSaldo.setInt(1, saldo);
        statementUpdateSaldo.setString(2, nama);
        statementUpdateSaldo.setString(3, nomorHp);
        
        int rowsUpdated = statementUpdateSaldo.executeUpdate();
        
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Nama atau No_Hp tidak ditemukan");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menyimpan data");
    }
    }//GEN-LAST:event_lbl_simpanMouseClicked

    private void txt_saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saldoActionPerformed
       //null
    }//GEN-LAST:event_txt_saldoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JLabel btn_kembali;
    public static javax.swing.JLabel iddepo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbl_simpan;
    public static javax.swing.JTextField txt_NomorHp;
    public static javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_saldo;
    // End of variables declaration//GEN-END:variables
}
