package commons;

import java.util.ArrayList;

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;
	
	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){
		
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}
        
        //Funzione che aggiunge un dato film alla lista di collaborazioni di due attori.
        public void addFilm(Movie m){
            if(m != null && !movies.contains(m))
                movies.add(m);
        }
        
        //Funzione che elimina un dato film dalla lista di collaborazioni di due attori.
        public void removeFilm(Movie m){
            if(m != null)
                movies.remove(m);
        }
        
        //Numero di film in cui i due attori hanno collaborato.
        public int movsize(){
            return movies.size();
        }
        
        @Override
        public String toString(){
            return(actorA.getName() + " collabora con " + actorB.getName() + " con score " + getScore());
        }
	
}
