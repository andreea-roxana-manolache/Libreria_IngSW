import libreria.strategy.RicercaPerAutore;
import libreria.strategy.*;
import model.Libro;
import model.StatoLettura;
import libreria.Libreria;
public class Main{
    public static void main(String[] args) {
        Libreria libreria = Libreria.getInstance();

        libreria.aggiungiLibro(new Libro.Builder("Il nome della rosa", "Umberto Eco")
                .valutazione(3)
                .build());

        libreria.aggiungiLibro(new Libro.Builder("1984", "Orwell").genere("Distopico")
                .valutazione(5)
                .build());
        libreria.aggiungiLibro(new Libro.Builder("Orgoglio e Pregiudizio", "Jane Austen")
                .genere("Romantico")
                .valutazione(4)
                .build());
        libreria.aggiungiLibro(new Libro.Builder("Il Signore degli Anelli", "Tolkien").build());
        for (Libro libro : libreria.getLibri()){
            System.out.println(libro);
        }
        libreria.setStrategiaRicerca(new RicercaPerTitolo());
        System.out.println("Ricerca per autore: " + libreria.ricerca("signore"));

        libreria.setStrategiaFiltro(new FiltroPerGenere("Distopico"));
        libreria.setStrategiaOrdinamento(new OrdinamentoPerTitolo());

        System.out.println("Libri filtrati: " + libreria.filtra());
        System.out.println("Libri ordindati: "+ libreria.ordina());




    }
}