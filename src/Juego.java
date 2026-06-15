import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        if (this.jugador.getVidas() <= 0 || this.avion.getEnergia() <= 0) {
            this.juegoTerminado = true;
            System.out.println("¡Game Over!");
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
    // Controles de movimiento para la interfaz gráfica
    public void moverAvionIzquierda() {
        if (!juegoTerminado) this.avion.mover(-20.0, 0.0);
    }
    public void moverAvionDerecha() {
        if (!juegoTerminado) this.avion.mover(20.0, 0.0);
    }
    public void subirAvion() {
        if (!juegoTerminado) this.avion.mover(0.0, 20.0);
    }
    public void bajarAvion() {
        if (!juegoTerminado) this.avion.mover(0.0, -20.0);
    }
    // Getters para el renderizado de la interfaz
    public Avion getAvion() {
        return avion;
    }
    public Escuadron getEscuadron() {
        return escuadron;
    }
    public List<Misil> getMisilesEnElAire() {
        return misilesEnElAire;
    }
    public Jugador getJugador() {
        return jugador;
    }
    public Nivel getNivelActual() {
        return nivelActual;
    }
}