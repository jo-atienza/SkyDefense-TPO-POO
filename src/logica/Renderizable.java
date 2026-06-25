package logica;
import java.util.List;
/**
 * Interfaz que expone solo los datos necesarios para que la vista pueda dibujar el estado del juego.
 * RenderizadorVisual depende de esta abstracción y no de la clase concreta Juego.
 */
public interface Renderizable {

    // --- ENTIDADES EN PANTALLA ---
    List<Dron> getDrones();
    List<Misil> getMisilesEnElAire();
    List<ExplosionVisual> getExplosiones();

    // --- DATOS DEL AVIÓN ---
    double getAvionX();
    double getAvionAltitud();
    double getAvionEnergia();

    // --- DATOS DEL JUGADOR ---
    int getVidas();
    int getPuntaje();

    // --- DATOS DEL NIVEL ---
    int getNumeroNivel();

    // --- ESTADOS DEL JUEGO ---
    boolean isJuegoGanado();
    boolean isJuegoTerminado();
    boolean isPausado();
    boolean isEnTransicionNivel();
}