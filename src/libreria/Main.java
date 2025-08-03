package libreria;

import libreria.view.LibreriaFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibreriaFrame::new);
    }
}