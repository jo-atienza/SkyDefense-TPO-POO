package logica;

public class Nivel {
    private int numeroNivel;
    // Constante de incremento de dificultad
    private static final double INCREMENTO_DIFICULTAD = 0.15;
    // Constructor
    public Nivel(int numeroNivel) {
        // Validamos que por error no se cree un nivel 0 o negativo
        if (numeroNivel < 1) {
            this.numeroNivel = 1;
        } else {
            this.numeroNivel = numeroNivel;
        }
    }
    // Método principal que calcula el multiplicador
    public double calcularFactor() {
        return 1.0 + (INCREMENTO_DIFICULTAD * (numeroNivel - 1));
    }
    // Getter
    public int getNumeroNivel() {
        return numeroNivel;
    }
    // Setter por si el juego necesita reiniciar el nivel actual o forzar un salto
    public void setNumeroNivel(int numeroNivel) {
        if (numeroNivel >= 1) {
            this.numeroNivel = numeroNivel;
        }
    }
}