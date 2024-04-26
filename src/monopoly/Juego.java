/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.Arrays;

import monopoly.gui.VentanaApp;

/**
 *
 * @author gab
 */
public class Juego {
    
    public Tablero tablero = new Tablero();
    public ListaJugadores jugadores = new ListaJugadores();
    public Dados dados = new Dados();
    public volatile Jugador jugadorActual;
    public VentanaApp guiFrame = new VentanaApp();
    public volatile boolean enEspera, turnoSeProlonga, turnoTerminado;
    public volatile int cantidadJugadores;
    
    public void crearJugadores() {
        cantidadJugadores = 0;
        guiFrame.obtenerNumJugadores(this);
        
        while (cantidadJugadores == 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
        
        for (int i = 0; i < cantidadJugadores; i++)
            jugadores.añadirJugador();
        jugadorActual = jugadores.head;
    }
    
    public void inicializarJugadores() {
        // Se pide que cada jugador tire los dados, se ordenan, se asigna dinero inicial.
        int opc;
        jugadorActual = jugadores.head;
        do {
            // Se asigna la casilla actual a Salida
            jugadorActual.casillaActual = tablero.head;
            
            enEspera = true;
            
            guiFrame.añadirPanelTirarDados(jugadorActual, this);
            
            // Espera a que el jugador tire los dados.
            while (enEspera) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {}
            }
            
            jugadorActual.dineroTotal = dados.obtenerSuma();
            
            jugadorActual = jugadorActual.siguienteJugador;
            
        } while(jugadorActual != jugadores.head);
        jugadores.ordenarMayorAMenor();
        jugadores.asignarDineroInicial();
        dados.cuentaDobles = 0;
    }
    
    public Jugador siguienteTurno() {
        Jugador siguiente = jugadorActual.siguienteJugador;
        while (siguiente.dineroTotal <= 0) {
            siguiente = siguiente.siguienteJugador;
        }
        return siguiente;
    }
    
    public void iniciaJuego() {
        String[] opc = new String[3];
        boolean juegoTermina = false;

        guiFrame.añadirPanelJuego(this);
        do {
            
            do {
                
                turnoSeProlonga = false;
                turnoTerminado = false;

                // Establecer vista del jugador actual.
                if (jugadorActual.turnosEnCarcel >= 0) {
                    // Si el jugador llegó a la cárcel en al menos su turno pasado,
                    // tiene la opción de tirar dados, pagar su multa o usar carta
                    // "Salga de la Cárcel gratis" si la tiene
                    enEspera = true;
                    opc[0] = "Tirar dados";
                    opc[1] = "Pagar";
                    if (jugadorActual.paseDeCarcel != null) {
                        opc[2] = "Usar carta";
                    } else {
                        opc[2] = " ";
                    }
                    guiFrame.enviarCambios(opc);
                    // Espera a que el jugador escoja opción.
                    while (enEspera) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {}
                    }
                } else {
                    if (jugadorActual.turnosEnCarcel == -2) {
                        dados.cuentaDobles = 0;
                        jugadorActual.turnosEnCarcel = -1;
                    } else if (jugadorActual.turnosEnCarcel == -1) {
                        // Si el jugador no está en la cárcel, solo tiene la opción 
                        // de tirar los dados
                        enEspera = true;
                        guiFrame.enviarCambios("Tirar dados");
                        // Espera a que el jugador tire los dados.
                        while (enEspera) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {}
                        }
                    }
                    // Si llega con turnosEnCarcel == -3, es porque debe evaluarse 
                    // la casilla actual sin hacer mayores cambios
                }
                
                // Si está encarcelado, se verifican los pasos a tomar
                if (jugadorActual.casillaActual.tipo.equals("Cárcel") && jugadorActual.turnosEnCarcel!=-1) {
                    if (jugadorActual.turnosEnCarcel >= 0) {
                        
                        if (dados.cuentaDobles == 1) {
                            
                            jugadorActual.turnosEnCarcel = -1;
                            // Se establece número de dobles en cero
                            dados.cuentaDobles = 0;
                            
                        } else {
                            
                            if (jugadorActual.turnosEnCarcel >= 2) {
                                
                                // Perdió oportunidad de salir con dobles; debe pagar multa
                                // Solo tiene opción de pagar multa
                                enEspera = true;
                                guiFrame.enviarCambios("Pagar");
                                // Espera a que el jugador presione botón.
                                while (enEspera) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {}
                                }
                                jugadorActual.turnosEnCarcel = -1;
                                
                            } else {
                                jugadorActual.turnosEnCarcel ++;
                            }
                            
                            // Pasa al sgte turno
                            enEspera = true;
                            guiFrame.enviarCambios("Siguiente turno");
                            // Espera a que el jugador presione botón.
                            while (enEspera) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {}
                            }
                                
                        }
                    } else {
                        // Jugador no está encarcelado
                        jugadorActual.turnosEnCarcel = -1;
                        
                        // Pasa al sgte turno
                        enEspera = true;
                        guiFrame.enviarCambios("Siguiente turno");
                        // Espera a que el jugador presione botón.
                        while (enEspera) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {}
                        }
                    }
                    
                }
                    
                if (dados.cuentaDobles > 0) {    // El jugador ha sacado al menos un doble
                    if (dados.cuentaDobles < 3) {   // El jugador lleva menos de 3 dobles
                        // El jugador tira los dados otra vez finalizado su turno
                        turnoSeProlonga = true;
                    } else {
                        // El jugador es enviado a la cárcel y termina su turno
                        jugadorActual.casillaActual = tablero.carcel;
                        jugadorActual.turnosEnCarcel = 0;
                        guiFrame.panelJuego.pTablero.actualizarUbicacion(jugadorActual);

                        enEspera = true;
                        // La única opción que se le da al jugador es de pasar al siguiente turno
                        guiFrame.enviarCambios("Siguiente turno");
                        // Espera a que el jugador presione botón.
                        while (enEspera) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {}
                        }
                    }
                }
                
                if (jugadorActual.dineroTotal == 0) {
                    turnoTerminado = true;
                }
                
                if ((jugadorActual.turnosEnCarcel==-1 || jugadorActual.turnosEnCarcel==-3) && !turnoTerminado) {

                    if (jugadorActual.turnosEnCarcel==-1) {
                        // El jugador debe moverse tantos espacios como indiquen los dados
                        jugadorActual.mover(dados.obtenerSuma(), guiFrame.panelJuego);
                        //guiFrame.panelJuego.actualizarVisionJugador();
                    } else {
                        jugadorActual.turnosEnCarcel = -1;
                    }

                    // Debe mostrarse la vista de la casilla a la que acaba de llegar,
                    // es decir, activar botones correspondientes y mostrar casilla actual
                    if (Arrays.asList("Salida", "A Cárcel", "Parking Gratuito").contains(jugadorActual.casillaActual.tipo)) {
                        
                        if (jugadorActual.casillaActual.tipo.equals("A Cárcel")) {
                            
                            jugadorActual.casillaActual = tablero.carcel;
                            jugadorActual.turnosEnCarcel = 0;
                            turnoSeProlonga = false;    // En caso de que obtuviera dobles previamente, se asegura que su turno termine inmediatamente
                            guiFrame.panelJuego.pTablero.actualizarUbicacion(jugadorActual);
                            
                        }

                        // La única opción que se le da al jugador es de pasar al siguiente turno
                        enEspera = true;
                        guiFrame.enviarCambios("Siguiente turno");
                        while (enEspera) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {}
                        }
                        
                    } else {
                        
                        // Se obtiene la acción disponible en la casilla actual
                        opc[0] = jugadorActual.casillaActual.obtenerOpciones(this);

                        if (opc[0].equals("Comprar")) {
                            // Si el jugador puede Comprar, también debe poder pasar al siguiente
                            // turno en caso de no querer Comprar
                            opc[1] = "Siguiente turno";
                            opc[2] = " ";
                            guiFrame.enviarCambios(opc);
                        } else {
                            guiFrame.enviarCambios(opc[0]);
                        }

                        enEspera = true;
                        // Espera a que el jugador presione botón.
                        while (enEspera) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {}
                        }

                        if (jugadorActual.dineroTotal==0) {
                            turnoTerminado = true;
                        }
                        
                        // Si la casilla es una carta, el botón que se habrá presionado
                        // habrá sido "Obtener carta", por lo que ahora debe activarse
                        // "Usar carta"
                        
                        if (!turnoTerminado) {
                            
                            if (jugadorActual.casillaActual.tipo.equals("Cofre Comunitario") || jugadorActual.casillaActual.tipo.equals("Suerte")) {

                                if ((jugadorActual.casillaActual.nombre.split(" ")[0].equals("Avanza") 
                                        && !jugadorActual.casillaActual.nombre.equals("Avanza a la Cárcel"))
                                    || (jugadorActual.casillaActual.nombre.split(" ")[0].equals("Regresa"))) {
                                     // Si se mueve de casilla falta evaluar casilla en la que queda, por lo 
                                     // que se "prolonga" el turno
                                     turnoSeProlonga = true;
                                     jugadorActual.turnosEnCarcel = -3;
                                }
                                
                                if (jugadorActual.casillaActual.nombre.equals("Avanza a la Cárcel")) {
                                    turnoSeProlonga = false;
                                }

                                enEspera = true;
                                guiFrame.enviarCambios("Usar carta");
                                // Espera a que el jugador presione botón.
                                while (enEspera) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {}
                                }
                                
                                if (jugadorActual.dineroTotal==0) {
                                    turnoTerminado = true;
                                }
                                
                                if (!turnoTerminado) {
                                    guiFrame.panelJuego.pTablero.actualizarUbicacion(jugadorActual);
                                }
                                
                                // Comprueba si el siguiente jugador en la lista que no está en 
                                // bancarrota no es jugadorActual
                                if (jugadorActual == siguienteTurno()) {
                                    juegoTermina = true;
                                }

                                if (!turnoSeProlonga && !juegoTermina) {
                                    enEspera = true;
                                    guiFrame.enviarCambios("Siguiente turno");
                                    // Espera a que el jugador presione botón.
                                    while (enEspera) {
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {}
                                    }
                                }
                                

                            } else {
                                // Si el botón no era de obtener carta, debe ahora activarse el botón
                                // de siguiente turno
                                
                                if (!turnoSeProlonga && !juegoTermina) {
                                    enEspera = true;
                                    guiFrame.enviarCambios("Siguiente turno");
                                    // Espera a que el jugador presione botón.
                                    while (enEspera) {
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {}
                                    }
                                }

                            }
                        }


                    }
                }
                
            } while(turnoSeProlonga);
            
            // Comprueba si el siguiente jugador en la lista que no está en 
            // bancarrota no es jugadorActual
            if (jugadorActual == siguienteTurno()) {
                juegoTermina = true;
            }

        } while(!juegoTermina);
        
        this.guiFrame.eliminarPanelJuego();
        
    }
    
    public void finJuego() {
        this.guiFrame.añadirPanelFinJuego(this);
    }

}
