package lamportMutexCS420;

//Request message class to store in requestqueue
public class Request {
    private int processId;
    private VectorClock clock;

    public Request(int processId, VectorClock clock) {
        this.processId = processId;
        this.clock = clock;
    }

    public int getProcessId() {
        return processId;
    }

    public VectorClock getClock() {
        return clock;
    }
}
