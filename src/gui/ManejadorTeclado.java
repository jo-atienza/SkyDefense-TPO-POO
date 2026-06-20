package gui;

import logica.Juego;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManejadorTeclado extends KeyAdapter {
    private Juego juego;
    private boolean izq, der, arriba, abajo, enter;

    public ManejadorTeclado(Juego juego) {
        this.juego = juego;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            // 1. Levantamos la bandera para que PanelJuego la lea y salga del menú
            this.enter = true;
            // 2. Lógica de transiciones y pausa dentro del juego
            if (juego.isEnTransicionNivel()) {
                juego.confirmarDespegue();
            } else {
                juego.conmutarPausa();
            }
        }
        // Controles de movimiento
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
        // El enter no se apaga acá, lo resetea el PanelJuego con resetEnter()
    }
    // --- GETTERS ---
    public boolean isIzq() { return izq; }
    public boolean isDer() { return der; }
    public boolean isArriba() { return arriba; }
    public boolean isAbajo() { return abajo; }
    public boolean isEnter() { return enter; }
    public void resetEnter() { this.enter = false; }
}