/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * 
 */
public class RoundedPanel extends JPanel {
    
    private Color backgroundColor;
    private Color foregroundColor;
    private int cornerRadius;
    private boolean hasOutline;
    private Color outlineColor;
    private boolean outlineIsDotted;

    public RoundedPanel(int radius, Color bgColor, Color fgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        hasOutline = false;
    }

    public RoundedPanel(int radius, Color bgColor, Color fgColor, Color olColor, boolean dottedOutline) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        hasOutline = true;
        outlineColor = olColor;
        outlineIsDotted = dottedOutline;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            graphics.setColor(getBackground());
        }
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
        graphics.setColor(foregroundColor);
        graphics.setStroke(new BasicStroke(8));
        graphics.drawRoundRect(3, 3, width-8, height-8, arcs.width, arcs.height); //paint border

        if (hasOutline) {
            graphics.setColor(outlineColor);
            if (outlineIsDotted) {
                graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            } else {
                graphics.setStroke(new BasicStroke(4));
            }
            graphics.drawRoundRect(6, 6, width-14, height-14, arcs.width, arcs.height); //paint border
        }
    }
    
}
