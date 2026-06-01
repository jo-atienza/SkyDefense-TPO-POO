public class SistemaDeDetonacion {

    public double calcularDistancia(EntidadMovil misil, EntidadMovil avion) {
        double diferenciaX = misil.getPosicionX() - avion.getPosicionX();
        double diferenciaY = misil.getAltitud() - avion.getAltitud();

        // Fórmula de distancia entre dos puntos (Pitágoras)
        return Math.sqrt((diferenciaX * diferenciaX) + (diferenciaY * diferenciaY));
    }

    public void aplicarEfecto(Misil misil, Avion avion, Jugador jugador) {
        // 1. Calculamos a qué distancia explotó
        double distancia = calcularDistancia(misil, avion);

        // 2. Creamos la explosión para que analice esa distancia
        Explosion explosion = new Explosion(distancia);

        // 3. Repartimos las consecuencias
        jugador.sumarPuntos(explosion.getPuntosOtorgados());

        if (explosion.isQuitaVida()) {
            jugador.perderVida();
        } else {
            avion.recibirDaño(explosion.getDañoCalculado());
        }
    }
}