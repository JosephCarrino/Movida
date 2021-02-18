  *** PROGETTO MOVIDA ***

Strutture Dati realizzate: Albero Binario di Ricerca, Hash Concatenamento
Algoritmi di ordinamento realizzati: Heap Sort, Selection Sort

------------------------------------------------------------------------------------
				       Main

Il main è la parte del progetto relativa al testing ed è un esempio di esecuzione
delle funzioni implementate. In particolare viene utilizzata ogni funzione presente nel
progetto. Si utilizzano uno dei due dizionari ed uno dei due algoritmi di sorting.
 
------------------------------------------------------------------------------------
				      Commons

Pacchetto contenente le interfacce fornite come base per la realizzazione del progetto.
Sono state effettuate alcune modifiche minori nelle classi Movie, Person e Collaboration.
Le modifiche includono anche alcuni metodi toString, utili alla stampa delle informazioni.

------------------------------------------------------------------------------------
					Core
Nel Core è inclusa la classe Database, che implementa tutte le interfacce del progetto,
nello specifico legge le informazioni da file, le scrive su file, include le funzioni 
di inserimento, cancellazione e ricerca.

------------------------------------------------------------------------------------
					Data

E' qui presente una classe astratta Data, che funziona da scheletro per i dizionari da
implementare. La classe astratta Data include una variabile n che tiene traccia del numero
di elementi.
I dizionari da implementare estendono la classe Data.

L'ABR si basa sull'utilizzo di istanze della classe Nodo. Quest'ultima classe è utile perchè
viene dichiarata come Java Generics, quindi capace di contenere qualsiasi tipo di oggetto
(sia Movie che Person, ma anche altro).
La comparazione utilizzata è quella fornita da Java tramite variabili di tipo Comparable.   
Le funzioni principali dell'ABR sono insert(), search() e delete(). 
L'implementazione realizzata offre anche la possibilità di utilizzare il costrutto foreach,
data la creazione di un iterator. L'iterator dell'ABR è realizzato tramite uno Stack, 
che contiene solo le 3 principali operazioni: isEmpty(), push(), pop().

L'Hash Concatenamento si basa sull'implementazione di liste non ordinate. Le liste sono 
semplici nell'utilizzo ma poco efficienti nella ricerca, tuttavia si è cercato di implementare
una funzione di hashing che non creasse liste troppo lunghe (grazie anche alla creazione di
una tabella che avesse per lunghezza un numero primo). La classe HashConcatenamento quindi
è necessaria solo per effettuare l'hashing degli oggetti passati, per poi richiamare le varie
funzioni (insert(), search(), delete()) sulle liste corrispondenti. E' stata implementata anche
una sottoclasse iterator che scorre gli elementi partendo dalla prima lista fino all'ultima in
modo da poter richiamare funzioni come il foreach. 

------------------------------------------------------------------------------------
					Grafi

Ogni nodo, in questa implementazione, viene interpretata come una persona; ogni nodo ha una
sua lista di adiacenza, ovvero una lista di archi. Ogni arco è rappresentato dal nodo di 
arrivo e contiene la collaborazione tra i 2 attori collegati. Come richiesto, il peso di ogni
arco è uguale al valore della collaborazione. Il grafo, di conseguenza, è rappresentato da
una lista di nodi. 
Le strutture dati ausiliarie utilizzate sono, infatti, la ListaNonOrdinata (già usata per 
l'HashConcatenamento), la LinkedList della libreria java.util (utilizzata come coda nella
funzione getTeamOf()) e la PriorityQueue, anch'essa della libreria java.util, utilizzata
nella funzione maximizeCollaborationsInTheTeamof().
La funzione getTeamOf() è stata interpretata come una BFS, dato che questo algoritmo di visita
permette di rappresentare una determinata partizione del grafo. 
La funzione maximizeCollaborationsInTheTeamof() è stata invece interpretata come il calcolo
di un Maximum Spanning Tree, e si è quindi usato un algoritmo di Prim riadattato.

------------------------------------------------------------------------------------
					Sorting

Si è realizzata una classe astratta Sorting, che viene estesa da entrambi gli algoritmi di 
ordinamento utilizzati. 

L'algoritmo di Selection Sort viene realizzato in maniera diversa rispetto alla sua
implementazione canonica. In particolare, visto che le query di ricerca richieste volevano,
molto spesso, un ordinamento decrescente, si è scelto di realizzare un Selection Sort che 
ordinasse dal maggiore al minore, sempre rispetto alle chiavi di comparazione (si utilizzano
anche qui i campi di tipo Comparable). Anche il sorting parziale è realizzato in questo modo. 

L'algoritmo di Heap Sort si basa su maxHeap, e quindi anche in questo caso vi è un ordinamento
decrescente. E' stato necessario utilizzare un array ausiliario per rendere la funzione fruibile
alla classe database in maniera diretta; questo poichè l'algoritmo HeapSort lavora su un array
che non considera l'elemento di indice 0. 

------------------------------------------------------------------------------------
					  IO

IO è una cartella contenente il file di input di Movida e il file di output. 

------------------------------------------------------------------------------------
Nella cartella carluccicarrino/dist/javadoc è presente tutta la documentazione per avere informazioni
su ogni specifica funzione. 


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Anno Accademico: 2019-2020					    |
Sorgente e readme realizzati da Umberto Carlucci e Giuseppe Carrino.|
							            |
                                                                    |
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

