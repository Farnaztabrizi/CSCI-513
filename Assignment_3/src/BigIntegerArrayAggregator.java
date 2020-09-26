import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class BigIntegerArrayAggregator {
    /**
     * generate an int array with the random number between 0 to 200 with the size of {@code size}
     *
     * @param size the size of result array
     * @return a random {@code int[]}
     */
    static int[] generateRandomIntArray(int size) {
        //SET THE RANDOM SEED PARAMETER TO GENERATE SAME ARRAY IN EVERY RUN
        Random random = new Random(0);

        //GENERATE AN ARRAY WITH LENGTH OF 'SIZE' CONTAINS INTEGER RANDOM NUMBERS BETWEEN 0 TO 200
        int[] result = random.ints(size, 0, 200).toArray();

        return result;
    }

    /**
     * sum a big array of integers by a thread pool with size of {@code threadPoolSize}. each thread calculate the
     * {@code bufferSize} of elements of the intended array.
     *
     * @param intArray       the input array of integers
     * @param threadPoolSize the thread pool size
     * @param bufferSize     the number of elements that a thread want to aggregate
     * @return sum of all input array elements
     */
    static long sum(int[] intArray, int threadPoolSize, int bufferSize) {
        //THE TOTAL OBJECT IS INSTANCE OF ATOMIC LONG THAT DOES NOT ALLOW MULTIPLE CONCURRENT WRITES
        AtomicLong total = new AtomicLong();

        //CREATE A POOL OF THREADS. THE NUMBER OF THREADS IS EQUAL TO 'threadPoolSize'
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        int maxNumberOfRunnables = (int) Math.ceil(intArray.length / bufferSize);
        for (int i = 0; i < maxNumberOfRunnables; i++) {

            //FOR LAST SECTION OF INPUT ARRAY
            if (i == maxNumberOfRunnables - 1) {
                //CREATE AGGREGATOR RUNNABLE TO CALCULATE THE SUM OF INTENDED SECTION
                Aggregator aggregator =
                        new Aggregator(intArray, i * bufferSize, intArray.length - 1, total);

                //SUBMIT THE AGGREGATOR TASK TO THE THREAD POOL TO RUN, AFTER A THREAD END THE PREVIOUS TASK
                executorService.submit(aggregator);
            }

            //FOR OTHER SECTIONS ON INPUT ARRAY
            else {
                //CREATE AGGREGATOR RUNNABLE TO CALCULATE THE SUM OF INTENDED SECTION
                Aggregator aggregator =
                        new Aggregator(intArray, i * bufferSize, (i + 1) * bufferSize - 1, total);

                //SUBMIT THE AGGREGATOR TASK TO THE THREAD POOL TO RUN, AFTER A THREAD END THE PREVIOUS TASK
                executorService.submit(aggregator);
            }
        }

        //WAIT FOR THREAD POOL TO END ALL TASKS THEN SHUT DOWN IT
        executorService.shutdown();

        //WAIT TO COMPLETE AGGREGATION PROCESS
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
        }

        return total.get();
    }
}
