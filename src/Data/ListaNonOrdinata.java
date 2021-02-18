package Data;

import commons.Person;
import Grafi.Grafo;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Classe che implementa la struttura dati ListaNonOrdinata, che a sua volta utilizza
 * la classe NodoLista per implementare i nodi. Si tratta di una lista unidirezionale 
 * con le varie funzioni per l'inserimento, la ricerca, la cancellazione.
 * @author Giuseppe Carrino
 */
public class ListaNonOrdinata<Key extends Comparable, T extends Object> implements Iterable<T>{
    //Dimensione della lista
    private int size;
    /**
     * Nodo in testa alla lista.
     */
    private NodoLista<Key, T> head;
    
    public ListaNonOrdinata(){
        head= null;
        size= 0;
    }
    
    @Override
    public Iterator<T> iterator(){
        return new ListaIterator<T>();
    }
    
    /**
     * Metodo di ricerca classico di una lista unidirezionale non ordinata:
     * si continua a scorrere la lsita in maniera iterativa fino a quando non si
     * trova l'oggetto cercato.
     * @param keyP La chiave da cercare
     * @return L'oggetto cercato (ritorna "null" se l'oggetto non è presente)
     * Costo O(size) nel caso pessimo, O(1) nell'ottimo.
     */
    public T search(Key keyP){
        if(keyP != null && head != null){
            if(keyP.equals(head.getKey())){
                return head.getElem();
            }
            else{
                if(head.next != null){
                NodoLista<Key, T> tmp= head.next;               
                while(tmp != null){
                    if(tmp.getKey().equals(keyP))
                        return tmp.getElem();
                    else
                        tmp= tmp.next;
                }
                return null;
                }
            }   
        }
        return null; 
    }
    
    /**
     * Funzione di inserimento in coda, si scorre la lista fino a quando non si raggiunge
     * l'ultimo elemento, quindi si aggiunge un nuovo nodo contenente l'oggetto da
     * aggiungere.
     * @param keyP Chiave dell'oggetto da aggiungere.
     * @param elemP Oggetto da aggiungere.
     * @return True se l'oggetto è stato aggiunto, false altrimenti (oggetto null
     * oppure oggetto già presente).
     * Costo O(size).
     */
    public boolean insert(Key keyP, T elemP){
        if(elemP != null && keyP != null){
            if(search(keyP) == null){
                if(head == null){
                    head= new NodoLista(keyP, elemP, null); 
                    size++;
                    return true;
                }
                else{
                    NodoLista tmp= head;
                    while(head.next != null)
                        head= head.next;
                    NodoLista ins= new NodoLista(keyP, elemP, null);
                    head.next= ins;
                    head= tmp;
                    size++;
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    /**
     * Funzione che cancella un elemento dalla lista. Prima l'elemento viene cercato,
     * se è presente si scorre la lista fino a quando non si raggiunge e si elimina.
     * Se l'elemento era il primo della lista, si fa scorrere la head al secondo elemento.
     * @param keyP Chiave dell'oggetto da eliminare.
     * @return True se l'oggetto è stato eliminato, false altrimenti (chiave == null
     * oppure oggetto non trovato).
     * Costo O(size) nel caso pessimo, O(1) nel caso ottimo.
     */
    public boolean delete(Key keyP){
        if(keyP != null){
            if(search(keyP) != null){
                if(head.getKey().equals(keyP)){
                    head= head.next;
                    size--;
                    return true;
                }
                else{
                    NodoLista tmp= head;
                    while(!(head.next.getKey().equals(keyP)))
                        head= head.next;
                    head.next= head.next.next;
                    head= tmp;
                    size--;
                    return true;
                }
            } else
                return false;
        } else
            return false;
    }

    /**
     * Metodo che diminuisce di 1 il numero di film in cui ha recitato un dato attore.
     * @param keyP Chiave dell'attore di cui ridurre il numero di film.
     * Costo O(size) nel caso pessimo, O(1) nel caso ottimo.
     */
    public void removeFilm(Key keyP){
        if(keyP != null){
            if(search(keyP) != null){
                NodoLista tmp= head;
                while(!(tmp.getKey().equals(keyP)))
                    tmp= tmp.next;
                Person P = (Person)tmp.getElem();
                P.removeFilm();
                tmp.setElem((T)P);
            }
        }
    }
    
    /**
     * Metodo che svuota la lista, facendo puntare la head a null e azzerando
     * la dimensione.
     * Costo O(1).
     */
    public void Clear(){
        this.head= null;
        this.size= 0;
    }
    public T getElem(){
        return head.getElem();
    }
    
    public Key getKey(){
        return head.getKey();
    }
    
    public int size(){
        return size;
    }
    
    public NodoLista getHead(){
        return head;
    }
        
    
    /**
     * Sottoclasse che implementa un iteratore funzionante su ListaNonOrdinata.
     * Si implementano i metodi hasNext(), che controlla che la lista contenga ancora un elemento
     * su cui iterare, e next() che scorre effettivamente la lista.
     * @param <T> Tipo generico dell'oggetto contenuto nella lista.
     * Per iterare l'intera lista si ha un costo O(size).
     */
    private class ListaIterator<E extends T> implements Iterator<T>{
        NodoLista<Key, T> currElem= head;
        
        @Override
        public boolean hasNext(){
            return (currElem != null);
        }
        
        @Override
        public T next(){
            if(hasNext()){
                T Curr= currElem.getElem();
                currElem= currElem.next;
                return Curr;
            }
            else throw new NoSuchElementException();
        }
    }
}
