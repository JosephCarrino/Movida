package Sorting;
import java.util.Comparator;
/**
 * Classe che rappresenta un'implementazione di SelectionSort; in questo specifico caso, il sorting
 * viene fatto in ordine decrescente, quindi dalla chiave maggiore alla chiave minore.
 * @author Umberto Carlucci
 */
public class SelectionSort extends Sorting {
    /**
     * La funzione ordina totalmente l'array tramite SelectionSort, in ordine decrescente.
     * Costo: O(n^2)
     * @param <T> Il tipo generico dell'array.
     * @param source L'array che si vuole ordinare.
     * @param c Il criterio di ordinamento dell'array.
     */
    @Override
    public <T> void sort(T[] source, Comparator<? super T> c) {
       if(c==null) return;
       int max;
       for(int i = 0; i < source.length - 1 ; i++) {
           max = i;
           for(int j = i + 1; j < source.length; j++) {
               if(c.compare(source[j], source[max]) > 0) max = j;
           }
           if(max != i) {
               T temp = source[i];
               source[i] = source[max];
               source[max] = temp;
           }
       }
    } 
    /**
     * L'array viene ordinato solo parzialmente tramite SelectionSort, si ordinano i primi m elementi.
     * Costo: O(n*m)
     * @param <T> Il tipo generico dell'array.
     * @param source L'array che si vuole ordinare.
     * @param c Il criterio di ordinamento dell'array.
     * @param m Quanti elementi dell'array si vogliono ordinare.
     */
    @Override
    public <T> void PartSort(T[] source, Comparator<? super T> c, int m) {
        if(m < 0 || c == null) return;
        if(m >= source.length) sort(source, c);
        else{
            int max;
            for(int i = 0; i < m; i++){
                max = i;
                for(int j = i; j < source.length; j++){
                   if(c.compare(source[j], source[max]) > 0) max = j;
                }
                if(max != i) {
                    T temp = source[i];
                    source[i] = source[max];
                    source[max] = temp;
                }
               
            }
        }
    }
}
