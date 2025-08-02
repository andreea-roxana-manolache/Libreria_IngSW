package libreria.command;
import libreria.Libreria;
import libreria.model.Libro;

public class AggiungiLibroCommand implements Command{
    private final Libreria libreria; //Reciever
    private final Libro libro;

    public AggiungiLibroCommand(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }
    @Override
    public void esegui() {
        libreria.aggiungiLibro(libro); //chiama metodo su reciever
    }
}
