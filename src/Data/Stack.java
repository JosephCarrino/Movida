package Data;
/**
 * Implementazione dello Stack, utile nella creazione dell'iterator per l'ABR. Gli elementi
 * sono Nodi. Lo Stack in questione è implementato tramite gli array. E' un'implementazione
 * molto compatta, include solo le funzioni isEmpty(), push() e pop().
 * @author Umberto Carlucci
 */
public class Stack {
    private Nodo[] S = new Nodo[1];
    private int n = 0;
    /**
     * @return true se lo Stack è vuoto, false altrimenti.
     */
    public boolean isEmpty(){
        return n == 0;
    }
    /**
     * Funzione che fa la push di un Nodo sulla cima dello Stack. Nell'implementazione
     * tramite array, se l'array è totalmente occupato, se ne raddoppiano le dimensioni prima 
     * di inserire il nuovo elemento.
     * @param elem Il Nodo di cui si vuole fare la push.
     */
    public void push(Nodo elem){
        if (n == S.length) {
            Nodo[] temp = new Nodo[2 * S.length];
            for (int i = 0; i < n; i++) temp[i] = S[i];
            S = temp;
        }
	S[n] = elem;
	n = n + 1;
    }
    /**
     * Funzione di pop, per togliere il Nodo in cima allo Stack. Anche qui si pone una certa
     * attenzione alle dimensioni dell'array, le cui dimensioni vengono dimezzate nel caso in cui
     * l'array sia occupato solo per 1/4.
     * @return Nodo risultante dalla pop (se esiste), altrimenti null.
     */
    public Nodo pop(){
        if(this.isEmpty()) return null;
        n = n - 1;
        Nodo elem = S[n];
        if(n > 1 && n == S.length/4){
            Nodo[] temp = new Nodo[S.length / 2];
            for (int i = 0; i < n; i++) temp[i] = S[i]; 
            S = temp;		
        }
        return elem;
    }
}
