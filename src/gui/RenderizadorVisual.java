package gui;

import excepciones.RecursoVisualNoEncontradoException;
import logica.*;
import logica.Dron;
import logica.Misil;
import logica.ExplosionVisual;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class RenderizadorVisual {
    private Image imagenAvion;
    private Image imagenDron;
    private Image imagenExplosion;
    private Image[] fondos;
    public RenderizadorVisual() {
        this.fondos = new Image[5];
        cargarRecursos();
    }
    private void cargarRecursos() {
        // Rutas exactas de fondos
        String[] rutasFondos = {
                "src/recursos/Amanecer-Nivel1.png",
                "src/recursos/Mediodia-Nivel 2.png",
                "src/recursos/Atardecer-Nivel 3.png",
                "src/recursos/Noche- Nivel 4.png",
                "src/recursos/Noche- Nivel 5.png"
        };
        for (int i = 0; i < rutasFondos.length; i++) {
            try {
                this.fondos[i] = ImageIO.read(new File(rutasFondos[i]));
            } catch (IOException e) {
                throw new RecursoVisualNoEncontradoException("No se encontro el recurso visual determinado");
            }
        }
        try {
            this.imagenAvion = ImageIO.read(new File("src/recursos/avion.png"));
            this.imagenDron = ImageIO.read(new File("src/recursos/dron.png"));
            this.imagenExplosion = ImageIO.read(new File("src/recursos/explosion.png"));
        } catch (IOException e) {
            throw new RecursoVisualNoEncontradoException("No se encontro el recurso visual determinado");
        }
    }

    public void dibujarMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1200, 700);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("SKY DEFENSE", 410, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Presiona ENTER para despegar", 450, 320);
    }

    public void dibujarJuego(Graphics g, Juego juego) {
        // FONDO SEGUN NIVEL
        int nivelActual = juego.getNivelActual().getNumeroNivel();
        int indiceFondo = nivelActual - 1;
        if (indiceFondo >= 0 && indiceFondo < fondos.length && fondos[indiceFondo] != null) {
            g.drawImage(fondos[indiceFondo], 0, 0, 1200, 700, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1200, 700);
        }
        // DIBUJA EL AVIÓN
        int posAvionX = (int) juego.getAvion().getPosicionX();
        int posAvionY = 700 - (int) (juego.getAvion().getAltitud() / 10);
        if (imagenAvion != null) {
            // Agrandamos a 70x70 y lo centramos
            g.drawImage(imagenAvion, posAvionX - 35, posAvionY - 35, 70, 70, null);
        }
        // DIBUJA DRONES
        for (Dron dron : juego.getEscuadron().getDrones()) {
            int dronX = (int) dron.getPosicionX();
            int dronY = 700 - (int) (dron.getAltitud() / 10);
            if (imagenDron != null) {
                // Agrandamos a 60x60
                g.drawImage(imagenDron, dronX - 30, dronY - 30, 60, 60, null);
            }
        }
        //DIBUJA MISILES Y EXPLOSIONES
        g.setColor(Color.YELLOW);
        for (Misil misil : juego.getMisilesEnElAire()) {
            int misilX = (int) misil.getPosicionX();
            int misilY = 700 - (int) (misil.getAltitud() / 10);
            g.fillOval(misilX - 5, misilY - 10, 10, 20);
        }
        if (imagenExplosion != null) {
            for (ExplosionVisual ev : juego.getPoolExplosiones()) {
                int expX = (int) ev.getPosicionX();
                int expY = 700 - (int) (ev.getAltitud() / 10);
                int tFinal = 60 + ((40 - ev.getTiempoRestante()) / 2);
                g.drawImage(imagenExplosion, expX - (tFinal/2), expY - (tFinal/2), tFinal, tFinal, null);
            }
        }
        //Informacion del jugador en la partida
        Font fuenteArcade = new Font("Monospaced", Font.BOLD, 18);
        g.setFont(fuenteArcade);
        g.setColor(Color.BLACK);
        g.drawString("Puntaje: " + juego.getJugador().getPuntaje(), 22, 32);
        g.drawString("Vidas: " + juego.getJugador().getVidas(), 202, 32);
        g.drawString("Energía: " + (int) juego.getAvion().getEnergia() + "%", 352, 32);
        g.drawString("Nivel: " + nivelActual, 1052, 32);
        g.setColor(Color.WHITE);
        g.drawString("Puntaje: " + juego.getJugador().getPuntaje(), 20, 30);
        g.drawString("Vidas: " + juego.getJugador().getVidas(), 200, 30);
        g.drawString("Energía: " + (int) juego.getAvion().getEnergia() + "%", 350, 30);
        g.drawString("Nivel: " + nivelActual, 1050, 30);
        //CARTELES DE TRANSICION
        if (juego.isJuegoGanado()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, 1200, 700);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 50));
            g.drawString("¡MISIÓN CUMPLIDA!", 340, 280);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.drawString("Has defendido los cielos exitosamente.", 360, 330);
        } else if (juego.isJuegoTerminado()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, 1200, 700);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 60));
            g.drawString("GAME OVER", 430, 300);
        } else if (juego.isPausado()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 60));
            g.drawString("PAUSA", 500, 300);
        } else if (juego.isEnTransicionNivel()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("¡OLEADA COMPLETADA!", 380, 260);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.drawString("Presiona ENTER para despegar al Nivel " + (nivelActual + 1), 330, 330);
        }
    }
}