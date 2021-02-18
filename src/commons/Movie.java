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
 * Classe usata per rappresentare un film
 * nell'applicazione Movida.
 * 
 * Un film identificato in modo univoco dal titolo 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * La classe pu essere modicata o estesa ma deve implementare tutti i metodi getter
 * per recupare le informazioni caratterizzanti di un film.
 * 
 */
public class Movie {
	
	private String title;
	private Integer year;
	private Integer votes;
	private Person[] cast;
	private Person director;
	
	public Movie(String title, Integer year, Integer votes,
			Person[] cast, Person director) {
		this.title = title;
		this.year = year;
		this.votes = votes;
		this.cast = cast;
		this.director = director;
	}
        public void setCast(Person[] cast){
                this.cast= cast;
        }
	public String getTitle() {
		return this.title;
	}

	public Integer getYear() {
		return this.year;
	}

	public Integer getVotes() {
		return this.votes;
	}

	public Person[] getCast() {
		return this.cast;
	}

	public Person getDirector() {
		return this.director;
	}
        
        //Ritorna true se nel cast del film è presente un attore col nome
        //passato come parametro.
        public boolean isStarredBy(String name){
            for(Person p : cast)
                if(p.getKey().equals(name))
                    return true;
            return false;
        }
        
        public boolean isDirectedBy(String name){
            return(director.getKey().equals(name));
        }
        
        /**
         * Metodo che ritorna il titolo del film senza spazi e in minuscolo per usarlo come chiave di comparazione
         * @return titolo senza spazi
         */
        public String getKey() {
                return title.replace(" ", "").toLowerCase();
        }
        
        /**
         * Funzione necessaria per restituire un toString simile alla scrittura 
         * dei film canonica.
         * @return Una stringa contenente tutti gli attori separati da una ', '.
         */
        public String stringC(){
            String tmp= "", actor;
            int len= cast.length;
            for(int i= 0; i < len - 1; i++){
                actor=cast[i].getName().concat(", ");
                tmp= tmp.concat(actor);
            }
            tmp= tmp.concat(cast[len-1].getName());
            return tmp;
        }
        
        @Override
        public String toString(){
            return "Title: " + title + "\nYear: " + year + "\nDirector: " + director.getName() + "\nCast: " + stringC() + "\nVotes: " + votes;
        }
	
	
}
