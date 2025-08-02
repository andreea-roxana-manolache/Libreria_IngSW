package libreria.strategy;
import libreria.model.Libro;
import java.util.*;

public class OrdinamentoPerTitolo implements StrategiaOrdinamento {
    @Override
    public List<Libro> ordina(List<Libro> libri) {
        List<Libro> res = new ArrayList<>(libri);
        Collections.sort(res, new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.getTitolo().compareToIgnoreCase(l2.getTitolo());
            }
        });
        return res;
    }
}
