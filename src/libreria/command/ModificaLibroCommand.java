package libreria.command;

import libreria.Libreria;
import libreria.model.Libro;

public class ModificaLibroCommand implements Command {
    private final Libreria libreria; //Reciever
    private final Libro oldLibro;
    private final Libro newLibro;

    public ModificaLibroCommand(Libreria libreria, Libro oldLibro, Libro newLibro) {
        this.libreria = libreria;
        this.oldLibro = oldLibro;
        this.newLibro = newLibro;
    }
    @Override
    public void esegui() {
        libreria.modificaLibro(oldLibro, newLibro); //chiama metodo su reciever
    }
}
