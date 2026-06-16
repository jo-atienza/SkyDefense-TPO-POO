package excepciones;

public class AltitudFueraDeRangoException extends RuntimeException {

    public AltitudFueraDeRangoException(double altitud) {
        super("Límite del motor excedido: La altitud ingresada de " + altitud + "m está fuera del rango operativo permitido (1000m - 5000m).");
    }
}