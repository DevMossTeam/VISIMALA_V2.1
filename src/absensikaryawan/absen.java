package absensikaryawan;

import com.raven.datechooser.Dates;
import com.raven.datechooser.Months;
import com.raven.datechooser.Years;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import koneksi.koneksi;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static login.Login.userId;

public class absen extends javax.swing.JPanel {
    private Timer timer;
    private Timer t;
    private boolean maxTimeSet = false;
    private Connection conn; // java.sql.Connection;
//    public String userID;
    private final String MONTH_ENGLISH[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String dateFormat = "dd-MM-yyyy";
    private int MONTH = 1;
    private int YEAR = 2021;
    private int DAY = 1;
    private int STATUS = 1;   //  1 is day    2 is month  3 is year
    private int startYear;
    private com.raven.datechooser.SelectedDate selectedDate = new com.raven.datechooser.SelectedDate();
    private List<com.raven.datechooser.EventDateChooser> events;
    private JTextField textRefernce;
    
    public absen(String userId) {
        initComponents();
           setTimeFromDatabase();
        dt();
        times();
        setupTimer();
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String currentTime = timeFormat.format(currentDate);
//        this.userID = userId; 
        execute();

//    if (currentTime.compareTo("05:00") >= 0 && currentTime.compareTo("12:00") <= 0) {
//        // Enable the timein button and disable the timeout button
//        timein.setEnabled(true);
//        timeout.setEnabled(false);
//    } else if (currentTime.compareTo("12:01") >= 0 && currentTime.compareTo("23:59") <= 0) {
//        // Enable the timeout button and disable the timein button
//        timein.setEnabled(false);
//        timeout.setEnabled(true);
//    } else {
//        // Disable both buttons
//        timein.setEnabled(false);
//        timeout.setEnabled(false);
//    }

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
    
private String generateUserId(Connection conn) throws SQLException {
    String prefix = "KAP"; 
    int number = 1; 
    String idAbsen = ""; 

    boolean idFound = false;
    while (!idFound) {
        String userIdToCheck = prefix + number;
        String query = "SELECT COUNT(*) FROM absensi WHERE id_absensi = ?";
        PreparedStatement checkStmt = conn.prepareStatement(query);
        checkStmt.setString(1, userIdToCheck);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            if (count == 0) {
                idAbsen = userIdToCheck;
                idFound = true;
            } else {
                number++; // Jika ID sudah digunakan, tambahkan nomor
            }
        }

        rs.close();
        checkStmt.close();
    }

    return idAbsen;
}

private void execute() {
    // Set the foreground color for the main component
    setForeground(new Color(255, 255, 255));

    // Set the foreground color for the header component
    header.setBackground(new java.awt.Color(204, 93, 93)); // Replace Color.RED with your desired color

    // Initialize the events list
    events = new ArrayList<>();

    // Set up the initial display for today's date
    toDay(false);
}


    public void setTextRefernce(JTextField txt) {
        this.textRefernce = txt;
        this.textRefernce.setEditable(false);
        this.textRefernce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (textRefernce.isEnabled()) {
//                    showPopup();
                }
            }
        });
        setText(false, 0);
    }

    private void setText(boolean runEvent, int act) {
        if (textRefernce != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = df.parse(DAY + "-" + MONTH + "-" + YEAR);
                textRefernce.setText(new SimpleDateFormat(dateFormat).format(date));
            } catch (ParseException e) {
                System.err.println(e);
            }
        }
        if (runEvent) {
//            runEvent(act);
        }
    }

    private void runEvent(int act) {
        com.raven.datechooser.SelectedAction action = new com.raven.datechooser.SelectedAction() {
            @Override
            public int getAction() {
                return act;
            }
        };
        for (com.raven.datechooser.EventDateChooser event : events) {
            event.dateSelected(action, selectedDate);
        }
    }

    private com.raven.datechooser.Event getEventDay(Dates dates) {
        return (MouseEvent evt, int num) -> {
            dates.clearSelected();
            dates.setSelected(num);
            DAY = num;
            selectedDate.setDay(DAY);
            selectedDate.setMonth(MONTH);
            selectedDate.setYear(YEAR);
            setText(true, 1);
            if (evt != null && evt.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evt)) {
//                popup.setVisible(false);
            }
        };
    }

    private com.raven.datechooser.Event getEventMonth() {
        return (MouseEvent evt, int num) -> {
            MONTH = num;
            selectedDate.setDay(DAY);
            selectedDate.setMonth(MONTH);
            selectedDate.setYear(YEAR);
            setText(true, 2);
            Dates d = new Dates();
            d.setForeground(getForeground());
            d.setEvent(getEventDay(d));
            d.showDate(MONTH, YEAR, selectedDate);
            if (slide.slideToDown(d)) {
                cmdMonth.setText(MONTH_ENGLISH[MONTH - 1]);
                cmdYear.setText(YEAR + "");
                STATUS = 1;
            }
        };
    }

    private com.raven.datechooser.Event getEventYear() {
        return (MouseEvent evt, int num) -> {
            YEAR = num;
            selectedDate.setDay(DAY);
            selectedDate.setMonth(MONTH);
            selectedDate.setYear(YEAR);
            setText(true, 3);
            Months d = new Months();
            d.setEvent(getEventMonth());
            if (slide.slideToDown(d)) {
                cmdMonth.setText(MONTH_ENGLISH[MONTH - 1]);
                cmdYear.setText(YEAR + "");
                STATUS = 2;
            }
        };
    }

    private void toDay(boolean runEvent) {
        Dates dates = new Dates();
        dates.setForeground(getForeground());
        dates.setEvent(getEventDay(dates));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String toDay = df.format(date);
        DAY = Integer.valueOf(toDay.split("-")[0]);
        MONTH = Integer.valueOf(toDay.split("-")[1]);
        YEAR = Integer.valueOf(toDay.split("-")[2]);
        selectedDate.setDay(DAY);
        selectedDate.setMonth(MONTH);
        selectedDate.setYear(YEAR);
        dates.showDate(MONTH, YEAR, selectedDate);
        slide.slideNon(dates);
        cmdMonth.setText(MONTH_ENGLISH[MONTH - 1]);
        cmdYear.setText(YEAR + "");
        setText(runEvent, 0);
    }

    public void toDay() {
        toDay(true);
    }

    private void setDateNext() {
        Dates dates = new Dates();
        dates.setForeground(getForeground());
        dates.setEvent(getEventDay(dates));
        dates.showDate(MONTH, YEAR, selectedDate);
        if (slide.slideToLeft(dates)) {
            cmdMonth.setText(MONTH_ENGLISH[MONTH - 1]);
            cmdYear.setText(YEAR + "");
        }
    }

    private void setDateBack() {
        Dates dates = new Dates();
        dates.setForeground(getForeground());
        dates.setEvent(getEventDay(dates));
        dates.showDate(MONTH, YEAR, selectedDate);
        if (slide.slideToRight(dates)) {
            cmdMonth.setText(MONTH_ENGLISH[MONTH - 1]);
            cmdYear.setText(YEAR + "");
        }
    }

    private void setYearNext() {
        com.raven.datechooser.Years years = new com.raven.datechooser.Years();
        years.setEvent(getEventYear());
        startYear = years.next(startYear);
        slide.slideToLeft(years);
    }

    private void setYearBack() {
        if (startYear >= 1000) {
            com.raven.datechooser.Years years = new com.raven.datechooser.Years();
            years.setEvent(getEventYear());
            startYear = years.back(startYear);
            slide.slideToLeft(years);
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

        calender = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        cmdForward = new com.raven.datechooser.Button();
        MY = new javax.swing.JLayeredPane();
        cmdMonth = new com.raven.datechooser.Button();
        lb = new javax.swing.JLabel();
        cmdYear = new com.raven.datechooser.Button();
        cmdPrevious = new com.raven.datechooser.Button();
        slide = new com.raven.datechooser.Slider();
        time2 = new javax.swing.JLabel();
        time1 = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        timein = new javax.swing.JButton();
        timeout = new javax.swing.JButton();
        bg = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));
        setPreferredSize(new java.awt.Dimension(996, 595));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        calender.setBackground(new java.awt.Color(255, 255, 255));
        calender.setPreferredSize(new java.awt.Dimension(835, 335));

        header.setBackground(new java.awt.Color(204, 93, 93));
        header.setMaximumSize(new java.awt.Dimension(262, 40));

        cmdForward.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensikaryawan/forward.png"))); // NOI18N
        cmdForward.setPaintBackground(false);
        cmdForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdForwardActionPerformed(evt);
            }
        });

        MY.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0);
        flowLayout1.setAlignOnBaseline(true);
        MY.setLayout(flowLayout1);

        cmdMonth.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdMonth.setForeground(new java.awt.Color(255, 255, 255));
        cmdMonth.setText("January");
        cmdMonth.setFocusPainted(false);
        cmdMonth.setFont(new java.awt.Font("Kh Content", 0, 14)); // NOI18N
        cmdMonth.setPaintBackground(false);
        cmdMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMonthActionPerformed(evt);
            }
        });
        MY.add(cmdMonth);

        lb.setForeground(new java.awt.Color(255, 255, 255));
        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("-");
        MY.add(lb);

        cmdYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdYear.setForeground(new java.awt.Color(255, 255, 255));
        cmdYear.setText("2018");
        cmdYear.setFocusPainted(false);
        cmdYear.setFont(new java.awt.Font("Kh Content", 0, 14)); // NOI18N
        cmdYear.setPaintBackground(false);
        cmdYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdYearActionPerformed(evt);
            }
        });
        MY.add(cmdYear);

        cmdPrevious.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensikaryawan/previous.png"))); // NOI18N
        cmdPrevious.setPaintBackground(false);
        cmdPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPreviousActionPerformed(evt);
            }
        });
        cmdPrevious.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmdPreviousKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MY, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdForward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdForward, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        slide.setLayout(new javax.swing.BoxLayout(slide, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout calenderLayout = new javax.swing.GroupLayout(calender);
        calender.setLayout(calenderLayout);
        calenderLayout.setHorizontalGroup(
            calenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        calenderLayout.setVerticalGroup(
            calenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calenderLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(calender, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 840, 340));

        time2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        time2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time2.setText("-");
        add(time2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 220, 40));

        time1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        time1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time1.setText("-");
        add(time1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 220, 40));

        lbl_time.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_time.setText("-");
        add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 220, 30));

        lbl_date.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_date.setText("-");
        add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 220, 40));

        timein.setBorder(null);
        timein.setContentAreaFilled(false);
        timein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeinActionPerformed(evt);
            }
        });
        add(timein, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 270, 40));

        timeout.setBorder(null);
        timeout.setContentAreaFilled(false);
        timeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeoutActionPerformed(evt);
            }
        });
        add(timeout, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, 270, 40));

        bg.setBackground(new java.awt.Color(221, 254, 236));
        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/absensikaryawan/time.png"))); // NOI18N
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cmdForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdForwardActionPerformed
        if (STATUS == 1) {   //  Date
            if (MONTH == 12) {
                MONTH = 1;
                YEAR++;
            } else {
                MONTH++;
            }
            setDateNext();
        } else if (STATUS == 3) {    //  Year
            setYearNext();
        } else {
            YEAR++;
            Months months = new Months();
            months.setEvent(getEventMonth());
            slide.slideToLeft(months);
            cmdYear.setText(YEAR + "");
        }
    }//GEN-LAST:event_cmdForwardActionPerformed

    private void cmdMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMonthActionPerformed
        if (STATUS != 2) {
            STATUS = 2;
            Months months = new Months();
            months.setEvent(getEventMonth());
            slide.slideToDown(months);
        } else {
            Dates dates = new Dates();
            dates.setForeground(getForeground());
            dates.setEvent(getEventDay(dates));
            dates.showDate(MONTH, YEAR, selectedDate);
            slide.slideToDown(dates);
            STATUS = 1;
        }
    }//GEN-LAST:event_cmdMonthActionPerformed

    private void cmdYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdYearActionPerformed
        if (STATUS != 3) {
            STATUS = 3;
            Years years = new Years();
            years.setEvent(getEventYear());
            startYear = years.showYear(YEAR);
            slide.slideToDown(years);
        } else {
            Dates dates = new Dates();
            dates.setForeground(getForeground());
            dates.setEvent(getEventDay(dates));
            dates.showDate(MONTH, YEAR, selectedDate);
            slide.slideToDown(dates);
            STATUS = 1;
        }
    }//GEN-LAST:event_cmdYearActionPerformed

    private void cmdPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPreviousActionPerformed
        if (STATUS == 1) {   //  Date
            if (MONTH == 1) {
                MONTH = 12;
                YEAR--;
            } else {
                MONTH--;
            }
            setDateBack();
        } else if (STATUS == 3) {    //  Year
            setYearBack();
        } else {
            if (YEAR >= 1000) {
                YEAR--;
                Months months = new Months();
                months.setEvent(getEventMonth());
                slide.slideToLeft(months);
                cmdYear.setText(YEAR + "");
            }
        }
    }//GEN-LAST:event_cmdPreviousActionPerformed

    private void cmdPreviousKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmdPreviousKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            Component com = slide.getComponent(0);
            if (com instanceof Dates) {
                Dates d = (Dates) com;
                d.up();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            Component com = slide.getComponent(0);
            if (com instanceof Dates) {
                Dates d = (Dates) com;
                d.down();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            Component com = slide.getComponent(0);
            if (com instanceof Dates) {
                Dates d = (Dates) com;
                d.back();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            Component com = slide.getComponent(0);
            if (com instanceof Dates) {
                Dates d = (Dates) com;
                d.next();
            }
        }
    }//GEN-LAST:event_cmdPreviousKeyPressed

    private void timeinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeinActionPerformed
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(dateFormatter);

        boolean recordExists = checkRecordExists(formattedDate);

        if (!recordExists) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = now.format(dateTimeFormatter);

            try (java.sql.Connection conn = koneksi.configDB();
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO absensi (status_absensi, id_absensi, id_user, time_in) VALUES (?, ?, ?, ?)")) {
                // Set values for status_absensi, id_user, and time_in columns
                String idAbsen = generateUserId(conn);
                pstmt.setString(1, getTimeStatus(formattedDateTime.substring(11, 16))); // Set status based on time_in value (hours and minutes only)
                pstmt.setString(2, idAbsen);
                pstmt.setString(3, userId); // Replace someUserId with the actual user ID
                pstmt.setString(4, formattedDateTime); // Insert the current date and time

                // Execute the INSERT query
                pstmt.executeUpdate();
                setTimeFromDatabase();

                System.out.println("UserID: " + userId);

                // Display a message indicating successful insertion
                JOptionPane.showMessageDialog(this, "Time inserted into database successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Display a message indicating that a record already exists for the current date
            JOptionPane.showMessageDialog(this, "A record for today already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        }

        // Method to check if a record for the specified date already exists in the database
        private boolean checkRecordExists(String date) {
            boolean recordExists = false;
            try (java.sql.Connection conn = koneksi.configDB();
                PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM absensi WHERE DATE(time_in) = ?")) {
                pstmt.setString(1, date);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        if (count > 0) {
                            recordExists = true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return recordExists;
        }

        private String getTimeStatus(String time) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            if (hours > 7 || (hours == 7 && minutes > 0)) {
                return "terlambat";
            } else {
                return "tepat waktu"; // You can set another status here if needed
            }
    }//GEN-LAST:event_timeinActionPerformed

    private void timeoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeoutActionPerformed
        LocalDateTime now = LocalDateTime.now();

        // Format date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(dateFormatter);

        // Check if a timeout has already been recorded for the current date and user
        boolean timeoutRecorded = checkTimeoutRecorded(formattedDate, userId);

        if (!timeoutRecorded) {
            // If no timeout has been recorded for the current date and user, proceed with update
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = now.format(dateTimeFormatter);

            try (java.sql.Connection conn = koneksi.configDB();
                PreparedStatement pstmt = conn.prepareStatement("UPDATE absensi SET time_out = ? WHERE DATE(time_in) = ? AND id_user = ?")) {

                pstmt.setString(1, formattedDateTime); // Insert the current date and time
                pstmt.setString(2, formattedDate); // Update the row with the current date
                pstmt.setString(3, userId); // Update the row for the current user

                // Execute the UPDATE query
                int rowsAffected = pstmt.executeUpdate();
                setTimeFromDatabase();

                if (rowsAffected > 0) {
                    // Display a message indicating successful update
                    JOptionPane.showMessageDialog(this, "Time out updated in database successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Display a message indicating failure to update
                    JOptionPane.showMessageDialog(this, "Failed to update time out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Display a message indicating that a timeout has already been recorded for the current date and user
            JOptionPane.showMessageDialog(this, "A timeout has already been recorded for today.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        }

        // Method to check if a timeout has already been recorded for the specified date and user in the database
        private boolean checkTimeoutRecorded(String date, String userID) {
            boolean timeoutRecorded = false;
            try (java.sql.Connection conn = koneksi.configDB();
                PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM absensi WHERE DATE(time_out) = ? AND id_user = ? AND time_in IS NOT NULL")) {
                pstmt.setString(1, date);
                pstmt.setString(2, userID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        if (count > 0) {
                            timeoutRecorded = true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return timeoutRecorded;
        }

        private String getLastInsertedId() throws SQLException {
            String lastInsertedId = null;

            try (Connection conn = koneksi.configDB();
                PreparedStatement pstmt = conn.prepareStatement("SELECT id_absensi FROM absensi ORDER BY id_absensi DESC LIMIT 1")) {

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    lastInsertedId = rs.getString("id_absensi"); // Retrieve the value from the result set
                }
            }

            return lastInsertedId;
    }//GEN-LAST:event_timeoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane MY;
    private javax.swing.JLabel bg;
    private javax.swing.JPanel calender;
    private com.raven.datechooser.Button cmdForward;
    private com.raven.datechooser.Button cmdMonth;
    private com.raven.datechooser.Button cmdPrevious;
    private com.raven.datechooser.Button cmdYear;
    private javax.swing.JPanel header;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_time;
    private com.raven.datechooser.Slider slide;
    private javax.swing.JLabel time1;
    private javax.swing.JLabel time2;
    private javax.swing.JButton timein;
    private javax.swing.JButton timeout;
    // End of variables declaration//GEN-END:variables
}
