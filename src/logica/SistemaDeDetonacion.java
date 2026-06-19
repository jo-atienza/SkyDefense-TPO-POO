package logica;

public class SistemaDeDetonacion {
    public void aplicarEfecto(Misil misil, Avion avion, Jugador jugador) {
        // 1. Calculamos la diferencia en X (ya está en escala 1:1)
        double difX = avion.getPosicionX() - misil.getPosicionX();
        // Dividimos por 10 para igualar la escala visual
        double difY = (avion.getAltitud() / 10.0) - (misil.getAltitud() / 10.0);
        double distanciaVisual = Math.sqrt((difX * difX) + (difY * difY));
        if (distanciaVisual > 150.0) {
            // Zona segura: a más de 150 metros
            jugador.sumarPuntos(40);
        } else if (distanciaVisual > 80.0 && distanciaVisual <= 150.0) {
            // Impacto leve: entre 80 y 150 metros
            jugador.sumarPuntos(20);
            avion.recibirDaño(0.20); // Pierde 20% de energía
        } else if (distanciaVisual >= 20.0 && distanciaVisual <= 80.0) {
            // Impacto grave: entre 20 y 80 metros
            avion.recibirDaño(0.40);
        } else if (distanciaVisual < 20.0) {
            //Impacto directo: pierde una vida
            jugador.perderVida();
            // Restauramos la energía al 100% si le quedan vidas para seguir jugando
            if (jugador.getVidas() > 0) {
                avion.setEnergia(100.0);
            }
        }
    }
}