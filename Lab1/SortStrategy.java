package com.company;

public interface SortStrategy {

    public long[] sort(long[] ar);


    long getSortTime();

    void resetTimer();

    String strategyName();
}
