import libreria.memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import libreria.Libreria;
import libreria.model.Libro;
import libreria.command.AggiungiLibroCommand;
import java.util.List;

public class CaretakerTest {
    private Libreria libreria;
    private Caretaker caretaker;

    @BeforeEach
    public void inizializza(){
        libreria = Libreria.getInstance();
        libreria.setLibri(List.of());
        caretaker = new Caretaker(libreria);
    }

    @Test
    public void testAggiornaStato(){
        Libro libro = new Libro.Builder("Titolo test", "Autore test", "ISBN001").build();
        AggiungiLibroCommand cmd = new AggiungiLibroCommand(libreria, libro);

        caretaker.eseguiComando(cmd);

        assertEquals(1, libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(libro));
        assertTrue(caretaker.puoFareUndo());
        assertFalse(caretaker.puoFareRedo());
    }

    @Test
    public void testRipristinaStatoPrecedente(){
        Libro libro = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        AggiungiLibroCommand cmd = new AggiungiLibroCommand(libreria, libro);

        caretaker.eseguiComando(cmd);
        caretaker.undo();

        assertEquals(0, libreria.getLibri().size());
        assertTrue(caretaker.puoFareRedo());
    }

    @Test
    public void testRipristinaDopoUndo(){
        Libro libro = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        AggiungiLibroCommand cmd = new AggiungiLibroCommand(libreria, libro);

        caretaker.eseguiComando(cmd);
        caretaker.undo();
        caretaker.redo();

        List<Libro> libri = libreria.getLibri();
        assertEquals(1, libri.size());
        assertEquals("ISBN001", libri.getFirst().getIsbn());
    }

    @Test
    public void testUndoRedoMultipli(){
        Libro libro1 = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        Libro libro2 = new Libro.Builder("Titolo2", "Autore2", "ISBN002").build();

        caretaker.eseguiComando(new AggiungiLibroCommand(libreria,libro1));
        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, libro2));

        assertEquals(2, libreria.getLibri().size());

        caretaker.undo(); //rimuove libro2
        assertEquals(1,libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(libro1));

        caretaker.undo();
        assertEquals(0,libreria.getLibri().size());

        caretaker.redo(); //ripristina libro1
        assertEquals(1,libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(libro1));

        caretaker.redo(); //ripristina libro2
        assertEquals(2,libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(libro2));
    }
}
