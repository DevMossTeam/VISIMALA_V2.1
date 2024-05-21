
package Pengeluaran;

import static groovy.ui.text.FindReplaceUtility.dispose;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import mainOwner.MainOwner;
import tabel_aksi.PanelAction;
import tabel_aksi.TableActionCellEditor;
import tabel_aksi.TableActionCellRender;
import tabel_aksi.TableActionEvent;
import tabel_aksi.TableCustomizer;


public class pengeluaran extends javax.swing.JPanel {

    private static int getSelectedRow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static Object getValueAt(int row, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   private Connection con;
    private Statement stat;
    private ResultSet res;
    private MainOwner mainInstance; // Referensi ke objek Main

    
    public pengeluaran(MainOwner mainInstance) {
         this.mainInstance = mainInstance; // Inisialisasi mainInstance dengan objek Main yang diterima
        initComponents();
        caris.setText(" Search...");
        caris.setForeground(new Color (255,255,255));
        koneksi();
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
    TableCustomizer.customizeTable(tabel);
    
   DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? PanelAction.class : super.getColumnClass(columnIndex);
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 4;
            }
        };
    model.addColumn("Id Pengeluaran");
    model.addColumn("Tanggal");
    model.addColumn("Keterangan"); 
    model.addColumn("Total");
    model.addColumn("Action");
    try {
        model.setRowCount(0); 
        con = koneksi.configDB();
        stat = con.createStatement();
          String sql = "SELECT id_pengeluaran, tgl_pengeluaran, keterangan, total_pengeluaran FROM pengeluaran";
        res = stat.executeQuery(sql);{
         while (res.next()) {
         model.addRow(new Object[]{
                        res.getString("id_pengeluaran"),
                        res.getString("tgl_pengeluaran"),
                        res.getString("keterangan"),
                        res.getString("total_pengeluaran"),
                    new PanelAction()
                });
            }
            tabel.setModel(model);
            tabel.getTableHeader().setReorderingAllowed(false);
            tabel.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
            tabel.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(new TableActionEvent() {
                @Override
                public void onEdit(int row) {
                setEditButtonListener();   
                }
                @Override
                public void onDelete(int row) {
                 hapusData();                    
                }
                @Override
                public void onView(int row) {    
                }
            }));         
        }
         tabel.getColumnModel().getColumn(4).setPreferredWidth(35); // Lebar preferensi
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data.");
    }
}  
  
private void hapusData(){
  int row = pengeluaran.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus");
            return;
        }
        int idPengeluaran = Integer.parseInt(pengeluaran.getValueAt(row, 0).toString());

        // Hapus data dari database
        try {
            String query = "DELETE FROM pengeluaran WHERE id_pengeluaran = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, idPengeluaran);
                pstmt.executeUpdate();
            }

            // Refresh tabel setelah penghapusan
            tabel();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
}

    public void refreshTable() {
    DefaultTableModel model = (DefaultTableModel) tabel.getModel();
    model.setRowCount(0); 
    tabel(); 
}
private void cariData(String kataKunci) {
    DefaultTableModel model = (DefaultTableModel) tabel.getModel();
    model.setRowCount(0); // Clear existing rows

    try {
        // Use prepared statement to prevent SQL injection
        String query = "SELECT * FROM pengeluaran WHERE id_pengeluaran LIKE ? OR tgl_pengeluaran LIKE ? OR keterangan LIKE ? OR total_pengeluaran LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + kataKunci + "%");
            }

            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    // Adjust column types based on actual data types
                    model.addRow(new Object[]{
                        res.getString("id_pengeluaran"),
                        res.getString("tgl_pengeluaran"),
                        res.getString("keterangan"),
                        res.getString("total_pengeluaran"),
                    });
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal mencari data");
    }
}

private void setEditButtonListener(){
     int i = tabel.getSelectedRow();
    if (i == -1) {
        return;
    }
        if (mainInstance != null) {
        mainInstance.showFormOwner(new Pengeluaran.FormPengeluaran());
    } else {
        System.out.println("Error: Objek Main belum ditetapkan");
    }
    // panggil data ketika klik edit ke halaman form_pproduk
    String kode = tabel.getValueAt(i, 0).toString();
    String tanggal = tabel.getValueAt(i, 1).toString();
    String ket = tabel.getValueAt(i, 2).toString();
    String total = tabel.getValueAt(i, 3).toString();
// nyambungin nya
    FormPengeluaran.kodesPeng.setText(kode);
    FormPengeluaran.Tanggal.setText(tanggal);
    FormPengeluaran.Keterangan.setText(ket);
    FormPengeluaran.Total.setText(total);
    dispose();
}
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        caris = new javax.swing.JTextField();
        refrash = new javax.swing.JLabel();
        Tambas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1023, 646));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabel);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 940, 350));

        caris.setBackground(new java.awt.Color(74, 74, 74));
        caris.setForeground(new java.awt.Color(255, 255, 255));
        caris.setText("Srearch...");
        caris.setBorder(null);
        caris.setCaretColor(new java.awt.Color(74, 74, 74));
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
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 43, 170, 20));

        refrash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/refresh (1).png"))); // NOI18N
        refrash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refrashMouseClicked(evt);
            }
        });
        add(refrash, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        Tambas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/plus (3).png"))); // NOI18N
        Tambas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambasMouseClicked(evt);
            }
        });
        add(Tambas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pengeluaran/icon.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 18, -1, 60));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel5.setText("Pengeluaran");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainTabel.png"))); // NOI18N
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
        String kataKunci = caris.getText();
        cariData(kataKunci);
    }//GEN-LAST:event_carisActionPerformed

    private void carisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusLost
             if(caris.getText().equals("")) {
            caris.setText(" Search...");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusLost

    private void carisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusGained
                if(caris.getText().equals(" Search...")) {
            caris.setText("");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusGained

    private void refrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refrashMouseClicked
   refreshTable(); 
    }//GEN-LAST:event_refrashMouseClicked

    private void TambasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambasMouseClicked
        if (mainInstance != null) {
            mainInstance.showFormOwner(new Pengeluaran.tambahData());
        } else {
            System.out.println("Error: Objek Main belum ditetapkan");
        }
    }//GEN-LAST:event_TambasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tambas;
    private javax.swing.JTextField caris;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel refrash;
    private javax.swing.JTable tabel;
    // End of variables declaration//GEN-END:variables

    

    
}
