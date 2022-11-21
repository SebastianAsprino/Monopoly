/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import monopoly.gui.PJuego;

/**
 *
 *
 */
public class Jugador {
    public String nombre;
    public Color color;
    public int[][] billetes;
    public int dineroTotal;

    ArrayList<Propiedad> propiedades;
    public String paseDeCarcel;

    public volatile int turnosEnCarcel;

    public volatile Casilla casillaActual;
    public Jugador siguienteJugador;
    
    public Jugador(Color color) {
        int[][] bill = {
            {500, 2},
            {200, 1},
            {100, 2},
            {50, 1},
            {20, 1},
            {10, 2},
            {5, 1},
            {1,5},
        };
        this.billetes = bill;
        this.dineroTotal = 0;
        this.color = color;
        this.propiedades = new ArrayList<Propiedad>();
        this.turnosEnCarcel = -1;
    }
    
    public void mover(int espacios, PJuego pj) {
        for (int k = 0; k < pj.pBotones.getComponentCount(); k++) {
            pj.pBotones.getComponent(k).setEnabled(false);
        }
                
        while (espacios > 0) {
            // La casilla actual del jugador pasa a ser la siguiente casilla
            this.casillaActual = casillaActual.siguienteCasilla;

            // Los espacios que faltan por recorrer disminuyen
            espacios --;

            // Se realiza el desplazamiento de la ficha desde su posición actual
            // hasta la posición de la casilla nueva
            pj.pTablero.actualizarUbicacion(this);
            try {
                Thread.sleep(450);  // Permite que se muestre el recorrido pausado
            } catch (InterruptedException ex) {}
            
            if (this.casillaActual.tipo.equals("Salida")) {
                this.sumarDinero(200);
            }
        }
        while (espacios < 0) {
            // La casilla actual del jugador pasa a ser la anterior casilla
            this.casillaActual = casillaActual.anteriorCasilla;

            // Los espacios que faltan por recorrer disminuyen
            espacios ++;

            // Se realiza el desplazamiento de la ficha desde su posición actual
            // hasta la posición de la casilla nueva
            pj.pTablero.actualizarUbicacion(this);
            try {
                Thread.sleep(450);  // Permite que se muestre el recorrido pausado
            } catch (InterruptedException ex) {}
            
        }
        
    }
    
    public void sumarDinero(int cantidad) {
        int signo, i;
        
        if (cantidad < 0) {
            signo = -1;
            cantidad *= -1;
        } else {
            signo = 1;
        }
        
        if ((signo==-1 && this.dineroTotal>cantidad) || (signo==1)) {
            
            while (cantidad>0) {
                i = 0;
                
                while (i<8 && cantidad>0) {
                    while (cantidad>=this.billetes[i][0] && this.billetes[i][1]+signo>=0) {
                        // Se suma/resta un billete a la cantidad de billetes de la 
                        // denominacion actual.
                        this.billetes[i][1] += signo;
                        // El dinero total aumenta/disminuye de acuerdo a la 
                        // denominación.
                        this.dineroTotal += this.billetes[i][0]*signo;
                        // La cantidad que falta por sumar/restar disminuye de 
                        // acuerdo a la denominación.
                        cantidad -= this.billetes[i][0];
                    }
                    i ++;
                }

                if (cantidad > 0) {
                    // No se pudo completar la resta porque el jugador no tiene 
                    // suficientes billetes de ciertas denominaciones.
                    i = 0;
                    while (i<8 && this.billetes[i][1]<=0) {
                        i ++;
                    }
                    // Se resta un billete de la mayor denominación que 
                    // tenga y el dinero total disminuye de forma acorde.
                    this.billetes[i][1] --;
                    this.dineroTotal -= this.billetes[i][0];
                    // En lugar de restar, ahora queda por añadir al dinero
                    // del jugador.
                    cantidad = this.billetes[i][0] - cantidad;
                    signo = 1;
                }
            }
        }
                
    }
    
    public void añadirPropiedad(Propiedad nuevaPropiedad) {
        this.propiedades.add(nuevaPropiedad);
    }
    
    public void establecerEnBancarrota() {
        // Establece todos los valores relacionados al dinero a cero
        this.dineroTotal = 0;
        for (int i = 0; i < this.billetes.length; i++) {
            this.billetes[i][1] = 0;
        }
        // Vacía la lista de propiedades del jugador y establece el dueño de cada una como nulo
        Iterator<Propiedad> p = this.propiedades.iterator();
        while (p.hasNext()) {
            Propiedad q = p.next();
            q.dueño = null;
        }
        this.propiedades.clear();
        //"Devuelve" la carta Salga de la cárcel gratis a la pila
        this.paseDeCarcel = null;
        
    }
    
    public int cuentaPropiedadesDeTipo(String tipo) {
        int c = 0;
        Iterator<Propiedad> p = this.propiedades.iterator();
        while (p.hasNext()) {
            if (p.next().tipo.equals(tipo)) {
                c ++;
            }
        }
        return c;
    }
    
    public boolean tieneMonopolio(Color color) {
        int c = 0;
        Iterator<Propiedad> p = this.propiedades.iterator();
        while (p.hasNext()) {
            if (p.next().colorConjunto.equals(color)) {
                c ++;
            }
        }
        if (color == new Color(149,84,54) | color == new Color(0,92,176)) {   //Conjuntos con 2 propiedades
            if (c == 2) {
                return true;
            }
        } else if (c == 3) {
            return true;
        }
        return false;
    }

}
