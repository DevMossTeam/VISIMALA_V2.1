package tabel_aksi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override 
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
        PanelAction action = new PanelAction();
        Color customColor = new Color(255, 252, 252);
        if (isSelected == false && row % 2 == 0) {
            action.setBackground(customColor);
        } else {
            action.setBackground(com.getBackground());
        }
        return action;
    }
}
