public class Nivel {

    private int numeroNivel;

    // Constante de incremento de dificultad que pide el TP
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
        // Nivel 1: 1.0 + (0.15 * 0) = 1.0 (Sin incremento, velocidad normal)
        // Nivel 2: 1.0 + (0.15 * 1) = 1.15 (15% más rápido)
        // Nivel 3: 1.0 + (0.15 * 2) = 1.30 (30% más rápido)
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