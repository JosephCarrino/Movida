package Core;

import java.io.*;
import java.util.Comparator;
import commons.*;
import Data.*;
import Sorting.*;
import Grafi.Grafo;


/**
 * Classe che implementa tutte le funzioni principali di Movida, ovvero il caricamento
 * da file e il salvataggio su file, le funzioni di ricerca, di rimozione ecc.
 * @author Carlucci Umberto e Carrino Giuseppe
 */
public class Database implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {
    
    private SortingAlgorithm CurrentSort;
    private MapImplementation CurrentMap;
    
    private Sorting SortAlg;
    public Data<Movie> mData;
    public Data<Person> pData;
    public Grafo CollG= new Grafo();
    
    @Override
    public boolean setSort(SortingAlgorithm a){
        if(a == CurrentSort) return false;
        else{
            switch (a){
                case HeapSort:
                    CurrentSort= a;
                    SortAlg= new HeapSort();
                    return true;
                case SelectionSort:
                    CurrentSort= a;
                    SortAlg= new SelectionSort();
                    return true; 
                default:
                     return false;
            }
        }
    }
    
    @Override
    public boolean setMap(MapImplementation m){
        if(m == CurrentMap) return false;
        else{
            switch(m){
                case HashConcatenamento:
                    CurrentMap= m;
                    HashConcatenamento<Movie> TempM= new HashConcatenamento(NumeriPrimi.nPrimo(100));
                    if (mData != null){
                        for (Movie oldm : mData)
                            TempM.insert(oldm.getKey(), oldm);
                    }
                    mData= TempM;
                    HashConcatenamento<Person> TempP= new HashConcatenamento(NumeriPrimi.nPrimo(100));
                    if(pData != null){
                        for (Person oldp : pData)
                            TempP.insert(oldp.getKey(), oldp); 
                    }
                    pData= TempP;   
                    return true;
                case ABR:
                    CurrentMap= m;
                    ABR<Movie> TM= new ABR();
                    if (mData != null){
                        for (Movie oldm : mData)
                            TM.insert(oldm.getKey(), oldm);
                    }
                    mData= TM;
                    ABR<Person> TP= new ABR();
                    if(pData != null){
                        for (Person oldp : pData)
                            TP.insert(oldp.getKey(), oldp);
                    }
                    pData= TP;    
                    return true; 
                default:
                    return false;
            }
        }
    }
    
    
    private final String TITLE = "Title:";
    private final String YEAR= "Year:";
    private final String DIR= "Director:";
    private final String CAST= "Cast:";
    private final String VOTES= "Votes:";

    final private String[] fields = {TITLE, YEAR, DIR, CAST, VOTES};

    /**
     * Metodo che carica i dati dei film contenuti nel file in Input, memorizzandoli
     * nel dizionario selezionato e salvando attori e collaborazioni in un grafo.
     * Viene utilizzato un array che memorizza le righe relative a ogni film preso singolarmente,
     * verificando che la riga inizi con le stringhe opportune. Si verifica poi per ogni elemento
     * che esso non sia già presente nella struttura dati (settando tutti i caratteri in minuscolo ed
     * eliminando gli spazi per comodità). Se un attore è già presente, ma il film inserito
     * no, si aggiunge un film all'attore in questione.
     * @param f File di input.
     */
    @Override
    public void loadFromFile(File f){
        if(!f.exists() || !f.canRead())
            throw new MovidaFileException();
       try{    
        BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String[] nfields = new String[fields.length];
        
        while(b.ready()){
            for(int i= 0; i < fields.length; i++){
                String line= b.readLine();
                if(line.startsWith(fields[i])) nfields[i]= (line.substring(fields[i].length())).trim();
                else{
                    System.out.println("Field error\n");
                    throw new MovidaFileException();
                }
            }
            b.readLine();
            if(mData.search(nfields[0].replace(" ", "").toLowerCase()) == null){
                String[] actors = nfields[3].split(", ");
                Person[] cast= new Person[actors.length];
                for(int i= 0; i < actors.length; i++){
                    Person p = pData.search(actors[i].replace(" ", "").toLowerCase());
                    if(p == null){
                            cast[i]= new Person(actors[i], false);
                            cast[i].newFilm();
                            pData.insert(cast[i].getKey(), cast[i]);
                    }
                    else{
                        cast[i]= p;
                        cast[i].newFilm();                       
                    }
                }
                Person director = pData.search(nfields[2].replace(" ", "").toLowerCase());
                if(director == null){
                    director= new Person(nfields[2], true);
                    director.newFilm();
                    pData.insert(director.getKey(), director);
                }
                else{
                    director.newFilm();
                }
                
                Movie movie= new Movie(nfields[0], Integer.parseInt(nfields[1]), Integer.parseInt(nfields[4]), cast, director);
                mData.insert(movie.getKey(), movie);
                
                for(int i= 0; i < cast.length; i++){
                    for(int j= i+1; j < cast.length; j++)
                    {
                        
                        Person A = cast[i];
                        Person B = cast[j]; 
                        CollG.insertA(A, B, movie);        
                    }
                }
            }
        }
        b.close();
        
       } catch (IOException e) {throw new MovidaFileException(); }
        }
    
    @Override
    public void saveToFile(File f){
        if(f.exists()) f.delete();
		try {
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
                        for(Movie currMovie : mData){
                            bw.write(currMovie.toString());
                            bw.newLine();
                            bw.newLine();
                        }
                        bw.close();
                }
                catch (IOException e) { 
                    throw new MovidaFileException();
                }
    
    }
    
    /**Costo: HashConcatenamento= O(mData.dimension + pData.dimension)
     *        ABR= O(1).
     */        
    @Override
    public void clear(){
        mData.Clear();
        pData.Clear();
        CollG.Clear();
    }
    
    //Costo: O(1)
    @Override
    public int countMovies(){
        if (mData == null)
            return 0;
        else
            return mData.size();       
    }
    
    //Costo: O(1)
    @Override
    public int countPeople(){
        if (pData == null)
            return 0;
        else
            return pData.size();
    }    
    
    /**
     * Funzione che elimina un film dato il suo titolo. Si cancella quindi il film
     * da mData e si rimuove un film dagli attori e dal regista che vi hanno partecipato:
     * se questi ultimi non hanno altri film all'attivo salvati in mData, vengono a
     * loro volta cancellati da pData.
     * @param title Titolo del film da eliminare
     * @return True se il film è eliminato, False altrimenti (title == null o
     * film non presente).
     * Costo: HashConcatenamento= O(mData.dimension/|mData|) nel caso medio;
     *        ABR= O(logn) nel caso medio.
     */
    @Override
    public boolean deleteMovieByTitle(String title){
        if(mData == null)
            return false;
        else
        {            
            Movie toDel = mData.search(title.replace(" ", "").toLowerCase());
            //controllo che il film da eliminare esista
            if(toDel == null){
                return false;
            }
            else
            {
                //aggiusto il numero di collaborazioni del regista e lo elimino in caso non abbia partecipato a nessun film
                Person Dir = toDel.getDirector();
                pData.removeFilm(Dir.getKey());                
                if((Dir.getFilms()) == 0)
                    pData.delete(Dir.getKey());   

                //aggiusto il numero di collaborazioni di ogni attore del cast e lo elimino in caso non abbia partecipato a nessun film
                for(Person actor : toDel.getCast()){
                    pData.removeFilm(actor.getKey());
                    if((actor.getFilms()) == 0){
                        pData.delete(actor.getKey());
                        CollG.deleteN(actor);
                    }
                    for(Person B : toDel.getCast())
                        if(B.getKey().compareTo(actor.getKey()) != 0)
                            CollG.deleteCollab(actor, B, toDel);
                }
                mData.delete(title.replace(" ", "").toLowerCase());
                return true;
            }
        }
    }
    
    /**Costo: HashConcatenamento= O(mData.dimension/|mData|) nel caso medio;
     *        ABR= O(logn) nel caso medio.
     */                
    @Override
    public Movie getMovieByTitle(String title){
        if(mData == null)
            return null;
        else
            return mData.search(title.replace(" ","").toLowerCase());
    }
    
    /**Costo: HashConcatenamento= O(pData.dimension/|pData|) nel caso medio;
     *        ABR= O(logn) nel caso medio.
     */
    @Override
    public Person getPersonByName(String name){
        if(pData == null)
            return null;
        else
            return pData.search(name.replace(" ", "").toLowerCase());
    }
    
    /**
     * Funzione che ritorna un array contenente tutti i film salvati.
     * @return array di film.
     * Costo O(|mData|).
     */
    @Override
    public Movie[] getAllMovies(){
        if((mData == null) || (mData.size() == 0)) 
            return null;
        else{
            Movie[] allMovies = new Movie[mData.size()];
            int i = 0;
            for (Movie m : mData) 
                allMovies[i++]= m;
            return allMovies;
        }
    }
    
    /**
     * Funzione che restituisce un array contenente tutte le persone (attori
     * e registi) salvate.
     * @return array di persone.
     * Costo O(|pData|).
     */
    @Override
    public Person[] getAllPeople(){
        if((pData == null) || (pData.size() == 0)) 
            return null;
        else{
            Person[] allPeople = new Person[pData.size()];
            int i = 0;
            for (Person p : pData) 
                allPeople[i++]= p;
            return allPeople;
        }
    }
 
    /**
     * Funzione che ritorna un array di persone, contenente tutti gli attori
     * salvati in memoria. Viene implementata per rendere più comoda la ricerca
     * dei "MostActiveActors", evtiando che si comparino anche i registi.
     * @return array di attori.
     * Costo O(|pData|).
     */
    public Person[] getAllActors(){
        if((pData == null) || (pData.size() == 0))
            return null;
        else{
            int i= 0;
            for (Person p : pData){
                if(p.isActor())
                    i++;
            }
            if (i == 0)
                return null;
            else{
                Person[] allActors = new Person[i];
                int j= 0;
                for (Person p : pData){
                    if(p.isActor())
                        allActors[j++] = p;
                }
                return allActors;
            }
        }
    }
    /**
     * La funzione scorre prima tutta la struttura dati contenente i film
     * per salvare il numero di film trovati che contengono nel titolo la stringa
     * title, dopodichè alloca un array di dimensione @i (numero di film trovati), 
     * riscorre mData e salva all'interno dell'array i film trovati.
     * @param title Parte di titolo cercata
     * @return Array di film trovati contenenti @title nel titolo
     * Costo: O(|mData|)
     */
    @Override
    public Movie[] searchMoviesByTitle(String title){
        if(mData == null) 
            return new Movie[0];
        int i= 0;
        title= title.replace(" ", "").toLowerCase();
        for(Movie m : mData) if(m.getKey().contains(title)) i++;
        Movie[] found = new Movie[i];
        i= 0;
        for (Movie m: mData) if(m.getKey().contains(title)) found[i++] = m;
        return found;
    }
    
    /**
     * La funzione scorre prima tutta la struttura dati contenente i film
     * per salvare il numero di film usciti nell' anno passato come parametro,
     * dopodichè alloca un array di dimensione @i (numero di film trovati),
     * riscorre mData e salva all'interno dell'array i film trovati
     * @param year Anno di uscita dei film da restituire
     * @return Array di film trovati usciti nell' anno @year
     * Costo: O(|mData|).
     */
    @Override
    public Movie[] searchMoviesInYear(Integer year){
        if(mData == null)
            return new Movie[0];
        int i= 0;
        for (Movie m: mData) 
            if(m.getYear().equals(year)) 
                i++;
        Movie[] found = new Movie[i];
        i= 0;
        for (Movie m: mData) 
            if(m.getYear().equals(year)) 
                found[i++] = m;
        return found;       
    }
        
    /**
     * La funzione scorre prima tutta la struttura dati contenente i film
     * per salvare il numero di film diretti dal regista passato come parametro,
     * dopodichè alloca un array di dimensione @i (numero di film trovati),
     * quindi riscorre mData e salva all' interno dell'array i film trovati
     * @param name Nome del regista cercato
     * @return Array di film trovati diretti dal regista chiamato @name
     * Costo= O(|mData|).
     */
    @Override
    public Movie[] searchMoviesDirectedBy(String name){
        if(mData == null)
            return new Movie[0];
        int i= 0;
        name= name.replace(" ", "").toLowerCase();
        for(Movie m : mData) 
            if(m.isDirectedBy(name))
                i++;
        Movie[] found = new Movie[i];
        i= 0;
        for(Movie m : mData)
            if(m.isDirectedBy(name))
                found[i++]= m;
        return found;
    
    }
    
    /**
     * La funzione scorre prima tutta la struttura dati contenente i film, richiamando
     * la funzione isStarredBy(String name) che ritorna true nel caso l'attore di nome
     * "name" reciti nel film: in tal caso si aumenta @i.
     * Viene quindi creato un array di grandezza @i e riscorrendo tutti i film
     * vengolo salvati solo quelli che presentano nel cast l'attore cercato.
     * @param name Nome dell'attore cercato
     * @return Array di film aventi nel cast l'attore cercato
     * Costo= O(|mData|).
     */
    @Override
    public Movie[] searchMoviesStarredBy (String name){
        if(mData == null)
            return new Movie[0];
        int i= 0;
        name= name.replace(" ", "").toLowerCase();
        for(Movie m : mData){
                if(m.isStarredBy(name)) 
                    i++;
        } 
        Movie[] found = new Movie[i];
        i= 0;
        for(Movie m : mData){
            if(m.isStarredBy(name)) 
                found[i++] = m;
        }    
        return found;    
    }
    
    /**
     * La funzione prima controlla che ci siano dei film memorizzati e che non sia
     * richiesto di ritornare un array con dimensione inferiore a 0. 
     * Viene quindi creato un array temporaneo che sarà riempito con tutti i film 
     * e quindi ordinato grazie al metodo di ordinamento in selezione attuale, secondo 
     * il numero di voti.
     * Viene poi restituito l'intero array se è richiesto un numero di film uguale o superiore
     * a quello dei film totali, altrimenti viene creato un array di dimensione @N
     * riempito con i primi @N film dell'array ordinato.
     * @param N Numero di film richiesti
     * @return Array di @N film più votati
     * Costo: HeapSort = O(Nlogn + n);
     *        SelectionSort = O(N*n).
     */
    @Override
    public Movie[] searchMostVotedMovies (Integer N){
        if(mData == null || N <= 0 || mData.size() == 0)
            return new Movie[0];
        Movie[] tmp= getAllMovies();
        SortAlg.PartSort(tmp, Comparator.comparing(Movie :: getVotes), N);
        if(N >= mData.size())
            return tmp;
        else{
                Movie[] MostVoted = new Movie[N];
                for(int i= 0; i < N; i++)
                    MostVoted[i]= tmp[i];
                return MostVoted;
            }
    }
    
    /**
     * La funzione prima controlla che ci siano dei film memorizzati e che non sia
     * richiesto di ritornare un array con dimensione inferiore a 0. 
     * Viene quindi creato un array temporaneo che sarà riempito con tutti i film 
     * e quindi ordinato grazie al metodo di ordinamento in selezione attuale, secondo
     * l'anno di uscita.
     * Viene poi restituito l'intero array se è richiesto un numero di film uguale o superiore
     * a quello dei film totali, altrimenti viene creato un array di dimensione @N
     * riempito con i primi @N film dell'array ordinato.
     * @param N Numero di film richiesti
     * @return Array di @N film più recenti
     * Costo: HeapSort = O(N*logn + n);
     *        SelectionSort = O(N*n).
     */
    @Override
    public Movie[] searchMostRecentMovies (Integer N){
        if(mData == null || N <= 0 || mData.size() == 0)
            return new Movie[0];
        Movie[] tmp= getAllMovies();
        SortAlg.PartSort(tmp, Comparator.comparing(Movie :: getYear), N);
        if(N >= mData.size())
            return tmp;
        else{
                Movie[] MostRecent = new Movie[N];
                for(int i= 0; i < N; i++)
                    MostRecent[i]= tmp[i];
                return MostRecent;
            }
    }
    
    /**
     * La funzione prima controlla che ci siano persone memorizzate e che non sia
     * richiesto di ritornare un array con dimensione inferiore a 0.
     * Viene quindi creato un array temporaneo che sarà riempito con tutte le persone
     * e quindi ordinato grazie al metodo di ordinamento in selesione attuale, secondo
     * il numero di film in cui ogni attore ha recitato.
     * Viene poi restituito l'intero array se è richiesto un numero di persone uguale o superiore
     * a quello delle persone totali, altrimenti viene creato un array di dimensione @N
     * riempito con le prime @N persone dell'array ordinato.
     * @param N Numero di persone richieste
     * @return Array di @N attori con più film
     * Costo: HeapSort = O(N*logn + n);
     *        SelectionSort = O(N*n).
     */
    @Override
    public Person[] searchMostActiveActors (Integer N){
        if(pData == null || N <= 0 || pData.size() == 0)
            return new Person[0];
        Person[] tmp= getAllActors();
        SortAlg.PartSort(tmp, Comparator.comparing(Person :: getFilms), N);     
        if(N >= tmp.length)
            return tmp;
        else{
                Person[] MostActive = new Person[N];
                for(int i= 0; i < N; i++)
                    MostActive[i]= tmp[i];
                return MostActive;
            }
    }
    
    @Override
    public Person[] getDirectCollaboratorsOf(Person A){
        return CollG.getDirectCollaboratorsOf(A);
    }
    
    @Override
    public Person[] getTeamOf(Person A){
        return CollG.getTeamOf(A);
    }
    
    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person A){
        return CollG.maximizeCollaborationsInTheTeamOf(A);
    }
}
