package libreria.view;

import libreria.controller.LibreriaController;
import libreria.model.Libro;

import javax.swing.*;
import java.awt.*;

public class LibreriaFrame extends JFrame{
    private final LibreriaController controller;
    private final LibroTableModel modello;
    private final JTable tabella;

    public LibreriaFrame(){
        super("Libreria personale");

        controller = new LibreriaController();
        modello = new LibroTableModel(controller.getLibri());
        tabella = new JTable(modello);

        setLayout(new BorderLayout());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JScrollPane(tabella), BorderLayout.CENTER);
        add(creaBarraStrumenti(), BorderLayout.NORTH);

        setVisible(true);
    }

    private JToolBar creaBarraStrumenti(){
        JToolBar toolbar = new JToolBar();

        JButton aggiungBtn = new JButton("Aggiungi");
        JButton modificaBtn = new JButton("Modifica");
        JButton rimuoviBtn = new JButton("Rimuovi");

        aggiungBtn.addActionListener(e -> {
            LibroDialog dialog = new LibroDialog(this);
            dialog.setVisible(true);
            if(dialog.isConfermato()){
                controller.aggiungiLibro(dialog.getLibro());
                modello.aggiorna(controller.getLibri());
            }
        });

        modificaBtn.addActionListener(e -> {
            int row = tabella.getSelectedRow();
            if(row >= 0){
                Libro libroOriginale = modello.getLibroAt(row);
                LibroDialog dialog = new LibroDialog(this, libroOriginale);
                dialog.setVisible(true);
                if(dialog.isConfermato()){
                    controller.modificaLibro(libroOriginale, dialog.getLibro());
                    modello.aggiorna(controller.getLibri());
                }
            }
        });

        rimuoviBtn.addActionListener(e -> {
            int row = tabella.getSelectedRow();
            if(row >= 0){
                Libro libro = modello.getLibroAt(row);
                controller.rimuoviLibro(libro);
                modello.aggiorna(controller.getLibri());
            }
        });
        toolbar.add(aggiungBtn);
        toolbar.add(modificaBtn);
        toolbar.add(rimuoviBtn);
        return toolbar;
    }
}
