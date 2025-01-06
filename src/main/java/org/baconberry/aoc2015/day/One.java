package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class One implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int result = 0;
        int counter = 0;
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                counter++;
                if (c == '(') {
                    result++;
                } else if (c == ')') {
                    result--;
                }
                if (part == 2 && result == -1) {
                    return String.valueOf(counter);
                }
            }
        }
        return String.valueOf(result);
    }
}
