package Model;

import java.util.concurrent.ThreadLocalRandom;

public class Tablero {
    private Casilla[] arrCasillas = new Casilla[64];

    public Tablero(){
        initCasillas();
    }

    private void initCasillas(){
        int[] casillasOca = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59};
        int[] casillasPuente = {6, 12};
        int[] casillasPosada = {19};
        int[] casillasPozo = {31};
        int[] casillasDado = {26, 53};
        int[] casillasLaberinto = {42};
        int[] casillasCarcel = {52};
        int[] casillasMuerte = {52};
        int[] casillasMeta = {63};

        initCasilla(casillasOca, Casilla.CasillaType.OCA);
        initCasilla(casillasPuente, Casilla.CasillaType.PUENTE);
        initCasilla(casillasPosada, Casilla.CasillaType.POSADA);
        initCasilla(casillasPozo, Casilla.CasillaType.POZO);
        initCasilla(casillasDado, Casilla.CasillaType.DADOS);
        initCasilla(casillasLaberinto, Casilla.CasillaType.LABERINTO);
        initCasilla(casillasCarcel, Casilla.CasillaType.PRISION);
        initCasilla(casillasMuerte, Casilla.CasillaType.MUERTE);
        initCasilla(casillasMeta, Casilla.CasillaType.META);
        llenarCasillasNormales();
    }

    private void initCasilla(int[] tipoCasilla, Casilla.CasillaType tipo){
        Casilla casilla = new Casilla(tipo);
        for (int i = 0; i < tipoCasilla.length; i++){
            arrCasillas[tipoCasilla[i]] = casilla;
        }
    }

    private void llenarCasillasNormales(){
        for (int i = 0; i < arrCasillas.length - 1; i++){
            Casilla casilla = new Casilla(Casilla.CasillaType.NORMAL);
            if (arrCasillas[i] == null) {
                arrCasillas[i] = casilla;
            }
        }
    }
    public int tirarDado(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return randomNum;
    }

    public void checkCasilla(int num){
        System.out.println(arrCasillas[num].getCasillaType().getMensaje());
    }
}
