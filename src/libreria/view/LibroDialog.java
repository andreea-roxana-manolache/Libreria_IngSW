package libreria.view;

import javax.swing.*;
import java.awt.*;

import libreria.model.Libro;
import libreria.model.StatoLettura;

public class LibroDialog extends JDialog {
    private JTextField titoloField;
    private JTextField autoreField;
    private JTextField isbnField;
    private JTextField genereField;
    private JComboBox<Integer> valutazioneBox;
    private JComboBox<String> statoBox;

    private boolean confermato = false;
    private Libro libro;

    public LibroDialog(JFrame parent){
        this(parent, null);
    }

    public LibroDialog(JFrame parent, Libro libroEsistente){
        super(parent, "Libro", true);
        setLayout(new BorderLayout());
        this.setSize(400, 300);
        this.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(6,2,5,5));
        titoloField = new JTextField();
        autoreField = new JTextField();
        isbnField = new JTextField();
        genereField = new JTextField();
        valutazioneBox = new JComboBox<>(new Integer[]{1,2,3,4,5});
        statoBox = new JComboBox<>(new String[]{"LETTO", "DA_LEGGERE", "IN_LETTURA"});

        panel.add(new JLabel("Titolo"));
        panel.add(titoloField);
        panel.add(new JLabel("Autore"));
        panel.add(autoreField);
        panel.add(new JLabel("ISBN"));
        panel.add(isbnField);
        panel.add(new JLabel("Genere"));
        panel.add(genereField);
        panel.add(new JLabel("Valutazione"));
        panel.add(valutazioneBox);
        panel.add(new JLabel("Stato"));
        panel.add(statoBox);

        add(panel, BorderLayout.CENTER);

        JButton confermaBtn = new JButton("Conferma");
        JButton annullaBtn = new JButton("Annulla");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confermaBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        confermaBtn.addActionListener(e -> {
            if(validaInput()){
                libro = new Libro.Builder(
                        titoloField.getText(),
                        autoreField.getText(),
                        isbnField.getText()
                )
                        .genere(genereField.getText())
                        .valutazione((Integer) valutazioneBox.getSelectedItem())
                        .statoLettura(StatoLettura.valueOf((String) statoBox.getSelectedItem()))
                        .build();
                confermato = true;
                setVisible(false);
            }
        });

        annullaBtn.addActionListener(e -> {
            confermato = false;
            setVisible(false);
        });

        if (libroEsistente != null){
            riempiCampi(libroEsistente);
        }
    }

    private boolean validaInput(){
        if(titoloField.getText().isBlank() || autoreField.getText().isBlank() || isbnField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Titolo, autore e ISBN sono obbligatori.");
            return false;
        }
        return true;
    }

    private void riempiCampi(Libro libro){
        titoloField.setText(libro.getTitolo());
        autoreField.setText(libro.getAutore());
        isbnField.setText(libro.getIsbn());
        genereField.setText(libro.getGenere());
        valutazioneBox.setSelectedItem(libro.getValutazione());
        statoBox.setSelectedItem(libro.getStatoLettura());
        isbnField.setEditable(false);
    }

    public boolean isConfermato(){
        return confermato;
    }

    public Libro getLibro(){
        return libro;
    }
}
