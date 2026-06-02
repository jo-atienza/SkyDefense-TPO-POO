public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Sky Defense ---");

        // Al instanciar el controlador principal:
        // Juego crea a Nivel, Jugador, Escuadron, Avion, etc.
        Juego juego = new Juego();

        // imprime  algo en la consola para verificar que todo funciona.
        if (!juego.isJuegoTerminado()) {
            System.out.println("¡El juego ha sido inicializado correctamente!");
            System.out.println("Listo para defender el espacio aéreo.");
        }
    }
}