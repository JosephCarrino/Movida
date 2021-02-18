package Data;

/**
 * Classe astratta relativa ai dizionari implementati che descrive le funzioni
 * base necessarie.
 * @author Carlucci Umberto e Carrino Giuseppe.
 */
public abstract class Data<T> implements Iterable<T>{
    
    protected int n;
    
    public Data() { n= 0;}
    
    /** 
     * Metodo astratto per l'inserimento di nuovi film o persone
     * @param k chiave di comparazione
     * @param obj film/persona da inserire
     */
    public abstract void insert(Comparable k, T obj);
    
    /**
     * Metodo astratto per la rimozione di film o persone
     * @param k chiave di confronto dell' oggetto da cencellare
     * @return true se si è eliminato l'oggetto, false altrimenti
     */
    public abstract boolean delete(Comparable k);
    
    /**
     * Metodo per svuotare la struttura dati scelta
     */
    public abstract void Clear();
    
    /**
     * Metodo astratto per la ricerca di un dato oggetto
     * @param k chiave di comaparazione
     * @return oggetto cercato
     */
    public abstract T search(Comparable k);
    
    /**
     * Metodo generico per la dimensione della struttura dati
     * @return dimensione della struttura
     */
    public int size() {return n;}
    
    /**
     * Metodo che cerca nella struttura dati una data persona e ne diminuisce il numero di film in cui ha collaborato
     * @param k chiave di comparazione per cercare la persona
     */
    public abstract void removeFilm(Comparable k);
    
}
