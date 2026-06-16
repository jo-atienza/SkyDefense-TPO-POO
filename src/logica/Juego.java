package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import excepciones.*;

public class Juego {
    private Nivel nivelActual;
    private Jugador jugador;
    private Escuadron escuadron;
    private Avion avion;
    private SistemaDeDetonacion sistemaDetonacion;
    private boolean juegoTerminado;
    private List<Misil> misilesEnElAire;

    public Juego() {
        this.jugador = new Jugador(3);
        this.sistemaDetonacion = new SistemaDeDetonacion();
        this.juegoTerminado = false;
        this.nivelActual = new Nivel(1);
        iniciarNivel();
    }
    public void iniciarNivel() {
        this.escuadron = new Escuadron();
        this.avion = new Avion(500.0, 1000.0, 100.0); // Posición inicial centrada
        this.misilesEnElAire = new ArrayList<>();
    }
    public void actualizarEstado() {
        if (juegoTerminado) {
            return;
        }
        // 1. DELEGACIÓN (GRASP): El Escuadrón es el experto, él gestiona sus drones
        escuadron.actualizar(nivelActual.calcularFactor(), misilesEnElAire);
        // 2. El Juego solo gestiona los elementos globales (misiles cayendo)
        actualizarMisiles();
        // 3. Verificaciones de reglas de negocio globales
        verificarGameOver();
        verificarFinNivel();
    }
    private void actualizarMisiles() {
        // Actualización de misiles y validación de detonaciones
        Iterator<Misil> iteradorMisiles = misilesEnElAire.iterator();
        while (iteradorMisiles.hasNext()) {
            Misil misil = iteradorMisiles.next();
            misil.mover();
            if (misil.getAltitud() <= misil.getAltitudDetonacion()) {
                sistemaDetonacion.aplicarEfecto(misil, avion, jugador);
                iteradorMisiles.remove();
            }
        }
    }
    public void avanzarNivel() {
        this.jugador.sumarPuntos(300);
        this.nivelActual.setNumeroNivel(this.nivelActual.getNumeroNivel() + 1);
        iniciarNivel();
    }
    public void verificarGameOver() {
        // 1. Si nos quedamos sin energía, se cobra una vida
        if (this.avion.getEnergia() <= 0) {
            this.jugador.perderVida();

            // Si aún nos quedan vidas, restauramos el motor al 100% para seguir
            if (this.jugador.getVidas() > 0) {
                this.avion.setEnergia(100.0);
            }
        }

        // 2. El Game Over definitivo solo ocurre si las vidas se agotan
        if (this.jugador.getVidas() <= 0) {
            this.juegoTerminado = true;
        }
    }
    public void verificarFinNivel() {
        if (this.escuadron.estaCompleto() && this.escuadron.getDronesActivos() == 0) {
            avanzarNivel();
        }
    }
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
    //UNIFICAMOS LOS MOVIMIENTOS EN UNA SOLA FUNCIÓN
    public void moverAvion(double deltaX, double deltaY) {
        if (juegoTerminado) {
            throw new JuegoYaFinalizadoException("Mover Avión (" + deltaX + ", " + deltaY + ")");
        }
        this.avion.mover(deltaX, deltaY);
    }
    // Getters para el renderizado de la interfaz
    public Avion getAvion() { return avion; }
    public Escuadron getEscuadron() { return escuadron; }
    public List<Misil> getMisilesEnElAire() { return misilesEnElAire; }
    public Jugador getJugador() { return jugador; }
    public Nivel getNivelActual() { return nivelActual; }
}