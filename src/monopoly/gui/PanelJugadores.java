/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import monopoly.Juego;
import monopoly.Jugador;

/**
 *
 *
 */
public class PanelJugadores extends JPanel {
    
    public PanelJugadores(Juego juego) {
        setLayout(new FlowLayout(FlowLayout.LEADING, 12, 9));
        setOpaque(false);
        setSize(1435*78/100, 80);
        setPreferredSize(getSize());
        Color borde, centro;
        Jugador jugador = juego.jugadores.head;
        do {
            
            if (jugador != juego.jugadorActual) {
                borde = new Color(jugador.color.getRed()-7, jugador.color.getGreen()-7, jugador.color.getBlue()-7);
            } else {
                borde = Color.white;
            }
            
            if (jugador.dineroTotal == 0) {
                centro = Color.gray;
                borde = Color.lightGray;
            } else {
                centro = jugador.color;
            }
            
            RoundedPanel panel = new RoundedPanel(30, centro, borde, Color.white, false);    //(redondez del borde, color relleno, color borde, linea color, linea es puntuada)
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(getWidth()*100/423, 60));
            
            JLabel labelDinero = new JLabel(Integer.toString(jugador.dineroTotal));
            labelDinero.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));  // Estilo de fuente dinero jugadores
            labelDinero.setForeground(Color.darkGray);  // Color de texto dinero jugadores
            
            ImageIcon imagenJugador;
            String imgName = jugador.color.getRed()+""+jugador.color.getGreen()+""+jugador.color.getBlue();
            if (jugador.turnosEnCarcel >= 0) {
                imagenJugador = new ImageIcon(getClass().getResource("/assets/jugadores/" + imgName + "_jailed.png"));
            } else {
                imagenJugador = new ImageIcon(getClass().getResource("/assets/jugadores/" + imgName + ".png"));
            }
            imagenJugador = new ImageIcon(imagenJugador.getImage().getScaledInstance(31, 30, Image.SCALE_SMOOTH));
            labelDinero.setIcon(imagenJugador);
            labelDinero.setIconTextGap(10);
            panel.add(labelDinero);
            
            add(panel);
            jugador = jugador.siguienteJugador;
        } while(jugador != juego.jugadores.head);
        
    }
    
}
