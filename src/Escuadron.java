import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Escuadron {
    private List<Dron> drones;
    private int dronesActivos;
    // Constantes para respetar las reglas del negocio del TP
    private static final int MAX_DRONES_TOTALES = 10;
    private static final int MAX_DRONES_ACTIVOS = 4;
    // Constructor
    public Escuadron() {
        // Inicializamos la lista vacía para evitar errores NullPointerException
        this.drones = new ArrayList<>();
        this.dronesActivos = 0;
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
            if (dron.getPosicionX() < -50 || dron.getPosicionX() > 1050) {
                iterador.remove();
                registrarDronDestruidoOCompletado();
                continue;
            }
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
        this.registrarDronEnPantalla();
    }
    // Método para sumar un dron a la lista (Restaurado para el controlador Juego)
    public void agregarDron(Dron dron) {
        // Solo lo agrega si el escuadrón todavía no llegó al límite de 10
        if (!estaCompleto()) {
            this.drones.add(dron);
        } else {
            System.out.println("El escuadrón ya tiene sus 10 drones.");
        }
    }
    // Método que verifica si el escuadrón ya llegó a su capacidad máxima
    public boolean estaCompleto() {
        return this.drones.size() >= MAX_DRONES_TOTALES;
    }
    // Método para saber si podemos mandar otro dron a la pantalla
    public boolean verificarActivos() {
        // Retorna true si hay menos de 4 drones activos
        return this.dronesActivos < MAX_DRONES_ACTIVOS;
    }
    // Métodos extra de apoyo para que el controlador
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