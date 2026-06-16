package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManejadorTeclado extends KeyAdapter {
    private boolean izq, der, arriba, abajo, enter;

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) izq = true;
        if (tecla == KeyEvent.VK_RIGHT) der = true;
        if (tecla == KeyEvent.VK_UP) arriba = true;
        if (tecla == KeyEvent.VK_DOWN) abajo = true;
        if (tecla == KeyEvent.VK_ENTER) enter = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) izq = false;
        if (tecla == KeyEvent.VK_RIGHT) der = false;
        if (tecla == KeyEvent.VK_UP) arriba = false;
        if (tecla == KeyEvent.VK_DOWN) abajo = false;
        if (tecla == KeyEvent.VK_ENTER) enter = false;
    }

    // Getters para que el Panel pueda consultar el estado
    public boolean isIzq() { return izq; }
    public boolean isDer() { return der; }
    public boolean isArriba() { return arriba; }
    public boolean isAbajo() { return abajo; }
    public boolean isEnter() { return enter; }

    // Para apagar el Enter una vez que arrancamos el juego
    public void resetEnter() { this.enter = false; }
}