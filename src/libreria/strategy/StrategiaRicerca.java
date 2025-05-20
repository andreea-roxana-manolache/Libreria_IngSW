package libreria.strategy;
import java.util.*;
import model.Libro;

public interface StrategiaRicerca {
    List<Libro> ricerca(List<Libro> libri, String chiave);
}
