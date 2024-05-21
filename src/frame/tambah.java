/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import mainOwner.MainOwner;

/**
 *
 * @author YAVIE
 */
public class tambah extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    
    /**
     * Creates new form tambah
     */
    public tambah() {
        koneksi();
        initComponents();
        berhasils.setVisible(false);
        jamrentang.setVisible(false);
        jamangka.setVisible(false);
        harirentang.setVisible(false);
        hariangka.setVisible(false);
        harganegatif.setVisible(false);
        harsalah.setVisible(false);
        harko.setVisible(false);
        namproko.setVisible(false);
        namproada.setVisible(false);
        datagagal1.setVisible(false);
        datagagal2.setVisible(false);
        
        
            close.setVisible(false);
            

        jams.setForeground(new Color (0,0,0));
        haris.setForeground(new Color (0,0,0));
        kosongkan();
//        tabel();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 3,
                (screenSize.height - frameSize.height) / 4);
    }

    private void koneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = koneksi.configDB();
            stat = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: ");
            e.printStackTrace(); // Cetak exception untuk debugging
        }
    }
    private void kosongkan() {
        nampro.setText("");
        harpro.setText("");
        haris.setText(" Hari...");
        jams.setText(" Jam...");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenis = new javax.swing.ButtonGroup();
        close = new javax.swing.JLabel();
        datagagal2 = new javax.swing.JLabel();
        datagagal1 = new javax.swing.JLabel();
        namproada = new javax.swing.JLabel();
        namproko = new javax.swing.JLabel();
        harko = new javax.swing.JLabel();
        harsalah = new javax.swing.JLabel();
        harganegatif = new javax.swing.JLabel();
        berhasils = new javax.swing.JLabel();
        jamangka = new javax.swing.JLabel();
        jamrentang = new javax.swing.JLabel();
        hariangka = new javax.swing.JLabel();
        harirentang = new javax.swing.JLabel();
        jen = new combo_suggestion.ComboBoxSuggestion();
        jLabel15 = new javax.swing.JLabel();
        jams = new javax.swing.JTextField();
        haris = new javax.swing.JTextField();
        harpro = new javax.swing.JTextField();
        nampro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        simpans = new javax.swing.JButton();
        clears = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        kembalis = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/close.png"))); // NOI18N
        add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 220, -1, -1));

        datagagal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/19.png"))); // NOI18N
        add(datagagal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        datagagal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/18.png"))); // NOI18N
        add(datagagal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        namproada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/17.png"))); // NOI18N
        add(namproada, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, -1, -1));

        namproko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/16.png"))); // NOI18N
        add(namproko, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        harko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/15.png"))); // NOI18N
        add(harko, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        harsalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/14.png"))); // NOI18N
        add(harsalah, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        harganegatif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/13.png"))); // NOI18N
        add(harganegatif, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        berhasils.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Tambahkan subjudul (2).png"))); // NOI18N
        add(berhasils, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

        jamangka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/4.png"))); // NOI18N
        add(jamangka, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        jamrentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/3.png"))); // NOI18N
        add(jamrentang, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        hariangka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/6.png"))); // NOI18N
        add(hariangka, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        harirentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/5.png"))); // NOI18N
        add(harirentang, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        jen.setBorder(null);
        jen.setEditable(false);
        jen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kilogram", "Satuan" }));
        add(jen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 236, 140, 20));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 2 (1).png"))); // NOI18N
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));

        jams.setBorder(null);
        jams.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jamsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jamsFocusLost(evt);
            }
        });
        jams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jamsActionPerformed(evt);
            }
        });
        add(jams, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 390, 70, 30));

        haris.setBorder(null);
        haris.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                harisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                harisFocusLost(evt);
            }
        });
        add(haris, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, 70, 30));

        harpro.setBorder(null);
        harpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harproActionPerformed(evt);
            }
        });
        add(harpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, 270, 30));

        nampro.setBorder(null);
        nampro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namproActionPerformed(evt);
            }
        });
        add(nampro, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, 270, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Batas Waktu");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Harga Produk");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Jenis Produk");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 2.png"))); // NOI18N
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 390, 100, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 1.png"))); // NOI18N
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 1.png"))); // NOI18N
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 310, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Nama Produk");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Frame 2.png"))); // NOI18N
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 390, 100, -1));

        simpans.setBackground(new java.awt.Color(10, 172, 42));
        simpans.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpans.setForeground(new java.awt.Color(255, 255, 255));
        simpans.setText("Simpan");
        simpans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpansActionPerformed(evt);
            }
        });
        add(simpans, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, -1, -1));

        clears.setBackground(new java.awt.Color(11, 146, 215));
        clears.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clears.setForeground(new java.awt.Color(255, 255, 255));
        clears.setText("Clear");
        clears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearsActionPerformed(evt);
            }
        });
        add(clears, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, 90, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambahkan Produk");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        kembalis.setBackground(new java.awt.Color(128, 147, 131));
        kembalis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembalisMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (38).png"))); // NOI18N

        javax.swing.GroupLayout kembalisLayout = new javax.swing.GroupLayout(kembalis);
        kembalis.setLayout(kembalisLayout);
        kembalisLayout.setHorizontalGroup(
            kembalisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kembalisLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kembalisLayout.setVerticalGroup(
            kembalisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        add(kembalis, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 520, 50, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (14).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void kembalisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembalisMouseClicked
        // TODO add your handling code here:
    // Mendapatkan referensi ke objek Main dari panelForm
    MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
    
    // Menampilkan kembali panelProduk_O
    mainInstance.showFormOwner(new frame.panelProduk_O(mainInstance));

    }//GEN-LAST:event_kembalisMouseClicked

    private void clearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearsActionPerformed
        // TODO add your handling code here:
              kosongkan();
    }//GEN-LAST:event_clearsActionPerformed

    private void simpansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpansActionPerformed
    try {
        // Validate inputs
        String namaProduk = nampro.getText().trim();
        String hargaProduk = harpro.getText().trim();
        String BwHari = haris.getText().trim();
        String BwJam = jams.getText().trim();

        // Ensure that hargaProduk is a valid number
        try {
            double hargaValue = Double.parseDouble(hargaProduk);
            if (hargaValue < 0) {
            harganegatif.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                harganegatif.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            harsalah.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    harsalah.setVisible(false);
                    close.setVisible(false);
                }
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
            return; // Return to stop further execution
        }

        // Validate BwJam
 try {
    // Ensure that the value of "jam" is within the range of 0-24
    int jamValue = Integer.parseInt(BwJam);
    if (jamValue < 0 || jamValue > 24) {
        // Display the appropriate message and close button
        jamrentang.setVisible(true);
        close.setVisible(true);

        // Create a new Timer with an anonymous inner class for the ActionListener
        Timer timerCorrect = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the message and close button after 3 seconds
                jamrentang.setVisible(false);
                close.setVisible(false);
            }
        });
        timerCorrect.setRepeats(false); // Set to run only once
        timerCorrect.start(); // Start the timer
        return;
    }
} catch (NumberFormatException e) {
    // Display the appropriate message and close button
    jamangka.setVisible(true);
    close.setVisible(true);

    // Create a new Timer with an anonymous inner class for the ActionListener
    Timer timerIncorrect = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide the message and close button after 3 seconds
            jamangka.setVisible(false);
            close.setVisible(false);
        }
    });
    timerIncorrect.setRepeats(false); // Set to run only once
    timerIncorrect.start(); // Start the timer

    return;
}

        try {
    // Ensure that the value of "jam" is within the range of 0-24
        int hariValue = Integer.parseInt(BwHari);
        if (hariValue < 0 || hariValue > 356) {
        // Display the appropriate message and close button
        harirentang.setVisible(true);
        close.setVisible(true);

        // Create a new Timer with an anonymous inner class for the ActionListener
        Timer timerCorrect = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the message and close button after 3 seconds
                harirentang.setVisible(false);
                close.setVisible(false);
            }
        });
        timerCorrect.setRepeats(false); // Set to run only once
        timerCorrect.start(); // Start the timer
        return;
    }
} catch (NumberFormatException e) {
    // Display the appropriate message and close button
    hariangka.setVisible(true);
    close.setVisible(true);

    // Create a new Timer with an anonymous inner class for the ActionListener
    Timer timerIncorrect = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide the message and close button after 3 seconds
            hariangka.setVisible(false);
            close.setVisible(false);
        }
    });
    timerIncorrect.setRepeats(false); // Set to run only once
    timerIncorrect.start(); // Start the timer

    return;
}

        // Continue with the remaining validation and database checks...
        // Your remaining code...

        if (hargaProduk.isEmpty()) {
            harko.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                harko.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
            return;
        }

        if (namaProduk.isEmpty()) {
            namproko.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                namproko.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
            return;
        }

        // Check if the product name already exists in the database
        if (isNamaProdukExists(namaProduk, t)) {
            namproada.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                namproada.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
            return; // Prevent further execution
        }

        if (t == null) {
            // Insert new data
            String insertQuery = "INSERT INTO produk(nama_produk, harga_produk, jenis_produk, hari, jam) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPstmt.setString(1, namaProduk);
                insertPstmt.setString(2, hargaProduk);
                insertPstmt.setString(3, jen.getSelectedItem().toString());
                insertPstmt.setString(4, BwHari);
                insertPstmt.setString(5, BwJam);

                int affectedRows = insertPstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = insertPstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idProduk = generatedKeys.getInt(1);
                            System.out.println("ID Produk: " + idProduk);

                            DefaultTableModel model = (DefaultTableModel) panelProduk_O.tabels.getModel();
                            model.addRow(new Object[]{
                                    idProduk,
                                    namaProduk,
                                    hargaProduk,
                                    jen.getSelectedItem().toString(),
                                    BwHari + " Hari", // Display " Hari"
                                    BwJam + " Jam"   // Display " Jam"
                            });

                            // Update jumlah data
                            int rowCount = model.getRowCount();
                            jLabel1.setText("Jumlah Data: " + rowCount);

                            // Clear UI components and show success message
                            kosongkan();
                                        // Tampilkan label berhasils
            berhasils.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                berhasils.setVisible(false);
                close.setVisible(false);

                // Kembali ke halaman panelProduk_O setelah menampilkan label berhasils
                MainOwner mainInstance = (MainOwner) SwingUtilities.getWindowAncestor(this);
                mainInstance.showFormOwner(new frame.panelProduk_O(mainInstance));
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
                        }
                    }
                } else {
                    // Data insertion failed
            datagagal1.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                datagagal1.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
                }
            } catch (SQLException e) {
                e.printStackTrace();
           datagagal2.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        datagagal2.setVisible(false);
                        close.setVisible(false);
                    }
                });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
            }
        } else {
            // Update existing data
            String updateQuery = "UPDATE produk SET nama_produk=?, harga_produk=?, jenis_produk=?, hari=?, jam=? WHERE id_produk=?";
            try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
                updatePstmt.setString(1, namaProduk);
                updatePstmt.setString(2, hargaProduk);
                updatePstmt.setString(3, jen.getSelectedItem().toString());
                updatePstmt.setString(4, BwHari);
                updatePstmt.setString(5, BwJam);
                updatePstmt.setString(6, t);

                int affectedRows = updatePstmt.executeUpdate();

                if (affectedRows > 0) {
                    DefaultTableModel model = (DefaultTableModel) panelProduk_O.tabels.getModel();
                    int selectedRowIndex = panelProduk_O.tabels.getSelectedRow();

                    // Update the existing row in the table model
                    if (selectedRowIndex != -1) {
                        model.setValueAt(namaProduk, selectedRowIndex, 1);
                        model.setValueAt(hargaProduk, selectedRowIndex, 2);
                        model.setValueAt(jen.getSelectedItem().toString(), selectedRowIndex, 3);
                        model.setValueAt(BwHari + " Hari", selectedRowIndex, 4); // Display " Hari"
                        model.setValueAt(BwJam + " Jam", selectedRowIndex, 5);   // Display " Jam"
                    }

                    // Update jumlah data
                    int rowCount = model.getRowCount();
                    jLabel1.setText("Jumlah Data: " + rowCount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            kosongkan(); // Clear UI components
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate");

            // Reset nilai t agar kembali ke mode penambahan data baru
            t = null;
        }

        // Setelah semua operasi selesai, kembalikan ke panel Produk_O
    //    Main mainInstance = (Main) SwingUtilities.getWindowAncestor(this);
      //  mainInstance.showForm(new frame.panelProduk_O(mainInstance));
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai ! ");
    }
    }//GEN-LAST:event_simpansActionPerformed
private boolean isNamaProdukExists(String namaProduk, String currentId) {
    try {
        String query = "SELECT COUNT(*) FROM produk WHERE nama_produk = ? AND id_produk != ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, namaProduk);
            pstmt.setString(2, currentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai");
    }
    return false;
    
    
    } 
    private void namproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namproActionPerformed

    private void harproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harproActionPerformed

    private void jamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jamsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamsActionPerformed

    private void harisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harisFocusGained
        // TODO add your handling code here:
                if(haris.getText().equals(" Hari...")) {
            haris.setText("");
            haris.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_harisFocusGained

    private void harisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harisFocusLost
        // TODO add your handling code here:
                       if(haris.getText().equals("")) {
            haris.setText(" Hari...");
            haris.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_harisFocusLost

    private void jamsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jamsFocusGained
        // TODO add your handling code here:
                                if(jams.getText().equals(" Jam...")) {
            jams.setText("");
            jams.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jamsFocusGained

    private void jamsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jamsFocusLost
               if(jams.getText().equals("")) {
            jams.setText(" Jam...");
            jams.setForeground(new Color(102,102,102));
        }
    }//GEN-LAST:event_jamsFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel berhasils;
    private javax.swing.JButton clears;
    private javax.swing.JLabel close;
    private javax.swing.JLabel datagagal1;
    private javax.swing.JLabel datagagal2;
    private javax.swing.JLabel harganegatif;
    private javax.swing.JLabel hariangka;
    private javax.swing.JLabel harirentang;
    private javax.swing.JTextField haris;
    private javax.swing.JLabel harko;
    private javax.swing.JTextField harpro;
    private javax.swing.JLabel harsalah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jamangka;
    private javax.swing.JLabel jamrentang;
    private javax.swing.JTextField jams;
    private combo_suggestion.ComboBoxSuggestion jen;
    private javax.swing.ButtonGroup jenis;
    private javax.swing.JPanel kembalis;
    private javax.swing.JTextField nampro;
    private javax.swing.JLabel namproada;
    private javax.swing.JLabel namproko;
    private javax.swing.JButton simpans;
    // End of variables declaration//GEN-END:variables
}
