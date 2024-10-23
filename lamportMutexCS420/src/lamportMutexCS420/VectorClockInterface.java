package lamportMutexCS420;

public interface VectorClockInterface {
	int[] getClock();
    void increment(int processId);
    void update(int[] remoteClock);
}
