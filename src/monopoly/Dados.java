/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.Random;

/**
 *
 * @author gab
 */
public final class Dados {
    public int valor1;
    public int valor2;
    public int cuentaDobles;
    public Random genAleatorio = new Random();
    
    public Dados() {
        this.tirar();
        this.cuentaDobles = 0;
    }
    
    public void tirar() {
        this.valor1 = genAleatorio.nextInt(6) + 1;
        this.valor2 = genAleatorio.nextInt(6) + 1;
        if (this.valor1 == this.valor2) {
            this.cuentaDobles ++;
        } else {
            this.cuentaDobles = 0;
        }
    }
    
    public int obtenerSuma() {
        return (this.valor1 + this.valor2);
    }
}
