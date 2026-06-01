public class Misil extends EntidadMovil {

    private double altitudDetonacion;
    private double velocidadCaida;

    public Misil(double posicionX, double altitud) {
        super(posicionX, altitud);

        // El TP pide que la altitud de detonación se defina en el momento del lanzamiento
        this.altitudDetonacion = generarAltitudDetonacion();

        // Le ponemos una velocidad base inicial provisoria
        this.velocidadCaida = 50.0;
    }

    // Cumplimos con el contrato de EntidadMovil
    @Override
    public void mover() {
        // Los misiles descienden en línea recta [cite: 10]
        // Por lo tanto, solo restamos a la altitud en el eje Y (no tocamos la posición X)
        this.altitud -= velocidadCaida;
    }

    // Método interno para calcular la explosión
    private double generarAltitudDetonacion() {
        // El TP pide un valor aleatorio comprendido entre 1200 y 4500 metros [cite: 12]
        double minimo = 1200.0;
        double maximo = 4500.0;
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