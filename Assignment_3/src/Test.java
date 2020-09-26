import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class Test {
    //THE SIZE OF RANDOM INT ARRAY
    final static int ARRAY_SIZE = 200_000_000;
    final static int THREAD_POOL_SIZE = 10;
    final static int BUFFER_SIZE = 2_000_000;

    public static void main(String[] args) {
        int arraySize = ARRAY_SIZE;
        int threadPoolSize = THREAD_POOL_SIZE;
        int bufferSize = BUFFER_SIZE;

        if (args.length > 0) {
            arraySize = Integer.parseInt(args[0]);
            threadPoolSize = Integer.parseInt(args[1]);
            bufferSize = Integer.parseInt(args[2]);
        }

        //GENERATE A BIG RANDOM ARRAY OF INTEGERS
        int[] bigIntArray = BigIntegerArrayAggregator.generateRandomIntArray(arraySize);

        /*CALCULATE THE SUM OF ARRAY'S ELEMENTS BY POOL OF THREADS. ANY THREAD IS USED MULTIPLE TIMES TO CALCULATE SUM
        OF INTENDED SECTION OF ARRAY
         */
        long total = BigIntegerArrayAggregator.sum(bigIntArray, threadPoolSize, bufferSize);

        String formattedTotal = String.format("%,d", total);
        System.out.println("sum: " + formattedTotal);
    }
}
