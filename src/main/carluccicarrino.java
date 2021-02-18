package main;

import java.io.*;
import Core.*;
import commons.*;

public class carluccicarrino {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database Core = new Database();
        System.out.println("ESEMPI DI FUNZIONI DI MOVIDA");
        System.out.println("");

        MapImplementation Map = MapImplementation.HashConcatenamento;
        if (Core.setMap(Map)) {
            System.out.println("Si seleziona il dizionario " + Map + ".\n");
        } else {
            System.out.println("Mappa non implementata, si seleziona automaticamente ABR.\n");
            Core.setMap(MapImplementation.ABR);
        }

        SortingAlgorithm Sort = SortingAlgorithm.HeapSort;
        if (Core.setSort(Sort)) {
            System.out.println("Si seleziona l'algoritmo di sorting " + Sort + ".\n");
        } else {
            System.out.println("Algoritmo non implementato, si seleziona automaticamente HeapSort.\n");
            Core.setSort(SortingAlgorithm.HeapSort);
        }

        File loadFile = new File("C:\\users\\josep\\Desktop\\UniJo\\Algoritmi\\carluccicarrino\\src\\IO\\esempio.txt");
        File saveFile = new File("C:\\users\\josep\\Desktop\\UniJo\\Algoritmi\\carluccicarrino\\src\\IO\\savefile.txt");

        Core.clear();
        System.out.println("STRUTTURE DATI SVUOTATE\n");

        Core.loadFromFile(loadFile);
        System.out.println("CARICAMENTO DEI DATI\n");

        System.out.println("Sono presenti " + Core.countMovies() + " film.\n");
        System.out.println("Lista titoli:\n");
        for (Movie m : Core.getAllMovies()) {
            System.out.println(m.getTitle());
        }

        System.out.println("\nSono presenti " + Core.countPeople() + " persone, di cui " + Core.getAllActors().length + " attori.\n");
        System.out.println("Lista persone:\n");
        for (Person p : Core.getAllPeople()) {
            System.out.println(p.getName());
        }

        String getM = "Taxi Driver";
        System.out.println("\nRicerca del film: " + getM + ".");
        if (Core.getMovieByTitle(getM) != null) {
            System.out.println("Dati del film: \n\n" + Core.getMovieByTitle(getM));
        } else {
            System.out.println("Film non presente.");
        }

        String getP = "Bruce Willis";
        System.out.println("\nRicerca della persona: " + getP + ".");
        if (Core.getPersonByName(getP) != null) {
            System.out.println("Persona di nome " + Core.getPersonByName(getP) + ", trovata.");
        } else {
            System.out.println("Persona non presente.");
        }

        String search = "The";
        System.out.println("\nRicerca dei film contenenti la stringa '" + search + "' nel titolo.");
        int i = 0;
        if (Core.searchMoviesByTitle(search).length != 0) {
            for (Movie m : Core.searchMoviesByTitle(search)) {
                i++;
                System.out.println(i + ". " + m.getTitle() + ".");
            }
        } else {
            System.out.println("Nessun film trovato.");
        }

        String delete = "The Fugitives";
        System.out.println("\nEliminazione film chiamato '" + delete + "'.");
        if (Core.deleteMovieByTitle(delete)) {
            System.out.println("Eliminato con successo.");
        } else {
            System.out.println("Film non trovato");   
        }
        
        String search1 = "The";
        System.out.println("\nRicerca dei film contenenti la stringa '" + search1 + "' nel titolo.");
        i = 0;
        if (Core.searchMoviesByTitle(search1).length != 0) {
            for (Movie m : Core.searchMoviesByTitle(search1)) {
                i++;
                System.out.println(i + ". " + m.getTitle() + ".");
            }
        } else {
            System.out.println("Nessun film trovato.");
        }
        
        int year = 1988;
        System.out.println("\nRicerca dei film usciti nel " + year + ".");
        i= 0;
        if(Core.searchMoviesInYear(year).length != 0){
            for (Movie m : Core.searchMoviesInYear(year)){
                i++;
                System.out.println(i + ". " + m.getTitle() + ".");
            }
        } else {
            System.out.println("Nessun film trovato.");
        }
        
        String director = "Robert Zemeckis";
        System.out.println("\nRicerca del film diretti da " + director + ".");
        i= 0;
        if(Core.searchMoviesDirectedBy(director).length != 0){
            for (Movie m : Core.searchMoviesDirectedBy(director)){
                i++;
                System.out.println(i + ". " + m.getTitle() + ".");
            }                
        } else {
            System.out.println("Nessun film trovato.");
        }
        
        String actor = "Bruce Willis";
        System.out.println("\nRicerca dei film con attore " + actor + ".");
        i= 0;
        if(Core.searchMoviesStarredBy(actor).length != 0){
            for (Movie m : Core.searchMoviesStarredBy(actor)){
                i++;
                System.out.println(i + ". " + m.getTitle() + ".");
            }
        } else {
            System.out.println("Nessun film trovato.");
        }
        
        int N = 3;
        System.out.println("\nStampa dei primi " + N + " film col maggior numero di voti.");
        i= 0;
        if(Core.searchMostVotedMovies(N).length > 0){
            for (Movie m : Core.searchMostVotedMovies(N)){
                i++;
                System.out.println(i + ". " + m.getTitle() + " con " + m.getVotes() + " voti.");
            }
        } else {
            System.out.println("Inserire numero maggiore di 0.");
        }
        
        N = 40;
        System.out.println("\nStampa degli ultimi " + N + " film usciti.");
        i = 0;
        if(Core.searchMostRecentMovies(N).length > 0){
            for (Movie m : Core.searchMostRecentMovies(N)){
                i++;
                System.out.println(i + ". " + m.getTitle() + " uscito nel " + m.getYear() + ".");
            }
        } else {
            System.out.println("Inserire numero maggiore di 0.");
        }
        
        N = 7;
        System.out.println("\nStampa dei " + N + " attori con maggior numero di film all'attivo.");
        i= 0;
        if(Core.searchMostActiveActors(N).length > 0){
            for (Person p : Core.searchMostActiveActors(N)){
                i++;
                System.out.println(i + ". " + p + ".");
            }
        } else {
            System.out.println("Inserire numero maggiore di 0.");
        }
        
        System.out.println("\nTESTING COLLABORATION\n\n");
        
        Person actorColl = Core.getPersonByName("William Baldwin");
        if(actorColl != null){
            System.out.println("\nStampa dei collaboratori diretti di " + actorColl.getName() + ".");
            i= 0;
            if(Core.getDirectCollaboratorsOf(actorColl) != null){
                for(Person p : Core.getDirectCollaboratorsOf(actorColl)){
                    i++;
                    System.out.println(i + ". " + p.getName() + ".");
                }
            } else {
                System.out.println("Collaboratori assenti.");
            }
        } else {
            System.out.println("Persona non trovata.");
        }
        
        Person actorTeam = actorColl;
        if(actorTeam != null){
            System.out.println("\nStampa del team di " + actorTeam.getName() + ".");
            i= 0;
            if(Core.getTeamOf(actorTeam) != null){
                for(Person p : Core.getTeamOf(actorTeam)){
                    i++;
                    System.out.println(i + ". " + p.getName() + ".");
                }
            } else {
                System.out.println("Team assente.");
            }
        } else {
            System.out.println("Persona non trovata");
        }
        
        Person actorMax = actorTeam;
        if(actorMax != null){
            System.out.println("\nStampa delle collaborazioni migliori del team di " + actorMax.getName() + ".");
            i= 0;
            if(Core.maximizeCollaborationsInTheTeamOf(actorMax) != null){
                for(Collaboration c : Core.maximizeCollaborationsInTheTeamOf(actorMax)){
                    i++;
                    System.out.println(i + ". " + c + ".");      
                }
            } else {
                System.out.println("Team assente");
            }
        } else {
            System.out.println("Persona non trovata.");
        }
         
        System.out.println("\nSALVATAGGIO DATI\n\n");
        Core.saveToFile(saveFile);
    }
}
