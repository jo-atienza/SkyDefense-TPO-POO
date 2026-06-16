package gui;

import logica.Juego;
import logica.Dron;
import logica.Misil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class RenderizadorVisual {
    private Image imagenAvion;

    public RenderizadorVisual(Image imagenAvion) {
        this.imagenAvion = imagenAvion;
    }

    public void dibujarMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("SKY DEFENSE", 310, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Presiona ENTER para despegar", 350, 320);
    }

    public void dibujarJuego(Graphics g, Juego juego) {
        // 1. Dibujamos el avión (Verde o Imagen)
        int posAvionX = (int) juego.getAvion().getPosicionX();
        int posAvionY = 600 - (int) (juego.getAvion().getAltitud() / 10);

        if (imagenAvion != null) {
            g.drawImage(imagenAvion, posAvionX, posAvionY, 50, 50, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(posAvionX, posAvionY, 50, 50);
        }

        // --- LO NUEVO: LA GUERRA VISIBLE ---

        // 2. Dibujamos los Drones (Rectángulos Rojos)
        g.setColor(Color.RED);
        for (Dron dron : juego.getEscuadron().getDrones()) {
            int dronX = (int) dron.getPosicionX();
            // Misma fórmula de escala que usamos para el avión
            int dronY = 600 - (int) (dron.getAltitud() / 10);

            // Dibujamos un dron de 40px de ancho por 20px de alto
            g.fillRect(dronX, dronY, 40, 20);
        }

        // 3. Dibujamos los Misiles (Óvalos Amarillos)
        g.setColor(Color.YELLOW);
        for (Misil misil : juego.getMisilesEnElAire()) {
            int misilX = (int) misil.getPosicionX();
            int misilY = 600 - (int) (misil.getAltitud() / 10);

            // Dibujamos un misil de 10px de ancho por 20px de alto
            g.fillOval(misilX, misilY, 10, 20);
        }

        // -----------------------------------

        // 4. Dibujamos el HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntaje: " + juego.getJugador().getPuntaje(), 20, 30);
        g.drawString("Vidas: " + juego.getJugador().getVidas(), 200, 30);
        g.drawString("Energía: " + (int) juego.getAvion().getEnergia() + "%", 350, 30);
        g.drawString("Nivel: " + juego.getNivelActual().getNumeroNivel(), 850, 30);

        // 5. Game Over
        if (juego.isJuegoTerminado()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("GAME OVER", 300, 300);
        }
    }
}