
package notifikasi;

import javax.swing.SwingConstants;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author gymwo
 */
public class notifSukses extends javax.swing.JPanel {

    public notifSukses(String subText) {
        initComponents();
        
        setOpaque(false);
        textSukses.setText("<html>" + subText.replace("\n", "<br>") + "</html>");
        textSukses.setHorizontalAlignment(SwingConstants.CENTER);
        textSukses.setVerticalAlignment(SwingConstants.CENTER); // S
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textSukses = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(518, 284));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textSukses.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        textSukses.setForeground(new java.awt.Color(69, 68, 68));
        textSukses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textSukses.setText("i");
        textSukses.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(textSukses, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 450, 100));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/notifikasi/notifSukses.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
    
    private String subText;
    
    public void popUpNotif(String subText) {
        System.out.println("subText: " + subText);
        this.subText = subText;
        textSukses.setText("<html>" + subText.replace("\n", "<br>") + "</html>");
        textSukses.setHorizontalAlignment(SwingConstants.CENTER);
        textSukses.setVerticalAlignment(SwingConstants.CENTER); // Set vertical alignment to center
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel textSukses;
    // End of variables declaration//GEN-END:variables
}
