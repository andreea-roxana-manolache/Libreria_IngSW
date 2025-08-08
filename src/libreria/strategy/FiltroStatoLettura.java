package libreria.strategy;
import libreria.model.Libro;
import java.util.*;

public class FiltroStatoLettura implements StrategiaFiltro{
    private final String statoLettura;

    public FiltroStatoLettura(String statoLettura) {
        this.statoLettura = statoLettura.toUpperCase();
    }

    @Override
    public List<Libro> filtra(List<Libro> libri) {
        List<Libro> res = new ArrayList<>();
        for (Libro libro: libri){
            String stato = libro.getStatoLettura();
            if (stato != null && stato.toUpperCase().equals(this.statoLettura)){
                res.add(libro);
            }
        }
        return res;
    }
}
