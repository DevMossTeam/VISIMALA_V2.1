package mainKaryawan;


import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import login.Login;
import mainOwner.MainOwner;
import mainOwner.popupAkun;
import menu.MenuEvent;
import static login.Login.userId;

/**
 *
 * @author gymwo
 */
public class MainKaryawan extends javax.swing.JFrame {

 private transaksi.Transaksi panelTransaksi;
    private MainOwner mainInstance;
    private popupAkun pAkun;
    
    /**
     * Creates new form Main
     */
    public MainKaryawan() {
        initComponents();
        headerKar.setVisible(false);
        panelTransaksi = new transaksi.Transaksi(mainInstance);
        text1.setText("Dashboard");

        showFormKaryawan(new dashboardKaryawan.dashboard());

        menu11.setEvent(new MenuEvent() {
        @Override // untuk pindah panel
        public void selected(int index, int subIndex) {
         if (index == 0){
         showFormKaryawan(new dashboardKaryawan.dashboard());
        text1.setText("Dashboard");
        headerKar.setVisible(false);  
        } else if (index == 1 && subIndex==1){
        showFormKaryawan(new transaksi.formTransaksi());
        text1.setText("Transaksi");
          headerKar.setVisible(true);
        } else if (index == 1 && subIndex==2){
          showFormKaryawan(panelTransaksi);
        text1.setText("Transaksi");
        headerKar.setVisible(true);
         } else if (index == 2){
        text1.setText("Deposit");
         headerKar.setVisible(true);
         } else if (index == 3){
        showFormKaryawan(new absensikaryawan.absen(userId));
        text1.setText("Absensi");
        headerKar.setVisible(true);
         } else if (index == 4){
        text1.setText("Lemari Laundry");
         headerKar.setVisible(true);

            } else {
              
            }
        }
    });
}

 public void showFormKaryawan(Component com) {
        body1.removeAll();
        body1.add(com);
        body1.repaint();
        body1.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        minimize1 = new javax.swing.JLabel();
        close1 = new javax.swing.JLabel();
        body1 = new javax.swing.JPanel();
        scrollPaneWin112 = new scroll.win11.ScrollPaneWin11();
        menu11 = new menu.MenuKaryawan();
        jPanel4 = new javax.swing.JPanel();
        logoutKaryawan = new javax.swing.JLabel();
        lbl_jabatan1 = new javax.swing.JLabel();
        profil_pegawai = new javax.swing.JLabel();
        lbl_username1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text1 = new javax.swing.JLabel();
        headerKar = new javax.swing.JLabel();
        backroundClose = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1224, 691));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(231, 231, 231));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 163, 163)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        minimize1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize1MouseClicked(evt);
            }
        });
        jPanel1.add(minimize1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1134, 20, 30, 30));

        close1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close1MouseClicked(evt);
            }
        });
        jPanel1.add(close1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 30, 30));

        body1.setBackground(new java.awt.Color(231, 231, 231));
        body1.setPreferredSize(new java.awt.Dimension(996, 595));
        body1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(body1, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 80, 996, 595));

        scrollPaneWin112.setBorder(null);
        scrollPaneWin112.setViewportView(menu11);

        jPanel1.add(scrollPaneWin112, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 167, 228, 465));

        jPanel4.setBackground(new java.awt.Color(56, 161, 141));

        logoutKaryawan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        logoutKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        logoutKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/logout.png"))); // NOI18N
        logoutKaryawan.setText("Logout");
        logoutKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutKaryawanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(logoutKaryawan)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoutKaryawan)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 228, 70));

        lbl_jabatan1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbl_jabatan1.setForeground(new java.awt.Color(0, 102, 255));
        lbl_jabatan1.setText("Pegawai");
        jPanel1.add(lbl_jabatan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, -1, -1));

        profil_pegawai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profil_pegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/defaultProfil.png"))); // NOI18N
        jPanel1.add(profil_pegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 7, 50, 50));

        lbl_username1.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        lbl_username1.setText("Probowo");
        jPanel1.add(lbl_username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 110, 30));

        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 30, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/logoVisimala.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/buttonHeader.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, -1, -1));

        text1.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text1.setText("jLabel2");
        jPanel1.add(text1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 15, 160, -1));

        headerKar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/header.png"))); // NOI18N
        jPanel1.add(headerKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

        backroundClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/backroundClose.png"))); // NOI18N
        jPanel1.add(backroundClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(1122, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutKaryawanMouseClicked
         Login frameLogin = new Login();
         frameLogin.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_logoutKaryawanMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
    if (pAkun == null) {
        pAkun = new popupAkun(userId); 
    }
    pAkun.setVisible(!pAkun.isVisible());
    }//GEN-LAST:event_jLabel12MouseClicked

    private void minimize1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimize1MouseClicked
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimize1MouseClicked

    private void close1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close1MouseClicked
        int option = JOptionPane.showConfirmDialog(null, "Anda yakin ingin keluar dari aplikasi?", "Konfirmasi Keluar" , JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            System.exit(0);
        }
    }//GEN-LAST:event_close1MouseClicked

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
            java.util.logging.Logger.getLogger(MainKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainKaryawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backroundClose;
    private javax.swing.JPanel body1;
    private javax.swing.JLabel close1;
    private javax.swing.JLabel headerKar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JLabel lbl_jabatan1;
    public static javax.swing.JLabel lbl_username1;
    private javax.swing.JLabel logoutKaryawan;
    private menu.MenuKaryawan menu11;
    private javax.swing.JLabel minimize1;
    public static javax.swing.JLabel profil_pegawai;
    private scroll.win11.ScrollPaneWin11 scrollPaneWin112;
    public static javax.swing.JLabel text1;
    // End of variables declaration//GEN-END:variables
}
