package com.company.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operations {
    private final List<Integer> numbers;
    private List<Computer> threads = new ArrayList<>();
    private final int numberOfThreads;

    public Operations(List<Integer> numbers, int numberOfThreads) {
        this.numbers = numbers;
        this.numberOfThreads = numberOfThreads;
    }

    public int calculateSum() {
        int sum = 0;
        int numberOfElements = numbers.size();
        int ponderOfThread = numberOfElements / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * ponderOfThread;
            int finish = i != numberOfThreads-1 ? (i+1) * ponderOfThread : numberOfElements;
            SumComputer sumComputer = new SumComputer(start, finish, numbers);
            threads.add(sumComputer);
            sumComputer.setPriority(10);
            sumComputer.start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException ignored) {

            } finally {
                sum += threads.get(i).getResult();
            }
        }
        threads.clear();
        return sum;
    }

    /**
     * Generic implementation of a thread which also has a numeric value associated
     */
    private static abstract class Computer extends Thread {
        protected long computedResult = 0;

        public long getResult() {
            return computedResult;
        }

    }

    private static class SumComputer extends Computer {

        private final int startIndex;
        private final int finalIndex;
        private final List<Integer> numbers;

        public SumComputer(int startIndex, int finalIndex, List<Integer> numbers) {
            this.startIndex = startIndex;
            this.finalIndex = finalIndex;
            this.numbers = numbers;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < finalIndex; i++) {
                 computedResult += numbers.get(i);
            }
            //System.out.format("Indexes: %d-%d: Sum: %f\n", startIndex, finalIndex-1, computedResult);
        }
    }

    public static List<Integer> generateRandomNumbers(int range) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < range; i++) {
            numbers.add(random.nextInt(1000));
        }
        return numbers;
    }

}
