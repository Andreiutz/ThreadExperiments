package com.company;

import com.company.lists.Operations;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> numbers = Operations.generateRandomNumbers(100000);
        Operations operations = new Operations(numbers, 4);
        long startTime = System.nanoTime();
        long result = operations.calculateSum();
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Result = " + result);
        System.out.println("Total execution time: " + (double) elapsedTime / 1000000 + " milliseconds");
    }
}
