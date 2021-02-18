/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafi;

import commons.*;
import Data.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
/**
 * Classe che implementa un grafo composto da nodi che rappresentano un attore e da archi
 * che rappresentano le collaborazioni tra attori. I nodi sono rappresentati da una lista e ognuno
 * di essi possiede una lista di archi che funge da lista di adiacenza. Si è scelto
 * di lavorare con le liste sia per una maggiore comodità, avendo già implementato
 * la classe ListaNonOrdinata, sia poichè si è valutato il frequente utilizzo
 * di costrutti "for-each" sulla struttura dati contenenti i nodi, che porta asintoticamente
 * i costi di ogni funzione a O(n) rendendo meno ottimale l'utilizzo di strutture dati
 * più complesse.
 * @author Carlucci Umberto e Carrino Giuseppe.
 */
public class Grafo{
    
     /**
     * Lista di vertici (funge da lista di adiacenza).
     */
    ListaNonOrdinata<Comparable, NodoGrafo> NodiGrafo;
    
    public class NodoGrafo{
        /**
         * Persona relativa al vertice.
        */
        public final Person p;
        /**
         * Chiave di comparazione della persona.
        */
        public final Comparable Key;
        /**
         * Booleano necessario per la visita.
         */
        public boolean m;
        /**
         * Distanza necessaria per calcolare il MST.
         */
        public Double d;
        /**
         * Lista di adiacenza del vertice.
         */
        ListaNonOrdinata<Comparable, Arco> adiacenza;
        
        public NodoGrafo(Comparable Key, Person p){
            this.Key= Key;
            this.p= p;
            m= false;
            d= 0.0;   
            adiacenza= new ListaNonOrdinata();
        }
        
        public Double getD(){
            return d;
        }
        
    }
    
    private class Arco {
        /**
         * Vertice destinazione dell'arco.
         */
        NodoGrafo B;
        /**
         * Collaborazioni tra i vertici.
         */
        Collaboration collab;
        
        public Arco(NodoGrafo B, Collaboration collab){
            this.B= B;
            this.collab= collab;
        }
        
        public double weight(){
            if(collab == null)
                return 0;
            else
                return collab.getScore();
        }
    }
    
    public Grafo(){
        NodiGrafo= new ListaNonOrdinata();
    }
    
    /**
     * Funzione che inserisce un nodo all'interno del grafo.
     * @param p Persona da inserire.
     * @return True se la persona è stata inserita, false se era già presente nella lista di nodi.
     */
    public boolean insertN(Person p){
        return NodiGrafo.insert(p.getKey(), new NodoGrafo(p.getKey(),p));
    }
    
    /**
     * Funzione che inserisce un film nella collaborazione tra due attori. Si inseriscono
     * i due attor nel grafo e si lavora sui nodi ad essi relativi: se anche solo uno dei
     * due attori è stato appena inserito, si crea una nuova collaborazione tra A e B e
     * si aggiunge un arco relativo ad A-B in entrambe le liste di adiacenza. Se entrambi
     * gli attori erano già presenti allora si lavora sugli archi relativi alla loro collaborazione:
     * se esistono (basta sapere che ne esiste uno) si aggiunge il film 'm' alle collaborazioni,
     * altrimenti si creano due archi come sopra.
     * @param A Persona A.
     * @param B Persona B.
     * @param m Film da aggiungere alla collaborazione.
     */
    public void insertA(Person A, Person B, Movie m){
        boolean insA= insertN(A);
        boolean insB= insertN(B);
        NodoGrafo nA= NodiGrafo.search(A.getKey());
        NodoGrafo nB= NodiGrafo.search(B.getKey());
        if(insA || insB){
            Collaboration collab= new Collaboration(A, B);
            collab.addFilm(m);
            nA.adiacenza.insert(B.getKey(), new Arco(nB, collab));
            nB.adiacenza.insert(A.getKey(), new Arco(nA, collab));
        }
        else{
            Arco AB= nA.adiacenza.search(B.getKey());
            Arco BA= nB.adiacenza.search(A.getKey());
            if(AB != null){
                AB.collab.addFilm(m);
                BA.collab.addFilm(m);                               
            }
            else{
                Collaboration collab= new Collaboration(A, B);
                collab.addFilm(m);
                nA.adiacenza.insert(B.getKey(), new Arco(nB, collab));
                nB.adiacenza.insert(A.getKey(), new Arco(nA, collab));
            }
        }
    }
    
    /**
     * Si elimina un nodo dalla lista di nodi, andando a cancellare anche tutte le
     * collaborazioni ad esso relative nelle liste di adiacenza degli altri nodi.
     * @param p Attore da rimuovere.
     */
    public void deleteN(Person p){
        if(NodiGrafo.delete(p.getKey()))
            for(NodoGrafo n : NodiGrafo)
                n.adiacenza.delete(p.getKey());
    }
    
    public void Clear(){
        NodiGrafo.Clear();
    }
    
    /**
     * Funzione che date due persone e un film, elimina quest'ultimo dalle collaborazioni
     * dei due attori. Si cercano i nodi relativi agli attori e, se presenti, si lavora
     * sugli archi A-B e B-A. Si rimuove quindi il film dalla collaborazione e, nel caso
     * il film in questione fosse l'unico della collaborazione, si eliminano completamente
     * gli archi A-B e B-A.
     * @param A Persona A.
     * @param B Persona B.
     * @param m Film da rimuovere.
     */
    public void deleteCollab(Person A, Person B, Movie m){
        NodoGrafo nA= NodiGrafo.search(A.getKey());
        NodoGrafo nB= NodiGrafo.search(B.getKey());
        if(nA == null || nB == null)
            return;
        Arco AB= nA.adiacenza.search(B.getKey());
        Arco BA= nB.adiacenza.search(A.getKey());
        if(AB == null || BA == null)
            return;
        Collaboration collabAB= AB.collab;
        Collaboration collabBA= BA.collab;
        collabAB.removeFilm(m);
        collabBA.removeFilm(m);
        if(collabAB.movsize() == 0){
            nA.adiacenza.delete(B.getKey());
            nB.adiacenza.delete(A.getKey());
        }
    }
    
    /**
     * Funzione che data una persona, ne restituisce i collaboratori diretti (attori
     * con cui ha lavorato direttamente). Si cerca il nodo relativo nella lista di nodi e si 
     * crea un array di lunghezza pari al numero di nodi adiacenti (collaboratori diretti): si 
     * salvano quindi le collaborazioni effettuate con gli attori adiacenti nell'array.
     * Costo: O(|nA.adiacenza|).
     * @param A Attore di cui restituire i collaboratori diretti.
     * @return Array di collaboratori diretti.
     */
    public Person[] getDirectCollaboratorsOf(Person A){
        if(A == null)
            return null;
        NodoGrafo nA= NodiGrafo.search(A.getKey());
        if(nA == null)
            return null;
        Person[] Collaborators= new Person[nA.adiacenza.size()];
        int i= 0;
        for(Arco AB : nA.adiacenza)
            Collaborators[i++]= AB.B.p;
        return Collaborators;
    }
    
    /**
     * Funzione che data una persona, ne restituisce il team (attori con cui ha lavorato
     * direttamente o indirettamente). La funzione lavora come una classica
     * BFS. Si setta un valore boolean 'm' di tutti i nodi a false,
     * quindi si cerca il nodo relativo ad A. Vengono poi dichiarate una ListaNonOrdinata, che 
     * servirà a memorizzare tutti gli attori del team, e una LinkedList (dalla libreria
     * java.util) che funziona come coda. Si aggiunge il nodo sorgente alla coda e si setta
     * 'm' a true, quindi si procede con la BFS andando a salvare i nodi rimossi dalla coda
     * nella ListaNonOrdinata, per poi visitare i nodi adiacenti ancora non visitati (m == false),
     * aggiungendoli quindi alla coda.
     * Costo: O(|team|^2 + |NodiGrafo|) dato che si visitano tutti gli archi relativi ai nodi del team
     *        e si esegue un for su tutti i nodi presenti nel grafo.
     * @param A Persona di cui restituire il team.
     * @return Array di persone appartennti al team di A.
     */
    public Person[] getTeamOf(Person A){
        if(A == null)
            return null;
        for(NodoGrafo n : NodiGrafo) n.m= false;
        NodoGrafo nA= NodiGrafo.search(A.getKey());
        if(nA == null)
            return null;
        ListaNonOrdinata<Comparable, Person> temp= new ListaNonOrdinata();
        LinkedList<NodoGrafo> LL= new LinkedList();
        LL.add(nA);
        nA.m= true;
        while(!LL.isEmpty()){
            NodoGrafo visiting = LL.remove();
            temp.insert(visiting.Key, visiting.p);
            for(Arco directV : visiting.adiacenza){
                if(!directV.B.m){
                LL.add(directV.B);
                directV.B.m= true;
                }
            }
        }
        int i= temp.size();
        Person[] team = new Person[i];
        i= 0;
        for(Person p : temp)
            team[i++]= p;
        return team;
    }

    /**
     * Funzione restituisce l'array di collaborazioni con media di voti più elevata 
     * nel team di un attore, considerando le proprietà di un ICC. Si tratta
     * dell'algoritmo di Prim rivisitato in modo da ottenere un MaximumSpanningTree.
     * Si cerca il nodo relativo all'attore passato come parametro, quindi si allocano
     * una PriorityQueue (con criterio di ordinamento invertito, in modo da tenere in testa
     * i nodi con distanza, ovvero valore, maggiore), importata dalla libreria java.util, e una ListaNonOrdinata
     * di Collaboration che serve per memorizzare le collaborazioni migliori dell'ICC.
     * Si setta quindi la distanza di tutti i nodi a -inf, esclusa quella della sorgente
     * settata a +inf. Si prende quindi il nodo in testa a PQ e se ne controllano i nodi
     * adiacenti: se non sono stati visitati si imposta come distanza quella dell'arco 
     * preso in considerazione, altrimenti se l'arco attuale ha valore maggiore di
     * quello salvato in precedenza, si sostituisce a quest'ultimo. Si ritorna
     * quindi un array riempito con le collaborazioni salvate nella lista temp.
     * Costo: O(|team|^2*log(|team|) + |NodiGrafo|) Poichè il costo dell'algoritmo 
     *        di Prim ha costo O(m*log(n)), dove m corrisponde circa a |team|^2 (numero archi)
     *        e n a |team| dato che si lavora solo su questa porzione di grafo. Va inoltre
     *        considerato il for-each utilizzato su tutti i nodi per settare la loro distanza
     *        a -inf.
     * @param A Persona di cui restiturie il MaxICC.
     * @return Array di collaboration di valore maggiore.
     */
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person A){
        if(A == null)
            return null;
        NodoGrafo nA = NodiGrafo.search(A.getKey());
        if(nA == null)
            return null;
        PriorityQueue<NodoGrafo> PQ = new PriorityQueue(Comparator.comparing(NodoGrafo::getD).reversed());
        ListaNonOrdinata<Comparable, Collaboration> temp = new ListaNonOrdinata();
        for(NodoGrafo n : NodiGrafo)
            n.d= Double.NEGATIVE_INFINITY;
        nA.d = Double.POSITIVE_INFINITY;
        PQ.add(nA);
        while(!PQ.isEmpty()){
            NodoGrafo visiting = PQ.remove();
            for(Arco arco : visiting.adiacenza){
                NodoGrafo nodoB = arco.B;
                if(nodoB.d == Double.NEGATIVE_INFINITY){
                    nodoB.d= arco.weight();
                    PQ.add(nodoB);
                    temp.insert(nodoB.Key, arco.collab);
                }
                else if (arco.weight() > nodoB.d && (PQ.contains(nodoB))){
                    PQ.remove(nodoB);
                    temp.delete(nodoB.Key);
                    nodoB.d= arco.weight();
                    PQ.add(nodoB);
                    temp.insert(nodoB.Key, arco.collab);                    
                }
            }
        }
        int i= temp.size();
        Collaboration[] MST = new Collaboration[i];
        i= 0;
        for(Collaboration a : temp)
            MST[i++]= a;
        return MST;
    }
    
}
