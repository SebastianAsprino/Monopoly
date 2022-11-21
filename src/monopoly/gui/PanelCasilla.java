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
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import monopoly.Carta;
import monopoly.Casilla;
import monopoly.CasillaDePago;
import monopoly.Propiedad;


/**
 *
 * 
 */
public class PanelCasilla extends RoundedPanel {
    
    class Spacer extends JPanel {
        Spacer() {
            super();
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(25, 4));
        }
        Spacer(int y) {
            super();
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(25, y));
        }
    }
    
    public PanelCasilla(Casilla casilla) {
        super(20, Color.white, Color.white, new Color(159, 213, 209), true);    //(redondez del borde, color relleno, color borde, linea color, linea es puntuada)
        setBounds(15, 85, 295, 300);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setOpaque(false);
        
        JLabel labelNombre = new JLabel(casilla.nombre.toUpperCase(), SwingConstants.CENTER);
        
        if (!(casilla.tipo.equals("Cofre Comunitario") || casilla.tipo.equals("Suerte"))) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 100, 8));
            add(new Spacer(15));
            
            if (casilla.nombre.length()>23) {
                labelNombre.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));  // Estilo de fuente de nombre para resto de casillas
            } else {
                labelNombre.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));  // Estilo de fuente de nombre
            }
            labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
            
            if (!casilla.nombre.equals("Salida")) {
                add(labelNombre);
            } else {
                add(new Spacer(18));
            }
            
            if (Arrays.asList("Propiedad", "Servicio", "Estación").contains(casilla.tipo)) {
                Propiedad p = (Propiedad)casilla;
                
                if (casilla.tipo.equals("Propiedad")) {
                    add(new Spacer(3));

                    JPanel linea = new JPanel();
                    linea.setPreferredSize(new Dimension(getWidth(), 14));
                    linea.setBackground(p.colorConjunto);
                    add(linea);

                    add(new Spacer());
                }

                String s = "";
                Color ic;
                if (p.dueño == null) {
                    ic = Color.lightGray;
                    s = "POR COMPRAR";
                } else {
                    ic = p.dueño.color;
                    s = "COMPRADA";
                }
                RoundedPanel indicacionComprada = new RoundedPanel(20, ic, ic);
                indicacionComprada.setOpaque(false);
                indicacionComprada.setPreferredSize(new Dimension(150, 40));
                indicacionComprada.setLayout(new GridLayout(1, 1));

                JLabel labelComprada = new JLabel(s, SwingConstants.CENTER);
                labelComprada.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));    // Estilo de fuente de texto COMPRADA/POR COMPRAR
                indicacionComprada.add(labelComprada);
                
                switch(casilla.tipo) {
                    case "Propiedad":
                        s = "";
                        if (p.venta/p.renta>=1) {
                            s += "  ";
                        }
                        add(new JLabel(String.format("Costo de venta            $%d", p.venta), SwingConstants.CENTER));
                        add(new JLabel(String.format("Costo de renta            %s$%d", s, p.renta), SwingConstants.CENTER));

                        add(new Spacer());
                        
                        add(indicacionComprada);
                        
                        add(new Spacer());

                        for (int i = 0; i < this.getComponentCount(); i++) {
                            if (this.getComponent(i).getClass().equals(JLabel.class)) {
                                JLabel labelComponent = (JLabel)this.getComponent(i);
                                if (!labelComponent.getText().equals(casilla.nombre.toUpperCase())) {
                                    this.getComponent(i).setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));    // Estilo de fuente de texto venta y renta
                                }
                            }
                        }

                        add(new Spacer());

                        s = "<html><center>Si el dueño posee todos los títulos<br>del conjunto, la renta se duplica.</center></html>";
                        if (p.dueño != null) {
                            if (p.dueño.tieneMonopolio(p.colorConjunto)) {
                                s = "<html><center>El dueño posee todos los títulos del<br>conjunto, así que la renta se duplica.</center></html>";
                            }
                        }
                        add(new JLabel(s) {
                            @Override
                            public void setFont(Font font) {
                                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
                            }
                        });

                        break;
                    case "Servicio":
                        
                        add(new JLabel("Costo: $"+p.venta, SwingConstants.CENTER) {
                            @Override
                            public void setFont(Font font) {
                                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
                            }
                        });
                        
                        s = "<html>Si el dueño posee un Servicio,"
                                + "<br>la renta es 4 veces los dados."
                                + "<br>  "
                                + "<br>Si el dueño posee dos Servicios,"
                                + "<br>la renta es 10 veces los dados.</html>";
                        add(new JLabel(s));
                        
                        add(new Spacer(1));
                        
                        add(indicacionComprada);
                        
                        s = "";
                        if (p.dueño != null) {
                            if (p.dueño.cuentaPropiedadesDeTipo("Servicio")==1) {
                                s = "<html>El dueño posee un Servicio.</html>";
                            } else {
                                s = "<html>El dueño posee dos Servicios.</html>";
                            }
                        } else {
                            s = "<html>Al momento, no se cobra renta.</html>";
                        }
                        add(new JLabel(s));
                        
                        for (int i = 0; i < this.getComponentCount(); i++) {
                            if (this.getComponent(i).getClass().equals(JLabel.class)) {
                                JLabel labelComponent = (JLabel)this.getComponent(i);
                                if (!labelComponent.getText().equals(casilla.nombre.toUpperCase()) && !labelComponent.getText().equals("Costo: $"+p.venta)) {
                                    this.getComponent(i).setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));    // Estilo de fuente de texto venta y renta
                                }
                            }
                        }
                        
                        break;

                    case "Estación":
                        
                        add(new JLabel("Costo: $"+p.venta, SwingConstants.CENTER) {
                            @Override
                            public void setFont(Font font) {
                                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                            }
                        });
                        
                        for (int i = 1; i <= 4; i++) {
                            if (i < 4) {
                                add(new JLabel(String.format("Renta para %d                           $%d", i, p.renta*i), SwingConstants.CENTER));
                            } else {
                                add(new JLabel(String.format("Renta para %d                         $%d", i, p.renta*i), SwingConstants.CENTER));
                            }
                        }
                        
                        add(indicacionComprada);

                        for (int i = 0; i < this.getComponentCount(); i++) {
                            if (this.getComponent(i).getClass().equals(JLabel.class)) {
                                JLabel labelComponent = (JLabel)this.getComponent(i);
                                if (!labelComponent.getText().equals(casilla.nombre.toUpperCase()) 
                                        && !labelComponent.getText().equals("Costo: $"+p.venta)) {
                                    this.getComponent(i).setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));    // Estilo de fuente de texto venta y renta
                                }
                            }
                        }

                        s = "<html><center>La renta aumenta dependiendo de<br>cuántas estaciones tenga el dueño.</center></html>";
                        if (p.dueño != null) {
                            if (p.dueño.cuentaPropiedadesDeTipo("Estación")>1) {
                                s = "<html><center>Se cobrará la renta correspondiente<br>a "+p.dueño.cuentaPropiedadesDeTipo("Estación")+" estaciones.</center></html>";
                            } else {
                                s = "<html><center>Se cobrará la renta correspondiente<br>a una estación.</center></html>";
                            }
                        }
                        add(new JLabel(s) {
                            @Override
                            public void setFont(Font font) {
                                super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
                            }
                        });
                        
                        break;
                }
            } else {                
                add(new Spacer(5));
                
                JLabel label = new JLabel();
                ImageIcon imagen = new ImageIcon(getClass().getResource("/assets/icons/" + casilla.nombre + ".png"));
                imagen = new ImageIcon(imagen.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
                label.setIcon(imagen);
                add(label);
                
                if (casilla.nombre.equals("Impuestos") || casilla.nombre.equals("Impuestos de lujo")) {
                    CasillaDePago cdp = (CasillaDePago) casilla;
                    add(new JLabel("Debe pagar $" + cdp.pago + "") {
                        @Override
                        public void setFont(Font font) {
                            super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                        }
                    });
                } else if (casilla.nombre.equals("Cárcel")) {
                    add(new JLabel("<html><center>Sólo de visita.<center></html>") {
                        @Override
                        public void setFont(Font font) {
                            super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                        }
                    });
                } else if (casilla.nombre.equals("Salida")) {
                    add(new JLabel("<html><center>Cada vez que pase por aquí<br>recibirá $200.<center></html>") {
                        @Override
                        public void setFont(Font font) {
                            super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                        }
                    });
                } else if (casilla.nombre.equals("Parking gratuito")) {
                    add(new JLabel("<html><center>Sólo de paso.<center></html>") {
                        @Override
                        public void setFont(Font font) {
                            super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                        }
                    });
                }
                
            }
            
        } else {
                    
            Carta cc = (Carta) casilla;
            
            RoundedPanel bgp;
            if (cc.tipo.equals("Cofre Comunitario")) {
                bgp = new RoundedPanel(20, new Color(255, 131, 196), new Color(255, 131, 196));
            } else {
                bgp = new RoundedPanel(20, new Color(112, 201, 255), new Color(112, 201, 255));
            }
            
            bgp.setPreferredSize(new Dimension(285, 290));
            bgp.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 8));
            bgp.setOpaque(false);
            add(bgp);

            bgp.add(new Spacer(3));

            RoundedPanel panelTipoCarta = new RoundedPanel(17, Color.WHITE, Color.WHITE);
            panelTipoCarta.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
            panelTipoCarta.add(new JLabel(cc.tipo.toUpperCase()) {
                @Override
                public void setFont(Font font) {
                    super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
                }
                @Override
                public void setForeground(Color color) {
                    super.setForeground(Color.black);
                }
            });
            panelTipoCarta.setSize(bgp.getWidth()-40, 40);
            panelTipoCarta.setPreferredSize(new Dimension(240, 40));
            panelTipoCarta.setOpaque(false);
            bgp.add(panelTipoCarta);

            if (!cc.descripcion.equals("Obtén una carta de la pila.")) {
                bgp.add(new Spacer(8));
                if (casilla.nombre.length()>24) {
                    String[] splitNombre = casilla.nombre.split(" ");
                    int palabrasPrimeraLinea = splitNombre.length;
                    int numLetras;

                    do {
                        palabrasPrimeraLinea --;
                        numLetras = 0;
                        for (int i = 0; i < palabrasPrimeraLinea; i++) {
                            numLetras += splitNombre[i].length();
                        }
                    } while (numLetras > 24);

                    int ip = 0;
                    String st = "<html><center>";
                    while (ip < palabrasPrimeraLinea) {
                        st += splitNombre[ip] + " ";
                        ip ++;
                    }

                    st = st.trim() + "<br>";
                    while (ip < splitNombre.length) {
                        st += splitNombre[ip] + " ";
                        ip ++;
                    }

                    st = st.trim() + "</center></html>";

                    labelNombre.setText(st);
                } else {
                    labelNombre.setText(casilla.nombre);
                }
                labelNombre.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));  // Estilo de fuente de nombre para Cofre Comunitario o Suerte
                bgp.add(labelNombre);
            }

            bgp.add(new Spacer(5));

            bgp.add(new JLabel(cc.descripcion) {
                @Override
                public void setFont(Font font) {
                    super.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
                }
                @Override
                public void setForeground(Color color) {
                    super.setForeground(Color.white);
                }
            });
            
        }
            
        
            
                
    }
             
    
}
