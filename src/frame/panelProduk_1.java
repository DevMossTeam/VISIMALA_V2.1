/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;


/**
 *
 * @author YAVIE
 */
public class panelProduk_1 extends javax.swing.JPanel {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    
    public panelProduk_1() {
        koneksi();
        initComponents();
        kosongkan();
        tabel();

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
        haris.setText("");
        jams.setText("");
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
                    res.getString("hari") + " Hari",
                    res.getString("jam") + " Jam"                    
                });

                // Set cell renderer for the first row only
                if (rowCount == 0) {
                    tabels.getColumnModel().getColumn(0).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(2).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(3).setCellRenderer(new CustomTableCellRenderer());
                    tabels.getColumnModel().getColumn(4).setCellRenderer(new CustomTableCellRenderer());
                    
                }

                rowCount++;

                // Update JLabel to display the current row count
                jLabel1.setText("Jumlah Data: " + rowCount);
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jams = new javax.swing.JTextField();
        comjenispro = new javax.swing.JComboBox<>();
        nampro = new javax.swing.JTextField();
        harpro = new javax.swing.JTextField();
        haris = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        ssimpan = new javax.swing.JButton();
        sedit = new javax.swing.JButton();
        caris = new javax.swing.JTextField();
        sclear = new javax.swing.JButton();
        shapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (28).png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (30).png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 530, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel4.setText("Table Produk");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel5.setText("Form Produk");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (30).png"))); // NOI18N
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 280, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Harga Produk");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Jenis Produk");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Batas Waktu");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Nama Produk");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        jams.setText("  Jam . . .");
        jams.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jamsActionPerformed(evt);
            }
        });
        add(jams, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 80, 30));

        comjenispro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kilogram", "Satuan", " " }));
        comjenispro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        add(comjenispro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 220, 30));

        nampro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        nampro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namproActionPerformed(evt);
            }
        });
        add(nampro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 220, 30));

        harpro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        harpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harproActionPerformed(evt);
            }
        });
        add(harpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 220, 30));

        haris.setText("  Hari . . .");
        haris.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        haris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harisActionPerformed(evt);
            }
        });
        add(haris, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 80, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText("/");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, -1, -1));

        ssimpan.setBackground(new java.awt.Color(10, 172, 42));
        ssimpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ssimpan.setForeground(new java.awt.Color(255, 255, 255));
        ssimpan.setText("Simpan");
        ssimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssimpanActionPerformed(evt);
            }
        });
        add(ssimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 90, -1));

        sedit.setBackground(new java.awt.Color(255, 153, 0));
        sedit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sedit.setForeground(new java.awt.Color(255, 255, 255));
        sedit.setText("Edit");
        sedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seditActionPerformed(evt);
            }
        });
        add(sedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 530, 90, -1));

        caris.setBackground(new java.awt.Color(89, 77, 77));
        caris.setForeground(new java.awt.Color(255, 255, 255));
        caris.setText("  Search");
        caris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carisActionPerformed(evt);
            }
        });
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 72, 170, 30));

        sclear.setBackground(new java.awt.Color(11, 146, 215));
        sclear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sclear.setForeground(new java.awt.Color(255, 255, 255));
        sclear.setText("Clear");
        sclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sclearActionPerformed(evt);
            }
        });
        add(sclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 530, 90, -1));

        shapus.setBackground(new java.awt.Color(204, 0, 0));
        shapus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        shapus.setForeground(new java.awt.Color(255, 255, 255));
        shapus.setText("Hapus");
        shapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapusActionPerformed(evt);
            }
        });
        add(shapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 530, 90, -1));

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
        jScrollPane1.setViewportView(tabels);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 530, 320));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/Desain tanpa judul (20).png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frame/KK (2).png"))); // NOI18N
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jamsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamsActionPerformed

    private void namproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namproActionPerformed

    private void harproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harproActionPerformed

    private void harisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harisActionPerformed

    private void ssimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssimpanActionPerformed
    try {
        // Validate inputs
        String namaProduk = nampro.getText().trim();
        String hargaProduk = harpro.getText().trim();
        String BwHari = haris.getText().trim();
        String BwJam =jams.getText().trim();

        // Ensure that hargaProduk is a valid number
        try {
            double hargaValue = Double.parseDouble(hargaProduk);
            if (hargaValue < 0) {
                JOptionPane.showMessageDialog(null, "Harga Produk tidak boleh negatif");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai");
            return; // Return to stop further execution
        }

        // Validate BwJam
        int jamValue;
        try {
            jamValue = Integer.parseInt(BwJam);
            if (jamValue < 0 || jamValue > 24) {
                JOptionPane.showMessageDialog(null, "Batas Jam harus berada dalam rentang 0-24");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai");
            return; // Return to stop further execution
        }

        // Continue with the remaining validation and database checks...

        if (hargaProduk.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harga Produk tidak boleh kosong");
            return;
        }

        if (namaProduk.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama Produk tidak boleh kosong");
            return;
        }
        

        // Check if the product name already exists in the database
        if (isNamaProdukExists(namaProduk, t)) {
            JOptionPane.showMessageDialog(null, "Nama Produk sudah ada. Silakan pilih nama yang lain.");
            return; // Prevent further execution
        }

        if (t == null) {
            // Insert new data
            String insertQuery = "INSERT INTO produk(nama_produk, harga_produk, jenis_produk, hari, jam) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPstmt.setString(1, namaProduk);
                insertPstmt.setString(2, hargaProduk);
                insertPstmt.setString(3, comjenispro.getSelectedItem().toString());
                insertPstmt.setString(4, BwHari);
                insertPstmt.setString(5, BwJam);

                int affectedRows = insertPstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = insertPstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idProduk = generatedKeys.getInt(1);
                            System.out.println("ID Produk: " + idProduk);

                            DefaultTableModel model = (DefaultTableModel) tabels.getModel();
                            model.addRow(new Object[]{
                                    idProduk,
                                    namaProduk,
                                    hargaProduk,
                                    comjenispro.getSelectedItem().toString(),
                                    BwHari + " Hari", // Display " Hari"
                                    BwJam + " Jam"   // Display " Jam"
                            });

                            // Update jumlah data
                            int rowCount = model.getRowCount();
                    jLabel1.setText("Jumlah Data: " + rowCount);

                    // Clear UI components and show success message
                                kosongkan();
                                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                            }
                        }
                    } else {
                        // Data insertion failed
                        JOptionPane.showMessageDialog(null, "Data gagal disimpan");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Data gagal disimpan");
                }
            } else {
            // Update existing data
            String updateQuery = "UPDATE produk SET nama_produk=?, harga_produk=?, jenis_produk=?, hari=?, jam=? WHERE id_produk=?";
            try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
                updatePstmt.setString(1, namaProduk);
                updatePstmt.setString(2, hargaProduk);
                updatePstmt.setString(3, comjenispro.getSelectedItem().toString());
                updatePstmt.setString(4, BwHari);
                updatePstmt.setString(5, BwJam);
                updatePstmt.setString(6, t);

                int affectedRows = updatePstmt.executeUpdate();

                if (affectedRows > 0) {
                    DefaultTableModel model = (DefaultTableModel) tabels.getModel();
                    int selectedRowIndex = tabels.getSelectedRow();

                    // Update the existing row in the table model
                    if (selectedRowIndex != -1) {
                        model.setValueAt(namaProduk, selectedRowIndex, 1);
                        model.setValueAt(hargaProduk, selectedRowIndex, 2);
                        model.setValueAt(comjenispro.getSelectedItem().toString(), selectedRowIndex, 3);
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
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Perintah SALAH, Masukkan Data Yang Sesuai ! ");
    }
}

// Method to check if the product name already exists in the database
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
    }//GEN-LAST:event_ssimpanActionPerformed
private void tampilkanDataBerdasarkanID(int idProduk) {
    try {
        res = stat.executeQuery("SELECT * FROM produk WHERE id_produk='" + idProduk + "'");
        while (res.next()) {
            nampro.setText(res.getString("nama_produk"));
            harpro.setText(res.getString("harga_produk"));
            comjenispro.setSelectedItem(res.getString("jenis_produk"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    private void seditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seditActionPerformed
   try {
        // Get the selected row index
        int selectedRow = tabels.getSelectedRow();

        // Ensure a row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to edit");
            return;
        }

        // Get the ID from the first column (assuming it's the ID column)
        int idProduk = Integer.parseInt(tabels.getValueAt(selectedRow, 0).toString());

        // Fetch data from the database based on the ID
        String query = "SELECT * FROM produk WHERE id_produk = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idProduk); // Set the ID parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Display data in JTextFields
                nampro.setText(rs.getString("nama_produk"));
                harpro.setText(rs.getString("harga_produk"));
                comjenispro.setSelectedItem(rs.getString("jenis_produk"));
                haris.setText(rs.getString("hari"));
                jams.setText(rs.getString("jam"));

                // Save the ID for later use (in btn_simpanActionPerformed)
                t = Integer.toString(idProduk);
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Error while fetching data for editing");
    }
    }//GEN-LAST:event_seditActionPerformed

    private void shapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapusActionPerformed
    int row = tabels.getSelectedRow();

    // Pastikan ada baris yang dipilih
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
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
        jLabel1.setText("Jumlah Data: " + rowCount);

        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus data: " );
    }
    }//GEN-LAST:event_shapusActionPerformed

    private void sclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sclearActionPerformed
      kosongkan();
    }//GEN-LAST:event_sclearActionPerformed

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
        String kataKunci = caris.getText();
        cariData(kataKunci);
    }//GEN-LAST:event_carisActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caris;
    private javax.swing.JComboBox<String> comjenispro;
    private javax.swing.JTextField haris;
    private javax.swing.JTextField harpro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jams;
    private javax.swing.JTextField nampro;
    private javax.swing.JButton sclear;
    private javax.swing.JButton sedit;
    private javax.swing.JButton shapus;
    private javax.swing.JButton ssimpan;
    private javax.swing.JTable tabels;
    // End of variables declaration//GEN-END:variables
}
