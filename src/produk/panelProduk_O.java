
package produk;

import static groovy.ui.text.FindReplaceUtility.dispose;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import mainOwner.MainOwner;
import notifikasi.notifGagal;
import notifikasi.notifSukses;
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
public class panelProduk_O extends javax.swing.JPanel {
   private Connection conn; // java.sql.Connection;
    private Statement stm; // java.sql.Statement;
    private PreparedStatement pstm; //java.sql.PreparedStatement;
    private ResultSet res; // java.sql.ResultSet;
    private MainOwner mainInstance; 
    
    public panelProduk_O(MainOwner mainInstance) {
        this.mainInstance = mainInstance; 
        initComponents();
        
        caris.setText(" Search...");
        caris.setForeground(new Color (255,255,255));
        
        setEditButtonListener();
        setTambasButtonListener();
        
        tabel();      
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 4);
    }

    private void tabel() {
    TableCustomizer.customizeTable(tabels);
    
   DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? PanelAction.class : super.getColumnClass(columnIndex);
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 6;
            }
        };
    model.addColumn("Id Produk");
    model.addColumn("Nama Produk");
    model.addColumn("Harga Produk"); 
    model.addColumn("Jenis Produk");
    model.addColumn("Batas Hari");
    model.addColumn("Batas Jam");
    model.addColumn("Action");
    try {
        model.setRowCount(0); 
        conn = koneksi.configDB();
        stm = conn.createStatement();
        String sql = "SELECT id_produk, nama_produk, harga_produk, jenis_produk, hari, jam FROM produk";
        res = stm.executeQuery(sql);{
         while (res.next()) {
         model.addRow(new Object[]{
                        res.getString("id_produk"),
                        res.getString("nama_produk"),
                        res.getString("harga_produk"),
                        res.getString("jenis_produk"),
                        res.getString("hari"),
                        res.getString("jam"),
                    new PanelAction()
                });
            }
            tabels.setModel(model);
            tabels.getTableHeader().setReorderingAllowed(false);
            tabels.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
            tabels.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(new TableActionEvent() {
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
//                    int barisTerpilih = tabels.getSelectedRow();
//                    String idproduk = tb_user.getValueAt(barisTerpilih, 0).toString();
//                    tampilkanDetailProduk( idproduk);
//                
                }
            }));         
        }
         tabels.getColumnModel().getColumn(6).setPreferredWidth(60); // Lebar preferensi
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data.");
    }
}
    public void refreshTable() {
    DefaultTableModel model = (DefaultTableModel) tabels.getModel();
    model.setRowCount(0);
    tabel(); 
}
    
private void setEditButtonListener() { 
    int i = tabels.getSelectedRow();
    if (i != -1) {
        mainInstance.showFormOwner(new produk.form_pproduk());
    } else {
        return; 
    }

    if (mainInstance != null) {
        mainInstance.showFormOwner(new produk.form_pproduk());
    } else {
        System.out.println("Error: Objek Main belum ditetapkan");
    }
    String id = tabels.getValueAt(i, 0).toString();
    String namapro = tabels.getValueAt(i, 1).toString();
    String harga = tabels.getValueAt(i, 2).toString();
    String jenis = tabels.getValueAt(i, 3).toString();
    String hariss = tabels.getValueAt(i, 4).toString();
    String jamss = tabels.getValueAt(i, 5).toString();

    form_pproduk.idpro.setText(id);
    form_pproduk.nam.setText(namapro);
    form_pproduk.jen.setSelectedItem(jenis); 
    form_pproduk.har.setText(harga);
    form_pproduk.haris.setText(hariss);
    form_pproduk.jams.setText(jamss);
    dispose();
}

private void hapusData() {
       int row = tabels.getSelectedRow();
    // Pildul barisnyaaaaaaaaa
    if (row == -1) {
     GlassPanePopup.showPopup(new notifGagal("Silahkan pilih baris terlebih dahulu "));
        return;
    }
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
if (option != JOptionPane.YES_OPTION) {
    return;
    }
    // Dapatkan ID Produk dari kolom pertama (indeks 0) kenapa 0? karena di tabel dia pertama
    String idProduk = tabels.getValueAt(row, 0).toString();
    try {
        // Hapus data dari database
        String query = "DELETE FROM produk WHERE id_produk = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idProduk);
            pstmt.executeUpdate();
        }
        // Hapus baris dari tabel
        DefaultTableModel model = (DefaultTableModel) tabels.getModel();
        model.removeRow(row);
     
         GlassPanePopup.showPopup(new notifSukses("Berhasil menghapus data "));
            int durationInMillis = 2000;
            Timer timer = new Timer(durationInMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GlassPanePopup.closePopupAll(); 
                }
            });
            timer.setRepeats(false);
            timer.start();
    } catch (Exception e) {
        GlassPanePopup.showPopup(new notifGagal("Gagal menghapus data "));
    }
}

private void setTambasButtonListener() {
    tambas.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (mainInstance != null) {
                mainInstance.showFormOwner(new produk.tambah());
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
            String query = "SELECT * FROM produk WHERE id_produk LIKE ? OR nama_produk LIKE ? OR harga_produk LIKE ? OR jenis_produk LIKE ? OR hari LIKE ? OR jam LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (int i = 1; i <= 6; i++) {
                    pstmt.setString(i, "%" + kataKunci + "%");
                }

                try (ResultSet res = pstmt.executeQuery()) {
                    while (res.next()) {
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

private void addSearchListener() {
    caris.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String keyword = caris.getText().trim();
            cariData(keyword);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String keyword = caris.getText().trim();
            cariData(keyword);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            String keyword = caris.getText().trim();
            cariData(keyword);
             }
        });
      }
    
//private void tampilkanDetailProduk(String idproduk) {
//    try {
//        // Query database untuk mendapatkan detail produk berdasarkan ID
//        String query = "SELECT * FROM produk WHERE id_produk = ?";
//        PreparedStatement stat = conn.prepareStatement(query);
//        stat.setString(1, idproduk);
//
//        ResultSet res = stat.executeQuery();
//        if (res.next()) {
//            String idProduk = res.getString("id_produk");
//            String namaProduk = res.getString("nama_produk");
//            String hargaProduk = res.getString("harga_produk");
//            String jenisProduk = res.getString("jenis_produk");
//            String batasHari = res.getString("hari");
//            String batasJam = res.getString("jam");
//
//            String detail = String.format("ID Produk: %s\nNama : %s\nHarga : %s\nJenis : %s\nBatas Hari : %s\nBatas Jam : %s",
//                    idProduk, namaProduk, hargaProduk, jenisProduk, batasHari, batasJam);
//
//            // Mendapatkan URL gambar dari resource
//            String imageName = res.getString("nama_file_gambar"); // Ganti "nama_file_gambar" dengan nama kolom yang benar
//            URL imageUrl = getClass().getResource("/produk/" + imageName + "icon.png");
//            if (imageUrl != null) {
//                ImageIcon icon = new ImageIcon(imageUrl);
//                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
//                ImageIcon scaledIcon = new ImageIcon(scaledImage);
//
//                // Tampilkan dialog pesan dengan gambar dan detail
//                JOptionPane.showMessageDialog(this, detail, "Detail Produk", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
//            } else {
//                JOptionPane.showMessageDialog(this, "Gambar tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Data Produk tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    } catch (SQLException e) {
//        JOptionPane.showMessageDialog(this, "Gagal menampilkan detail: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabels = new javax.swing.JTable();
        caris = new javax.swing.JTextField();
        refress = new javax.swing.JLabel();
        tambas = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(237, 237, 237));
        setPreferredSize(new java.awt.Dimension(1052, 689));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/Desain tanpa judul (28).png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 60));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel5.setText("Tabel Produk");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, 50));

        tabels.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Produk", "Nama Produk", "Harga Produk", "Jenis Produk", "Batas Hari", "Batas Jam", "Action"
            }
        ));
        tabels.setGridColor(new java.awt.Color(125, 151, 132));
        tabels.setRowHeight(25);
        tabels.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabels);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 940, 350));

        caris.setBackground(new java.awt.Color(74, 74, 74));
        caris.setForeground(new java.awt.Color(255, 255, 255));
        caris.setText("Search...");
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
        add(caris, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 170, 25));

        refress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/refresh (1).png"))); // NOI18N
        refress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refressMouseClicked(evt);
            }
        });
        add(refress, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        tambas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/produk/plus (3).png"))); // NOI18N
        tambas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambasMouseClicked(evt);
            }
        });
        add(tambas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usser/desainTabel.png"))); // NOI18N
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void carisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carisActionPerformed
//                String kataKunci = caris.getText();
//        cariData(kataKunci);
    }//GEN-LAST:event_carisActionPerformed

    private void tabelsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsMouseClicked

    private void carisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusLost
        // TODO add your handling code here:
               if(caris.getText().equals("")) {
            caris.setText(" Search...");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusLost

    private void carisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carisFocusGained
        addSearchListener();
            if(caris.getText().equals(" Search...")) {
            caris.setText("");
            caris.setForeground(new Color(255,255,255));
        }
    }//GEN-LAST:event_carisFocusGained

    private void tambasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambasMouseClicked
        // TODO add your handling code here:
        if (mainInstance != null) {
            mainInstance.showFormOwner(new produk.tambah());
        } else {
            System.out.println("Error: Objek Main belum ditetapkan");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tambasMouseClicked

    private void refressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refressMouseClicked
      refreshTable();        // TODO add your handling code here:
    }//GEN-LAST:event_refressMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caris;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel refress;
    public static javax.swing.JTable tabels;
    private javax.swing.JLabel tambas;
    // End of variables declaration//GEN-END:variables
}
