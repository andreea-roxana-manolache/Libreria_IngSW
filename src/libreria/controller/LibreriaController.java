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

    public void caricaDaJson(String path){
        List<Libro> caricati = new Archivio().caricaDaFileJSon(path);
        libreria.setLibri(caricati);
    }

    public void caricaDaCsv(String path){
        List<Libro> lista = new Archivio().caricaDaFileCSV(path);
        libreria.setLibri(lista);
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
