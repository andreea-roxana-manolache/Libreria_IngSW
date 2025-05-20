package libreria.strategy;
import java.util.*;
import model.Libro;

public interface StrategiaOrdinamento {
    List<Libro> ordina(List<Libro> libri);
}
