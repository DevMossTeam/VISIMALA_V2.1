
package mainOwner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import koneksi.koneksi;

/**
 *
 * @author gymwo
 */
public class popupAkun extends javax.swing.JFrame {
  private final String idUser;
  
    public popupAkun(String userId) {
        initComponents();
        
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setPreferredSize(new java.awt.Dimension(309, 457));
    setLocation(1264, 257);
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

             this.idUser = userId;
        tampilkanInformasiAkun();
    }



    private popupAkun() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
private void tampilkanInformasiAkun() {
    String query = "SELECT Username, No_Hp, jabatan, email, profile_pic FROM user WHERE id_user = ?";
        try (Connection conn = koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, getIdUser());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("Username");
                    String noHP = rs.getString("No_Hp");
                    String jabatan = rs.getString("jabatan");
                    String email = rs.getString("email");
                    // Set teks pada JLabel sesuai dengan informasi akun yang login
                    lb_jabatan.setText(jabatan);
                    lb_nohp.setText(noHP);
                    lb_email.setText(email);
                    lb_username.setText(username);

                    byte[] imageBytes = rs.getBytes("profile_pic");

                    // Cek jika data gambar tidak null
                    if (imageBytes != null && imageBytes.length > 0) {
                        Image image = Toolkit.getDefaultToolkit().createImage(imageBytes);
                        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        profil.setIcon(imageIcon);
                    } else {
                        profil.removeAll();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User dengan ID tersebut tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil informasi akun: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getIdUser() {
        return idUser;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_jabatan = new javax.swing.JLabel();
        lb_nohp = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        lb_alamat = new javax.swing.JLabel();
        lb_username = new javax.swing.JLabel();
        profil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(309, 457));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_jabatan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_jabatan.setText("-");
        getContentPane().add(lb_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 245, 160, -1));

        lb_nohp.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_nohp.setText("-");
        getContentPane().add(lb_nohp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 285, 160, -1));

        lb_email.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_email.setText("-");
        getContentPane().add(lb_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 325, 160, -1));

        lb_alamat.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_alamat.setText("-");
        getContentPane().add(lb_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 365, 160, -1));

        lb_username.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lb_username.setText("-");
        getContentPane().add(lb_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 205, 160, -1));

        profil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profil.setPreferredSize(new java.awt.Dimension(60, 120));
        getContentPane().add(profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 78, 100, 100));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/popupakun (2).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(popupAkun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(popupAkun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(popupAkun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(popupAkun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new popupAkun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lb_alamat;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_jabatan;
    private javax.swing.JLabel lb_nohp;
    private javax.swing.JLabel lb_username;
    private javax.swing.JLabel profil;
    // End of variables declaration//GEN-END:variables

    void closePopup() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
