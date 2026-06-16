package excepciones;

public class JuegoYaFinalizadoException extends RuntimeException {

    public JuegoYaFinalizadoException(String accionIntentada) {
        super("Estado inconsistente: Se intentó ejecutar la acción '" + accionIntentada + "' pero el juego ya se encuentra en estado de Game Over.");
    }
}