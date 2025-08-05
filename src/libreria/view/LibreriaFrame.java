package libreria.view;

import libreria.controller.LibreriaController;
import libreria.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        add(creaBarraStrumentiSalva(), BorderLayout.SOUTH);
        add(creaBarraStrumentiStrategy(), BorderLayout.EAST);

        setVisible(true);
    }

    private JToolBar creaBarraStrumenti(){
        JToolBar toolbar = new JToolBar();

        JButton aggiungBtn = new JButton("Aggiungi");
        JButton modificaBtn = new JButton("Modifica");
        JButton rimuoviBtn = new JButton("Rimuovi");
        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");

        aggiungBtn.addActionListener(e -> {
            LibroDialog dialog = new LibroDialog(this);
            dialog.setVisible(true);
            if(dialog.isConfermato()){
                Libro nuovoLibro = dialog.getLibro();
                if(!controller.aggiungiLibro(nuovoLibro)){
                    JOptionPane.showMessageDialog(this, "ISBN già presente, libro non aggiunto.");
                } else {
                    modello.aggiorna(controller.getLibri());
                }
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

        undoBtn.addActionListener(e -> {
            if(!controller.undo()){
                JOptionPane.showMessageDialog(this, "Nessuna operazione da annullare.");
            } else{
                modello.aggiorna(controller.getLibri());
            }
        });

        redoBtn.addActionListener(e -> {
            if(!controller.redo()){
                JOptionPane.showMessageDialog(this, "Nessuna operazione da ripetere.");
            } else{
                modello.aggiorna(controller.getLibri());
            }
        });


        toolbar.add(aggiungBtn);
        toolbar.add(modificaBtn);
        toolbar.add(rimuoviBtn);
        toolbar.addSeparator();
        toolbar.add(undoBtn);
        toolbar.add(redoBtn);

        return toolbar;
    }

    private JToolBar creaBarraStrumentiSalva(){
        JToolBar toolbar = new JToolBar();
        JButton salvaJsonBtn = new JButton("SalvaJson");
        JButton salvaCsvBtn = new JButton("SalvaCsv");
        JButton caricaJsonBtn = new JButton("CaricaJson");
        JButton caricaCsvBtn = new JButton("CaricaCsv");

        salvaJsonBtn.addActionListener(e -> {
            controller.salvaSuJson(controller.getLibri(), "libreria.json");
            JOptionPane.showMessageDialog(this, "Libreria salvata.");
        });

        salvaCsvBtn.addActionListener(e -> {
            controller.salvaSuCsv(controller.getLibri(), "libreria.csv");
            JOptionPane.showMessageDialog(this, "Libreria salvata.");
        });

        caricaJsonBtn.addActionListener(e -> {
            if(controller.caricaDaJson("libreria.json")){
                modello.aggiorna(controller.getLibri());
            }else {
                JOptionPane.showMessageDialog(this, "Nessun file json trovato o libreria vuota.");
            }
        });

        caricaCsvBtn.addActionListener(e -> {
            if(controller.caricaDaCsv("libreria.csv")){
                modello.aggiorna(controller.getLibri());
            } else{
                JOptionPane.showMessageDialog(this, "Nessun file csv trovato o libreria vuota.");
            }
        });

        toolbar.add(salvaJsonBtn);
        toolbar.add(salvaCsvBtn);
        toolbar.addSeparator();
        toolbar.add(caricaJsonBtn);
        toolbar.add(caricaCsvBtn);
        return toolbar;
    }

    private JToolBar creaBarraStrumentiStrategy(){
        JToolBar toolbar = new JToolBar(SwingConstants.VERTICAL);

        JButton ricercaTitoloBtn = new JButton("Ricerca Titolo");
        JButton ricercaAutoreBtn = new JButton("Ricerca Autore");
        JButton filtraGenereBtn = new JButton("Filtra Genere");
        JButton filtraStatoBtn = new JButton("Filtra Stato");
        JButton ordinaTitoloBtn = new JButton("Ordina per Titolo");
        JButton ordinaValutazioneBtn = new JButton("Ordina per Valutazione");

        ricercaTitoloBtn.addActionListener(e -> {
            String chiave = JOptionPane.showInputDialog(this, "Inserisci Titolo");
            if(chiave != null){
                List<Libro> risultati = controller.ricercaPerTitolo(chiave);
                modello.aggiorna(risultati);
            }
        });

        ricercaAutoreBtn.addActionListener(e -> {
            String chiave = JOptionPane.showInputDialog(this, "Inserisci Autore");
            if(chiave != null){
                List<Libro> risultati = controller.ricercaPerAutore(chiave);
                modello.aggiorna(risultati);
            }
        });

        filtraGenereBtn.addActionListener(e -> {
            String genere = JOptionPane.showInputDialog(this, "Inserisci Genere");
            if(genere != null){
                List<Libro> risultati = controller.filtraPerGenere(genere);
                modello.aggiorna(risultati);
            }
        });

        filtraStatoBtn.addActionListener(e -> {
            String stato = JOptionPane.showInputDialog(this, "Inserisci Stato (LETTO, DA_LEGGERE, IN_LETTURA)");
            if(stato != null){
                List<Libro> risultati = controller.filtraPerStato(stato);
                modello.aggiorna(risultati);
            }
        });

        ordinaTitoloBtn.addActionListener(e -> modello.aggiorna(controller.ordinaPerTitolo()));
        ordinaValutazioneBtn.addActionListener(e -> modello.aggiorna(controller.ordinaPerValutazione()));

        toolbar.add(ricercaTitoloBtn);
        toolbar.add(ricercaAutoreBtn);
        toolbar.add(filtraGenereBtn);
        toolbar.add(filtraStatoBtn);
        toolbar.add(ordinaTitoloBtn);
        toolbar.add(ordinaValutazioneBtn);
        return toolbar;

    }
}
