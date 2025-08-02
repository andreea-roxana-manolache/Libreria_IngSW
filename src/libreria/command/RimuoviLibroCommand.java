package libreria.command;

import libreria.Libreria;
import libreria.model.Libro;

public class RimuoviLibroCommand implements Command {
    private final Libreria libreria; //Reciever
    private final Libro libro;

    public RimuoviLibroCommand(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }
    @Override
    public void esegui() {
        libreria.rimuoviLibro(libro); //chiama metodo su reciever
    }
}
