import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaDeDetonacionTest {

    // --- 1. TEST DE DISTANCIA LETAL (< 20 metros) ---
    @Test
    public void testExplosionCercanaQuitaVida() {
        Jugador jugador = new Jugador(3);
        Avion avion = new Avion(100.0, 1500.0, 100.0);
        // Misil a 10 metros del avión
        Misil misil = new Misil(100.0, 1510.0);
        SistemaDeDetonacion sistema = new SistemaDeDetonacion();

        sistema.aplicarEfecto(misil, avion, jugador);

        assertEquals(2, jugador.getVidas(), "Explosión letal (< 20m): debería quitar 1 vida.");
        assertEquals(100.0, avion.getEnergia(), "Si quita vida, no resta energía extra.");
    }

    // --- 2. TEST DE DISTANCIA MEDIA (Entre 80 y 150 metros) ---
    @Test
    public void testExplosionMediaQuitaEnergia() {
        Jugador jugador = new Jugador(3);
        Avion avion = new Avion(100.0, 1500.0, 100.0);
        // Misil a 100 metros exactos del avión (1600 - 1500)
        Misil misil = new Misil(100.0, 1600.0);
        SistemaDeDetonacion sistema = new SistemaDeDetonacion();

        sistema.aplicarEfecto(misil, avion, jugador);

        assertEquals(80.0, avion.getEnergia(), "Explosión media (80-150m): debería quitar 20% de energía (100 - 20 = 80).");
        assertEquals(20, jugador.getPuntaje(), "Debería sumar 20 puntos al jugador.");
        assertEquals(3, jugador.getVidas(), "No debería perder vidas en esta distancia.");
    }

    // --- 3. TEST DE DISTANCIA LEJANA (> 150 metros) ---
    @Test
    public void testExplosionLejanaSumaPuntos() {
        Jugador jugador = new Jugador(3);
        Avion avion = new Avion(100.0, 1500.0, 100.0);
        // Misil a 200 metros del avión
        Misil misil = new Misil(100.0, 1700.0);
        SistemaDeDetonacion sistema = new SistemaDeDetonacion();

        sistema.aplicarEfecto(misil, avion, jugador);

        assertEquals(100.0, avion.getEnergia(), "Explosión lejana (> 150m): no hace daño al avión.");
        assertEquals(40, jugador.getPuntaje(), "Debería sumar 40 puntos limpios.");
        assertEquals(3, jugador.getVidas(), "Las vidas quedan intactas.");
    }

    // --- 4. TEST DE VIDA EXTRA (Regla de los 1000 puntos) ---
    @Test
    public void testJugadorGanaVidaExtraCadaMilPuntos() {
        Jugador jugador = new Jugador(3); // Arranca con 3

        // Le sumamos 1000 puntos de golpe
        jugador.sumarPuntos(1000);

        assertEquals(4, jugador.getVidas(), "Al llegar a 1000 puntos, debería recibir una vida extra (4 en total).");
    }

    // --- 5. TEST DE INCREMENTO DE DIFICULTAD (Regla del 15%) ---
    @Test
    public void testIncrementoDificultadNivelDos() {
        Nivel nivelDos = new Nivel(2);

        // El factor para el nivel 2 debería ser: 1.0 + (0.15 * 1) = 1.15
        // El 0.001 extra es un margen de tolerancia que exige Java al comparar números decimales (doubles)
        assertEquals(1.15, nivelDos.calcularFactor(), 0.001, "El nivel 2 debería aumentar las velocidades exactamente un 15% (1.15).");
    }
}