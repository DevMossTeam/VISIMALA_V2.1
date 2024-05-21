
package notifikasi;
import javax.swing.SwingConstants;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author gymwo
 */
public class notifGagal extends javax.swing.JPanel {

    public notifGagal(String subText) {
        initComponents();
        setOpaque(false); // Membuat JPanel transparan
        textGagal.setText("<html>" + subText.replace("\n", "<br>") + "</html>");
        textGagal.setHorizontalAlignment(SwingConstants.CENTER);
        textGagal.setVerticalAlignment(SwingConstants.CENTER); // Set vertical alignment to center
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textGagal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textGagal.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        textGagal.setForeground(new java.awt.Color(69, 68, 68));
        textGagal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textGagal.setText("i");
        textGagal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(textGagal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 450, 100));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/notifikasi/notifGagal.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
    
    private String subText;
    
    public void popUpNotif(String subText) {
        System.out.println("subText: " + subText);
        this.subText = subText;
        textGagal.setText("<html>" + subText.replace("\n", "<br>") + "</html>");
        textGagal.setHorizontalAlignment(SwingConstants.CENTER);
        textGagal.setVerticalAlignment(SwingConstants.CENTER); // Set vertical alignment to center
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel textGagal;
    // End of variables declaration//GEN-END:variables
}
