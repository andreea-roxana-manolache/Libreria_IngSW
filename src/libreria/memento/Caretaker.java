package libreria.memento;
import java.util.*;

import libreria.Libreria;
import libreria.command.Command;

public class Caretaker {
    private final Stack<Memento> undoStack = new Stack<>();
    private final Stack<Memento> redoStack = new Stack<>();
    private final Libreria originator;

    public Caretaker(Libreria originator) {
        this.originator = originator;
    }
    public void eseguiComando(Command command){ //Invoker del comando
        salvaStato();
        command.esegui();
        redoStack.clear();
    }
    public void salvaStato(){
        Memento statoCorrente = originator.creaMemento();
        undoStack.push(statoCorrente);
    }
    public void undo(){
        if (puoFareUndo()) {
            Memento precedente = undoStack.pop();
            Memento statoCorrente = originator.creaMemento();
            redoStack.push(statoCorrente);
            originator.ripristinaDaMemento(precedente);
        }
    }
    public void redo(){
        if (puoFareRedo()){
            Memento corrente = originator.creaMemento();
            Memento successivo = redoStack.pop();
            undoStack.push(corrente);
            originator.ripristinaDaMemento(successivo);
        }
    }
    public boolean puoFareUndo(){
        return !undoStack.isEmpty();
    }
    public boolean puoFareRedo(){
        return !redoStack.isEmpty();
    }
}
