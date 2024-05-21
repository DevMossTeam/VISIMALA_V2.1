package Pengeluaran;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import mainOwner.MainOwner;


public class FormPengeluaran extends javax.swing.JPanel {
private Connection con;
    public FormPengeluaran() {
        initComponents();
        kodesPeng.setVisible(false);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Total = new javax.swing.JTextField();
        Tanggal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Keterangan = new javax.swing.JTextArea();
        simpan = new javax.swing.JButton();
        kodesPeng = new javax.swing.JTextField();
        btn_batal = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(919, 580));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalActionPerformed(evt);
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
        Tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalActionPerformed(evt);
            }
        });
        add(Tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 164, 130, 20));

        Keterangan.setBackground(new java.awt.Color(255, 252, 252));
        Keterangan.setColumns(20);
        Keterangan.setLineWrap(true);
        Keterangan.setRows(1);
        Keterangan.setWrapStyleWord(true);
        jScrollPane1.setViewportView(Keterangan);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 300, 290, 100));

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

        kodesPeng.setEditable(false);
        add(kodesPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 230, -1));

        btn_batal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_batalMouseClicked(evt);
            }
        });
        add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 495, 120, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pengeluaran/desainEditPengeluaran.png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
    try {
        String kodePengeluaran = kodesPeng.getText();
        String ket = Keterangan.getText().trim();
        String tanggal = Tanggal.getText().trim();
        String total = Total.getText().trim();
        // Lakukan operasi pembaruan data setelah validasi
        con = koneksi.configDB();
        String updateQuery = "UPDATE pengeluaran SET tgl_pengeluaran = ?, keterangan = ?, total_pengeluaran = ? WHERE id_pengeluaran = ?";
        try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
            updatePstmt.setString(1, tanggal);
            updatePstmt.setString(2, ket);
            updatePstmt.setString(3, total);
            updatePstmt.setString(4, kodePengeluaran);

            int affectedRows = updatePstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
                mainInstance.showFormOwner(new Pengeluaran.pengeluaran(mainInstance));
            } else {
                JOptionPane.showMessageDialog(null, "Data gagal diperbarui, silakan coba lagi.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai!");
    }
    }//GEN-LAST:event_simpanActionPerformed

    private void TotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TotalFocusGained
        if(Total.getText().equals(" Total Pengeluaran...")) {
            Total.setText("");
            Total.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_TotalFocusGained

    private void TotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TotalFocusLost
                   if(Total.getText().equals("")) {
            Total.setText(" Total Pengeluaran...");
            Total.setForeground(new Color(102,102,102));
                   }
    }//GEN-LAST:event_TotalFocusLost

    private void TanggalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TanggalFocusGained

    }//GEN-LAST:event_TanggalFocusGained

    private void TanggalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TanggalFocusLost

    }//GEN-LAST:event_TanggalFocusLost

    private void TanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalActionPerformed

    }//GEN-LAST:event_TanggalActionPerformed

    private void TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalActionPerformed

    }//GEN-LAST:event_TotalActionPerformed

    private void btn_batalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_batalMouseClicked
    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
    mainInstance.showFormOwner(new Pengeluaran.pengeluaran(mainInstance));        // TODO add your handling code here:
    }//GEN-LAST:event_btn_batalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextArea Keterangan;
    public static javax.swing.JTextField Tanggal;
    public static javax.swing.JTextField Total;
    private javax.swing.JLabel btn_batal;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField kodesPeng;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables
}
