package lamportMutexCS420;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            //Name processes that were created in server
            ProcessInterface process0 = (ProcessInterface) Naming.lookup("//localhost/Process0");
            ProcessInterface process1 = (ProcessInterface) Naming.lookup("//localhost/Process1");
            ProcessInterface process2 = (ProcessInterface) Naming.lookup("//localhost/Process2");
            ProcessInterface process3 = (ProcessInterface) Naming.lookup("//localhost/Process3");

            System.out.println("Processes are ready.");

            process0.requestCriticalSection();
            process2.requestCriticalSection();
            process0.releaseCriticalSection();
            process1.requestCriticalSection();
            process3.requestCriticalSection();
            process2.releaseCriticalSection();
            process1.releaseCriticalSection();
            process3.releaseCriticalSection();
            																	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
