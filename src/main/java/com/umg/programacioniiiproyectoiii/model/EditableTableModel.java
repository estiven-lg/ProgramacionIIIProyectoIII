package com.umg.programacioniiiproyectoiii.model;

import javax.swing.table.AbstractTableModel;

public class EditableTableModel extends AbstractTableModel {

    String columnTitles;
    String Values[][];
    int rowCount = 1000;
    TableData data;

    public EditableTableModel() {
        this.data = new TableData();
        columnTitles = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Values = new String[this.rowCount][columnTitles.length()];
        for (int i = 0; i < this.rowCount; i++) {
            this.setValueAt(Integer.toString(i), i, 0);
        }

    }

    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnTitles.length();
    }

    @Override
    public Object getValueAt(int row, int column) {

        return this.data.resolve(row, column);

    }

    @Override
    public String getColumnName(int column) {
        return columnTitles.substring(column, column + 1);
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {

        this.data.insert((String) value, row, column);
    }
}
