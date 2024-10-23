package lamportMutexCS420;

import java.util.Arrays;

public class VectorClock implements VectorClockInterface, Comparable<VectorClock> {
	private int[] clock;

    public VectorClock() {
        this.clock = new int[4]; //For 4 processes
    }

    @Override
    public int[] getClock() {
        return clock.clone();
    }

    @Override
    public void increment(int processId) {
        clock[processId]++;
    }

    @Override
    public void update(int[] remoteClock) {
        for (int i = 0; i < clock.length; i++) {
            clock[i] = Math.max(clock[i], remoteClock[i]);
        }
    }

	@Override
	//Comparing clocks by taking total clock value
	public int compareTo(VectorClock o) {
		int sumThis = Arrays.stream(this.clock).sum();
        int sumOther = Arrays.stream(o.clock).sum();
        return Integer.compare(sumThis, sumOther);
	}
}
