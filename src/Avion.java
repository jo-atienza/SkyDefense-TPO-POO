public class Avion extends EntidadMovil {

    private double energia;

    // Constantes para respetar las reglas del negocio del TP
    private static final double ALTITUD_MINIMA = 1000.0;
    private static final double ALTITUD_MAXIMA = 5000.0;

    // Constructor: Usamos 'super' para enviarle la X y la altitud a EntidadMovil
    public Avion(double posicionX, double altitud, double energiaInicial) {
        super(posicionX, altitud);
        this.energia = energiaInicial;

        // Validación inicial por si se crea fuera de los límites
        limitarAltitud();
    }

    // Sobreescribimos el método abstracto obligatorio de la clase padre
    @Override
    public void mover() {
        // En un juego, el movimiento del avión suele estar dictado por los controles
        // del teclado (que maneja el controlador). Podés dejar este método genérico
        // vacío o usarlo para actualizar su estado general en cada "frame".
    }

    // Método específico del avión para desplazarse de izquierda a derecha
    public void moverHorizontal(double cantidad) {
        this.posicionX += cantidad;
    }

    // Método para subir o bajar, validando que no rompa las reglas del TP
    public void cambiarAltitud(double cantidad) {
        this.altitud += cantidad;
        limitarAltitud(); // Llamamos al método privado para que corrija si se pasó
    }

    // Método de apoyo interno (private) para mantener la cohesión
    private void limitarAltitud() {
        if (this.altitud < ALTITUD_MINIMA) {
            this.altitud = ALTITUD_MINIMA;
        } else if (this.altitud > ALTITUD_MAXIMA) {
            this.altitud = ALTITUD_MAXIMA;
        }
    }

    // Método para cuando el SistemaDeDetonacion le reste porcentaje de energía
    public void recibirDaño(double porcentajeDanio) {
        // Asumiendo que el daño viene en porcentaje (ej: 0.20 para 20%)
        double energiaPerdida = this.energia * porcentajeDanio;
        this.energia -= energiaPerdida;

        if (this.energia < 0) {
            this.energia = 0;
        }
    }

    // Getters y Setters
    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }
}