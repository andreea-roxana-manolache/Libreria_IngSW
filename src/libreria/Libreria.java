package libreria;

import libreria.memento.Memento;
import libreria.memento.Originator;
import model.Libro;
import libreria.strategy.StrategiaRicerca;
import libreria.strategy.StrategiaFiltro;
import libreria.strategy.StrategiaOrdinamento;

import java.util.*;

public class Libreria implements Originator {

    private static Libreria instance;
    private List<Libro> libri;

    //Strategy: context maniente un riferimento alla strategia
    private StrategiaRicerca strategiaRicerca;
    private StrategiaFiltro strategiaFiltro;
    private StrategiaOrdinamento strategiaOrdinamento;

    private Libreria(){
        libri = new ArrayList<>();
    }
    public static synchronized Libreria getInstance(){
        if(instance == null){
            instance = new Libreria();
        }
        return instance;
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

    public void setStrategiaRicerca(StrategiaRicerca strategia){
        this.strategiaRicerca = strategia;
    }
    public List<Libro> ricerca(String chiave) {
        if (strategiaRicerca == null) return getLibri();
        return strategiaRicerca.ricerca(libri, chiave);
    }
    public void setStrategiaFiltro(StrategiaFiltro strategia){
        this.strategiaFiltro = strategia;
    }
    public List<Libro> filtra(){
        if (strategiaFiltro == null) return getLibri();
        return strategiaFiltro.filtra(libri);
    }
    public void setStrategiaOrdinamento(StrategiaOrdinamento strategia){
        this.strategiaOrdinamento = strategia;
    }
    public List<Libro> ordina(){
        if (strategiaOrdinamento == null) return getLibri();
        return strategiaOrdinamento.ordina(libri);
    }




}
