package Model;
import java.util.HashMap;
import java.util.Map;

public class Casilla {
    private CasillaType tipo;

    public enum CasillaType {
        NORMAL, PUENTE, OCA, MUERTE, POZO, POSADA, PRISION, DADOS, LABERINTO
    }

    public Casilla(CasillaType tipo){
        this.tipo = tipo;
    }

    public CasillaType getCasillaType() {
        return this.tipo;
    }
}
