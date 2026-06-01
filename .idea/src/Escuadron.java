import java.util.List;
import java.util.ArrayList;

public class Escuadron {
    private int dronesActivos;
    private List<Dron> drones;

    public Escuadron() {
        this.dronesActivos = 0;
        this.drones = new ArrayList<>();
    }
}