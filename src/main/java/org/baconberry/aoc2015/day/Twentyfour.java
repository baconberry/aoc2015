package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.CollectionUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class Twentyfour implements ISolver {
    ExecutorService executor = Executors.newWorkStealingPool();
    @Override
    public String solve(List<String> lines, int part) {
        int[] weights = lines.stream()
                .filter(Predicate.not(String::isBlank))
                .mapToInt(Integer::parseInt)
                .toArray();
        int total = sum(weights);
        if (total % 3 != 0) {
            throw new IllegalArgumentException("Invalid input, can't arrange 3 groups of the same weight");
        }
        this.groupTarget = total / 3;
        int[] qe = permuteGroups(new int[]{}, new int[]{}, new int[]{}, weights);

        return String.valueOf(product(qe));
    }

    int groupTarget;
    long minQe = Integer.MAX_VALUE;
    int minLenA = Integer.MAX_VALUE;

    private int[] permuteGroups(int[] a, int[] b, int[] c, int[] weights) {
        if (a.length > minLenA) {
            return null;
        }
        int aSum = sum(a);
        if (aSum > groupTarget
                || sum(b) > groupTarget
                || sum(c) > groupTarget) {
            return null;
        }
        if (weights.length == 0) {
            if (aSum == sum(b) && aSum == sum(c)) {
                return a;
            }
            return null;
        }

        int[] res = null;
        int[] newWeights = Arrays.copyOfRange(weights, 0, weights.length);
        for (int weight : weights) {
            newWeights = Arrays.copyOfRange(newWeights, 1, newWeights.length);
            int[] localResult = permuteGroups(CollectionUtils.cowAddInt(a, weight), b, c, newWeights);
            if (validateLocalResult(localResult)) {
                res = CollectionUtils.cowAddInt(a, weight);
            }
            localResult = permuteGroups(a, CollectionUtils.cowAddInt(b, weight), c, newWeights);
            if (validateLocalResult(localResult)) {
                res = CollectionUtils.cowAddInt(b, weight);
            }
            localResult = permuteGroups(a, b, CollectionUtils.cowAddInt(c, weight), newWeights);
            if (validateLocalResult(localResult)) {
                res = CollectionUtils.cowAddInt(c, weight);
            }
        }
        return res;
    }

    int product(int[] a) {
        int product = 1;
        for (int i : a) {
            product *= i;
        }
        return product;
    }
    int sum(int[] a) {
        int sum = 0;
        for (int i : a) {
            sum += i;
        }
        return sum;
    }

    private boolean validateLocalResult(int[] localResult) {
        if (localResult == null) {
            return false;
        }
        if (localResult.length == minLenA && product(localResult) < minQe) {
            minQe = product(localResult);
            return true;
        }
        if (localResult.length < minLenA) {
            minQe = product(localResult);
            minLenA = localResult.length;
            return true;
        }
        return false;
    }
}
