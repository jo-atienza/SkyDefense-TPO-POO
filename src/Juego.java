public class Juego {

    private Nivel nivelActual;
    private Jugador jugador;
    private Escuadron escuadron;
    private Avion avion;
    private SistemaDeDetonacion sistemaDetonacion;
    private boolean juegoTerminado;

    public Juego() {
        // Inicializamos las piezas principales al abrir el juego
        this.jugador = new Jugador(3); // Arranca con 3 vidas, por ejemplo
        this.sistemaDetonacion = new SistemaDeDetonacion();
        this.juegoTerminado = false;
        this.nivelActual = new Nivel(1);

        iniciarNivel();
    }

    public void iniciarNivel() {
        // Al arrancar un nivel, se resetea el escuadrón y la posición del avión
        this.escuadron = new Escuadron();

        // El avión arranca en la posición X=0, Altitud=1000, con 100 de energía
        this.avion = new Avion(0.0, 1000.0, 100.0);
    }

    public void avanzarNivel() {
        // Se suma el puntaje de victoria del nivel
        this.jugador.sumarPuntos(300);

        // Avanzamos el número de nivel
        this.nivelActual.setNumeroNivel(this.nivelActual.getNumeroNivel() + 1);

        // Preparamos el escenario para el nuevo nivel
        iniciarNivel();
    }

    public void verificarGameOver() {
        if (this.jugador.getVidas() <= 0 || this.avion.getEnergia() <= 0) {
            this.juegoTerminado = true;
            System.out.println("¡Game Over!");
        }
    }

    public void verificarFinNivel() {
        // Si ya pasaron los 10 drones y no hay más activos, ganaste el nivel
        if (this.escuadron.estaCompleto() && this.escuadron.getDronesActivos() == 0) {
            avanzarNivel();
        }
    }

    // Getters necesarios para la interfaz gráfica
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}