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
        this.avion = new Avion(500.0, 1000.0, 100.0); // Arranca en el medio (ej: X=500)
        this.misilesEnElAire = new ArrayList<>();
    }

    public void actualizarEstado() {
        if (juegoTerminado) {
            return;
        }

        // --- 1. SPWANING: Lógica para que aparezcan drones nuevos ---
        // Si el escuadrón no llegó a 10 y hay menos de 4 en pantalla, intentamos crear uno
        if (!escuadron.estaCompleto() && escuadron.verificarActivos()) {
            // Le damos una pequeña probabilidad para que no aparezcan los 4 de golpe
            if (Math.random() < 0.02) {
                crearDron();
            }
        }

        // --- 2. MOVER DRONES Y DISPARAR ---
        Iterator<Dron> iteradorDrones = escuadron.getDrones().iterator();
        while (iteradorDrones.hasNext()) {
            Dron dron = iteradorDrones.next();
            dron.mover();

            // Si el dron sale de los límites de la pantalla (ej: < 0 o > 1000), desaparece
            if (dron.getPosicionX() < -50 || dron.getPosicionX() > 1050) {
                iteradorDrones.remove();
                escuadron.registrarDronDestruidoOCompletado();
                continue; // Saltamos al siguiente dron
            }

            if (dron.verificarDisparo()) {
                Misil nuevoMisil = dron.disparar();
                // A los misiles también les aplicamos el aumento de velocidad del nivel
                nuevoMisil.setVelocidadCaida(nuevoMisil.getVelocidadCaida() * nivelActual.calcularFactor());
                this.misilesEnElAire.add(nuevoMisil);
            }
        }

        // --- 3. MOVER MISILES Y DETONAR ---
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

    // Método de apoyo para crear y configurar el dron antes de mandarlo a volar
    private void crearDron() {
        boolean ladoIzq = Math.random() < 0.5;
        double posX = ladoIzq ? -10.0 : 1010.0;
        double altitudDron = 4000.0 + (Math.random() * 1000); // Vuelan alto

        // Creamos el dron con valores base
        Dron nuevoDron = new Dron(posX, altitudDron, ladoIzq, 5.0, 0.01);

        // APLICAMOS LA DIFICULTAD DEL NIVEL (El factor multiplicador)
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
}