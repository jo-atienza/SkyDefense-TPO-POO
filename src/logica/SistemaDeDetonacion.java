package logica;

public class SistemaDeDetonacion {
    /* PATRÓN SINGLETON
    1. Variable estática privada para guardar la única instancia*/
    private static SistemaDeDetonacion instancia;
    // 2. Constructor privado para que ninguna otra clase pueda hacer un "new"
    private SistemaDeDetonacion() {}
    // 3. Método público estático para obtener la única instancia
    public static SistemaDeDetonacion getInstance() {
        if (instancia == null) {
            instancia = new SistemaDeDetonacion();
        }
        return instancia;
    }
    public void aplicarEfecto(Misil misil, Avion avion, Jugador jugador) {
        double difX = avion.getPosicionX() - misil.getPosicionX();
        double difY = (avion.getAltitud() / 10.0) - (misil.getAltitud() / 10.0);
        double distanciaVisual = Math.sqrt((difX * difX) + (difY * difY));

        // Instanciamos Explosion pasándole la distancia calculada
        Explosion explosion = new Explosion(distanciaVisual);

        // Aplicamos los efectos delegando la responsabilidad matemática en el objeto Explosion
        if (explosion.getPuntosOtorgados() > 0) {
            jugador.sumarPuntos(explosion.getPuntosOtorgados());
        }
        if (explosion.getDañoCalculado() > 0) {
            avion.recibirDaño(explosion.getDañoCalculado());
        }
        if (explosion.isQuitaVida()) {
            jugador.perderVida();
            if (jugador.getVidas() > 0) {
                avion.setEnergia(100.0);
            }
        }
    }
}