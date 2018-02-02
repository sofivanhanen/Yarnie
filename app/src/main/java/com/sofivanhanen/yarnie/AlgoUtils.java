package com.sofivanhanen.yarnie;

import java.util.ArrayList;

/**
 * Created by sofvanh on 31/01/18.
 */

public class AlgoUtils {

    // TODO: Get rid of the ArrayLists here

    // Helper method for finding bigger value
    public static int max(int a, int b) {
        if (b > a) return b;
        else return a;
    }

    // Helper algorithm to 'backtrack' matrix and find the values used
    // We assume that we're looking at the last value in the matrix.
    // TODO: Make this work with project objects.
    // TODO: Create tests for this alg.
    private static ArrayList<Integer> findRelevantValues(int[][] matrix, int[] values, int[] weights) {

        // Starting indices
        int i = matrix.length - 1;
        int j = matrix[0].length - 1;

        if (i < 1 || j < 1) return new ArrayList<>();

        // Right now we are returning a list of values. Of course, what we want to return, is a list of projects etc.
        ArrayList<Integer> retVals = new ArrayList<>();

        loop : for (; i > 0; i--) {
            if (matrix[i-1][j] == matrix[i][j])
                // In the matrix, if the value above is the same, the item defining that row was not used.
                continue;
            else {
                // Understanding of the 0/1 algorithm is required to understand this
                retVals.add(values[i]);
                j -= weights[i];
                if (j == 0) break loop;
            }
        }

        return retVals;

    }

    // First algorithm
    // 0/1 knapsack problem with no value; goal is maximum weight.
    // I've taken a generic 0/1 solution and taken out the values array, replacing it's place
    // in the algorithm with the weights array. This way max weight is respected, while
    // total weight is valued; in the end our bag is as full as we can get.
    // TODO: Make this work with project objects.
    // TODO: Create tests for this alg.
    public static ArrayList<Integer> knapsackWeightOnly(int[] weights, int maxWeight) {

        if (weights.length == 0 || maxWeight == 0) return new ArrayList<>();

        // Using dynamic programming
        int temp[][] = new int[weights.length+1][maxWeight+1];

        for(int i = 0; i <= weights.length; i++) {
            for (int w = 0; w <= maxWeight; w++) {
                if (i == 0 || w == 0)
                    // Where maxweight = 0 and # of items = 0, value is 0.
                    temp[i][w] = 0;
                else if (weights[i-1] <= w)
                    // This looks confusing because in temp, row and column 0 are full of 0's,
                    // but in weights the actual values start at 0.
                    temp[i][w] = max(weights[i-1] + temp[i-1][w-weights[i-1]],  temp[i-1][w]);
                else
                    temp[i][w] = temp[i-1][w];
            }
        }

        return findRelevantValues(temp, weights, weights);

    }
}