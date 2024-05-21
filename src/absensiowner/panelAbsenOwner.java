
package absensiowner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import mainOwner.MainOwner;

public class panelAbsenOwner extends javax.swing.JPanel {

    private Timer timer;
    private Timer t;
//    private JButton next1;
//    private JButton prev2;
//    private JButton next2;
//    private JButton prev1;
    private int currentPage1 = 1; // Current page for time in data
    private int currentPage2 = 1;
    private final int rowsPerPage = 3; // Assuming 3 rows per page
    private int totalEntries;
    private JLabel totalEntriesLabel;
    
    
    private MainOwner mainAbsensi; // Referensi ke objek Main
    
    public panelAbsenOwner() {
          this.mainAbsensi = mainAbsensi            ; // Inisialisasi mainInstance dengan objek Main yang diterima
        
        initComponents();
    countAttendanceStatus();
    tabel(); // Initialize table appearance
    dt();
    times();
    setupTimer();
    JumlahPegawai();
    loadTimeInData();
    loadTimeOutData();
    JButton next = new JButton("Next");
    JButton prev = new JButton("Previous");
    setPaginationLabel1();
    setPaginationLabel2();
    next1.addActionListener(e -> next1ActionPerformed(e));
    next2.addActionListener(e -> next2ActionPerformed(e));
    prev1.addActionListener(e -> prev1ActionPerformed(e));
    prev2.addActionListener(e -> prev2ActionPerformed(e));
    this.totalEntriesLabel = totalEntriesLabel; // Assign the JLabel
}

private void tabel() {
    timein.setShowGrid(true);
    timein.setShowHorizontalLines(true);
    timein.setShowVerticalLines(true);

    timeout.setShowGrid(true);
    timeout.setShowHorizontalLines(true);
    timeout.setShowVerticalLines(true);
}

private void loadTimeInData() {
    DefaultTableModel timeInModel = new DefaultTableModel();
    timeInModel.addColumn("ID");
    timeInModel.addColumn("Jam Masuk");
    timeInModel.addColumn("Nama Pegawai");
    timeInModel.addColumn("Status");

    try {
        int offset = (currentPage1 - 1) * rowsPerPage;
        String dataSql = "SELECT absensi.id_absensi, absensi.time_in, user.Username, absensi.status_absensi " +
                "FROM absensi " +
                "JOIN user ON absensi.id_user = user.id_user LIMIT ?, ?";
        String countSql = "SELECT COUNT(*) AS totalRows FROM absensi"; // SQL to get total row count
        try (Connection conn = koneksi.configDB();
             PreparedStatement dataStmt = conn.prepareStatement(dataSql);
             PreparedStatement countStmt = conn.prepareStatement(countSql)) {

            // Execute query to get total row count
            try (ResultSet countResult = countStmt.executeQuery()) {
                if (countResult.next()) {
                    totalEntries = countResult.getInt("totalRows");
                }
            }

            dataStmt.setInt(1, offset);
            dataStmt.setInt(2, rowsPerPage);
            try (ResultSet res = dataStmt.executeQuery()) {
                while (res.next()) {
                    String idAbsensi = res.getString("id_absensi");
                    String waktuAbsensi = res.getString("time_in");
                    String namaPegawai = res.getString("Username");
                    String statusAbsensi = res.getString("status_absensi");

                    // Extracting time in "HH:mm" format from datetime string
                    String timeIn = waktuAbsensi.substring(11, 16);

                    timeInModel.addRow(new Object[]{idAbsensi, timeIn, namaPegawai, statusAbsensi});
                }
            }
        }
        timein.setModel(timeInModel);
        setTableAlignment(timein);

        // Update total entries label
        updateTotalEntriesLabel1(offset, timeInModel.getRowCount());
    } catch (Exception e) {
        handleException("Error loading time in data", e);
    }
}

private void loadTimeOutData() {
    DefaultTableModel timeOutModel = new DefaultTableModel();
    timeOutModel.addColumn("ID");
    timeOutModel.addColumn("Jam Keluar");
    timeOutModel.addColumn("Nama Pegawai");
    timeOutModel.addColumn("Status"); // Add Status column

    try {
        int offset = (currentPage2 - 1) * rowsPerPage;
        String dataSql = "SELECT absensi.id_absensi, absensi.time_out, user.Username, absensi.status_absensi " +
                "FROM absensi " +
                "JOIN user ON absensi.id_user = user.id_user LIMIT ?, ?";
        String countSql = "SELECT COUNT(*) AS totalRows FROM absensi"; // SQL to get total row count
        try (Connection conn = koneksi.configDB();
             PreparedStatement dataStmt = conn.prepareStatement(dataSql);
             PreparedStatement countStmt = conn.prepareStatement(countSql)) {

            // Execute query to get total row count
            try (ResultSet countResult = countStmt.executeQuery()) {
                if (countResult.next()) {
                    totalEntries = countResult.getInt("totalRows");
                }
            }

            dataStmt.setInt(1, offset);
            dataStmt.setInt(2, rowsPerPage);
            try (ResultSet res = dataStmt.executeQuery()) {

                while (res.next()) {
                    String idAbsensi = res.getString("id_absensi");
                    String waktuAbsensi = res.getString("time_out");
                    String namaPegawai = res.getString("Username");
                    String status;

                    // Check if waktuAbsensi is not empty before extracting substring
                    if (!waktuAbsensi.isEmpty() && waktuAbsensi.length() >= 16) {
                        status = "Sesi Berakhir";
                        // Extracting time out "HH:mm" format from datetime string
                        String timeOut = waktuAbsensi.substring(11, 16);
                        timeOutModel.addRow(new Object[]{idAbsensi, timeOut, namaPegawai, status});
                    } else {
                        status = "Masih Aktif";
                        timeOutModel.addRow(new Object[]{idAbsensi, "", namaPegawai, status});
                    }
                }
            }
        }
        timeout.setModel(timeOutModel);
        setTableAlignment(timeout);
        
        // Update total entries label
        updateTotalEntriesLabel2(offset, timeOutModel.getRowCount());
    } catch (Exception e) {
        handleException("Error loading time out data", e);
    }
}

private void setTableAlignment(JTable table) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}

private void updateTotalEntriesLabel1(int startRow, int endRow) {
    // Calculate the actual start row and end row based on the current page and rows per page
    startRow = Math.min(Math.max((currentPage1 - 1) * rowsPerPage + 1, 1), totalEntries);
    endRow = Math.min(startRow + rowsPerPage - 1, totalEntries);

    total1.setText("Showing " + startRow + " to " + endRow + " of " + totalEntries + " entries");
}

private void updateTotalEntriesLabel2(int startRow, int endRow) {
    // Calculate the actual start row and end row based on the current page and rows per page
    startRow = Math.min(Math.max((currentPage2 - 1) * rowsPerPage + 1, 1), totalEntries);
    endRow = Math.min(startRow + rowsPerPage - 1, totalEntries);

    total2.setText("Showing " + startRow + " to " + endRow + " of " + totalEntries + " entries");
}
       private void setPaginationLabel1() {
        num1.setText(String.valueOf(currentPage1));
    }
        private void setPaginationLabel2() {
        num2.setText(String.valueOf(currentPage2));
    }
    private int getMaxPage() {
    return (int) Math.ceil((double) totalEntries / rowsPerPage);
}
    
    private boolean isTimeLaterThan(String time, String targetTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date timeDate = sdf.parse(time);
            Date targetTimeDate = sdf.parse(targetTime);

            return timeDate.compareTo(targetTimeDate) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
            private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        timer.start();
    }

    private void updateClock() {
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        String formattedTime = timeFormat.format(currentDate);
        lbl_time.setText(formattedTime);
    }

    private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
        // Optionally, display a dialog or log the exception to a file.
    }

    public void dt() {
        Date currentDate = new Date();
        Locale indonesianLocale = new Locale("id", "ID");

        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE dd MMMM", indonesianLocale);
        String formattedDate = sdfDate.format(currentDate);
        lbl_date.setText(formattedDate);

        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        String formattedYear = sdfYear.format(currentDate);
        lbl_year.setText(formattedYear);
    }

    public void times() {
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dt = new Date();
                SimpleDateFormat st = new SimpleDateFormat("hh:mm:ss a");
                String tt = st.format(dt);
                lbl_time.setText(tt);
            }
        });
        t.start();
    }
    
private void cariData1(String kataKunci) {
    DefaultTableModel tbl = (DefaultTableModel) timein.getModel();
    tbl.setRowCount(0); // Clear existing rows

    try {
        String query = "SELECT " +
                "    absensi.id_absensi, " +
                "    absensi.time_in, " +
                "    user.Username, " +  // Corrected column name
                "    absensi.status_absensi " +
                "FROM " +
                "    absensi " +
                "JOIN user ON absensi.id_user = user.id_user " + // Join with user table
                "WHERE " +
                "    (absensi.id_absensi LIKE ? OR " +
                "    absensi.time_in LIKE ? OR " +
                "    user.Username LIKE ? OR " +  // Corrected table alias and column name
                "    absensi.status_absensi LIKE ?) AND " +
                "    absensi.status_absensi = 'Terlambat'";

        java.sql.Connection conn = (Connection) koneksi.configDB();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }

            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                String statusText = res.getString("absensi.status_absensi");

                tbl.addRow(new Object[]{
                        res.getString("absensi.id_absensi"),
                        res.getString("absensi.time_in"),
                        res.getString("user.Username"), // Corrected column name
                        statusText
                });
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
        private void search1() {
        String searchText1 = cari1.getText().trim(); // Get the text from the JTextField
        cariData1(searchText1); // Call the search method with the text
    }

private void cariData2(String kataKunci) {
    DefaultTableModel tbl = (DefaultTableModel) timein.getModel();
    tbl.setRowCount(0); // Bersihkan baris yang sudah ada

    try {
        // Gunakan prepared statement untuk menghindari SQL injection
        String query = "SELECT \n" +
                "    absensi.id_absensi, \n" +
                "    absensi.waktu_absensi, \n" +
                "    absensi.Username, \n" +
                "    absensi.status_absensi \n" +
                "FROM \n" +
                "    absensi \n" +
                "WHERE \n" +
                "    (absensi.id_absensi LIKE ? OR " +
                "    absensi.waktu_absensi LIKE ? OR " +
                "    absensi.Username LIKE ? OR " +
                "    absensi.status_absensi LIKE ?) AND " +
                "    absensi.status_absensi = 'Tepat Waktu'";
        
        java.sql.Connection conn = (Connection) koneksi.configDB();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }

            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                String statusText = res.getString("absensi.status_absensi");

                tbl.addRow(new Object[]{
                        res.getString("absensi.id_absensi"),
                        res.getString("absensi.yime_out"),
                        res.getString("absensi.id_user"),
                        statusText
                });
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}

private String JumlahPegawai() { 
    try {
        Connection conn = koneksi.configDB();
        String sql = "SELECT COUNT(*) as jumlahPegawai FROM user WHERE jabatan = 'Pegawai'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int jumlahPegawai = rs.getInt("jumlahPegawai");
                lbl_jumlahuser.setText(String.valueOf(jumlahPegawai));
                System.out.println("jumlahPegawai: " + jumlahPegawai);
            }
        }
    } catch (Exception e) {
        handleException("Error retrieving employee count from the database", e);
    }
    return null;
}

private void countAttendanceStatus() {
    int tepatWaktuCount = 0;
    int terlambatCount = 0;

    try {
        String sql = "SELECT status_absensi FROM absensi";
        try (Connection conn = koneksi.configDB();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet res = pstmt.executeQuery()) {

            while (res.next()) {
                String statusAbsensi = res.getString("status_absensi");

                if (statusAbsensi.equalsIgnoreCase("Tepat Waktu")) {
                    tepatWaktuCount++;
                } else if (statusAbsensi.equalsIgnoreCase("Terlambat")) {
                    terlambatCount++;
                }
            }
        }

        // Set the text of "HadirJ" and "TerlambatJ"
        HadirJ.setText(String.valueOf(tepatWaktuCount));
        TerlambatJ.setText(String.valueOf(terlambatCount));

    } catch (Exception e) {
        handleException("Error counting attendance status", e);
    }
}
//        private void deleteRowFromDatabase(String idAbsensi) {
//        try {
//            Connection conn = koneksi.configDB();
//            String sql = "DELETE FROM absensi WHERE id_absensi = ?";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, idAbsensi);
//                pstmt.executeUpdate();
//            }
//
//            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
//        } catch (Exception e) {
//            handleException("Data Gagal dihapus", e);
//        }
//        }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        next2 = new javax.swing.JButton();
        table1 = new javax.swing.JScrollPane();
        timein = new javax.swing.JTable();
        TerlambatJ = new javax.swing.JLabel();
        table2 = new javax.swing.JScrollPane();
        timeout = new javax.swing.JTable();
        detailwaktu = new javax.swing.JButton();
        detailpegawai = new javax.swing.JButton();
        num2 = new javax.swing.JLabel();
        HadirJ = new javax.swing.JLabel();
        downloadpdf = new javax.swing.JButton();
        prev2 = new javax.swing.JButton();
        next1 = new javax.swing.JButton();
        lbl_jumlahuser = new javax.swing.JLabel();
        lbl_year = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        prev1 = new javax.swing.JButton();
        cari1 = new javax.swing.JTextField();
        cari2 = new javax.swing.JTextField();
        num1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));
        setPreferredSize(new java.awt.Dimension(996, 595));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        next2.setBorder(null);
        next2.setBorderPainted(false);
        next2.setContentAreaFilled(false);
        next2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next2ActionPerformed(evt);
            }
        });
        add(next2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 510, 30, 20));

        timein.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        timein.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Jam Masuk", "Nama Pegawai", "Status"
            }
        ));
        timein.setRowHeight(30);
        table1.setViewportView(timein);

        add(table1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 410, 120));

        TerlambatJ.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        TerlambatJ.setForeground(new java.awt.Color(255, 255, 255));
        TerlambatJ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TerlambatJ.setText("-");
        add(TerlambatJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 190, 40));

        timeout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        timeout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Jam Masuk", "Nama Pegawai", "Status"
            }
        ));
        timeout.setEditingColumn(0);
        timeout.setEditingRow(0);
        timeout.setRowHeight(30);
        table2.setViewportView(timeout);

        add(table2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 410, 120));

        detailwaktu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        detailwaktu.setContentAreaFilled(false);
        add(detailwaktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 80, 30));

        detailpegawai.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        detailpegawai.setContentAreaFilled(false);
        detailpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailpegawaiMouseClicked(evt);
            }
        });
        detailpegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailpegawaiActionPerformed(evt);
            }
        });
        add(detailpegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 80, 30));

        num2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        num2.setForeground(new java.awt.Color(255, 255, 255));
        num2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        num2.setText("1");
        num2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(num2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 513, 30, 20));

        HadirJ.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        HadirJ.setForeground(new java.awt.Color(255, 255, 255));
        HadirJ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HadirJ.setText("-");
        add(HadirJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 190, 40));

        downloadpdf.setBorderPainted(false);
        downloadpdf.setContentAreaFilled(false);
        add(downloadpdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 120, 30));

        prev2.setBorder(null);
        prev2.setBorderPainted(false);
        prev2.setContentAreaFilled(false);
        prev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev2ActionPerformed(evt);
            }
        });
        add(prev2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 510, 30, 20));

        next1.setBorder(null);
        next1.setBorderPainted(false);
        next1.setContentAreaFilled(false);
        next1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next1ActionPerformed(evt);
            }
        });
        add(next1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 510, 30, 20));

        lbl_jumlahuser.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_jumlahuser.setForeground(new java.awt.Color(255, 255, 255));
        lbl_jumlahuser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_jumlahuser.setText("-");
        add(lbl_jumlahuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 200, 40));

        lbl_year.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_year.setForeground(new java.awt.Color(255, 255, 255));
        lbl_year.setText("-");
        add(lbl_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, 110, -1));

        total1.setText("-");
        add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, 300, -1));

        lbl_date.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setText("-");
        add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 150, -1));

        lbl_time.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_time.setText("-");
        add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, 170, -1));

        total2.setText("-");
        add(total2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 290, -1));

        prev1.setBorder(null);
        prev1.setBorderPainted(false);
        prev1.setContentAreaFilled(false);
        prev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev1ActionPerformed(evt);
            }
        });
        add(prev1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 30, 20));

        cari1.setBorder(null);
        add(cari1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 90, 10));

        cari2.setBorder(null);
        cari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cari2ActionPerformed(evt);
            }
        });
        add(cari2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 350, 80, 10));

        num1.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        num1.setForeground(new java.awt.Color(255, 255, 255));
        num1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        num1.setText("1");
        num1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(num1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 510, 30, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensiowner/desainAbsensiOwner.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void next2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next2ActionPerformed
        if (currentPage2 < getMaxPage()) {
            currentPage2++;
            loadTimeOutData();
            setPaginationLabel2();
        }
    }//GEN-LAST:event_next2ActionPerformed

    private void detailpegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailpegawaiActionPerformed
          MainOwner mainAbsensi = (MainOwner) SwingUtilities.getWindowAncestor(this);
        mainAbsensi.showFormOwner(new usser.tabelUser(mainAbsensi));

    }//GEN-LAST:event_detailpegawaiActionPerformed

    private void prev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev2ActionPerformed
        if (currentPage2 > 1) {
            currentPage2--;
            loadTimeOutData();
            setPaginationLabel2();
        }
    }//GEN-LAST:event_prev2ActionPerformed

    private void next1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next1ActionPerformed
        if (currentPage1 < getMaxPage()) {
            currentPage1++;
            loadTimeInData();
            setPaginationLabel1();
        }
    }//GEN-LAST:event_next1ActionPerformed

    private void prev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev1ActionPerformed
        if (currentPage1 > 1) {
            currentPage1--;
            loadTimeInData();
            setPaginationLabel1();
        }
    }//GEN-LAST:event_prev1ActionPerformed

    private void cari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cari2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cari2ActionPerformed

    private void detailpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailpegawaiMouseClicked

    }//GEN-LAST:event_detailpegawaiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HadirJ;
    private javax.swing.JLabel TerlambatJ;
    private javax.swing.JTextField cari1;
    private javax.swing.JTextField cari2;
    private javax.swing.JButton detailpegawai;
    private javax.swing.JButton detailwaktu;
    private javax.swing.JButton downloadpdf;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_jumlahuser;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_year;
    private javax.swing.JButton next1;
    private javax.swing.JButton next2;
    private javax.swing.JLabel num1;
    private javax.swing.JLabel num2;
    private javax.swing.JButton prev1;
    private javax.swing.JButton prev2;
    private javax.swing.JScrollPane table1;
    private javax.swing.JScrollPane table2;
    private javax.swing.JTable timein;
    private javax.swing.JTable timeout;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    // End of variables declaration//GEN-END:variables
}
