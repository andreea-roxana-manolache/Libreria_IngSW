package libreria.controller;

import libreria.Libreria;
import libreria.command.*;
import libreria.memento.Caretaker;
import libreria.model.Libro;
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

    public void aggiungiLibro(Libro libro){
        caretaker.eseguiComando(new AggiungiLibroCommand(libreria, libro));
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

}
