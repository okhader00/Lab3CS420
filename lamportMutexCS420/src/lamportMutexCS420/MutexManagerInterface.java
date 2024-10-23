package lamportMutexCS420;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MutexManagerInterface extends Remote {
	void requestEntry(int processId, VectorClock clock) throws RemoteException;
    void releaseEntry(int processId) throws RemoteException;
}
