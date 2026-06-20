package gui;
import logica.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelJuego extends JPanel implements ActionListener {
    private Juego juego;
    private Timer timer;
    private boolean enMenuInicio;
    private ManejadorTeclado teclado;
    private RenderizadorVisual renderizador;

    public PanelJuego(Juego juegoLogico) {
        this.juego = juegoLogico;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.enMenuInicio = true;
        // Delegamos el teclado pasándole el juego para que maneje Pausa/Enter
        this.teclado = new ManejadorTeclado(juegoLogico);
        this.addKeyListener(teclado);
        // El renderizador inicia vacío porque gestiona sus propios recursos
        this.renderizador = new RenderizadorVisual();
        this.timer = new Timer(16, this);
        this.timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (enMenuInicio) {
            if (teclado.isEnter()) {
                enMenuInicio = false;
                teclado.resetEnter();
                if (juego.isPausado()) {
                    juego.conmutarPausa();
                }
            }
        } else if (!juego.isJuegoTerminado()) {
            double deltaX = 0;
            double deltaY = -1.2; // "Gravedad suavizada"
            // Controles regulados
            if (teclado.isIzq()) deltaX -= 7.0;
            if (teclado.isDer()) deltaX += 7.0;
            if (teclado.isArriba()) deltaY += 8.0;
            if (teclado.isAbajo()) deltaY -= 4.0;
            try {
                juego.moverAvion(deltaX, deltaY);
            } catch (RuntimeException excepcion) {
                // Las excepciones de fin de juego mueren acá silenciosamente
            }
            juego.actualizarEstado();
        }
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enMenuInicio) {
            renderizador.dibujarMenu(g);
        } else {
            renderizador.dibujarJuego(g, juego);
        }
    }
}