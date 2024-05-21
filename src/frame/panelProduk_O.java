/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;
import static groovy.ui.text.FindReplaceUtility.dispose;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;
import mainOwner.MainOwner;


/**
 *
 * @author YAVIE
 */
public class panelProduk_O extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private MainOwner mainInstance; // Referensi ke objek Main
    
    public panelProduk_O(MainOwner mainInstance) {
        this.mainInstance = mainInstance; // Inisialisasi mainInstance dengan objek Main yang diterima
        initComponents();
        samberhasils.setVisible(false);
        pilbar.setVisible(false);
        close.setVisible(false);
        galhap.setVisible(false);
        pildul.setVisible(false);
        
         caris.setText(" Search...");
        caris.setForeground(new Color (255,255,255));
        
        koneksi();
        setEditButtonListener();
        setTambasButtonListener();
//        kosongkan();
        tabel();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 4);
    }

    private void koneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = koneksi.configDB();
            stat = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: ");
            e.printStackTrace(); // Cetak exception untuk debugging
        }
    }

    
    private void tabel() {
        // Set show grid untuk menampilkan garis pembatas
        tabels.setShowGrid(true);

        // Set show horizontal lines dan show vertical lines
        tabels.setShowHorizontalLines(true);
        tabels.setShowVerticalLines(true);

        DefaultTableModel t = new DefaultTableModel();
        t.addColumn("Id Produk");
        t.addColumn("Nama Produk");
        t.addColumn("Harga Produk");
        t.addColumn("Jenis Produk");
        t.addColumn("Batas Hari");
        t.addColumn("Batas Jam");
        tabels.setModel(t);

        try {
            res = stat.executeQuery("SELECT * FROM produk");
            int rowCount = 0;

            while (res.next()) {
                t.addRow(new Object[]{
                    res.getString("id_produk"),
                    res.getString("nama_produk"),
                    res.getString("harga_produk"),
                    res.getString("jenis_produk"),
                    res.getString("hari"),
                    res.getString("jam"),                   
                });

                // Set cell renderer for the first row only
                if (rowCount == 0) {
                    tabels.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(4).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(5).setCellRenderer(new CustomTableCellRenderer());
                    
                }

                rowCount++;

                // Update JLabel to display the current row count
                //bagro.setText("Jumlah Data: " + rowCount);
            }
        } catch (Exception e) {
            Component rootPane = null;
            JOptionPane.showMessageDialog(rootPane, e);
        }

        // Set rata tengah (centered) untuk semua sel di tabel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < tabels.getColumnCount(); i++) {
            tabels.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set rata tengah (centered) untuk header kolom
        JTableHeader header = tabels.getTableHeader();
        header.setDefaultRenderer(centerRenderer);
        header.setPreferredSize(new Dimension(100, 40)); // Sesuaikan dimensi header jika diperlukan
        header.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14)); // Sesuaikan font header jika diperlukan
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Mengubah warna latar belakang untuk baris pertama
            if (row == 0) {
                cellComponent.setBackground(new java.awt.Color(23, 233, 184)); // Ganti warna sesuai kebutuhan
            } else {
                cellComponent.setBackground(table.getBackground());
            }

            return cellComponent;
        }
    }
    
    public void refreshTable() {
    DefaultTableModel model = (DefaultTableModel) tabels.getModel();
    model.setRowCount(0); // Mengosongkan tabel sebelum mengisi ulang dengan data baru
    
    // Panggil metode tabel() atau eksekusi query untuk mengambil data terbaru dari database dan mengisi ulang tabel
    tabel(); // Anda dapat memanggil kembali metode tabel() atau menulis ulang kode di sini untuk mengisi ulang tabel
}
private void setEditButtonListener() {
    sedit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = tabels.getSelectedRow();
            if (i != -1) {
                // Memanggil showForm dari objek Main hanya jika ada baris yang dipilih
                mainInstance.showFormOwner(new frame.form_pproduk());
            } else {
                // Tampilkan pildul jika tidak ada baris yang dipilih
                pildul.setVisible(true);
                close.setVisible(true);
                // Close the correct JLabel after 3 seconds
                Timer timerCorrect = new Timer(3000, (evt) -> {
                    pildul.setVisible(false);
                    close.setVisible(false);
                });
                timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
                timerCorrect.start();
            }
        }
    });
}

private void setTambasButtonListener() {
    tambas.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (mainInstance != null) {
                mainInstance.showFormOwner(new frame.tambah());
            } else {
                System.out.println("Error: Objek Main belum ditetapkan");
            }
        }
    });
}
    private void cariData(String kataKunci) {
        DefaultTableModel model = (DefaultTableModel) tabels.getModel();
        model.setRowCount(0); // Clear existing rows

        try {
            // Use prepared statement to prevent SQL injection
            String query = "SELECT * FROM produk WHERE id_produk LIKE ? OR nama_produk LIKE ? OR harga_produk LIKE ? OR jenis_produk LIKE ? OR hari LIKE ? OR jam LIKE ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                for (int i = 1; i <= 6; i++) {
                    pstmt.setString(i, "%" + kataKunci + "%");
                }

                try (ResultSet res = pstmt.executeQuery()) {
                    while (res.next()) {
                        // Adjust column types based on actual data types
                        model.addRow(new Object[]{
                            res.getString("id_produk"),
                            res.getString("nama_produk"),
                            res.getInt("harga_produk"),
                            res.getString("jenis_produk"),
                            res.getInt("hari"),
                            res.getInt("jam"),
                        });
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencari ");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        close = new javax.swing.JLabel();
        pildul = new javax.swing.JLabel();
        galhap = new javax.swing.JLabel();
        pilbar = new javax.swing.JLabel();
        samberhasils = new javax.swing.JLabel();
        refress = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tambas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sedit = new javax.swing.JButton();
        shapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        caris = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        bagro = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/close.png"))); // NOI18N
        add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, -1, -1));

        pildul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Tambahkan subjudul (5).png"))); // NOI18N
        pildul.setToolTipText("");
        add(pildul, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, -1, -1));

        galhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Tambahkan subjudul (3).png"))); // NOI18N
        galhap.setToolTipText("");
        add(galhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, -1));

        pilbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/9.png"))); // NOI18N
        pilbar.setToolTipText("");
        add(pilbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        samberhasils.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/3_1.png"))); // NOI18N
        samberhasils.setToolTipText("");
        add(samberhasils, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, -1));

        refress.setBackground(new java.awt.Color(255, 255, 255));
        refress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refressMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/refresh (1).png"))); // NOI18N
        refress.add(jLabel6);

        add(refress, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 40, 40));

        tambas.setBackground(new java.awt.Color(255, 255, 255));
        tambas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambasMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/plus (3).png"))); // NOI18N
        tambas.add(jLabel4);

        add(tambas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 40, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (28).png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (30).png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 880, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel5.setText("Form Produk");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        sedit.setBackground(new java.awt.Color(255, 153, 0));
        sedit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sedit.setForeground(new java.awt.Color(255, 255, 255));
        sedit.setText("Edit");
        sedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seditActionPerformed(evt);
            }
        });
        add(sedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 90, -1));

        shapus.setBackground(new java.awt.Color(204, 0, 0));
        shapus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        shapus.setForeground(new java.awt.Color(255, 255, 255));
        shapus.setText("Hapus");
        shapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapusActionPerformed(evt);
            }
        });
        add(shapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 130, 90, -1));

        tabels.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabels.setGridColor(new java.awt.Color(125, 151, 132));
        tabels.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabels);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 880, 320));

        caris.setBackground(new java.awt.Color(77, 69, 69));
        caris.setForeground(new java.awt.Color(255, 255, 255));
        caris.setBorder(null);
        caris.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                carisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                carisFocusLost(evt);
            }
        });
        caris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carisActionPerformed(evt);
            }
        });
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, 170, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (35).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, 360, 30));

        bagro.setBackground(new java.awt.Color(255, 255, 255));
        bagro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (15).png"))); // NOI18N
        add(bagro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
  /*  private void tampilkanDataBerdasarkanID(int idProduk) {
        try {
            res = stat.executeQuery("SELECT * FROM produk WHERE id_produk='" + idProduk + "'");
            while (res.next()) {
    //            nampro.setText(res.getString("nama_produk"));
      //          harpro.setText(res.getString("harga_produk"));
        //        comjenispro.setSelectedItem(res.getString("jenis_produk"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } */
    private void seditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seditActionPerformed
   int i = tabels.getSelectedRow();
    if (i == -1) {
        pildul.setVisible(true);
        close.setVisible(true);
        // Close the correct JLabel after 3 seconds
        Timer timerCorrect = new Timer(3000, (e) -> {
            pildul.setVisible(false);
            close.setVisible(false);
        });
        timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
        timerCorrect.start();
        return;
    }
        if (mainInstance != null) {
        mainInstance.showFormOwner(new frame.form_pproduk());
    } else {
        System.out.println("Error: Objek Main belum ditetapkan");
    }
    // Tetapkan nilai atribut form_produk berdasarkan baris yang dipilih
    String id = tabels.getValueAt(i, 0).toString();
    String namapro = tabels.getValueAt(i, 1).toString();
    String harga = tabels.getValueAt(i, 2).toString();
    String jenis = tabels.getValueAt(i, 3).toString();
    String hariss = tabels.getValueAt(i, 4).toString();
    String jamss = tabels.getValueAt(i, 5).toString();

    form_pproduk.idpro.setText(id);
    form_pproduk.nam.setText(namapro);
    form_pproduk.jen.setSelectedItem(jenis); // Menggunakan setSelectedItem untuk memilih item berdasarkan nilai yang diterima
    form_pproduk.har.setText(harga);
    form_pproduk.haris.setText(hariss);
    form_pproduk.jams.setText(jamss);
    dispose();
    }//GEN-LAST:event_seditActionPerformed

    private void shapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapusActionPerformed
    int row = tabels.getSelectedRow();

    // Pastikan ada baris yang dipilih
    if (row == -1) {
            pilbar.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
            Timer timerCorrect = new Timer(1000, (e) -> {
                pilbar.setVisible(false);
                close.setVisible(false);
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
        return;
    }

    // Dapatkan ID Produk dari kolom pertama (indeks 0)
    int idProduk = Integer.parseInt(tabels.getValueAt(row, 0).toString());
    
    try {
        // Hapus data dari database
        String query = "DELETE FROM produk WHERE id_produk = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idProduk);
            pstmt.executeUpdate();
        }

        // Hapus baris dari tabel
        DefaultTableModel model = (DefaultTableModel) tabels.getModel();
        model.removeRow(row);

        // Update jumlah data
        int rowCount = model.getRowCount();
        //bagro.setText("Jumlah Data: " + rowCount);
            samberhasils.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
        Timer timerCorrect = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                samberhasils.setVisible(false);
                close.setVisible(false);
            }
            });
            timerCorrect.setRepeats(false); // Setel agar timer hanya berjalan sekali
            timerCorrect.start();
    } catch (Exception e) {
            galhap.setVisible(true);
            close.setVisible(true);
            // Close the correct JLabel after 3 seconds
    // Create a new Timer with an anonymous inner class for the ActionListener
    Timer timerIncorrect = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide the message and close button after 3 seconds
            galhap.setVisible(false);
            close.setVisible(false);
        }
    });
    timerIncorrect.setRepeats(false); // Set to run only once
    timerIncorrect.start(); // Start the timer

    }
    }//GEN-LAST:event_shapusActionPerformed

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
                String kataKunci = caris.getText();
        cariData(kataKunci);
    }//GEN-LAST:event_carisActionPerformed

    private void tabelsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsMouseClicked

    private void tambasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambasMouseClicked
        // TODO add your handling code here:
            if (mainInstance != null) {
        mainInstance.showFormOwner(new frame.tambah());
    } else {
        System.out.println("Error: Objek Main belum ditetapkan");
    }
    }//GEN-LAST:event_tambasMouseClicked

    private void carisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusLost
        // TODO add your handling code here:
               if(caris.getText().equals("")) {
            caris.setText(" Search...");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusLost

    private void carisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusGained
        // TODO add your handling code here:
                if(caris.getText().equals(" Search...")) {
            caris.setText("");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusGained

    private void refressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refressMouseClicked
        // TODO add your handling code here:
        refreshTable();
    }//GEN-LAST:event_refressMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bagro;
    private javax.swing.JTextField caris;
    private javax.swing.JLabel close;
    private javax.swing.JLabel galhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pilbar;
    private javax.swing.JLabel pildul;
    private javax.swing.JPanel refress;
    private javax.swing.JLabel samberhasils;
    private javax.swing.JButton sedit;
    private javax.swing.JButton shapus;
    public static javax.swing.JTable tabels;
    private javax.swing.JPanel tambas;
    // End of variables declaration//GEN-END:variables
}
