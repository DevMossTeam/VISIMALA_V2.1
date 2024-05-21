package usser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import koneksi.koneksi;
import notifikasi.notifSukses;
import raven.glasspanepopup.GlassPanePopup;
import static usser.editUser.addRFID;
import static usser.editUser.deleteRFID;
import static usser.editUser.labelRFID;

public class popupRFID extends javax.swing.JPanel {

public popupRFID(String iduser) {

    initComponents();
    setOpaque(false);

    id_user.setText(iduser); // Menetapkan nilai iduser ke JTextField id_user

    SwingUtilities.invokeLater(() -> {
        RFIDfield.requestFocusInWindow();
    });

    RFIDfield.addActionListener(this::RFIDfieldActionPerformed); // Attach action listener
}


    public javax.swing.JTextField getRFIDfield() {
        return RFIDfield;
    }

private String generatekodeRFID(Connection conn) throws SQLException {
    String prefix = "kodeRFID";
    int number = 1;
    String RFID = "";

    boolean idFound = false;
    while (!idFound) {
        String userIdToCheck = prefix + number;
        String query = "SELECT COUNT(*) FROM rfid WHERE kode_RFID = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(query)) {
            checkStmt.setString(1, userIdToCheck);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count == 0) {
                        RFID = userIdToCheck;
                        idFound = true;
                    } else {
                        number++;
                    }
                }
            }
        }
    }
    return RFID;
}

 public void addRFIDTag(String idUser, String RFID) {
        if (!isRFIDExistsInDatabase(RFID)) {
            // Jika RFID belum ada di database, lakukan proses insert
            String sqlInsert = "INSERT INTO rfid (kode_RFID, RFID_Tag, id_user) VALUES (?, ?, ?)";

            try (Connection conn = koneksi.configDB();
                 PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

                String kodeRFID = generatekodeRFID(conn);

                pstmtInsert.setString(1, kodeRFID);
                pstmtInsert.setString(2, RFID);
                pstmtInsert.setString(3, idUser);
                int rowsInserted = pstmtInsert.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("RFID tag berhasil ditambahkan!");
                    labelRFID.setText(RFID);
                    labelRFID.setVisible(true);
                    addRFID.setVisible(false);
                    deleteRFID.setVisible(true);
                    this.setVisible(false); // Tutup popupRFID langsung
                    GlassPanePopup.showPopup(new notifSukses("RFID tag berhasil ditambahkan"));
                    int durasiInMillis = 2000;
                    Timer timer = new Timer(durasiInMillis, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GlassPanePopup.closePopupAll(); 
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            } catch (SQLException e) {
                System.err.println("Error " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("RFID tag sudah ada di database!");
        }
    }

private boolean isRFIDExistsInDatabase(String RFID) {
    String sql = "SELECT COUNT(*) FROM rfid WHERE RFID_Tag = ?";
    try (Connection conn = koneksi.configDB(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, RFID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                
                return count > 0;
            }
        }
    } catch (SQLException e) {
        System.err.println("Error checking RFID existence in the database: " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        RFIDfield = new javax.swing.JTextField();
        id_user = new javax.swing.JTextField();

        setBackground(new java.awt.Color(231, 231, 231));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 40, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainRFID.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        RFIDfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RFIDfieldActionPerformed(evt);
            }
        });
        add(RFIDfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 0));

        id_user.setText("jTextField1");
        add(id_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        GlassPanePopup.closePopupAll();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void RFIDfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RFIDfieldActionPerformed

  String idUser = id_user.getText();
  String RFID = RFIDfield.getText();
  addRFIDTag(idUser, RFID);
    }//GEN-LAST:event_RFIDfieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField RFIDfield;
    private javax.swing.JTextField id_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
