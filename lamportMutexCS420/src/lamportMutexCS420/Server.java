package lamportMutexCS420;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            //Create MutexManager object and bind it to RMI registry
            MutexManager manager = new MutexManager();
            Naming.rebind("rmi://localhost/MutexManager", manager);
            System.out.println("MutexManager is ready.");
            
            for (int i = 0; i < 4; i++) {		//Initializing processes and assigning them to MutexManager
                Process process = new Process(i, manager);
                Naming.rebind("//localhost/Process" + i, process);
            }
            System.out.println("Processes are ready.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
