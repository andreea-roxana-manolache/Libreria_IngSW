package libreria.controller;

import libreria.Libreria;
import libreria.command.*;
import libreria.memento.Caretaker;
import libreria.model.Libro;
import libreria.persistenza.Archivio;
import libreria.strategy.*;

import java.util.List;

public class LibreriaController {
    private final Libreria libreria;
    private final Caretaker caretaker;

    public LibreriaController(){
        this.libreria = Libreria.getInstance();
        this.caretaker = new Caretaker(libreria);
    }

    public List<Libro> getLibri(){
        return libreria.getLibri();
    }

    public Libreria getLibreria(){
        return libreria;
    }
    public Caretaker getCaretaker(){
        return caretaker;
    }

    public boolean aggiungiLibro(Libro libro){
        for(Libro l: libreria.getLibri())
            if(l.equals(libro))
                return false;
        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, libro));
        return true;
    }

    public void modificaLibro(Libro oldLibro, Libro newLibro){
        caretaker.eseguiComando(new ModificaLibroCommand(libreria, oldLibro, newLibro));
    }

    public void rimuoviLibro(Libro libro){
        caretaker.eseguiComando(new RimuoviLibroCommand(libreria, libro));
    }

    public boolean undo(){
        if (caretaker.puoFareUndo()){
            caretaker.undo();
            return true;
        }
        return false;
    }

    public boolean redo(){
        if(caretaker.puoFareRedo()){
            caretaker.redo();
            return true;
        }
        return false;
    }

    public boolean caricaDaJson(String path){
        List<Libro> caricati = Archivio.caricaDaFileJSon(path);
        if(caricati.isEmpty()) return false;
        libreria.setLibri(caricati);
        return true;
    }

    public boolean caricaDaCsv(String path){
        List<Libro> lista = Archivio.caricaDaFileCSV(path);
        if(lista.isEmpty()) return false;
        libreria.setLibri(lista);
        return true;
    }

    public void salvaSuJson(List<Libro> libri, String path){
        Archivio.salvaSuFileJSon(libri, path);
    }

    public void salvaSuCsv(List<Libro> libri, String path){
        Archivio.salvaSuFileCSV(libri, path);
    }

    public List<Libro> ricercaPerTitolo(String chiave){
        libreria.setStrategiaRicerca(new RicercaPerTitolo());
        return libreria.ricerca(chiave);
    }

    public List<Libro> ricercaPerAutore(String chiave){
        libreria.setStrategiaRicerca(new RicercaPerAutore());
        return libreria.ricerca(chiave);
    }

    public List<Libro> filtraPerGenere(String genere){
        libreria.setStrategiaFiltro(new FiltroPerGenere(genere));
        return libreria.filtra();
    }

    public List<Libro> filtraPerStato(String statoLettura){
        libreria.setStrategiaFiltro(new FiltroStatoLettura(statoLettura));
        return libreria.filtra();
    }

    public List<Libro> ordinaPerTitolo(){
        libreria.setStrategiaOrdinamento(new OrdinamentoPerTitolo());
        return libreria.ordina();
    }
    public List<Libro> ordinaPerValutazione(){
        libreria.setStrategiaOrdinamento(new OrdinamentoPerValutazione());
        return libreria.ordina();
    }
}
