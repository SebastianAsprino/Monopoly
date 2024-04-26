/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author gab
 */
public class Tablero {
    public Casilla head = null;
    public Casilla carcel;
    public Casilla tail = null;
    
    public Tablero() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("tablero.txt"));
            String line;
            while((line = br.readLine()) != null){
                añadirCasilla(line.trim().split("\t"));
                if (line.trim().split("\t")[1].equals("Cárcel")) {
                    carcel = tail;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void añadirCasilla(String[] splitRegistro) {
        Casilla c = null;
        switch (splitRegistro[1]) {
            case "Propiedad":
                c = (Casilla) new Propiedad(splitRegistro);
                break;
            case "Servicio":
                c =  (Casilla) new Propiedad(splitRegistro);
                break;
            case "Estación":
                c = (Casilla) new Propiedad(splitRegistro);
                break;
            case "Impuestos":
                c = (Casilla) new CasillaDePago(splitRegistro);
                break;
            case "Cárcel":
                c = (Casilla) new CasillaDePago(splitRegistro);
                break;
            case "A Cárcel":
                c = (Casilla) new Casilla(splitRegistro[0], splitRegistro[1], splitRegistro[2]);
                break;
            case "Cofre Comunitario":
                c = (Casilla) new Carta(splitRegistro);
                break;
            case "Suerte":
                c = (Casilla) new Carta(splitRegistro);
                break;
            case "Parking gratuito":
                c = (Casilla) new Casilla(splitRegistro[0], splitRegistro[1], splitRegistro[2]);
                break;
            case "Salida":
                c = (Casilla) new Casilla(splitRegistro[0], splitRegistro[1], splitRegistro[2]);
                break;
        }
        if (head == null) {
            head = c;
            tail = head;
            head.siguienteCasilla = tail;
            head.anteriorCasilla = tail;
            tail.siguienteCasilla = head;
            tail.anteriorCasilla = head;
        } else {
            tail.siguienteCasilla = c;
            c.anteriorCasilla = tail;
            c.siguienteCasilla = head;
            head.anteriorCasilla = c;
            tail = c;
        }
    }

}
