package logica;

public class SistemaDeDetonacion {

    public void aplicarEfecto(Misil misil, Avion avion, Jugador jugador) {
        // 1. Calculamos la diferencia en X (ya está en escala 1:1)
        double difX = avion.getPosicionX() - misil.getPosicionX();

        // 2. NORMALIZAMOS LA DIFERENCIA EN Y (Dividimos por 10 para igualar la escala visual)
        // Sin esto, la hitbox era un óvalo súper aplastado y era imposible recibir impacto directo
        double difY = (avion.getAltitud() / 10.0) - (misil.getAltitud() / 10.0);

        // 3. Teorema de Pitágoras para la distancia visual real (ahora es un círculo perfecto)
        double distanciaVisual = Math.sqrt((difX * difX) + (difY * difY));

        // 4. Evaluamos según las reglas estrictas del cuadro del TP
        if (distanciaVisual > 150.0) {
            // Zona segura: a más de 150 metros
            jugador.sumarPuntos(40);

        } else if (distanciaVisual > 80.0 && distanciaVisual <= 150.0) {
            // Impacto leve: entre 80 y 150 metros
            jugador.sumarPuntos(20);
            avion.recibirDaño(0.20); // Pierde 20% de energía

        } else if (distanciaVisual >= 20.0 && distanciaVisual <= 80.0) {
            // Impacto grave: entre 20 y 80 metros
            // Acá el TP dice que no obtiene puntos, solo pierde energía
            avion.recibirDaño(0.40);

        } else if (distanciaVisual < 20.0) {
            // Impacto directo: a menos de 20 metros (pegó en el centro del avión)
            jugador.perderVida();

            // Restauramos la energía al 100% si le quedan vidas para seguir jugando
            if (jugador.getVidas() > 0) {
                avion.setEnergia(100.0);
            }
        }
    }
}