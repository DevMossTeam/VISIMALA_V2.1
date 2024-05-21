package mainOwner;


import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import login.Login;
import static login.Login.userId;
import menu.MenuEvent;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author gymwo
 */
public class MainOwner extends javax.swing.JFrame {
  private produk.panelProduk_O produkPanel;
  private usser.tabelUser userPanel;
  private transaksi.Transaksi panelTransaksi;
  private Pengeluaran.pengeluaran panelPengeluaran;
  private popupAkun pAkun;
  private Deposit.TableDeposit panelDeposit;

  
    private JButton nextButton;
    private JButton prevButton;
    /**
     * Creates new form Main
     */
    public MainOwner() {
        GlassPanePopup.install(this); //panel pop up glasses pane;
        initComponents();
        JButton next = new JButton("Next");
        JButton prev = new JButton("Previous");
        
        text.setText("Dashboard");
        
        headerOwner.setVisible(false);
        produkPanel = new produk.panelProduk_O(this);
        userPanel = new usser.tabelUser(this);
        panelTransaksi = new transaksi.Transaksi(this);
        panelPengeluaran = new Pengeluaran.pengeluaran(this);
        panelDeposit = new Deposit.TableDeposit(this);
        
        
        menuOwner.setEvent(new MenuEvent() {
        @Override // untuk pindah panel
        public void selected(int index, int subIndex) {
             if (index == 0 ){ 
                  text.setText("Dashboard");
                 // dashboar Owner
                 } else if (index == 1 && subIndex==1){
                  showFormOwner(new transaksi.formTransaksi());
                  text.setText("Transaksi");
                  headerOwner.setVisible(true);
                 } else if (index == 1 && subIndex==2){
                  showFormOwner(panelTransaksi);
                   text.setText("Transaksi");
                  headerOwner.setVisible(true);
                 } else if (index == 2 && subIndex==1){
//                  showFormOwner(produkPanel);
                   text.setText("Manajemen Laundry");
                  headerOwner.setVisible(true);
                 } else if (index == 2 && subIndex==2){
//                  showFormOwner(userPanel);
                  text.setText("Lemari Laundry");
                  headerOwner.setVisible(true);
                  } else if (index == 3){
                  showFormOwner(panelDeposit); 
                  text.setText("Deposit");
                  headerOwner.setVisible(true);
                 } else if (index == 4){
                  showFormOwner(new absensiowner.panelAbsenOwner());
                  text.setText("Absensi");
                  headerOwner.setVisible(true);
                 } else if (index == 5){
//                  showFormOwner(new laporanKeuangan.P_Keuangan());
                  showFormOwner(new frame.P_Keuangan());
                  text.setText("Laporan Keuangan");
                  headerOwner.setVisible(true);
                  } else if (index == 6){
                  showFormOwner( panelPengeluaran);
                  text.setText("Pengeluaran");
                  headerOwner.setVisible(true);
                  } else if (index == 7 && subIndex==1){
                  showFormOwner(produkPanel);
                   text.setText("Produk");
                  headerOwner.setVisible(true);
                 } else if (index == 7 && subIndex==2){
                  text.setText("User");
                  showFormOwner(userPanel);
                  headerOwner.setVisible(true);
                  } else {
              
            }
        }
    });
}

 public void showFormOwner(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        close = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        scrollPaneWin112 = new scroll.win11.ScrollPaneWin11();
        menuOwner = new menu.MenuOwner();
        profil_owner = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_jabatan = new javax.swing.JLabel();
        infoAkun = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        logoutOwner = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        headerOwner = new javax.swing.JLabel();
        backroundClose = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(231, 231, 231));
        jPanel1.setPreferredSize(new java.awt.Dimension(1224, 691));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 20, 30, 30));

        text.setBackground(new java.awt.Color(51, 51, 51));
        text.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text.setText("jLabel2");
        jPanel1.add(text, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 15, 160, -1));

        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(1134, 20, 30, 30));

        body.setBackground(new java.awt.Color(231, 231, 231));
        body.setPreferredSize(new java.awt.Dimension(996, 595));
        body.setLayout(new java.awt.BorderLayout());
        jPanel1.add(body, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 80, 996, 595));

        scrollPaneWin112.setBorder(null);
        scrollPaneWin112.setPreferredSize(new java.awt.Dimension(199, 538));
        scrollPaneWin112.setViewportView(menuOwner);

        jPanel1.add(scrollPaneWin112, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 228, 450));

        profil_owner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profil_owner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/defaultProfil.png"))); // NOI18N
        jPanel1.add(profil_owner, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 7, 50, 50));

        lbl_username.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        lbl_username.setText("Probowo");
        jPanel1.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 110, -1));

        lbl_jabatan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbl_jabatan.setForeground(new java.awt.Color(0, 102, 255));
        lbl_jabatan.setText("Owner");
        jPanel1.add(lbl_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, -1, -1));

        infoAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoAkunMouseClicked(evt);
            }
        });
        jPanel1.add(infoAkun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 30, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/logoVisimala.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(56, 161, 141));

        logoutOwner.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        logoutOwner.setForeground(new java.awt.Color(255, 255, 255));
        logoutOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/logout.png"))); // NOI18N
        logoutOwner.setText("Logout");
        logoutOwner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutOwnerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(logoutOwner)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoutOwner)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 228, 70));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/buttonHeader.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, -1, -1));

        headerOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainOwner/header.png"))); // NOI18N
        jPanel1.add(headerOwner, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

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

    private void infoAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoAkunMouseClicked
    if (pAkun == null) {
        pAkun = new popupAkun(userId); // Buat instance popup jika belum ada
    }
    pAkun.setVisible(!pAkun.isVisible()); // Set visibilitas berdasarkan kondisi saat ini
    }//GEN-LAST:event_infoAkunMouseClicked

    private void logoutOwnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutOwnerMouseClicked
       Login frameLogin = new Login();
         frameLogin.setVisible(true);
         this.dispose();  
    }//GEN-LAST:event_logoutOwnerMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
int option = JOptionPane.showConfirmDialog(null, "Anda yakin ingin keluar dari aplikasi?", "Konfirmasi Keluar" , JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        this.setVisible(false);
        System.exit(0);
    }
    }//GEN-LAST:event_closeMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
    setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

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
            java.util.logging.Logger.getLogger(MainOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainOwner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backroundClose;
    private javax.swing.JPanel body;
    private javax.swing.JLabel close;
    private javax.swing.JLabel headerOwner;
    private javax.swing.JLabel infoAkun;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JLabel lbl_jabatan;
    public static javax.swing.JLabel lbl_username;
    private javax.swing.JLabel logoutOwner;
    private menu.MenuOwner menuOwner;
    private javax.swing.JLabel minimize;
    public static javax.swing.JLabel profil_owner;
    private scroll.win11.ScrollPaneWin11 scrollPaneWin112;
    public static javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables
}
