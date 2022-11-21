/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.Arrays;

/**
 *
 * @author gab
 */
public class Casilla {    
    public String nombre;
    public String tipo; //go, carta, propiedad(propiedades, servicios, estaciones), cárcel, impuestos, en blanco, 
    public int[] coordenadas;
    Casilla anteriorCasilla;
    Casilla siguienteCasilla;
    
    public Casilla(String nombre, String tipo, String coordenadas) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.coordenadas = new int[2];
        this.coordenadas[0] = Integer.parseInt(coordenadas.split(",")[0]);
        this.coordenadas[1] = Integer.parseInt(coordenadas.split(",")[1]);
    }

    public String obtenerOpciones(Juego juego) {
        
        if (this.tipo.equals("Cofre Comunitario") || this.tipo.equals("Suerte")) {  // CARTA
            Carta c = (Carta) this;
            if (c.descripcion.equals("Obtén una carta de la pila.")) {
                return "Obtener carta";
            } else {
                return "Usar carta";
            }
            
        } else if (Arrays.asList("Propiedad", "Estación", "Servicio").contains(this.tipo)) {    // PROPIEDAD
            
            Propiedad p = (Propiedad) this;
            if (p.dueño != null) {
                
                // La propiedad tiene dueño.
                if (p.dueño != juego.jugadorActual) {
                    // El jugador actual no es el dueño, entonces debe pagar renta.
                    return "Pagar";
                } else {
                    // El jugador actual es el dueño, entonces pasa al siguiente turno.
                    return "Siguiente turno";
                }
                
            } else {
                // La propiedad no tiene dueño, entonces *puede* comprarse.
                if (p.venta > juego.jugadorActual.dineroTotal) {
                    // Si el precio de venta sobrepasa el dinero del jugador 
                    // no puede comprarla
                    return "Siguiente turno";
                } else {
                    return "Comprar";
                }
            }
        } else if (this.tipo.equals("Impuestos")) { //IMPUESTOS
            // Al jugador actual solo le queda pagar el impuesto.
            return "Pagar";
        } else {    //PARKING GRATUITO o SALIDA o A CÁRCEL
            return "Siguiente turno";
        }
        
    }
    
}
