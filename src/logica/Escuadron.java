package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Escuadron {
    private List<Dron> drones;
    private int dronesActivos;
    private int dronesCreados; // NUEVO: Histórico de drones creados

    // Constantes para respetar las reglas del negocio del TP
    private static final int MAX_DRONES_TOTALES = 10;
    private static final int MAX_DRONES_ACTIVOS = 4;

    // Constructor
    public Escuadron() {
        this.drones = new ArrayList<>();
        this.dronesActivos = 0;
        this.dronesCreados = 0; // Inicializamos en 0
    }

    // --- EL ESCUADRÓN CONTROLA A SUS DRONES ---
    public void actualizar(double factorDificultad, List<Misil> misilesEnElAire) {
        if (!estaCompleto() && verificarActivos()) {
            if (Math.random() < 0.02) {
                crearDron(factorDificultad);
            }
        }

        Iterator<Dron> iterador = drones.iterator();
        while (iterador.hasNext()) {
            Dron dron = iterador.next();
            dron.mover();

            // Si el dron sale de la pantalla, lo borramos
            if (dron.getPosicionX() < -50 || dron.getPosicionX() > 1050) {
                iterador.remove();
                registrarDronDestruidoOCompletado();
                continue;
            }

            // Verificamos si el dron dispara en este frame
            if (dron.verificarDisparo()) {
                Misil nuevoMisil = dron.disparar();
                nuevoMisil.setVelocidadCaida(nuevoMisil.getVelocidadCaida() * factorDificultad);
                misilesEnElAire.add(nuevoMisil);
            }
        }
    }

    private void crearDron(double factorDificultad) {
        boolean ladoIzq = Math.random() < 0.5;
        double posX = ladoIzq ? -10.0 : 1010.0;
        double altitudDron = 4000.0 + (Math.random() * 1000);

        Dron nuevoDron = new Dron(posX, altitudDron, ladoIzq, 5.0, 0.01);
        nuevoDron.actualizarVelocidades(factorDificultad);

        this.drones.add(nuevoDron);
        this.dronesCreados++; // SUMAMOS AL HISTÓRICO
        this.registrarDronEnPantalla();
    }

    public void agregarDron(Dron dron) {
        if (!estaCompleto()) {
            this.drones.add(dron);
            this.dronesCreados++;
        } else {
            System.out.println("El escuadrón ya tiene sus 10 drones.");
        }
    }

    // Método que verifica si el escuadrón ya llegó a su capacidad máxima
    public boolean estaCompleto() {
        // AHORA evalúa el histórico de creados, no la lista actual
        return this.dronesCreados >= MAX_DRONES_TOTALES;
    }

    // Método para saber si podemos mandar otro dron a la pantalla
    public boolean verificarActivos() {
        return this.dronesActivos < MAX_DRONES_ACTIVOS;
    }

    public void registrarDronEnPantalla() {
        if (verificarActivos()) {
            this.dronesActivos++;
        }
    }

    public void registrarDronDestruidoOCompletado() {
        if (this.dronesActivos > 0) {
            this.dronesActivos--;
        }
    }

    // Getters
    public List<Dron> getDrones() {
        return drones;
    }

    public int getDronesActivos() {
        return dronesActivos;
    }
}