import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import libreria.model.Libro;
import libreria.model.StatoLettura;

public class LibroTest {

    @Test
    void testCostruzioneMinima(){
        Libro libro = new Libro.Builder("Il Nome della Rosa", "Umberto Eco", "ISBN001").build();

        assertEquals("Il Nome della Rosa", libro.getTitolo());
        assertEquals("Umberto Eco", libro.getAutore());
        assertEquals("ISBN001", libro.getIsbn());
        assertEquals("",libro.getGenere());
        assertEquals(-1, libro.getValutazione());
        assertNull(libro.getStatoLettura());
    }

    @Test
    void testCostruzioneCompleta(){
        Libro libro = new Libro.Builder("Il Signore degli Anelli", "Tolkien", "ISBN002")
                .genere("Fantasy")
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build();

        assertEquals("Il Signore degli Anelli", libro.getTitolo());
        assertEquals("Tolkien", libro.getAutore());
        assertEquals("ISBN002", libro.getIsbn());
        assertEquals("Fantasy", libro.getGenere());
        assertEquals(StatoLettura.DA_LEGGERE.toString(), libro.getStatoLettura());
    }

    @Test
    void testEqualsAndHashCode(){
        Libro libro1 = new Libro.Builder("Titolo1", "Autore1", "ISBN123").build();
        Libro libro2 = new Libro.Builder("Titolo2", "Autore2", "ISBN123").build();
        Libro libro3 = new Libro.Builder("Titolo3", "Autore3", "ISBN003").build();

        assertEquals(libro1, libro2);
        assertEquals(libro1.hashCode(), libro2.hashCode());
        assertNotEquals(libro1, libro3);
    }

    @Test
    void testToStringNotNull(){
        Libro libro = new Libro.Builder("1984", "George Orwell", "ISBN456")
                .genere("Romanzo distopico")
                .valutazione(4)
                .statoLettura(StatoLettura.IN_LETTURA)
                .build();

        String stringa = libro.toString();

        assertNotNull(stringa);
        assertTrue(stringa.contains("1984"));
        assertTrue(stringa.contains("George Orwell"));
        assertTrue(stringa.contains("ISBN456"));
        assertTrue(stringa.contains("Romanzo distopico"));
        assertTrue(stringa.contains("4"));
        assertTrue(stringa.contains("IN_LETTURA"));

    }
}
