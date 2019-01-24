package Model;

import Model.Casilla.CasillaType;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static Model.Casilla.CasillaType.*;

public class Tablero {

    private int numJugadores;
    private ArrayList<Jugador> jugadorArrayList = new ArrayList<>();
    private Casilla[] arrCasillas = new Casilla[64];
    private Scanner sc = new Scanner(System.in);
    private int randomNumTirada;
    private String ganador;
    private int turno = 1;


    public Tablero(){
        initCasillas();
        setPlayersNumber();
        setPlayersName();

        jugarPartida();
    }
    private void setPlayersNumber() {
        System.out.println("¿Cuantos jugadores habrá? (de 2 a 4 jugadores): ");
        do {
            numJugadores = Integer.parseInt(sc.nextLine());
            if (numJugadores > 4 || numJugadores < 0){
                System.out.println("Recuerda que és de 2 a 4 jugadores!");
            }
        }while(numJugadores > 4 || numJugadores <=1);
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

        initCasilla(casillasOca, OCA);
        initCasilla(casillasPuente, PUENTE);
        initCasilla(casillasPosada, POSADA);
        initCasilla(casillasPozo, POZO);
        initCasilla(casillasDado, DADOS);
        initCasilla(casillasLaberinto, LABERINTO);
        initCasilla(casillasCarcel, PRISION);
        initCasilla(casillasMuerte, MUERTE);
        initCasilla(casillasMeta, META);
        llenarCasillasNormales();
    }

    private void initCasilla(int[] tipoCasilla, CasillaType tipo){
        Casilla casilla = new Casilla(tipo);
        for (int iterator : tipoCasilla) {
            arrCasillas[iterator] = casilla;
        }
    }

    private void setPlayersName() {
        for(int i=1; i <= numJugadores; i++){
            System.out.println("Escribe el nombre del jugador " + i + " :");
            String nombreJugador = sc.nextLine();
            if (nombreJugador.length() >= 3) {
                Jugador jugador = new Jugador(nombreJugador);

                jugadorArrayList.add(jugador);
            } else {
                i--;
                System.out.println("El nombre tiene que contener un minimo de 3 letras!");
            }
        }
    }

    private void jugarPartida() {
        boolean endGame = false;
        do{
            Jugador jugadorActual = jugadorArrayList.get(turno - 1);
            System.out.println("\n\n"+jugadorActual.getName());
            System.out.println("Estas en la casilla numero "+ jugadorActual.getCasilla()+  " te toca tirar");

            //Comprueba que el jugador no tenga una espera para poder actuar
            if(!isEspera(jugadorActual)){
                //System.out.println("Escribe t para tirar dados");
                //String tirar = sc.nextLine();
                tirarDado();
                checkCasilla(jugadorActual);
            } else {
                System.out.println("Tienes que esperar: "+jugadorActual.getEspera()+" turno/s");
                jugadorActual.setEspera(jugadorActual.getEspera()-1);
            }

            if(arrCasillas[jugadorActual.getCasilla()].getCasillaType().equals(META)){
                ganador = jugadorActual.getName();
                endGame = true;
            }

            if(!arrCasillas[jugadorActual.getCasilla()].getCasillaType().equals(OCA)){
                if (turno == numJugadores){
                    turno = 1;
                } else {
                    turno++;
                }
            }

        }while(!endGame);

        //Escribimos el numero del ultimo jugador
        System.out.println("El ganador de la oca es: " + ganador);
    }

    private Boolean isEspera(Jugador jugadorActual) {
        return jugadorActual.getEspera() > 0;
    }

    private void llenarCasillasNormales(){
        for (int i = 0; i < arrCasillas.length - 1; i++){
            Casilla casilla = new Casilla(NORMAL);
            if (arrCasillas[i] == null) {
                arrCasillas[i] = casilla;
            }
        }
    }

    private void tirarDado(){
        randomNumTirada = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        System.out.println("Numero de la tirada "+ randomNumTirada);
    }

    private void checkCasilla(Jugador jugadorActual){
        int tirada = jugadorActual.getCasilla() + randomNumTirada;
        if (arrCasillas[tirada].getCasillaType().equals(META)){
            tirada = 63 - (/*randomNumTirada - */(tirada - 63));
        }

        CasillaType consequencia = arrCasillas[tirada].getCasillaType();
        switch (consequencia){
            case POZO:
                System.out.println("Has Caido en el Pozo!");
                jugadorActual.setEspera(2);
                System.out.println("Te toca esperar 2 turnos");
                System.out.println("Avanzas hasta la casilla " + tirada);
                jugadorActual.setCasilla(tirada);
                break;
            case PUENTE:
                System.out.println("Has Caido en el Puente!");
                if(jugadorActual.getCasilla() == 6){
                    System.out.println("Retrocedes hasta la casilla 6");
                    jugadorActual.setCasilla(12);
                } else {
                    System.out.println("Avanzas hasta la casilla 12");
                    jugadorActual.setCasilla(6);
                }
                break;
            case NORMAL:
                System.out.println("No te va a pasar nada, que suerte!");
                System.out.println("Avanzas hasta la casilla " + tirada);
                jugadorActual.setCasilla(tirada);
                break;
            case OCA:
                System.out.println("Has Caido en la Oca!");
                if (tirada == 59){
                    System.out.println("De Oca final has Ganado!");
                    tirada = 63;
                } else {
                    //Comprueba la siguiente OCA
                    if (arrCasillas[tirada + 4].getCasillaType().equals(OCA)) {
                        System.out.println("Avanzas hasta la casilla " + (tirada + 4));
                        jugadorActual.setCasilla(tirada + 4);
                    } else {
                        System.out.println("Avanzas hasta la casilla " + (tirada + 5));
                        jugadorActual.setCasilla(tirada + 5);
                    }
                    System.out.println("De oca a oca, TIRO PORQUE ME TOCA\n");
                }
                break;
            case MUERTE:
                System.out.println("Has Caido en la Muerte!");
                System.out.println("Vuelves a empezar");
                jugadorActual.setCasilla(1);
                break;
            case POSADA:
                System.out.println("Has Caido en la Posada!");
                System.out.println("Te toca esperar 2 turnos");
                System.out.println("Avanzas hasta la casilla " + tirada);
                jugadorActual.setEspera(2);
                jugadorActual.setCasilla(tirada);
                break;
            case PRISION:
                System.out.println("Has Caido en el pozo!");
                System.out.println("Te toca esperar 3 turnos");
                System.out.println("Avanzas hasta la casilla " + tirada);
                jugadorActual.setEspera(3);
                jugadorActual.setCasilla(tirada);
                break;
            case DADOS:
                System.out.println("Has Caido en los Dados!");
                System.out.println("Avanzas hasta la casilla " + (tirada + 8));
                jugadorActual.setCasilla(tirada + 8);
                break;
            case META:
                System.out.println("Enhorabuena! Has Ganado");
                jugadorActual.setCasilla(tirada);
                break;
            case LABERINTO:
                System.out.println("Has Caido en el pozo!");
                System.out.println("Retrocedes 30 casillas JAJAJAJAJA");
                jugadorActual.setCasilla(tirada - 30);
                break;
        }
    }
}
