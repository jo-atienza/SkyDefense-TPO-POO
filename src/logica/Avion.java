package logica;

import excepciones.AltitudFueraDeRangoException;

public class Avion extends EntidadMovil {

    private double energia;

    private static final double ALTITUD_MINIMA = 1000.0;
    private static final double ALTITUD_MAXIMA = 5000.0;

    public Avion(double posicionX, double altitud, double energiaInicial) {
        super(posicionX, altitud);
        if (altitud < 1000.0 || altitud > 5000.0) {
            throw new AltitudFueraDeRangoException(altitud);
        }
        this.energia = energiaInicial;
        limitarPosicion();
    }

    @Override
    public void mover() {
    }

    public void mover(double deltaX, double deltaY) {
        this.posicionX += deltaX;
        this.altitud += deltaY;
        limitarPosicion();
    }

    private void limitarPosicion() {
        // Límites horizontales adaptados a 1200 de ancho (1200 - 70 tamaño avión)
        if (this.posicionX < 0) {
            this.posicionX = 0;
        } else if (this.posicionX > 1130) {
            this.posicionX = 1130;
        }

        // Límites verticales
        if (this.altitud < ALTITUD_MINIMA) {
            this.altitud = ALTITUD_MINIMA;
        } else if (this.altitud > ALTITUD_MAXIMA) {
            this.altitud = ALTITUD_MAXIMA;
        }
    }

    public void recibirDaño(double porcentajeDaño) {
        double energiaPerdida = 100.0 * porcentajeDaño;
        this.energia -= energiaPerdida;

        if (this.energia <= 0) {
            this.energia = 0;
        }
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }
}