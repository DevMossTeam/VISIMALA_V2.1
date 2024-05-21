
package dashboardKaryawan;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.time.LocalDate;
import javafx.scene.layout.Region;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Group;
/*import javafx.scene.paint.Color;*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.util.StringConverter;

import koneksi.koneksi;

public class dashboard extends javax.swing.JPanel {
        private int currentPage1 = 1; // Current page for time in data
    private int currentPage2 = 1;
    private final int rowsPerPage = 3; // Assuming 3 rows per page
    private Timer timer;
    private Timer t;

    public dashboard() {
        initComponents();
                setTimeFromDatabase();
        dt();
        times();
        setupTimer();
         tabel();
         loadHistoryTransaksi();
         loadHistoryLaundry();
         loadLaundrySelesai();
         countTransaksiRows();
         showTotalDeposit();
         SwingUtilities.invokeLater(this::setupJavaFXPanel);
         this.panelChart = panelChart;
         setupJavaFXPanel();
    }
    
    private void setupJavaFXPanel() {
    // Create a JFXPanel
    JFXPanel jfxPanel = new JFXPanel();

    // Set preferred size of jfxPanel
    jfxPanel.setPreferredSize(new Dimension(340, 220));

    // Set background color of jfxPanel
    jfxPanel.setBackground(new Color(229, 140, 140));

    // Add JFXPanel to your Swing layout
    panelChart.add(jfxPanel); // Assuming panelChart is a JPanel in your Swing layout

    // Run JavaFX operations on JavaFX Application Thread
    Platform.runLater(() -> {
        // Create JavaFX content
        NumberAxis xAxis = new NumberAxis(1, 7, 1); // Setting integer range from 1 to 7
        xAxis.setStyle("-fx-tick-label-fill: rgb(229, 140, 140);");
        xAxis.setTickUnit(1); // Set tick unit to 1 to display integer values

        NumberAxis yAxis = new NumberAxis();
        yAxis.setStyle("-fx-tick-label-fill: rgb(229, 140, 140);");

        // Customize x-axis tick labels to represent days of the week
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                int dayIndex = object.intValue() - 1; // Adjusting day index to start from 0
                if (dayIndex >= 0 && dayIndex < daysOfWeek.length) {
                    return daysOfWeek[dayIndex];
                }
                return "";
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        // Establish database connection and execute query
        try (Connection conn = koneksi.configDB();
             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(kode_transaksi), DAYOFWEEK(tgl_transaksi) FROM transaksi WHERE WEEK(tgl_transaksi) = WEEK(NOW()) GROUP BY DAYOFWEEK(tgl_transaksi)")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dayOfWeek = resultSet.getInt(2);
                int count = resultSet.getInt(1);
                series.getData().add(new XYChart.Data<>(dayOfWeek, count)); // Using whole number for day of week
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle database errors properly in your application
        }

        lineChart.getData().add(series);

        // Set chart background and plot area to the desired color
        Color backgroundColor = new Color(229, 140, 140);
        String rgbColor = String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
        lineChart.setStyle("-fx-background-color: " + rgbColor + ";");
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: " + rgbColor + ";");

        // Set the color of the line to white
        String whiteColor = "-fx-stroke: #FFFFFF;";
        series.getNode().setStyle(whiteColor);

        // Set grid line color
        String gridColor = "-fx-tick-mark-stroke: rgb(140, 86, 86);";
        xAxis.setStyle(gridColor);
        yAxis.setStyle(gridColor);

        // Remove horizontal grid lines
        lineChart.setHorizontalGridLinesVisible(false);

        // Adjust chart layout to fit within the JFXPanel
        lineChart.setPrefSize(340, 220);
        lineChart.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // Remove the legend
        lineChart.setLegendVisible(false);

        // Create a JavaFX scene and add the line chart
        Scene scene = new Scene(new Group(lineChart), 340, 220);

        // Set the scene in the JFXPanel
        jfxPanel.setScene(scene);
    });
}

    
    private void tabel() {
    HistoryTransaksi.setShowGrid(true);
    HistoryTransaksi.setShowHorizontalLines(true);
    HistoryTransaksi.setShowVerticalLines(true);

    LaundrySelesai.setShowGrid(true);
    LaundrySelesai.setShowHorizontalLines(true);
    LaundrySelesai.setShowVerticalLines(true);
    
    HistoryLaundry.setShowGrid(true);
    HistoryLaundry.setShowHorizontalLines(true);
    HistoryLaundry.setShowVerticalLines(true);
}
private void loadHistoryTransaksi() {
    DefaultTableModel timeInModel = new DefaultTableModel();
    timeInModel.addColumn("ID");
    timeInModel.addColumn("Nama");
    timeInModel.addColumn("Nominal");
    timeInModel.addColumn("Status");

    try {
        String dataSql = "SELECT transaksi.kode_transaksi, pelanggan.nama, transaksi.totalPembayaran, transaksi.status_pembayaran " +
                "FROM transaksi " +
                "JOIN pelanggan ON transaksi.kode_pelanggan = pelanggan.kode_pelanggan " +
                "ORDER BY transaksi.kode_transaksi DESC LIMIT 3"; // Assuming id_transaksi is an auto-incremented primary key
        try (Connection conn = koneksi.configDB();
             PreparedStatement dataStmt = conn.prepareStatement(dataSql);
             ResultSet res = dataStmt.executeQuery()) {

            while (res.next()) {
                String noTransaksi = res.getString("kode_transaksi");
                String namaPelanggan = res.getString("nama");
                double nominal = res.getDouble("totalPembayaran") / 1000.0; // Convert to k
                String status = res.getString("status_pembayaran");

                // Format nominal to "1k" format
                String formattedNominal = String.format("%.1f", nominal) + "k";

                // Insert each new row at the beginning of the table model
                timeInModel.insertRow(0, new Object[]{noTransaksi, namaPelanggan, formattedNominal, status});
            }
        }
        HistoryTransaksi.setModel(timeInModel);
        setTableAlignment(HistoryTransaksi);
        JTableHeader header = HistoryTransaksi.getTableHeader();
        Font headerFont = new Font("Arimo", Font.PLAIN, 10);
        header.setFont(headerFont);

    } catch (Exception e) {
        handleException("Error loading transaction history", e);
    }
}
private void loadHistoryLaundry() {
    DefaultTableModel timeInModel = new DefaultTableModel();
    timeInModel.addColumn("ID"); // Assuming this is the transaction ID
    timeInModel.addColumn("Nama");
    timeInModel.addColumn("Rak");
    timeInModel.addColumn("Status");

    try {
        String dataSql = "SELECT transaksi.kode_transaksi, pelanggan.nama, transaksi.kode_rak, transaksi.status_laundry " +
                "FROM transaksi " +
                "JOIN pelanggan ON transaksi.kode_pelanggan = pelanggan.kode_pelanggan " +
                "ORDER BY transaksi.kode_transaksi DESC LIMIT 3"; // Assuming kode_transaksi is the transaction ID
        try (Connection conn = koneksi.configDB();
             PreparedStatement dataStmt = conn.prepareStatement(dataSql);
             ResultSet res = dataStmt.executeQuery()) {

            // Create a list to store rows in reverse order
            List<Object[]> rows = new ArrayList<>();

            while (res.next()) {
                String noTransaksi = res.getString("kode_transaksi");
                String namaPelanggan = res.getString("nama");
                String kodeRak = res.getString("kode_rak");
                String statusLaundry = res.getString("status_laundry");

                rows.add(new Object[]{noTransaksi, namaPelanggan, kodeRak, statusLaundry});
            }

            // Add rows from the list to the table model in reverse order
            for (int i = rows.size() - 1; i >= 0; i--) {
                timeInModel.addRow(rows.get(i));
            }
        }
        HistoryLaundry.setModel(timeInModel);
        setTableAlignment(HistoryLaundry);
        
        // Change font of the table header
        JTableHeader header = HistoryLaundry.getTableHeader();
        Font headerFont = new Font("Arimo", Font.PLAIN, 10);
        header.setFont(headerFont);

    } catch (Exception e) {
        handleException("Error loading laundry history", e);
    }
}

private void loadLaundrySelesai() {
    DefaultTableModel timeInModel = new DefaultTableModel();
    timeInModel.addColumn("ID"); // Assuming this is the transaction ID
    timeInModel.addColumn("Nama");
    timeInModel.addColumn("Rak");
    timeInModel.addColumn("Status");

    try {
        String dataSql = "SELECT transaksi.kode_transaksi, pelanggan.nama, transaksi.kode_rak, transaksi.status_pembayaran " +
                "FROM transaksi " +
                "JOIN pelanggan ON transaksi.kode_pelanggan = pelanggan.kode_pelanggan " +
                "ORDER BY transaksi.kode_transaksi DESC LIMIT 3"; // Assuming kode_transaksi is the transaction ID
        try (Connection conn = koneksi.configDB();
             PreparedStatement dataStmt = conn.prepareStatement(dataSql);
             ResultSet res = dataStmt.executeQuery()) {

            // Create a list to store rows in reverse order
            List<Object[]> rows = new ArrayList<>();

            while (res.next()) {
                String noTransaksi = res.getString("kode_transaksi");
                String namaPelanggan = res.getString("nama");
                String kodeRak = res.getString("kode_rak");
                String statusLaundry = res.getString("status_pembayaran");

                rows.add(new Object[]{noTransaksi, namaPelanggan, kodeRak, statusLaundry});
            }

            // Add rows from the list to the table model in reverse order
            for (int i = rows.size() - 1; i >= 0; i--) {
                timeInModel.addRow(rows.get(i));
            }
        }
        LaundrySelesai.setModel(timeInModel);
        setTableAlignment(LaundrySelesai);
        
        // Change font and color of the table header
        JTableHeader header = LaundrySelesai.getTableHeader();
        Font headerFont = new Font("Arimo", Font.PLAIN, 10);
        header.setFont(headerFont);
        header.setBackground(new Color(189, 182, 129)); // Set the background color

    } catch (Exception e) {
        handleException("Error loading laundry history", e);
    }
}

     private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
        // Optionally, display a dialog or log the exception to a file.
    }
     
public int countTransaksiRows() {
    int rowCount = 0;
    String countSql = "SELECT COUNT(*) AS rowCount FROM transaksi";

    try (Connection conn = koneksi.configDB();
         PreparedStatement countStmt = conn.prepareStatement(countSql);
         ResultSet resultSet = countStmt.executeQuery()) {

        if (resultSet.next()) {
            rowCount = resultSet.getInt("rowCount");
            // Set the text of the JLabel named "TotalTransaksi" to display the row count
            TotalTransaksi.setText(String.valueOf(rowCount));
        }
    } catch (SQLException e) {
        // Handle any potential exceptions here
        e.printStackTrace();
    }

    return rowCount;
}

public void showTotalDeposit() {
    String totalDepositSql = "SELECT SUM(jumlah_depo) AS totalDeposit FROM deposit";

    try (Connection conn = koneksi.configDB();
         PreparedStatement totalDepositStmt = conn.prepareStatement(totalDepositSql);
         ResultSet resultSet = totalDepositStmt.executeQuery()) {

        if (resultSet.next()) {
            double totalDeposit = resultSet.getDouble("totalDeposit");
            // Set the text of the TotalDeposit JLabel with "Rp. " concatenated with the total deposit amount
            TotalDeposit.setText("Rp. " + String.valueOf(totalDeposit));
        } else {
            // If there are no rows in the result set, set TotalDeposit text to "Rp. 0"
            TotalDeposit.setText("Rp. 0");
        }
    } catch (SQLException e) {
        // Handle any potential exceptions here
        e.printStackTrace();
    }
}
 
 private void setTableAlignment(JTable table) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(currentDate);
        lbl_time.setText(formattedTime);
    }
        
        public void times() {
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dt = new Date();
                SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
                String tt = st.format(dt);
                lbl_time.setText(tt);
            }
        });
        t.start();
    }
        
        public void dt() {
        Date currentDate = new Date();
        Locale indonesianLocale = new Locale("id", "ID");

        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE dd MMMM yyyy", indonesianLocale);
        String formattedDate = sdfDate.format(currentDate);
        lbl_date.setText(formattedDate);
    }
    
    private void setTimeFromDatabase() {
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Format the current date
    String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    Connection conn = null; // Declare Connection object outside try-with-resources block

    try {
        // Establish database connection
        conn = koneksi.configDB();
        PreparedStatement pstmt = conn.prepareStatement("SELECT time_in, time_out FROM absensi WHERE DATE(time_in) = ? ORDER BY id_absensi DESC LIMIT 1");

        // Set the current date as a parameter for the query
        pstmt.setString(1, formattedCurrentDate);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                // Get the time_in and time_out values from the ResultSet as strings
                String timeInString = rs.getString("time_in");
                String timeOutString = rs.getString("time_out");

                // Check if time_in and time_out are not null before parsing
                LocalTime timeIn = null;
                if (timeInString != null && !timeInString.isEmpty()) {
                    timeIn = LocalTime.parse(timeInString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                }
                LocalTime timeOut = null;
                if (timeOutString != null && !timeOutString.isEmpty()) {
                    timeOut = LocalTime.parse(timeOutString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                }

                // Format the time portions as strings or placeholders
                String formattedTimeIn = (timeIn != null) ? timeIn.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
                String formattedTimeOut = (timeOut != null) ? timeOut.format(DateTimeFormatter.ofPattern("HH:mm")) : "";

                // Set the retrieved time values in the corresponding text fields
                time1.setText(formattedTimeIn);
                time2.setText(formattedTimeOut);
            }
        }
    } catch (SQLException ex) {
        // Handle SQL exception
        ex.printStackTrace();
    } finally {
        // Close the connection in the finally block to ensure it's closed even if an exception occurs
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelChart = new javax.swing.JPanel();
        Transaksi = new javax.swing.JButton();
        Stash = new javax.swing.JButton();
        Absensi = new javax.swing.JButton();
        Deposit = new javax.swing.JButton();
        table1 = new javax.swing.JScrollPane();
        LaundrySelesai = new javax.swing.JTable();
        table3 = new javax.swing.JScrollPane();
        HistoryLaundry = new javax.swing.JTable();
        TotalDeposit = new javax.swing.JLabel();
        table2 = new javax.swing.JScrollPane();
        HistoryTransaksi = new javax.swing.JTable();
        TotalTransaksi = new javax.swing.JLabel();
        time1 = new javax.swing.JLabel();
        time2 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setBackground(new java.awt.Color(221, 254, 236));
        setPreferredSize(new java.awt.Dimension(996, 595));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelChart.setBackground(new java.awt.Color(229, 140, 140));
        panelChart.setPreferredSize(new java.awt.Dimension(340, 220));
        add(panelChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, -1));

        Transaksi.setBorder(null);
        Transaksi.setContentAreaFilled(false);
        Transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransaksiActionPerformed(evt);
            }
        });
        add(Transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 170, 50));

        Stash.setBorder(null);
        Stash.setContentAreaFilled(false);
        Stash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StashActionPerformed(evt);
            }
        });
        add(Stash, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 170, 40));

        Absensi.setBorder(null);
        Absensi.setContentAreaFilled(false);
        Absensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbsensiActionPerformed(evt);
            }
        });
        add(Absensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 170, 50));

        Deposit.setBorder(null);
        Deposit.setContentAreaFilled(false);
        Deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepositActionPerformed(evt);
            }
        });
        add(Deposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 520, 170, 40));

        LaundrySelesai.setBackground(new java.awt.Color(224, 217, 154));
        LaundrySelesai.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LaundrySelesai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Rak", "Status Pembayaran"
            }
        ));
        LaundrySelesai.setGridColor(new java.awt.Color(189, 182, 129));
        LaundrySelesai.setOpaque(false);
        LaundrySelesai.setRowHeight(38);
        table1.setViewportView(LaundrySelesai);

        add(table1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, 340, 140));

        HistoryLaundry.setBackground(new java.awt.Color(230, 230, 230));
        HistoryLaundry.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        HistoryLaundry.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Kode Rak", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HistoryLaundry.setRowHeight(40);
        HistoryLaundry.setRowSelectionAllowed(false);
        table3.setViewportView(HistoryLaundry);

        add(table3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 400, 200, 150));

        TotalDeposit.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        TotalDeposit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalDeposit.setText("-");
        add(TotalDeposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 150, -1));

        HistoryTransaksi.setBackground(new java.awt.Color(230, 230, 230));
        HistoryTransaksi.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        HistoryTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Nominal", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HistoryTransaksi.setRowHeight(40);
        HistoryTransaksi.setRowSelectionAllowed(false);
        table2.setViewportView(HistoryTransaksi);

        add(table2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 200, 150));

        TotalTransaksi.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        TotalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalTransaksi.setText("-");
        add(TotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 150, -1));

        time1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        time1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time1.setText("-");
        add(time1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 130, 30));

        time2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        time2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time2.setText("-");
        add(time2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 130, 30));

        lbl_date.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_date.setText("-");
        add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 180, 40));

        lbl_time.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_time.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_time.setText("-");
        add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 180, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dashboardKaryawan/bg.png"))); // NOI18N
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void TransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransaksiActionPerformed

    private void StashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StashActionPerformed

    private void AbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbsensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbsensiActionPerformed

    private void DepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepositActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepositActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Absensi;
    private javax.swing.JButton Deposit;
    private javax.swing.JTable HistoryLaundry;
    private javax.swing.JTable HistoryTransaksi;
    private javax.swing.JTable LaundrySelesai;
    private javax.swing.JButton Stash;
    private javax.swing.JLabel TotalDeposit;
    private javax.swing.JLabel TotalTransaksi;
    private javax.swing.JButton Transaksi;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JPanel panelChart;
    private javax.swing.JScrollPane table1;
    private javax.swing.JScrollPane table2;
    private javax.swing.JScrollPane table3;
    private javax.swing.JLabel time1;
    private javax.swing.JLabel time2;
    // End of variables declaration//GEN-END:variables
}
