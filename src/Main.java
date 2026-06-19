import gui.*;
import logica.Juego;

public class Main {
    public static void main(String[] args) {
        Juego juego = new Juego();
        VentanaJuego ventana = new VentanaJuego(juego);
        ventana.setVisible(true);
    }
}