package logica;

public class ExplosionVisual {
    private double posicionX;
    private double altitud;
    private int tiempoRestante; // Medido en frames

    public ExplosionVisual(double posicionX, double altitud, int duracionFrames) {
        this.posicionX = posicionX;
        this.altitud = altitud;
        this.tiempoRestante = duracionFrames;
    }
    public void decrementarTiempo() { this.tiempoRestante--; }
    public double getPosicionX() { return posicionX; }
    public double getAltitud() { return altitud; }
    public int getTiempoRestante() { return tiempoRestante; }
}