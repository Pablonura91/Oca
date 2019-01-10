package Model;

public class Casilla {
    private CasillaType tipo;

    public enum CasillaType {
        NORMAL("Normal"),
        PUENTE("Puente"),
        OCA("Oca"),
        MUERTE("Muerte"),
        POZO("Pozo"),
        POSADA("Posada"),
        PRISION("Prision"),
        DADOS("Dados"),
        META("Meta"),
        LABERINTO("Laberinto");

        private final String mensaje;

        CasillaType (String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return this.mensaje;
        }
    }

    public Casilla(CasillaType tipo){
        this.tipo = tipo;
    }

    public CasillaType getCasillaType() {
        return this.tipo;
    }
}
