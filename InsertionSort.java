package com.company;

public class InsertionSort implements SortStrategy {
    long startTime = 0;
    long endTime = 0;
    long totalTime = 0;

    @Override
    public long[] sort(long[] ar) {
        startTime = System.currentTimeMillis();
        for (int i = 1; i < ar.length; i++) {
            long index = ar[i];
            int j = i;
            while (j > 0 && ar[j - 1] > index) {
                ar[j] = ar[j - 1];
                j--;
            }
            ar[j] = index;
        }
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        return ar;
    }

    @Override
    public long getSortTime() {
        return totalTime;
    }

    @Override
    public void resetTimer() {
        startTime = 0;
        endTime = 0;
    }

    @Override
    public String strategyName() {
        return "Insertion Sort";
    }


}
