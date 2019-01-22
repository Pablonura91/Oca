package Model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Tablero {

    private final int numJugadores;
    private ArrayList<Jugador> jugadorArrayList = new ArrayList<>();

    private Casilla[] arrCasillas = new Casilla[64];
    Scanner sc = new Scanner(System.in);
    private int randomNum;
    private String ganador;

    public Tablero(){
        initCasillas();

        System.out.println("Cuantos jugadores habrá: ");
        numJugadores = Integer.parseInt(sc.nextLine());

        for(int i=0; i < numJugadores; i++){
            Jugador jugador = new Jugador();

            System.out.println("Escribe el nombre del jugador " + i + " :");
            String nombreJugador = sc.nextLine();
            jugador.setName(nombreJugador);

            jugadorArrayList.add(jugador);
        }

        jugarPartida();
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

    private void jugarPartida() {

        int turno = 1;


        do{
            //Sumamos turno
            turno++;

            //Inicializamos el jugador
            Jugador jugador = jugadorArrayList.get(turno);

            //Tiramos dado
            tirarDado();

            //checkeamos casilla
            checkCasilla();

            //Adquirimos consequencias
            int consequencia = jugador.getConsequencias();

            //Obtenemos nueva casilla
            nuevaCasilla(consequencia);

            //Seteamos la nueva casilla
            jugador.setCasilla(randomNum);

            //Miramos si el turno a dado la vuelta
            if(turno == numJugadores){
                turno = 1;
            }

            //Guardamos el nombre del ultimo jugador
            ganador = jugador.getName();

            //Miramos si el ultimo jugador ha llegado al final
        }while(randomNum != 63);

        //Escribimos el numero del ultimo jugador
        System.out.println("El ganador de la oca es: " + ganador);
    }

    private void nuevaCasilla(int consequencia) {
        randomNum += consequencia;
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
    public void tirarDado(){
        randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }

    public void checkCasilla(){
        System.out.println(arrCasillas[randomNum].getCasillaType().getMensaje());
    }
}
