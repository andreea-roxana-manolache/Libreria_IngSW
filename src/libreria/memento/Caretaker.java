package libreria.memento;
import java.util.*;

public class Caretaker {
    private final Stack<Memento> undoStack = new Stack<>();
    private final Stack<Memento> redoStack = new Stack<>();

    public void salvaStato(Memento m){
        undoStack.push(m);
        redoStack.clear();
    }
    public Memento undo(){
        if (!undoStack.isEmpty()){
            Memento m = undoStack.pop();
            redoStack.push(m);
            return m;
        }
        return null;
    }
    public Memento redo(){
        if (!redoStack.isEmpty()){
            Memento m = redoStack.pop();
            undoStack.push(m);
            return m;
        }
        return null;
    }
    public boolean puoFareUndo(){
        return !undoStack.isEmpty();
    }
    public boolean puoFareRedo(){
        return !redoStack.isEmpty();
    }
}
