package Model;

public class Jugador {

    private String name;
    private int numJugador;
    private int casilla;
    private int espera;
    private Casilla.CasillaType consequencias;

    public Jugador() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    public int getCasilla() {
        return casilla;
    }

    public void setCasilla(int casilla) {
        this.casilla = casilla;
    }

    public int getEspera() {
        return espera;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }

    public int getConsequencias() {
        return consequencias;
    }

    public void setConsequencias(Casilla.CasillaType consequencias) {
        this.consequencias = consequencias;
    }
}
