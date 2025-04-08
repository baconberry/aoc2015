package org.baconberry.aoc2015.day;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.CollectionUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
        if (part == 2) {
            ValPos[] valArr = new ValPos[contArr.length];
            for (int i = 0; i < contArr.length; i++) {
                valArr[i] = new ValPos(contArr[i], i);
            }
            int minLength = minCombination(new ValPos[]{}, valArr, liters);
            return String.valueOf(countMinCombination(new ValPos[]{}, valArr, liters, minLength));
        }
        var combinations = countCombinations(new int[containers.length], liters, new HashSet<>());
        return String.valueOf(combinations.getLeft());
    }

    Integer invalidMin = Integer.MAX_VALUE;

    int countMinCombination(ValPos[] containers, ValPos[] valArr, int litersToFill, int minLen) {
        if (litersToFill == 0 && containers.length == minLen) {
            return 1;
        }
        if (containers.length > minLen) {
            return 0;
        }
        if (litersToFill < 0 || valArr.length == 0) {
            return 0;
        }

        int with = countMinCombination(
                CollectionUtils.cowAdd(containers, valArr[0]),
                Arrays.copyOfRange(valArr, 1, valArr.length),
                litersToFill - valArr[0].value(),
                minLen
        );
        int without = countMinCombination(
                containers,
                Arrays.copyOfRange(valArr, 1, valArr.length),
                litersToFill,
                minLen
        );
        return with + without;
    }

    int minCombination(ValPos[] containers, ValPos[] valArr, int litersToFill) {
        if (litersToFill == 0) {
            return containers.length;
        }
        if (litersToFill < 0 || valArr.length == 0) {
            return Integer.MAX_VALUE;
        }

        var with = minCombination(
                CollectionUtils.cowAdd(containers, valArr[0]),
                Arrays.copyOfRange(valArr, 1, valArr.length),
                litersToFill - valArr[0].value()
        );
        var without = minCombination(
                containers,
                Arrays.copyOfRange(valArr, 1, valArr.length),
                litersToFill
        );

        return Math.min(with, without);
    }

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

    record ValPos(int value, int pos) {
    }
}
