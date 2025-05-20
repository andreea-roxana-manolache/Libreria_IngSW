package libreria.strategy;
import model.Libro;
import java.util.*;

public class RicercaPerAutore implements StrategiaRicerca {
    @Override
    public List<Libro> ricerca(List<Libro> libri, String chiave) {
        List<Libro> res = new ArrayList<>();
        String chiaveN = chiave.toUpperCase();
        for (Libro libro: libri){
            if (libro.getAutore().toUpperCase().contains(chiaveN)){
                res.add(libro);
            }
        }
        return res;
    }
}
