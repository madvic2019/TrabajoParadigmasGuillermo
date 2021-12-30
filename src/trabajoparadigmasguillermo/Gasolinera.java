package trabajoparadigmasguillermo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Guillermo Díaz García
 */
public class Gasolinera {
    private final Queue<String> colaEntrada = new LinkedList<>();
    private final Queue<Surtidor> esperandoOperario = new LinkedList<>();
    private final Surtidor[] surtidores = new Surtidor[8];
    private final Lock entrada = new ReentrantLock();
    private final Condition condEntrada = entrada.newCondition();
    private final Lock lockSurtidores = new ReentrantLock();
    private final Condition condSurt0 = lockSurtidores.newCondition();
    private final Condition condSurt1 = lockSurtidores.newCondition();
    private final Condition condSurt2 = lockSurtidores.newCondition();
    private final Condition condSurt3 = lockSurtidores.newCondition();
    private final Condition condSurt4 = lockSurtidores.newCondition();
    private final Condition condSurt5 = lockSurtidores.newCondition();
    private final Condition condSurt6 = lockSurtidores.newCondition();
    private final Condition condSurt7 = lockSurtidores.newCondition();
    private final boolean[] estadoOperarios = new boolean[5];
    
    public Gasolinera (){
        for(int i=0;i<8;i++){
            Surtidor surtidor = new Surtidor(i);
            surtidores[i] = surtidor;
        }
    }
    
    public int entrarGasolinera(String vehiculo){
        int surt = -1;
        try{ 
            entrada.lock();
            colaEntrada.add(vehiculo);
            surt = surtidorLibre();
            while(surt == -1){
                condEntrada.await();
                surt = surtidorLibre();
            }
        } catch(InterruptedException ex){
            System.out.println("Error awaiting " + vehiculo + " while waiting at entrance");
            //Log here
        } finally {
            entrada.unlock();
        }
        return surt;
    }
    
    public void accederSurtidor(String vehiculo, )
    
    public int operarSurtidor(int num){
        try{
            lockSurtidores.lock();
            int surt = surtidorLibre();
            while(surt == -1){
                
            }
        } catch(){
            
        } finally {
            lockSurtidores.unlock();
        }
    }
    
    private int surtidorLibre(){
        for(Surtidor nav : surtidores){
            if(nav.isLibre()) return nav.getNumero();
        }
        return -1;
    }
}
