package libreria.strategy;
import java.util.*;
import libreria.model.Libro;

public class FiltroPerGenere implements StrategiaFiltro {
    public final String genere;

    public FiltroPerGenere(String genere) {
        this.genere = genere.toUpperCase();
    }

    @Override
    public List<Libro> filtra(List<Libro> libri) {
        List<Libro> res = new ArrayList<>();
        for (Libro libro: libri){
            String g = libro.getGenere();
            if (g != null && g.toUpperCase().contains(this.genere)){
                res.add(libro);
            }
        }
        return res;
    }
}
