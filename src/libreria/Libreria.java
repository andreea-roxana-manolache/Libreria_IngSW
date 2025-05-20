package libreria;

import libreria.memento.Memento;
import libreria.memento.Originator;
import model.Libro;

import java.util.*;

public class Libreria implements Originator {

    private static Libreria lib;
    private List<Libro> libri;

    private Libreria(){
        libri = new ArrayList<>();
    }
    public static synchronized Libreria getInstance(){
        if(lib == null){
            lib = new Libreria();
        }
        return lib;
    }
    public void aggiungiLibro(Libro libro){
        libri.add(libro);
    }
    public void rimuoviLibro(Libro libro){
        libri.remove(libro);
    }
    public List<Libro> getLibri(){
        return new ArrayList<>(libri);
    }

    @Override
    public Memento creaMemento() {
        return new LibreriaMemento(new ArrayList<>(libri));
    }

    @Override
    public void ripristinaDaMemento(Memento memento) {
        if (memento instanceof LibreriaMemento lm){
            this.libri = new ArrayList<>(lm.stato());
        }else throw new IllegalArgumentException("Memento non valido");

    }
    //Record privato Memento concreto
    private record LibreriaMemento(List<Libro> stato) implements Memento{

    }
}
