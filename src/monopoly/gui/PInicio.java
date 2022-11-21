/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import monopoly.Juego;

/**
 *
 * 
 */
public class PInicio extends JPanel {
    JComboBox jcb;
    Juego j;
    
    public PInicio(Juego juego) {
        setSize(1435, 895);
        setBackground(new Color(159, 213, 209));
        setLocation(0, 0);
        setLayout(new FlowLayout(FlowLayout.CENTER, 500, 0));
        j = juego;
        
        add(new JLabel("<html><br><br></html>") {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 50));
            }
            @Override
            public void setForeground(Color color) {
                super.setForeground(new Color(159, 220, 248));
            }
        });
        
        add(new JLabel("<html>CRYPTOCOINPOLY</html>") {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 70));
            }
            @Override
            public void setForeground(Color color) {
                super.setForeground(Color.white);
            }
        });
        
        add(new JLabel("<html><br></br></html>") {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 50));
            }
            @Override
            public void setForeground(Color color) {
                super.setForeground(Color.white);
            }
        });
        
        add(new JLabel("<html></html>") {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 150));
            }
            @Override
            public void setForeground(Color color) {
                super.setForeground(new Color(240, 117, 164));
            }
        });
        
        add(new JLabel("<html><br><br><center>Escoja un número de jugadores<br>para empezar<br><br><br></center></html>") {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
            }
            @Override
            public void setForeground(Color color) {
                super.setForeground(new Color(36, 37, 41));
            }
        });
        
        jcb = new JComboBox<>();
        jcb.setModel(new DefaultComboBoxModel<>(new String[] {"0", "2", "3", "4"}));
        jcb.setSize(400, 30);
        jcb.setPreferredSize(jcb.getSize());
        jcb.setBackground(Color.white);
        jcb.setEditable(false);
        jcb.setForeground(Color.black);
        jcb.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
        jcb.setSelectedItem(jcb.getItemAt(0));
        jcb.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                j.cantidadJugadores = Integer.parseInt((String) jcb.getSelectedItem());
                setVisible(false);
                removeAll();
            }
        });
        add(jcb);
    }
    
}
