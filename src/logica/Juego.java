package logica;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import excepciones.*;

public class Juego implements Renderizable {
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
        this.sistemaDetonacion = SistemaDeDetonacion.getInstance();
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
        if (juegoTerminado || juegoGanado) {
            throw new JuegoYaFinalizadoException("Intento mover el avion con la partida finalizada.");
        }
        if (pausado || enTransicionNivel) {
            return;
        }
        this.avion.mover(deltaX, deltaY);
    }
    // IMPLEMENTACIÓN DE Renderizable
    // La vista usa estos métodos en lugar de acceder a Juego directamente.
    @Override
    public List<Dron> getDrones() {
        return escuadron.getDrones();
    }
    @Override
    public List<Misil> getMisilesEnElAire() {
        return misilesEnElAire;
    }
    @Override
    public List<ExplosionVisual> getExplosiones() {
        return poolExplosiones;
    }
    @Override
    public double getAvionX() {
        return avion.getPosicionX();
    }
    @Override
    public double getAvionAltitud() {
        return avion.getAltitud();
    }
    @Override
    public double getAvionEnergia() {
        return avion.getEnergia();
    }
    @Override
    public int getVidas() {
        return jugador.getVidas();
    }
    @Override
    public int getPuntaje() {
        return jugador.getPuntaje();
    }
    @Override
    public int getNumeroNivel() {
        return nivelActual.getNumeroNivel();
    }
    @Override
    public boolean isJuegoGanado() { return juegoGanado; }
    @Override
    public boolean isJuegoTerminado() { return juegoTerminado; }
    @Override
    public boolean isPausado() { return pausado; }
    @Override
    public boolean isEnTransicionNivel() { return enTransicionNivel; }
    // GETTERS INTERNOS (solo para clases del paquete logica), PanelJuego las necesita  para la lógica de control
    public Avion getAvion() { return avion; }
    public Escuadron getEscuadron() { return escuadron; }
    public Jugador getJugador() { return jugador; }
    public Nivel getNivelActual() { return nivelActual; }
    public List<ExplosionVisual> getPoolExplosiones() { return poolExplosiones; }
}