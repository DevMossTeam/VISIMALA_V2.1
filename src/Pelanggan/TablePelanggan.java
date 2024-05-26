package Pelanggan;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import koneksi.koneksi;

public class TablePelanggan extends javax.swing.JFrame {
    public TablePelanggan() {
        initComponents();
         TablePelanggan();
         
        txt_cari.setText("Cari");
        txt_cari.setForeground(new Color(255, 255, 255));
    }
    
    private void tabel() {
    tablepelanggan.setShowGrid(true);
    tablepelanggan.setShowHorizontalLines(true);
    tablepelanggan.setShowVerticalLines(true);
    }
    
private void TablePelanggan() {
    DefaultTableModel timeInModel = new DefaultTableModel();
    timeInModel.addColumn("ID");
    timeInModel.addColumn("Nama");
    timeInModel.addColumn("No Hp");
    timeInModel.addColumn("Alamat");

    String dataSql = "SELECT kode_pelanggan, nama, No_Hp, alamat FROM pelanggan";
    try (Connection conn = koneksi.configDB();
         PreparedStatement dataStmt = conn.prepareStatement(dataSql);
         ResultSet res = dataStmt.executeQuery()) {

        while (res.next()) {
            String noTransaksi = res.getString("kode_pelanggan");
            String namaPelanggan = res.getString("nama");
            String noHp = res.getString("No_Hp");
            String alamat = res.getString("alamat");

            // Insert each new row at the beginning of the table model
            timeInModel.insertRow(0, new Object[]{noTransaksi, namaPelanggan, noHp, alamat});
        }

        tablepelanggan.setModel(timeInModel);
        setTableAlignment(tablepelanggan);
        adjustColumnWidths(tablepelanggan);

        // Set header font
        JTableHeader header = tablepelanggan.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

    } catch (Exception e) {
        handleException("Error loading transaction history", e);
    }
}

    private void adjustColumnWidths(JTable table) {
    TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 15; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width + 1, width);
        }
        if (width > 300) {
            width = 300;
        }
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}

private void setTableAlignment(JTable table) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}

        private void handleException(String message, Exception e) {
        // Log the error message and exception stack trace
        System.err.println(message);
        e.printStackTrace();

        // Optionally, display an error message to the user
        JOptionPane.showMessageDialog(null, message + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tambah = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepelanggan = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        BG = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tambah.setBorder(null);
        tambah.setContentAreaFilled(false);
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        getContentPane().add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 40, 30));

        refresh.setBorder(null);
        refresh.setContentAreaFilled(false);
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        getContentPane().add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 50, 30));

        tablepelanggan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tablepelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tablepelanggan.setPreferredSize(new java.awt.Dimension(300, 100));
        tablepelanggan.setRowHeight(40);
        tablepelanggan.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tablepelanggan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 920, 390));

        txt_cari.setBackground(new java.awt.Color(74, 74, 74));
        txt_cari.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt_cari.setForeground(new java.awt.Color(255, 255, 255));
        txt_cari.setBorder(null);
        txt_cari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_cariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_cariFocusLost(evt);
            }
        });
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, 165, 30));

        BG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pelanggan/table.png"))); // NOI18N
        BG.setToolTipText("");
        getContentPane().add(BG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        //    MainPelanggan mainPelanggan = (MainPelanggan) SwingUtilities.getWindowAncestor(this);
        //    mainPelanggan.showFormPelanggan(new pelanggan.panel.DaftarPelanggan());
    }//GEN-LAST:event_tambahActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        TablePelanggan();
    }//GEN-LAST:event_refreshActionPerformed

    private void txt_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cariFocusGained
        if(txt_cari.getText().equals("Cari")) {
            txt_cari.setText("");
            txt_cari.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_txt_cariFocusGained

    private void txt_cariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cariFocusLost
        if(txt_cari.getText().isEmpty()) {
            txt_cari.setText("Cari");
            txt_cari.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_txt_cariFocusLost

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed

    }//GEN-LAST:event_txt_cariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tablepelanggan;
    private javax.swing.JButton tambah;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
