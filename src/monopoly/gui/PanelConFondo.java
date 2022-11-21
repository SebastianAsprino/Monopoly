/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author gab
 */
public class PanelConFondo extends JPanel {    //https://tips4java.wordpress.com/2008/10/12/background-panel/
    private Image fondo = null;

    public PanelConFondo(String path, int x, int y) {
        super();
        ImageIcon ii = new ImageIcon(getClass().getResource(path));
        Image f = ii.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
        fondo = f;
        fondo.getWidth(this);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(fondo.getWidth(this), fondo.getHeight(this));
    }
}

