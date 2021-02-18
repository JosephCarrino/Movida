package Sorting;

import java.util.Comparator;

/**
 * Classe che implementa l'algoritmo di ordinamento HeapSort, basato su un array
 * ordinato con la logica di un Max-Heap, identificando i figli
 * di un elmento di indice 'i' come "2*i" e "2*i+1". A causa di questa proprietà
 * della gerarchia, non viene usato l'indice 0 dell'array, risulta quindi necessario
 * usare un array ausiliario per poter restituire un array di facile lettura
 * da parte della classe Database (che usi quindi l'indice 0).
 * @author Giuseppe Carrino
 */
public class HeapSort extends Sorting{
    
    /**
     * La funzione ripristina la proprietà di ordinamento di un array
     * rispetto a @i, eseguendo ricorsivamente dei controlli sui figli.
     * @param <T> Il tipo generico di elemento ordinato
     * @param S L'array da ordinare
     * @param com Il criterio di ordinamento
     * @param c Il numero di elementi dell' "heap"
     * @param i L'inidice del "nodo padre"
     * Costo: O(log n).
     */
    public <T> void fixHeap(T[] S, Comparator<? super T> com, int c, int i){
        int max= 2*i;
        if(2 * i > c) return;
        if((2 * i + 1 <= c) && (com.compare((S[2 * i]),(S[2 * i + 1])) <= 0))
            max= 2*i+1;
        if(com.compare(S[i],S[max]) <= 0){
            T temp= S[max];
	    S[max] = S[i];
            S[i] = temp;
            fixHeap(S, com, c, max);
        }
    }
    
    /**
     * Funzione che ritorna il massimo (ovvero il primo elemento, nodo padre)
     * @param <T> Il tipo generico dell'array da ordinato
     * @param S L'array ordinato
     * @return Il primo elemento dell'array
     * Costo: O(1).
     */
    public <T> T findMax(T[] S){
        return S[1];
    }
    
    //Costo: O(log n).
    public <T> void deleteMax(T[] S, Comparator<? super T> com, int c){
        if(c <= 0) return;
        S[1]= S[c];
        c--;
        fixHeap(S, com, c, 1);
    }
    
    /**
     * La funzione costruisce nell'array da ordinare un "maxHeap", lavorando 
     * ricorsivamente sui figli dell'elemento con indice i, usando di volta in 
     * volta fixHeap per ripristinare la proprietà di ordinamento
     * @param <T> Il tipo generico dell'array da ordinare
     * @param S L'array ordinato
     * @param com Il criterio di ordinamento
     * @param n Il numero di elementi da ordinare
     * @param i Il nodo da cui partire per ordinare
     * Costo: O(n).
     */
    public <T> void heapify(T[] S, Comparator<? super T> com, int n, int i){
        if(i > n) return;
        heapify(S, com, n, 2*i);
        heapify(S, com, n, 2*i+1);
        fixHeap(S, com, n, i);
    };
    
    /**
     * La funzione ordina l'array utilizzando le funzioni necessarie a costruire
     * un heapSort, salvando man mano gli elementi più grandi in cima all'array.
     * Si usa un array ausiliario che non utilizzi l'indice 0 data la proprietà
     * "padre-figlio" di HeapSort.
     * @param <T> Il tipo generico dell'array da ordinare
     * @param S L'array da ordinare
     * @param com Il criterio di ordinamento
     * Costo: O(n*log n).
     */
    @Override
    public <T> void sort(T[] S, Comparator<? super T> com){
        T[] tmp = java.util.Arrays.copyOf(S, S.length+1);
        for(int i=tmp.length - 1; i>0; i--)
            tmp[i]= tmp[i-1];
        tmp[0] = null;
        heapify(tmp, com, tmp.length-1, 1);
        int i= 0;
        for(int c= tmp.length-1; c > 0; c--){
            T tempo = findMax(tmp);
            deleteMax(tmp, com, c);
            S[i]= tempo;
            i++;
        }
    }
    
    /**Come sopra, ma itera soltanto 'p' volte, ordinando quindi i primi 'p' elementi
     * Costo: O(p*log n + n).
     */
    @Override
    public <T> void PartSort(T[] S, Comparator<? super T> com, int p){
        if(p >= S.length)
            sort(S, com);
        else{
        T[] tmp= java.util.Arrays.copyOf(S, S.length+1);
        for(int i= tmp.length - 1; i>0; i--)
            tmp[i]= tmp[i-1];
        tmp[0]= null;
        heapify(tmp, com, tmp.length-1, 1);
        int i= 0;
        for(int c= tmp.length-1; c > tmp.length-p-1; c--){
            T tempo = findMax(tmp);
            deleteMax(tmp, com, c);
            S[i]= tempo;
            i++;
        }
        }
    }
}
