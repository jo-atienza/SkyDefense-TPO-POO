package logica;

public class Misil extends EntidadMovil {

    private double altitudDetonacion;
    private double velocidadCaida;

    public Misil(double posicionX, double altitud) {
        super(posicionX, altitud);

        // Le pasamos la altitud inicial para que el techo de explosión sea lógico
        this.altitudDetonacion = generarAltitudDetonacion(altitud);

        // Velocidad base inicial
        this.velocidadCaida = 50.0;
    }

    // Cumplimos con el contrato de EntidadMovil
    @Override
    public void mover() {
        // Los misiles descienden en línea recta
        this.altitud -= velocidadCaida;
    }

    // Método interno para calcular la explosión (AHORA CON LÓGICA CORREGIDA)
    private double generarAltitudDetonacion(double altitudLanzamiento) {
        double minimo = 1200.0;

        // El punto máximo de detonación será 200 metros por debajo del dron
        double maximo = altitudLanzamiento - 200.0;

        // Por las dudas, si el dron volaba muy bajo, evitamos que el máximo rompa la matemática
        if (maximo <= minimo) {
            maximo = minimo + 100.0;
        }

        return minimo + (Math.random() * (maximo - minimo));
    }

    // Getters y Setters
    public double getAltitudDetonacion() {
        return altitudDetonacion;
    }

    public double getVelocidadCaida() {
        return velocidadCaida;
    }

    public void setVelocidadCaida(double velocidadCaida) {
        this.velocidadCaida = velocidadCaida;
    }
}