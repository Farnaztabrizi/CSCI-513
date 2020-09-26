import java.util.concurrent.atomic.AtomicLong;

/**
 * Aggregator is a runnable that calculate the aggregation of array's elements from {@code from} to {@code to}
 */
public class Aggregator implements Runnable {
    private int[] inputArray;
    private int from, to;
    private AtomicLong total;

    /**
     * Constructor method fo {@code Aggregator}
     * @param inputArray the input array contains integers
     * @param from the start element of input array that aggregator calculate sum from it
     * @param to the end element of input array that aggregator calculate sum to it
     * @param total is an AtomicLong to store the sum of section's elements to it
     */
    public Aggregator(int[] inputArray, int from, int to, AtomicLong total) {
        this.inputArray = inputArray;
        this.from = from;
        this.to = to;
        this.total = total;
    }

    @Override
    public void run() {
        int sum = 0;

        for (int i = from; i <= to; i++) {
            sum += inputArray[i];
        }

        total.addAndGet(sum);
    }
}
