package tabel_aksi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ActionButton extends JButton {
    private boolean mousePress; // Menyimpan status tombol saat ditekan atau tidak

    public ActionButton() {
        setContentAreaFilled(false); // Menghapus area isi tombol
        setBorder(new EmptyBorder(1, 1, 1, 1)); // Mengatur jarak antara teks dan tepi tombol
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true; // Ketika tombol ditekan, set status mousePress menjadi true
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false; // Ketika tombol dilepas, set status mousePress menjadi false
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create(); // Membuat objek Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Mengaktifkan anti-aliasing
        int width = getWidth(); // Mendapatkan lebar tombol
        int height = getHeight(); // Mendapatkan tinggi tombol
        int size = Math.min(width, height); // Mendapatkan ukuran minimum dari lebar dan tinggi tombol
        int x = (width - size) / 2; // Menghitung posisi x untuk menggambar elips
        int y = (height - size) / 2; // Menghitung posisi y untuk menggambar elips

        // Mengatur warna tombol berdasarkan status mousePress
        if (mousePress) {
            g2.setColor(new Color(158, 158, 158)); // Warna saat tombol ditekan
        } else {
            g2.setColor(new Color(199, 199, 199)); // Warna saat tombol tidak ditekan
        }

        // Menggambar elips sebagai tampilan tombol
        g2.fill(new Ellipse2D.Double(x, y, 1, 1)); // Mengisi elips dengan warna yang telah ditentukan
        g2.dispose(); // Membebaskan sumber daya grafis
        super.paintComponent(grphcs); // Melanjutkan proses penggambaran komponen tombol
    }
}
