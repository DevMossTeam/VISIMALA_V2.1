
package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Profile extends javax.swing.JFrame {
       private String imagePath; // menyimpan path gambar yang diunggah
       private static final int CIRCLE_RADIUS = 150;


    public Profile() {
        initComponents();
    }
 // Metode untuk mengunggah gambar
  private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            displayImage(imagePath);
        }
    }

    // Metode untuk menampilkan gambar
private void displayImage(String path) {
    ImageIcon imageIcon = new ImageIcon(path); // Mengambil gambar dari path
    Image image = imageIcon.getImage(); // Mengambil objek Image dari ImageIcon

    // Membuat BufferedImage baru dengan ukuran yang sama dengan gambar
    int diameter = Math.min(image.getWidth(null), image.getHeight(null)); // Diameter lingkaran sesuai dengan gambar terkecil
    BufferedImage bufferedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
    
    // Menggambar gambar ke BufferedImage dengan bentuk lingkaran
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter); // Lingkaran dengan diameter sesuai gambar
    g2.clip(circle); // Gunakan lingkaran sebagai klip
    g2.drawImage(image, 0, 0, diameter, diameter, null); // Menggambar gambar ke dalam lingkaran
    g2.dispose();
    
    // Mengubah BufferedImage menjadi ImageIcon untuk ditampilkan di JLabel
    ImageIcon scaledImageIcon = new ImageIcon(bufferedImage.getScaledInstance(CIRCLE_RADIUS, CIRCLE_RADIUS, Image.SCALE_SMOOTH));

    // Menetapkan gambar yang diubah ukurannya ke JLabel
    displayFoto.setIcon(scaledImageIcon);
}

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uploadFoto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        displayFoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(657, 466));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uploadFoto.setText("upload");
        uploadFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFotoActionPerformed(evt);
            }
        });
        getContentPane().add(uploadFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 0, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 300));

        displayFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 260, 170));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void uploadFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFotoActionPerformed
     uploadImage();
    }//GEN-LAST:event_uploadFotoActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel displayFoto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton uploadFoto;
    // End of variables declaration//GEN-END:variables
}
