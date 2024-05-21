
package produk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import koneksi.koneksi;
import mainOwner.MainOwner;
import notifikasi.notifGagal;
import notifikasi.notifSukses;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author YAVIE
 */
public class tambah extends javax.swing.JPanel {
   private Connection con; // java.sql.Connection;
    private Statement stat; // java.sql.Statement;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res; // java.sql.ResultSet;
    private String t;

    /**
     * Creates new form tambah
     */
    public tambah() {
        koneksi();
        initComponents();
  
        jams.setForeground(new Color (0,0,0));
        haris.setForeground(new Color (0,0,0));
        kosongkan();
        autonumber();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 3,
                (screenSize.height - frameSize.height) / 4);
    }

    private void koneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = koneksi.configDB();
            stat = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: ");
            e.printStackTrace(); // Cetak exception untuk debugging
        }
    }
    private void kosongkan() {
        nampro.setText("");
        harpro.setText("");
        haris.setText(" Hari...");
        jams.setText(" Jam...");
    }
     private void autonumber(){  //untuk kodes (kode produknya biar otomatis)
            try {
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.Statement s=conn.createStatement();
                String sql = "SELECT * FROM produk ORDER BY id_produk DESC";
                ResultSet r = s.executeQuery(sql);
                if (r.next()) {
                    String no_transaksi = r.getString("id_produk").substring(2);
                    String PR = "" +(Integer.parseInt(no_transaksi)+1);
                    String Nol = "";
                    
                    if(PR.length()==1)
                        {Nol = "000";}
                    else if(PR.length()==2)
                    {Nol = "00";}
                    else if(PR.length()==3)
                    {Nol = "0";}
                    else if(PR.length()==4)
                    {Nol = "";}
                    kodes.setText("PR" + Nol + PR);
                } else {
                    kodes.setText("PR0001");
                }
                r.close();
                s.close();
            } catch (Exception e) {
    System.out.println("automatic error: " + e.getMessage());
            }
            }
 private boolean isNamaProdukExists(String namaProduk, Connection con) { //ini logika nama produk yang sudah ada atau belum
    try {
        String query = "SELECT COUNT(*) FROM produk WHERE nama_produk=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, namaProduk);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
 
 private void simpan(){
  try {
        // dataa
        String kodeProduk = kodes.getText();
        String namaProduk = nampro.getText().trim();
        String hargaProduk = harpro.getText().trim();
        String BwHari = haris.getText().trim();
        String BwJam = jams.getText().trim();

        // menentukan isian yang benar untuk harga produk
        try {
            double hargaValue = Double.parseDouble(hargaProduk);
            if (hargaValue < 0) {
                GlassPanePopup.showPopup(new notifGagal("Harga tidak boleh bernilai negetif!"));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
               GlassPanePopup.showPopup(new notifGagal("Masukkan data harga yang sesuai! "));
            return; // berhenti
        }
        // ini untuk jam
 try {
    // jam antara 0-24
    int jamValue = Integer.parseInt(BwJam);
    if (jamValue < 0 || jamValue > 24) {
           GlassPanePopup.showPopup(new notifGagal("Jam harus berada dalam rentang 0-24 jam! "));
        return;
    }
} catch (NumberFormatException e) {
       GlassPanePopup.showPopup(new notifGagal("Jam harus berupa angka! "));
    return;
}
        try {
    // ini hari, untuk 0 sampai 356 hari
        int hariValue = Integer.parseInt(BwHari);
        if (hariValue < 0 || hariValue > 356) {
           GlassPanePopup.showPopup(new notifGagal("Hari harus berada dalam rentang 0-365!"));
        return;
    }
} catch (NumberFormatException e) {
       GlassPanePopup.showPopup(new notifGagal("Hari harus berupa angka! "));
    return;
}
        if (hargaProduk.isEmpty()) {
           GlassPanePopup.showPopup(new notifGagal("Harga tidak boleh kosong! "));
            return;
        }
        if (namaProduk.isEmpty()) {
            GlassPanePopup.showPopup(new notifGagal("Nama produk tidak boleh kosong! "));
            return;
        }
if (isNamaProdukExists(namaProduk, con)) {  // ini untuk cek nama produk supaya ga dua kali (cuma dipanggil, logikanya dibawah)
    GlassPanePopup.showPopup(new notifGagal(" Nama produk sudah ada silahkan pilih nama produk yang lain "));
    return; // Hentikan eksekusi lebih lanjut
}
        if (t == null) {
            // Insert new data
            String insertQuery = "INSERT INTO produk(id_produk, nama_produk, harga_produk, jenis_produk, hari, jam) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPstmt.setString(1, kodeProduk);
                insertPstmt.setString(2, namaProduk);
                insertPstmt.setString(3, hargaProduk);
                insertPstmt.setString(4, jen.getSelectedItem().toString());
                insertPstmt.setString(5, BwHari);
                insertPstmt.setString(6, BwJam);

                int affectedRows = insertPstmt.executeUpdate();
if (affectedRows > 0) {
       GlassPanePopup.showPopup(new notifSukses(" Data berhasil disimpan! "));
          int durationInMillis = 2000;
            Timer timer = new Timer(durationInMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GlassPanePopup.closePopupAll(); 
                }
            });
            timer.setRepeats(false);
            timer.start();
} else {
  GlassPanePopup.showPopup(new notifGagal("Gagal menyimpan data! "));
}}}      
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai ! ");
    }
 }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenis = new javax.swing.ButtonGroup();
        jen = new combo_suggestion.ComboBoxSuggestion();
        jams = new javax.swing.JTextField();
        haris = new javax.swing.JTextField();
        harpro = new javax.swing.JTextField();
        nampro = new javax.swing.JTextField();
        kodes = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        simpans = new javax.swing.JButton();
        clears = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        kembalis = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(237, 237, 237));
        setPreferredSize(new java.awt.Dimension(1052, 689));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jen.setBorder(null);
        jen.setEditable(false);
        jen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "kiloan", "satuan" }));
        add(jen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 236, 140, 20));

        jams.setBorder(null);
        jams.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jamsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jamsFocusLost(evt);
            }
        });
        jams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jamsActionPerformed(evt);
            }
        });
        add(jams, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 400, 70, 20));

        haris.setBorder(null);
        haris.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                harisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                harisFocusLost(evt);
            }
        });
        add(haris, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 70, 20));

        harpro.setBorder(null);
        harpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harproActionPerformed(evt);
            }
        });
        add(harpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 320, 270, 20));

        nampro.setBorder(null);
        nampro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namproActionPerformed(evt);
            }
        });
        add(nampro, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, 270, 20));

        kodes.setText("jTextField1");
        add(kodes, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, -1, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Frame 2 (1).png"))); // NOI18N
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Batas Waktu");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Harga Produk");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Jenis Produk");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Frame 2.png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 390, 100, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Frame 1.png"))); // NOI18N
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 142, 320, 40));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Frame 1.png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 310, -1, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Nama Produk");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Frame 2.png"))); // NOI18N
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 390, 100, 40));

        simpans.setBackground(new java.awt.Color(10, 172, 42));
        simpans.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpans.setForeground(new java.awt.Color(255, 255, 255));
        simpans.setText("Simpan");
        simpans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpansActionPerformed(evt);
            }
        });
        add(simpans, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 90, -1));

        clears.setBackground(new java.awt.Color(11, 146, 215));
        clears.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clears.setForeground(new java.awt.Color(255, 255, 255));
        clears.setText("Clear");
        clears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearsActionPerformed(evt);
            }
        });
        add(clears, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 470, 90, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambahkan Produk");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        kembalis.setBackground(new java.awt.Color(128, 147, 131));
        kembalis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembalisMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N

        javax.swing.GroupLayout kembalisLayout = new javax.swing.GroupLayout(kembalis);
        kembalis.setLayout(kembalisLayout);
        kembalisLayout.setHorizontalGroup(
            kembalisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kembalisLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kembalisLayout.setVerticalGroup(
            kembalisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        add(kembalis, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 520, 50, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/KK (14).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 630));
    }// </editor-fold>//GEN-END:initComponents

    private void kembalisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembalisMouseClicked

    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
    mainInstance.showFormOwner(new produk.panelProduk_O(mainInstance));
    }//GEN-LAST:event_kembalisMouseClicked

    private void clearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearsActionPerformed
        // TODO add your handling code here:
              kosongkan();
    }//GEN-LAST:event_clearsActionPerformed

    private void simpansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpansActionPerformed
simpan();
    }//GEN-LAST:event_simpansActionPerformed

    private void namproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namproActionPerformed

    private void harproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harproActionPerformed

    private void jamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jamsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamsActionPerformed

    private void harisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harisFocusGained
        // TODO add your handling code here:
                if(haris.getText().equals(" Hari...")) {
            haris.setText("");
            haris.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_harisFocusGained

    private void harisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harisFocusLost
        // TODO add your handling code here:
                       if(haris.getText().equals("")) {
            haris.setText(" Hari...");
            haris.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_harisFocusLost

    private void jamsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jamsFocusGained
        // TODO add your handling code here:
                                if(jams.getText().equals(" Jam...")) {
            jams.setText("");
            jams.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jamsFocusGained

    private void jamsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jamsFocusLost
               if(jams.getText().equals("")) {
            jams.setText(" Jam...");
            jams.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jamsFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clears;
    private javax.swing.JTextField haris;
    private javax.swing.JTextField harpro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jams;
    private combo_suggestion.ComboBoxSuggestion jen;
    private javax.swing.ButtonGroup jenis;
    private javax.swing.JPanel kembalis;
    private javax.swing.JTextField kodes;
    private javax.swing.JTextField nampro;
    private javax.swing.JButton simpans;
    // End of variables declaration//GEN-END:variables
}
