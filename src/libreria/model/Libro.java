package libreria.model;

public class Libro {
    private final String titolo;
    private final String autore;
    private final String genere;
    private final String isbn;
    private final int valutazione; //da 1 a 5
    private final StatoLettura statoLettura;

    public static class Builder{
        //parametri obbligatori
        private final String titolo;
        private final String autore;
        private String isbn = "";

        //parametri opzionali
        private String genere = "";
        private int valutazione = -1;
        private StatoLettura statoLettura = null;

        public Builder(String titolo, String autore, String isbn){
            this.titolo = titolo;
            this.autore = autore;
            this.isbn = isbn;
        }
        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }
        public Builder valutazione(int valutazione){
            this.valutazione = valutazione;
            return this;
        }
        public Builder statoLettura(StatoLettura statoLettura){
            this.statoLettura = statoLettura;
            return this;
        }
        public Libro build(){
            return new Libro(this);
        }
    } //Builder

    private Libro(Builder builder){
        this.titolo = builder.titolo;
        this.autore = builder.autore;
        this.genere = builder.genere;
        this.isbn = builder.isbn;
        this.valutazione = builder.valutazione;
        this.statoLettura = builder.statoLettura;
    }

    public String getTitolo(){return titolo;}
    public String getAutore(){return autore;}
    public String getGenere(){return genere;}
    public String getIsbn(){return isbn;}
    public int getValutazione(){return valutazione;}
    public String getStatoLettura() {
        if (statoLettura != null) return statoLettura.name();
        return null;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return libro.isbn.equals(this.isbn);
    }
    @Override
    public int hashCode(){
        return isbn.hashCode();
    }
    @Override
    public String toString() {
        return "["+ "titolo=" + titolo +", autore=" + autore +", genere=" + genere +", isbn=" + isbn
                +", valutazione=" + valutazione +", stato=" + statoLettura +"]";
    }
}
