package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.StringTokenizer;

public class WorldPopulation {

    static SortStrategy sortStrategy;
    long[] population = new long[13484]; // Cheating because we know number of records!!

    // Lab Exercise: After creating some strategy classes -- set the default
    // strategy here.

    public WorldPopulation() {
        sortStrategy = new BubbleSort(); // Set the default strategy here.
    }

    public void readInputFile() {
        population = readPopulationFile("src\\WorldPopulation.csv");
    }

    public void setStrategy(SortStrategy strategy) {
        sortStrategy = strategy;
        //sortStrategy.sort(population);
    }

    // Lab Exercise: Read in the WorldPopulation.csv
    // Extract ONLY the numbers and store them into population[]

    public long[] readPopulationFile(String fileName) {
        int i = 0;
        try {
            FileReader inputFile = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(inputFile);
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                while (tokenizer.hasMoreTokens()) {

                    tokenizer.nextToken();
                    tokenizer.nextToken();
                    long count = Long.parseLong(tokenizer.nextToken());
                    population[i++] = count;
                }

            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return population;
    }

    // Lab Exercise. Complete this method.
    // Delegate sorting to the strategy object
    public void sortPopulation() {
        sortStrategy.sort(population);
        System.out.println("The sorting method is: " + sortStrategy.strategyName());
        System.out.println("The total time for sorting is: " + sortStrategy.getSortTime() + " mseconds.");
        //System.out.println();

    }

    public void computeTotalPopulation() {
        System.out.println("dd");
    }

    // Experiment with various strategies.
    // Create 3 strategies -- Bubble, insertion, and selection sort.
    public static void main(String[] args) {
        WorldPopulation worldPopulation = new WorldPopulation();

        System.out.println("Implementation of Sorting strategies on unsorted data:");
        // Bubble Sort
        worldPopulation.readInputFile();
        worldPopulation.setStrategy(new BubbleSort());
        worldPopulation.sortPopulation();

        // Selection Sort
        worldPopulation.readInputFile();
        worldPopulation.setStrategy(new SelectionSort());
        worldPopulation.sortPopulation();

        // Insertion Sort
        worldPopulation.readInputFile();
        worldPopulation.setStrategy(new InsertionSort());
        worldPopulation.sortPopulation();

        System.out.println();
        System.out.println("Implementation of Sorting strategies on sorted data:");

        // Bubble Sort
        worldPopulation.setStrategy(new BubbleSort());
        worldPopulation.sortPopulation();

        // Selection Sort
        worldPopulation.setStrategy(new SelectionSort());
        worldPopulation.sortPopulation();

        // Insertion Sort
        worldPopulation.setStrategy(new InsertionSort());
        worldPopulation.sortPopulation();
    }

}
