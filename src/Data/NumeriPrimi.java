/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 * Classe che contiene diversi numeri primi di vari ordini di grandezza per poter
 * costruire una tabella hash efficiente. 
 * @author Giuseppe Carrino
 */
public class NumeriPrimi {
    final static int[] primi= {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317,
                        196613, 393241, 786433};
    
    /**
     * Funzione che dato un intero, restituisce il più piccolo numero primo maggiore
     * di esso (tra quelli contenuti nell'array "primi").
     * @param n Numero a cui avvicinarsi.
     * @return Un numero primi contenuto in "primi".
     */
    public static int nPrimo(int n){
        if(n < primi[0])
            return primi[0];
        if(n > primi[primi.length-1])
            return primi[primi.length-1];
        int i= 0;
        while(primi[i] < n)
            i++;
        return primi[i];
    }
    
}
