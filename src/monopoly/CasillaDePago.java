package monopoly;

public class CasillaDePago extends Casilla {
    public int pago;

    public CasillaDePago(String[] reg) {
        super(reg[0], reg[1], reg[2]);
        this.pago = Integer.parseInt(reg[3]);
    }

    public void cobrarPago(Jugador jugador) {
        if (jugador.dineroTotal > this.pago) {
            jugador.sumarDinero(-this.pago);
        } else {
            jugador.establecerEnBancarrota();
        }
    }
}
