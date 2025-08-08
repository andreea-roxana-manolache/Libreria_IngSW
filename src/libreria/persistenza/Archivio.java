package libreria.persistenza;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import libreria.model.Libro;
import libreria.model.StatoLettura;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Archivio {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Libro.class, new LibroTypeAdapter())
            .setPrettyPrinting()
            .create();

    public static void salvaSuFileJSon(List<Libro> libri, String percorso){
        try (Writer writer = new FileWriter(percorso)) {
            gson.toJson(libri, writer);
        }catch (IOException e) {
            System.err.println("Errore durante il salvataggio in JSON: " + e.getMessage());
        }
    }

    public static List<Libro> caricaDaFileJSon(String percorso){
        try (Reader reader = new FileReader(percorso)) {
            Type listType = new TypeToken<List<Libro>>(){}.getType();
            return gson.fromJson(reader, listType);
        }catch (IOException | JsonSyntaxException e) {
            System.err.println("Errore durante il caricamento da JSON: " + e.getMessage());
            return new ArrayList<>(); // ritorna lista vuota in caso di errore
        }
    }

    public static void salvaSuFileCSV(List<Libro> libri, String percorso){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(percorso))) {
            // Intestazione colonne
            writer.write("\"Titolo\",\"Autore\",\"ISBN\",\"Genere\",\"Valutazione\",\"StatoLettura\"\n");
            for (Libro libro : libri) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",%d,%s\n",
                        libro.getTitolo().replace("\"", "\"\""),
                        libro.getAutore().replace("\"", "\"\""),
                        libro.getIsbn().replace("\"", "\"\""),
                        libro.getGenere().replace("\"", "\"\""),
                        libro.getValutazione(),
                        libro.getStatoLettura() != null ? libro.getStatoLettura() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio in CSV: " + e.getMessage());
        }
    }
    public static List<Libro> caricaDaFileCSV(String percorso) {
        List<Libro> libri = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {
            reader.readLine(); // salta intestazione
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campi.length < 6) {
                    System.err.println("Linea malformata: " + linea);
                    continue;
                }
                for (int i = 0; i < campi.length; i++) {
                    campi[i] = campi[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                }
                try {
                    String titolo = campi[0];
                    String autore = campi[1];
                    String isbn = campi[2];
                    String genere = campi[3];
                    int valutazione = campi[4].isEmpty() ? -1 : Integer.parseInt(campi[4]);
                    StatoLettura statoLettura = campi[5].isEmpty() ? null : StatoLettura.valueOf(campi[5]);

                    libri.add(new Libro.Builder(titolo, autore, isbn)
                            .genere(genere)
                            .valutazione(valutazione)
                            .statoLettura(statoLettura)
                            .build());
                } catch (Exception e) {
                    System.err.println("Errore nel parsing della riga: " + linea + " → " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento da CSV: " + e.getMessage());
        }
        return libri;
    }
}
