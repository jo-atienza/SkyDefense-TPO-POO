public abstract class EntidadMovil {
    protected double posicionX;
    protected double altitud;

    // Constructor: obliga a que cualquier entidad móvil nazca con una posición
    public EntidadMovil(double posicionX, double altitud) {
        this.posicionX = posicionX;
        this.altitud = altitud;
    }
    public abstract void mover();
    // Getters comunes que heredan todos los hijos sin tener que volver a escribirlos
    public double getPosicionX() {
        return posicionX;
    }
    public double getAltitud() {
        return altitud;
    }
    // Setters (opcionales, te sirven si el controlador necesita forzar un cambio de posición)
    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }
    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }
}