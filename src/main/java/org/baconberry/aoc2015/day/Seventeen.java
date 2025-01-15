package org.baconberry.aoc2015.day;

import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Seventeen implements ISolver {
    @Setter
    private int liters = 150;
    int[] containers = new int[]{20, 15, 10, 5, 5};

    @Override
    public String solve(List<String> lines, int part) {
        int[] contArr = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            contArr[i] = Integer.parseInt(lines.get(i).trim());
        }
        containers = contArr;
        var combinations = countCombinations(new int[containers.length], liters, new HashSet<>());
        if (part == 2) {
            return String.valueOf(combinations.getRight());
        }
        return String.valueOf(combinations.getLeft());
    }

    Integer invalidMin = Integer.MAX_VALUE;

    Pair<Integer, Integer> countCombinations(int[] arr, int litersRemaining, Set<String> memo) {
        if (!isValidArr(arr)) {
            return Pair.of(0, invalidMin);
        }
        if (litersRemaining == 0) {
            var key = Arrays.toString(arr);
            if (memo.contains(key)) {
                return Pair.of(0, invalidMin);
            }
            memo.add(key);
            return Pair.of(1, Arrays.stream(arr).sum());
        }
        if (litersRemaining < 0) {
            return Pair.of(0, invalidMin);
        }

        int sum = 0;
        var minList = new ArrayList<Integer>();
        for (int i = 0; i < containers.length; i++) {
            int[] newArr = Arrays.copyOf(arr, arr.length);
            newArr[i]++;
            var res = countCombinations(newArr, litersRemaining - containers[i], memo);
            sum += res.getLeft();
            minList.add(res.getRight());
        }
        int min = minList.stream().mapToInt(i -> i).min().getAsInt();
        var count = (int) minList.stream().filter(i -> i.equals(min)).count();
        return Pair.of(sum, count);
    }

    private boolean isValidArr(int[] arr) {
        for (int i : arr) {
            if (i > 1) {
                return false;
            }
        }
        return true;
    }
}
