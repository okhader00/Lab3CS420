package lamportMutexCS420;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Process extends UnicastRemoteObject implements ProcessInterface {
    private VectorClock clock;
    private boolean inCriticalSection;
    private MutexManager mutexManager;
    private int processId;

    public Process(int processId, MutexManager mutexManager) throws RemoteException {
        this.clock = new VectorClock();
        this.inCriticalSection = false;
        this.mutexManager = mutexManager;
        this.processId = processId;
    }

    @Override
    public synchronized void requestCriticalSection() throws RemoteException {
        //Increment clock and send request to mutexmanager to be processed
    	clock.increment(processId);
        mutexManager.requestEntry(processId, clock);
    }

    @Override
    public synchronized void releaseCriticalSection() throws RemoteException {
        //Indicate process is no longer in critical section and send release to mutexmanager
    	inCriticalSection = false;
        mutexManager.releaseEntry(processId);
    }

    @Override
    public VectorClock getClock() throws RemoteException {
        return clock;
    }

    public void enterCriticalSection() {
        inCriticalSection = true;
        System.out.println("Process " + processId + " entered the critical section.");
    }
    
    public void exitCriticalSection() {
        inCriticalSection = false;
    } 
}
