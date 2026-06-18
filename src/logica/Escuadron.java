package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Escuadron {
    private List<Dron> drones;
    private int dronesActivos;
    private int dronesCreados;

    private static final int MAX_DRONES_TOTALES = 10;
    private static final int MAX_DRONES_ACTIVOS = 4;

    public Escuadron() {
        this.drones = new ArrayList<>();
        this.dronesActivos = 0;
        this.dronesCreados = 0;
    }

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

            double posX = dron.getPosicionX();

            // 1. LÓGICA DE BORDES CORREGIDA (Adaptada a 1200 de ancho)
            if (dron.isRetirandose()) {
                if (posX < -100 || posX > 1300) {
                    iterador.remove();
                    registrarDronDestruidoOCompletado();
                    continue;
                }
            } else {
                if (posX <= 0 || posX >= 1140) {
                    dron.revertirDireccion();
                    dron.mover();
                }
            }

            // 2. LÓGICA DE DISPARO RESTRINGIDA
            if (posX > 20 && posX < 1120) {
                if (dron.verificarDisparo()) {
                    Misil nuevoMisil = dron.disparar();
                    nuevoMisil.setVelocidadCaida(nuevoMisil.getVelocidadCaida() * factorDificultad);
                    misilesEnElAire.add(nuevoMisil);
                }
            }
        }
    }

    private void crearDron(double factorDificultad) {
        boolean ladoIzq = Math.random() < 0.5;
        double posX = ladoIzq ? 1.0 : 1139.0;
        double altitudDron = 4000.0 + (Math.random() * 800);

        Dron nuevoDron = new Dron(posX, altitudDron, ladoIzq, 6.0, 0.02);
        nuevoDron.actualizarVelocidades(factorDificultad);

        this.drones.add(nuevoDron);
        this.dronesCreados++;
        this.registrarDronEnPantalla();
    }

    public boolean estaCompleto() { return this.dronesCreados >= MAX_DRONES_TOTALES; }
    public boolean verificarActivos() { return this.dronesActivos < MAX_DRONES_ACTIVOS; }
    public void registrarDronEnPantalla() { if (verificarActivos()) this.dronesActivos++; }
    public void registrarDronDestruidoOCompletado() { if (this.dronesActivos > 0) this.dronesActivos--; }
    public List<Dron> getDrones() { return drones; }
    public int getDronesActivos() { return dronesActivos; }
}