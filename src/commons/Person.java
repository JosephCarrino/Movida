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
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: name � usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person {

	final private String name;
        private int films;
        private final boolean actdir;
	
	public Person(String name, boolean actdir) {
		this.name = name;
                this.films= 0;
                this.actdir= actdir;
	}
	
	public String getName(){
		return this.name;
	}
        
        public void newFilm() {
            films++;
        }
        
        /**
         * Metodo che ritorna il nome della persona senza spazi e in minuscolo
         * @return nome senza spazi
         */
        public String getKey() {
		return name.replace(" ", "").toLowerCase();
	}
        
        public int getFilms() {
            return this.films;
        }
        
        public void removeFilm() {
            this.films--;
        }
        
        public boolean isActor(){
            return !actdir;
        }
        
        @Override
        public String toString(){
            return this.name + " che ha recitato in " + this.films + " film";
        }
	
        
}
