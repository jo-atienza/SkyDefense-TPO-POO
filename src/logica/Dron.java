package logica;

public class Dron extends EntidadMovil {

    private boolean moviendoDerecha;
    private double velocidadHorizontal;
    private double probabilidadDisparo;

    // NUEVAS REGLAS DE PATRULLAJE
    private int municion;
    private boolean retirandose;

    public Dron(double posicionX, double altitud, boolean moviendoDerecha, double velocidadHorizontal, double probabilidadDisparo) {
        super(posicionX, altitud);
        this.moviendoDerecha = moviendoDerecha;
        this.velocidadHorizontal = velocidadHorizontal;
        this.probabilidadDisparo = probabilidadDisparo;

        // Cada dron va a disparar 5 misiles antes de quedarse sin balas y huir
        this.municion = 5;
        this.retirandose = false;
    }

    @Override
    public void mover() {
        if (moviendoDerecha) {
            this.posicionX += velocidadHorizontal;
        } else {
            this.posicionX -= velocidadHorizontal;
        }
    }

    // Método para que rebote contra los bordes
    public void revertirDireccion() {
        this.moviendoDerecha = !this.moviendoDerecha;
    }

    public void actualizarVelocidades(double factorDificultad) {
        this.velocidadHorizontal *= factorDificultad;
        this.probabilidadDisparo *= factorDificultad;
    }

    public boolean verificarDisparo() {
        // Solo dispara si no está en retirada y si la suerte lo dicta
        return !retirandose && (Math.random() < probabilidadDisparo);
    }

    public Misil disparar() {
        this.municion--;
        // Si disparó su última bala, se marca en retirada
        if (this.municion <= 0) {
            this.retirandose = true;
        }
        return new Misil(this.posicionX, this.altitud);
    }

    public boolean isRetirandose() {
        return retirandose;
    }
}