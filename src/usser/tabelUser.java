
package usser;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import mainOwner.MainOwner;
import notifikasi.notifGagal;
import notifikasi.notifKonfirm;
import raven.glasspanepopup.GlassPanePopup;
import tabel_aksi.PanelAction;
import tabel_aksi.TableActionCellEditor;
import tabel_aksi.TableActionCellRender;
import tabel_aksi.TableActionEvent;
import tabel_aksi.TableCustomizer;

/**
 *
 * @author YAVIE
 */
public class tabelUser extends javax.swing.JPanel {
    private Connection conn; // java.sql.Connection;
    private Statement stm; // java.sql.Statement;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res; // java.sql.ResultSet;
    
private MainOwner mainUser; // Referensi ke objek Main

public tabelUser(MainOwner mainUser) {
    this.mainUser = mainUser; // Inisialisasi mainInstance dengan objek Main yang diterima
    initComponents();    
    setTambasButtonListener();
    tabel();
    refreshTable();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 4);

}

private void tabel() {
        TableCustomizer.customizeTable(tb_user);
       
        
    try (Connection conn = koneksi.configDB();
         Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("SELECT user.id_user, user.Username, user.password, user.jabatan, user.email, user.No_Hp, user.alamat, rfid.RFID_tag " +
        "FROM user " +
        "LEFT JOIN rfid ON user.id_user = rfid.id_user")){

      DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 7 || columnIndex == 8 ? PanelAction.class : super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7 || column == 8;
            }
        }; // Membuat model tabel dengan kolom yang sesuai
        model.addColumn("Id User");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Jabatan");
        model.addColumn("Email");
        model.addColumn("No HP");
        model.addColumn("Alamat");
        model.addColumn("RFID Tag");
        model.addColumn("Action");

        while (res.next()) {
                model.addRow(new Object[]{
                        res.getString("id_user"),
                        res.getString("Username"),
                        res.getString("password"),
                        res.getString("jabatan"),
                        res.getString("email"),
                        res.getString("No_Hp"),
                        res.getString("alamat"),
                        res.getString("RFID_tag"), // Tambahkan RFID Tag ke dalam model
                        new PanelAction()
                });
        }
        tb_user.setModel(model);
        tb_user.getTableHeader().setReorderingAllowed(false);
        tb_user.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        tb_user.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                setEditButtonListener();
            }

            @Override
            public void onDelete(int row) {
                try {
                    hapusData();
                } catch (SQLException ex) {
                    Logger.getLogger(tabelUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override 
            public void onView(int row) {
            showDetailUser();
            }
        }));
            tb_user.getColumnModel().getColumn(8).setPreferredWidth(90); // Lebar preferensi
    } catch (SQLException e) {
          JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

    public  void refreshTable() {
    DefaultTableModel model = (DefaultTableModel) tb_user.getModel();
    model.setRowCount(0);
    tabel();
}
    
private void setEditButtonListener() {
    int selectedRow = tb_user.getSelectedRow();
    if (selectedRow >= 0) { // Memeriksa apakah ada baris yang dipilih
        usser.editUser editUser = new usser.editUser();
        mainUser.showFormOwner(editUser); // Navigasi ke panel editUser

        String id = tb_user.getValueAt(selectedRow, 0).toString();
        String username = tb_user.getValueAt(selectedRow, 1).toString();
        String password = tb_user.getValueAt(selectedRow, 2).toString();
        String jabatan = tb_user.getValueAt(selectedRow, 3).toString();
        String email = tb_user.getValueAt(selectedRow, 4).toString();
        String nohp = tb_user.getValueAt(selectedRow, 5) != null ? tb_user.getValueAt(selectedRow, 5).toString() : "";
        String alamat = tb_user.getValueAt(selectedRow, 6) != null ? tb_user.getValueAt(selectedRow, 6).toString() : "";
        String RFID = tb_user.getValueAt(selectedRow, 7) != null ? tb_user.getValueAt(selectedRow, 7).toString() : "";

        editUser.labelRFID.setText(RFID);
        if (RFID.isEmpty()) {
            editUser.labelRFID.setVisible(false);
            editUser.addRFID.setVisible(true);
            editUser.deleteRFID.setVisible(false);
        } else {
            editUser.labelRFID.setVisible(true);
            editUser.addRFID.setVisible(false);
            editUser.deleteRFID.setVisible(true);
        }

        byte[] imageData = getImageDataFromDatabase(id);
        
        editUser.displayProfileIEdit(imageData);
        editUser.iduser.setText(id);
        editUser.txt_nama.setText(username);
        editUser.txt_password.setText(password);
        editUser.txt_email.setText(email);
        editUser.txt_nohp.setText(nohp);
        editUser.txt_alamat.setText(alamat);
        editUser.cbox_jabatan.setText(jabatan);
        editUser.labelRFID.setText(RFID);
    } else {
       GlassPanePopup.showPopup(new notifGagal("Pilih baris yang akan diedit!"));
    }
}

private byte[] getImageDataFromDatabase(String id) {
    byte[] imageData = null;

    String query = "SELECT profile_pic FROM user WHERE id_user = ?";
    try (Connection conn = koneksi.configDB();
         PreparedStatement pstm = conn.prepareStatement(query)) {

        pstm.setString(1, id);

        try (ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                Blob blob = rs.getBlob("profile_pic");
                if (blob != null) {
                    imageData = blob.getBytes(1, (int) blob.length());
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        Logger.getLogger(tabelUser.class.getName()).log(Level.SEVERE, null, e);
    }
    return imageData;
}



private void showDetailUser() {
    int selectedRow = tb_user.getSelectedRow();
    if (selectedRow >= 0) { 
        usser.detailUser detailUser = new usser.detailUser();
        GlassPanePopup.showPopup(new detailUser());

        String id = tb_user.getValueAt(selectedRow, 0).toString();
        String username = tb_user.getValueAt(selectedRow, 1).toString();
        String password = tb_user.getValueAt(selectedRow, 2).toString();
        String jabatan = tb_user.getValueAt(selectedRow, 3).toString();
        String email = tb_user.getValueAt(selectedRow, 4).toString();
        String nohp = tb_user.getValueAt(selectedRow, 5) != null ? tb_user.getValueAt(selectedRow, 5).toString() : "";
        String alamat = tb_user.getValueAt(selectedRow, 6) != null ? tb_user.getValueAt(selectedRow, 6).toString() : "";
        String RFID = tb_user.getValueAt(selectedRow, 7) != null ? tb_user.getValueAt(selectedRow, 7).toString() : "";
        
        byte[] imageData = getImageDataFromDatabase(id);
        detailUser.displayProfilDetail(imageData);

        detailUser.id_user.setText(id);
        detailUser.d_username.setText(username);
        detailUser.d_password.setText(password);
        detailUser.d_email.setText(email);
        detailUser.d_nohp.setText(nohp);
        detailUser.d_alamat.setText(alamat);
        detailUser.d_jabatan.setText(jabatan);
        detailUser.d_rfid.setText(RFID);

    } else {
         GlassPanePopup.showPopup(new notifGagal("Pilih baris yang akan diedit!"));
    }
}





private void hapusData() throws SQLException {
    int row = tb_user.getSelectedRow();
    if (row == -1) {
        return;
    }
   String idUser = tb_user.getValueAt(row, 0).toString();
   GlassPanePopup.showPopup(new notifKonfirm("Apakah kamu ingin menghapus data ini? ", "DELETE FROM user WHERE id_user = ?", idUser));
}


private void setTambasButtonListener() {
    TambahUs.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (mainUser != null) {
                mainUser.showFormOwner(new tambahUser());
            } else {
                System.out.println("main belum diterapkan");
            }
        }
    });
}


private void cariData(String keyword) {
    DefaultTableModel model = (DefaultTableModel) tb_user.getModel();
    model.setRowCount(0); // Menghapus semua baris dari model tabel
    try { 
        conn = koneksi.configDB();
        // Adjust the SQL query to join with the rfid table
        String sql = "SELECT u.id_user, u.Username, u.password, u.jabatan, u.email, u.No_Hp, u.alamat, r.RFID_tag " +
                     "FROM user u " +
                     "LEFT JOIN rfid r ON u.id_user = r.id_user " +
                     "WHERE u.id_user LIKE ? OR u.Username LIKE ? OR u.password LIKE ? OR u.jabatan LIKE ? OR u.email LIKE ? OR u.No_Hp LIKE ? OR u.alamat LIKE ? OR r.RFID_tag LIKE ?";
        pstm = conn.prepareStatement(sql);
        for (int i = 1; i <= 8; i++) {
            pstm.setString(i, "%" + keyword + "%");
        }
        res = pstm.executeQuery(); 
        while (res.next()) {
            Object[] rowData = new Object[]{              
                res.getString("id_user"),
                res.getString("Username"),
                res.getString("password"),
                res.getString("jabatan"),
                res.getString("email"),
                res.getString("No_Hp"),
                res.getString("alamat"),
                res.getString("RFID_tag") // Add RFID_tag to the row data
            };
            model.addRow(rowData); 
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

    private void addSearchListener() {
        txt_cari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String keyword = txt_cari.getText().trim();
                cariData(keyword);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String keyword = txt_cari.getText().trim();
                cariData(keyword);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String keyword = txt_cari.getText().trim();
                cariData(keyword);
                 }
            });
          }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tb_user = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_cari = new javax.swing.JTextField();
        refresh = new javax.swing.JLabel();
        TambahUs = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));
        setMinimumSize(new java.awt.Dimension(1023, 646));
        setPreferredSize(new java.awt.Dimension(1023, 646));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_user.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_user.setRowHeight(25);
        jScrollPane2.setViewportView(tb_user);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 940, 350));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/users.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 60));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel5.setText("Tabel User");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        txt_cari.setBackground(new java.awt.Color(74, 74, 74));
        txt_cari.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txt_cari.setForeground(new java.awt.Color(255, 255, 255));
        txt_cari.setText("Search...");
        txt_cari.setBorder(null);
        txt_cari.setSelectedTextColor(new java.awt.Color(255, 255, 255));
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
        add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 165, 25));

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/refresh (1).png"))); // NOI18N
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
        });
        add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        TambahUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/plus (3).png"))); // NOI18N
        TambahUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahUsMouseClicked(evt);
            }
        });
        add(TambahUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainTabel.png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed

    }//GEN-LAST:event_txt_cariActionPerformed

    private void txt_cariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cariFocusLost
txt_cari.setText("Search...");
    }//GEN-LAST:event_txt_cariFocusLost

    private void txt_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cariFocusGained
  addSearchListener();
txt_cari.setText("");
    }//GEN-LAST:event_txt_cariFocusGained

    private void TambahUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahUsMouseClicked
        if (mainUser != null) {
            mainUser.showFormOwner(new usser.tambahUser());
        } else {
            System.out.println("Error: Objek Main belum ditetapkan");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_TambahUsMouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked
           refreshTable();
    }//GEN-LAST:event_refreshMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TambahUs;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel refresh;
    private javax.swing.JTable tb_user;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
