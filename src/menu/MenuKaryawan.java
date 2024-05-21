package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import menu.MenuItemKaryawan;

public class MenuKaryawan extends JComponent {

    public MenuEvent getEvent() {
        return event;
    }

    public void setEvent(MenuEvent event) {
        this.event = event;
    }

    private MenuEvent event;
    private MigLayout layout;
    private String[][] menuItems = new String[][]{
        {"Dashboard"},
        {"Transaksi", "Form Transaksi", "Riwayat Transaksi"},
        {"Deposit"},
        {"Absensi"},
        {"Lemari Laundry"},
    };

    public MenuKaryawan() {
        init();
    }

    //Spac menu 
    private void init() {
        // layout wrap 1, fillx, inset 2
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        // Init MenuItem
        for (int i = 0; i < menuItems.length; i++) {
            if (i == 0) {
                // margin index 0
                addMenu(menuItems[i][0], i, "gapy 20");
            } else {
                // margin for index +
                addMenu(menuItems[i][0], i, "gapy 10");
            }
        }
    }

    private Icon getIcon(int index) {
        URL url = getClass().getResource("/mainKaryawan/" + index + ".png");
        if (url != null) {
            return new ImageIcon(url);
        } else {
            return null;
        }
    }

    private void addMenu(String menuName, int index, String layoutConstraints) {
        int length = menuItems[index].length;
        MenuItemKaryawan item = new MenuItemKaryawan(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (length > 1) {
                    if (!item.isSelected()) {
                        hideAllMenus(); // Sembunyikan semua menu sebelum membuka yang baru
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));
                    } else {
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if (event != null) {
                        event.selected(index, 0);
                    }
                }
            }
        });
        add(item, layoutConstraints); // Menambahkan layoutConstraints sesuai dengan indeksnya
        revalidate();
        repaint();
    }

    private void addSubMenu(MenuItemKaryawan item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx , gapy 0, inset 2", "fill"));
        panel.setName(index + "");
        // warna background sub menu
        panel.setBackground(new Color(53, 153, 134));
        for (int i = 1; i < length; i++) {
            MenuItemKaryawan subItem = new MenuItemKaryawan(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (event != null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
            });
            subItem.initSubMenu1(i, length);
            panel.add(subItem);
        }
        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();

        MenuAnimationKaryawan.showMenu1(panel, item, layout, true);
    }

    private void hideMenu(MenuItemKaryawan item, int index) {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")) {
                com.setName(null);
                MenuAnimationKaryawan.showMenu1(com, item, layout, false);
                break;
            }
        }
    }

    private void hideAllMenus() {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null) {
                com.setName(null);
                MenuItemKaryawan parentItem = (MenuItemKaryawan) getComponent(getComponentZOrder(com) - 1);
                parentItem.setSelected(false);
                MenuAnimationKaryawan.showMenu1(com, parentItem, layout, false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        // warna backround menu
        g2.setColor(new Color(56, 161, 141));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }
}
