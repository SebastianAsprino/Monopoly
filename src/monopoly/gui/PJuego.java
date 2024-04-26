/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;

import javax.swing.JPanel;
import monopoly.Juego;

/**
 *
 * 
 */
public class PJuego extends JPanel {
    PanelJugadores pJugadores;
    PanelBilletes pBilletes;
    PanelCasilla pCasilla;
    public PanelBotones pBotones;
    public PanelTablero pTablero;

    Juego juego;

    public PJuego(Juego j) {
        setLayout(null);
        setSize(1435, 895);
        setBackground(new Color(159, 213, 209));    // Color fondo de la vista de juego
        this.juego = j;

        this.pTablero = new PanelTablero(juego.jugadores);
        this.add(this.pTablero);

        this.pBotones = new PanelBotones(this.juego);
        this.add(this.pBotones);
        
        this.pJugadores = new PanelJugadores(this.juego);
        this.add(this.pJugadores);
        
        this.pBilletes = new PanelBilletes(this.juego.jugadorActual);
        this.add(this.pBilletes);

        this.pCasilla = new PanelCasilla(this.juego.jugadorActual.casillaActual);
        this.add(this.pCasilla);
    }
    
    public void actualizarVisionJugador() {
        this.remove(this.pBilletes);
        this.remove(this.pCasilla);
        this.remove(this.pJugadores);
        
        this.pBilletes = new PanelBilletes(this.juego.jugadorActual);
        this.add(this.pBilletes);
        
        this.pCasilla = new PanelCasilla(this.juego.jugadorActual.casillaActual);
        this.add(this.pCasilla);
        
        this.pJugadores = new PanelJugadores(this.juego);
        this.add(this.pJugadores);
        
        this.revalidate();
        this.repaint();
    }

    public void enviarCambios(String opciones) {
        this.actualizarVisionJugador();
        this.pBotones.cambiarBotones(opciones);
    }

    public void enviarCambios(String[] opciones) {
        this.actualizarVisionJugador();
        this.pBotones.cambiarBotones(opciones);
    }
    
}
