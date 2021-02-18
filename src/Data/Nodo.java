package Data;
/**
 * Classe che implementa un Nodo, elemento cardine della struttura dati ABR.
 * @author Umberto Carlucci
 */

public class Nodo<T> implements Comparable<Nodo>{
    private T data;
    private Comparable key;
    public Nodo<T> sx;
    public Nodo<T> dx;
    public Nodo<T> parent;
    /**
     * Funzione che, a partire da un elemento e dalla sua chiave, crea e restituisce un nodo.
     * Padre e figli sinistro/destro vengono messi a null.
     * @param k Chiave dell'elemento.
     * @param dato L'elemento da inserire.
     */
    public Nodo(Comparable k, T dato){
        data = dato;
        key = k;
        sx = null;
        dx = null;
        parent = null;
    }  
    
    public Comparable getKey(){
        return key;
    }
    
    public T getData(){
        return data;
    }
    
    public void setKey(Comparable k){
        key = k;
    }
    
    public void setData(T dato){
        data = dato;
    }
    
    /**
     * Funzione necessaria alla comparazione di chiavi. Si compara la chiave del nodo con la
     * chiave del Nodo passato come parametro.
     * @param c Nodo con cui comparare.
     * @return -1 se la chiave del Nodo passato è maggiore della chiave del this, 1 altrimenti.
     */
    @Override 
    public int compareTo(Nodo c){
        if(key.compareTo(c.key) < 0) return -1;
        else return 1;
    }
}