package usser;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import koneksi.koneksi;
import mainOwner.MainOwner;
import notifikasi.notifGagal;
import notifikasi.notifSukses;
import raven.glasspanepopup.GlassPanePopup;

public class editUser extends javax.swing.JPanel {
    private Connection conn; // java.sql.Connection;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res; // java.sql.ResultSet;
    private String imagePath; // menyimpan path gambar yang diunggah
   private tabelUser panelUser;
   private static final int CIRCLE_RADIUS = 300; // Sesuaikan dengan kebutuhan
   
    public editUser() {
        initComponents();
        invisible.setVisible(false);
        iduser.setVisible(false);

        }
        public void setPanelUser(tabelUser panelUser) {
        this.panelUser = panelUser;
        }
        
  private void uploadImage1() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            displayImage1(imagePath);      
        }
    } 
  
private void displayImage1(String path) {
    ImageIcon imageIcon = new ImageIcon(path); 
    Image image = imageIcon.getImage(); 
    int labelWidth = lbl_profile.getWidth();
    int labelHeight = lbl_profile.getHeight();
    BufferedImage bufferedImage = new BufferedImage(labelWidth, labelHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.drawImage(image, 0, 0, labelWidth, labelHeight, null); 
    g2.dispose();
    ImageIcon scaledImageIcon = new ImageIcon(bufferedImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH));
    lbl_profile.setIcon(scaledImageIcon);
}      

public void displayProfileIEdit(byte[] imageData) {
        if (imageData != null) {
            ImageIcon imageIcon = new ImageIcon(imageData);
            Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Sesuaikan ukuran gambar
            lbl_profile.setIcon(new ImageIcon(image));
        } else {
            lbl_profile.setIcon(null);
        }
}

private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
}
private boolean isValidPhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^\\+?[0-9]{7,14}$") || phoneNumber.matches("^[0-9]{7,12}$");
}

private void editData() {
    try {
        String Username = txt_nama.getText();
        String password = txt_password.getText();
        String jabatan = cbox_jabatan.getText().toString();
        String email = txt_email.getText();
        String No_Hp = txt_nohp.getText();
        String alamat = txt_alamat.getText();
        String id_user = iduser.getText();

        // Validasi input
        if (Username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            GlassPanePopup.showPopup(new notifGagal("Username,Password,Email tidak boleh kosong "));
            return;
        }

        // Validasi panjang password
        if (password.length() < 6) {
        GlassPanePopup.showPopup(new notifGagal("Password minimal harus 6 karakter "));
        return;
        }
        // Validasi format email
        if (!isValidEmail(email)) {
            GlassPanePopup.showPopup(new notifGagal("Masukkan email yang valid "));
            return;
        }

        if (!isValidPhoneNumber(No_Hp)) {
            GlassPanePopup.showPopup(new notifGagal("Masukkan No Hp yang valid"));
        return;
        }
        conn = koneksi.configDB();
        // Ambil byte array gambar baru jika ada
        byte[] newImageData = null;
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            FileInputStream fis = new FileInputStream(imageFile);
            newImageData = new byte[(int) imageFile.length()];
            fis.read(newImageData);
            fis.close();
        }

        // Ambil byte array gambar lama
//        byte[] imageData = getImageDataFromDatabase(id_user);

        String checkQuery = "SELECT id_user FROM user WHERE id_user=?";
        pstm = conn.prepareStatement(checkQuery);
        pstm.setString(1, id_user);
        res = pstm.executeQuery();

        if (res.next()) {
            String updateQuery;
            if (newImageData != null) {
                // Jika ada gambar baru, gunakan gambar baru
                updateQuery = "UPDATE user SET Username=?, password=?, jabatan=?, email=?, No_Hp=?, alamat=?, profile_pic=? WHERE id_user=?";
                pstm = conn.prepareStatement(updateQuery);
                pstm.setString(1, Username);
                pstm.setString(2, password);
                pstm.setString(3, jabatan);
                pstm.setString(4, email);
                pstm.setString(5, No_Hp);
                pstm.setString(6, alamat);
                pstm.setBytes(7, newImageData);
                pstm.setString(8, id_user);
            } else {
                // Jika tidak ada gambar baru, gunakan gambar lama
                updateQuery = "UPDATE user SET Username=?, password=?, jabatan=?, email=?, No_Hp=?, alamat=? WHERE id_user=?";
                pstm = conn.prepareStatement(updateQuery);
                pstm.setString(1, Username);
                pstm.setString(2, password);
                pstm.setString(3, jabatan);
                pstm.setString(4, email);
                pstm.setString(5, No_Hp);
                pstm.setString(6, alamat);
                pstm.setString(7, id_user);
            }

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
//                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
                GlassPanePopup.showPopup(new notifSukses("Data berhasil diperbarui "));
                   int durationInMillis = 2000;
        Timer timer = new Timer(durationInMillis, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        GlassPanePopup.closePopupAll(); 
        }
        });
        timer.setRepeats(false);
        timer.start();
    
                // Tampilkan kembali panel tabel user setelah update
                MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
                mainInstance.showFormOwner(new usser.tabelUser(mainInstance));
            } else {
            GlassPanePopup.showPopup(new notifGagal("Gagal memperbarui data"));
        return;
            }
        } else {
            GlassPanePopup.showPopup(new notifGagal("Data dengan id_user yang diberikan tidak ditemukan "));
        }
    } catch (SQLException e) {
          
    } catch (Exception e) {
    }
}

//
//private byte[] getImageDataFromDatabase(String id_user) throws SQLException {
//    String query = "SELECT profile_pic FROM user WHERE id_user=?";
//    pstm = conn.prepareStatement(query);
//    pstm.setString(1, id_user);
//    ResultSet rs = pstm.executeQuery();
//    if (rs.next()) {
//        return rs.getBytes("profile_pic");
//    }
//    return null;
//}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        invisible = new javax.swing.JLabel();
        visible = new javax.swing.JLabel();
        simpans = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_hapusProfile = new javax.swing.JButton();
        labelRFID = new javax.swing.JTextField();
        iduser = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_nohp = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        cbox_jabatan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat = new javax.swing.JTextArea();
        addRFID = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_profile = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        deleteRFID = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setBackground(new java.awt.Color(231, 231, 231));
        setPreferredSize(new java.awt.Dimension(996, 595));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        invisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Not Visible pw.png"))); // NOI18N
        invisible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invisibleMouseClicked(evt);
            }
        });
        add(invisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 242, -1, -1));

        visible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/Visible pw.png"))); // NOI18N
        visible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visibleMouseClicked(evt);
            }
        });
        add(visible, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 242, -1, -1));

        simpans.setBackground(new java.awt.Color(10, 172, 42));
        simpans.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpans.setForeground(new java.awt.Color(255, 255, 255));
        simpans.setBorder(null);
        simpans.setContentAreaFilled(false);
        simpans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpansActionPerformed(evt);
            }
        });
        add(simpans, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 110, 30));

        btn_batal.setBorder(null);
        btn_batal.setContentAreaFilled(false);
        btn_batal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_batalMouseClicked(evt);
            }
        });
        add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 490, 120, 30));

        btn_hapusProfile.setBackground(new java.awt.Color(204, 0, 51));
        btn_hapusProfile.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_hapusProfile.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapusProfile.setBorder(null);
        btn_hapusProfile.setContentAreaFilled(false);
        btn_hapusProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_hapusProfileMouseClicked(evt);
            }
        });
        btn_hapusProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusProfileActionPerformed(evt);
            }
        });
        add(btn_hapusProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, 100, 30));

        labelRFID.setBackground(new java.awt.Color(255, 252, 252));
        labelRFID.setText("RFID");
        labelRFID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelRFID.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        labelRFID.setEnabled(false);
        labelRFID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelRFIDActionPerformed(evt);
            }
        });
        add(labelRFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 328, 90, -1));

        iduser.setEditable(false);
        iduser.setText("idUser");
        add(iduser, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 470, 230, -1));

        txt_nama.setBackground(new java.awt.Color(255, 252, 252));
        txt_nama.setText("username");
        txt_nama.setBorder(null);
        add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 114, 270, 20));

        txt_email.setBackground(new java.awt.Color(255, 252, 252));
        txt_email.setText("email");
        txt_email.setBorder(null);
        txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_emailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_emailFocusLost(evt);
            }
        });
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 197, 270, 20));

        txt_nohp.setBackground(new java.awt.Color(255, 252, 252));
        txt_nohp.setText("nohp");
        txt_nohp.setBorder(null);
        txt_nohp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nohpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nohpFocusLost(evt);
            }
        });
        txt_nohp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nohpActionPerformed(evt);
            }
        });
        add(txt_nohp, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 285, 270, 20));

        txt_password.setBackground(new java.awt.Color(255, 252, 252));
        txt_password.setText("Password");
        txt_password.setBorder(null);
        txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passwordFocusLost(evt);
            }
        });
        add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 242, 250, -1));

        cbox_jabatan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cbox_jabatan.setText("pegawai");
        add(cbox_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 158, 270, -1));

        txt_alamat.setBackground(new java.awt.Color(255, 252, 252));
        txt_alamat.setColumns(20);
        txt_alamat.setLineWrap(true);
        txt_alamat.setRows(1);
        txt_alamat.setWrapStyleWord(true);
        txt_alamat.setBorder(null);
        txt_alamat.setPreferredSize(new java.awt.Dimension(165, 15));
        txt_alamat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_alamatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_alamatFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txt_alamat);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 290, 70));

        addRFID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/buttonRFID.png"))); // NOI18N
        addRFID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRFIDMouseClicked(evt);
            }
        });
        add(addRFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 325, -1, -1));

        jPanel2.setBackground(new java.awt.Color(230, 236, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(150, 150));
        jPanel2.setMinimumSize(new java.awt.Dimension(150, 150));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/pen 1.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 40, -1));

        lbl_profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/defaultProfile.png"))); // NOI18N
        lbl_profile.setMaximumSize(new java.awt.Dimension(150, 150));
        lbl_profile.setMinimumSize(new java.awt.Dimension(150, 150));
        lbl_profile.setPreferredSize(new java.awt.Dimension(230, 230));
        jPanel2.add(lbl_profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 200));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 172, 200, 200));
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 600));

        deleteRFID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/delete_RFID.png"))); // NOI18N
        deleteRFID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteRFIDMouseClicked(evt);
            }
        });
        add(deleteRFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 323, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainEditUser.png"))); // NOI18N
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nohpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nohpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nohpActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void simpansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpansActionPerformed
    editData();
    }//GEN-LAST:event_simpansActionPerformed

    private void txt_nohpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nohpFocusGained

    }//GEN-LAST:event_txt_nohpFocusGained

    private void txt_nohpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nohpFocusLost

    }//GEN-LAST:event_txt_nohpFocusLost

    private void txt_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusGained

    }//GEN-LAST:event_txt_emailFocusGained

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost

    }//GEN-LAST:event_txt_emailFocusLost

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
     uploadImage1();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txt_alamatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamatFocusGained

    }//GEN-LAST:event_txt_alamatFocusGained

    private void txt_alamatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_alamatFocusLost
        if(txt_alamat.getText().isEmpty()) {
            txt_alamat.setText("Alamat");
            txt_alamat.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_alamatFocusLost

    private void btn_batalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_batalMouseClicked
    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
    mainInstance.showFormOwner(new usser.tabelUser(mainInstance));
    }//GEN-LAST:event_btn_batalMouseClicked

    private void btn_hapusProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusProfileMouseClicked

        String id_user = iduser.getText();
    try {
        conn = koneksi.configDB();
        
        String query = "UPDATE user SET profile_pic = ? WHERE id_user = ?";
        pstm = conn.prepareStatement(query);
        pstm.setBytes(1, null); // Menghapus profile_pic dengan mengatur nilainya menjadi null
        pstm.setString(2, id_user);
        pstm.executeUpdate();
        lbl_profile.setIcon(null);
    } catch (SQLException e) {
    }
    }//GEN-LAST:event_btn_hapusProfileMouseClicked

    private void btn_hapusProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusProfileActionPerformed
  
    }//GEN-LAST:event_btn_hapusProfileActionPerformed

    private void addRFIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRFIDMouseClicked
GlassPanePopup.showPopup(new popupRFID(iduser.getText()));
    }//GEN-LAST:event_addRFIDMouseClicked

    private void deleteRFIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteRFIDMouseClicked
  String idUser = iduser.getText(); // Dapatkan nilai id_user dari JTextField

    // Tampilkan konfirmasi sebelum menghapus RFID entry
    int option = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus RFID entry?", "Konfirmasi Hapus RFID", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        String sqlDelete = "DELETE FROM rfid WHERE id_user = ?";

        try (Connection conn = koneksi.configDB();
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {

            pstmtDelete.setString(1, idUser);
            int rowsDeleted = pstmtDelete.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("RFID entry deleted successfully!");
                labelRFID.setVisible(false);
                addRFID.setVisible(true);
                deleteRFID.setVisible(false);
                GlassPanePopup.showPopup(new notifSukses("RFID entry berhasil dihapus"));
                int durationInMillis = 1500;
                Timer timer = new Timer(durationInMillis, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GlassPanePopup.closePopupAll(); 
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                System.out.println("No RFID entry deleted!");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting RFID entry: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_deleteRFIDMouseClicked

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
       visible.setVisible(true);
        txt_password.setEchoChar('\u2022');
    }//GEN-LAST:event_txt_passwordFocusGained

    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost
        txt_password.setEchoChar((char) 0);
    }//GEN-LAST:event_txt_passwordFocusLost

    private void visibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visibleMouseClicked
    visible.setVisible(false);
    invisible.setVisible(true);
    txt_password.setEchoChar((char)0); 
    }//GEN-LAST:event_visibleMouseClicked

    private void invisibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invisibleMouseClicked
    invisible.setVisible(false);
    visible.setVisible(true);
    txt_password.setEchoChar('\u2022'); 
    }//GEN-LAST:event_invisibleMouseClicked

    private void labelRFIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelRFIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_labelRFIDActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel addRFID;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapusProfile;
    public static javax.swing.JLabel cbox_jabatan;
    public static javax.swing.JLabel deleteRFID;
    public static javax.swing.JTextField iduser;
    private javax.swing.JLabel invisible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField labelRFID;
    public static javax.swing.JLabel lbl_profile;
    private javax.swing.JButton simpans;
    public static javax.swing.JTextArea txt_alamat;
    public static javax.swing.JTextField txt_email;
    public static javax.swing.JTextField txt_nama;
    public static javax.swing.JTextField txt_nohp;
    public static javax.swing.JPasswordField txt_password;
    private javax.swing.JLabel visible;
    // End of variables declaration//GEN-END:variables
}
