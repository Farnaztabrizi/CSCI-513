package com.company;

public class SelectionSort implements SortStrategy{
    long startTime = 0;
    long endTime   = 0;
    long totalTime = 0;

    @Override
    public long[] sort(long[] ar) {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < ar.length-1; i++)
        {
            int min = i;
            for (int j = i+1; j < ar.length; j++)
                if (ar[j] < ar[min]) min = j;
            long temp = ar[i];
            ar[i] = ar[min];
            ar[min] = temp;
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
        return "Selection Sort";
    }
}
