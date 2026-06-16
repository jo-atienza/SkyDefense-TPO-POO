package gui;
import logica.Juego;
import excepciones.RecursoVisualNoEncontradoException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class PanelJuego extends JPanel implements ActionListener {

    private Juego juego;
    private Timer timer;
    private Image imagenAvion;

    // INTERRUPTORES DE DIRECCIÓN (Arrancan apagados)
    private boolean izq, der, arriba, abajo;

    public PanelJuego(Juego juegoLogico) {
        this.juego = juegoLogico;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        try {
            this.imagenAvion = ImageIO.read(new File("src/recursos/avion.png"));
        } catch (IOException e) {
            // Podés comentar el throw y dejar el System.out para que el juego arranque igual
            System.out.println("No se encontró la imagen, dibujando cuadrado verde.");
            // throw new RecursoVisualNoEncontradoException("src/recursos/avion.png");
        }

        // EL OÍDO: Ahora solo enciende y apaga interruptores
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();
                if (tecla == KeyEvent.VK_LEFT) izq = true;
                if (tecla == KeyEvent.VK_RIGHT) der = true;
                if (tecla == KeyEvent.VK_UP) arriba = true;
                if (tecla == KeyEvent.VK_DOWN) abajo = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int tecla = e.getKeyCode();
                if (tecla == KeyEvent.VK_LEFT) izq = false;
                if (tecla == KeyEvent.VK_RIGHT) der = false;
                if (tecla == KeyEvent.VK_UP) arriba = false;
                if (tecla == KeyEvent.VK_DOWN) abajo = false;
            }
        });

        this.timer = new Timer(16, this);
        this.timer.start();
    }

    // EL GAME LOOP: Acá ocurre la magia de las físicas (60 FPS)
    @Override
    public void actionPerformed(ActionEvent e) {

        double deltaX = 0;
        // LA GRAVEDAD: Siempre tira el avión 2 metros para abajo en cada frame
        double deltaY = -2.0;

        // Evaluamos los motores encendidos
        if (izq) deltaX -= 5.0; // Movimiento horizontal fluido
        if (der) deltaX += 5.0;

        // Si aceleramos hacia arriba, le ganamos a la gravedad (ej: empuja 7, contrarresta 2 = sube 5)
        if (arriba) deltaY += 7.0;
        // Si apuntamos hacia abajo, caemos en picada sumando la fuerza a la gravedad
        if (abajo) deltaY -= 4.0;

        // Le mandamos el cálculo final a tu lógica intacta
        try {
            juego.moverAvion(deltaX, deltaY);
        } catch (RuntimeException excepcion) {
            // Silenciamos el error de límite en consola para no saturarla a 60 FPS
        }

        juego.actualizarEstado();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int posX = (int) juego.getAvion().getPosicionX();
        int posY = 600 - (int) (juego.getAvion().getAltitud() / 10);

        if (imagenAvion != null) {
            g.drawImage(imagenAvion, posX, posY, 50, 50, this);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(posX, posY, 50, 50);
        }
    }
}