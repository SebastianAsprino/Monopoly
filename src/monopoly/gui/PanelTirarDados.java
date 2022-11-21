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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import monopoly.Dados;
import monopoly.Juego;
import monopoly.Jugador;

/**
 *
 * 
 */
public class PanelTirarDados extends JPanel {
    JButton boton;
    JTextArea inputNombre;
    PanelDados pDados;
    Juego juego;
    String imgName;
    
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
    
    public PanelTirarDados(Jugador jugador, Juego juego) {
        this.juego = juego;
        this.setLayout(new GridBagLayout());
        this.setSize(1435, 895);
        this.setBackground(new Color(159, 213, 209));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 3;
        this.imgName = jugador.color.getRed()+""+jugador.color.getGreen()+""+jugador.color.getBlue();
        ImageIcon imagenJugador = new ImageIcon(getClass().getResource("/assets/jugadores/" + this.imgName + ".png"));
        imagenJugador = new ImageIcon(imagenJugador.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH));
        add(new JLabel(imagenJugador), gbc);
        
        gbc.gridy = 3;
        gbc.gridheight = 2;
        add(new JLabel("<html><center>Jugador, ingresa tu nombre y tira"
                + "<br>los dados para asignarte tu turno."
                + "<br>(Si no ingresas un nombre,"
                + "<br>te asignaremos uno).</center></html>") {
                    @Override
                    public void setFont(Font font) {
                        super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
                    }
                    @Override
                    public void setForeground(Color color) {
                        super.setForeground(Color.white);
                    }
                }, gbc);
        
        gbc.gridy = 5;
        gbc.gridheight = 1;
        this.inputNombre = new JTextArea();
        this.inputNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        this.inputNombre.setEditable(true);
        this.inputNombre.setBackground(jugador.color);
        add(this.inputNombre, gbc);
        
        gbc.gridy = 6;
        this.boton = new JButton("Tirar dados");
        this.boton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        this.boton.setForeground(Color.white);
        this.boton.setBackground(this.getBackground().darker());
        this.boton.setFocusable(false);
        this.boton.setBorderPainted(false);
        this.boton.addActionListener(this::botonAccion);
        add(this.boton, gbc);
        
        gbc.gridy = 7;
        gbc.gridheight = 1;
        this.pDados = new PanelDados(this.juego.dados);
        add(this.pDados, gbc);
        
    }
    
    public void botonAccion(ActionEvent e) {
        this.boton.setEnabled(false);
        if (this.boton.getText().equals("Tirar dados")) {
            this.juego.dados.tirar(); // Tira dados

            // Elimina pDados actual
            this.remove(this.pDados);

            // Ubicación nuevo panelDados
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(5,5,5,5);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridheight = 1;
            gbc.gridwidth = 3;

            // Añade nuevo panelDados (con valores actualizados)
            this.pDados = new PanelDados(this.juego.dados);
            this.add(this.pDados, gbc);
            this.boton.setEnabled(true);
            this.boton.setText("Siguiente");

            this.revalidate();
            this.repaint();
            
        } else {
            if (this.inputNombre.getText().trim().length()<1 || this.inputNombre.getText()==null) {
                switch (this.imgName) {
                    case "245209209":
                        this.juego.jugadorActual.nombre = "MASTER";
                        break;
                    case "213227242":
                        this.juego.jugadorActual.nombre = "EL4X4";
                        break;
                    case "225196253":
                        this.juego.jugadorActual.nombre = "FIRULAIS";
                        break;
                    case "245236152":
                        this.juego.jugadorActual.nombre = "BOLA";
                        break;
                }
            } else {
                this.juego.jugadorActual.nombre = this.inputNombre.getText().trim();
            }
            
            this.revalidate();
            this.repaint();
            this.removeAll();
            
            // Indica que no se está a la espera de que el jugador presione un botón
            this.juego.enEspera = false;
        }
            
    }

        
}
