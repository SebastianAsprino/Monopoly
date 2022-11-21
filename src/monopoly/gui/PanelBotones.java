/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import monopoly.Carta;
import monopoly.CasillaDePago;
import monopoly.Dados;
import monopoly.Juego;
import monopoly.Propiedad;

/**
 *
 * 
 * 
 */
public class PanelBotones extends JPanel {
    Juego juego;
    JPanel panelDados;
    JButton botonDados, botonComprar, botonPagar, botonObtenerCarta, 
    botonUsarCarta, botonSigTurno;
    
    class PanelDados extends JPanel {
        PanelDados(Dados dados) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            ImageIcon ii = new ImageIcon(getClass().getResource("/assets/dado/cara" + dados.valor1 + ".jpg"));
            ii = new ImageIcon(ii.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            add(new JLabel(ii));

            ii = new ImageIcon(getClass().getResource("/assets/dado/cara" + dados.valor2 + ".jpg"));
            ii = new ImageIcon(ii.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            add(new JLabel(ii));

            setOpaque(false);
        }
    }
    
    public PanelBotones(Juego j) {
        setLayout(new GridBagLayout());
        setBounds(15, 400, 295, 350);
        setOpaque(false);
        this.juego = j;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        
        this.panelDados = new PanelDados(this.juego.dados);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(this.panelDados, gbc);
        
        this.botonDados = new JButton("Tirar dados");
        this.botonDados.addActionListener(this::botonDadosAccion);   // Acción que se realiza al hacer click
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(this.botonDados, gbc);
        
        this.botonComprar = new JButton("Comprar");
        this.botonComprar.addActionListener(this::botonComprarAccion);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(this.botonComprar, gbc);
        
        this.botonPagar = new JButton("Pagar");
        this.botonPagar.addActionListener(this::botonPagarAccion);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(this.botonPagar, gbc);
        
        this.botonObtenerCarta = new JButton("Obtener carta");
        this.botonObtenerCarta.addActionListener(this::botonObtenerCartaAccion);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(this.botonObtenerCarta, gbc);
        
        this.botonUsarCarta = new JButton("Usar carta");
        this.botonUsarCarta.addActionListener(this::botonUsarCartaAccion);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(this.botonUsarCarta, gbc);
        
        this.botonSigTurno = new JButton("Siguiente turno");
        this.botonSigTurno.addActionListener(this::botonSigTurnoAccion);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(this.botonSigTurno, gbc);

        for (int i = 0; i < this.getComponentCount(); i++) {
            Component c = this.getComponent(i);
            if (c.getClass() == JButton.class) {
                JButton b = (JButton) c;
                b.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));   // Estilo de fuente botones
                b.setBackground(new Color(102, 04, 204));    // Color botones
                b.setMargin(new Insets(5, 5, 5, 5));
                b.setForeground(Color.white);
                b.setBorderPainted(false);
                b.setFocusPainted(false);
            }
        }
    }

    public void cambiarBotones(String textoBotonDisponible) {
        for (int i = 0; i < this.getComponentCount(); i++) {
            Component c = this.getComponent(i);
            if (c.getClass() == JButton.class) {
                JButton b = (JButton) c;
                if (!b.getText().equals(textoBotonDisponible)) {
                    b.setBackground(new Color(102, 04, 204));    // Color botones
                    b.setEnabled(false);
                } else {
                    b.setBackground(new Color(102, 04, 204));    // Color botones
                    b.setEnabled(true);
                }
            }
        }
    }

    public void cambiarBotones(String[] textoBotonDisponible) {
        for (int i = 0; i < this.getComponentCount(); i++) {
            Component c = this.getComponent(i);
            if (c.getClass() == JButton.class) {
                JButton b = (JButton) c;
                if (!Arrays.asList(textoBotonDisponible).contains(b.getText())) {
                    b.setBackground(new Color(102, 04, 204));    // Color botones
                    b.setEnabled(false);
                } else {
                    b.setBackground(new Color(102, 04, 204));    // Color botones
                    b.setEnabled(true);
                }
            }
        }
    }

    public void botonDadosAccion(ActionEvent e) {
        this.botonDados.setEnabled(false);
        this.juego.dados.tirar(); // Tira dados

        // Elimina panelDados actual
        this.remove(this.panelDados);

        // Ubicación nuevo panelDados
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        
        // Añade nuevo panelDados (con valores actualizados)
        this.panelDados = new PanelDados(this.juego.dados);
        this.add(this.panelDados, gbc);
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();

        // Indica que no se está a la espera de que el jugador presione un botón
        this.juego.enEspera = false;
    }

    public void botonComprarAccion(ActionEvent e) {
        this.botonComprar.setEnabled(false);
        Propiedad p = (Propiedad) juego.jugadorActual.casillaActual;
        p.comprar(juego.jugadorActual);
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();
        
        // Indica que no se está a la espera del jugador actual
        this.juego.enEspera = false;
    }

    public void botonPagarAccion(ActionEvent e) {
        this.botonPagar.setEnabled(false);
        if (Arrays.asList("Propiedad", "Estación", "Servicio").contains(this.juego.jugadorActual.casillaActual.tipo)) {
            // Jugador actual está en una casilla Propiedad; debe pagar renta.
            Propiedad p = (Propiedad) juego.jugadorActual.casillaActual;
            p.cobrarRenta(juego);
        } else {
            // Jugador actual está en casilla Cárcel o Impuestos.
            CasillaDePago cp = (CasillaDePago) juego.jugadorActual.casillaActual;
            cp.cobrarPago(juego.jugadorActual);
        }

        if (this.juego.jugadorActual.casillaActual.tipo.equals("Cárcel")) {
            this.juego.jugadorActual.turnosEnCarcel = -2;
        }
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();

        // Indica que no se está a la espera del jugador actual
        this.juego.enEspera = false;
    }

    public void botonUsarCartaAccion(ActionEvent e) {
        this.botonUsarCarta.setEnabled(false);
        if (this.juego.jugadorActual.turnosEnCarcel >= 0) {
            // Si está encarcelado, "usar carta" se refiere a usar el pase de cárcel
            this.juego.jugadorActual.paseDeCarcel = null;
            this.juego.jugadorActual.turnosEnCarcel = -2;
        } else {
            // Si no está encarcelado, "usar carta" se refiere a efectuar cambios
            // correspondientes a una carta obtenida en una casilla Carta
            Carta c = (Carta) this.juego.jugadorActual.casillaActual;
            c.usarCarta(this.juego.jugadorActual, this.juego.guiFrame, this.juego.tablero);
            if (c.nombre.startsWith("Avanza") || c.nombre.startsWith("Regresa")) {
                this.juego.guiFrame.panelJuego.pTablero.actualizarUbicacion(this.juego.jugadorActual);
            }
        }
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();

        // Indica que no se está a la espera del jugador actual
        this.juego.enEspera = false;
    }

    public void botonObtenerCartaAccion(ActionEvent e) {
        this.botonObtenerCarta.setEnabled(false);
        Carta c = (Carta) this.juego.jugadorActual.casillaActual;
        // Genera un número aleatorio indicando la línea de la carta a obtener
        int n;
        if (this.juego.jugadores.paseDeCarcelEstaOcupado(c.tipo)) {
            // Si pase de cárcel (línea 16) está ocupado, no puede obtenerlo
            n = (new Random()).nextInt(15);
        } else {
            n = (new Random()).nextInt(16);
        }
        // Obtiene carta en dicha posición en el archivo
        c.obtenerCarta(n);
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();

        // Indica que no se está a la espera del jugador actual
        this.juego.enEspera = false;
    }

    public void botonSigTurnoAccion(ActionEvent e) {
        this.botonSigTurno.setEnabled(false);
        // Siguiente turno significa tanto siguiente turno del mismo jugador
        // (por turno extendido) como pasar al turno del jugador siguiente
        
        if (!this.juego.turnoSeProlonga) {
            // Si el turno del jugador actual no se prolonga, se pasa a sgte jugadot
            this.juego.jugadorActual = this.juego.siguienteTurno();
            this.juego.dados.cuentaDobles = 0;
        }
        
        this.juego.guiFrame.panelJuego.actualizarVisionJugador();
        
        this.juego.turnoTerminado = true;
        // Indica que no se está a la espera del jugador actual
        this.juego.enEspera = false;
    }

}
