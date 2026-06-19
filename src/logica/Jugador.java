package logica;

import excepciones.ParametroInvalidoException;

public class Jugador {
    private int vidas;
    private int puntaje;

    // NUEVO: Definimos el tope máximo de vidas acumulables
    private static final int MAX_VIDAS = 5;

    public Jugador(int vidasIniciales) {
        if (vidasIniciales<=0){
            throw new ParametroInvalidoException("El valor de vidas iniciales no debe ser menor o igual que 0");
        }
        this.vidas = vidasIniciales;
        this.puntaje = 0;
    }

    public void sumarPuntos(int puntos) {
        int puntajeAnterior = this.puntaje;
        this.puntaje += puntos;

        // Lógica para detectar si cruzamos un múltiplo de 1000
        // Ej: puntajeAnterior era 980 (980/1000 = 0). puntaje nuevo es 1020 (1020/1000 = 1).
        if ((this.puntaje / 1000) > (puntajeAnterior / 1000)) {
            ganarVida();
        }
    }

    public void ganarVida() {
        // MODIFICADO: Solo sumamos vida si no llegamos al tope
        if (this.vidas < MAX_VIDAS) {
            this.vidas++;
        }
    }

    public void perderVida() {
        this.vidas--;
    }

    // --- GETTERS ---
    public int getVidas() {
        return vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }
}