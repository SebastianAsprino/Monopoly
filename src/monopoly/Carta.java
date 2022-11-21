/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import monopoly.gui.VentanaApp;

/**
 *
 *
 */
public class Carta extends Casilla {
    public String descripcion;

    public Carta(String[] reg) {
        super(reg[0], reg[1], reg[2]);
        this.descripcion = reg[3];
    }
    
    public void obtenerCarta(int numeroRegistro) {
        String registro;
        String[] splitRegistro;
        // https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java
        try (Stream<String> lines = Files.lines(Paths.get(this.tipo + ".txt"))) {
            registro = lines.skip(numeroRegistro).findFirst().get();
            splitRegistro = registro.trim().split("\t");
            this.nombre = splitRegistro[0];
            this.tipo = splitRegistro[1];
            this.descripcion = splitRegistro[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void usarCarta(Jugador jugadorActual, VentanaApp frame, Tablero tab) {
        // NO DEBE USARSE PARA APLICAR EFECTOS DE CARTA SALGA DE LA CÁRCEL
        String[] splitNombre = this.nombre.split(" ");
        switch (splitNombre[0]) {
            case "Recibe":
                int cantidadARecibir;
                if (splitNombre.length > 2) {
                    // Se recibe de todos los jugadores
                    cantidadARecibir = 0;
                    int cantidadACobrar = Integer.parseInt(splitNombre[1].substring(1));
                    Jugador j = jugadorActual.siguienteJugador;
                    while (j != jugadorActual) {
                        if (j.dineroTotal > cantidadACobrar) {
                            j.sumarDinero(-cantidadACobrar);
                            cantidadARecibir += cantidadACobrar;
                        } else {
                            cantidadARecibir += j.dineroTotal;
                            j.establecerEnBancarrota();
                        }
                        j = j.siguienteJugador;
                    }
                } else {
                    cantidadARecibir = Integer.parseInt(splitNombre[1].substring(1));
                }
                jugadorActual.sumarDinero(cantidadARecibir);
                break;
            case "Paga":
                int cantidadAPagar = Integer.parseInt(splitNombre[1].substring(1));
                if (splitNombre.length >= 3) {
                    // Paga una cantidad por cada propiedad
                    if (jugadorActual.propiedades.size() > 0) {
                        cantidadAPagar *= jugadorActual.propiedades.size();
                    } else {
                        cantidadAPagar = 0;
                    }
                }
                if (jugadorActual.dineroTotal > cantidadAPagar) {
                    jugadorActual.sumarDinero(-cantidadAPagar);
                } else {
                    jugadorActual.establecerEnBancarrota();
                }
                break;
            case "Avanza":
                if (this.nombre.equals("Avanza a la Cárcel")) {
                    // Casilla actual se establece como casilla Cárcel
                    jugadorActual.casillaActual = tab.carcel;
                    // Ubicación se actualiza
                    frame.panelJuego.pTablero.actualizarUbicacion(jugadorActual);
                    // Se indica que jugador está encarcelado
                    jugadorActual.turnosEnCarcel = 0;
                } else {
                    String casillaALlegar;
                    // Se obtiene nombre de la casilla a la cual llegar
                    if (splitNombre.length > 3) {
                        casillaALlegar = this.nombre.substring(this.nombre.indexOf(" a ")+3);
                    } else {
                        casillaALlegar = splitNombre[2];
                    }
                    while (!jugadorActual.casillaActual.nombre.equals(casillaALlegar)) {
                        jugadorActual.casillaActual = jugadorActual.casillaActual.siguienteCasilla;
                        if (jugadorActual.casillaActual.nombre.equals("Salida")) {
                            jugadorActual.sumarDinero(200);
                        }
                    }
                    jugadorActual.turnosEnCarcel = -3;
                }
                break;
            case "Regresa":
                if (splitNombre[1].equals("a")) {   // Regresa "a" una casilla en específico
                    String casillaALlegar;
                    if (splitNombre.length > 3) {
                        casillaALlegar = this.nombre.substring(this.nombre.indexOf(" a ")+3);
                    } else {
                        casillaALlegar = splitNombre[2];
                    }
                    while (!jugadorActual.casillaActual.nombre.equals(casillaALlegar)) {
                        jugadorActual.casillaActual = jugadorActual.casillaActual.anteriorCasilla;
                        if (jugadorActual.casillaActual.nombre.equals("Salida")) {
                            jugadorActual.sumarDinero(200);
                        }
                    }
                } else {    // Regresa una cantidad de espacios
                    int casillasMovidas = 0;
                    while (casillasMovidas < Integer.parseInt(splitNombre[1])) {
                        jugadorActual.casillaActual = jugadorActual.casillaActual.anteriorCasilla;
                        casillasMovidas ++;
                        if (jugadorActual.casillaActual.nombre.equals("Salida")) {
                            jugadorActual.sumarDinero(200);
                        }
                    }
                }
                jugadorActual.turnosEnCarcel = -3;
                break;
            case "Salga":   // Salga de la cárcel gratis
                jugadorActual.paseDeCarcel = this.tipo;
                break;
        }
        
        this.nombre = this.tipo;
        this.descripcion = "Obtén una carta de la pila.";
    }

}
