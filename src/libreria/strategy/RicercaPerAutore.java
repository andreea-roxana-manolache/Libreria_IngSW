package libreria.strategy;
import libreria.model.Libro;
import java.util.*;

public class RicercaPerAutore implements StrategiaRicerca {
    @Override
    public List<Libro> ricerca(List<Libro> libri, String chiave) {
        List<Libro> res = new ArrayList<>();
        String chiaveN = chiave == null ? "" : chiave.toUpperCase();
        for (Libro libro: libri){
            String autore = libro.getAutore();
            if (autore != null && autore.toUpperCase().contains(chiaveN)){
                res.add(libro);
            }
        }
        return res;
    }
}
