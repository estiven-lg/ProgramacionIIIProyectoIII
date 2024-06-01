package com.umg.programacioniiiproyectoiii.view;

import com.umg.programacioniiiproyectoiii.model.EditableTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

public class ContentSheet extends JPanel {

    EditableTableModel model;
    JTable table;

    public ContentSheet() {
        this.model = new EditableTableModel();
         this.table = new JTable(model) {
            @Override
            public Component prepareRenderer(
                    TableCellRenderer renderer, int row, int col) {
                if (col == 0) {
                    this.getColumnModel().getColumn(0).setPreferredWidth(50);
                    return this.getTableHeader().getDefaultRenderer()
                            .getTableCellRendererComponent(this, this.getValueAt(
                                    row, col), false, false, row, col);
                } else {
                    return super.prepareRenderer(renderer, row, col);
                }
            }
        };

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(table), BorderLayout.CENTER);

    }

}
