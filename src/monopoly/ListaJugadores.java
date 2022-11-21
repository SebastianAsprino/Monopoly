/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Color;

/**
 *
 * 
 */
public class ListaJugadores {
    
    public Jugador head = null;
    Jugador tail = null;
    int idColor = 0;
    
    public void añadirJugador() {
        Color[] colores = {
            new Color(245, 209, 209),   // Rosado
            new Color(213, 227, 242),   // Azul
            new Color(225, 196, 253),   // Morado
            new Color(245, 236, 152)    // Amarillo
        };
        Jugador nuevoJugador = new Jugador(colores[idColor]);
        if (head == null) {
            head = nuevoJugador;
            tail = head;
            head.siguienteJugador = tail;
        } else {
            tail.siguienteJugador = nuevoJugador;
            tail = nuevoJugador;
        }
        tail.siguienteJugador = head;
        idColor ++;
    }
    
    public void ordenarMayorAMenor() {
        Jugador actual = this.head, j;
        Color auxColor;
        String auxString;
        
        do {
            j = actual.siguienteJugador;
            do {
                if (j.dineroTotal > actual.dineroTotal) {
                    auxString = actual.nombre;
                    actual.nombre = j.nombre;
                    j.nombre = auxString;
                    
                    auxColor = actual.color;
                    actual.color = j.color;
                    j.color = auxColor;
                    
                    auxString = Integer.toString(actual.dineroTotal);
                    actual.dineroTotal = j.dineroTotal;
                    j.dineroTotal = Integer.parseInt(auxString);
                }
                j = j.siguienteJugador;
            } while(j != this.head);
            actual = actual.siguienteJugador;
        } while(actual != this.tail);
    }
    
    public void asignarDineroInicial() {
        Jugador jugadorActual = this.head;
        do {
            jugadorActual.dineroTotal = 1500;
            jugadorActual = jugadorActual.siguienteJugador;
        } while(jugadorActual != this.head);
    }

    public boolean paseDeCarcelEstaOcupado(String tipoCarta) {
        Jugador j = head;
        do {
            if (j.paseDeCarcel != null) {
                if (j.paseDeCarcel.equals(tipoCarta)) {
                    return true;
                }
            }
            j = j.siguienteJugador;
        } while(j!=head);
        return false;
    }
    
}
