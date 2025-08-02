package libreria.persistenza;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import libreria.model.Libro;
import libreria.model.StatoLettura;

import java.io.IOException;

public class LibroTypeAdapter extends TypeAdapter<Libro> {

    @Override
    public void write(JsonWriter out, Libro libro) throws IOException {
        out.beginObject();
        out.name("titolo").value(libro.getTitolo());
        out.name("autore").value(libro.getAutore());
        out.name("isbn").value(libro.getIsbn());
        out.name("genere").value(libro.getGenere());
        out.name("valutazione").value(libro.getValutazione());
        out.name("statoLettura").value(
                libro.getStatoLettura() != null ? libro.getStatoLettura() : null
        );
        out.endObject();
    }
    
    @Override
    public Libro read(JsonReader in) throws IOException {
        String titolo = null;
        String autore = null;
        String isbn = null;
        String genere = "";
        int valutazione = -1;
        StatoLettura statoLettura = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "titolo" -> titolo = in.nextString();
                case "autore" -> autore = in.nextString();
                case "genere" -> genere = in.nextString();
                case "isbn" -> isbn = in.nextString();
                case "valutazione" -> valutazione = in.nextInt();
                case "statoLettura" -> {
                    if (in.peek() != com.google.gson.stream.JsonToken.NULL) {
                        String statoStr = in.nextString();
                        statoLettura = StatoLettura.valueOf(statoStr);
                    } else {
                        in.nextNull();
                    }
                }
                default -> in.skipValue();
            }
        }
        in.endObject();

        if (titolo == null || autore == null || isbn == null) {
            throw new IOException("Campi obbligatori mancanti nel JSON: titolo, autore o isbn");
        }

        return new Libro.Builder(titolo, autore, isbn)
                .genere(genere)
                .valutazione(valutazione)
                .statoLettura(statoLettura)
                .build();
    }
}

