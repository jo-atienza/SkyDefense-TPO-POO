public class Explosion {

    private double dañoCalculado;
    private int puntosOtorgados;
    private boolean quitaVida;

    // Al crear la explosión, le pasamos la distancia y ella calcula el resto
    public Explosion(double distancia) {
        calcularEfectos(distancia);
    }

    private void calcularEfectos(double distancia) {
        if (distancia > 150.0) {
            this.puntosOtorgados = 40;
            this.dañoCalculado = 0.0;
            this.quitaVida = false;
        } else if (distancia >= 80.0 && distancia <= 150.0) {
            this.puntosOtorgados = 20;
            this.dañoCalculado = 0.20; // 20% de daño
            this.quitaVida = false;
        } else if (distancia >= 20.0 && distancia < 80.0) {
            this.puntosOtorgados = 0;
            this.dañoCalculado = 0.40; // 40% de daño
            this.quitaVida = false;
        } else {
            // Menos de 20 metros
            this.puntosOtorgados = 0;
            this.dañoCalculado = 0.0;
            this.quitaVida = true;
        }
    }

    // Getters
    public double getDañoCalculado() {
        return dañoCalculado;
    }

    public int getPuntosOtorgados() {
        return puntosOtorgados;
    }

    public boolean isQuitaVida() {
        return quitaVida;
    }
}