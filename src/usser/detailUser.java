
package usser;
import java.awt.Image;
import javax.swing.ImageIcon;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author gymwo
 */
public class detailUser extends javax.swing.JPanel {

    public detailUser() {
        initComponents();
        id_user.setVisible(false);
        invisible.setVisible(false);
         setOpaque(false);
    }
    
public void displayProfilDetail(byte[] imageData) {
        if (imageData != null) {
            ImageIcon imageIcon = new ImageIcon(imageData);
            Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Sesuaikan ukuran gambar
            profile.setIcon(new ImageIcon(image));
        } else {
            profile.setIcon(null);
        }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProf = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        CLOSE = new javax.swing.JLabel();
        invisible = new javax.swing.JLabel();
        visible = new javax.swing.JLabel();
        d_password = new javax.swing.JPasswordField();
        d_alamat = new javax.swing.JLabel();
        d_username = new javax.swing.JLabel();
        d_jabatan = new javax.swing.JLabel();
        d_email = new javax.swing.JLabel();
        d_nohp = new javax.swing.JLabel();
        d_rfid = new javax.swing.JLabel();
        id_user = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelProf.setBackground(new java.awt.Color(231, 231, 231));
        panelProf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelProf.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 80));

        add(panelProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 52, 80, 80));

        CLOSE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CLOSEMouseClicked(evt);
            }
        });
        add(CLOSE, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 5, 20, 20));

        invisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Not Visible pw.png"))); // NOI18N
        invisible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invisibleMouseClicked(evt);
            }
        });
        add(invisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 337, -1, -1));

        visible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Visible pw.png"))); // NOI18N
        visible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visibleMouseClicked(evt);
            }
        });
        add(visible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, -1));

        d_password.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_password.setForeground(new java.awt.Color(102, 102, 102));
        d_password.setText("-");
        d_password.setBorder(null);
        d_password.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        d_password.setEnabled(false);
        d_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                d_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                d_passwordFocusLost(evt);
            }
        });
        add(d_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 337, 200, -1));

        d_alamat.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_alamat.setForeground(new java.awt.Color(102, 102, 102));
        d_alamat.setText("-");
        add(d_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, 220, -1));

        d_username.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_username.setForeground(new java.awt.Color(102, 102, 102));
        d_username.setText("-");
        add(d_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 220, -1));

        d_jabatan.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_jabatan.setForeground(new java.awt.Color(102, 102, 102));
        d_jabatan.setText("-");
        add(d_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 220, -1));

        d_email.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_email.setForeground(new java.awt.Color(102, 102, 102));
        d_email.setText("-");
        add(d_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 220, -1));

        d_nohp.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_nohp.setForeground(new java.awt.Color(102, 102, 102));
        d_nohp.setText("-");
        add(d_nohp, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 395, 220, 20));

        d_rfid.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        d_rfid.setForeground(new java.awt.Color(102, 102, 102));
        d_rfid.setText("-");
        add(d_rfid, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, 220, -1));

        id_user.setText("iduser");
        add(id_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 550, 190, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainShowUser.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void CLOSEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CLOSEMouseClicked
   GlassPanePopup.closePopupAll();
    }//GEN-LAST:event_CLOSEMouseClicked

    private void d_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d_passwordFocusGained
       visible.setVisible(true);
       d_password.setEchoChar('\u2022');
    }//GEN-LAST:event_d_passwordFocusGained

    private void d_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d_passwordFocusLost
       d_password.setEchoChar((char) 0);
    }//GEN-LAST:event_d_passwordFocusLost

    private void visibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visibleMouseClicked
    visible.setVisible(false);
    invisible.setVisible(true);
    d_password.setEchoChar((char)0);
    }//GEN-LAST:event_visibleMouseClicked

    private void invisibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invisibleMouseClicked
    invisible.setVisible(false);
    visible.setVisible(true);
    d_password.setEchoChar('\u2022');         // TODO add your handling code here:
    }//GEN-LAST:event_invisibleMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CLOSE;
    public static javax.swing.JLabel d_alamat;
    public static javax.swing.JLabel d_email;
    public static javax.swing.JLabel d_jabatan;
    public static javax.swing.JLabel d_nohp;
    public static javax.swing.JPasswordField d_password;
    public static javax.swing.JLabel d_rfid;
    public static javax.swing.JLabel d_username;
    public static javax.swing.JLabel id_user;
    private javax.swing.JLabel invisible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelProf;
    public static javax.swing.JLabel profile;
    private javax.swing.JLabel visible;
    // End of variables declaration//GEN-END:variables

}
