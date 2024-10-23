package lamportMutexCS420;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MutexManager extends UnicastRemoteObject implements MutexManagerInterface {

    private List<Request> requestQueue; //Queue to hold request message objects
    private int currentProcessInCriticalSection; //Id of process in critical section or -1 if none
    
    protected MutexManager() throws RemoteException {
        this.requestQueue = new ArrayList<>();
        this.currentProcessInCriticalSection = -1;
    }

    @Override
    public synchronized void requestEntry(int processId, VectorClock clock) throws RemoteException {
        Request newRequest = new Request(processId, clock);
        requestQueue.add(newRequest);
        System.out.println("Request to enter critical section sent by Process " + processId);

        //Sort queue whenever new request added
        sortRequests();

        //If critical section is free start processing requests
        if (currentProcessInCriticalSection == -1) {
        	processNextRequest();
        }
    }

    //Method to process releases messages from processes
    @Override
    public synchronized void releaseEntry(int processId) throws RemoteException {
    	//Check that releasing process is actually in critical section
        if (currentProcessInCriticalSection == processId) {
            System.out.println("Process " + processId + " exited the critical section.");
            currentProcessInCriticalSection = -1; //Freeing critical section
            
            //Remove the process from the request queue
            requestQueue.removeIf(request -> request.getProcessId() == processId);

            processNextRequest();
        }
    }
    
    private void sortRequests() {
        //Sort requests by comparing vectorclocks
        requestQueue.sort((r1, r2) -> {
            int clockComparison = r1.getClock().compareTo(r2.getClock());
            if (clockComparison != 0) {
                return clockComparison;
            } else {
                return Integer.compare(r1.getProcessId(), r2.getProcessId());
            }
        });
    }

    private void processNextRequest() throws RemoteException {
        if (currentProcessInCriticalSection == -1 && !requestQueue.isEmpty()) {
        	//When critical section is free and requests remain, take next request and grant access
            Request nextRequest = requestQueue.get(0);
            currentProcessInCriticalSection = nextRequest.getProcessId();

            ProcessInterface process = null;
			try {
				process = (ProcessInterface) java.rmi.Naming.lookup("rmi://localhost/Process" + nextRequest.getProcessId());
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
            process.enterCriticalSection(); 
        }
    }
}
