package libreria.strategy;
import java.util.*;
import libreria.model.Libro;
public interface StrategiaFiltro {
    List<Libro> filtra(List<Libro> libri);
}
