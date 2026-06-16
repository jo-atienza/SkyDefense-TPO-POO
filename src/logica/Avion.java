package logica;

import excepciones.AltitudFueraDeRangoException;
public class Avion extends EntidadMovil {

    private double energia;

    // Constantes para respetar las reglas del negocio del TP
    private static final double ALTITUD_MINIMA = 1000.0;
    private static final double ALTITUD_MAXIMA = 5000.0;

    // Constructor: Usamos 'super' para enviarle la X y la altitud a EntidadMovil
    public Avion(double posicionX, double altitud, double energiaInicial) {
        super(posicionX, altitud);
        if (altitud < 1000.0 || altitud > 5000.0) {
            throw new AltitudFueraDeRangoException(altitud);
        }
        this.energia = energiaInicial;
        // Validación inicial por si se crea fuera de los límites
        limitarAltitud();
    }

    // Sobreescribimos el método abstracto obligatorio de la clase padre
    @Override
    public void mover() {
    }

    // --- OVERLOAD (Sobrecarga): Reemplaza a moverHorizontal y cambiarAltitud ---
    public void mover(double deltaX, double deltaY) {
        this.posicionX += deltaX;
        this.altitud += deltaY;
        limitarAltitud();
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
    public void recibirDaño(double porcentajeDaño) {
        // Multiplicamos por 100.0 fijo, no por this.energia
        double energiaPerdida = 100.0 * porcentajeDaño;
        this.energia -= energiaPerdida;

        if (this.energia <= 0) {
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