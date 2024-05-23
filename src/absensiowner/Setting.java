package absensiowner;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import koneksi.koneksi;

public class Setting extends javax.swing.JFrame {
    private String startDate;
    private String endDate;
    private LocalTime selectedTime;
    private ComboBox<String> hours;
    private ComboBox<String> minutes;
    private JFXPanel jfxPanel;

    public Setting() {
        initComponents();
        initUI();
    }

    private void initUI() {
        // Initialize JFXPanel and add to combopanel
        jfxPanel = new JFXPanel();
        jfxPanel.setBackground(new java.awt.Color(255, 255, 255));
        combopanel.setLayout(new BorderLayout());
        combopanel.add(jfxPanel, BorderLayout.CENTER);

        // JavaFX components need to be initialized on the JavaFX Application Thread
        SwingUtilities.invokeLater(() -> Platform.runLater(this::settingJavaFXPanel));
    }

private void settingJavaFXPanel() {
    // Create ComboBoxes for hours and minutes
    hours = new ComboBox<>();
    minutes = new ComboBox<>();

    for (int i = 0; i < 24; i++) {
        hours.getItems().add(String.format("%02d", i));
    }

    for (int i = 0; i < 60; i++) {
        minutes.getItems().add(String.format("%02d", i));
    }

    // Retrieve default time from the database
    String defaultTime = getDefaultTimeFromDatabase();

    // Set default time to ComboBoxes
    if (defaultTime != null) {
        String[] timeParts = defaultTime.split(":");
        hours.setValue(timeParts[0]);
        minutes.setValue(timeParts[1]);
    } else {
        // If default time is null, set default to 08:00
        hours.setValue("08");
        minutes.setValue("00");
    }

    // Set the font for the ComboBoxes
    hours.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
    minutes.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");

    // Set the size for the ComboBoxes
    hours.setPrefSize(80, 30);
    minutes.setPrefSize(80, 30);

    // Create labels for hours and minutes
    javafx.scene.control.Label hoursLabel = new javafx.scene.control.Label("Jam:");
    javafx.scene.control.Label minutesLabel = new javafx.scene.control.Label("Menit:");

    // Set the font for the labels
    hoursLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 12));
    minutesLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 12));

    HBox hBox = new HBox(5, hoursLabel, hours, minutesLabel, minutes);
    hBox.setAlignment(Pos.CENTER);
    Scene scene = new Scene(hBox, 320, 80);

    // Set the background color of the HBox to white
    hBox.setStyle("-fx-background-color: #FFFFFF;");

    jfxPanel.setScene(scene);

    // Listener to get selected time
    hours.setOnAction(event -> updateSelectedTime());
    minutes.setOnAction(event -> updateSelectedTime());
}

    private String getDefaultTimeFromDatabase() {
        String defaultTime = null;
        try {
            String sql = "SELECT waktuAbsensi FROM setting_absensi WHERE setting_id = ?";
            try (Connection conn = koneksi.configDB();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1); // Assuming setting_id for default time is 1
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        defaultTime = rs.getString("waktuAbsensi");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultTime;
    }

    private void updateSelectedTime() {
        int hour = Integer.parseInt(hours.getValue());
        int minute = Integer.parseInt(minutes.getValue());
        selectedTime = LocalTime.of(hour, minute);

        // For demonstration, print the selected time to the console
        System.out.println("Selected Time: " + selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void saveSelectedTimeToDatabase() {
        String selectedTimeString = hours.getValue() + ":" + minutes.getValue();
        try {
            Connection conn = koneksi.configDB();
            String checkSql = "SELECT COUNT(*) FROM setting_absensi WHERE setting_id = ?";
            String insertSql = "INSERT INTO setting_absensi (setting_id, waktuAbsensi) VALUES (?, ?)";
            String updateSql = "UPDATE setting_absensi SET waktuAbsensi = ? WHERE setting_id = ?";

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, 1);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Update existing record
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, selectedTimeString);
                            updateStmt.setInt(2, 1);
                            updateStmt.executeUpdate();
                            System.out.println("Selected time updated in database: " + selectedTimeString);
                        }
                    } else {
                        // Insert new record
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, 1);
                            insertStmt.setString(2, selectedTimeString);
                            insertStmt.executeUpdate();
                            System.out.println("Selected time inserted into database: " + selectedTimeString);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        confirm = new javax.swing.JButton();
        combopanel = new javax.swing.JPanel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        confirm.setBorder(null);
        confirm.setContentAreaFilled(false);
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        getContentPane().add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 100, 20));

        combopanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout combopanelLayout = new javax.swing.GroupLayout(combopanel);
        combopanel.setLayout(combopanelLayout);
        combopanelLayout.setHorizontalGroup(
            combopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        combopanelLayout.setVerticalGroup(
            combopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(combopanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 320, -1));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensiowner/bgsetting.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
      saveSelectedTimeToDatabase();
    }//GEN-LAST:event_confirmActionPerformed
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Setting().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JPanel combopanel;
    private javax.swing.JButton confirm;
    // End of variables declaration//GEN-END:variables
}