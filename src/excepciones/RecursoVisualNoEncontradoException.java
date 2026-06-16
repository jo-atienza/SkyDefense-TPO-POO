package excepciones;

public class RecursoVisualNoEncontradoException extends RuntimeException {

    public RecursoVisualNoEncontradoException(String rutaArchivo) {
        super("ERROR CRÍTICO: No se pudo encontrar ni cargar el recurso visual en la ruta -> " + rutaArchivo);
    }
}