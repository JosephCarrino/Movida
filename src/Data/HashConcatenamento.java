package Data;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe che implementa una tabella Hash, in questo caso si tratta di un array
 * di liste non ordinate, con i vari metodi di inserimento, ricerca, cancellazione.
 * Si implementa anche una funzione iterator per permettere di usare costrutti come
 * il "for-each". Con il termine "size" quando si parla di costi, ci si riferisce
 * al numero di elementi presenti nella lista presa in considerazione. Nel caso in cui
 * la lista contenga un solo elemento, ci si riduce a un costo costante (O(1)) in qualsiasi
 * caso.
 * @author Giuseppe Carrino
 */
public class HashConcatenamento<T> extends Data<T> {
    
        /**
         * Un array di liste non ordinate: l'effettiva struttura dati.
         */
        public ListaNonOrdinata<Comparable, T>[] tabella;
        /**
         * La dimensione dell'array di liste, passato come parametro al costruttore.
         */
        private int dimension;
        
        public HashConcatenamento(int dim){
            tabella= new ListaNonOrdinata[Math.abs(dim)];
            this.dimension= dim;
        }
        
        @Override
        public Iterator<T> iterator(){
            return new HashIterator<T>();
        }
        
        @Override
        public int size(){
            return n;
        }
        
       /**
        * Classica funzione di hashing che si basa sulle potenze di 31, aggiungendo
        * a ogni iterazione un carattere della stringa da hashare.
        * @param key Stringa su cui effettuare hashing.
        * @return Un intero che identifica la stringa hashata (calcolato in mod 
        * tabella.length).
        * Costo O(key.length)
        */
        public int hashFunc(String key) {
            int hashKey = 0;
		for(int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			hashKey = 31*hashKey + c;
		}
		return Math.abs(hashKey) % tabella.length;
	}

        /**
         * Funzione che inserisce nella tabella Hash un oggetto passato come parametro.
         * Si effettua l'hashing della chiave di comparazione dell'oggetto da inserire,
         * quindi si inserisce l'oggetto nella lista rispettiva contenuta nella tabella.
         * (se non presente una lista in tale posizione dell'array, si crea).
         * @param k Chiave di comparazione dell'oggetto da inserire
         * @param obj Oggetto da inserire
         * Costo= costo inserimento in lista (O(size)) + hashing (O(k.length)).
         * Il resto ha costo costante.
         */
        @Override
        public void insert(Comparable k, T obj){
            if(k == null || obj == null)
                return;
            int i= hashFunc(k.toString());
            if(tabella[i] == null){
                tabella[i]= new ListaNonOrdinata();
            }
            if(tabella[i].insert(k, obj))
                n++;
        }
        
        /**
         * Funzione che elimina un oggetto nella tabella Hash. Si effettua
         * hashing della chiave di comparazione dell'oggetto, quindi si richiama
         * la funzione delete sulla lista corrispondente nella tabella (se non è stata
         * creata non si esegue nulla). Se si è eliminato l'ultimo elemento di una lista,
         * si elimina completamente la lista dalla tabella Hash.
         * @param k Chiave di comparazione dell'oggetto da eliminare.
         * @return True se si elimina l'oggetto, false altrimenti (chiave == null,
         * oppure oggetto non presente nella tabella).
         * Costo= costo delete della lista (O(size) nel caso pessimo) + hashing (O(k.length)).
         * Il resto ha costo costante.
         * Nel caso ottimo (oggetto trovato in testa alla lista)
         * il costo si riduce a quello della funzione di hashing.
         */
        @Override
        public boolean delete(Comparable k){
            if(k == null)
                return false;
            int i= hashFunc(k.toString());
            if(tabella[i] == null)
                return false;
            else{
                if(tabella[i].delete(k)){
                    if(tabella[i].size() == 0)
                        tabella[i]= null;
                    n--;
                    return true;
                }
                return false;
            }            
        }
        
        /**
         * Funzione che cerca un oggetto all'interno della tabella Hash. Prima si
         * effettua l'hashing della chiave di comparazione dell'oggetto cercato,
         * quindi si richiama la funzione "search" sulla lista non ordinata
         * corrispondente. Se tale lista non esiste, non si esegue nulla.
         * @param k Chiave di comparazione dell'oggetto cercato.
         * @return L'oggetto generico T cercato.
         * Costo= costo search della lista (O(size) nel caso pessimo) + hashing (O(k.length)).
         * Il resto ha costo costante.
         * Nel caso ottimo (oggetto trovato in testa alla lista)
         * il costo si riduce a quello della funzione di hashing.
         */
        @Override
        public T search(Comparable k){
            if(k == null)
                return null;
            int i= hashFunc(k.toString());
            if(tabella[i] == null){
                return null;
            }
            else{
                return tabella[i].search(k);
            }
        }
        
        /**
         * Metodo che diminuisce di 1 il numero di film a cui ha partecipato
         * l'attore dato in input. Si effettua hashing della chiave di comparazione
         * (nome dell'attore) quindi si richiama removeFilm() sulla lista
         * corrispondente.
         * @param k Chiave di comparazione dell'attore (il suo nome).
         * Costo= costo removeFilm() della lista (O(size) nel caso pessimo) + hashing(O(k.length)).
         * Il resto ha costo costante.
         * Nel caso ottimo (oggetto trovato in testa alla lista)
         * il costo si riduce a quello della funzione di hashing.
         */
        @Override
        public void removeFilm(Comparable k){
            if(k == null)
                return;
            int i= hashFunc(k.toString());
            if(tabella[i] != null){
                tabella[i].removeFilm(k);
            }
        }
        
        /**
         * Metodo che svuota completamente la tabella Hash, eliminando tutte le liste
         * presenti (richiamando la clear di ogni lista). Si crea quindi una nuova tabella
         * Hash con la stessa dimensione della precedente.
         * Costo= O(tabella.length).
         */
        @Override
        public void Clear(){
            for(int i= 0; i < tabella.length; i++)
                if(tabella[i] != null)
                    tabella[i].Clear();
        tabella= new ListaNonOrdinata[dimension];
        n= 0;
        }
        
        /**
         * Sottoclasse che implementa un iterator funzionante sulla struttura dati 
         * HashConcatenamento. Si implementano le funzioni hasNext(), che controlla
         * la possibilità di continuare a iterare, e Next() che itera effettivamente.
         * @param <T> Tipo generico dell'oggetto contenuto nella tabella Hash.
         */
        private class HashIterator<E extends T> implements Iterator<T>{
            
            final ListaNonOrdinata<Comparable, T>[] tab = tabella;
            int i = 0;
            NodoLista<Comparable, T> curr, currHead;
            
            @Override
            public boolean hasNext(){
                while(curr == null && i < tab.length){
                    if(tab[i] != null){
                    currHead= tab[i].getHead();
                    curr= tab[i++].getHead(); 
                    }
                    else{
                        currHead= null;
                        curr= null;
                        i++;
                    }
                }
                if(curr != null){  
                    if(curr.getElem() == null)
                        curr= curr.next;
                    return true;                 
                }
                return false;
            }
            
            @Override
            public T next(){
                if(hasNext()){
                    T currElem= curr.getElem();
                    curr= curr.next;
                    return currElem;
                }
                else throw new NoSuchElementException();
            }
        }
}
