import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import libreria.Libreria;
import libreria.model.Libro;
import libreria.model.StatoLettura;
import libreria.memento.Memento;

import java.util.List;

public class LibreriaTest {

    private Libreria libreria;

    @BeforeEach
    void resetLibreria(){
        libreria = Libreria.getInstance();
        libreria.setLibri(List.of()); //svuota la libreria prima di ogni test
    }

    @Test
    void testSingleton(){
        Libreria L1 = Libreria.getInstance();
        Libreria L2 = Libreria.getInstance();

        assertSame(L1,L2);
    }

    @Test
    void testAggiungiLibro(){
        Libro libro = new Libro.Builder("Titolo1", "Autore1", "ISBN123").build();
        libreria.aggiungiLibro(libro);

        assertTrue(libreria.getLibri().contains(libro));
    }

    @Test
    void testRimuoviLibro(){
        Libro libro = new Libro.Builder("Titolo1", "Autore1", "ISBN123").build();
        libreria.aggiungiLibro(libro);
        libreria.rimuoviLibro(libro);
        assertFalse(libreria.getLibri().contains(libro));
    }

    @Test
    void testModificaLibro(){
        Libro vecchio = new Libro.Builder("1984", "Orwell", "ISBN001").build();
        Libro nuovo = new Libro.Builder("1984", "G. Orwell", "ISBN001")
                .genere("Romanzo Distopico")
                .valutazione(4)
                .statoLettura(StatoLettura.LETTO)
                .build();
        libreria.aggiungiLibro(vecchio);
        libreria.modificaLibro(vecchio, nuovo);

        List<Libro> libri = libreria.getLibri();

        assertEquals(1, libri.size());
        assertEquals("G. Orwell", libri.getFirst().getAutore());
        assertEquals("Romanzo Distopico", libri.getFirst().getGenere());
        assertEquals(4, libri.getFirst().getValutazione());
        assertEquals("LETTO", libri.getFirst().getStatoLettura());

    }

    @Test
    void testSetLibri(){
        Libro libro1 = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        Libro libro2 = new Libro.Builder("Titolo2", "Autore2", "ISBN002").build();
        libreria.setLibri(List.of(libro1, libro2));

        List<Libro> libri = libreria.getLibri();
        assertEquals(2, libri.size());
        assertTrue(libri.contains(libro1));
        assertTrue(libri.contains(libro2));

    }

    @Test
    void testMemento(){
        Libro libro1 = new Libro.Builder("Titolo1", "Autore1", "ISBN001").build();
        libreria.aggiungiLibro(libro1);

        Memento memento = libreria.creaMemento();

        Libro libro2 = new Libro.Builder("Titolo2", "Autore2", "ISBN002").build();
        libreria.aggiungiLibro(libro2);

        assertEquals(2, libreria.getLibri().size());

        libreria.ripristinaDaMemento(memento);

        List<Libro> libri = libreria.getLibri();
        assertEquals(1, libri.size());
        assertTrue(libri.contains(libro1));
        assertFalse(libri.contains(libro2));
    }

}
