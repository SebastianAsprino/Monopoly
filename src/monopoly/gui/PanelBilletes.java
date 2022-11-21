/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import monopoly.Jugador;

/**
 *
 * 
 */
public class PanelBilletes extends JPanel {

    public PanelBilletes(Jugador jugadorActual) {
        setBackground(new Color(159, 213, 209));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(1435*32/100, 895-50);
        setPreferredSize(getSize());
        
        for (int i = 0; i < 8; i++) {
            JLayeredPane panelDenominacion =  new JLayeredPane();
            panelDenominacion.setSize(getWidth(), getHeight()/10);
            panelDenominacion.setPreferredSize(panelDenominacion.getSize());
            panelDenominacion.setLayout(null);

            for (int j = 0; j < jugadorActual.billetes[i][1]; j++) {
                JLabel label = new JLabel();
                // Accede a la imagen en carpetas assets -> billetes -> billete(número de denominacion).jpg
                ImageIcon imagen = new ImageIcon(getClass().getResource("/assets/billetes/billete" + jugadorActual.billetes[i][0] + ".PNG"));
                imagen = new ImageIcon(imagen.getImage().getScaledInstance(
                        160,    // Ancho de la imagen de cada billete
                        panelDenominacion.getHeight(), 
                        Image.SCALE_SMOOTH
                ));
                label.setIcon(imagen);
                label.setBounds(j*12, 0, 160, panelDenominacion.getHeight());

                panelDenominacion.add(label);
            }
            panelDenominacion.setOpaque(false);

            add(panelDenominacion);
        }
        
        JPanel panelCarta =  new JPanel();
        panelCarta.setSize(getWidth(), getHeight()/9);
        panelCarta.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelCarta.setPreferredSize(panelCarta.getSize());
        panelCarta.setOpaque(false);
        
        if (jugadorActual.paseDeCarcel != null) {
            // Representación pase para salir de cárcel
            JPanel paseCarcel = new JPanel();
            paseCarcel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
            
            if (jugadorActual.paseDeCarcel.equals("Cofre Comunitario")) {
                paseCarcel.setBackground(new Color(255, 131, 196)); // Color de fondo
            } else {
                paseCarcel.setBackground(new Color(112, 201, 255)); // Color de fondo
            }
            
            paseCarcel.setSize(200, 90);    // Tamaño
            paseCarcel.setPreferredSize(paseCarcel.getSize());
            paseCarcel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 4));
            paseCarcel.add(new JLabel("<html><center><br>Salga de la<br>cárcel, gratis<br><center</html>") {
                @Override
                public void setFont(Font font) {
                    super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
                }
                @Override
                public void setForeground(Color color) {
                    super.setForeground(Color.white);
                }
            });    // Texto
            panelCarta.add(paseCarcel);
        }
        add(panelCarta);
        setLocation(1435*78/100, 10);
    }
     
}