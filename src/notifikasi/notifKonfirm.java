
package notifikasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import koneksi.koneksi;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author gymwo
 */
public class notifKonfirm extends javax.swing.JPanel {
     private Connection conn; // java.sql.Connection;
    private Statement stm; // java.sql.Statement;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res; // java.sql.ResultSet;
    public String queryNotif;
    public String idNotif;
    
    
    
    public notifKonfirm(String subTest, String query, String id)  throws SQLException{
        initComponents();
        setOpaque(false);
        textKonfirm.setText("<html>" + subTest.replace("\n", "<br>") + "</html>");
        textKonfirm.setHorizontalAlignment(SwingConstants.CENTER);
        textKonfirm.setVerticalAlignment(SwingConstants.CENTER);
        queryNotif = query;
        idNotif = id;
            
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textKonfirm = new javax.swing.JLabel();
        ya = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(518, 284));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textKonfirm.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        textKonfirm.setForeground(new java.awt.Color(69, 68, 68));
        textKonfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textKonfirm.setText("i");
        textKonfirm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(textKonfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 450, 60));

        ya.setBorder(null);
        ya.setContentAreaFilled(false);
        ya.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yaMouseClicked(evt);
            }
        });
        ya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yaActionPerformed(evt);
            }
        });
        add(ya, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, 80, 20));

        batal.setBorder(null);
        batal.setContentAreaFilled(false);
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });
        add(batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 90, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/notifikasi/notifKonfirm.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void yaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yaMouseClicked
    
    }//GEN-LAST:event_yaMouseClicked

    private void yaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yaActionPerformed
         try {
             // TODO add your handling code here:
             queryButton(queryNotif, idNotif);
         } catch (SQLException ex) {
             Logger.getLogger(notifKonfirm.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("ex: "+ex);
         }
    }//GEN-LAST:event_yaActionPerformed

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
    GlassPanePopup.closePopupAll();
    }//GEN-LAST:event_batalActionPerformed
    private String subText;
    
    public void popUpNotif(String subText) {
        System.out.println("subText: " + subText);
        this.subText = subText;
        textKonfirm.setText("<html>" + subText.replace("\n", "<br>") + "</html>");
        textKonfirm.setHorizontalAlignment(SwingConstants.CENTER);
        textKonfirm.setVerticalAlignment(SwingConstants.CENTER); // Set vertical alignment to center
    }
    
    private void queryButton(String queryHit, String id) throws SQLException{
        conn = koneksi.configDB(); 
        String query = queryHit;
        pstm = conn.prepareStatement(query);
        pstm.setString(1,id );
        pstm.executeUpdate();
        GlassPanePopup.closePopupAll();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton batal;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel textKonfirm;
    private javax.swing.JButton ya;
    // End of variables declaration//GEN-END:variables

}
