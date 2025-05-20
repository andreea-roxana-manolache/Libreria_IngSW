import model.Libro;
import model.StatoLettura;
import libreria.Libreria;
public class Main{
    public static void main(String[] args) {
        Libro nuovoLibro = new Libro.Builder("abc", "io").build();
        Libreria libreria = Libreria.getInstance();
        libreria.aggiungiLibro(nuovoLibro);

        Libro nuovo1 = new Libro.Builder("abc", "io").genere("giallo")
                .isbn("123")
                .valutazione(3)
                .build();
        libreria.aggiungiLibro(nuovo1);

        for (Libro libro : libreria.getLibri()){
            System.out.println(libro);
        }


    }
}