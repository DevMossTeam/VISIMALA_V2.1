package Deposit;

import static com.mysql.cj.jdbc.JdbcStatement.MAX_ROWS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mainOwner.MainOwner;
import mainKaryawan.MainKaryawan;


public class RiwayatDeposit extends javax.swing.JPanel {
    private DefaultTableModel tableModel;
    private static final int MAX_ROWS = 500;

    public RiwayatDeposit() {
        initComponents();
        loadDataToTable();
    }
    
   private void loadDataToTable() {
        tableModel = new DefaultTableModel();
    tableModel.addColumn("Waktu Deposit");
    tableModel.addColumn("Nama");
    tableModel.addColumn("Keterangan");
    tableModel.addColumn("Saldo");
   
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
        String query = "SELECT waktuDepo, nama, keterangan, saldo FROM riwayat_deposit";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        int rowCount = 0; // Tambahkan variabel rowCount untuk menghitung jumlah baris
        while (rs.next()) {
            String waktuDepo = rs.getString("waktuDepo");
            String nama = rs.getString("nama");
            String keterangan = rs.getString("Keterangan");
            int saldo = rs.getInt("saldo");

            tableModel.addRow(new Object[]{waktuDepo, nama, keterangan, saldo});
            rowCount++; // Tambahkan 1 ke rowCount setiap kali baris ditambahkan
        }

        rs.close();
        stmt.close();
        conn.close();

        // Hapus data yang melebihi batas maksimum jika ada
        while (rowCount > MAX_ROWS) {
            removeOldestData();
            rowCount--;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat data dari database");
    }

    tableR.setModel(tableModel);

    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    tableR.setRowSorter(sorter);
    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
}
   
   private void removeOldestData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
            String query = "DELETE FROM riwayat_deposit ORDER BY waktuDepo ASC LIMIT 1";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menghapus data tertua dari database");
        }

        tableModel.removeRow(0);
    }
   
    private void addDataToTable(String waktuDepo, String nama, String keterangan, int saldo) {
        tableModel.addRow(new Object[]{waktuDepo, nama, keterangan, saldo});

        if (tableModel.getRowCount() > MAX_ROWS) {
            removeOldestData();
        }
    }

   
   

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableR = new javax.swing.JTable();
        btn_kembali = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableR.setModel(new javax.swing.table.DefaultTableModel(
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
        tableR.setRowHeight(25);
        jScrollPane1.setViewportView(tableR);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 910, 410));

        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (38).png"))); // NOI18N
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 44, -1, -1));

        lbl_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/search.png"))); // NOI18N
        add(lbl_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(925, 53, -1, -1));

        txt_search.setBackground(new java.awt.Color(74, 74, 74));
        txt_search.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txt_search.setForeground(new java.awt.Color(255, 255, 255));
        txt_search.setBorder(null);
        txt_search.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 180, 26));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/riwayatDeposit.png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked

        MainOwner mainDepo = (MainOwner) SwingUtilities.getWindowAncestor(this);
        mainDepo.showFormOwner(new Deposit.TableDeposit(mainDepo));
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
     String searchText = txt_search.getText();

        // Buat sorter untuk tabel
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) tableR.getModel());
        tableR.setRowSorter(rowSorter);

        // Atur filter berdasarkan teks pencarian
        try {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        } catch (PatternSyntaxException ex) {
            // Jika terjadi kesalahan dalam pola regex
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid search pattern.");
        }

    }//GEN-LAST:event_txt_searchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable tableR;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
