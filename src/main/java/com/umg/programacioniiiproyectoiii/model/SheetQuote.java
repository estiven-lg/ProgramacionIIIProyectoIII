package com.umg.programacioniiiproyectoiii.model;

public class SheetQuote {

    SheetQuote next;
    SheetData current;

    public SheetQuote(SheetData current) {
        this.current = current;
        next = null;
    }

    public void addSheet(SheetData sheet) {
        SheetQuote tmpSheet = this;

        while (tmpSheet.next != null) {
            tmpSheet = tmpSheet.next;
        }

        tmpSheet.next = new SheetQuote(sheet);
    }

    public int length() {
        int n = 1;
        SheetQuote tmpSheet = this;

        while (tmpSheet.next != null) {
            tmpSheet = tmpSheet.next;
            n++;
        }
        return n;
    }
}
