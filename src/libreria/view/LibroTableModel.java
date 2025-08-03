package libreria.view;

import javax.swing.table.AbstractTableModel;
import libreria.model.Libro;
import java.util.List;

public class LibroTableModel extends AbstractTableModel {
    private final String[] colonne = {"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato Lettura"};
    private List<Libro> libri;

    public LibroTableModel(List<Libro> libri) {
        this.libri = libri;
    }

    @Override
    public int getRowCount() {
        return libri.size();
    }

    @Override
    public int getColumnCount() {
        return colonne.length;
    }

    @Override
    public String getColumnName(int column) {
        return colonne[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro libro = libri.get(rowIndex);
        return switch(columnIndex){
            case 0 -> libro.getTitolo();
            case 1 -> libro.getAutore();
            case 2 -> libro.getIsbn();
            case 3 -> libro.getGenere();
            case 4 -> libro.getValutazione();
            case 5 -> libro.getStatoLettura();
            default -> "";
        };
    }

    public Libro getLibroAt(int row) {
        return libri.get(row);
    }

    public void aggiorna(List<Libro> nuoviLibri) {
        this.libri = nuoviLibri;
        fireTableDataChanged();
    }
}
