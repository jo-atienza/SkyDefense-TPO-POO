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
    private List<Misil> misilesEnElAire;
    private List<ExplosionVisual> poolExplosiones;
    private boolean juegoTerminado;
    private boolean juegoGanado;
    private boolean pausado;
    private boolean enTransicionNivel;

    public Juego() {
        this.jugador = new Jugador(3);
        this.sistemaDetonacion = new SistemaDeDetonacion();
        this.juegoTerminado = false;
        this.juegoGanado = false;
        this.nivelActual = new Nivel(1);
        this.poolExplosiones = new ArrayList<>();
        this.pausado = false;
        this.enTransicionNivel = false;
        iniciarNivel();
    }
    public void iniciarNivel() {
        this.escuadron = new Escuadron();
        this.avion = new Avion(500.0, 3000.0, 100.0);
        this.misilesEnElAire = new ArrayList<>();
    }
    // Métodos de actualización individuales independientes
    public void actualizarEstado() {
        if (juegoTerminado || juegoGanado || pausado || enTransicionNivel) {
            return;
        }
        actualizarEscuadron();
        actualizarMisiles();
        actualizarExplosionesVisuales();
        verificarGameOver();
        verificarFinNivel();
    }
    private void actualizarEscuadron() {
        escuadron.actualizar(nivelActual.calcularFactor(), misilesEnElAire);
    }
    private void actualizarMisiles() {
        Iterator<Misil> iteradorMisiles = misilesEnElAire.iterator();
        while (iteradorMisiles.hasNext()) {
            Misil misil = iteradorMisiles.next();
            misil.mover();
            if (misil.getAltitud() <= misil.getAltitudDetonacion()) {
                sistemaDetonacion.aplicarEfecto(misil, avion, jugador);
                this.poolExplosiones.add(new ExplosionVisual(misil.getPosicionX(), misil.getAltitud(), 40));
                iteradorMisiles.remove();
            }
        }
    }
    private void actualizarExplosionesVisuales() {
        Iterator<ExplosionVisual> itEx = poolExplosiones.iterator();
        while (itEx.hasNext()) {
            ExplosionVisual ev = itEx.next();
            ev.decrementarTiempo();
            if (ev.getTiempoRestante() <= 0) {
                itEx.remove();
            }
        }
    }
    public void avanzarNivel() {
        if (this.nivelActual.getNumeroNivel() < 5) {
            this.nivelActual.setNumeroNivel(this.nivelActual.getNumeroNivel() + 1);
            this.enTransicionNivel = false;
            iniciarNivel();
        }
    }
    public void conmutarPausa() {
        if (!juegoTerminado && !juegoGanado && !enTransicionNivel) {
            this.pausado = !this.pausado;
        }
    }
    public void confirmarDespegue() {
        if (enTransicionNivel) {
            avanzarNivel();
        }
    }
    public void verificarGameOver() {
        if (this.avion.getEnergia() <= 0) {
            this.jugador.perderVida();
            if (this.jugador.getVidas() > 0) {
                this.avion.setEnergia(100.0);
            }
        }
        if (this.jugador.getVidas() <= 0) {
            this.juegoTerminado = true;
        }
    }
    public void verificarFinNivel() {
        // Al eliminarse los contadores obsoletos, la verificación queda limpia y cohesiva
        if (this.escuadron.estaCompleto() && this.escuadron.getDronesActivos() == 0) {
            this.jugador.sumarPuntos(300);

            if (this.nivelActual.getNumeroNivel() >= 5) {
                this.juegoGanado = true;
                this.enTransicionNivel = false;
            } else {
                this.enTransicionNivel = true;
            }
        }
    }
    public void moverAvion(double deltaX, double deltaY) {
        if (juegoTerminado || juegoGanado){
            throw new JuegoYaFinalizadoException("Intento mover el avion con la partida finalizada.");
        }
        if (pausado || enTransicionNivel) {
            return;
        }
        this.avion.mover(deltaX, deltaY);
    }
    // --- GETTERS ---
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isJuegoGanado() { return juegoGanado; }
    public boolean isPausado() { return pausado; }
    public boolean isEnTransicionNivel() { return enTransicionNivel; }
    public List<ExplosionVisual> getPoolExplosiones() { return poolExplosiones; }
    public Avion getAvion() { return avion; }
    public Escuadron getEscuadron() { return escuadron; }
    public List<Misil> getMisilesEnElAire() { return misilesEnElAire; }
    public Jugador getJugador() { return jugador; }
    public Nivel getNivelActual() { return nivelActual; }
}