package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;

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

    // naive 110,62s user 0,27s system 99% cpu 1:50,99 total
    // optimized 0,40s user 0,04s system 102% cpu 0,436 total
    int calculatePresents(int houseNumber, int part) {
        int sum = 0;
        int sqr = (int) Math.sqrt(houseNumber);
        for (int i = 1; i <= sqr; i++) {
            if (houseNumber % i == 0) {
                int j = houseNumber / i;
                if (part == 1 || (part == 2 && j <= 50)) {
                    sum += i * multiplier;
                }
                if (j != sqr && (part == 1 || (part == 2 && i <= 50))) {
                    sum += j * multiplier;
                }
            }
        }
        return sum;
    }
}
