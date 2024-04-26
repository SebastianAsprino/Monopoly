/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import monopoly.Juego;
import monopoly.Jugador;

/**
 *
 * 
 */
public class PanelFinJuego extends JPanel {
    JFrame frame;
    
    public PanelFinJuego(Juego j, JFrame parent) {
        setLayout(new GridBagLayout());
        setSize(1435, 895);
        setBackground(new Color(159, 213, 209));
        frame = parent;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        add(new JLabel("<html><center>¡Felicidades, " 
                + j.jugadorActual.nombre + "! Has ganado.<br><br>Terminaste el juego con $" + j.jugadorActual.dineroTotal 
                + " mientras tus<br>contrincantes quedaron en bancarrota.<br><br></center></html>") {
                    @Override
                    public void setFont(Font font) {
                        super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
                    }
                    @Override
                    public void setForeground(Color color) {
                        super.setForeground(Color.white);
                    }
                }, gbc);
        
        gbc.gridy = 2;
        gbc.gridheight = 2;
        String imgName = j.jugadorActual.color.getRed()+""+j.jugadorActual.color.getGreen()+""+j.jugadorActual.color.getBlue();
        ImageIcon imagenJugador = new ImageIcon(getClass().getResource("/assets/jugadores/" + imgName + ".png"));
        imagenJugador = new ImageIcon(imagenJugador.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        add(new JLabel(imagenJugador), gbc);
        
        if (j.cantidadJugadores>2) {
            gbc.gridy = 5;
            gbc.gridheight = 1;
            Jugador jugador = j.jugadorActual.siguienteJugador;
            JPanel pjug = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
            pjug.setOpaque(false);
            do {
                imgName = j.jugadorActual.color.getRed()+""+j.jugadorActual.color.getGreen()+""+j.jugadorActual.color.getBlue();
                ImageIcon ii = new ImageIcon(getClass().getResource("/assets/jugadores/" + imgName + ".png"));
                ii = new ImageIcon(ii.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                pjug.add(new JLabel(ii));
                jugador = jugador.siguienteJugador;
            } while (jugador != j.jugadorActual);
            add(pjug, gbc);
        }
        
        gbc.gridy = 6;
        gbc.gridheight = 3;
        add(new JPanel() {
            @Override
            public void setOpaque(boolean b) {
                super.setOpaque(false);
            }
        }, gbc);
        
        gbc.gridy = 10;
        gbc.gridheight = 1;
        JButton botonSalir = new JButton("Salir del juego");
        botonSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
        botonSalir.setBackground(j.jugadorActual.color);
        botonSalir.setFocusPainted(false);
        botonSalir.setBorderPainted(false);
        botonSalir.addActionListener(this::botonSalirAccion);
        add(botonSalir, gbc);
        
    }
    
    public void botonSalirAccion(ActionEvent e) {
        frame.dispose();
    }
    
}
