package libreria.strategy;
import java.util.*;
import model.Libro;
public interface StrategiaFiltro {
    List<Libro> filtra(List<Libro> libri);
}
