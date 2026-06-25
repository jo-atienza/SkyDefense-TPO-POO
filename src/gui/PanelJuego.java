package gui;
import logica.Juego;
import excepciones.JuegoYaFinalizadoException;
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
        this.teclado = new ManejadorTeclado(juegoLogico);
        this.addKeyListener(teclado);
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
            double deltaY = -1.2;
            if (teclado.isIzq())    deltaX -= 7.0;
            if (teclado.isDer())    deltaX += 7.0;
            if (teclado.isArriba()) deltaY += 8.0;
            if (teclado.isAbajo())  deltaY -= 4.0;
            //catch especifico en lugar de RuntimeException generico.
            try {
                juego.moverAvion(deltaX, deltaY);
            } catch (JuegoYaFinalizadoException ex) {
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
            // juego es pasado como Renderizable gracias a que Juego implements Renderizable.
            // RenderizadorVisual no conoce Juego: solo ve la interfaz.
            renderizador.dibujarJuego(g, juego);
        }
    }
}