package gui;
import logica.Juego;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManejadorTeclado extends KeyAdapter {
    private Juego juego; //teclado conoce al motor del juego (acoplamiento necesario)
    private boolean izq, der, arriba, abajo, enter;
    public ManejadorTeclado(Juego juego) {
        this.juego = juego;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_ENTER) {
            //Si estamos en la pantalla intermedia esto nos permite empezar
            if (juego.isEnTransicionNivel()){
                juego.confirmarDespegue();
            }
            //Sino si estamos en pleno juego, esto nos permite pausar/despausar
            else {
                juego.conmutarPausa();
            }
        }
        //MOVIMIENTO
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
    public boolean isIzq() { return izq; }
    public boolean isDer() { return der; }
    public boolean isArriba() { return arriba; }
    public boolean isAbajo() { return abajo; }
    public boolean isEnter() { return enter; }
    public void resetEnter() { this.enter = false; }
}