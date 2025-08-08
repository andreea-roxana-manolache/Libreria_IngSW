import libreria.memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import libreria.Libreria;
import libreria.model.Libro;
import libreria.model.StatoLettura;
import libreria.command.AggiungiLibroCommand;
import libreria.command.ModificaLibroCommand;
import libreria.command.RimuoviLibroCommand;
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
    public void testAggiungiLibro(){
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

    @Test
    public void testModificaLibro(){
        Libro vecchio = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, vecchio));

        Libro nuovo = new Libro.Builder("Tit.1", "Autore1", "ISBN001")
                .genere("Giallo")
                .valutazione(3)
                .statoLettura(StatoLettura.LETTO)
                .build();

        caretaker.eseguiComando(new ModificaLibroCommand(libreria, vecchio, nuovo));

        assertEquals(1, libreria.getLibri().size());
        assertEquals("Tit.1", libreria.getLibri().getFirst().getTitolo());
        assertEquals("Giallo", libreria.getLibri().getFirst().getGenere());

        caretaker.undo();
        assertEquals("Titolo1", libreria.getLibri().getFirst().getTitolo());
        assertEquals("", libreria.getLibri().getFirst().getGenere());

        caretaker.redo();
        assertEquals("Tit.1", libreria.getLibri().getFirst().getTitolo());

    }

    @Test
    public void testRimuoviLibro(){
        Libro l1 = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        Libro l2 = new Libro.Builder("Titolo2", "Autore2", "ISBN002").build();

        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, l1));
        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, l2));
        assertEquals(2, libreria.getLibri().size());

        caretaker.eseguiComando(new RimuoviLibroCommand(libreria, l1));
        assertEquals(1, libreria.getLibri().size());
        assertFalse(libreria.getLibri().contains(l1));
        assertTrue(libreria.getLibri().contains(l2));

        caretaker.undo();
        assertEquals(2,libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(l1));

        caretaker.redo();
        assertEquals(1,libreria.getLibri().size());
        assertFalse(libreria.getLibri().contains(l1));
    }
}
