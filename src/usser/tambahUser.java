
package usser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import mainOwner.MainOwner;
//
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import notifikasi.notifGagal;
import notifikasi.notifSukses;
import raven.glasspanepopup.GlassPanePopup;



public class tambahUser extends javax.swing.JPanel {
    private Connection conn; // java.sql.Connection;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    //
     private String imagePath;
//     private static final int CIRCLE_RADIUS = 150;
 

    public tambahUser() {
        initComponents();
        invisible1.setVisible(false);
        
//        cbox_jabatan1.setVisible(false);
            
    txt_password1.setText("Password");
    txt_password1.setForeground(new Color(153, 153, 153));
    txt_nama1.setText("Username baru");
    txt_nama1.setForeground(new Color(153, 153, 153));
    txt_email1.setText("Email");
    txt_email1.setForeground(new Color(153, 153, 153));
    txt_nohp1.setText("Handphone");
    txt_nohp1.setForeground(new Color(153, 153, 153));
    txt_alamat1.setText("Alamat");
    txt_alamat1.setForeground(new Color(153, 153, 153));

      
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 3,
                (screenSize.height - frameSize.height) / 4);
    }
    
      private void kosongkan() {
    txt_password1.setText("Password");
    txt_password1.setForeground(new Color(153, 153, 153));
    txt_nama1.setText("Username baru");
    txt_nama1.setForeground(new Color(153, 153, 153));
    txt_email1.setText("Email");
    txt_email1.setForeground(new Color(153, 153, 153));
    txt_nohp1.setText("Handphone");
    txt_nohp1.setForeground(new Color(153, 153, 153));
    txt_alamat1.setText("Alamat");
    txt_alamat1.setForeground(new Color(153, 153, 153));

    }  
    
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
  
private void displayImage(String path) {
    ImageIcon imageIcon = new ImageIcon(path); 
    Image image = imageIcon.getImage(); 
    int labelWidth = tampilkanFoto.getWidth();
    int labelHeight = tampilkanFoto.getHeight();
    BufferedImage bufferedImage = new BufferedImage(labelWidth, labelHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.drawImage(image, 0, 0, labelWidth, labelHeight, null); 
    g2.dispose();
    ImageIcon scaledImageIcon = new ImageIcon(bufferedImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH));
    tampilkanFoto.setIcon(scaledImageIcon);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenis = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        txt_nohp1 = new javax.swing.JTextField();
        txt_email1 = new javax.swing.JTextField();
        txt_nama1 = new javax.swing.JTextField();
        txt_password1 = new javax.swing.JPasswordField();
        cbox_jabatan1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat1 = new javax.swing.JTextArea();
        cropPanel = new javax.swing.JPanel();
        editProfile = new javax.swing.JLabel();
        tampilkanFoto = new javax.swing.JLabel();
        simpans = new javax.swing.JButton();
        clears = new javax.swing.JButton();
        hapus_profil = new javax.swing.JButton();
        invisible1 = new javax.swing.JLabel();
        visible1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));
        setPreferredSize(new java.awt.Dimension(996, 595));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 50, 48));

        txt_nohp1.setBackground(new java.awt.Color(255, 252, 252));
        txt_nohp1.setText("No HP");
        txt_nohp1.setBorder(null);
        txt_nohp1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nohp1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nohp1FocusLost(evt);
            }
        });
        txt_nohp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nohp1ActionPerformed(evt);
            }
        });
        add(txt_nohp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 286, 270, 20));

        txt_email1.setBackground(new java.awt.Color(255, 252, 252));
        txt_email1.setText("Email");
        txt_email1.setBorder(null);
        txt_email1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_email1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_email1FocusLost(evt);
            }
        });
        add(txt_email1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 197, 270, 20));

        txt_nama1.setBackground(new java.awt.Color(255, 252, 252));
        txt_nama1.setText("Username");
        txt_nama1.setBorder(null);
        txt_nama1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nama1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nama1FocusLost(evt);
            }
        });
        txt_nama1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama1ActionPerformed(evt);
            }
        });
        add(txt_nama1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 114, 270, 20));

        txt_password1.setBackground(new java.awt.Color(255, 252, 252));
        txt_password1.setText("Password");
        txt_password1.setBorder(null);
        txt_password1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_password1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_password1FocusLost(evt);
            }
        });
        add(txt_password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 242, 250, -1));

        cbox_jabatan1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cbox_jabatan1.setText("pegawai");
        add(cbox_jabatan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 158, 270, -1));

        txt_alamat1.setBackground(new java.awt.Color(255, 252, 252));
        txt_alamat1.setColumns(20);
        txt_alamat1.setLineWrap(true);
        txt_alamat1.setRows(1);
        txt_alamat1.setWrapStyleWord(true);
        txt_alamat1.setBorder(null);
        txt_alamat1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_alamat1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_alamat1FocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txt_alamat1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 290, 80));

        cropPanel.setBackground(new java.awt.Color(230, 236, 255));
        cropPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/pen 1.png"))); // NOI18N
        editProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProfileMouseClicked(evt);
            }
        });
        cropPanel.add(editProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 40, -1));

        tampilkanFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tampilkanFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/defaultProfile.png"))); // NOI18N
        cropPanel.add(tampilkanFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 200));

        add(cropPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 172, 200, 200));

        simpans.setBackground(new java.awt.Color(68, 166, 59));
        simpans.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpans.setForeground(new java.awt.Color(255, 255, 255));
        simpans.setBorder(null);
        simpans.setContentAreaFilled(false);
        simpans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpansActionPerformed(evt);
            }
        });
        add(simpans, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 500, 130, 20));

        clears.setBackground(new java.awt.Color(59, 88, 166));
        clears.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clears.setForeground(new java.awt.Color(255, 255, 255));
        clears.setBorder(null);
        clears.setContentAreaFilled(false);
        clears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearsActionPerformed(evt);
            }
        });
        add(clears, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 500, 120, 20));

        hapus_profil.setBorder(null);
        hapus_profil.setContentAreaFilled(false);
        hapus_profil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hapus_profilMouseClicked(evt);
            }
        });
        add(hapus_profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 100, 20));

        invisible1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Not Visible pw.png"))); // NOI18N
        invisible1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invisible1MouseClicked(evt);
            }
        });
        add(invisible1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 242, -1, -1));

        visible1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Visible pw.png"))); // NOI18N
        visible1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visible1MouseClicked(evt);
            }
        });
        add(visible1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 242, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainTambahUser.png"))); // NOI18N
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 970, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void clearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearsActionPerformed
kosongkan();
    }//GEN-LAST:event_clearsActionPerformed
private boolean isValidEmail(String email) {

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
}
private boolean isValidPhoneNumber(String phoneNumber) {
return phoneNumber.matches("^\\+?[0-9]{7,14}$") || phoneNumber.matches("^[0-9]{7,12}$");
}

private void simpan() {
    try {
        String Username = txt_nama1.getText();
        String password = txt_password1.getText();
        String jabatan = cbox_jabatan1.getText().toString();
        String email = txt_email1.getText();
        String No_Hp = txt_nohp1.getText(); 
        String alamat = txt_alamat1.getText();

        if (Username.isEmpty() || password.isEmpty() || email.isEmpty()) {
        GlassPanePopup.showPopup(new notifGagal("Username,Password,Email tidak boleh kosong "));
            return;
        }

        if (password.length() < 6) {
        GlassPanePopup.showPopup(new notifGagal("Password minimal harus 6 karakter "));    
            return;
        }

        if (!isValidEmail(email)) {
           GlassPanePopup.showPopup(new notifGagal("Masukkan email yang valid "));
            return;
        }

        if (!isValidPhoneNumber(No_Hp)) {
          GlassPanePopup.showPopup(new notifGagal("Masukkan No Hp yang valid"));
          return;
        }
        conn = koneksi.configDB();
        String idUser = generateUserId(conn); // Panggil metode
        String insertQuery = "INSERT INTO user(id_user, Username, password, jabatan, email, No_Hp, alamat, profile_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pstm = conn.prepareStatement(insertQuery);
        pstm.setString(1, idUser);
        pstm.setString(2, Username);
        pstm.setString(3, password);
        pstm.setString(4, jabatan);
        pstm.setString(5, email);
        pstm.setString(6, No_Hp);
        pstm.setString(7, alamat);
        if (imagePath != null) {
            // Baca file gambar menjadi byte hanya jika imagePath tidak kosong
            File imageFile = new File(imagePath);
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] imageData = new byte[(int) imageFile.length()];
            fis.read(imageData);
            fis.close();
            pstm.setBytes(8, imageData);
        } else {
            // Jika imagePath kosong, set profile_pic menjadi null
            pstm.setNull(8, java.sql.Types.BLOB);
        }

        int rowsInserted = pstm.executeUpdate();
        if (rowsInserted > 0) {
            GlassPanePopup.showPopup(new notifSukses("Berhasil menambahkan data baru"));
               int durationInMillis = 2000;
        Timer timer = new Timer(durationInMillis, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        GlassPanePopup.closePopupAll(); 
        }
        });
        timer.setRepeats(false);
        timer.start();

            MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
            mainInstance.showFormOwner(new usser.tabelUser(mainInstance));
        }
    } catch (SQLException e) {
             GlassPanePopup.showPopup(new notifGagal("Gagal menambahkan data baru"));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

private String generateUserId(Connection conn) throws SQLException {
    String prefix = "UID"; 
    int number = 1; 
    String idUser = ""; 

    boolean idFound = false;
    while (!idFound) {
        String userIdToCheck = prefix + number;
        String query = "SELECT COUNT(*) FROM user WHERE id_user = ?";
        PreparedStatement checkStmt = conn.prepareStatement(query);
        checkStmt.setString(1, userIdToCheck);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            if (count == 0) {
                idUser = userIdToCheck;
                idFound = true;
            } else {
                number++; // Jika ID sudah digunakan, tambahkan nomor
            }
        }
        rs.close();
        checkStmt.close();
    }
    return idUser;
}
    private void simpansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpansActionPerformed

   simpan();
       
    }//GEN-LAST:event_simpansActionPerformed

    private void txt_nama1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama1ActionPerformed

    private void txt_nohp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nohp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nohp1ActionPerformed

    private void txt_email1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_email1FocusGained
      if(txt_email1.getText().equals("Email")) {
        txt_email1.setText("");
        txt_email1.setForeground(new Color(0,0,0));
       }
    }//GEN-LAST:event_txt_email1FocusGained

    private void txt_email1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_email1FocusLost
     if(txt_email1.getText().isEmpty()) {
        txt_email1.setText("Email");
        txt_email1.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_txt_email1FocusLost

    private void txt_nohp1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nohp1FocusGained
       if(txt_nohp1.getText().equals("Handphone")) {
        txt_nohp1.setText("");
        txt_nohp1.setForeground(new Color(0,0,0));
       }
    }//GEN-LAST:event_txt_nohp1FocusGained

    private void txt_nohp1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nohp1FocusLost
       if(txt_nohp1.getText().isEmpty()) {
        txt_nohp1.setText("Handphone");
        txt_nohp1.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_txt_nohp1FocusLost

    private void txt_alamat1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamat1FocusGained
      if(txt_alamat1.getText().equals("Alamat")) {
        txt_alamat1.setText("");
        txt_alamat1.setForeground(new Color(0,0,0));
       }
    }//GEN-LAST:event_txt_alamat1FocusGained

    private void txt_alamat1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamat1FocusLost
      if(txt_alamat1.getText().isEmpty()) {
        txt_alamat1.setText("Alamat");
        txt_alamat1.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_txt_alamat1FocusLost

    private void txt_nama1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nama1FocusGained
        if(txt_nama1.getText().equals("Username baru")) {
          txt_nama1.setText("");
          txt_nama1.setForeground(new Color(0,0,0));
         }
    }//GEN-LAST:event_txt_nama1FocusGained

    private void txt_nama1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nama1FocusLost
      if(txt_nama1.getText().isEmpty()) {
        txt_nama1.setText("Username baru");
        txt_nama1.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_txt_nama1FocusLost

    private void editProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProfileMouseClicked
   uploadImage();     // TODO add your handling code here:
    }//GEN-LAST:event_editProfileMouseClicked

    private void hapus_profilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hapus_profilMouseClicked
 tampilkanFoto.setIcon(null);
    }//GEN-LAST:event_hapus_profilMouseClicked

    private void txt_password1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password1FocusGained
    if(txt_password1.getText().equals("Password")) {
        txt_password1.setText("");
        txt_password1.setForeground(new Color(0, 0, 0));
        visible1.setVisible(true);
        txt_password1.setEchoChar('\u2022');
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password1FocusGained

    private void txt_password1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password1FocusLost
    if(txt_password1.getText().isEmpty()) {
        txt_password1.setText("Password");
        txt_password1.setForeground(new Color(153, 153, 153)); // Set text color to light gray
        txt_password1.setEchoChar((char) 0);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password1FocusLost

    private void visible1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visible1MouseClicked
    visible1.setVisible(false);
    invisible1.setVisible(true);
    txt_password1.setEchoChar((char)0);        // TODO add your handling code here:
    }//GEN-LAST:event_visible1MouseClicked

    private void invisible1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invisible1MouseClicked
    invisible1.setVisible(false);
    visible1.setVisible(true);
    txt_password1.setEchoChar('\u2022');         // TODO add your handling code here:
    }//GEN-LAST:event_invisible1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
 MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
        mainInstance.showFormOwner(new usser.tabelUser(mainInstance));
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cbox_jabatan1;
    private javax.swing.JButton clears;
    private javax.swing.JPanel cropPanel;
    private javax.swing.JLabel editProfile;
    private javax.swing.JButton hapus_profil;
    private javax.swing.JLabel invisible1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup jenis;
    private javax.swing.JButton simpans;
    private javax.swing.JLabel tampilkanFoto;
    private javax.swing.JTextArea txt_alamat1;
    private javax.swing.JTextField txt_email1;
    private javax.swing.JTextField txt_nama1;
    private javax.swing.JTextField txt_nohp1;
    private javax.swing.JPasswordField txt_password1;
    private javax.swing.JLabel visible1;
    // End of variables declaration//GEN-END:variables
}
