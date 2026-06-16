package gui;

import logica.Juego;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PanelJuego extends JPanel implements ActionListener {

    private Juego juego;
    private Timer timer;
    private boolean enMenuInicio;

    // --- NUESTROS AYUDANTES ---
    private ManejadorTeclado teclado;
    private RenderizadorVisual renderizador;

    public PanelJuego(Juego juegoLogico) {
        this.juego = juegoLogico;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.enMenuInicio = true;

        // 1. Delegamos el teclado
        this.teclado = new ManejadorTeclado();
        this.addKeyListener(teclado);

        // 2. Cargamos la imagen y delegamos el dibujo
        Image img = null;
        try {
            img = ImageIO.read(new File("src/recursos/avion.png"));
        } catch (IOException e) {
            System.out.println("No se encontró la imagen.");
        }
        this.renderizador = new RenderizadorVisual(img);

        // 3. Arrancamos el motor
        this.timer = new Timer(16, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (enMenuInicio) {
            if (teclado.isEnter()) {
                enMenuInicio = false;
                teclado.resetEnter(); // Apagamos el enter para que no quede pegado
            }
        } else if (!juego.isJuegoTerminado()) {
            double deltaX = 0;
            double deltaY = -2.0;

            // Le consultamos al ayudante del teclado qué botones están presionados
            if (teclado.isIzq()) deltaX -= 5.0;
            if (teclado.isDer()) deltaX += 5.0;
            if (teclado.isArriba()) deltaY += 7.0;
            if (teclado.isAbajo()) deltaY -= 4.0;

            try {
                juego.moverAvion(deltaX, deltaY);
            } catch (RuntimeException excepcion) {}

            juego.actualizarEstado();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Le pasamos el pincel "g" a nuestro ayudante para que dibuje
        if (enMenuInicio) {
            renderizador.dibujarMenu(g);
        } else {
            renderizador.dibujarJuego(g, juego);
        }
    }
}