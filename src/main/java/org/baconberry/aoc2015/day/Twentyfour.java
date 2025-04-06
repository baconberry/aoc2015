package org.baconberry.aoc2015.day;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.baconberry.aoc2015.ISolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.baconberry.aoc2015.CollectionUtils.cowAddInt;
import static org.baconberry.aoc2015.CollectionUtils.merge;

@Slf4j
public class Twentyfour implements ISolver {
    private final Counter ops = Metrics.counter("Twentyfor.operations");
    private static final int[] EMPTY_ARR = new int[]{};

    @Override
    public String solve(List<String> lines, int part) {
        int[] weights = lines.stream()
                .filter(Predicate.not(String::isBlank))
                .mapToInt(Integer::parseInt)
                .toArray();
        int total = sumArr(weights);
        if (total % 3 != 0) {
            throw new IllegalArgumentException("Invalid input, can't arrange 3 groups of the same weight");
        }
        this.groupTarget = total / 3;
        Arrays.sort(weights);
        int[] aux = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            aux[i] = weights[weights.length - 1 - i];
        }
        weights = aux;

        coinAlgo(weights, EMPTY_ARR, EMPTY_ARR, groupTarget, 0);
        log.info("Total operations [{}]", operationCounter);

        return String.valueOf(minQe);
    }

    int operationCounter = 0;

    int groupTarget;
    BigInteger minQe = BigInteger.valueOf(Long.MAX_VALUE);

    int minLen = Integer.MAX_VALUE;


    private int[] coinAlgo(int[] coins, int[] group, int[] discarded, int sum, int level) {
        operationCounter++;
        ops.increment();
        if (level == 2) {
            // in this level there is only 1 possible sum of coins
            if (sumArr(coins) == sum) {
                return coins;
            }
            return null;
        }

        if (sum == 0) {
            if (coinAlgo(merge(coins, discarded), EMPTY_ARR, EMPTY_ARR, groupTarget, level + 1) != null) {
                if (level == 0) {
                    if (minLen < group.length) {
//                        log.info("Valid group found but longer than minLen [{}]", group);
                        return null;
                    } else if (minLen > group.length || productArr(group).compareTo(minQe) < 0) {
                        minLen = group.length;
                        minQe = productArr(group);
//                        log.info("Found new min arr [{}]", group);
                    }
                } else {
                    return group;
                }
            }
            return null;
        } else if (sum < 0 || coins.length == 0) {
            return null;
        }
        int[] coinsWithoutFirst = Arrays.copyOfRange(coins, 1, coins.length);

        //branch with
        int[] with = coinAlgo(coinsWithoutFirst, cowAddInt(group, coins[0]), discarded, sum - coins[0], level);
        if (with != null) {
            return with;
        }

        // branch without
        return coinAlgo(coinsWithoutFirst, group, cowAddInt(discarded, coins[0]), sum, level);
    }


    static BigInteger productArr(int[] a) {
        var product = BigInteger.ONE;
        for (int i : a) {
            product = product.multiply(BigInteger.valueOf(i));
        }
        return product;
    }

    static int sumArr(int[] a) {
        int sum = 0;
        for (int i : a) {
            sum += i;
        }
        return sum;
    }

}
