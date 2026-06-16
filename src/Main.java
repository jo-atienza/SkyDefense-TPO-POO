/* import excepciones.*;
import gui.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Sky Defense ---");

        // Al instanciar el controlador principal:
        // Juego crea a Nivel, Jugador, Escuadron, Avion, etc.
        Juego juego = new Juego();
        //Creamos la ventana física
        VentanaJuego ventana = new VentanaJuego(juego); // <-- Le pasamos la lógica a la GUI        ventana.setVisible(true);
        // imprime  algo en la consola para verificar que todo funciona.
        if (!juego.isJuegoTerminado()) {
            System.out.println("¡El juego ha sido inicializado correctamente!");
            System.out.println("Listo para defender el espacio aéreo.");
        }
    }
}*/
import gui.*;
import logica.Juego;

public class Main {
    public static void main(String[] args) {
        Juego juego = new Juego();
        VentanaJuego ventana = new VentanaJuego(juego);
        ventana.setVisible(true);
    }
}