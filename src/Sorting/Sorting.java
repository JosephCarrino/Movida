package Sorting;

import java.util.Comparator;

public abstract class Sorting{
    /**
     * Metodo astratto che ordina l'array
     * @param <T> Tipo di array (può essere array di film o di persone)
     * @param v Array da ordinare
     * @param k Chiave di ordinamento 
     */
    public abstract <T> void sort(T[] v, Comparator<? super T> k);
    
    /**
     * Metodo astratto che ordina l'array parzialmente
     * @param <T> Tipo di array (può essere di film o di persone)
     * @param v Array da ordinare
     * @param k Chiave di ordinamento
     * @param p Primi 'p' elementi da ordinare
     */
    public abstract <T> void PartSort(T[] v, Comparator<? super T> k, int p);
    
}
