/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package commons;

/**
 * 
 * Interfaccia usata per descrivere le operazioni di 
 * ricerca nell'applicazione Movida
 * 
 */
public interface IMovidaSearch {
	
	/**
	 * Ricerca film per titolo. 
	 * 
	 * Restituisce i film il cui titolo contiene la stringa 
	 * title passata come parametro.
	 * 
	 * Per il match esatto usare il metodo getMovieByTitle(String s)
	 * 
	 * Restituisce un vettore vuoto se nessun film rispetta il criterio di ricerca.  
	 *  
	 * @param title titolo del film da cercare
	 * @return array di film
	 */
	public Movie[] searchMoviesByTitle(String title);
	
	/**
	 * Ricerca film per anno. 
	 * 
	 * Restituisce i film usciti in sala nell'anno 
	 * anno passato come parametro.
	 *  
	 * Restituisce un vettore vuoto se nessun film rispetta il criterio di ricerca.  
	 *  
	 * @param year anno del film da cercare
	 * @return array di film
	 */
	public Movie[] searchMoviesInYear(Integer year);

	/**
	 * Ricerca film per regista. 
	 * 
	 * Restituisce i film diretti dal regista il cui nome � passato come parametro. 
	 *  
	 * Restituisce un vettore vuoto se nessun film rispetta il criterio di ricerca.  
	 *  
	 * @param name regista del film da cercare 
	 * @return array di film
	 */
	public Movie[] searchMoviesDirectedBy(String name);

	/**
	 * Ricerca film per attore. 
	 * 
	 * Restituisce i film a cui ha partecipato come attore 
	 * la persona il cui nome � passato come parametro. 
	 *  
	 * Restituisce un vettore vuoto se nessun film rispetta il criterio di ricerca.  
	 *  
	 * @param name attore coinvolto nel film da cercare 
	 * @return array di film
	 */
	public Movie[] searchMoviesStarredBy(String name);

	/**
	 * Ricerca film pi� votati. 
	 * 
	 * Restituisce gli N film che hanno 
	 * ricevuto pi� voti, in ordine decrescente di voti.
	 * 
	 * Se il numero di film totali � minore di N restituisce tutti i film,
	 * comunque in ordine.
	 *   
	 * @param N numero di film che la ricerca deve resistuire  
	 * @return array di film
	 */
	public Movie[] searchMostVotedMovies(Integer N);

	/**
	 * Ricerca film pi� recenti. 
	 * 
	 * Restituisce gli N film pi� recenti,
	 * in base all'anno di uscita in sala confrontato con l'anno corrente.
	 * 
	 * Se il numero di film totali � minore di N restituisce tutti i film,
	 * comunque in ordine.
	 *   
	 * @param N numero di film che la ricerca deve resistuire
	 * @return array di film
	 */
	public Movie[] searchMostRecentMovies(Integer N);

	/**
	 * Ricerca gli attori pi� attivi. 
	 * 
	 * Restituisce gli N attori che hanno partecipato al numero 
	 * pi� alto di film
	 * 
	 * Se il numero di attori � minore di N restituisce tutti gli attori,
	 * comunque in ordine.
	 *
	 * @param N numero di attori che la ricerca deve resistuire
	 * @return array di attori
	 */
	public Person[] searchMostActiveActors(Integer N);
}
