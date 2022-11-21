/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import monopoly.Juego;
import monopoly.Jugador;

/**
 *
 *
 */
public class VentanaApp extends JFrame {
    public volatile PJuego panelJuego;

    public VentanaApp() {
        super("CRYPTOCOINPOLY");
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}
        this.setSize(1435, 895);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void añadirPanelJuego(Juego juego) {
        this.panelJuego = new PJuego(juego);
        this.add(panelJuego);
        this.setVisible(true);
    }
    
    public void eliminarPanelJuego() {
        this.panelJuego.removeAll();
        this.remove(this.panelJuego);
    }

    public void enviarCambios(String opciones) {
        this.panelJuego.enviarCambios(opciones);
    }

    public void enviarCambios(String[] opciones) {
        this.panelJuego.enviarCambios(opciones);
        
    }
    
    public void añadirPanelFinJuego(Juego juego) {
        PanelFinJuego pfj = new PanelFinJuego(juego, this);
        this.add(pfj);
        this.setVisible(true);
    }
    
    public void añadirPanelTirarDados(Jugador jugador, Juego juego) {
        PanelTirarDados ptd = new PanelTirarDados(jugador, juego);
        this.add(ptd);
        this.setVisible(true);
    }
    
    public void obtenerNumJugadores(Juego juego) {
        PInicio p = new PInicio(juego);
        this.add(p);
        this.setVisible(true);
    }
    
}
