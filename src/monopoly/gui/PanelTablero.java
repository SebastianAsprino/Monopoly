/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import monopoly.Jugador;
import monopoly.ListaJugadores;

public class PanelTablero extends PanelConFondo {
    ListaJugadores jug;
    public PanelTablero(ListaJugadores jugadores) {
        super("/assets/boardV01.png", 770, 770);
        setSize(770, 770);
        setLocation(1435*23/100, 85);
        setLayout(null);

        jug = jugadores;
        Jugador j = jugadores.head;
        do {
            ubicarFicha(j);
            j = j.siguienteJugador;
        } while(j != jugadores.head);

    }

    private void ubicarFicha(Jugador j) {
        int[] ajuste = new int[2];

        // Ubica ficha de jugador en el tablero
        JLabel ficha = new JLabel();
        String imgName = j.color.getRed()+""+j.color.getGreen()+""+j.color.getBlue();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/assets/jugadores/" + imgName + "_ficha.png"));
        imagen = new ImageIcon(imagen.getImage().getScaledInstance(31, 30, Image.SCALE_SMOOTH));
        ficha.setIcon(imagen);

        // Establece ajustes a coordenadas iniciales dependiendo del jugador
        switch (imgName) {
            case "245209209":
                ajuste[0] = 0;
                ajuste[1] = 0;
                break;
            case "213227242":
                ajuste[0] = 30;
                ajuste[1] = 16;
                break;
            case "225196253":
                ajuste[0] = 0;
                ajuste[1] = 32;
                break;
            case "245236152":
                ajuste[0] = 30;
                ajuste[1] = 48;
                break;
        }

        // Ajusta de acuerdo a si la casilla está vertical u horizontal
        if (j.casillaActual.coordenadas[1]>=669 || j.casillaActual.coordenadas[1]==0) {
            ficha.setLocation(
                j.casillaActual.coordenadas[0]+ajuste[0],
                j.casillaActual.coordenadas[1]+ajuste[1]
            );
        } else {
            ficha.setLocation(
                j.casillaActual.coordenadas[0]+ajuste[1],
                j.casillaActual.coordenadas[1]+ajuste[0]
            );
        }
        ficha.setSize(30, 30);
        ficha.setPreferredSize(new java.awt.Dimension(30, 30));
        ficha.setName(j.nombre);

        add(ficha);
    }

    public void actualizarUbicacion(Jugador jugador) {
        String nombreComp;
        for (int i = 0; i < getComponentCount(); i++) {
            nombreComp = getComponent(i).getName();
            if (nombreComp != null) {
                if (nombreComp.equals(jugador.nombre)) {
                    remove(getComponent(i));
                    ubicarFicha(jugador);

                    validate();
                    repaint();
                }
            }
        }
    }
    
}
