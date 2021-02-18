package Data;

/**
 * Struttura dati che implementa il nodo di una lista non ordinata.
 * @author Giuseppe Carrino
 */
public class NodoLista<Key extends Comparable, T extends Object> {
    /**
     * Chiave di comparazione dell'oggetto salvato.
     */
    private Key key;
    /**
     * Oggetto generico di tipo T salvato.
     */
    private T elem;
    /**
     * Nodo successivo nella lista.
     */
    NodoLista next;  
    
    public NodoLista(Key key, T elem, NodoLista next){
        this.key= key;
        this.elem= elem;
        this.next= next;
    }
    
    public void setKey(Key key){
        this.key= key;
    }
    
    public void setElem(T elem){
        this.elem= elem;
    }
    
    public Key getKey(){
        return key;
    }
    
    public T getElem(){
        return elem;
    }
}

