package gui;
import logica.Juego;
import javax.swing.JFrame;


public class VentanaJuego extends JFrame {

    public VentanaJuego(Juego juego) {
        this.setTitle("Sky Defense - TPO");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Pegamos el lienzo a la ventana
        PanelJuego panel = new PanelJuego(juego);
        this.add(panel);
    }
}