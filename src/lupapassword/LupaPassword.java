package lupapassword;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import koneksi.koneksi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import login.Login;
//import login.Login;
//import login.Login;

public class LupaPassword extends javax.swing.JFrame {
    private final int COUNTDOWN_DURATION = 61; // Countdown duration in seconds
    private Timer timer;
    private int secondsLeft;
    private String userEmail;
    private final JTextField[] otpFields = new JTextField[6];
    
public LupaPassword() {
    initComponents();
    
    // Set initial visibility of panels and reload button
    Panel1.setVisible(true);
    Panel2.setVisible(false);
    Panel3.setVisible(false);
    Reload.setVisible(false);
    
    // Set default text and color for email text field in Panel1
    txt_email.setText("Email");
    txt_email.setForeground(new Color(153, 153, 153));
    txt_email.requestFocusInWindow();
    
    // Set default text and color for new password text field in Panel2
    txt_password2.setText("Password Baru");
    txt_password2.setForeground(new Color(153, 153, 153));
    
    // Set default text and color for confirm password text field in Panel3
    txt_password1.setText("Konfirmasi Password");
    txt_password1.setForeground(new Color(153, 153, 153));
    
    // Request focus for Button1 after making Panel1 visible
    Panel1.requestFocusInWindow();
    Button1.requestFocusInWindow();
    
    // Request focus for Button2 after making Panel2 visible
    Panel2.requestFocusInWindow();
    
    // Request focus for Button3 after making Panel3 visible
    Panel3.requestFocusInWindow();
    Button3.requestFocusInWindow();
    
    // Start countdown timer
    startCountdownTimer();
       
    otpFields[0] = jTextField1;
    otpFields[1] = jTextField2;
    otpFields[2] = jTextField3;
    otpFields[3] = jTextField4;
    otpFields[4] = jTextField5;
    otpFields[5] = jTextField6;

    Font numberFont = new Font("Arial", Font.BOLD, 16); // Adjust the font size as needed

    for (int i = 0; i < otpFields.length; i++) {
        otpFields[i].setFont(numberFont); // Set the font for each JTextField
        otpFields[i].setHorizontalAlignment(JTextField.CENTER);
        otpFields[i].setMargin(new Insets(5, 5, 5, 5)); // Adjust margin if needed

        final int index = i; // Create a final variable for the lambda
        ((AbstractDocument) otpFields[i].getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newText = otpFields[index].getText().substring(0, offset) + text + otpFields[index].getText().substring(offset + length);
                if (newText.length() <= 1 && newText.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                    focusNextTextField(index);
                    if (index == otpFields.length - 1) {
                        submitOTP(); // Automatically submit OTP if last box is filled
                    }
                }
            }
        });

        otpFields[i].addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && otpFields[index].getText().isEmpty() && index > 0) {
                    otpFields[index - 1].requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    pack();
}

    private void focusNextTextField(int index) {
        if (index < otpFields.length - 1) {
            otpFields[index + 1].requestFocus();
        }
    }
    
private void submitOTP() {
    StringBuilder otp = new StringBuilder();
    for (JTextField field : otpFields) {
        otp.append(field.getText());
    }
    String otpKode = otp.toString(); // Concatenate OTP from all fields

    try (Connection conn = koneksi.configDB();
         PreparedStatement pst = conn.prepareStatement("SELECT * FROM otp WHERE kode = ?")) {
        pst.setString(1, otpKode);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // OTP code found in the database, proceed to Panel3
            Panel3.setVisible(true);
            Panel2.setVisible(false);
        } else {
            // OTP code not found in the database, show error message
            JOptionPane.showMessageDialog(this, "OTP Salah");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
    // You may add additional logic here if needed
    System.out.println("Submission of OTP completed.");
}


    private void startCountdownTimer() {
        secondsLeft = COUNTDOWN_DURATION;
        timer = new Timer(1000, (java.awt.event.ActionEvent evt) -> {
            secondsLeft--;
            if (secondsLeft >= 0) {
                updateTimeLabel();
            } else {
                timer.stop();
                // Countdown reached 00:00
                Reload.setVisible(true); // Set Reload JLabel visible
                Time.setVisible(false);  // Set Time JLabel invisible
            }
        });
        timer.start();
    }

    private void updateTimeLabel() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String timeText = String.format("%02d:%02d", minutes, seconds);
        Time.setText(timeText); // Update Time JLabel with current time
    }
    
 private static String generateidKode(Connection conn) throws SQLException {
    String prefix = "OTP";
    int number = 1;
    String idKode = "";

    boolean idFound = false;
    while (!idFound) {
        String userIdToCheck = prefix + number;
        String query = "SELECT COUNT(*) FROM otp WHERE ID_Kode = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(query)) {
            checkStmt.setString(1, userIdToCheck);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count == 0) {
                        idKode = userIdToCheck;
                        idFound = true;
                    } else {
                        number++; // If ID is already used, increment the number
                    }
                }
            }
        }
    }

    return idKode;
}
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel();
        Button1 = new javax.swing.JButton();
        txt_email = new javax.swing.JTextField();
        Cancel = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        Panel2 = new javax.swing.JPanel();
        anno = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        Reload = new javax.swing.JLabel();
        Cancel1 = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        Background1 = new javax.swing.JLabel();
        Panel3 = new javax.swing.JPanel();
        Button3 = new javax.swing.JButton();
        txt_password2 = new javax.swing.JTextField();
        txt_password1 = new javax.swing.JTextField();
        Cancel2 = new javax.swing.JLabel();
        Background2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(71, 38));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel1.setEnabled(false);
        Panel1.setPreferredSize(new java.awt.Dimension(543, 691));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Button1.setForeground(new java.awt.Color(10, 86, 56));
        Button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/Email.png"))); // NOI18N
        Button1.setBorder(null);
        Button1.setBorderPainted(false);
        Button1.setContentAreaFilled(false);
        Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button1ActionPerformed(evt);
            }
        });
        Panel1.add(Button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 380, 50));

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
        Panel1.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 310, 20));

        Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/Cancel_.png"))); // NOI18N
        Cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelMouseClicked(evt);
            }
        });
        Panel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 440, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/1.png"))); // NOI18N
        Panel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(Panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 690));

        Panel2.setEnabled(false);
        Panel2.setPreferredSize(new java.awt.Dimension(543, 691));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        anno.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        anno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anno.setText("-");
        anno.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Panel2.add(anno, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 290, 90));

        jTextField5.setBackground(new java.awt.Color(237, 245, 224));
        jTextField5.setFont(jTextField5.getFont().deriveFont(jTextField5.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField5.setForeground(new java.awt.Color(102, 102, 102));
        jTextField5.setBorder(null);
        jTextField5.setPreferredSize(new java.awt.Dimension(60, 60));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        Panel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 270, 20, 20));

        jTextField6.setBackground(new java.awt.Color(237, 245, 224));
        jTextField6.setFont(jTextField6.getFont().deriveFont(jTextField6.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField6.setForeground(new java.awt.Color(102, 102, 102));
        jTextField6.setBorder(null);
        jTextField6.setPreferredSize(new java.awt.Dimension(60, 60));
        Panel2.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 270, 20, 20));

        jTextField3.setBackground(new java.awt.Color(237, 245, 224));
        jTextField3.setFont(jTextField3.getFont().deriveFont(jTextField3.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField3.setForeground(new java.awt.Color(102, 102, 102));
        jTextField3.setBorder(null);
        jTextField3.setPreferredSize(new java.awt.Dimension(60, 60));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        Panel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 270, 20, 20));

        jTextField4.setBackground(new java.awt.Color(237, 245, 224));
        jTextField4.setFont(jTextField4.getFont().deriveFont(jTextField4.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField4.setForeground(new java.awt.Color(102, 102, 102));
        jTextField4.setBorder(null);
        jTextField4.setPreferredSize(new java.awt.Dimension(60, 60));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        Panel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 270, 20, 20));

        jTextField2.setBackground(new java.awt.Color(237, 245, 224));
        jTextField2.setFont(jTextField2.getFont().deriveFont(jTextField2.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jTextField2.setBorder(null);
        jTextField2.setPreferredSize(new java.awt.Dimension(60, 60));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        Panel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 270, 20, 20));

        jTextField1.setBackground(new java.awt.Color(237, 245, 224));
        jTextField1.setFont(jTextField1.getFont().deriveFont(jTextField1.getFont().getStyle() | java.awt.Font.BOLD, 5));
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setBorder(null);
        jTextField1.setPreferredSize(new java.awt.Dimension(60, 60));
        Panel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 270, 20, 20));

        Reload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/Reload.png"))); // NOI18N
        Reload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReloadMouseClicked(evt);
            }
        });
        Panel2.add(Reload, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 430, -1, -1));

        Cancel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Cancel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Cancel1MouseClicked(evt);
            }
        });
        Panel2.add(Cancel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 510, 60, 20));

        Time.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Time.setForeground(new java.awt.Color(153, 153, 153));
        Time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Time.setText("01:00");
        Panel2.add(Time, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 420, 60, 40));

        Background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/2.png"))); // NOI18N
        Background1.setToolTipText("");
        Panel2.add(Background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 690));

        Panel3.setEnabled(false);
        Panel3.setPreferredSize(new java.awt.Dimension(543, 691));
        Panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Button3.setForeground(new java.awt.Color(10, 86, 56));
        Button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/ResetPass.png"))); // NOI18N
        Button3.setBorder(null);
        Button3.setBorderPainted(false);
        Button3.setContentAreaFilled(false);
        Button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button3ActionPerformed(evt);
            }
        });
        Panel3.add(Button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 400, 50));

        txt_password2.setBackground(new java.awt.Color(237, 245, 224));
        txt_password2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txt_password2.setBorder(null);
        txt_password2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_password2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_password2FocusLost(evt);
            }
        });
        txt_password2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_password2ActionPerformed(evt);
            }
        });
        Panel3.add(txt_password2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 310, 20));

        txt_password1.setBackground(new java.awt.Color(237, 245, 224));
        txt_password1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txt_password1.setBorder(null);
        txt_password1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_password1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_password1FocusLost(evt);
            }
        });
        txt_password1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_password1ActionPerformed(evt);
            }
        });
        Panel3.add(txt_password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 310, 20));

        Cancel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/Cancel_.png"))); // NOI18N
        Cancel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Cancel2MouseClicked(evt);
            }
        });
        Panel3.add(Cancel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, -1, -1));

        Background2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupapassword/3.png"))); // NOI18N
        Panel3.add(Background2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, -1));

        getContentPane().add(Panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed

    }//GEN-LAST:event_txt_emailActionPerformed

    private void Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button1ActionPerformed
    String userEmail = txt_email.getText();    

    // Validate user input
    if (userEmail.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter your email.");
        return;
    }

    try (Connection conn = koneksi.configDB();
         PreparedStatement pst = conn.prepareStatement("SELECT id_user FROM user WHERE email = ?")) {
        pst.setString(1, userEmail);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            Panel2.setVisible(true);
            Panel1.setVisible(false);

            String userId = getUserIdFromDatabase(conn, userEmail);
            if (!userId.isEmpty()) {
                String otp = generateOTP();
                insertOTPInDatabase(otp, userId, conn);
                System.out.println("Generated OTP: " + otp);
                
                sendEmail(userEmail, otp); // Send email with OTP

                String message = "<html><center>Kode verifikasi telah dikirimkan ke<br>" + userEmail + ".<br>Silakan masukkan kode<br>tersebut untuk melanjutkan.</center></html>";
                anno.setText(message);
                anno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // Align text to center
                Dimension size = anno.getPreferredSize(); // Get preferred size based on text content
                anno.setSize(size); // Set size to preferred size
    
            } else {
                System.out.println("No user available to generate OTP for.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email not found in the database");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
}

public static String getUserIdFromDatabase(Connection conn, String userEmail) {
    String sql = "SELECT id_user FROM user WHERE email = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setString(1, userEmail);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("id_user");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "";
}

public static void insertOTPInDatabase(String otp, String userId, Connection conn) throws SQLException {
    String insertSql = "INSERT INTO otp (ID_Kode, kode, id_user) VALUES (?, ?, ?)";
    String idKode = generateidKode(conn); // Generate OTP ID
    try (PreparedStatement preparedStatement = conn.prepareStatement(insertSql)) {
        preparedStatement.setString(1, idKode);
        preparedStatement.setString(2, otp); // Set OTP value
        preparedStatement.setString(3, userId);
        preparedStatement.executeUpdate();
    }
}

public static String generateOTP() {
    int otpLength = 6;
    String numbers = "0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder(otpLength);

    for (int i = 0; i < otpLength; i++) {
        int index = random.nextInt(numbers.length());
        sb.append(numbers.charAt(index));
    }
    return sb.toString();
}

public static void sendEmail(String userEmail, String otp) {
    String myEmail = "visimaladesktop@gmail.com";
    String emailPassword = "cqos cciu knat feyp"; // Consider using more secure methods for handling passwords

    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");

    try {
        String recipientEmail = userEmail;

        // Read HTML content from file
        String htmlBody = readHtmlFile("index.html");

        // Replace placeholder in HTML content with OTP
        htmlBody = htmlBody.replace("<div class=\"otp\"></div>", "<div class=\"otp\">" + otp + "</div>");

        // Sending the email
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, emailPassword);
            }
        });

        // Construct the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Reset Password");
        message.setContent(htmlBody, "text/html");

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully!");
    } catch (MessagingException | IOException e) {
        e.printStackTrace();
    }
}
private static String readHtmlFile(String fileName) throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    try (InputStream inputStream = LupaPassword.class.getResourceAsStream(fileName);
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line).append("\n");
        }
    }
    return contentBuilder.toString();
    }//GEN-LAST:event_Button1ActionPerformed

    private void Button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button3ActionPerformed
    Panel3.setVisible(false);
    
    // Close the current window
    dispose();
    
    // Retrieve passwords from text fields
    String password1 = txt_password1.getText();
    String password2 = txt_password2.getText();
    String email = txt_email.getText(); // Retrieve email from txt_email
    
    // Check if passwords match
    if (password1.equals(password2)) {
        // Check if password length is at least 6 characters
        if (password1.length() >= 6) {
            // Passwords match and length is sufficient, update the database
            if (!email.isEmpty()) {
                updatePasswordInDatabase(password1, email); // Call the method with password1 and email
                System.out.println("Password updated for email: " + email);
            } else {
                // Handle the scenario where email is empty
                JOptionPane.showMessageDialog(this, "Email field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Password length is less than 6 characters, show an error message
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Passwords don't match, handle this scenario (e.g., show an error message)
        JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void updatePasswordInDatabase(String newPassword, String email) {
    // Implement database update logic here
    // You would typically use JDBC or an ORM framework like Hibernate to interact with the database
    
    // Example using JDBC (replace with your actual database update logic)
    try {
        // Establish database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/project_laundryku", "root", "");

        // Prepare SQL statement
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        // Set parameters
        statement.setString(1, newPassword);
        statement.setString(2, email);
        
        // Execute the update
        int rowsUpdated = statement.executeUpdate();
        
        // Check if the update was successful
        if (rowsUpdated > 0) {
            System.out.println("Password updated successfully for email: " + email);
        } else {
            System.out.println("Failed to update password for email: " + email);
        }
        
        // Close resources
        statement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database errors
    }

        Login frameLogin = new Login();
        frameLogin.setVisible(true);
    }//GEN-LAST:event_Button3ActionPerformed

    private void txt_password2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password2FocusGained
        if(txt_password2.getText().equals("Password Baru")) {
            txt_password2.setText("");
            txt_password2.setForeground(new Color(0, 0, 0));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password2FocusGained

    private void txt_password2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password2FocusLost
        if(txt_password2.getText().isEmpty()) {
            txt_password2.setText("Password Baru");
            txt_password2.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txt_password2FocusLost

    private void txt_password2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_password2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password2ActionPerformed

    private void txt_password1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password1FocusGained
        if(txt_password1.getText().equals("Konfirmasi Password")) {
            txt_password1.setText("");
            txt_password1.setForeground(new Color(0, 0, 0));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password1FocusGained

    private void txt_password1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_password1FocusLost
        if(txt_password1.getText().isEmpty()) {
            txt_password1.setText("Konfirmasi Password");
            txt_password1.setForeground(new Color(153, 153, 153));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password1FocusLost

    private void txt_password1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_password1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password1ActionPerformed

    private void CancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelMouseClicked
        Panel1.setVisible(false);
        dispose();
        Login frameLogin = new Login();
        frameLogin.setVisible(true);
    }//GEN-LAST:event_CancelMouseClicked

    private void Cancel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Cancel1MouseClicked
        Panel2.setVisible(false);
        dispose();
        Login frameLogin = new Login();
        frameLogin.setVisible(true);
    }//GEN-LAST:event_Cancel1MouseClicked

    private void Cancel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Cancel2MouseClicked
        Panel3.setVisible(false);
        dispose();
        Login frameLogin = new Login();
        frameLogin.setVisible(true);
    }//GEN-LAST:event_Cancel2MouseClicked

    private void ReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReloadMouseClicked
    Time.setVisible(true);
    startCountdownTimer();
    Reload.setVisible(false);

    String newOTP = generateOTP();
    String userEmail = txt_email.getText();

    try (Connection conn = koneksi.configDB()) {
        conn.setAutoCommit(false);

        // Delete existing OTP records
        String deleteQuery = "DELETE FROM otp";
        try (PreparedStatement pstDelete = conn.prepareStatement(deleteQuery)) {
            pstDelete.executeUpdate();
        }

        // Insert the new OTP into the database
        String insertQuery = "INSERT INTO otp (ID_Kode, kode, id_user) VALUES (?, ?, ?)";
        String idKode = generateidKode(conn); // Generate OTP ID
        try (PreparedStatement pstInsert = conn.prepareStatement(insertQuery)) {
            pstInsert.setString(1, idKode);
            pstInsert.setString(2, newOTP);
            pstInsert.setString(3, getUserIdFromDatabase(conn, userEmail));
            pstInsert.executeUpdate();
        }

        conn.commit();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        return;
    }

    // Send email with the new OTP
    sendEmail(userEmail, newOTP);

    try {
        // Read HTML file (if needed)
        String htmlContent = readHtmlFile("index.html");
        // Use htmlContent as needed
    } catch (IOException ex) {
        Logger.getLogger(LupaPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_ReloadMouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LupaPassword().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Background1;
    private javax.swing.JLabel Background2;
    private javax.swing.JButton Button1;
    private javax.swing.JButton Button3;
    private javax.swing.JLabel Cancel;
    private javax.swing.JLabel Cancel1;
    private javax.swing.JLabel Cancel2;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel3;
    private javax.swing.JLabel Reload;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel anno;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_password1;
    private javax.swing.JTextField txt_password2;
    // End of variables declaration//GEN-END:variables
}