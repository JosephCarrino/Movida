package Data;
import java.util.Iterator;
import commons.Person;
/**
 * Classe che implementa un ABR, in particolare le principali funzioni di ricerca, inserimento
 * e cancellazione di elementi. L'ABR utilizza delle istanze della classe "Nodo". Inoltre, 
 * vengono implementate anche le funzioni inerenti all'oggetto "Iterator", per utilizzare
 * costrutti sintattici come il foreach.
 * @author Umberto Carlucci
 */


public class ABR <T> extends Data <T> {
    /**
     * Creazione della radice. La variabile intera n rappresenta il numero di elementi.
     */
    public Nodo<T> root;
    
    public ABR (){
        root = null;
        n = 0;
    }
    
    /**
     * Classico inserimento all'interno di un ABR. Prima si cerca la posizione dove inserire,
     * di conseguenza si crea un'istanza di Nodo coi parametri, infine si inserisce il nodo.
     * Costo: O(n) nel caso peggiore, O(h) nel caso medio. Per h si intende l'altezza, che mediamente è pari a O(log n).
     * @param k La chiave dell'elemento da inserire.
     * @param obj L'elemento da inserire.
     */
    @Override
    public void insert(Comparable k, T obj) {
        Nodo<T> pos = null;
        Nodo<T> tmp = root;
        while(tmp != null){
            pos = tmp;
            if (tmp.getKey().compareTo(k) > 0) 
                tmp = tmp.sx;
            else tmp = tmp.dx;
        }
        Nodo<T> toInsert = new Nodo(k, obj);
        toInsert.parent = pos;
        if (pos == null) root = toInsert;
        else if (pos.getKey().compareTo(k) > 0) 
            pos.sx = toInsert;
        else pos.dx = toInsert;
        n++;
    }
    
    
    /**
     * Funzione di cancellazione dall'ABR. La cancellazione nell'ABR è un'operazione molto articolata, difatti
     * vengono implementate diverse funzioni aggiuntive. In questo caso la funzione searchRec è la prima ad essere
     * chiamata, utile a stabilire la presenza o meno della chiave cercata all'interno dell'ABR, e, in caso positivo
     * le informazioni relative al nodo associato a quella chiave vengono salvate nel Nodo toDelete. La parte piu 
     * consistente è quella relativa all'eliminazione di un nodo con 2 figli. Il contenuto informativo del Nodo da 
     * eliminare viene scambiato col suo predecessore.
     * Costo: O(n) nel caso peggiore, O(h) nel caso medio.
     * @param k La chiave dell'elemento da eliminare.
     * @return La funzione ritorna true se ha trovato ed eliminato l'elemento, false altrimenti.
     */
    @Override
    public boolean delete(Comparable k){
        Nodo<T> toDelete = searchRec(root, k);
        if(toDelete == null) return false;
        else{
            if(toDelete.sx!=null && toDelete.dx!=null){
                Nodo<T> pred = max(toDelete.sx);
                T t1 = pred.getData();
                T t2 = toDelete.getData();
                Comparable k1 = pred.getKey();
                Comparable k2 = toDelete.getKey();
                toDelete.setData(t1);
                pred.setData(t2);
                toDelete.setKey(k1);
                pred.setKey(k2);
                toDelete = pred;
            }
            contraiNodo(toDelete);
            n--;
            return true;
        }
    }
   /**
    * Questa funzione serve ad eliminare il nodo v, che a questo punto dell'esecuzione può avere al piu un figlio.
    * Dato che nella funzione precedente v viene sostituito col suo predecessore, v non potrà mai avere un figlio destro
    * ma solo un figlio sinistro. In particolare, se non ha figli, si chiama direttamente pota(v) cosi da poterlo definitivamente
    * togliere dall'albero. Se invece v ha figlio sinistro, si deve scambiare il contenuto informativo col suo figlio sinistro,
    * qui denominato f, di conseguenza si fa pota(f) cosi da toglierlo dall'ABR.
    * Infine si devono innestare i suoi eventuali sotto alberi sinistri e destri come nuovi sotto alberi sinistri e destri di v.
    * Ciò viene fatto tramite le chiamate delle funzioni innestaSin e innestaDes. La funzione contraiNodo utilizzata controlla
    * anche l'esistenza di un figlio destro di v, permettendo cosi di poter cambiare la logica del programma quando si elimina un 
    * nodo; difatti, anche se al posto del predecessore si utilizzasse il successore, questa funzione riuscirebbe comunque a portare
    * a termine questo compito.
    * @param v Il nodo da eliminare.
    */
    private void contraiNodo(Nodo<T> v) {
        Nodo<T> f = null;
        if(v.sx!=null) 
            f = v.sx;
        else 
            if(v.dx != null) f = v.dx;
        if(f==null)
            pota(v);
        else{
            T t1 = v.getData();
            T t2 = f.getData();
            Comparable k1 = v.getKey();
            Comparable k2 = f.getKey();
            f.setData(t1);
            v.setData(t2);
            f.setKey(k1);
            v.setKey(k2);
            ABR a = pota(f);
            innestaSin(v, a.pota(f.sx));
            innestaDes(v, a.pota(f.dx));
        }
    }
    /**
     * Questa funzione, serve ad innestare il sotto albero a nella parte sinistra di u. L'innesto è impossibile in 2 casi,
     * se il nodo ha già un figlio sinistro oppure se l'albero da innestare è vuoto (cioè la root è null).
     * @param u Nodo al quale si vuole innestare un sotto albero a sinistra.
     * @param a Albero che si vuole innestare a sinistra di u.
     */
    private void innestaSin(Nodo u, ABR a){
        Nodo z = u;
        if(z.sx!=null) return;
        if(a.root == null) return;
        z.sx = a.root;
        z.sx.parent = z;
        a.root = null;
    }
    /**
     * Le stesse azioni di prima, fatte in modo totalmente analogo ma innestando a destra del nodo.
     * @param u Nodo al quale si vuole innestra un sotto albero a destra.
     * @param a Albero che si vuole innestare a destra di u.
     */
    private void innestaDes(Nodo u, ABR a){
        Nodo z = u;
        if(z.dx!=null) return;
        if(a.root == null) return;
        z.dx = a.root;
        z.dx.parent = z;
        a.root = null;
    }
    /**
     * Funzione che serve a staccare un sotto albero a partire da un nodo. Il Nodo viene staccato
     * dal padre e diventa la radice di un nuovo ABR. Casi particolari si hanno quando il Nodo è
     * null, difatti il return è una nuova istanza di ABR, oppure quando il Nodo è la radice
     * dall'ABR istanziato, la cui radice viene messa a null.
     * @param v Il nodo da cui si vuole partire.
     * @return Un nuovo ABR a sè stante, che avrà v come radice.
     */
    private ABR pota(Nodo v){
        Nodo k = v;
        if(k == null) return new ABR();
        if(k == root) root = null;
        else{
            if(k.parent.sx == v)
                k.parent.sx = null;
            else k.parent.dx = null;
            k.parent = null;
        }
        ABR a = new ABR();
        a.root = k;
        return a;
    }
    /**
     * Funzione che ritorna un Nodo, nello specifico una foglia, che si trova piu in
     * profondità, spostandosi solo sui figli destri. La particolarità di questa funzione è
     * che se richiamata sul figlio sinistro di un certo Nodo R, questa funzione avrà come
     * Nodo di return il predecessore di R.
     * @param v Nodo di partenza.
     * @return Nodo trovato.
     */
    private Nodo max(Nodo v){
        while(v!=null && v.dx!=null) {
            v = v.dx;
        }
        return v;
    }
    /**
     * Semplice funzione di clear dell'ABR, si setta la radice a null e il numero di Nodi a 0.
     */
    @Override
    public void Clear(){
        root = null;
        n = 0;
    }
    
    /**
     * Funzione che permette la ricerca su un ABR. Si basa su una funzione ricorsiva,
     * searchRec(), che cerca il Nodo spostandosi nei sotto alberi di destra o di sinistra
     * in base alle chiavi.
     * Costo: O(n) nel caso peggiore, O(h) nel caso medio.
     * @param k Chiave dell'elemento da cercare.
     * @return Parte informativa del nodo cercato (se esiste), altrimenti null.
     */
    @Override
    public T search(Comparable k) {
        Nodo<T> found;
        found = searchRec(root, k);
        if (found != null){ 
            return found.getData();
        }
        else{ 
            return null;
        }
    }
    /**
     * Funzione di ricerca ricorsiva all'interno di un ABR. Parte dalla radice e
     * controlla se si trova già nel Nodo cercato; se è già in quel nodo, lo ritorna.
     * Altrimenti si muove a sinistra oppure a destra in base alle chiavi di comparazione.
     * @param radice Radice dell'ABR.
     * @param k Chiave dell'elemento da cercare.
     * @return Nodo cercato (se esiste), altrimenti null.
     */
    private Nodo searchRec(Nodo radice, Comparable k){
        if(radice == null || radice.getKey().equals(k)) return radice;
        else if(radice.getKey().compareTo(k) > 0) return(searchRec(radice.sx, k));
        else return(searchRec(radice.dx, k));       
    }
    /**
     * Funzione utile a diminuire il numero di film in cui la persona con chiave k
     * ha collaborato.
     * @param k Chiave della persona.
     */
    @Override
    public void removeFilm(Comparable k){
        if(root.getData() instanceof Person){
            Nodo<Person> tmp = searchRec(root, k);
            if (tmp!= null) (tmp.getData()).removeFilm();
        }      
    }
    
    @Override
    public Iterator<T> iterator() {
        Nodo radice = root;
        return new ABRIterator<T>(radice);
    }

    
   /**
    * Iteratore dell'ABR, utile quando si vogliono usare costrutti simili
    * al foreach. In questo caso viene implementato tramite uno Stack.
    */
    private class ABRIterator<T> implements Iterator<T>{
        Stack s;
        Nodo currElem = iteratorStart(root); 
        /**
         * Classe che inizializza il currElem, cercando il nodo piu in profondità
         * possibile passando solo per i figli sinistri.
         * @param radice La radice dell'ABR.
         * @return Il nodo foglia piu a sinistra..
         */
        private Nodo iteratorStart(Nodo radice){
            if(radice == null)
                return null;
            else{
                while(radice.sx!=null) radice = radice.sx;
                return radice;
            }
        }
        public ABRIterator(Nodo radice){
            s = new Stack();
            while(radice != null) {
                s.push(radice);
                radice = radice.sx;
            }
        }
        /**
         * Funzione che controlla se il currElem ha un elemento successivo.
         * @return true se esiste un successivo, false altrimenti.
         */
        @Override
        public boolean hasNext(){
            return !s.isEmpty();
        }
        /**
         * Funzione che da un certo elemento, si sposta al successivo, tramite
         * lo stack.
         * @return Parte informativa del vecchio currElem.
         */
        @Override 
        public T next(){
            Nodo<T> tmp = s.pop();
            T risultato;
            risultato = tmp.getData();
            currElem = tmp;
            if(tmp.dx!=null){
                tmp = tmp.dx;
                while(tmp!=null){
                    s.push(tmp);
                    tmp = tmp.sx;
                }
            }
            return risultato;
        }
    }
}
