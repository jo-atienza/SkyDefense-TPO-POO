public class Jugador {

    private int puntaje;
    private int vidas;
    // Variable interna para saber cuándo dar la próxima vida extra
    private int proximoObjetivoVida;

    public Jugador(int vidasIniciales) {
        this.puntaje = 0;
        this.vidas = vidasIniciales;
        this.proximoObjetivoVida = 1000;
    }

    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
        obtenerVidaExtra();
    }

    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
        }
    }

    public void obtenerVidaExtra() {
        // Verifica si pasamos la barrera de los 1000, 2000, 3000, etc.
        if (this.puntaje >= this.proximoObjetivoVida) {
            this.vidas++;
            this.proximoObjetivoVida += 1000;
        }
    }

    // Getters
    public int getPuntaje() {
        return puntaje;
    }

    public int getVidas() {
        return vidas;
    }
}