package login;

import register.Register;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import lupapassword.LupaPassword;
import static mainKaryawan.MainKaryawan.lbl_jabatan1;
import static mainKaryawan.MainKaryawan.lbl_username1;
import static mainKaryawan.MainKaryawan.profil_pegawai;
import static mainOwner.MainOwner.lbl_jabatan;
import static mainOwner.MainOwner.lbl_username;
import static mainOwner.MainOwner.profil_owner;
import session.session;

public class Login extends javax.swing.JFrame {
    private Timer t;
    public static String nameUser;
    public static String userId;
    private String rfid = null;
    private int invalidAttempts = 0;
    
public Login() {
    initComponents();
    startTimer();
    invisible.setVisible(false);
    incorrect.setVisible(false);
    correct.setVisible(false);
    close.setVisible(false);
    
    // Set default text for password and username fields
    txt_password.setText("Password");
    txt_password.setForeground(new Color(153, 153, 153));
    txt_username.setText("Username");
    txt_username.setForeground(new Color(153, 153, 153));
    
    // Set echo char for password field
    txt_password.setEchoChar((char) 0);
    
    // Set focus to login button
    RFIDfield.requestFocusInWindow();
    this.RFIDfield = RFIDfield;

    // Attach key listener to text fields
    txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            handleKeyPress(evt); 
        }
    });
    
    txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            handleKeyPress(evt);
        }
    });
}

private void handleKeyPress(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        login.doClick();
    }
}
        
    private void startTimer() {
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        t.start();
    }

    private void updateTime() {
  
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Date dt = new Date();
                SimpleDateFormat st = new SimpleDateFormat("HH:mm");
                String tt = st.format(dt);
                Time.setText(tt);
            }
        });
    }  
    
    public void stopTimer() {
        t.stop();
    }

public void scanRFID(String rfid, JTextField txtUsername, JPasswordField txtPassword) {
    // SQL query to retrieve username and password based on RFID
    String sql = "SELECT u.username, u.password " +
                 "FROM user u " +
                 "INNER JOIN rfid r ON u.id_user = r.id_user " +
                 "WHERE r.RFID_Tag = ?";
    
    try (java.sql.Connection connLogin = koneksi.configDB();
         PreparedStatement pstmt = connLogin.prepareStatement(sql)) {

        // Set parameter (RFID) for the prepared statement
        pstmt.setString(1, rfid);
        
        // Execute query
        ResultSet rs = pstmt.executeQuery();
        
        // If a matching RFID is found
        if (rs.next()) {
            // Get username and password from the result set
            String username = rs.getString("username");
            String passwordFromDB = rs.getString("password");
            
            // Set username and password text fields
            txtUsername.setText(username);
            txtPassword.setText(passwordFromDB);
        } else {
            // RFID not found in the database
            // You can handle this case as per your requirement, like showing an error message
            System.out.println("RFID not found in the database");
        }
        
        // Close resources
        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle SQL exceptions
    }
}

    public void rfid(){
         Scanner scanner = new Scanner(System.in);
         String input = scanner.nextLine();
        System.out.println("Scanner: "+ input);
         rfidLogin(input);
    }
     
public void rfidLogin(String getRfid) {
    try {
        // Establish database connection
        java.sql.Connection conn = koneksi.configDB();
        rfid = getRfid;
        // SQL query to retrieve user details based on RFID
        String sql = "SELECT u.*, r.kode_RFID " +
                     "FROM user u " +
                     "INNER JOIN rfid r ON u.id_user = r.id_user " +
                     "WHERE r.RFID_Tag=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the RFID parameter in the prepared statement
            pstmt.setString(1, rfid);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // If a user with the given RFID is found
                if (rs.next()) {
                    // Extract user details
                     userId = rs.getString("id_user"); // Mengambil nilai id_user dari ResultSet
//                    String idUser = rs.getString("id_user");
                    String username = rs.getString("username");
                    // Assuming you have a password field in your login form, validate it here
                    // Set session variables
                    session.setUsername(username);
                    session.setId_user(userId);
                    String jabatan = rs.getString("jabatan");        // Check the position and set the visibility accordingly
                    
           if ("pegawai".equals(jabatan)) {
            mainKaryawan.MainKaryawan mainFrame1 = new mainKaryawan.MainKaryawan();
            mainFrame1.setVisible(true);
            lbl_username1.setText(username);
            lbl_jabatan1.setText(jabatan);
            displayProfilePic(username, jabatan);
        } else if ("owner".equals(jabatan)) {
            mainOwner.MainOwner mainFrame = new mainOwner.MainOwner();
            mainFrame.setVisible(true);
            lbl_username.setText(username);
            lbl_jabatan.setText(jabatan);
            displayProfilePic(username, jabatan);
        }
                    // Close the login window
                    this.dispose();
                } else {
                    // Increment invalid attempts count
                    invalidAttempts++;
                    // If invalid attempts exceed the limit
                    if (invalidAttempts >= 3) {
                        // Disable RFID input
                        RFIDfield.setEnabled(false);
                        // Show error message
                        login.requestFocusInWindow();
                        JOptionPane.showMessageDialog(this, "Exceeded maximum invalid attempts. RFID input disabled.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Show error message for invalid RFID
                        JOptionPane.showMessageDialog(this, "Invalid RFID. Attempt " + invalidAttempts + "/3", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Clear username, password, and RFID fields
                    txt_username.setText("");
                    txt_password.setText("");
                    RFIDfield.setText("");
                }
            }
        }
    } catch (SQLException e) {
        // Handle database errors
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
 
private void displayProfilePic(String username, String jabatan) {
    try {
        String query = "SELECT profile_pic FROM user WHERE username=?";
        try (Connection conn = koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    byte[] imageData = rs.getBytes("profile_pic");
                    if (imageData != null) {
                        ImageIcon imageIcon = new ImageIcon(imageData);
                        int width = 50; // Tentukan ukuran gambar, misalnya 100 pixel
                        int height = 50; // Tentukan ukuran gambar, misalnya 100 pixel
                        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(image);
                        if (jabatan.equals("pegawai")) {
                            profil_pegawai.setIcon(scaledIcon);
                        } else if (jabatan.equals("owner")) {
                            profil_owner.setIcon(scaledIcon);
                        }
                    } else {
                        if (jabatan.equals("pegawai")) {
                            profil_pegawai.setIcon(new ImageIcon(getClass().getResource("/mainOwner/defaultProfil.png")));
                        } else if (jabatan.equals("owner")) {
                            profil_owner.setIcon(new ImageIcon(getClass().getResource("/mainOwner/defaultProfil.png")));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Data gambar tidak ditemukan.");
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        close = new javax.swing.JLabel();
        correct = new javax.swing.JLabel();
        incorrect = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        registerasi = new javax.swing.JButton();
        login = new javax.swing.JButton();
        Lupa = new javax.swing.JLabel();
        invisible = new javax.swing.JLabel();
        visible = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        Dashboard = new javax.swing.JLabel();
        RFIDfield = new javax.swing.JTextField();
        Time = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocation(new java.awt.Point(71, 38));
        setMinimumSize(new java.awt.Dimension(1224, 691));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, -1, -1));

        correct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/correct.png"))); // NOI18N
        getContentPane().add(correct, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

        incorrect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/invalid.png"))); // NOI18N
        getContentPane().add(incorrect, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

        txt_username.setBackground(new java.awt.Color(237, 245, 224));
        txt_username.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txt_username.setBorder(null);
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 290, 320, 20));

        registerasi.setForeground(new java.awt.Color(10, 86, 56));
        registerasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/Register Button.png"))); // NOI18N
        registerasi.setBorder(null);
        registerasi.setBorderPainted(false);
        registerasi.setContentAreaFilled(false);
        registerasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerasiActionPerformed(evt);
            }
        });
        getContentPane().add(registerasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 490, 160, 40));

        login.setBackground(new java.awt.Color(242, 242, 242));
        login.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/Button Login.png"))); // NOI18N
        login.setBorder(null);
        login.setBorderPainted(false);
        login.setContentAreaFilled(false);
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        getContentPane().add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 370, 400, 60));

        Lupa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Lupa.setForeground(new java.awt.Color(10, 86, 56));
        Lupa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lupa.setText("Lupa Password?");
        Lupa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LupaMouseClicked(evt);
            }
        });
        getContentPane().add(Lupa, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 440, -1, 20));

        invisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/Not Visible.png"))); // NOI18N
        invisible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invisibleMouseClicked(evt);
            }
        });
        getContentPane().add(invisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 330, -1, 40));

        visible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/Visible.png"))); // NOI18N
        visible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visibleMouseClicked(evt);
            }
        });
        getContentPane().add(visible, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 330, -1, 40));

        txt_password.setBackground(new java.awt.Color(237, 245, 224));
        txt_password.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txt_password.setBorder(null);
        txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passwordFocusLost(evt);
            }
        });
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 340, 320, 20));

        Dashboard.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        Dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/Login.png"))); // NOI18N
        getContentPane().add(Dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 690));

        RFIDfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RFIDfieldActionPerformed(evt);
            }
        });
        getContentPane().add(RFIDfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 0));

        Time.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Time.setForeground(new java.awt.Color(255, 255, 255));
        Time.setText("_");
        getContentPane().add(Time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void registerasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerasiActionPerformed
    Register frameRegister = new Register();
    frameRegister.setVisible(true);
    this.setVisible(false); 
    }//GEN-LAST:event_registerasiActionPerformed
    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
    }//GEN-LAST:event_txt_usernameActionPerformed
    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained
        if(txt_username.getText().equals("Username")) {
            txt_username.setText("");
            txt_username.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txt_usernameFocusGained
    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
       if(txt_username.getText().equals("")) {
            txt_username.setText("Username");
            txt_username.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_usernameFocusLost
    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
    }//GEN-LAST:event_txt_passwordActionPerformed
    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
 try {
        String loginQuery = "SELECT * FROM user WHERE username=? AND password=?";

        try (Connection connLogin = koneksi.configDB();
             PreparedStatement pstLogin = connLogin.prepareStatement(loginQuery)) {

            pstLogin.setString(1, txt_username.getText());
            pstLogin.setString(2, new String(txt_password.getPassword()));

            try (ResultSet rsLogin = pstLogin.executeQuery()) {
               if (rsLogin.next()) {
                        correct.setVisible(true);
                        close.setVisible(true);
                        userId = rsLogin.getString("id_user");
                        String jabatan = rsLogin.getString("jabatan");
                        String username = rsLogin.getString("username");

                        Timer timerCorrect = new Timer(3000, (e) -> {
                            correct.setVisible(false);
                            close.setVisible(false);
                            this.setVisible(false);

                            if ("owner".equals(jabatan)) {
                                mainOwner.MainOwner mainFrame = new mainOwner.MainOwner();
                                mainFrame.setVisible(true);
                                lbl_username.setText(username);
                                lbl_jabatan.setText(jabatan);
                                displayProfilePic(username, jabatan);

                            } else if ("pegawai".equals(jabatan)) {
                                mainKaryawan.MainKaryawan mainFrame1 = new mainKaryawan.MainKaryawan();
                                mainFrame1.setVisible(true);
                                lbl_username1.setText(username);
                                lbl_jabatan1.setText(jabatan);
                                displayProfilePic(username, jabatan);
                            }
                        });
                        timerCorrect.setRepeats(false);
                        timerCorrect.start();
                    } else {
                        incorrect.setVisible(true);
                        close.setVisible(true);

                        Timer timerIncorrect = new Timer(3000, (e) -> {
                            incorrect.setVisible(false);
                            close.setVisible(false);
                        });
                        timerIncorrect.setRepeats(false);
                        timerIncorrect.start();
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_loginActionPerformed
    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
    if(txt_password.getText().equals("Password")) {
        txt_password.setText("");
        txt_password.setForeground(new Color(0, 0, 0));
        visible.setVisible(true);
        txt_password.setEchoChar('\u2022');
    }
    }//GEN-LAST:event_txt_passwordFocusGained
    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost
    if(txt_password.getText().isEmpty()) {
        txt_password.setText("Password");
        txt_password.setForeground(new Color(153, 153, 153)); // Set text color to light gray
        txt_password.setEchoChar((char) 0);
    }
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

    private void LupaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LupaMouseClicked
    LupaPassword lupapasswordLupaPassword = new LupaPassword();
    lupapasswordLupaPassword.setVisible(true);
    this.setVisible(false); 

    try (Connection conn = koneksi.configDB();
         PreparedStatement pst = conn.prepareStatement("DELETE FROM `otp`")) {
        // Execute the delete query
        pst.executeUpdate();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    }//GEN-LAST:event_LupaMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
    correct.setVisible(false);
    incorrect.setVisible(false);
    close.setVisible(false);      // TODO add your handling code here:
    }//GEN-LAST:event_closeMouseClicked

    private void RFIDfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RFIDfieldActionPerformed
    String RFID = RFIDfield.getText(); // Retrieve the text from the text field
    rfidLogin(RFID);
    }//GEN-LAST:event_RFIDfieldActionPerformed
    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dashboard;
    private javax.swing.JLabel Lupa;
    private javax.swing.JTextField RFIDfield;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel close;
    private javax.swing.JLabel correct;
    private javax.swing.JLabel incorrect;
    private javax.swing.JLabel invisible;
    private javax.swing.JButton login;
    private javax.swing.JButton registerasi;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    private javax.swing.JLabel visible;
    // End of variables declaration//GEN-END:variables
}