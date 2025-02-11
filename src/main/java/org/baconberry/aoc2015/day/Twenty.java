package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Twenty implements ISolver {
    int multiplier = 10;

    @Override
    public String solve(List<String> lines, int part) {
        int minPresents = Integer.parseInt(lines.getFirst());
        int c = 0;
        int n = 0;
        if (part == 2) {
            multiplier = 11;
        }
        while (c < minPresents) {
            c = calculatePresents(++n, part);
        }
        return String.valueOf(n);
    }

    int calculatePresents(int houseNumber, int part) {
        int sum = houseNumber * multiplier;
        for (int i = 1; i <= houseNumber / 2; i++) {
            if (houseNumber % i == 0) {
                if (part == 2 && houseNumber / i > 50) {
                    continue;
                }
                sum += i * multiplier;
            }
        }
        return sum;
    }
}
