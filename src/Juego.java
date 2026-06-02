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
        // Generación de drones
        // Verifica los límites del TP: máx 10 por nivel y máx 4 simultáneos en pantalla
        if (!escuadron.estaCompleto() && escuadron.verificarActivos()) {
            // Probabilidad baja para escalonar la aparición de los enemigos
            if (Math.random() < 0.02) {
                crearDron();
            }
        }
        // Actualización de drones (movimiento y disparos)
        Iterator<Dron> iteradorDrones = escuadron.getDrones().iterator();
        while (iteradorDrones.hasNext()) {
            Dron dron = iteradorDrones.next();
            dron.mover();
            // Elimina los drones que salen de los límites de la pantalla
            if (dron.getPosicionX() < -50 || dron.getPosicionX() > 1050) {
                iteradorDrones.remove();
                escuadron.registrarDronDestruidoOCompletado();
                continue;
            }
            if (dron.verificarDisparo()) {
                Misil nuevoMisil = dron.disparar();
                // Aplica el factor de dificultad a la velocidad de caída del misil
                nuevoMisil.setVelocidadCaida(nuevoMisil.getVelocidadCaida() * nivelActual.calcularFactor());
                this.misilesEnElAire.add(nuevoMisil);
            }
        }
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
        verificarGameOver();
        verificarFinNivel();
    }
    // Instancia y configura un dron nuevo antes de agregarlo al escuadrón
    private void crearDron() {
        boolean ladoIzq = Math.random() < 0.5;
        double posX = ladoIzq ? -10.0 : 1010.0;
        double altitudDron = 4000.0 + (Math.random() * 1000);
        Dron nuevoDron = new Dron(posX, altitudDron, ladoIzq, 5.0, 0.01);
        // Ajuste de velocidades según el nivel actual
        nuevoDron.actualizarVelocidades(nivelActual.calcularFactor());
        escuadron.agregarDron(nuevoDron);
        escuadron.registrarDronEnPantalla();
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
        if (!juegoTerminado) this.avion.moverHorizontal(-20.0);
    }
    public void moverAvionDerecha() {
        if (!juegoTerminado) this.avion.moverHorizontal(20.0);
    }
    public void subirAvion() {
        if (!juegoTerminado) this.avion.cambiarAltitud(20.0);
    }
    public void bajarAvion() {
        if (!juegoTerminado) this.avion.cambiarAltitud(-20.0);
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