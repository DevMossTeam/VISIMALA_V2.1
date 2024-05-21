package register;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import koneksi.koneksi;
import login.Login;

public class Register extends javax.swing.JFrame {
    public Register() {
        initComponents();
        inemail.setVisible(false);
         inpass.setVisible(false);
        correct.setVisible(false);
         incorrect.setVisible(false);
        close.setVisible(false); // Hide close JButton
        boolean ownerExists = checkOwnerExists();
        jComboBox1.setVisible(!ownerExists);
        txt_password.setText("Password");
txt_password.setForeground(new Color(153, 153, 153));
txt_confirmPassword.setText("Confirm Password");
txt_confirmPassword.setForeground(new Color(153, 153, 153));
txt_username.setText("Username");
txt_username.setForeground(new Color(153, 153, 153));
txt_email.setText("Email");
txt_email.setForeground(new Color(153, 153, 153));

// Set echo char for password fields
txt_password.setEchoChar((char) 0);
txt_confirmPassword.setEchoChar((char) 0);

// Set focus to login button
daftar.requestFocusInWindow();

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

txt_confirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(java.awt.event.KeyEvent evt) {
        handleKeyPress(evt);
    }
});

txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(java.awt.event.KeyEvent evt) {
        handleKeyPress(evt);
    }
});
    }
private void handleKeyPress(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        daftar.doClick();
    }
}
private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    return email.matches(emailRegex);
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
                number++; // If ID is already used, increment the number
            }
        }

        rs.close();
        checkStmt.close();
    }

    return idUser;
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        close = new javax.swing.JLabel();
        incorrect = new javax.swing.JLabel();
        inemail = new javax.swing.JLabel();
        correct = new javax.swing.JLabel();
        inpass = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_confirmPassword = new javax.swing.JPasswordField();
        txt_password = new javax.swing.JPasswordField();
        Login = new javax.swing.JButton();
        daftar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(71, 38));
        setMaximumSize(new java.awt.Dimension(1224, 691));
        setMinimumSize(new java.awt.Dimension(1224, 691));
        setPreferredSize(new java.awt.Dimension(1224, 691));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, -1, -1));

        incorrect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/incorrect.png"))); // NOI18N
        getContentPane().add(incorrect, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

        inemail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/incorrect.png"))); // NOI18N
        inemail.setAutoscrolls(true);
        getContentPane().add(inemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

        correct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/correct.png"))); // NOI18N
        getContentPane().add(correct, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

        inpass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/Inpassword.png"))); // NOI18N
        getContentPane().add(inpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, 250));

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
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 320, 20));

        txt_email.setBackground(new java.awt.Color(237, 245, 224));
        txt_email.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
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
        getContentPane().add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 310, 20));

        txt_confirmPassword.setBackground(new java.awt.Color(237, 245, 224));
        txt_confirmPassword.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txt_confirmPassword.setBorder(null);
        txt_confirmPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_confirmPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_confirmPasswordFocusLost(evt);
            }
        });
        txt_confirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_confirmPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txt_confirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 300, 20));

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
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 300, 20));

        Login.setForeground(new java.awt.Color(0, 102, 204));
        Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/button login.png"))); // NOI18N
        Login.setBorder(null);
        Login.setBorderPainted(false);
        Login.setContentAreaFilled(false);
        Login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Login.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        getContentPane().add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, 140, 60));

        daftar.setBackground(new java.awt.Color(10, 86, 56));
        daftar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        daftar.setForeground(new java.awt.Color(255, 255, 255));
        daftar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/button register.png"))); // NOI18N
        daftar.setBorder(null);
        daftar.setContentAreaFilled(false);
        daftar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        daftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarActionPerformed(evt);
            }
        });
        getContentPane().add(daftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 400, 60));

        jComboBox1.setBackground(new java.awt.Color(237, 245, 224));
        jComboBox1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "owner", "pegawai", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 430, 80, 40));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/Register.png"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void daftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarActionPerformed
    if (!txt_username.getText().isEmpty() && !txt_password.getText().isEmpty() && !txt_email.getText().isEmpty() && txt_password.getText().equals(txt_confirmPassword.getText())) {
        if (txt_password.getText().length() >= 6) {
            if (isValidEmail(txt_email.getText())) {
                try {
                    // Check if "Owner" combo box is visible
                    String selectedRole = jComboBox1.isVisible() ? jComboBox1.getSelectedItem().toString() : "Pegawai";

                    String sql = "INSERT INTO user (id_user, username, password, email, jabatan) VALUES (?, ?, ?, ?, ?)";

                    java.sql.Connection conn = (Connection) koneksi.configDB();
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    String idUser = generateUserId(conn); // Generate user ID
                    
                    pst.setString(1, idUser);
                    pst.setString(2, txt_username.getText());
                    pst.setString(3, txt_password.getText());
                    pst.setString(4, txt_email.getText());
                    pst.setString(5, selectedRole);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        SwingUtilities.invokeLater(() -> {
                            correct.setVisible(true);
                            close.setVisible(true); // Set close JButton visible

                            // Close the correct JLabel after 3 seconds
                            Timer timerCorrect = new Timer(3000, (e) -> {
                                correct.setVisible(false);
                                close.setVisible(false); // Hide close JButton
                                this.setVisible(false);
                                new Login().setVisible(true);
                            });
                            timerCorrect.setRepeats(false); // Ensure it runs only once
                            timerCorrect.start();
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            incorrect.setVisible(true);
                            close.setVisible(true); // Set close JButton visible

                            // Close the incorrect JLabel after 3 seconds
                            Timer timerIncorrect = new Timer(3000, (e) -> {
                                incorrect.setVisible(false);
                                close.setVisible(false); // Hide close JButton
                            });
                            timerIncorrect.setRepeats(false); // Ensure it runs only once
                            timerIncorrect.start();
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            } else {
                inemail.setVisible(true); // Set inemail JLabel visible
                close.setVisible(true); // Set close JButton visible

                // Close the inemail JLabel after 3 seconds
                Timer timerInemail = new Timer(3000, (e) -> {
                    inemail.setVisible(false);
                    close.setVisible(false); // Hide close JButton
                });
                timerInemail.setRepeats(false); // Ensure it runs only once
                timerInemail.start();
            }
        } else {
            inpass.setVisible(true); // Set inpass JLabel visible
            close.setVisible(true); // Set close JButton visible

            // Close the inpass JLabel after 3 seconds
            Timer timerInpass = new Timer(3000, (e) -> {
                inpass.setVisible(false);
                close.setVisible(false); // Hide close JButton
            });
            timerInpass.setRepeats(false); // Ensure it runs only once
            timerInpass.start();
        }
    } else {
        if (!isValidEmail(txt_email.getText())) {
            inemail.setVisible(true); // Set inemail JLabel visible
            close.setVisible(true); // Set close JButton visible

            // Close the inemail JLabel after 3 seconds
            Timer timerInemail = new Timer(3000, (e) -> {
                inemail.setVisible(false);
                close.setVisible(false); // Hide close JButton
            });
            timerInemail.setRepeats(false); // Ensure it runs only once
            timerInemail.start();
        } else {
            incorrect.setVisible(true);
            close.setVisible(true); // Set close JButton visible

            // Close the incorrect JLabel after 3 seconds
            Timer timerIncorrect = new Timer(3000, (e) -> {
                incorrect.setVisible(false);
                close.setVisible(false); // Hide close JButton
            });
            timerIncorrect.setRepeats(false); // Ensure it runs only once
            timerIncorrect.start();
        }
    }
    }//GEN-LAST:event_daftarActionPerformed

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
    if(txt_password.getText().equals("Password")) {
        txt_password.setText("");
        txt_password.setForeground(new Color(0, 0, 0));
        txt_password.setEchoChar('\u2022'); // Set echo character to hide password
    }
    }//GEN-LAST:event_txt_passwordFocusGained

    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost
    if(txt_password.getText().isEmpty()) {
        txt_password.setText("Password");
        txt_password.setForeground(new Color(153, 153, 153)); // Set text color to light gray
        txt_password.setEchoChar((char) 0); // Remove echo character
    }
    }//GEN-LAST:event_txt_passwordFocusLost

    private void txt_confirmPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_confirmPasswordFocusGained
    if(txt_confirmPassword.getText().equals("Confirm Password")) {
        txt_confirmPassword.setText("");
        txt_confirmPassword.setForeground(new Color(0, 0, 0));
        txt_confirmPassword.setEchoChar('\u2022'); // Set echo character to hide password
    }
    }//GEN-LAST:event_txt_confirmPasswordFocusGained

    private void txt_confirmPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_confirmPasswordFocusLost
    if(txt_confirmPassword.getText().isEmpty()) {
        txt_confirmPassword.setText("Confirm Password");
        txt_confirmPassword.setForeground(new Color(153, 153, 153)); // Set text color to light gray
        txt_confirmPassword.setEchoChar((char) 0); // Remove echo character
    }
    }//GEN-LAST:event_txt_confirmPasswordFocusLost

    private void txt_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusGained
    if(txt_email.getText().equals("Email")) {
        txt_email.setText("");
        txt_email.setForeground(new Color(0, 0, 0));
    }
    }//GEN-LAST:event_txt_emailFocusGained

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost
    if(txt_email.getText().isEmpty()) {
        txt_email.setText("Email");
        txt_email.setForeground(new Color(153, 153, 153));
    }
    }//GEN-LAST:event_txt_emailFocusLost

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed

    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed

    }//GEN-LAST:event_txt_emailActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    }
    private boolean checkOwnerExists() {
    boolean ownerExists = false;
    String ownerRole = "Owner";

    try {
        // Establish database connection
        Connection conn = (Connection) koneksi.configDB();

        // Check if there is any user with "Owner" role
        String checkOwnerSql = "SELECT COUNT(*) FROM user WHERE jabatan = ?";
        java.sql.PreparedStatement checkOwnerPst = conn.prepareStatement(checkOwnerSql);
        checkOwnerPst.setString(1, ownerRole);
        java.sql.ResultSet rs = checkOwnerPst.executeQuery();

        // If the count is greater than 0, "Owner" already exists
        if (rs.next() && rs.getInt(1) > 0) {
            ownerExists = true;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    return ownerExists;
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained
    if(txt_username.getText().equals("Username")) {
        txt_username.setText("");
        txt_username.setForeground(new Color(0,0,0));
    }
    }//GEN-LAST:event_txt_usernameFocusGained

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
    if(txt_username.getText().isEmpty()) {
        txt_username.setText("Username");
        txt_username.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_txt_usernameFocusLost

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed

    }//GEN-LAST:event_txt_usernameActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        dispose();
        Login frameLogin = new Login();
        frameLogin.setVisible(true);
    }//GEN-LAST:event_LoginActionPerformed

    private void txt_confirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_confirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_confirmPasswordActionPerformed

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        inemail.setVisible(false);
        correct.setVisible(false);
        incorrect.setVisible(false);
        inpass.setVisible(false);
        close.setVisible(false);      // TODO add your handling code here:
    }//GEN-LAST:event_closeMouseClicked
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton Login;
    private javax.swing.JLabel close;
    private javax.swing.JLabel correct;
    private javax.swing.JButton daftar;
    private javax.swing.JLabel incorrect;
    private javax.swing.JLabel inemail;
    private javax.swing.JLabel inpass;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPasswordField txt_confirmPassword;
    private javax.swing.JTextField txt_email;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
