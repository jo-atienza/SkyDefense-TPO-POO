public class Dron extends EntidadMovil {

    private boolean ladoInicio; // true = izquierda, false = derecha (por ejemplo)
    private double velocidadDesplazamiento;
    private double frecuenciaDisparo;

    // Constructor: Usamos super() para las variables heredadas
    public Dron(double posicionX, double altitud, boolean ladoInicio, double velocidadDesplazamiento, double frecuenciaDisparo) {
        super(posicionX, altitud);
        this.ladoInicio = ladoInicio;
        this.velocidadDesplazamiento = velocidadDesplazamiento;
        this.frecuenciaDisparo = frecuenciaDisparo;
    }

    // Cumplimos con el contrato de EntidadMovil
    @Override
    public void mover() {
        // Los drones enemigos atraviesan la pantalla de izquierda a derecha o viceversa
        if (ladoInicio) {
            // Si arranca en la izquierda, se mueve hacia la derecha (suma X)
            this.posicionX += velocidadDesplazamiento;
        } else {
            // Si arranca en la derecha, se mueve hacia la izquierda (resta X)
            this.posicionX -= velocidadDesplazamiento;
        }
    }

    // El dron es el "Creador" del misil según tu diagrama
    public Misil disparar() {
        // Los misiles descienden en línea recta desde la posición del dron que los lanzó
        // Retornamos un nuevo misil pasándole la X y la Y (altitud) actuales del dron
        return new Misil(this.posicionX, this.altitud);
    }

    // Método para que el controlador o el escuadrón sepa cuándo crear un misil
    public boolean verificarDisparo() {
        // La frecuencia de disparo determinará la probabilidad de disparar en este "frame" o turno.
        // Usamos Math.random() como una forma sencilla de evaluarlo.
        return Math.random() < frecuenciaDisparo;
    }

    // Método para aplicar el incremento de dificultad
    public void actualizarVelocidades(double factorIncremento) {
        // Cada nuevo nivel incrementa dichas velocidades en un 15%
        // factorIncremento vendrá de la clase Nivel (ej: 1.15)
        this.velocidadDesplazamiento *= factorIncremento;
        this.frecuenciaDisparo *= factorIncremento;
    }

    // Getters y Setters
    public boolean isLadoInicio() {
        return ladoInicio;
    }

    public void setLadoInicio(boolean ladoInicio) {
        this.ladoInicio = ladoInicio;
    }

    public double getVelocidadDesplazamiento() {
        return velocidadDesplazamiento;
    }

    public void setVelocidadDesplazamiento(double velocidadDesplazamiento) {
        this.velocidadDesplazamiento = velocidadDesplazamiento;
    }

    public double getFrecuenciaDisparo() {
        return frecuenciaDisparo;
    }

    public void setFrecuenciaDisparo(double frecuenciaDisparo) {
        this.frecuenciaDisparo = frecuenciaDisparo;
    }
}