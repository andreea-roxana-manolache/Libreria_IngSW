package model;

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

        //parametri opzionali
        private String genere = "";
        private String isbn = "";
        private int valutazione = -1;
        private StatoLettura statoLettura = null;

        public Builder(String titolo, String autore){
            this.titolo = titolo;
            this.autore = autore;
        }
        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }
        public Builder isbn(String isbn){
            this.isbn = isbn;
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

    public String getTitolo(){
        return titolo;
    }
    public String getAutore(){
        return autore;
    }
    public String getGenere(){
        return genere;
    }
    public String getIsbn(){
        return isbn;
    }
    public int getValutazione(){
        return valutazione;
    }
    public StatoLettura getStatoLettura(){
        return statoLettura;
    }

    @Override
    public String toString() {
        return "["+ "titolo=" + titolo +", autore=" + autore +", genere=" + genere +", isbn=" + isbn
                +", valutazione=" + valutazione +", stato=" + statoLettura +"]";
    }




}
