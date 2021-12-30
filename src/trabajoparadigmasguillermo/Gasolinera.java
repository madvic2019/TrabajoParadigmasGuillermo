package trabajoparadigmasguillermo;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Guillermo Díaz García
 */
public class Gasolinera {
    public Queue<Vehiculo> cola;
    
    public Gasolinera (){
        this.cola = new LinkedList<>();
    }
}
