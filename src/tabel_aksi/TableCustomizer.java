package tabel_aksi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class TableCustomizer {
    public static void customizeTable(JTable table) {
        // Setel tinggi baris
        table.setRowHeight(30);
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
                Color customColor = new Color(255, 255, 255);
                if (!isSelected && row % 2 == 0) {
                    com.setBackground(customColor);
                } else {
                    com.setBackground(jtable.getBackground());
                }
                return com;
            }
        };

        // Atur renderer khusus untuk setiap kolom
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JViewport viewport = (JViewport) table.getParent(); // JViewport merupakan latar belakang paling belakang di JTable
        viewport.setBackground(new Color(231, 231, 231));

        // Atur ukuran header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(100, 35)); // Sesuaikan dimensi header jika diperlukan

        // Atur warna dan font untuk header
        // header.setBackground(new Color(0, 102, 204)); // Ganti warna header
        // header.setForeground(Color.WHITE); // Ganti warna teks header
        header.setFont(new Font("Arimo", Font.BOLD, 13)); // Ganti jenis dan ukuran font header

        // Set rata tengah (centered) untuk semua sel di tabel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        // Atur ukuran dan warna garis vertikal
        table.setGridColor(new Color(217, 217, 217)); // Ganti warna garis vertikal
        table.setShowVerticalLines(true); // Tampilkan garis vertikal
    }
}
