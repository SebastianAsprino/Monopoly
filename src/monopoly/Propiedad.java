/*
 * To change this license header, choose License Headers in Project Propiedad.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Color;

/**
 *
 * 
 */
public class Propiedad extends Casilla {
    public int venta;
    public int renta;
    public Jugador dueño;
    public Color colorConjunto;

    public Propiedad(String[] registro) {
        super(registro[0], registro[1], registro[2]);
        this.venta =  Integer.parseInt(registro[3]);
        this.renta =  Integer.parseInt(registro[4]);
        String[] RGB = registro[5].split(",");
        this.colorConjunto = new Color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2]));
    }
    
    public void cobrarRenta(Juego juego) {
        int cantidadACobrar = 0;
        switch (this.tipo) {
            case "Propiedad":
                if (this.dueño.tieneMonopolio(this.colorConjunto)) {
                    cantidadACobrar = this.renta*2;
                } else {
                    cantidadACobrar = this.renta;
                }
                break;
            case "Estación":
                cantidadACobrar = this.renta*this.dueño.cuentaPropiedadesDeTipo("Estación");
                break;
            case "Servicio":
                int cantidadServicios = this.dueño.cuentaPropiedadesDeTipo("Servicio");
                if (cantidadServicios==1) {
                    cantidadACobrar = juego.dados.obtenerSuma()*4;
                } else {
                    cantidadACobrar = juego.dados.obtenerSuma()*10;
                }
                break;
        }
        if (juego.jugadorActual.dineroTotal > cantidadACobrar) {
            juego.jugadorActual.sumarDinero(-cantidadACobrar);
            this.dueño.sumarDinero(cantidadACobrar);
        } else {
            this.dueño.sumarDinero(juego.jugadorActual.dineroTotal);
            juego.jugadorActual.establecerEnBancarrota();
        }
        
    }

    public void comprar(Jugador jugadorActual) {
        
        if (jugadorActual.dineroTotal > this.venta) {
            this.dueño = jugadorActual;
            jugadorActual.sumarDinero(-this.venta);
            jugadorActual.añadirPropiedad(this);
        }
        
    }
    
}
