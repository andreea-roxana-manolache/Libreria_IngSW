package libreria.strategy;
import java.util.*;
import model.Libro;

public class FiltroPerGenere implements StrategiaFiltro {
    public final String genere;

    public FiltroPerGenere(String genere) {
        this.genere = genere.toUpperCase();
    }

    @Override
    public List<Libro> filtra(List<Libro> libri) {
        List<Libro> res = new ArrayList<>();
        for (Libro libro: libri){
            if (libro.getGenere().toUpperCase().contains(this.genere)){
                res.add(libro);
            }
        }
        return res;
    }
}
