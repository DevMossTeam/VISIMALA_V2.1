package Pelanggan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import koneksi.koneksi;

public class DaftarPelanggan extends javax.swing.JPanel {

    public DaftarPelanggan() {
        initComponents();

        txt_nama.setText("Username");
        txt_nama.setForeground(new Color(153, 153, 153));
        txt_NoHp.setText("No Telp");
        txt_NoHp.setForeground(new Color(153, 153, 153));
        txt_alamat.setText("   Alamat");
        txt_alamat.setForeground(new Color(153, 153, 153));
        btn_simpan.requestFocusInWindow();
        
            // Attach key listener to text fields
    txt_nama.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            handleKeyPress(evt); 
        }
    });
    
    txt_NoHp.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            handleKeyPress(evt);
        }
    });
}
private void handleKeyPress(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        btn_simpan.doClick();
    }
}
    
private String generateUserId(Connection conn) throws SQLException {
    String prefix = "KP"; 
    int number = 1; 
    String idCust = ""; 

    boolean idFound = false;
    while (!idFound) {
        String userIdToCheck = prefix + number;
        String query = "SELECT COUNT(*) FROM pelangggan WHERE kode_pelanggan = ?";
        PreparedStatement checkStmt = conn.prepareStatement(query);
        checkStmt.setString(1, userIdToCheck);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            if (count == 0) {
                idCust = userIdToCheck;
                idFound = true;
            } else {
                number++; // Jika ID sudah digunakan, tambahkan nomor
            }
        }

        rs.close();
        checkStmt.close();
    }
    return idCust;
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_nama = new javax.swing.JTextField();
        txt_NoHp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat = new javax.swing.JTextArea();
        btn_kembali = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        bg = new javax.swing.JLabel();
        kodePelanggan = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nama.setBackground(new java.awt.Color(255, 252, 252));
        txt_nama.setBorder(null);
        txt_nama.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_namaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_namaFocusLost(evt);
            }
        });
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 270, 20));

        txt_NoHp.setBackground(new java.awt.Color(255, 252, 252));
        txt_NoHp.setBorder(null);
        txt_NoHp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_NoHpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_NoHpFocusLost(evt);
            }
        });
        txt_NoHp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NoHpActionPerformed(evt);
            }
        });
        add(txt_NoHp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 270, 20));

        txt_alamat.setBackground(new java.awt.Color(255, 252, 252));
        txt_alamat.setColumns(20);
        txt_alamat.setLineWrap(true);
        txt_alamat.setRows(1);
        txt_alamat.setWrapStyleWord(true);
        txt_alamat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_alamat.setPreferredSize(new java.awt.Dimension(210, 18));
        txt_alamat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_alamatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_alamatFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txt_alamat);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 300, 130));

        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 40, 20));

        btn_clear.setBorder(null);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 80, 30));

        btn_simpan.setBorder(null);
        btn_simpan.setContentAreaFilled(false);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 440, 90, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pelanggan/bg.png"))); // NOI18N
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        add(kodePelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 240, 50));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_namaFocusGained
        if(txt_nama.getText().equals("Username")) {
            txt_nama.setText("");
            txt_nama.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txt_namaFocusGained

    private void txt_namaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_namaFocusLost
        if(txt_nama.getText().isEmpty()) {
            txt_nama.setText("Username");
            txt_nama.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_namaFocusLost

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_NoHpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_NoHpFocusGained
        if(txt_NoHp.getText().equals("No Telp")) {
            txt_NoHp.setText("");
            txt_NoHp.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txt_NoHpFocusGained

    private void txt_NoHpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_NoHpFocusLost
        if(txt_NoHp.getText().isEmpty()) {
            txt_NoHp.setText("No Telp");
            txt_NoHp.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_NoHpFocusLost

    private void txt_NoHpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NoHpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NoHpActionPerformed

    private void txt_alamatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamatFocusGained
        if(txt_alamat.getText().equals("   Alamat")) {
            txt_alamat.setText("");
            txt_alamat.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txt_alamatFocusGained

    private void txt_alamatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamatFocusLost
        if(txt_alamat.getText().isEmpty()) {
            txt_alamat.setText("   Alamat");
            txt_alamat.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_alamatFocusLost

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
        //        MainOwner mainDepo = (MainOwner) SwingUtilities.getWindowAncestor(this);
        //        mainDepo.showFormOwner(new Deposit.TableDeposit(mainDepo));
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        txt_nama.setText("");
        txt_NoHp.setText("");
        txt_alamat.setText("");
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String nama = txt_nama.getText();
        String noHp = txt_NoHp.getText();
        String alamat = txt_alamat.getText();

        // Validation: Check if nama and noHp are not empty
        if (nama.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong!");
            return;
        }
        if (noHp.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No HP tidak boleh kosong!");
            return;
        }

        try (java.sql.Connection conn = koneksi.configDB();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pelanggan (kode_pelanggan, nama, No_Hp, alamat) VALUES (?, ?, ?, ?)")) {

            // Generate the user ID
            String idCust = generateUserId(conn);

            // Set the parameters for the PreparedStatement
            pstmt.setString(1, idCust);
            pstmt.setString(2, nama);
            pstmt.setString(3, noHp);
            pstmt.setString(4, alamat);

            // Execute the insert statement
            pstmt.executeUpdate();

            // Optionally, you can show a message indicating success
            JOptionPane.showMessageDialog(null, "Data has been saved successfully!");

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_simpanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_clear;
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel kodePelanggan;
    private javax.swing.JTextField txt_NoHp;
    private javax.swing.JTextArea txt_alamat;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
