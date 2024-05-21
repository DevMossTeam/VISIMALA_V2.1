package Deposit;

import static groovy.ui.text.FindReplaceUtility.dispose;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.PatternSyntaxException;
import javax.swing.table.TableRowSorter;
import notifikasi.notifGagal;
import raven.glasspanepopup.GlassPanePopup;


public class TableDeposit extends javax.swing.JPanel {
    private mainOwner.MainOwner mainInstance;
    private mainKaryawan.MainKaryawan mainInstance1;
    private mainOwner.MainOwner mainDepo;
  
    private JTable table_deposit;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    
    private Connection con;
    private Statement stat;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res;


    public TableDeposit(mainOwner.MainOwner mainDepo ) {
      this.mainDepo = mainDepo;
        initComponents();
         loadDataToTable();
 
              
    }
    
    private void loadDataToTable() {
    tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Deposit");
        tableModel.addColumn("Nama");
        tableModel.addColumn("Nomor HP");
        tableModel.addColumn("Saldo");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "");
            String query = "SELECT d.id_depo, p.nama, p.No_Hp, d.jumlah_depo " +
                           "FROM deposit d " +
                           "JOIN pelanggan p ON d.kode_pelanggan = p.kode_pelanggan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String idDepo = rs.getString("id_depo");
                String nama = rs.getString("nama");
                String nomorHp = rs.getString("No_Hp");
                int saldo = rs.getInt("jumlah_depo");

                tableModel.addRow(new Object[]{idDepo, nama, nomorHp, saldo});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data dari database");
        }

        table.setModel(tableModel);
    }
    
    private void startDeletionTimer(String idDepo) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_laundryku", "root", "")) {
                    String query = "DELETE FROM deposit WHERE id_depo = ? AND jumlah_depo = 0";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, idDepo);
                        int affectedRows = stmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Entry with id_depo " + idDepo + " has been deleted.");
                            loadDataToTable();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 1 * 60 * 1000); // 5 minutes
    }
  



    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        history = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        btn_topUp = new javax.swing.JButton();
        btn_daftar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        history.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/history 1.png"))); // NOI18N
        history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyMouseClicked(evt);
            }
        });
        add(history, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, -1, -1));

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

        btn_topUp.setBorder(null);
        btn_topUp.setContentAreaFilled(false);
        btn_topUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_topUpActionPerformed(evt);
            }
        });
        add(btn_topUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 520, 150, 40));

        btn_daftar.setBorder(null);
        btn_daftar.setContentAreaFilled(false);
        btn_daftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_daftarActionPerformed(evt);
            }
        });
        add(btn_daftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 520, 150, 40));

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        table.setRowHeight(25);
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 910, 370));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deposit/TableDeposit.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_topUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_topUpActionPerformed

    int i = table.getSelectedRow();
    if (i != -1) {
     mainDepo.showFormOwner(new Deposit.TopUpDeposit());
    } else {
         GlassPanePopup.showPopup(new notifGagal("Pilih baris terlebih dahulu!"));
        return; 
    }
    String id = table.getValueAt(i, 0).toString();
    String nama = table.getValueAt(i, 1).toString();
    String nomorHp = table.getValueAt(i, 2).toString();
 

//    TopUpDeposit.id.setText(id);
    TopUpDeposit.txt_nama.setText(nama);
    TopUpDeposit.txt_NomorHp.setText(nomorHp); 
  
    dispose();

    }//GEN-LAST:event_btn_topUpActionPerformed

    private void btn_daftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daftarActionPerformed
       if (mainDepo != null) {
            mainDepo.showFormOwner(new Deposit.DaftarDeposit());
        } else {
            System.out.println("Error: Objek Main belum ditetapkan");
        }
    }//GEN-LAST:event_btn_daftarActionPerformed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
     // Ambil teks dari kotak pencarian
        String searchText = txt_search.getText();

        // Buat sorter untuk tabel
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(rowSorter);

        // Atur filter berdasarkan teks pencarian
        try {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        } catch (PatternSyntaxException ex) {
            // Jika terjadi kesalahan dalam pola regex
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid search pattern.");
        }
    

    }//GEN-LAST:event_txt_searchActionPerformed

    private void historyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyMouseClicked
       if (mainDepo != null) {
            mainDepo.showFormOwner(new Deposit.RiwayatDeposit());
        } else {
            System.out.println("Error: Objek Main belum ditetapkan");
        }
    }//GEN-LAST:event_historyMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_daftar;
    private javax.swing.JButton btn_topUp;
    private javax.swing.JLabel history;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
