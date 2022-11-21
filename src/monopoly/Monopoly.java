/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * 
 */
public class Monopoly {
    /**
     * 
     */
    public static void main(String[] args) {
        
        
        Juego juego = new Juego();
        juego.crearJugadores();
        juego.inicializarJugadores();
        juego.iniciaJuego();
        juego.finJuego();
        
    }
    
}
